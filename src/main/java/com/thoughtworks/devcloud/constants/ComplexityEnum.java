package com.thoughtworks.devcloud.constants;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for complexity name of table C_METRICS.
 */
public enum ComplexityEnum {

    COMPLEXITY("complexity", "Cyclomatic complexity"),
    FILE_COMPLEXITY("file_complexity", "Complexity average by file"),
    FUNCTION_COMPLEXITY("function_complexity", "Complexity average by function");

    private String name;
    private String description;

    private ComplexityEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getComplexityName() {
        return name;
    }

    public static List<String> getAllComplexityNames() {
        return Arrays.stream(values())
                .map(complexityEnum -> complexityEnum.getComplexityName()).collect(Collectors.toList());
    }

    public static ComplexityEnum fromComplexityName(String complexityName) {
        for (ComplexityEnum complexityEnum : ComplexityEnum.values()) {
            if (complexityEnum.getComplexityName().equalsIgnoreCase(complexityName)) {
                return complexityEnum;
            }
        }
        return null;
    }
}
