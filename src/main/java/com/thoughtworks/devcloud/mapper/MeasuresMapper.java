package com.thoughtworks.devcloud.mapper;


import com.thoughtworks.devcloud.constants.DuplicatedLineEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.List;

public abstract class MeasuresMapper<T> {

    abstract List<T> transformMeasure2Rank(List<CProjectMeasures> cProjectMeasures);

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

    protected String getProjectName(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getCProjects().getProjectName();
    }

    protected String getMetricName(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getCMetrics().getName();
    }

    protected String getRepName(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getCSnapshots().getScmAddr();
    }

    protected BigDecimal getValue(CProjectMeasures cProjectMeasure) {
        return cProjectMeasure.getValue();
    }
}
