package com.thoughtworks.devcloud.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Unit test for {@link ComplexityRank}.
 */
public class ComplexityRankTest {

    @Test
    public void shouldReturnTrueListWhenComparing() {
        ComplexityRank complexityRank = new ComplexityRank();
        complexityRank.setComplexity(new BigDecimal(100));
        complexityRank.setRepoName("http://git.com");

        ComplexityRank complexityRank2 = new ComplexityRank();
        complexityRank2.setComplexity(new BigDecimal(111));
        complexityRank2.setRepoName("http://git2.com");

        int result = complexityRank.compareTo(complexityRank2);
        Assert.assertTrue(result < 0);

        ComplexityRank complexityRank3 = new ComplexityRank();
        complexityRank3.setComplexity(new BigDecimal(100));
        complexityRank3.setRepoName("http://git3.com");

        result = complexityRank3.compareTo(complexityRank);
        Assert.assertTrue(result == 0);
    }
}
