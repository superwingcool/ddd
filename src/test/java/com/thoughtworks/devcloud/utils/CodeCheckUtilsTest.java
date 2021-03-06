package com.thoughtworks.devcloud.utils;

import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link CodeCheckUtils}.
 */
public class CodeCheckUtilsTest {

    private List<RuleRank> ruleRankList = constructorRuleRankList();

    @InjectMocks
    private CodeCheckUtils codeCheckUtils;

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
        CodeCheckUtils.updateRank(ruleRankList);
        Assert.assertNotNull(ruleRankList);

        Assert.assertEquals("rule2", ruleRankList.get(0).getRuleName());
        Assert.assertEquals(1, ruleRankList.get(0).getRank());
    }

    @Test(expected = NullObjectException.class)
    public void getNullThrowExceptionShouldThrowExceptoinGivenBlank() {
        CodeCheckUtils.getNullThrowException(new ArrayList<>());
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
