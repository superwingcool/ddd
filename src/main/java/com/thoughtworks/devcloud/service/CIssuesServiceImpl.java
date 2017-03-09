package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.repository.CIssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link CIssuesService}.
 */
@Service("cIssuesService")
@Transactional
public class CIssuesServiceImpl implements CIssuesService {

    @Autowired
    private CIssuesRepository cIssuesRepository;

    @Override
    public List<RuleRank> findCIssuesListByDevcloudProjectId(String devcloudProjectUuid, IssueStatus issueStatus) {
        return cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(devcloudProjectUuid, issueStatus.value());
    }
}
