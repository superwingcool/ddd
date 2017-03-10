package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.TJenkinsJobBuildInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for table T_JENKINS_JOB_BUILDER_INFO.
 */
@Repository
public interface TJenkinsJobBuildInfoRepository extends JpaRepository<TJenkinsJobBuildInfo, Long> {

    @Query("SELECT tjb.cProjects.projectUuid FROM TJenkinsJobBuildInfo tjb " +
            "WHERE tjb.id in (SELECT max(tjb1.id) FROM TJenkinsJobBuildInfo tjb1 GROUP BY tjb1.gitUrl) ")
    List<String> findLatestProjectUuidListByGitUrl();
}
