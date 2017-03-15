package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.ComplexityRank;

import java.util.List;

/**
 * Created by jiezhou on 14/03/2017.
 */
public interface ComplexityRankService {
    List<ComplexityRank> findComplexityListByDevcloudProjectId(String devcloudProjectUuid);
}
