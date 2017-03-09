package com.thoughtworks.devcloud.utils;

import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link CodeCheckUtils}.
 */
public class CodeCheckUtilsTest {

    private List<RuleRank> ruleRankList = constructorRuleRankList();

    @Test
    public void transform2ResponseObjectTest() {
        ResponseObject responseObject = CodeCheckUtils.transform2ResponseObject(ruleRankList, 1L);
        Assert.assertNotNull(responseObject);
        Assert.assertNotNull(responseObject.getResult());
        Assert.assertNotNull(responseObject.getResult().getTotal());
        Assert.assertEquals(String.valueOf(ruleRankList.size()), responseObject.getResult().getTotal());
    }

    @Test
    public void updateRankTest() {
        List<RuleRank> sortedRuleRankList = CodeCheckUtils.updateRank(ruleRankList);
        Assert.assertNotNull(sortedRuleRankList);

        Assert.assertEquals("rule2", sortedRuleRankList.get(0).getRuleName());
        Assert.assertEquals(1, sortedRuleRankList.get(0).getRank());
    }

    private List<RuleRank> constructorRuleRankList() {
        List<RuleRank> ruleRankList = new ArrayList<RuleRank>();

        RuleRank ruleRank1 = new RuleRank();
        ruleRank1.setRuleName("rule1");
        ruleRank1.setCounts(10L);

        RuleRank ruleRank2 = new RuleRank();
        ruleRank2.setRuleName("rule2");
        ruleRank2.setCounts(100L);

        RuleRank ruleRank3 = new RuleRank();
        ruleRank3.setRuleName("rule3");
        ruleRank3.setCounts(1L);

        ruleRankList.add(ruleRank1);
        ruleRankList.add(ruleRank2);
        ruleRankList.add(ruleRank3);
        return ruleRankList;
    }
}
