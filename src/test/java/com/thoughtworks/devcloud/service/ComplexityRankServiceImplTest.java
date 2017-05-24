package com.thoughtworks.devcloud.service;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.thoughtworks.devcloud.base.SpringBaseTest;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link ComplexityRankServiceImpl}.
 */
public class ComplexityRankServiceImplTest extends SpringBaseTest {

    @Autowired
    private ComplexityRankService complexityRankService;


    @Test(expected = NullObjectException.class)
    public void findComplexityListByDevCloudProjectIdShouldThrowExceptionGivenNoProjects() {
        complexityRankService.findComplexityListByDevcloudProjectId(devCloudProjectUuid);
    }

    @Test
    @DatabaseSetup("classpath:db/complexity.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/complexity.xml")
    public void shouldReturnComplexityRankList() {
        List<ComplexityRank> complexityRankList = complexityRankService.findComplexityListByDevcloudProjectId(devCloudProjectUuid);
        assertThat(complexityRankList, notNullValue());
        assertThat(complexityRankList.size(), is(1));
        assertThat(complexityRankList.get(0).getComplexity().compareTo(BigDecimal.valueOf(18.00)), is(0));
    }

    @Test(expected = NullObjectException.class)
    public void getComplexityListByTenantIdShouldThrowExceptionGivenNoProjects(){
        complexityRankService.getComplexityListByTenantId("tenantId1");
    }

    @Test(expected = NullObjectException.class)
    @DatabaseSetup("classpath:db/complexity.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/complexity.xml")
    public void getComplexityListByTenantIdShouldThrowExceptionGivenNoSnapShort(){
        complexityRankService.getComplexityListByTenantId("tenantId2");
    }

    @Test
    @DatabaseSetup("classpath:db/complexity.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/complexity.xml")
    public void getComplexityListByTenantIdShouldReturnSuccessGivenRightRanks(){
        ResultObject<TenantComplexityRank> result = complexityRankService.getComplexityListByTenantId("tenantId");
        assertThat(result, notNullValue());
        assertThat(result.getRepoCheckedCount(), is("1"));
        assertThat(result.getTotal(), is("1"));
        assertThat(result.getInfo().get(0).getProjectName(), is("devCloudProjectUuid"));
        assertThat(result.getInfo().get(0).getRepoName(), is("https://git.org"));
        assertThat(result.getInfo().get(0).getComplexity().compareTo(BigDecimal.valueOf(18.00)), is(0));
    }



}
