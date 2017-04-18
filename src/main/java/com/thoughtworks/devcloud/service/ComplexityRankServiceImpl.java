package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.mapper.ProjectComplexityMeasuresMapper;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
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

    @Autowired
    private ProjectComplexityMeasuresMapper projectComplexityMeasuresMapper;

    @Override
    public List<ComplexityRank> findComplexityListByDevcloudProjectId(String devCloudProjectUuid) {
        List<String> devCloudProjectIds = Arrays.asList(devCloudProjectUuid);
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(devCloudProjectIds);
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<CProjectMeasures> cProjectMeasuresList =
                cProjectMeasuresRepository.findMeasureListByDevCloudProjectId(devCloudProjectIds,
                        generateComplexityNameList(), snapshotIdList);
        List<ComplexityRank> complexityRankList = projectComplexityMeasuresMapper.transformMeasure2Rank(cProjectMeasuresList);
        //List<ComplexityRank> complexityRankList = transform2ComplexityRank(cProjectMeasuresList);
        return complexityRankList;
    }


    @Override
    public ResultObject<TenantComplexityRank> getComplexityListByTenantId(String tenantId) {

        List<String> projects = cProjectsService.getProjectsByTenantId(tenantId);
        CodeCheckUtils.getNullThrowException(projects);
        List<Long> snapshotIdList = cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects);
        CodeCheckUtils.getNullThrowException(snapshotIdList);
        List<Object[]> ranks = cProjectMeasuresRepository.getMeasureListByProjects(projects, generateComplexityNameList(),
                        snapshotIdList);
        List<TenantComplexityRank> complexityRanks = getComplexityRanks(ranks);
        Long counts = cProjectsService.countDistinctByGitUrl(projects);
        return new ResultObject<TenantComplexityRank>(String.valueOf(complexityRanks.size()),
                complexityRanks,
                String.valueOf(counts),
                String.valueOf(projects.size()));

    }

    private List<TenantComplexityRank> getComplexityRanks(List<Object[]> ranks) {
        List<TenantComplexityRank> complexityRanks = new ArrayList<>();
        if(!CollectionUtils.isEmpty(ranks)){
            for (Object[] obj: ranks) {
                TenantComplexityRank complexityRank = new TenantComplexityRank(String.valueOf(obj[0]),
                        String.valueOf(obj[1]),
                        (BigDecimal)obj[2],
                        (BigDecimal)obj[3],
                        (BigDecimal)obj[4]);
                complexityRanks.add(complexityRank);
            }
        }
        return complexityRanks;
    }

    private List<String> generateComplexityNameList() {
        return ComplexityEnum.getAllMeasureNames();
    }

}
