package com.thoughtworks.devcloud.service;

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
    public List<RuleRank> findViolatedCIssuesListByDevcloudProjectId(String devcloudProjectUuid) {
        return cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(devcloudProjectUuid, 0);
    }

    @Override
    public List<RuleRank> findIgnoredCIssuesListByDevcloudProjectId(String devcloudProjectUuid) {
        return cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(devcloudProjectUuid, 1);
    }

    @Override
    public List<RuleRank> findRevisedCIssuesListByDevcloudProjectId(String devcloudProjectUuid) {
        return cIssuesRepository.findCIssuesListByDevcloudProjectIdAndStatus(devcloudProjectUuid, 2);
    }
}
