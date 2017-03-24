package com.thoughtworks.devcloud;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
public class CodeCheckApplicationTest {

    @InjectMocks
    private CodeCheckApplication codeCheckApplication;

    @Test
    public void test() {
        Assert.assertNotNull("not null");
    }
}
