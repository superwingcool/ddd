package com.thoughtworks.devcloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * Abstract entity for ranking.
 */
@Data
public abstract class AbstractRank implements Comparable<AbstractRank> {

    /** 排名 **/
    protected int rank;
}
