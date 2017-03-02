package com.thoughtworks.spike;

import com.thoughtworks.devcloud.CodeCheckApplication;
import com.thoughtworks.devcloud.service.ProjectService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CodeCheckApplication.class)
public class CodeCheckApplicationTests {

    @Autowired
    private ProjectService projectService;

    @Test
    public void test() throws Exception {
        Assert.assertTrue(projectService.findAllProjects().size() > 0);

        String name = "fanfantest_New_CI01_0311";
        Assert.assertEquals("fanfantest_New_CI01_0311", projectService.findByName(name).getName());
    }


}
