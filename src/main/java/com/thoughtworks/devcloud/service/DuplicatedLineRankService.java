package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;

import java.util.List;

/**
 * Created by jiezhou on 16/03/2017.
 */
public interface DuplicatedLineRankService {

    List<DuplicatedLineRank> findMeasureListByDevcloudProjectId(String devcloudProjectUuid);

    ResultObject<TenantDuplicatedLineRank> findMeasureListByDevCloudTenantId(String tenantId);
}
