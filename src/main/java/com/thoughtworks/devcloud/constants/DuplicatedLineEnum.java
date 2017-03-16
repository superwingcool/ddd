package com.thoughtworks.devcloud.constants;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for duplicated lines of table C_METRICS.
 */
public enum DuplicatedLineEnum {

    LOC("loc", "Number of all code lines - INT"),
    DUPLICATED_LINES("duplicated_lines", "Duplicated lines - INT"),
    DUPLICATED_LINES_DENSITY("duplicated_lines_density", "Duplicated lines balanced by statements - PERCENT");

    private String name;
    private String description;

    private DuplicatedLineEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getMeasureName() {
        return name;
    }

    public static List<String> getAllMeasureNames() {
        return Arrays.stream(values())
                .map(complexityEnum -> complexityEnum.getMeasureName()).collect(Collectors.toList());
    }

    public static DuplicatedLineEnum fromMeasureName(String measureNameStr) {
        for (DuplicatedLineEnum measureEnum : DuplicatedLineEnum.values()) {
            if (measureEnum.getMeasureName().equalsIgnoreCase(measureNameStr)) {
                return measureEnum;
            }
        }
        return null;
    }
    }
