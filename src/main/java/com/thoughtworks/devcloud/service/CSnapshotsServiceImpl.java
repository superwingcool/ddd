package com.thoughtworks.devcloud.service;


import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSnapshotsServiceImpl implements CSnapshotsService {

    @Autowired
    private CSnapshotsRepository cSnapshotsRepository;

    @Override
    public List<Long> findLatestCSnapshotsIdListByGitUrl(List<String> projectIds) {
        List<Long> snapshotIds = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projectIds);
        CodeCheckUtils.getNullThrowException(snapshotIds);
        return snapshotIds;
    }
}
