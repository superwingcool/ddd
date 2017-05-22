package com.thoughtworks.devcloud.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class DuplicatedLineRankTest {

    @Test
    public void compareReturnTrueGivenNull() {
        DuplicatedLineRank duplicatedLineRankNull = new DuplicatedLineRank();
        duplicatedLineRankNull.setDuplicatedLinesDensity(null);
        duplicatedLineRankNull.setDuplicatedLinesDensityCompare(null);
        duplicatedLineRankNull.setRepoName("http://git.com");


        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        duplicatedLineRank.setDuplicatedLinesDensity("30%");
        duplicatedLineRankNull.setDuplicatedLinesDensityCompare(new BigDecimal("30"));
        duplicatedLineRank.setRepoName("http://git.com");

        Assert.assertTrue(duplicatedLineRankNull.compareTo(duplicatedLineRank) < 0);
        Assert.assertTrue(duplicatedLineRank.compareTo(duplicatedLineRankNull) > 0);
    }

    @Test
    public void compareToReturnSuccessGivenNotNull() {

        DuplicatedLineRank duplicatedLineRank1 = new DuplicatedLineRank();
        duplicatedLineRank1.setDuplicatedLinesDensity("30%");
        duplicatedLineRank1.setDuplicatedLinesDensityCompare(new BigDecimal("30"));
        duplicatedLineRank1.setRepoName("http://git1.com");

        DuplicatedLineRank duplicatedLineRank2 = new DuplicatedLineRank();
        duplicatedLineRank2.setDuplicatedLinesDensity("40%");
        duplicatedLineRank2.setDuplicatedLinesDensityCompare(new BigDecimal("40"));
        duplicatedLineRank2.setRepoName("http://git2.com");

        DuplicatedLineRank duplicatedLineRank3 = new DuplicatedLineRank();
        duplicatedLineRank3.setDuplicatedLinesDensity("30%");
        duplicatedLineRank3.setDuplicatedLinesDensityCompare(new BigDecimal("30"));
        duplicatedLineRank3.setRepoName("http://git3.com");

        assertThat(duplicatedLineRank1.compareTo(duplicatedLineRank2), lessThan(0));
        assertThat(duplicatedLineRank1.compareTo(duplicatedLineRank3), greaterThan(0));

    }

}