package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by jiezhou on 23/03/2017.
 */
public class ComplexityRankApiControllerTest {

    @Mock
    private ComplexityRankService complexityRankService;

    @Mock
    private CProjectsService cProjectsService;

    @InjectMocks
    private ComplexityRankApiController complexityRankApiController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnEmptyResponseObjectWhenNoList() {
        String devcloudProjectUuid = "XXXX";
        List<ComplexityRank> complexityRankList = new ArrayList<>();
        when(complexityRankService.findComplexityListByDevcloudProjectId(devcloudProjectUuid))
                .thenReturn(complexityRankList);

        when(cProjectsService.countDistinctByGitUrl(devcloudProjectUuid)).thenReturn(100L);

        ResponseObject responseObject = complexityRankApiController.getViolatedRules(devcloudProjectUuid);
        verify(complexityRankService, times(1)).findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        assertThat(responseObject.getError(), is(Null.NULL));
        assertThat(responseObject.getStatus(), is("success"));
    }
}
