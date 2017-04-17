package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CMetrics;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.domain.CProjects;
import com.thoughtworks.devcloud.domain.CSnapshots;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;


public class TenantDuplicatedLineMeasuresMapperTest {

    private final String key = "http://git.address.test.com/";
    private TenantDuplicatedLineMeasuresMapper tenantDuplicatedLineMeasuresMapper;

    @Before
    public void setUp(){

        tenantDuplicatedLineMeasuresMapper = new TenantDuplicatedLineMeasuresMapper();
    }

    @Test
    public void transformShouldAddNewRankGivenExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures();
        List<TenantDuplicatedLineRank> ranks = tenantDuplicatedLineMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getProjectName(), is("projectName"));
        assertThat(ranks.get(0).getCodeLines(), nullValue());

    }

    @Test
    public void transformShouldAddNewRankGivenNotExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures();
        List<TenantDuplicatedLineRank> ranks = tenantDuplicatedLineMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getProjectName(), is("projectName"));
        assertThat(ranks.get(0).getDuplicatedLines(), is(BigDecimal.TEN));

    }

    private List<CProjectMeasures> getProjectMeasures() {
        List<CProjectMeasures> cProjectMeasures = new ArrayList<>();
        CProjectMeasures cProjectMeasure = new CProjectMeasures();
        cProjectMeasure.setCProjects(createProject());
        cProjectMeasure.setCSnapshots(createSnapshot());
        cProjectMeasure.setCMetrics(createCMetrics());
        cProjectMeasure.setValue(BigDecimal.TEN);

        CProjectMeasures cProjectMeasure1 = new CProjectMeasures();
        cProjectMeasure1.setCSnapshots(createSnapshot());
        cProjectMeasure1.setCProjects(createProject());
        cProjectMeasure1.setCMetrics(createCMetrics());
        cProjectMeasure1.setValue(BigDecimal.TEN);
        cProjectMeasures.add(cProjectMeasure1);
        return cProjectMeasures;
    }

    private CProjects createProject(){
        CProjects project = new CProjects();
        project.setProjectName("projectName");
        return project;
    }

    private DuplicatedLineRank getDuplicatedLineRank() {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        duplicatedLineRank.setCodeLines(BigDecimal.ONE);
        return duplicatedLineRank;
    }

    private CMetrics createCMetrics() {
        CMetrics metrics = new CMetrics();
        metrics.setName("duplicated_lines");
        return metrics;
    }

    private CSnapshots createSnapshot() {
        CSnapshots snapshot = new CSnapshots();
        snapshot.setScmAddr(key);
        return snapshot;
    }

}