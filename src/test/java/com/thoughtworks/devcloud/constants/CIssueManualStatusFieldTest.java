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
    }
}
