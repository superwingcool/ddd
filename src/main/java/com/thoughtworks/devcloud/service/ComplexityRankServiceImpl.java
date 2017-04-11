package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;


/**
 * Implementation of {@link ComplexityRankService}.
 */
@Service("complexityRankService")
@Transactional
public class ComplexityRankServiceImpl implements ComplexityRankService {

    @Autowired
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Autowired
    private CSnapshotsRepository cSnapshotsRepository;

    @Autowired
    private CProjectsService cProjectsService;

    @Override
    public List<ComplexityRank> findComplexityListByDevcloudProjectId(String devcloudProjectUuid) {
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devcloudProjectUuid));
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevcloudProjectId(devcloudProjectUuid,
                        generateComplexityNameList(), snapshotIdList);
        List<ComplexityRank> complexityRankList = transform2ComplexityRank(cProjectMeasuresList);
        return complexityRankList;
    }


    @Override
    public ResultObject<ComplexityRank> getComplexityListByTenantId(String tenantId) {

        List<String> projects = cProjectsService.getProjectsByTenantId(tenantId);
        CodeCheckUtils.getNullThrowException(projects);
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects);
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<Object[]> ranks = cProjectMeasuresRepository.getMeasureListByProjects(projects, generateComplexityNameList(),
                        snapshotIdList);
        List<ComplexityRank> complexityRanks = getComplexityRanks(ranks);
        Long counts = cProjectsService.countDistinctByGitUrl(projects);
        return new ResultObject<ComplexityRank>(String.valueOf(complexityRanks.size()),
                complexityRanks,
                String.valueOf(counts),
                String.valueOf(complexityRanks.size()));

    }

    private List<ComplexityRank> getComplexityRanks(List<Object[]> ranks) {
        List<ComplexityRank> complexityRanks = new ArrayList<>();
        if(!CollectionUtils.isEmpty(ranks)){
            for (Object[] obj: ranks) {
                ComplexityRank complexityRank = new ComplexityRank(String.valueOf(obj[0]),
                        String.valueOf(obj[1]),
                        (BigDecimal)obj[2],
                        (BigDecimal)obj[3],
                        (BigDecimal)obj[4]);
                complexityRanks.add(complexityRank);
            }
        }
        return complexityRanks;
    }

    public List<ComplexityRank> transform2ComplexityRank(List<CProjectMeasures> cProjectMeasuresList) {
        Map<String, ComplexityRank> repoComplexityMap = new HashMap<String, ComplexityRank>();
        for (CProjectMeasures cProjectMeasures : cProjectMeasuresList) {
            String repoName = cProjectMeasures.getCSnapshots().getScmAddr();
            String metricName = cProjectMeasures.getCMetrics().getName();
            BigDecimal value = cProjectMeasures.getValue();

            if (!repoComplexityMap.containsKey(repoName)) {
                ComplexityRank complexityRank = new ComplexityRank();
                complexityRank.setRepoName(repoName);
                repoComplexityMap.put(repoName, complexityRank);
            }

            ComplexityRank complexityRank = repoComplexityMap.get(repoName);
            updateComplexityRank(complexityRank, metricName, value);
        }
        return new ArrayList<ComplexityRank>(repoComplexityMap.values());
    }

    private void updateComplexityRank(ComplexityRank complexityRank,
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

    private List<String> generateComplexityNameList() {
        return ComplexityEnum.getAllMeasureNames();
    }

}
