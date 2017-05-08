package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository for table C_PROJECTS.
 */
@Repository
public interface CProjectsRepository extends JpaRepository<CProjects, Long> {

    @Query("SELECT count(distinct p.scmAddr) FROM CProjects p WHERE p.devcloudProjectUuid in (:devCloudProjectUuid) " +
            "AND p.currentSnapshotId != -1")
    Long countDistinctByGitUrl(@Param("devCloudProjectUuid") List<String> devCloudProjectUuid);

    @Query(value = "select c.DEVCLOUD_PROJECT_UUID from C_PROJECTS c" +
            " left join C_PROJECT_USERS u on c.PROJECT_UUID =  u.PROJECT_UUID" +
            " where u.DOMAIN_UUID = ?1 and c.CURRENT_SNAPSHOT_ID != -1" +
            " group by c.DEVCLOUD_PROJECT_UUID"
            , nativeQuery = true)
    List<String> getScannedProjectsByTenantId(@Param("tenantId") String tenantId);
}
