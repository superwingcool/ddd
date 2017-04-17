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
            "FROM CProjectMeasures cpm JOIN FETCH cpm.cProjects p" +
            " JOIN FETCH cpm.cSnapshots s JOIN FETCH cpm.cMetrics m" +
            " where p.devcloudProjectUuid in(:devCloudProjectUuid)" +
            " AND m.name IN (:measureNames)" +
            " AND s.id IN (:snapshotIds)"
    )
    List<CProjectMeasures> findMeasureListByDevCloudProjectId(
            @Param("devCloudProjectUuid") List<String> devCloudProjectUuid,
            @Param("measureNames") List<String> measureNames,
            @Param("snapshotIds") List<Long> snapshotIds
    );

    @Query(value = "SELECT repoName, projectName," +
            " fileComplexity, functionComplexity, complexity " +
            " FROM (SELECT p.PROJECT_NAME as projectName, p.SCM_ADDR as repoName," +
            " sum(case when m.NAME='complexity' then c.VALUE end ) as complexity," +
            " sum(case when m.NAME='file_complexity' then c.VALUE  end ) as fileComplexity," +
            " sum(case when m.NAME='function_complexity' then c.VALUE  end ) as functionComplexity" +
            " FROM C_PROJECT_MEASURES c left join C_PROJECTS p on c.PROJECT_UUID=p.PROJECT_UUID" +
            " left join C_METRICS m on c.METRIC_ID=m.ID" +
            " where p.DEVCLOUD_PROJECT_UUID in (:devCloudProjectIds)" +
            " and c.SNAPSHOT_ID in (:snapshotIds)" +
            " and m.NAME in (:measureNames)" +
            " group by p.DEVCLOUD_PROJECT_UUID,p.SCM_ADDR, p.PROJECT_NAME) result" +
            " order by complexity DESC, repoName ASC" ,
            nativeQuery = true)
    List<Object[]> getMeasureListByProjects(
            @Param("devCloudProjectIds") List<String> devCloudProjectIds,
            @Param("measureNames") List<String> measureNames,
            @Param("snapshotIds") List<Long> snapshotIds);
}
