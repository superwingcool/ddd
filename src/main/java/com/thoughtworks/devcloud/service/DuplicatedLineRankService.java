package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.DuplicatedLineRank;

import java.util.List;

/**
 * Created by jiezhou on 16/03/2017.
 */
public interface DuplicatedLineRankService {
    List<DuplicatedLineRank> findDuplicatedListByDevcloudProjectId(String devcloudProjectUuid);
}
