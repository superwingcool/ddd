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

}
