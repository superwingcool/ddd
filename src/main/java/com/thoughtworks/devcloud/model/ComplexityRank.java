package com.thoughtworks.devcloud.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Entity for complexity ranking.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComplexityRank extends AbstractRank implements Serializable {

    private static final long serialVersionUID = 2652053504612156135L;

    /** 仓库名称 **/
    private String repoName;

    /** 任务名称 **/
    private String taskName;

    /** 任务URL **/
    private String taskDetailUrl;

    /** 文件平均复杂度 **/
    private BigDecimal fileComplexity;

    /** 函数平均复杂度 **/
    private BigDecimal functionComplexity;

    /** 总圈复杂度 **/
    private BigDecimal complexity;


    @Override
    public int compareTo(AbstractRank o) {
        ComplexityRank target = (ComplexityRank) o;
        if (!(o instanceof ComplexityRank)) return -1;
        if (this.getComplexity() == null) return -1;
        if (target.getComplexity() == null) return 1;
        int result = this.getComplexity().compareTo(target.getComplexity());
        if (result == 0) {
            result = Math.negateExact(this.getRepoName().compareTo(target.getRepoName()));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexityRank)) return false;
        ComplexityRank that = (ComplexityRank) o;
        return repoName != null ? repoName.equals(that.repoName) : that.repoName == null;
    }

    @Override
    public int hashCode() {
        return repoName != null ? repoName.hashCode() : 0;
    }
}
