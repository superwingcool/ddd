package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.TJenkinsJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository for table T_JENKINS_JOB_INFO.
 */
@Repository
public interface TJenkinsJobInfoRepository extends JpaRepository<TJenkinsJobInfo, Long> {

//    @Query("SELECT count(distinct tj.gitUrl) FROM TJenkinsJobInfo tj")
//    Long countDistinctByGitUrl();
}
