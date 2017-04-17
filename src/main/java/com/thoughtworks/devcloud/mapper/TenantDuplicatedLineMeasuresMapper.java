package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDuplicatedLineMeasuresMapper extends MeasuresMapper<TenantDuplicatedLineRank> {

    @Override
    public List<TenantDuplicatedLineRank> transformMeasure2Rank(List<CProjectMeasures> cProjectMeasures) {
        Map<String, TenantDuplicatedLineRank> rankMaps = new HashMap<>();
        for (CProjectMeasures cProjectMeasure : cProjectMeasures) {
            String repoName = getRepName(cProjectMeasure);
            String metricName = getMetricName(cProjectMeasure);
            String projectName = getProjectName(cProjectMeasure);
            BigDecimal value = getValue(cProjectMeasure);
            String key = repoName.concat(projectName);
            if (!rankMaps.containsKey(key)) {
                TenantDuplicatedLineRank duplicatedLineRank = new TenantDuplicatedLineRank();
                duplicatedLineRank.setRepoName(repoName);
                duplicatedLineRank.setProjectName(projectName);
                rankMaps.put(key, duplicatedLineRank);
            }
            TenantDuplicatedLineRank duplicatedLineRank = rankMaps.get(key);
            setDuplicatedLineValues(duplicatedLineRank, metricName, value);
        }
        return new ArrayList<TenantDuplicatedLineRank>(rankMaps.values());
    }
}
