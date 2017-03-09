package com.thoughtworks.devcloud.constants;

/**
 * Enum for status of  table c_issues.
 */
public enum IssueStatus {

    UNSOLVED(0),
    SOLVED(1),
    IGNORED(2);

    private int value;

    private IssueStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
