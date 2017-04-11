package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.CodeCheckApplication;
import com.thoughtworks.devcloud.advice.CodeCheckControllerAdvice;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeCheckApplication.class)
public class TenantRankApiControllerComplexityTest {

    private MockMvc mockMvc;

    @MockBean
    private ComplexityRankService complexityRankService;

    @Autowired
    private TenantRankApiController tenantRankApiController;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(tenantRankApiController)
                .setControllerAdvice(new CodeCheckControllerAdvice()).build();
    }

    @Test
    public void getComplexityRulesShouldReturnSuccessGivenHaveData() throws Exception {
        List<ComplexityRank> ranks = new ArrayList<>();
        ComplexityRank rank = new ComplexityRank();
        rank.setProjectName("p");
        rank.setRepoName("http://localhost:8080");
        rank.setComplexity(BigDecimal.ONE);
        rank.setFileComplexity(BigDecimal.TEN);
        rank.setFunctionComplexity(BigDecimal.ZERO);
        ranks.add(rank);
        ResultObject<ComplexityRank> resultObject = new ResultObject<>();
        resultObject.setTotal("10");
        resultObject.setRepoCheckedCount("2");
        resultObject.setInfo(ranks);
        resultObject.setProjectCount("12");
        when(complexityRankService.getComplexityListByTenantId(anyString())).thenReturn(resultObject);
        this.mockMvc.perform(get("/tenants/tenantId/repos/complexity"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":{\"total\":\"10\",\"info\":" +
                        "[{\"repoName\":\"http://localhost:8080\",\"projectName\":\"p\"," +
                        "\"fileComplexity\":10,\"functionComplexity\":0,\"complexity\":1,\"rank\":1}]," +
                        "\"repoCheckedCount\":\"2\",\"projectCount\":\"12\"}}"));
    }

    @Test
    public void getComplexityRulesShouldReturnNullGivenNull() throws Exception {

        when(complexityRankService.getComplexityListByTenantId(anyString())).thenReturn(null);
        this.mockMvc.perform(get("/tenants/tenantId/repos/complexity"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\" : null}"));
    }



}