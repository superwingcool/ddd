package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.DuplicatedLineEnum;
import com.thoughtworks.devcloud.domain.CMetrics;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.domain.CProjects;
import com.thoughtworks.devcloud.domain.CSnapshots;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.mapper.ProjectDuplicatedLineMeasuresMapper;
import com.thoughtworks.devcloud.mapper.TenantDuplicatedLineMeasuresMapper;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link DuplicatedLineRankServiceImpl}.
 */
public class DuplicatedLineRankServiceImplTest {

    @Mock
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @Mock
    private CProjectsService cProjectsService;

    @Mock
    private ProjectDuplicatedLineMeasuresMapper projectDuplicatedLineMeasuresMapper;

    @Mock
    private TenantDuplicatedLineMeasuresMapper tenantDuplicatedLineMeasuresMapper;

    @InjectMocks
    private DuplicatedLineRankServiceImpl duplicatedLineRankServiceImpl;

    private String tenantId = "tenantId";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullObjectException.class)
    public void findMeasureListByDevCloudProjectIdShouldThrowExceptionGivenNull() {
        duplicatedLineRankServiceImpl.findMeasureListByDevcloudProjectId("project_uuid");

    }

    @Test
    public void findMeasureListByDevCloudProjectIdShouldMeasuresGivenData() {
        String projectId = "project_id";
        List<Long> snapShorts = getSnapshots();
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(projectId))).thenReturn(snapShorts);

        when(cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(Arrays.asList(projectId),
                generateMeasureNameList(),
                snapShorts)).thenReturn(getMeasures());

        when(projectDuplicatedLineMeasuresMapper.transformMeasure2Rank(anyListOf(CProjectMeasures.class))).
                thenReturn(getDuplicatedLineRanks());

        List<DuplicatedLineRank> ranks = duplicatedLineRankServiceImpl
                .findMeasureListByDevcloudProjectId(projectId);

        assertThat(ranks, notNullValue());
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getDuplicatedLinesDensity(), is("10%"));
        assertThat(ranks.get(0).getRepoName(), is("http"));
    }

    @Test(expected = NullObjectException.class)
    public void findMeasureListByDevCloudTenantIdShouldThrowExceptionGivenNullProject() {
        duplicatedLineRankServiceImpl.findMeasureListByDevCloudTenantId(tenantId);
    }

    @Test(expected = NullObjectException.class)
    public void findMeasureListByDevCloudTenantIdShouldThrowExceptionGivenNullSnapshot() {
        List<String> projects = getProjects();
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        duplicatedLineRankServiceImpl.findMeasureListByDevCloudTenantId(tenantId);
    }

    @Test
    public void findMeasureListByDevCloudTenantIdShouldReturnSuccessGivenRightData() {

        List<String> projects = getProjects();
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);

        List<Long> snapShorts = getSnapshots();
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);

        when(cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(projects, generateMeasureNameList(),
                snapShorts)).thenReturn(getMeasures());

        when(tenantDuplicatedLineMeasuresMapper.transformMeasure2Rank(anyObject())).
                thenReturn(getTenantDuplicatedLineRanks());

        when(cProjectsService.countDistinctByGitUrl(projects)).thenReturn(12l);

        ResultObject<TenantDuplicatedLineRank> ranks = duplicatedLineRankServiceImpl
                .findMeasureListByDevCloudTenantId("tenantId");

        assertThat(ranks, notNullValue());
        assertThat(ranks.getRepoCheckedCount(), is("12"));
        assertThat(ranks.getTotal(), is("1"));
        assertThat(ranks.getProjectCount(), is("1"));
        assertThat(ranks.getInfo().get(0).getDuplicatedLinesDensity(), is("10%"));
        assertThat(ranks.getInfo().get(0).getRepoName(), is("http"));
        assertThat(ranks.getInfo().get(0).getProjectName(), is("projectName"));

    }

    private List<String> getProjects() {
        List<String> projects = new ArrayList<>();
        projects.add("devProjectId");
        return projects;
    }

    private List<Long> getSnapshots() {
        List<Long> snapShorts = new ArrayList<>();
        snapShorts.add(234l);
        return snapShorts;
    }

    private List<CProjectMeasures> getMeasures() {
        List<CProjectMeasures> measures = new ArrayList<>();
        CProjectMeasures cProjectMeasures = new CProjectMeasures();
        cProjectMeasures.setCSnapshots(createSnapshot());
        cProjectMeasures.setCMetrics(createCMetrics());
        cProjectMeasures.setValue(BigDecimal.TEN);
        cProjectMeasures.setCProjects(getProject());
        measures.add(cProjectMeasures);
        return measures;
    }

    private CProjects getProject() {
        CProjects project = new CProjects();
        project.setProjectName("projectName");
        return project;
    }

    private List<TenantDuplicatedLineRank> getTenantDuplicatedLineRanks() {
        List<TenantDuplicatedLineRank> ranks = new ArrayList<>();
        TenantDuplicatedLineRank duplicatedLineRank = new TenantDuplicatedLineRank();
        duplicatedLineRank.setCodeLines(BigDecimal.TEN);
        duplicatedLineRank.setDuplicatedLinesDensity("10%");
        duplicatedLineRank.setRepoName("http");
        duplicatedLineRank.setProjectName("projectName");
        ranks.add(duplicatedLineRank);
        return ranks;
    }

    private List<DuplicatedLineRank> getDuplicatedLineRanks() {
        List<DuplicatedLineRank> ranks = new ArrayList<>();
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        duplicatedLineRank.setCodeLines(BigDecimal.TEN);
        duplicatedLineRank.setDuplicatedLinesDensity("10%");
        duplicatedLineRank.setRepoName("http");
        ranks.add(duplicatedLineRank);
        return ranks;
    }

    private CMetrics createCMetrics() {
        CMetrics metrics = new CMetrics();
        metrics.setName("duplicated_lines");
        return metrics;
    }

    private CSnapshots createSnapshot() {
        CSnapshots snapshot = new CSnapshots();
        snapshot.setScmAddr("http://git.address.com");
        return snapshot;
    }

    private List<String> generateMeasureNameList() {
        return DuplicatedLineEnum.getAllMeasureNames();
    }



}
