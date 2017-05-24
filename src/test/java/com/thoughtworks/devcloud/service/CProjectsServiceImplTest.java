package com.thoughtworks.devcloud.service;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.thoughtworks.devcloud.base.SpringBaseTest;
import com.thoughtworks.devcloud.exception.NullObjectException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link CProjectsServiceImpl}.
 */
public class CProjectsServiceImplTest extends SpringBaseTest {

    @Autowired
    protected CProjectsService cProjectsService;


    @Test(expected = NullObjectException.class)
    public void getProjectsByTenantIdShouldThrowExceptionGivenNull() {
        cProjectsService.getProjectsByTenantId(tenantId);
    }

    @Test
    @DatabaseSetup("classpath:db/scan_project.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/scan_project.xml")
    public void getProjectsByTenantIdShouldReturnProjectsGivenData() {
        List<String> results = cProjectsService.getProjectsByTenantId(tenantId);
        assertThat(results, notNullValue());
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is("devCloudProjectUuid"));
    }
}
