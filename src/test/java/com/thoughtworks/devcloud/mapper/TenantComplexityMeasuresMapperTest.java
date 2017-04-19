package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.TenantComplexityRank;
import com.thoughtworks.devcloud.model.TenantDuplicatedLineRank;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


public class TenantComplexityMeasuresMapperTest extends MeasuresMapperBaseTest {

    private TenantComplexityMeasuresMapper tenantComplexityMeasuresMapper;

    @Before
    public void setUp(){

        tenantComplexityMeasuresMapper = new TenantComplexityMeasuresMapper();
    }

    @Test
    public void transformShouldAddNewRankGivenExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("complexity");
        cProjectMeasures.add(getExistMeasure("file_complexity"));
        List<TenantComplexityRank> ranks = tenantComplexityMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getProjectName(), is("projectName"));
        assertThat(ranks.get(0).getRepoName(), is(key));
        assertThat(ranks.get(0).getFileComplexity(), is(BigDecimal.ONE));
        assertThat(ranks.get(0).getComplexity(), is(BigDecimal.TEN));

    }

    @Test
    public void transformShouldAddNewRankGivenNotExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("complexity");
        List<TenantComplexityRank> ranks = tenantComplexityMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getProjectName(), is("projectName"));
        assertThat(ranks.get(0).getRepoName(), is(key));
        assertThat(ranks.get(0).getFileComplexity(), nullValue());
        assertThat(ranks.get(0).getComplexity(), is(BigDecimal.TEN));

    }


}