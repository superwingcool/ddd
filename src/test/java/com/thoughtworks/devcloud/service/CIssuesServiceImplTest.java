package com.thoughtworks.devcloud.service;

import com.google.common.primitives.Ints;
import com.thoughtworks.devcloud.constants.CIssueManualStatusField;
import com.thoughtworks.devcloud.constants.CIssueStatusField;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.repository.CIssuesRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Unit test for {@link CIssuesServiceImpl}.
 */
public class CIssuesServiceImplTest {

    @Mock
    private CIssuesRepository cIssuesRepository;

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @InjectMocks
    private CIssuesServiceImpl cIssuesServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnRuleRankList() {
        String devcloudProjectUuid = "XXXX";
        IssueStatus issueStatus = IssueStatus.IGNORED;

        List<RuleRank> rankList = new ArrayList<>();

        List<Long> snapshotIdList = new ArrayList<>();
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(devcloudProjectUuid)).thenReturn(snapshotIdList);
        when(cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(devcloudProjectUuid,
                Ints.asList(CIssueStatusField.UNSOLVED.value()),
                Ints.asList(CIssueManualStatusField.IGNORED.value()),
                snapshotIdList)).thenReturn(rankList);
        List<RuleRank> ruleRankList = cIssuesServiceImpl.findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
        Assert.assertTrue(ruleRankList.isEmpty());
    }
}
