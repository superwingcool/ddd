package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProjectDuplicatedLineMeasuresMapper extends MeasuresMapper<DuplicatedLineRank> {
    @Override
    public List<DuplicatedLineRank> transformMeasure2Rank(List<CProjectMeasures> cProjectMeasures) {
        Map<String, DuplicatedLineRank> rankMaps = new HashMap<>();
        for (CProjectMeasures cProjectMeasure : cProjectMeasures) {
            String repoName = getRepName(cProjectMeasure);
            String metricName = getMetricName(cProjectMeasure);
            BigDecimal value = getValue(cProjectMeasure);
            if (!rankMaps.containsKey(repoName)) {
                DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
                duplicatedLineRank.setRepoName(repoName);
                rankMaps.put(repoName, duplicatedLineRank);
            }
            DuplicatedLineRank duplicatedLineRank = rankMaps.get(repoName);
            setDuplicatedLineValues(duplicatedLineRank, metricName, value);
        }
        return new ArrayList<DuplicatedLineRank>(rankMaps.values());
    }
}
