package com.thoughtworks.devcloud.service;

import com.google.common.primitives.Ints;
import com.thoughtworks.devcloud.constants.CIssueManualStatusField;
import com.thoughtworks.devcloud.constants.CIssueStatusField;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.repository.CIssuesRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.hibernate.criterion.NotNullExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link CIssuesServiceImpl}.
 */
public class CIssuesServiceImplTest {

    @Mock
    private CIssuesRepository cIssuesRepository;

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @Mock
    private CProjectsService cProjectsService;

    @InjectMocks
    private CIssuesServiceImpl cIssuesServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullObjectException.class)
    public void findCIssuesListByDevCloudProjectIdShouldThrowExceptionGivenNoSnapshort() {
        String devCloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.IGNORED;
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devCloudProjectUuid))).thenReturn(new ArrayList<Long>());
        List<RuleRank> ruleRankList = cIssuesServiceImpl.findCIssuesListByDevcloudProjectId(devCloudProjectUuid,
                issueStatus);
    }

    @Test
    public void findCIssuesListByDevcloudProjectIdShouldReturnRuleRankList() {
        String devcloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.IGNORED;

        List<RuleRank> rankList = new ArrayList<>();

        List<Long> snapshotIdList = new ArrayList<>();
        snapshotIdList.add(123l);
        List<String> projects = new ArrayList<String>();

        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devcloudProjectUuid))).thenReturn(snapshotIdList);
        when(cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(Arrays.asList(devcloudProjectUuid),
                Ints.asList(CIssueStatusField.UNSOLVED.value()),
                Ints.asList(CIssueManualStatusField.IGNORED.value()),
                snapshotIdList)).thenReturn(rankList);
        List<RuleRank> ruleRankList = cIssuesServiceImpl.findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
        Assert.assertTrue(ruleRankList.isEmpty());
    }

    @Test(expected = NullObjectException.class)
    public void findCIssuesListByTenantIdShouldThrowExceptionGivenNoProjects(){
        String tenantId = "tenantId";
        IssueStatus issueStatus = IssueStatus.VIOLATED;
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(new ArrayList<String>());
        ResultObject r = cIssuesServiceImpl.findCIssuesListByTenantId(tenantId,issueStatus);
    }

    @Test(expected = NullObjectException.class)
    public void findCIssuesListByTenantIdShouldThrowExceptionGivenNoSnapshot(){
        String tenantId = "tenantId";
        IssueStatus issueStatus = IssueStatus.VIOLATED;
        List<String> projects = new ArrayList<String>();
        projects.add("xxxx");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(new ArrayList<>());
        ResultObject r = cIssuesServiceImpl.findCIssuesListByTenantId(tenantId,issueStatus);
    }

    @Test
    public void findCIssuesListByTenantIdShouldReturnSuccessGivenData(){
        String tenantId = "tenantId";
        IssueStatus issueStatus = IssueStatus.VIOLATED;
        List<String> projects = new ArrayList<String>();
        projects.add("xxxx");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        List<Long> snapShorts = new ArrayList<Long>();
        snapShorts.add(234l);
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);
        List<RuleRank> rankList = new ArrayList<>();
        rankList.add(mock(RuleRank.class));
        when(cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(projects,
                Ints.asList(CIssueStatusField.UNSOLVED.value()),
                Ints.asList(CIssueManualStatusField.IGNORED.value()),
                snapShorts)).thenReturn(rankList);
        when(cProjectsService.countDistinctByGitUrl(projects)).thenReturn(12l);
        ResultObject r = cIssuesServiceImpl.findCIssuesListByTenantId(tenantId,issueStatus);
        assertThat(r, notNullValue());
    }
}
