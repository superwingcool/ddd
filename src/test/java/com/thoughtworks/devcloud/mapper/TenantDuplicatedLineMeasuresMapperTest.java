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


public class TenantDuplicatedLineMeasuresMapperTest extends MeasuresMapperBaseTest {

    private TenantDuplicatedLineMeasuresMapper tenantDuplicatedLineMeasuresMapper;

    @Before
    public void setUp(){

        tenantDuplicatedLineMeasuresMapper = new TenantDuplicatedLineMeasuresMapper();
    }

    @Test
    public void transformShouldAddNewRankGivenExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("duplicated_lines");
        cProjectMeasures.add(getExistMeasure("duplicated_lines_density"));
        List<TenantDuplicatedLineRank> ranks = tenantDuplicatedLineMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getProjectName(), is("projectName"));
        assertThat(ranks.get(0).getDuplicatedLinesDensityCompare(), is(BigDecimal.ONE));
        assertThat(ranks.get(0).getDuplicatedLinesDensity(), is("1.00%"));
        assertThat(ranks.get(0).getDuplicatedLines(), is(BigDecimal.TEN));

    }

    @Test
    public void transformShouldAddNewRankGivenNotExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("duplicated_lines");
        List<TenantDuplicatedLineRank> ranks = tenantDuplicatedLineMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getProjectName(), is("projectName"));
        assertThat(ranks.get(0).getDuplicatedLinesDensityCompare(), nullValue());
        assertThat(ranks.get(0).getDuplicatedLinesDensity(), nullValue());
        assertThat(ranks.get(0).getDuplicatedLines(), is(BigDecimal.TEN));

    }


}