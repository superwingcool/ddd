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
            "AND cpm.cMetrics.name IN (:measureNameList) " +
            "AND cpm.cSnapshots.id IN (:snapshotIdList)"
    )
    List<CProjectMeasures> findMeasureListByDevcloudProjectId(
            @Param("devcloudProjectUuid") String devcloudProjectUuid,
            @Param("measureNameList") List<String> measureNameList,
            @Param("snapshotIdList") List<Long> snapshotIdList);
}
