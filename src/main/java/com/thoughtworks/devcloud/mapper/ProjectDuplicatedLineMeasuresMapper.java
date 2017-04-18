package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProjectDuplicatedLineMeasuresMapper extends MeasuresMapper<DuplicatedLineRank> {

    @Override
    protected DuplicatedLineRank getRank(Map<String, DuplicatedLineRank> rankMaps, CProjectMeasures cProjectMeasure) {
        String key = getRepoName(cProjectMeasure);
        if (!rankMaps.containsKey(key)) {
            TenantDuplicatedLineRank duplicatedLineRank = new TenantDuplicatedLineRank();
            rankMaps.put(key, duplicatedLineRank);
        }
        return rankMaps.get(key);
    }

    @Override
    protected void bindValues(CProjectMeasures cProjectMeasure, DuplicatedLineRank rank) {
        String metricName = getMetricName(cProjectMeasure);
        BigDecimal value = getValue(cProjectMeasure);
        rank.setRepoName(getRepoName(cProjectMeasure));
        setDuplicatedLineValues(rank, metricName, value);
    }
}
