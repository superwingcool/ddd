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

    @Test
    public void shouldReturnName() {
        Assert.assertEquals("complexity", ComplexityEnum.COMPLEXITY.getMeasureName());
        Assert.assertEquals("file_complexity", ComplexityEnum.FILE_COMPLEXITY.getMeasureName());
        Assert.assertEquals("function_complexity", ComplexityEnum.FUNCTION_COMPLEXITY.getMeasureName());
    }

    @Test
    public void shouldReturnEnum() {
        ComplexityEnum complexityEnum = ComplexityEnum.fromMeasureName("complexity");
        Assert.assertEquals(ComplexityEnum.COMPLEXITY, complexityEnum);
    }
}
