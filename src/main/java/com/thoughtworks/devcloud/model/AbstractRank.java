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
@NoArgsConstructor
@AllArgsConstructor
public class AbstractRank implements Comparable<AbstractRank> {

    /** 排名: 根据counts 降序排列 **/
    protected int rank;

    /** 次数: 统计出来的**/
    protected Long counts;

    protected AbstractRank(Long counts) {
        this.counts = counts;
    }

    @Override
    public int compareTo(AbstractRank o) {
        return this.getCounts().compareTo(o.getCounts());
    }
}
