package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for table CProjectMeasures.
 */
@Repository
public interface CProjectMeasuresRepository extends JpaRepository<CProjectMeasures, Long> {

    @Query("SELECT cpm " +
            "FROM CProjectMeasures cpm " +
            "WHERE cpm.cProjects.devcloudProjectUuid=:devcloudProjectUuid " +
            "AND cpm.cMetrics.name IN (:complexityNameList)" +
            "AND cpm.cProjects.projectUuid IN " +
            "(" +
                "SELECT tjb.cProjects.projectUuid FROM TJenkinsJobBuildInfo tjb " +
                "WHERE tjb.id in (SELECT max(tjb1.id) FROM TJenkinsJobBuildInfo tjb1 GROUP BY tjb1.gitUrl) " +

            ")"
    )
    List<CProjectMeasures> findComplexityListByDevcloudProjectId(
            @Param("devcloudProjectUuid") String devcloudProjectUuid,
            @Param("complexityNameList") List<String> complexityNameList);
}
