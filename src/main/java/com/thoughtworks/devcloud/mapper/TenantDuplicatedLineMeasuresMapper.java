package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class TenantDuplicatedLineMeasuresMapper extends MeasuresMapper<TenantDuplicatedLineRank> {

    @Override
    protected TenantDuplicatedLineRank getRank(Map<String, TenantDuplicatedLineRank> rankMaps, CProjectMeasures cProjectMeasure) {
        String key = getKey(cProjectMeasure, getProjectName(cProjectMeasure));
        if (!rankMaps.containsKey(key)) {
            TenantDuplicatedLineRank duplicatedLineRank = new TenantDuplicatedLineRank();
            rankMaps.put(key, duplicatedLineRank);
        }
        return rankMaps.get(key);
    }

    @Override
    protected void bindValues(CProjectMeasures cProjectMeasure, TenantDuplicatedLineRank rank) {
        String metricName = getMetricName(cProjectMeasure);
        BigDecimal value = getValue(cProjectMeasure);
        rank.setRepoName(getRepoName(cProjectMeasure));
        rank.setProjectName(getProjectName(cProjectMeasure));
        setDuplicatedLineValues(rank, metricName, value);
    }
}
