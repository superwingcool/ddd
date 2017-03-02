package com.thoughtworks.devcloud.repository;

import com.thoughtworks.devcloud.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByName(String name);

    @Query("from Project p where p.name=:name")
    Project findProjects(@Param("name") String name);


}
