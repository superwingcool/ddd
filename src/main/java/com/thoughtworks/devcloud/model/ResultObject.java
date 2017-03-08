package com.thoughtworks.devcloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Created by jiezhou on 08/03/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject {

    private static final long serialVersionUID = 1L;

    private String total;

    private List<RuleRank> info;
}
