package com.thoughtworks.devcloud.constants;


/**
 * Enum for status of  table c_issues.
 */
public enum CIssueStatusField {

    UNSOLVED(0, "unsolved"),
    SOLVED(1, "solved");

    private int value;
    private String description;

    private CIssueStatusField(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return value;
    }

}
