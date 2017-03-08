package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.RuleRank;

import java.util.List;


/**
 * Project service interface.
 */
public interface CIssuesService {

    List<RuleRank> findViolatedCIssuesListByDevcloudProjectId(String devcloudProjectUuid);

    List<RuleRank> findIgnoredCIssuesListByDevcloudProjectId(String devProjectUuid);

    List<RuleRank> findRevisedCIssuesListByDevcloudProjectId(String devProjectUuid);
}
