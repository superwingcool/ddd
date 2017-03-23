package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.AbstractRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
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
    private CProjectsService cProjectsService;

    @Autowired
    private CSnapshotsRepository cSnapshotsRepository;

    protected abstract List<String> generateMeasureNameList();

    protected abstract List<? extends AbstractRank> transform2MeasureRank(List<CProjectMeasures> cProjectMeasuresList);

    public List<? extends AbstractRank> findMeasureListByDevcloudProjectId(String devcloudProjectUuid) {
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(devcloudProjectUuid);

        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevcloudProjectId(devcloudProjectUuid,
                        generateMeasureNameList(), snapshotIdList);

        List<? extends AbstractRank> measureRankList = transform2MeasureRank(cProjectMeasuresList);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);

        CodeCheckUtils.transform2ResponseObject(measureRankList, repoCheckCount);

        return measureRankList;
    }
}
