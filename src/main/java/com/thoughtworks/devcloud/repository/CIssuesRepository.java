package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CIssues;
import com.thoughtworks.devcloud.model.RuleRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for table C_ISSUES.
 */
@Repository
public interface CIssuesRepository extends JpaRepository<CIssues, Long> {

    @Query("SELECT new com.thoughtworks.devcloud.model.RuleRank(i.cRules.name, " +
            "i.cRules.priority, i.cRules.categoryBig, i.cRules.language, i.cRules.systemTags, count(i.cRules.id)) " +
            "FROM CIssues i " +
            "WHERE i.status=:issueStatus AND i.manualStatus in (:manualStatuses) " +
            "AND i.cProjects.devcloudProjectUuid=:devcloudProjectUuid " +
            "AND i.cProjects.projectUuid IN " +
            "(" +
                "SELECT tjb.cProjects.projectUuid FROM TJenkinsJobBuildInfo tjb " +
                "WHERE tjb.id in (SELECT max(tjb1.id) FROM TJenkinsJobBuildInfo tjb1 GROUP BY tjb1.gitUrl) " +

            ")" +
            "GROUP BY i.cRules.id ORDER BY count(i.cRules.id) DESC"
    )
    List<RuleRank> findCIssuesListByDevcloudProjectIdAndStatus(@Param("devcloudProjectUuid") String devcloudProjectUuid,
                                                               @Param("issueStatus") Integer status,
                                                               @Param("manualStatuses") List<Integer> manualStatusList);
}
