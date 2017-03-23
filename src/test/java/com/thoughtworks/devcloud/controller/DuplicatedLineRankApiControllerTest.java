package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import com.thoughtworks.devcloud.service.DuplicatedLineRankService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by jiezhou on 23/03/2017.
 */
public class DuplicatedLineRankApiControllerTest {

    @Mock
    private DuplicatedLineRankService duplicatedLineRankService;

    @Mock
    private CProjectsService cProjectsService;

    @InjectMocks
    private DuplicatedLineRankApiController duplicatedLineRankApiController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        List<DuplicatedLineRank> duplicatedLineRanks = new ArrayList<>();
        when(duplicatedLineRankService.findMeasureListByDevcloudProjectId(devcloudProjectUuid))
                .thenReturn(duplicatedLineRanks);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(10L);

        ResponseObject responseObject = duplicatedLineRankApiController.getViolatedRules(devcloudProjectUuid);
        verify(duplicatedLineRankService, times(1))
                .findMeasureListByDevcloudProjectId(devcloudProjectUuid);
        assertThat(responseObject.getError(), is(Null.NULL));
        assertThat(responseObject.getStatus(), is("success"));
    }
}
