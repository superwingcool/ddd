package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.DuplicatedLineEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * Implementation of {@link DuplicatedLineRankService}.
 */
@Service("duplicateLineRankService")
@Transactional
public class DuplicatedLineRankServiceImpl implements DuplicatedLineRankService {

    @Autowired
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Autowired
    private CSnapshotsRepository cSnapshotsRepository;


    protected List<String> generateMeasureNameList() {
        return DuplicatedLineEnum.getAllMeasureNames();
    }

    @Override
    public List<DuplicatedLineRank> findMeasureListByDevcloudProjectId(String devCloudProjectUuid) {
        List<String> devCloudProjectIds = Arrays.asList(devCloudProjectUuid);
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(devCloudProjectIds);
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(devCloudProjectIds,
                        generateMeasureNameList(), snapshotIdList);

        List<DuplicatedLineRank> duplicatedLineRankList = transform2MeasureRank(cProjectMeasuresList);
        return duplicatedLineRankList;
    }

    protected List<DuplicatedLineRank> transform2MeasureRank(List<CProjectMeasures> cProjectMeasuresList) {
        Map<String, DuplicatedLineRank> repoDuplicatedLineRankMap = new HashMap<String, DuplicatedLineRank>();
        for (CProjectMeasures cProjectMeasures : cProjectMeasuresList) {
            String repoName = cProjectMeasures.getCSnapshots().getScmAddr();
            String metricName = cProjectMeasures.getCMetrics().getName();
            BigDecimal value = cProjectMeasures.getValue();

            if (!repoDuplicatedLineRankMap.containsKey(repoName)) {
                DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
                duplicatedLineRank.setRepoName(repoName);
                repoDuplicatedLineRankMap.put(repoName, duplicatedLineRank);
            }

            DuplicatedLineRank complexityRank = repoDuplicatedLineRankMap.get(repoName);
            updateMeasureRank(complexityRank, metricName, value);
        }
        return new ArrayList<DuplicatedLineRank>(repoDuplicatedLineRankMap.values());
    }

    private void updateMeasureRank(DuplicatedLineRank duplicatedLineRank, String metricName, BigDecimal value) {
        DuplicatedLineEnum duplicatedLineEnum = DuplicatedLineEnum.fromMeasureName(metricName);
        switch (duplicatedLineEnum) {
            case DUPLICATED_LINES:
                duplicatedLineRank.setDuplicatedLines(value);
                break;
            case DUPLICATED_LINES_DENSITY:
                duplicatedLineRank.setDuplicatedLinesDensity(value);
                break;
            case LOC:
                duplicatedLineRank.setCodeLines(value);
                break;
            default:
                // nothing to do
                break;
        }
    }
}
