package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
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
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link ComplexityRankServiceImpl}.
 */
public class ComplexityRankServiceImplTest {

    @Mock
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @Mock
    private CProjectsService cProjectsService;

    @InjectMocks
    private ComplexityRankServiceImpl complexityRankServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullObjectException.class)
    public void findComplexityListByDevCloudProjectIdShouldThrowExceptionGivenNoProjects() {
        String devCloudProjectUuid = "XXXX";
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devCloudProjectUuid)))
                .thenReturn( new ArrayList<Long>());

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

        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devcloudProjectUuid)))
                .thenReturn(snapshotIdList);
        when(cProjectMeasuresRepository.findMeasureListByDevcloudProjectId(devcloudProjectUuid,
                ComplexityEnum.getAllMeasureNames(), snapshotIdList)).thenReturn(cProjectMeasuresList);

        List<ComplexityRank> complexityRankList =
                complexityRankServiceImpl.findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        Assert.assertTrue(complexityRankList.isEmpty());

    }

    @Test
    public void shouldReturnSame() {
        List<CProjectMeasures> cProjectMeasuresList = new ArrayList<>();
        List<ComplexityRank> complexityRankList =
                complexityRankServiceImpl.transform2ComplexityRank(cProjectMeasuresList);

        Assert.assertTrue(complexityRankList.isEmpty());
    }

    @Test(expected = NullObjectException.class)
    public void getComplexityListByTenantIdShouldThrowExceptionGivenNoProjects(){
        String tenantId = "tenantId";
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(new ArrayList<String>());
        complexityRankServiceImpl.getComplexityListByTenantId(tenantId);

    }

    @Test(expected = NullObjectException.class)
    public void getComplexityListByTenantIdShouldThrowExceptionGivenNoSnapshort(){
        String tenantId = "tenantId";
        List<String> projects = new ArrayList<>();
        projects.add("devProjectId");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(new ArrayList<>());
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
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);
        when(cProjectMeasuresRepository.getMeasureListByProjects(projects, ComplexityEnum.getAllMeasureNames(),
                snapShorts)).thenReturn(new ArrayList<Object[]>());
        ResultObject<ComplexityRank> result = complexityRankServiceImpl.getComplexityListByTenantId(tenantId);
        assertThat(result, notNullValue());
        assertThat(result.getTotal(), is("0"));
        assertThat(result.getInfo().size(), is(0));

    }

    @Test
    public void getComplexityListByTenantIdShouldReturnSuccessGivenRightRanks(){
        String tenantId = "tenantId";
        List<String> projects = new ArrayList<>();
        projects.add("devProjectId");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        List<Long> snapShorts = new ArrayList<>();
        snapShorts.add(234l);
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);
        List<Object[]> ranks = new ArrayList<Object[]>();
        Object[] obj = new Object[]{"repos", "projectName", BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO};
        ranks.add(obj);
        when(cProjectMeasuresRepository.getMeasureListByProjects(projects, ComplexityEnum.getAllMeasureNames(),
                snapShorts)).thenReturn(ranks);
        when(cProjectsService.countDistinctByGitUrl(projects)).thenReturn(12l);
        ResultObject<ComplexityRank> result = complexityRankServiceImpl.getComplexityListByTenantId(tenantId);
        assertThat(result, notNullValue());
        assertThat(result.getRepoCheckedCount(), is("12"));
        assertThat(result.getTotal(), is("1"));
        assertThat(result.getInfo().get(0).getRepoName(), is("repos"));
        assertThat(result.getInfo().get(0).getComplexity(), is(BigDecimal.ZERO));
    }
}
