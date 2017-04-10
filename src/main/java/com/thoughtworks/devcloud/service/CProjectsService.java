package com.thoughtworks.devcloud.service;


import java.util.List;

/**
 * Project service interface.
 */
public interface CProjectsService {

    Long countDistinctByGitUrl(List<String> devCloudProjectUuid);

    List<String> getProjectsByTenantId(String tenantId);
}
