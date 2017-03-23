package com.thoughtworks.devcloud.constants;

import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test for {@link IssueStatus}.
 */
public class IssueStatusTest {

    @Test
    public void shouldReturnStatusWhenGettingStatus() {
        Assert.assertEquals(0, IssueStatus.IGNORED.getStatus());
        Assert.assertEquals(1, IssueStatus.REVISED.getStatus());
        Assert.assertEquals(0, IssueStatus.VIOLATED.getStatus());
    }

    @Test
    public void shouldReturnListWhenGettingManualStatus() {
        Assert.assertEquals(Ints.asList(2), IssueStatus.IGNORED.getAllManualStatusList());

    }
}
