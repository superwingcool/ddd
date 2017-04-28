package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProjectComplexityMeasuresMapper extends MeasuresMapper<ComplexityRank> {

    @Override
    protected ComplexityRank getRank(Map<String, ComplexityRank> rankMaps, CProjectMeasures cProjectMeasure) {
        String key = getRepoName(cProjectMeasure);
        if (!rankMaps.containsKey(key)) {
            ComplexityRank complexity = new ComplexityRank();
            rankMaps.put(key, complexity);
        }
        return rankMaps.get(key);
    }

    @Override
    protected void bindRankValues(CProjectMeasures cProjectMeasure, ComplexityRank rank) {
        String metricName = getMetricName(cProjectMeasure);
        BigDecimal value = getValue(cProjectMeasure);
        rank.setRepoName(getRepoName(cProjectMeasure));
        rank.setTaskName(getProjectName(cProjectMeasure));
        rank.setTaskDetailUrl(generateTaskDetailUrl(cProjectMeasure));
        setComplexityRankValues(rank, metricName, value);
    }
}
