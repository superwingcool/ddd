package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.repository.TJenkinsJobInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link TJenkinsJobInfoService}.
 */
@Service("tJenkinsJobInfoService")
@Transactional
public class TJenkinsJobInfoServiceImpl implements TJenkinsJobInfoService {

    @Autowired
    private TJenkinsJobInfoRepository tJenkinsJobInfoRepository;

    @Override
    public Long countDistinctByGitUrl() {
        return tJenkinsJobInfoRepository.countDistinctByGitUrl();
    }
}
