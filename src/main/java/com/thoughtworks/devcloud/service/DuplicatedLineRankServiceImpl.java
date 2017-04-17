package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.DuplicatedLineEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.mapper.ProjectDuplicatedLineMeasuresMapper;
import com.thoughtworks.devcloud.mapper.TenantDuplicatedLineMeasuresMapper;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * Implementation of {@link DuplicatedLineRankService}.
 */
@Service("duplicateLineRankService")
public class DuplicatedLineRankServiceImpl implements DuplicatedLineRankService {

    @Autowired
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Autowired
    private CSnapshotsRepository cSnapshotsRepository;

    @Autowired
    private CProjectsService cProjectsService;

    @Autowired
    private ProjectDuplicatedLineMeasuresMapper projectDuplicatedLineMeasuresMapper;

    @Autowired
    private TenantDuplicatedLineMeasuresMapper tenantDuplicatedLineMeasuresMapper;


    @Override
    public List<DuplicatedLineRank> findMeasureListByDevcloudProjectId(String devCloudProjectUuid) {
        List<String> devCloudProjectIds = Arrays.asList(devCloudProjectUuid);
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(devCloudProjectIds);
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<CProjectMeasures> cProjectMeasures =
                cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(devCloudProjectIds,
                        generateMeasureNameList(), snapshotIdList);
        List<DuplicatedLineRank> duplicatedLineRanks = projectDuplicatedLineMeasuresMapper
                .transformMeasure2Rank(cProjectMeasures);
        return duplicatedLineRanks;
    }

    @Override
    public ResultObject<TenantDuplicatedLineRank> findMeasureListByDevCloudTenantId(String tenantId) {

        List<String> projects = cProjectsService.getProjectsByTenantId(tenantId);
        CodeCheckUtils.getNullThrowException(projects);
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects);
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(projects,
                        generateMeasureNameList(), snapshotIdList);
        List<TenantDuplicatedLineRank> duplicatedLineRanks = tenantDuplicatedLineMeasuresMapper.
                transformMeasure2Rank(cProjectMeasuresList);
        Long counts = cProjectsService.countDistinctByGitUrl(projects);
        return new ResultObject<TenantDuplicatedLineRank>(String.valueOf(duplicatedLineRanks.size()),
                duplicatedLineRanks,
                String.valueOf(counts),
                String.valueOf(projects.size()));
    }

    protected List<String> generateMeasureNameList() {
        return DuplicatedLineEnum.getAllMeasureNames();
    }

}
