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
        Assert.assertEquals(Ints.asList(CIssueStatusField.UNSOLVED.value()), IssueStatus.IGNORED.getStatusList());
        Assert.assertEquals(Ints.asList(CIssueStatusField.SOLVED.value()), IssueStatus.REVISED.getStatusList());
        Assert.assertEquals(CIssueStatusField.getAllStatuses(), IssueStatus.VIOLATED.getStatusList());
    }

    @Test
    public void shouldReturnListWhenGettingManualStatus() {
        Assert.assertEquals(Ints.asList(2), IssueStatus.IGNORED.getAllManualStatusList());

    }
}
