package com.thoughtworks.devcloud.mapper;


import com.thoughtworks.devcloud.domain.CMetrics;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.domain.CProjects;
import com.thoughtworks.devcloud.domain.CSnapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MeasuresMapperBaseTest {

    protected final String key = "http://git.address.test.com/";

    protected List<CProjectMeasures> getProjectMeasures(String name) {
        List<CProjectMeasures> cProjectMeasures = new ArrayList<>();
        CProjectMeasures cProjectMeasure1 = new CProjectMeasures();
        cProjectMeasure1.setCSnapshots(createSnapshot());
        cProjectMeasure1.setCProjects(createProject());
        cProjectMeasure1.setCMetrics(createCMetrics(name));
        cProjectMeasure1.setValue(BigDecimal.TEN);
        cProjectMeasures.add(cProjectMeasure1);
        return cProjectMeasures;
    }

    protected CProjectMeasures getExistMeasure(String name) {
        CProjectMeasures cProjectMeasure = new CProjectMeasures();
        cProjectMeasure.setCSnapshots(createSnapshot());
        cProjectMeasure.setCProjects(createProject());
        cProjectMeasure.setCMetrics(createCMetrics(name));
        cProjectMeasure.setValue(BigDecimal.ONE);
        return cProjectMeasure;
    }



    private CProjects createProject(){
        CProjects project = new CProjects();
        project.setProjectName("projectName");
        return project;
    }

    protected CMetrics createCMetrics(String name) {
        CMetrics metrics = new CMetrics();
        metrics.setName(name);
        return metrics;
    }

    private CSnapshots createSnapshot() {
        CSnapshots snapshot = new CSnapshots();
        snapshot.setScmAddr(key);
        return snapshot;
    }
}
