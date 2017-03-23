package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for table C_PROJECTS.
 */
@Repository
public interface CProjectsRepository extends JpaRepository<CProjects, Long> {

    @Query("SELECT count(distinct p.scmAddr) FROM CProjects p WHERE p.devcloudProjectUuid=:devcloudProjectUuid " +
            "AND p.currentSnapshotId != -1")
    Long countDistinctByGitUrl(@Param("devcloudProjectUuid") String devcloudProjectUuid);
}
