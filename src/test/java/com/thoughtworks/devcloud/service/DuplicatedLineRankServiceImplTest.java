package com.thoughtworks.devcloud.service;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.thoughtworks.devcloud.base.SpringBaseTest;
import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link DuplicatedLineRankServiceImpl}.
 */
public class DuplicatedLineRankServiceImplTest extends SpringBaseTest {

    @Autowired
    private DuplicatedLineRankService duplicatedLineRankService;

    @Test(expected = NullObjectException.class)
    public void findMeasureListByDevCloudProjectIdShouldThrowExceptionGivenNull() {
        duplicatedLineRankService.findMeasureListByDevcloudProjectId(devCloudProjectUuid);

    }

    @Test
    @DatabaseSetup("classpath:db/duplicated_line.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/duplicated_line.xml")
    public void findMeasureListByDevCloudProjectIdShouldMeasuresGivenData() {
        List<DuplicatedLineRank> ranks = duplicatedLineRankService.findMeasureListByDevcloudProjectId(devCloudProjectUuid);
        assertThat(ranks, notNullValue());
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getDuplicatedLinesDensity(), is("10.00%"));
        assertThat(ranks.get(0).getRepoName(), is("https://git.org"));
    }

    @Test(expected = NullObjectException.class)
    @DatabaseSetup("classpath:db/duplicated_line.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/duplicated_line.xml")
    public void findMeasureListByDevCloudTenantIdShouldThrowExceptionGivenNullProject() {
        duplicatedLineRankService.findMeasureListByDevCloudTenantId("tenantId2");
    }

    @Test(expected = NullObjectException.class)
    @DatabaseSetup("classpath:db/duplicated_line.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/duplicated_line.xml")
    public void findMeasureListByDevCloudTenantIdShouldThrowExceptionGivenNullSnapshot() {
        duplicatedLineRankService.findMeasureListByDevCloudTenantId("tenantId3");
    }

    @Test
    @DatabaseSetup("classpath:db/duplicated_line.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/duplicated_line.xml")
    public void findMeasureListByDevCloudTenantIdShouldReturnSuccessGivenRightData() {
        ResultObject<TenantDuplicatedLineRank> ranks = duplicatedLineRankService.findMeasureListByDevCloudTenantId(tenantId);
        assertThat(ranks, notNullValue());
        assertThat(ranks.getRepoCheckedCount(), is("1"));
        assertThat(ranks.getTotal(), is("1"));
        assertThat(ranks.getProjectCount(), is("1"));
        assertThat(ranks.getInfo().get(0).getDuplicatedLinesDensity(), is("10.00%"));
        assertThat(ranks.getInfo().get(0).getRepoName(), is("https://git.org"));
        assertThat(ranks.getInfo().get(0).getProjectName(), is("devCloudProjectUuid"));
    }
}
