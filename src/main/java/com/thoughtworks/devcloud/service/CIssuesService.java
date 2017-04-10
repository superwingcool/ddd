package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;

import java.util.List;


/**
 * Project service interface.
 */
public interface CIssuesService {

    List<RuleRank> findCIssuesListByDevcloudProjectId(String devProjectUuid, IssueStatus issueStatus);

    ResultObject<RuleRank> findCIssuesListByTenantId(String tenantId, IssueStatus issueStatus);
}
