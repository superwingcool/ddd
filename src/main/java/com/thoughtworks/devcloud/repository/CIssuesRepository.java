package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CIssues;
import com.thoughtworks.devcloud.model.RuleRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for C_ISSUES.
 */
@Repository
public interface CIssuesRepository extends JpaRepository<CIssues, Long> {

    @Query("SELECT new com.thoughtworks.devcloud.model.RuleRank(i.cRules.name," +
            " i.cRules.priority, i.cRules.categorySmall, i.cRules.language, i.cRules.systemTags, count(i.cRules.id)) " +
            "FROM CIssues i WHERE i.id IN " +
            "(" +
                "SELECT max(ii.id) FROM CIssues ii WHERE i.cProjects.devcloudProjectUuid=:devcloudProjectUuid " +
                "GROUP BY ii.cSnapshots.scmAddr" +
            ")" +
            "GROUP BY i.cRules.id ORDER BY count(i.cRules.id) DESC"
    )
    List<RuleRank> findViolatedCIssuesListByDevcloudProjectId(@Param("devcloudProjectUuid") String devcloudProjectUuid);

    @Query("SELECT new com.thoughtworks.devcloud.model.RuleRank(i.cRules.name," +
            " i.cRules.priority, i.cRules.categorySmall, i.cRules.language, i.cRules.systemTags, count(i.cRules.id)) " +
            "FROM CIssues i WHERE i.cProjects.devcloudProjectUuid=:devcloudProjectUuid")
    List<RuleRank> findIgnoredCIssuesListByDevcloudProjectId(@Param("devcloudProjectUuid") String devcloudProjectUuid);

    @Query("SELECT new com.thoughtworks.devcloud.model.RuleRank(i.cRules.name," +
            " i.cRules.priority, i.cRules.categorySmall, i.cRules.language, i.cRules.systemTags, count(i.cRules.id)) " +
            "FROM CIssues i WHERE i.cProjects.devcloudProjectUuid=:devcloudProjectUuid")
    List<RuleRank> findRevisedCIssuesListByDevcloudProjectId(@Param("devcloudProjectUuid") String devcloudProjectUuid);

}
