package com.thoughtworks.devcloud.model;

import lombok.*;

import java.io.Serializable;


/**
 * Entity for rule ranking.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RuleRank extends AbstractRank implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 排名: 根据counts 降序排列 **/
    private int rank;

    /** 规则名称: 对应Table C_RULES的字段: NAME **/
    private String ruleName;

    /** 问题级别: 对应Table C_RULES的字段: PRIORITY **/
    private Integer priority;

    /** 问题分类: 对应Table C_RULES的字段: CATEGORY_BIG **/
    private String category;

    /** 语言: 对应Table C_RULES的字段: LANGUAGE **/
    private String language;

    /** 标签: 对应Table C_RULES的字段: SYSTEM_TAGS **/
    private String tag;

    /** 次数: 统计出来的**/
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
    public int compareTo(AbstractRank o) {
        return this.getCounts().compareTo(((RuleRank)o).getCounts());
    }
}
