package com.thoughtworks.devcloud.constants;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for {@link ComplexityEnum}.
 */
public class ComplexityEnumTest {
    @Test
    public void shouldReturnAllComplexityNames() {
        List<String> complexityNameList =  ComplexityEnum.getAllMeasureNames();
        Assert.assertEquals(3, complexityNameList.size());
        Assert.assertTrue(complexityNameList.contains("complexity"));
        Assert.assertTrue(complexityNameList.contains("file_complexity"));
        Assert.assertTrue(complexityNameList.contains("function_complexity"));
    }
}
