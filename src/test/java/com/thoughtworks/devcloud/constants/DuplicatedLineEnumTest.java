package com.thoughtworks.devcloud.constants;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for {@link DuplicatedLineEnum}.
 */
public class DuplicatedLineEnumTest {
    @Test
    public void shouldReturnAllComplexityNames() {
        List<String> duplicatedLineNameList =  DuplicatedLineEnum.getAllMeasureNames();
        Assert.assertEquals(3, duplicatedLineNameList.size());
        Assert.assertTrue(duplicatedLineNameList.contains("loc"));
        Assert.assertTrue(duplicatedLineNameList.contains("duplicated_lines"));
        Assert.assertTrue(duplicatedLineNameList.contains("duplicated_lines_density"));
    }

    @Test
    public void shouldReturnName() {
        Assert.assertEquals("loc", DuplicatedLineEnum.LOC.getMeasureName());
        Assert.assertEquals("duplicated_lines", DuplicatedLineEnum.DUPLICATED_LINES.getMeasureName());
        Assert.assertEquals("duplicated_lines_density", DuplicatedLineEnum.DUPLICATED_LINES_DENSITY.getMeasureName());
    }

    @Test
    public void shouldReturnEnum() {
        DuplicatedLineEnum duplicatedLineEnum = DuplicatedLineEnum.fromMeasureName("loc");
        Assert.assertEquals(DuplicatedLineEnum.LOC, duplicatedLineEnum);
    }
}
