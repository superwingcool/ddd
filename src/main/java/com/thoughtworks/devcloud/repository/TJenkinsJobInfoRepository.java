package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CIssues;
import com.thoughtworks.devcloud.model.RuleRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for table T_JENKINS_JOB_INFO.
 */
@Repository
public interface TJenkinsJobInfoRepository extends JpaRepository<CIssues, Long> {

    @Query("SELECT count(distinct tj.gitUrl) FROM TJenkinsJobInfo tj")
    Long countDistinctByGitUrl();
}
