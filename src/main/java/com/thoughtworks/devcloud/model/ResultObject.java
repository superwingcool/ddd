package com.thoughtworks.devcloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Result object for rule rank.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject {

    private static final long serialVersionUID = 1L;

    /** total count of info list**/
    private String total;

    private List<RuleRank> info;

    /** count of repo be checked**/
    private String repoCheckedCount;
}
