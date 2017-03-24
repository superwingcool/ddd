package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.repository.CProjectsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


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
        Assert.assertTrue(cProjectsServiceImpl.countDistinctByGitUrl("XXXXX") == 0);
    }
}
