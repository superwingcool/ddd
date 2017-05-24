package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CMetrics;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.domain.CProjects;
import com.thoughtworks.devcloud.domain.CSnapshots;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class MeasuresMapperTest {

    private MeasuresMapper measuresMapper;

    @Before
    public void setUp(){
        measuresMapper = new MeasuresMapper() {
            @Override
            protected Object getRank(Map rankMaps, CProjectMeasures cProjectMeasure) {
                return null;
            }

            @Override
            protected void bindRankValues(CProjectMeasures cProjectMeasure, Object rank) {

            }
        };
    }

    @Test
    public void setDuplicatedLineValuesShouldRankGivenDuplicatedLines() {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        measuresMapper.setDuplicatedLineValues(duplicatedLineRank, "duplicated_lines", BigDecimal.ONE);
        assertThat(duplicatedLineRank.getDuplicatedLines(), is(BigDecimal.ONE));
    }

    @Test
    public void setDuplicatedLineValuesShouldRankGivenDuplicatedLinesDensity() {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        measuresMapper.setDuplicatedLineValues(duplicatedLineRank, "duplicated_lines_density", BigDecimal.ONE);
        assertThat(duplicatedLineRank.getDuplicatedLinesDensityCompare(), is(BigDecimal.ONE));
        assertThat(duplicatedLineRank.getDuplicatedLinesDensity(), is("1.00%"));
    }

    @Test
    public void setDuplicatedLineValuesShouldRankGivenLoc() {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        measuresMapper.setDuplicatedLineValues(duplicatedLineRank, "loc", BigDecimal.ONE);
        assertThat(duplicatedLineRank.getCodeLines(), is(BigDecimal.ONE));
    }

    @Test
    public void setComplexityRankValuesShouldRankGivenComplexity() {
        ComplexityRank complexityRank = new ComplexityRank();
        measuresMapper.setComplexityRankValues(complexityRank, "complexity", BigDecimal.TEN);
        assertThat(complexityRank.getComplexity(), is(BigDecimal.TEN));
    }

    @Test
    public void setComplexityRankValuesShouldRankGivenFileComplexity() {
        ComplexityRank complexityRank = new ComplexityRank();
        measuresMapper.setComplexityRankValues(complexityRank, "file_complexity", BigDecimal.TEN);
        assertThat(complexityRank.getFileComplexity(), is(BigDecimal.TEN));
    }

    @Test
    public void setComplexityRankValuesShouldRankGivenFunctionComplexity() {
        ComplexityRank complexityRank = new ComplexityRank();
        measuresMapper.setComplexityRankValues(complexityRank, "function_complexity", BigDecimal.TEN);
        assertThat(complexityRank.getFunctionComplexity(), is(BigDecimal.TEN));
    }

    private CProjectMeasures createProjectMeasures() {
        CProjectMeasures projectMeasure = new CProjectMeasures();
        CProjects cProject = new CProjects();
        cProject.setDevcloudProjectUuid("devCloudProjectUuid");
        projectMeasure.setCProjects(cProject);
        CMetrics cMetrics = new CMetrics();
        cMetrics.setName("duplicated_line");
        projectMeasure.setCMetrics(cMetrics);
        projectMeasure.setValue(BigDecimal.TEN);
        CSnapshots cSnapshots = new CSnapshots();
        cSnapshots.setScmAddr("repo_name");
        projectMeasure.setCSnapshots(cSnapshots);
        return projectMeasure;
    }

    @Test
    public void getProjectName() {
        CProjectMeasures c = createProjectMeasures();
        assertThat(measuresMapper.getProjectName(c), is("devCloudProjectUuid"));
    }

    @Test
    public void getMetricName() {
        CProjectMeasures c = createProjectMeasures();
        assertThat(measuresMapper.getMetricName(c), is("duplicated_line"));
    }

    @Test
    public void getRepName() {
        CProjectMeasures c = createProjectMeasures();
        assertThat(measuresMapper.getRepoName(c), is("repo_name"));
    }

    @Test
    public void getKey() {
        CProjectMeasures c = createProjectMeasures();
        assertThat(measuresMapper.getKey(c, "projectName"), is("repo_nameprojectName"));
    }

    @Test
    public void getValue() {
        CProjectMeasures c = createProjectMeasures();
        assertThat(measuresMapper.getValue(c), is(BigDecimal.TEN));
    }

}