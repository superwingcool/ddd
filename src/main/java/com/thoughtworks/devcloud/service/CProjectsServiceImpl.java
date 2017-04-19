package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.repository.CProjectsRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Implementation of {@link CProjectsService}.
 */
@Service("cProjectsService")
@Transactional
public class CProjectsServiceImpl implements CProjectsService {

    @Autowired
    private CProjectsRepository cProjectsRepository;

    @Override
    public Long countDistinctByGitUrl(List<String> devCloudProjectUuid) {
        return cProjectsRepository.countDistinctByGitUrl(devCloudProjectUuid);
    }

    @Override
    public List<String> getProjectsByTenantId(String tenantId){
        List<String> projects = cProjectsRepository.getProjectsByTenantId(tenantId);
        CodeCheckUtils.getNullThrowException(projects);
       return projects;
    }


}
