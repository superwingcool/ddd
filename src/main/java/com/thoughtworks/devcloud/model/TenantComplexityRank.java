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
@NoArgsConstructor
@AllArgsConstructor
public class TenantComplexityRank extends ComplexityRank implements Serializable {

    /** 项目名称 **/
    private String projectName;

    public TenantComplexityRank(String repoName, String projectName, BigDecimal fileComplexity,
                                BigDecimal functionComplexity, BigDecimal complexity){
        super.setRepoName(repoName);
        this.projectName = projectName;
        super.setFileComplexity(fileComplexity);
        super.setFunctionComplexity(functionComplexity);
        super.setComplexity(complexity);
    }
}
