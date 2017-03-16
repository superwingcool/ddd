package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TJenkinsJobInfoService tJenkinsJobInfoService;

    @Override
    public List<ComplexityRank> findComplexityListByDevcloudProjectId(String devcloudProjectUuid) {
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevcloudProjectId(devcloudProjectUuid,
                        generateComplexityNameList());

        List<ComplexityRank> complexityRankList = transform2ComplexityRank(cProjectMeasuresList);

        Long repoCheckCount = tJenkinsJobInfoService.countDistinctByGitUrl();

        CodeCheckUtils.transform2ResponseObject(complexityRankList, repoCheckCount);
        return complexityRankList;
    }

    private List<ComplexityRank> transform2ComplexityRank(List<CProjectMeasures> cProjectMeasuresList) {
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
