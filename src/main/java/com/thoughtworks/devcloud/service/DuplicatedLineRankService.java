package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.AbstractRank;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;

import java.util.List;

/**
 * Created by jiezhou on 16/03/2017.
 */
public interface DuplicatedLineRankService {
    List<DuplicatedLineRank> findMeasureListByDevcloudProjectId(String devcloudProjectUuid);
}
