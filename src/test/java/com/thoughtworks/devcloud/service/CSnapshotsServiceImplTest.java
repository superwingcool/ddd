package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.repository.CSnapshotsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class CSnapshotsServiceImplTest {

    @Mock
    private CSnapshotsRepository cSnapshotsRepository;

    @InjectMocks
    private CSnapshotsServiceImpl cSnapshotsServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullObjectException.class)
    public void getProjectsByTenantIdShouldThrowExceptionGivenNull() {
        List<String> projects = getProjects();
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(new ArrayList<>());
        cSnapshotsServiceImpl.findLatestCSnapshotsIdListByGitUrl(projects);
    }

    @Test
    public void getProjectsByTenantIdShouldReturnProjectsGivenData() {
        List<String> projects = getProjects();
        List<Long> snapshots = new ArrayList<>();
        snapshots.add(1l);
        when(cSnapshotsRepository.findLatestCSnapshotsIdListByGitUrl(projects)).thenReturn(snapshots);
        List<Long> results = cSnapshotsServiceImpl.findLatestCSnapshotsIdListByGitUrl(projects);
        assertThat(results, notNullValue());
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(1l));
    }


    private List<String> getProjects() {
        List<String> projects = new ArrayList<>();
        projects.add("project_id");
        return projects;
    }

}