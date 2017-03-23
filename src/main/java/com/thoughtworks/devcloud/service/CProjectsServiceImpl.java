package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.repository.CProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of {@link CProjectsService}.
 */
@Service("cProjectsService")
@Transactional
public class CProjectsServiceImpl implements CProjectsService {

    @Autowired
    private CProjectsRepository cProjectsRepository;

    @Override
    public Long countDistinctByGitUrl(String devcloudProjectUuid) {
        return cProjectsRepository.countDistinctByGitUrl(devcloudProjectUuid);
    }
}