package com.thoughtworks.devcloud.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Entity for complexity ranking.
 */
@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexityRank extends AbstractRank implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 仓库名称 **/
    private String repoName;

    /** 项目名称 **/
    private String projectName;

    /** 文件平均复杂度 **/
    private BigDecimal fileComplexity;

    /** 函数平均复杂度 **/
    private BigDecimal functionComplexity;

    /** 总圈复杂度 **/
    private BigDecimal complexity;



    @Override
    public int compareTo(AbstractRank o) {
        if (o instanceof ComplexityRank) {
            return this.getComplexity().compareTo(((ComplexityRank) o).getComplexity());
        } else {
            throw new RuntimeException("Different type can't be compared!");
        }
    }
}
