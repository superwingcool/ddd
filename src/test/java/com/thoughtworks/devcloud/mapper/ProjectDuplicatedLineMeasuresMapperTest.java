package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


public class ProjectDuplicatedLineMeasuresMapperTest extends MeasuresMapperBaseTest {

    private ProjectDuplicatedLineMeasuresMapper projectDuplicatedLineMeasuresMapper;

    @Before
    public void setUp(){
        projectDuplicatedLineMeasuresMapper = new ProjectDuplicatedLineMeasuresMapper();
    }

    @Test
    public void transformShouldAddNewRankGivenExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("duplicated_lines");
        cProjectMeasures.add(getExistMeasure("duplicated_lines_density"));
        List<DuplicatedLineRank> ranks = projectDuplicatedLineMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getDuplicatedLinesDensityCompare(), is(BigDecimal.ONE));
        assertThat(ranks.get(0).getDuplicatedLinesDensity(), is("1.00%"));
        assertThat(ranks.get(0).getDuplicatedLines(), is(BigDecimal.TEN));

    }

    @Test
    public void transformShouldAddNewRankGivenNotExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("duplicated_lines");
        List<DuplicatedLineRank> ranks = projectDuplicatedLineMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getDuplicatedLinesDensityCompare(), nullValue());
        assertThat(ranks.get(0).getDuplicatedLinesDensity(), nullValue());
        assertThat(ranks.get(0).getDuplicatedLines(), is(BigDecimal.TEN));

    }

}