package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CMetrics;
import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.domain.CProjects;
import com.thoughtworks.devcloud.domain.CSnapshots;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class MeasuresMapperTest {


    private final String key = "http://git.address.test.com/";
    private final String projectName = "projectName";

    private MeasuresMapper measuresMapper;

    @Before
    public void setUp(){
        measuresMapper = new MeasuresMapper() {
            @Override
            List transformMeasure2Rank(List list) {
                return null;
            }
        };
    }

    @Test
    public void setDuplicatedLineValuesShouldRankGivenDuplicatedLines() throws Exception {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        measuresMapper.setDuplicatedLineValues(duplicatedLineRank, "duplicated_lines", BigDecimal.ONE);
        assertThat(duplicatedLineRank.getDuplicatedLines(), is(BigDecimal.ONE));
    }

    @Test
    public void setDuplicatedLineValuesShouldRankGivenDuplicatedLinesDensity() throws Exception {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        measuresMapper.setDuplicatedLineValues(duplicatedLineRank, "duplicated_lines_density", BigDecimal.ONE);
        assertThat(duplicatedLineRank.getDuplicatedLinesDensity(), is("1.00%"));
    }

    @Test
    public void setDuplicatedLineValuesShouldRankGivenLoc() throws Exception {
        DuplicatedLineRank duplicatedLineRank = new DuplicatedLineRank();
        measuresMapper.setDuplicatedLineValues(duplicatedLineRank, "loc", BigDecimal.ONE);
        assertThat(duplicatedLineRank.getCodeLines(), is(BigDecimal.ONE));
    }



    @Test
    public void getProjectName() throws Exception {
        CProjectMeasures c = createProjectMeasures();
        assertThat(c.getCProjects().getProjectName(), is("projectName"));
    }

    private CProjectMeasures createProjectMeasures() {
        CProjectMeasures projectMeasure = new CProjectMeasures();
        CProjects cProject = new CProjects();
        cProject.setProjectName("projectName");
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
    public void getMetricName() throws Exception {
        CProjectMeasures c = createProjectMeasures();
        assertThat(c.getCMetrics().getName(), is("duplicated_line"));

    }

    @Test
    public void getRepName() throws Exception {
        CProjectMeasures c = createProjectMeasures();
        assertThat(c.getCSnapshots().getScmAddr(), is("repo_name"));
    }

    @Test
    public void getValue() throws Exception {
        CProjectMeasures c = createProjectMeasures();
        assertThat(c.getValue(), is(BigDecimal.TEN));

    }

}