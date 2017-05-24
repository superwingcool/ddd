package com.thoughtworks.devcloud.model;

import lombok.Getter;
import lombok.Setter;


/**
 * Abstract entity for ranking.
 */
@Setter
@Getter
public abstract class AbstractRank implements Comparable<AbstractRank> {

    /** 排名 **/
    protected int rank;

}
