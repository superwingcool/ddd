package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.repository.CProjectsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Unit test for {@link CProjectsServiceImpl}.
 */
public class CProjectsServiceImplTest {

    @Mock
    private CProjectsRepository cProjectsRepository;

    @InjectMocks
    private CProjectsServiceImpl cProjectsServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnLongWhenCounting() {
        Assert.assertTrue(cProjectsServiceImpl.countDistinctByGitUrl(Arrays.asList("XXXXX")) == 0);
    }

    @Test
    public void getProjectsByTenantIdShouldReturnProjectsGivenData() {

        String tenantId = "tenantId";
        List<String> devProjectIds = new ArrayList<>();
        devProjectIds.add("1");
        when(cProjectsRepository.getProjectsByTenantId(tenantId)).thenReturn(devProjectIds);
        List<String> results = cProjectsServiceImpl.getProjectsByTenantId(tenantId);
        assertThat(results, notNullValue());
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is("1"));
    }
}
