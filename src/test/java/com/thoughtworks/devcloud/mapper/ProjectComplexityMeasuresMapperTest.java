package com.thoughtworks.devcloud.mapper;

import com.thoughtworks.devcloud.domain.CProjectMeasures;
import com.thoughtworks.devcloud.model.ComplexityRank;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


public class ProjectComplexityMeasuresMapperTest extends MeasuresMapperBaseTest{


    private ProjectComplexityMeasuresMapper projectComplexityMeasuresMapper;

    @Before
    public void setUp(){

        projectComplexityMeasuresMapper = new ProjectComplexityMeasuresMapper();
    }

    @Test
    public void transformShouldAddNewRankGivenExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("complexity");
        cProjectMeasures.add(getExistMeasure("file_complexity"));
        List<ComplexityRank> ranks = projectComplexityMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getRepoName(), is(key));
        assertThat(ranks.get(0).getFileComplexity(), is(BigDecimal.ONE));
        assertThat(ranks.get(0).getComplexity(), is(BigDecimal.TEN));

    }

    @Test
    public void transformShouldAddNewRankGivenNotExisted() throws Exception {

        List<CProjectMeasures> cProjectMeasures = getProjectMeasures("complexity");
        List<ComplexityRank> ranks = projectComplexityMeasuresMapper.transformMeasure2Rank(cProjectMeasures);
        assertThat(ranks.size(), is(1));
        assertThat(ranks.get(0).getRepoName(), is(key));
        assertThat(ranks.get(0).getFileComplexity(), nullValue());
        assertThat(ranks.get(0).getComplexity(), is(BigDecimal.TEN));


    }



//    private CProjectMeasures getExistMeasure() {
//        CProjectMeasures cProjectMeasure = new CProjectMeasures();
//        cProjectMeasure.setCSnapshots(createSnapshot());
//        cProjectMeasure.setCMetrics(createFileFiCMetrics());
//        cProjectMeasure.setValue(BigDecimal.ONE);
//        return cProjectMeasure;
//    }

//    private CMetrics createFileFiCMetrics() {
//        CMetrics metrics = new CMetrics();
//        metrics.setName("file_complexity");
//        return metrics;
//    }
//
//    private CMetrics createCMetrics(String name) {
//        CMetrics metrics = new CMetrics();
//        metrics.setName("complexity");
//        return metrics;
//    }



}