package com.thoughtworks.devcloud.service;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.thoughtworks.devcloud.base.SpringBaseTest;
import com.thoughtworks.devcloud.exception.NullObjectException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class CSnapshotsServiceImplTest extends SpringBaseTest{

    @Autowired
    private CSnapshotsService cSnapshotsService;


    @Test(expected = NullObjectException.class)
    public void getProjectsByTenantIdShouldThrowExceptionGivenNull() {
        cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(getProjects());
    }

    @Test
    @DatabaseSetup("classpath:db/snapshot.xml")
    @DatabaseTearDown(type= DatabaseOperation.DELETE_ALL, value="classpath:db/snapshot.xml")
    public void getProjectsByTenantIdShouldReturnProjectsGivenData() {
        List<Long> results = cSnapshotsService.findLatestCSnapshotsIdListByGitUrl(getProjects());
        assertThat(results, notNullValue());
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(1));
    }


    private List<String> getProjects() {
        List<String> projects = new ArrayList<>();
        projects.add("devCloudProjectUuid");
        return projects;
    }

}