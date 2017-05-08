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
public class TenantDuplicatedLineRank extends DuplicatedLineRank {

    private static final long serialVersionUID = -9077775046693738453L;

    /** 项目名称 **/
    private String projectName;

    public TenantDuplicatedLineRank(String repoName, String projectName, String duplicatedLinesDensity,
                                    BigDecimal duplicatedLines, BigDecimal codeLines){
        super.setRepoName(repoName);
        this.projectName = projectName;
        super.setDuplicatedLinesDensity(duplicatedLinesDensity);
        super.setDuplicatedLines(duplicatedLines);
        super.setCodeLines(codeLines);
    }
}
