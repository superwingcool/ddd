package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import com.thoughtworks.devcloud.service.DuplicatedLineRankService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ProjectRankApiControllerTest {

    @Mock
    private ComplexityRankService complexityRankService;

    @Mock
    private DuplicatedLineRankService duplicatedLineRankService;

    @Mock
    private CProjectsService cProjectsService;

    @Mock
    private CIssuesService cIssuesService;


    @InjectMocks
    private ProjectRankApiController projectRankApiController;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getComplexityRulesShouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        List<ComplexityRank> complexityRankList = new ArrayList<>();
        when(complexityRankService.findComplexityListByDevcloudProjectId(devcloudProjectUuid))
                .thenReturn(complexityRankList);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(100L);

        ResponseObject responseObject = projectRankApiController.getComplexityRules(devcloudProjectUuid);
        verify(complexityRankService, times(1)).findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        assertThat(responseObject.getError(), is(Null.NULL));
        assertThat(responseObject.getStatus(), is("success"));
    }

    @Test
    public void getDuplicatedLineRulesShouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        List<DuplicatedLineRank> duplicatedLineRanks = new ArrayList<>();
        when(duplicatedLineRankService.findMeasureListByDevcloudProjectId(devcloudProjectUuid))
                .thenReturn(duplicatedLineRanks);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(10L);

        ResponseObject responseObject = projectRankApiController.getDuplicatedLineRules(devcloudProjectUuid);
        verify(duplicatedLineRankService, times(1))
                .findMeasureListByDevcloudProjectId(devcloudProjectUuid);
        assertThat(responseObject.getError(), is(Null.NULL));
        assertThat(responseObject.getStatus(), is("success"));
    }

    @Test
    public void getRevisedRulesShouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.REVISED;
        List<RuleRank> complexityRankList = new ArrayList<>();
        when(cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid, issueStatus))
                .thenReturn(complexityRankList);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(100L);

        ResponseObject responseObject = projectRankApiController.getRevisedRules(devcloudProjectUuid);
        verify(cIssuesService, times(1)).findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
    }

    @Test
    public void getIgnoredRulesShouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.IGNORED;
        List<RuleRank> complexityRankList = new ArrayList<>();
        when(cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid, issueStatus))
                .thenReturn(complexityRankList);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(100L);

        ResponseObject responseObject = projectRankApiController.getIgnoredRules(devcloudProjectUuid);
        verify(cIssuesService, times(1)).findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
    }

    @Test
    public void getViolatedRulesShouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.VIOLATED;
        List<RuleRank> complexityRankList = new ArrayList<>();
        when(cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid, issueStatus))
                .thenReturn(complexityRankList);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(10L);

        ResponseObject responseObject = projectRankApiController.getViolatedRules(devcloudProjectUuid);
        verify(cIssuesService, times(1)).findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
    }

}