package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.domain.Project;

import java.util.List;

/**
 * Project service interface.
 */
public interface ProjectService {
    List<Project> findAllProjects();

    Project findByName(String name);
}
