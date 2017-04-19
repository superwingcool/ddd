package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class TenantComplexityMeasuresMapper extends MeasuresMapper<TenantComplexityRank> {

    @Override
    protected TenantComplexityRank getRank(Map<String, TenantComplexityRank> rankMaps, CProjectMeasures cProjectMeasure) {
        String key = getRepoName(cProjectMeasure);
        if (!rankMaps.containsKey(key)) {
            TenantComplexityRank complexity = new TenantComplexityRank();
            rankMaps.put(key, complexity);
        }
        return rankMaps.get(key);
    }

    @Override
    protected void bindRankValues(CProjectMeasures cProjectMeasure, TenantComplexityRank rank) {
        rank.setRepoName(getRepoName(cProjectMeasure));
        rank.setProjectName(getProjectName(cProjectMeasure));
        setComplexityRankValues(rank, getMetricName(cProjectMeasure), getValue(cProjectMeasure));
    }
}