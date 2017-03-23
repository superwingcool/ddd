package com.thoughtworks.devcloud.service;


/**
 * Project service interface.
 */
public interface CProjectsService {

    Long countDistinctByGitUrl(String devcloudProjectUuid);
}
