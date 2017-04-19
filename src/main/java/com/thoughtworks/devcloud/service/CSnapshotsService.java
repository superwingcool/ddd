package com.thoughtworks.devcloud.service;


import java.util.List;

public interface CSnapshotsService {

    List<Long> findLatestCSnapshotsIdListByGitUrl(List<String> projectIds);
}
