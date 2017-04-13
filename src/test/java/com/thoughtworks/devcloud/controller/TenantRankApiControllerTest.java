package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.CodeCheckApplication;
import com.thoughtworks.devcloud.advice.CodeCheckControllerAdvice;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import com.thoughtworks.devcloud.service.CIssuesService;
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
public class TenantRankApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ComplexityRankService complexityRankService;

    @MockBean
    private CIssuesService cIssuesService;

    @Autowired
    private TenantRankApiController tenantRankApiController;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(tenantRankApiController)
                .setControllerAdvice(new CodeCheckControllerAdvice()).build();
    }

    @Test
    public void getViolatedRulesShouldReturnSuccessWhenHaveRank() throws Exception {

        mockService(IssueStatus.VIOLATED);
        this.mockMvc.perform(get("/tenants/tenantId/rules/violated"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":{\"total\":\"10\",\"info\":" +
                        "[{\"rank\":1,\"ruleName\":\"rule_name\",\"priority\":3,\"category\":\"c\"," +
                        "\"language\":\"JAVA\",\"tag\":\"tag\",\"counts\":12}],\"repoCheckedCount\":\"2\"," +
                        "\"projectCount\":\"12\"}}"));

    }

    @Test
    public void getViolatedRulesShouldReturnNullGivenNull() throws Exception {

        when(cIssuesService.findCIssuesListByTenantId("tenantId",IssueStatus.VIOLATED)).thenReturn(null);
        this.mockMvc.perform(get("/tenants/tenantId/rules/violated"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\" : null}"));
    }

    @Test
    public void getIgnoredRulesShouldReturnSuccessWhenHaveRank() throws Exception {
        mockService(IssueStatus.IGNORED);
        this.mockMvc.perform(get("/tenants/tenantId/rules/ignored"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":{\"total\":\"10\",\"info\":" +
                        "[{\"rank\":1,\"ruleName\":\"rule_name\",\"priority\":3,\"category\":\"c\"," +
                        "\"language\":\"JAVA\",\"tag\":\"tag\",\"counts\":12}],\"repoCheckedCount\":\"2\"," +
                        "\"projectCount\":\"12\"}}"));
    }

    @Test
    public void getIgnoredRulesShouldReturnNullGivenNull() throws Exception {

        when(cIssuesService.findCIssuesListByTenantId("tenantId",IssueStatus.IGNORED)).thenReturn(null);
        this.mockMvc.perform(get("/tenants/tenantId/rules/violated"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\" : null}"));
    }

    @Test
    public void getRevisedRulesShouldReturnSuccessWhenHaveRanks() throws Exception {
        mockService(IssueStatus.REVISED);
        this.mockMvc.perform(get("/tenants/tenantId/rules/revised"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":{\"total\":\"10\",\"info\":" +
                        "[{\"rank\":1,\"ruleName\":\"rule_name\",\"priority\":3,\"category\":\"c\"," +
                        "\"language\":\"JAVA\",\"tag\":\"tag\",\"counts\":12}],\"repoCheckedCount\":\"2\"," +
                        "\"projectCount\":\"12\"}}"));
    }

    @Test
    public void getRevisedRulesShouldReturnNullGivenNull() throws Exception {

        when(cIssuesService.findCIssuesListByTenantId("tenantId",IssueStatus.REVISED)).thenReturn(null);
        this.mockMvc.perform(get("/tenants/tenantId/rules/violated"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\" : null}"));
    }

    @Test
    public void getComplexityRulesShouldReturnSuccessGivenHaveData() throws Exception {
        List<TenantComplexityRank> ranks = new ArrayList<>();
        TenantComplexityRank rank = new TenantComplexityRank();
        rank.setProjectName("p");
        rank.setRepoName("http://localhost:8080");
        rank.setComplexity(BigDecimal.ONE);
        rank.setFileComplexity(BigDecimal.TEN);
        rank.setFunctionComplexity(BigDecimal.ZERO);
        ranks.add(rank);
        ResultObject<TenantComplexityRank> resultObject = new ResultObject<>();
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

    private void mockService(IssueStatus issueStatus) {
        String tenantId = "tenantId";
        List<RuleRank> ranks = new ArrayList<>();
        RuleRank rank = new RuleRank();
        rank.setCategory("c");
        rank.setCounts(12l);
        rank.setLanguage("JAVA");
        rank.setPriority(3);
        rank.setRuleName("rule_name");
        rank.setTag("tag");
        ranks.add(rank);
        ResultObject<RuleRank> resultObject = new ResultObject<>();
        resultObject.setTotal("10");
        resultObject.setRepoCheckedCount("2");
        resultObject.setInfo(ranks);
        resultObject.setProjectCount("12");
        when(cIssuesService.findCIssuesListByTenantId(tenantId, issueStatus)).thenReturn(resultObject);
    }

}