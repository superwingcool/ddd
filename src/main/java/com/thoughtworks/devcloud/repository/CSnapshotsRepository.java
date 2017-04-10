package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CSnapshots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for table C_SNAPSHOTS.
 */
@Repository
public interface CSnapshotsRepository extends JpaRepository<CSnapshots, Long> {

    @Query(value = "SELECT s1.id FROM C_SNAPSHOTS s1 " +
            "JOIN" +
            "(SELECT s2.scm_addr AS scm_addr, MAX(s2.COMMIT_END_TIME) AS end_time FROM C_SNAPSHOTS s2 " +
            "GROUP BY s2.scm_addr) AS s3 " +
            "ON s1.scm_addr = s3.scm_addr AND s1.commit_end_time = s3.end_time " +
            "WHERE s1.project_id in " +
            "(select p.id from C_PROJECTS p where p.devcloud_project_uuid in (:devCloudProjectUuid))",
            nativeQuery = true)
    List<Long> findLatestCSnapshotsIdListByGitUrl(@Param("devCloudProjectUuid") List<String> devCloudProjectUuid);

}
