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

    /** 仓库名称 **/
    private String repoName;

    /** 任务名称 **/
    private String taskName;

    /** 任务URL **/
    private String taskDetailUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractRank)) return false;
        AbstractRank that = (AbstractRank) o;
        if (repoName != null ? !repoName.equals(that.repoName) : that.repoName != null) return false;
        return taskName != null ? taskName.equals(that.taskName) : that.taskName == null;
    }

    @Override
    public int hashCode() {
        int result = repoName != null ? repoName.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        return result;
    }
}
