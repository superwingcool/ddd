package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.constants.ComplexityEnum;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Unit test for {@link ComplexityRankServiceImpl}.
 */
public class ComplexityRankServiceImplTest {

    @Mock
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @InjectMocks
    private ComplexityRankServiceImpl complexityRankServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnComplexityRankList() {
        String devcloudProjectUuid = "XXXX";
        List<Long> snapshotIdList = new ArrayList<Long>();
        List<CProjectMeasures> cProjectMeasuresList = new ArrayList<>();

        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(Arrays.asList(devcloudProjectUuid))).thenReturn(snapshotIdList);
        when(cProjectMeasuresRepository.findMeasureListByDevcloudProjectId(devcloudProjectUuid,
                ComplexityEnum.getAllMeasureNames(), snapshotIdList)).thenReturn(cProjectMeasuresList);

        List<ComplexityRank> complexityRankList =
                complexityRankServiceImpl.findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        Assert.assertTrue(complexityRankList.isEmpty());

    }

    @Test
    public void shouldReturnSame() {
        List<CProjectMeasures> cProjectMeasuresList = new ArrayList<>();
        List<ComplexityRank> complexityRankList =
                complexityRankServiceImpl.transform2ComplexityRank(cProjectMeasuresList);

        Assert.assertTrue(complexityRankList.isEmpty());
    }

}
