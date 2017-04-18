package com.thoughtworks.devcloud.mapper;


import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.constants.DuplicatedLineEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MeasuresMapper<T> {

    protected abstract T getRank(Map<String, T> rankMaps, CProjectMeasures cProjectMeasure);

    protected abstract void bindRankValues(CProjectMeasures cProjectMeasure, T rank);

    public List<T> transformMeasure2Rank(List<CProjectMeasures> cProjectMeasures) {
        Map<String, T> rankMaps = new HashMap<>();
        fillRankMap(cProjectMeasures, rankMaps);
        return new ArrayList<T>(rankMaps.values());
    }

    protected void fillRankMap(List<CProjectMeasures> cProjectMeasures, Map<String, T> rankMaps) {
        for (CProjectMeasures cProjectMeasure : cProjectMeasures) {
            fillEachRank(rankMaps, cProjectMeasure);
        }
    }

    protected void fillEachRank(Map<String, T> rankMaps, CProjectMeasures cProjectMeasure) {
        T rank = getRank(rankMaps, cProjectMeasure);
        bindRankValues(cProjectMeasure, rank);
    }


    protected void setDuplicatedLineValues(DuplicatedLineRank duplicatedLineRank, String metricName,
                                           BigDecimal value) {
        DuplicatedLineEnum duplicatedLineEnum = DuplicatedLineEnum.fromMeasureName(metricName);
        switch (duplicatedLineEnum) {
            case DUPLICATED_LINES:
                duplicatedLineRank.setDuplicatedLines(value);
                break;
            case DUPLICATED_LINES_DENSITY:
                duplicatedLineRank.setDuplicatedLinesDensityCompare(value);
                duplicatedLineRank.setDuplicatedLinesDensity(NumberUtils.getPercentFromBigDecimal(value));
                break;
            case LOC:
                duplicatedLineRank.setCodeLines(value);
                break;
            default:
                // nothing to do
                break;
        }
    }

    protected void setComplexityRankValues(ComplexityRank complexityRank,
                              String metricName,
                              BigDecimal value) {
        ComplexityEnum complexityEnum = ComplexityEnum.fromMeasureName(metricName);
        switch (complexityEnum) {
            case COMPLEXITY:
                complexityRank.setComplexity(value);
                break;
            case FILE_COMPLEXITY:
                complexityRank.setFileComplexity(value);
                break;
            case FUNCTION_COMPLEXITY:
                complexityRank.setFunctionComplexity(value);
                break;
            default:
                // nothing to do
                break;
        }
    }

    protected String getKey(CProjectMeasures cProjectMeasure, String projectName) {
        return getRepoName(cProjectMeasure).concat(projectName);
    }

    protected String getProjectName(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getCProjects().getProjectName();
    }

    protected String getMetricName(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getCMetrics().getName();
    }

    protected String getRepoName(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getCSnapshots().getScmAddr();
    }

    protected BigDecimal getValue(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getValue();
    }
}
