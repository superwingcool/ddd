package com.thoughtworks.devcloud.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class NumberUtilsTest {

    @Test
    public void getPercentFromBigDecimalShouldReturn12PGiven12B() {
        String result = NumberUtils.getPercentFromBigDecimal(BigDecimal.valueOf(12.0000000));
        assertThat(result, is("12.00%"));
    }

    @Test
    public void getPercentFromBigDecimalShouldReturn12P12Given12P12() {
        String result = NumberUtils.getPercentFromBigDecimal(BigDecimal.valueOf(12.1200000));
        assertThat(result, is("12.12%"));
    }

    @Test
    public void getPercentFromBigDecimalShouldReturn12P35Given12P345() {
        String result = NumberUtils.getPercentFromBigDecimal(BigDecimal.valueOf(12.34600000));
        assertThat(result, is("12.35%"));
    }

}