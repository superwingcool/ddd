package com.thoughtworks.devcloud.controller;

import com.google.common.primitives.Ints;
import com.thoughtworks.devcloud.constants.CIssueManualStatusField;
import com.thoughtworks.devcloud.constants.CIssueStatusField;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.repository.CIssuesRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.service.CProjectsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TenantRankApiControllerTest {

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @Mock
    private CIssuesRepository cIssuesRepository;

    @Mock
    private CProjectsService cProjectsService;

    @Mock
    private CIssuesService cIssuesService;

    @InjectMocks
    private TenantRankApiController tenantRankApiController;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getViolatedRulesShouldReturnEmptyResponseObjectWhenNoList() throws Exception {
        String tenantId = "tenantId";
        IssueStatus issueStatus = mockService(tenantId,IssueStatus.VIOLATED);
        ResponseObject responseObject = tenantRankApiController.getViolatedRules(tenantId);
        verify(cIssuesService, times(1))
                .findCIssuesListByTenantId(tenantId,issueStatus);
    }

    @Test
    public void getIgnoredRulesShouldReturnEmptyResponseObjectWhenNoList() throws Exception {
        String tenantId = "tenantId";
        IssueStatus issueStatus = mockService(tenantId,IssueStatus.IGNORED);
        ResponseObject responseObject = tenantRankApiController.getIgnoredRules(tenantId);
        verify(cIssuesService, times(1))
                .findCIssuesListByTenantId(tenantId,issueStatus);
    }

    @Test
    public void getRevisedRulesShouldReturnEmptyResponseObjectWhenNoList() throws Exception {
        String tenantId = "tenantId";
        IssueStatus issueStatus = mockService(tenantId,IssueStatus.REVISED);
        ResponseObject responseObject = tenantRankApiController.getRevisedRules(tenantId);
        verify(cIssuesService, times(1))
                .findCIssuesListByTenantId(tenantId,issueStatus);
    }

    private IssueStatus mockService(String tenantId, IssueStatus issueStatus) {

        List<String> projects = new ArrayList<String>();
        projects.add("xxxx");
        when(cProjectsService.getProjectsByTenantId(tenantId)).thenReturn(projects);
        List<Long> snapShorts = new ArrayList<Long>();
        snapShorts.add(234l);
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapShorts);
        List<RuleRank> rankList = new ArrayList<>();
        rankList.add(mock(RuleRank.class));
        when(cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(projects,
                issueStatus.getStatusList(),
                issueStatus.getAllManualStatusList(),
                snapShorts)).thenReturn(rankList);
        when(cProjectsService.countDistinctByGitUrl(projects)).thenReturn(12l);
        return issueStatus;
    }

}