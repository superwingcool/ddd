package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Complexity rank api.
 * Complexity means Cyclomatic complexity.
 */
@RestController
@RequestMapping(value = "/complexityRank")
public class ComplexityRankApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ComplexityRankService complexityRankService;

    @Autowired
    private CProjectsService cProjectsService;

    @RequestMapping(value = "/project/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /complexityRank/project/{devcloudProjectUuid}, devcloudProjectUuid: " + devcloudProjectUuid);
        List<ComplexityRank> complexityRankList = complexityRankService.findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);
        return CodeCheckUtils.transform2ResponseObject(complexityRankList, repoCheckCount);
    }

}
