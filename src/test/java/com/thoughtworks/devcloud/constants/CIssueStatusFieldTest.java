package com.thoughtworks.devcloud.constants;

import org.junit.Assert;
import org.junit.Test;

/**
 * CUnit tests for {@link CIssueStatusField}.
 */
public class CIssueStatusFieldTest {

    @Test
    public void shouldReturnInteger() {
        Assert.assertEquals(1, CIssueStatusField.SOLVED.value());
        Assert.assertEquals(0, CIssueStatusField.UNSOLVED.value());
    }
}
