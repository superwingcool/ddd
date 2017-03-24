package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.repository.CProjectMeasuresRepository;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Unit test for {@link DuplicatedLineRankServiceImpl}.
 */
public class DuplicatedLineRankServiceImplTest {

    @Mock
    private CProjectMeasuresRepository cProjectMeasuresRepository;

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @InjectMocks
    private DuplicatedLineRankServiceImpl duplicatedLineRankServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnMeasueList() {
        String devcloudProjectUuid = "XXXX";
        List<DuplicatedLineRank> resultList =
                duplicatedLineRankServiceImpl.findMeasureListByDevcloudProjectId(devcloudProjectUuid);

        Assert.assertTrue(resultList.isEmpty());

    }

}
