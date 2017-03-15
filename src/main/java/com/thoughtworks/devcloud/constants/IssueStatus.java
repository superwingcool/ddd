package com.thoughtworks.devcloud.constants;


import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enum for status of  table c_issues.
 * status: 0 未解决 ；1 已解决
 * manual status: 0 未忽略 ；2 已忽略
 *
 * violated:  status = 0 & manual status in (0,2)
 * ignored:   status = 0 & manual status = 2
 * revised:   status = 1 & manual status in (0,2)
 */
public enum IssueStatus {

    VIOLATED(CIssueStatusField.UNSOLVED.value(), CIssueManualStatusField.getAllManualStatuses()),
    IGNORED(CIssueStatusField.UNSOLVED.value(), Ints.asList(CIssueManualStatusField.IGNORED.value())),
    REVISED(CIssueStatusField.SOLVED.value(), CIssueManualStatusField.getAllManualStatuses());

    private int status;
    private List<Integer> allManualStatusList;

    private IssueStatus(int status, List<Integer> allManualStatuses) {
        this.status = status;
        this.allManualStatusList = allManualStatuses;
    }

    public int getStatus() {
        return status;
    }

    public List<Integer> getAllManualStatusList() {
        return allManualStatusList;
    }

}
