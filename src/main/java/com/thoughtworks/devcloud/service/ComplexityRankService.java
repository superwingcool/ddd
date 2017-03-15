package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.ComplexityRank;

import java.util.List;


/**
 * Complexity rank service interface.
 */
public interface ComplexityRankService {
    List<ComplexityRank> findComplexityListByDevcloudProjectId(String devcloudProjectUuid);
}
