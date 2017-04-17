package com.thoughtworks.devcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Entity for duplicated line ranking.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicatedLineRank extends AbstractRank implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 仓库名称 **/
    private String repoName;

    /** 代码重复率 **/
    private BigDecimal duplicatedLinesDensity;

    /** 代码行 **/
    private BigDecimal codeLines;

    /** 重复代码行数 **/
    private BigDecimal duplicatedLines;

    @Override
    public int compareTo(AbstractRank o) {
        if (o instanceof DuplicatedLineRank) {
            DuplicatedLineRank target = (DuplicatedLineRank) o;
            if(this.getDuplicatedLinesDensity() == null) return -1;
            if(target.getDuplicatedLinesDensity() == null) return 1;
            int result = this.getDuplicatedLinesDensity().compareTo(target.getDuplicatedLinesDensity());
            if(result == 0){
                result = - this.getRepoName().compareTo(target.getRepoName());
            }
            return result;
        } else {
            throw new RuntimeException("Different type can't be compared!");
        }
    }
}
