package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CMetrics;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.domain.CProjects;
import com.thoughtworks.devcloud.domain.CSnapshots;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.mapper.ProjectComplexityMeasuresMapper;
import com.thoughtworks.devcloud.mapper.TenantComplexityMeasuresMapper;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link ComplexityRankServiceImpl}.
 */
public class ComplexityRankServiceImplTest {

    @Mock
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Mock
    private CSnapshotsService cSnapshotsService;

    @Mock
    private CProjectsService cProjectsService;

    @Mock
    private ProjectComplexityMeasuresMapper projectComplexityMeasuresMapper;

    @Mock
    private TenantComplexityMeasuresMapper tenantComplexityMeasuresMapper;

    @InjectMocks
    private ComplexityRankServiceImpl complexityRankServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullObjectException.class)
    public void findComplexityListByDevCloudProjectIdShouldThrowExceptionGivenNoProjects() {
        String devCloudProjectUuid = "XXXX";
        when(cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devCloudProjectUuid)))
                .thenThrow(NullObjectException.class);

        List<ComplexityRank> complexityRankList =
                complexityRankServiceImpl.findComplexityListByDevcloudProjectId(devCloudProjectUuid);
        Assert.assertTrue(complexityRankList.isEmpty());

    }

    @Test
    public void shouldReturnComplexityRankList() {
        String devcloudProjectUuid = "XXXX";
        List<Long> snapshotIdList = new ArrayList<Long>();
        snapshotIdList.add(1l);
        List<CProjectMeasures> cProjectMeasuresList = new ArrayList<>();
        List<String> devcloudProjectUuids = Arrays.asList(devcloudProjectUuid);
        when(cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(devcloudProjectUuids))
                .thenReturn(snapshotIdList);
        when(cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(devcloudProjectUuids,
                ComplexityEnum.getAllMeasureNames(), snapshotIdList)).thenReturn(cProjectMeasuresList);

        List<ComplexityRank> complexityRankList =
                complexityRankServiceImpl.findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        Assert.assertTrue(complexityRankList.isEmpty());

    }

    @Test(expected = NullObjectException.class)
    public void getComplexityListByTenantIdShouldThrowExceptionGivenNoProjects(){
        String tenantId = "tenantId";
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenThrow(NullObjectException.class);
        complexityRankServiceImpl.getComplexityListByTenantId(tenantId);

    }

    @Test(expected = NullObjectException.class)
    public void getComplexityListByTenantIdShouldThrowExceptionGivenNoSnapshort(){
        String tenantId = "tenantId";
        List<String> projects = new ArrayList<>();
        projects.add("devProjectId");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        when(cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(projects)).thenThrow(NullObjectException.class);
        complexityRankServiceImpl.getComplexityListByTenantId(tenantId);

    }

    @Test
    public void getComplexityListByTenantIdShouldReturnNullInformationGivenNoRanks(){
        String tenantId = "tenantId";
        List<String> projects = new ArrayList<>();
        projects.add("devProjectId");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        List<Long> snapShorts = new ArrayList<>();
        snapShorts.add(234l);
        when(cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);
        when(cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(projects, ComplexityEnum.getAllMeasureNames(),
                snapShorts)).thenReturn(new ArrayList<CProjectMeasures>());
        ResultObject<TenantComplexityRank> result = complexityRankServiceImpl.getComplexityListByTenantId(tenantId);
        assertThat(result, notNullValue());
        assertThat(result.getTotal(), is("0"));
        assertThat(result.getInfo().size(), is(0));

    }

    @Test
    public void getComplexityListByTenantIdShouldReturnSuccessGivenRightRanks(){
        List<String> projects = new ArrayList<>();
        projects.add("devProjectId");
        when(cProjectsService.getProjectsByTenantId("tenantId")).thenReturn(projects);
        List<Long> snapShorts = new ArrayList<>();
        snapShorts.add(234l);
        when(cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);
        when(cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(projects, ComplexityEnum.getAllMeasureNames(),
                snapShorts)).thenReturn(getProjectMeasures());
        when(tenantComplexityMeasuresMapper.transformMeasure2Rank(anyList())).thenReturn(getTenantRanks());
        when(cProjectsService.countDistinctByGitUrl(projects)).thenReturn(12l);
        ResultObject<TenantComplexityRank> result = complexityRankServiceImpl.getComplexityListByTenantId("tenantId");
        assertThat(result, notNullValue());
        assertThat(result.getRepoCheckedCount(), is("12"));
        assertThat(result.getTotal(), is("1"));
        assertThat(result.getInfo().get(0).getProjectName(), is("projectName"));
        assertThat(result.getInfo().get(0).getRepoName(), is("repos"));
        assertThat(result.getInfo().get(0).getComplexity(), is(BigDecimal.ONE));
    }

    private List<TenantComplexityRank> getTenantRanks() {

        List<TenantComplexityRank> ranks = new ArrayList<>();
        TenantComplexityRank rank = new TenantComplexityRank();
        rank.setProjectName("projectName");
        rank.setRepoName("repos");
        rank.setComplexity(BigDecimal.ONE);
        ranks.add(rank);
        return ranks;
    }

    protected List<CProjectMeasures> getProjectMeasures() {
        List<CProjectMeasures> cProjectMeasures = new ArrayList<>();
        CProjectMeasures cProjectMeasure1 = new CProjectMeasures();
        cProjectMeasure1.setCSnapshots(createSnapshot());
        cProjectMeasure1.setCProjects(createProject());
        cProjectMeasure1.setCMetrics(createCMetrics("file_complexity"));
        cProjectMeasure1.setValue(BigDecimal.TEN);
        cProjectMeasures.add(cProjectMeasure1);
        return cProjectMeasures;
    }

    private CProjects createProject(){
        CProjects project = new CProjects();
        project.setProjectName("projectName");
        return project;
    }

    protected CMetrics createCMetrics(String name) {
        CMetrics metrics = new CMetrics();
        metrics.setName(name);
        return metrics;
    }

    private CSnapshots createSnapshot() {
        CSnapshots snapshot = new CSnapshots();
        snapshot.setScmAddr("http");
        return snapshot;
    }

}
