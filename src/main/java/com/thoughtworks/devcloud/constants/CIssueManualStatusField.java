package com.thoughtworks.devcloud.constants;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for status of  table c_issues.
 */
public enum CIssueManualStatusField {

    UNIGNORED(0, "unignored"),
    IGNORED(2, "ignored");

    private int value;
    private String description;

    private CIssueManualStatusField(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return value;
    }

    public static List<Integer> getAllManualStatuses() {
        return Arrays.stream(values())
                .map(manualStatus -> manualStatus.value).collect(Collectors.toList());
    }
}
