package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.mapper.ProjectComplexityMeasuresMapper;
import com.thoughtworks.devcloud.mapper.TenantComplexityMeasuresMapper;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * Implementation of {@link ComplexityRankService}.
 */
@Service("complexityRankService")
@Transactional
public class ComplexityRankServiceImpl implements ComplexityRankService {

    @Autowired
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Autowired
    private CSnapshotsService cSnapshotsService;

    @Autowired
    private CProjectsService cProjectsService;

    @Autowired
    private ProjectComplexityMeasuresMapper projectComplexityMeasuresMapper;

    @Autowired
    private TenantComplexityMeasuresMapper tenantComplexityMeasuresMapper;

    @Override
    public List<ComplexityRank> findComplexityListByDevcloudProjectId(String devCloudProjectUuid) {
        List<String> devCloudProjectIds = Arrays.asList(devCloudProjectUuid);
        List<Long> snapshotIdList = cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(devCloudProjectIds);
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(devCloudProjectIds,
                        generateComplexityNameList(), snapshotIdList);
        List<ComplexityRank> complexityRankList = projectComplexityMeasuresMapper.transformMeasure2Rank(cProjectMeasuresList);
        return complexityRankList;
    }

    @Override
    public ResultObject<TenantComplexityRank> getComplexityListByTenantId(String tenantId) {

        List<String> projects = cProjectsService.getProjectsByTenantId(tenantId);
        List<Long> snapshotIdList = cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(projects);
        List<CProjectMeasures> cProjectMeasuresList = cProjectMeasuresRepository
                .findMeasureListByDevCloudProjectId(projects, generateComplexityNameList(), snapshotIdList);
        List<TenantComplexityRank> complexityRanks = tenantComplexityMeasuresMapper
                .transformMeasure2Rank(cProjectMeasuresList);
        Long counts = cProjectsService.countDistinctByGitUrl(projects);
        return new ResultObject<TenantComplexityRank>(String.valueOf(complexityRanks.size()),
                complexityRanks,
                String.valueOf(counts),
                String.valueOf(projects.size()));

    }

    private List<String> generateComplexityNameList() {
        return ComplexityEnum.getAllMeasureNames();
    }

}
