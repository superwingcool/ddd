package com.thoughtworks.devcloud;

import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.service.CIssuesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Temporal;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(CodeCheckApplication.class)
public class CodeCheckApplicationTests {

//    @Autowired
//    private CIssuesService cIssuesService;
//
//    @Test
//    public void testCIssuesService() throws Exception {
//
//        List<RuleRank> ruleRankList = cIssuesService.findViolatedCIssuesListByDevcloudProjectId("xxxxxxx", 0);
//
//    }

    @Test
    public void test() {
        Assert.assertNotNull("not null");
    }
}
