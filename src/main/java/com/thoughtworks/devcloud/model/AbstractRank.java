package com.thoughtworks.devcloud.model;

import lombok.Data;


/**
 * Abstract entity for ranking.
 */
@Data
public abstract class AbstractRank implements Comparable<AbstractRank> {

    /** 排名 **/
    protected int rank;
}
