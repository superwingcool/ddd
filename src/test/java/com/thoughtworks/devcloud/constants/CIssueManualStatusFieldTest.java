package com.thoughtworks.devcloud.constants;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for {@link CIssueManualStatusField}.
 */
public class CIssueManualStatusFieldTest {

    @Test
    public void shouldReturnAllStatusString() {
        List<Integer> manualStatusList = CIssueManualStatusField.getAllManualStatuses();
        Assert.assertEquals(2, manualStatusList.size());

        Assert.assertTrue(manualStatusList.contains(0));
        Assert.assertTrue(manualStatusList.contains(2));
        Assert.assertFalse(manualStatusList.contains(1));
    }

    @Test
    public void shouldReturnInteger() {
        Assert.assertEquals(2, CIssueManualStatusField.IGNORED.value());
        Assert.assertEquals(0, CIssueManualStatusField.UNIGNORED.value());
    }
}
