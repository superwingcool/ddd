package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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

    @InjectMocks
    private DuplicatedLineRankServiceImpl duplicatedLineRankServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullObjectException.class)
    public void findMeasureListByDevCloudProjectIdShouldThrowExceptionGivenNull() {
        duplicatedLineRankServiceImpl.findMeasureListByDevcloudProjectId("project_uuid");

    }

    @Test(expected = NullObjectException.class)
    public void findMeasureListByDevCloudProjectIdShouldMeasuresGivenData() {
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
        List<DuplicatedLineRank> result = duplicatedLineRankServiceImpl.findMeasureListByDevcloudProjectId(tenantId);
        assertThat(result, notNullValue());
        assertThat(result.size(), is("1"));

        assertThat(result.get(0).getRepoName(), is("repos"));
        assertThat(result.get(0).getCodeLines(), is(BigDecimal.ZERO));

    }



}
