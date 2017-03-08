package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.RuleRank;

import java.util.List;


/**
 * Project service interface.
 */
public interface CIssuesService {

    List<RuleRank> findViolatedCIssuesListByDevcloudProjectId(String devcloudProjectUuid, int status);

    List<RuleRank> findIgnoredCIssuesListByDevcloudProjectId(String devProjectUuid, int status);

    List<RuleRank> findRevisedCIssuesListByDevcloudProjectId(String devProjectUuid, int status);
}
