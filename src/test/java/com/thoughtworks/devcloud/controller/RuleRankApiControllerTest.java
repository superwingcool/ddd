package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
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

/**
 * Created by jiezhou on 23/03/2017.
 */
public class RuleRankApiControllerTest {

    @Mock
    private CIssuesService cIssuesService;

    @Mock
    private CProjectsService cProjectsService;

    @InjectMocks
    private RuleRankApiController ruleRankApiController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.VIOLATED;
        List<RuleRank> complexityRankList = new ArrayList<>();
        when(cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid, issueStatus))
                .thenReturn(complexityRankList);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(100L);

        ResponseObject responseObject = ruleRankApiController.getViolatedRules(devcloudProjectUuid);
        verify(cIssuesService, times(1)).findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
    }
}
