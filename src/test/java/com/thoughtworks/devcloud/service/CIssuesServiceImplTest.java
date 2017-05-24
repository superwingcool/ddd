package com.thoughtworks.devcloud.service;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.thoughtworks.devcloud.base.SpringBaseTest;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link CIssuesServiceImpl}.
 */
public class CIssuesServiceImplTest extends SpringBaseTest {

    @Autowired
    private CIssuesService cIssuesService;

    @Test(expected = NullObjectException.class)
    public void findCIssuesListByDevCloudProjectIdShouldThrowExceptionGivenNoSnapShort() {
        cIssuesService.findCIssuesListByDevcloudProjectId(devCloudProjectUuid, IssueStatus.IGNORED);
    }

    @Test
    @DatabaseSetup("classpath:db/issues.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value={"classpath:db/issues.xml"})
    public void findCIssuesListByDevCloudProjectIdShouldReturnRuleRankList() {
        List<RuleRank> ruleRanks = cIssuesService.findCIssuesListByDevcloudProjectId(devCloudProjectUuid, IssueStatus.VIOLATED);
        assertThat(ruleRanks, iterableWithSize(1));
        assertThat(ruleRanks.get(0).getRuleName(), is("ruleName"));
    }

    @Test(expected = NullObjectException.class)
    public void findCIssuesListByTenantIdShouldThrowExceptionGivenNoProjects(){
        cIssuesService.findCIssuesListByTenantId(tenantId,IssueStatus.VIOLATED);
    }

    @Test(expected = NullObjectException.class)
    public void findCIssuesListByTenantIdShouldThrowExceptionGivenNoSnapshot(){
       cIssuesService.findCIssuesListByTenantId(tenantId,IssueStatus.VIOLATED);
    }

    @Test
    @DatabaseSetup("classpath:db/issues.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value={"classpath:db/issues.xml"})
    public void findCIssuesListByTenantIdShouldReturnSuccessGivenData(){
        ResultObject r = cIssuesService.findCIssuesListByTenantId(tenantId,IssueStatus.VIOLATED);
        assertThat(r, notNullValue());
        assertThat(r.getProjectCount(), is("1"));
        assertThat(r.getInfo().get(0), instanceOf(RuleRank.class));
    }
}
