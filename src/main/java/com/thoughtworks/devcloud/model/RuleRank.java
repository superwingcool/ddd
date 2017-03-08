package com.thoughtworks.devcloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * Entity for rule ranking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleRank implements Serializable, Comparable<RuleRank> {

    private static final long serialVersionUID = 1L;

    private int rank;

    private String ruleName;

    private Integer priority;

    private String category;

    private String language;

    private String tag;

    private Long counts;

    public RuleRank(String ruleName, Integer priority, String category, String language, String tag, Long counts) {
        this.ruleName = ruleName;
        this.priority = priority;
        this.category = category;
        this.language = language;
        this.tag = tag;
        this.counts = counts;
    }

    @Override
    public int compareTo(RuleRank o) {
        return this.getCounts().compareTo(o.getCounts());
    }
}
