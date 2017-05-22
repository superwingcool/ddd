package com.thoughtworks.devcloud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Entity for duplicated line ranking.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"duplicatedLinesDensityCompare"})
public class DuplicatedLineRank extends AbstractRank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库名称
     **/
    private String repoName;

    /**
     * 代码重复率
     **/
    private BigDecimal duplicatedLinesDensityCompare;

    private String duplicatedLinesDensity;

    /**
     * 代码行
     **/
    private BigDecimal codeLines;

    /**
     * 重复代码行数
     **/
    private BigDecimal duplicatedLines;

    private String taskName;

    private String taskDetailUrl;

    @Override
    public int compareTo(AbstractRank o) {
        DuplicatedLineRank target = (DuplicatedLineRank) o;
        if (this.getDuplicatedLinesDensity() == null) return -1;
        if (target.getDuplicatedLinesDensity() == null) return 1;
        int result = this.getDuplicatedLinesDensityCompare().compareTo(target.getDuplicatedLinesDensityCompare());
        if (result == 0) {
            result = Math.negateExact(this.getRepoName().compareTo(target.getRepoName()));
        }
        return result;

    }
}
