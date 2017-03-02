package com.thoughtworks.devcloud.service;

import com.thoughtworks.devcloud.domain.Project;
import com.thoughtworks.devcloud.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }


}
