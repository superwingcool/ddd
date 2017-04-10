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
            "WHERE i.status in (:issueStatuses) AND i.manualStatus in (:manualStatuses) " +
            "AND i.cProjects.devcloudProjectUuid in (:devCloudProjectUuid) " +
            "AND i.cSnapshots.id IN (:snapshotIdList)" +
            "GROUP BY i.cRules.id ORDER BY count(i.cRules.id) DESC, i.cRules.name ASC"
    )
    List<RuleRank> findCIssuesListByDevcloudProjectIdAndStatus(@Param("devCloudProjectUuid") List<String> devCloudProjectUuid,
                                                               @Param("issueStatuses") List<Integer> statusList,
                                                               @Param("manualStatuses") List<Integer> manualStatusList,
                                                               @Param("snapshotIdList") List<Long> snapshotIdList);

}
