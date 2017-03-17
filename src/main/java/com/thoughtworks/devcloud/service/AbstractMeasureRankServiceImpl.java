package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.AbstractRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 *
 */
public abstract class AbstractMeasureRankServiceImpl<T> {

    @Autowired
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Autowired
    private TJenkinsJobInfoService tJenkinsJobInfoService;

    protected abstract List<String> generateMeasureNameList();

    protected abstract List<? extends AbstractRank> transform2MeasureRank(List<CProjectMeasures> cProjectMeasuresList);

    public List<? extends AbstractRank> findMeasureListByDevcloudProjectId(String devcloudProjectUuid) {
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevcloudProjectId(devcloudProjectUuid,
                        generateMeasureNameList());

        List<? extends AbstractRank> measureRankList = transform2MeasureRank(cProjectMeasuresList);
        Long repoCheckCount = tJenkinsJobInfoService.countDistinctByGitUrl();

        CodeCheckUtils.transform2ResponseObject(measureRankList, repoCheckCount);

        return measureRankList;
    }
}
