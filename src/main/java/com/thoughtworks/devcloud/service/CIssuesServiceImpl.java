package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.repository.CIssuesRepository;
import com.thoughtworks.devcloud.repository.CProjectsRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * Implementation of {@link CIssuesService}.
 */
@Service("cIssuesService")
@Transactional
public class CIssuesServiceImpl implements CIssuesService {

    @Autowired
    private CIssuesRepository cIssuesRepository;

    @Autowired
    private CSnapshotsService cSnapshotsService;

    @Autowired
    private CProjectsRepository cProjectsRepository;

    @Autowired
    private CProjectsService cProjectsService;

    @Override
    public List<RuleRank> findCIssuesListByDevcloudProjectId(String devCloudProjectUuid, IssueStatus issueStatus) {
        List<String> projectUUIDs = Arrays.asList(devCloudProjectUuid);
        List<Long> snapshotIdList = cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(projectUUIDs);
        return cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(projectUUIDs,
                issueStatus.getStatusList(), issueStatus.getAllManualStatusList(), snapshotIdList);
    }

    @Override
    public ResultObject<RuleRank> findCIssuesListByTenantId(String tenantId, IssueStatus issueStatus) {

        List<String> projects = cProjectsService.getProjectsByTenantId(tenantId);
        List<Long> snapshotIdList = cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(projects);

        List<RuleRank> ruleRank = cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(projects,
                issueStatus.getStatusList(), issueStatus.getAllManualStatusList(), snapshotIdList);
        Long counts = cProjectsService.countDistinctByGitUrl(projects);

        return new ResultObject<RuleRank>(String.valueOf(ruleRank.size()),
                                          ruleRank,
                                          String.valueOf(counts),
                                          String.valueOf(projects.size()));

    }
}
