package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.*;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import com.thoughtworks.devcloud.service.DuplicatedLineRankService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/tenants")
public class TenantRankApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ComplexityRankService complexityRankService;

    @Autowired
    private DuplicatedLineRankService duplicatedLineRankService;

    @Autowired
    private CIssuesService cIssuesService;

    @RequestMapping(value = "/{tenantId}/repos/complexity", method = RequestMethod.GET)
    public ResponseObject getComplexityRules(@PathVariable String tenantId) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /tenants/{tenantId}/repos/complexity, tenantId: " + tenantId);
        ResultObject<TenantComplexityRank> complexityRankList =
                complexityRankService.getComplexityListByTenantId(tenantId);
        return CodeCheckUtils.transform2ResponseObject(complexityRankList);
    }

    @RequestMapping(value = "/{tenantId}/repos/duplicatedLine", method = RequestMethod.GET)
    public ResponseObject getDuplicatedLineRules(@PathVariable String tenantId) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /tenants/{tenantId}/repos/duplicatedLine, tenantId: " + tenantId);
        ResultObject<TenantDuplicatedLineRank> complexityRankList =
                duplicatedLineRankService.findMeasureListByDevCloudTenantId(tenantId);
        return CodeCheckUtils.transform2ResponseObject(complexityRankList);
    }

    @RequestMapping(value = "/{tenantId}/rules/violated", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String tenantId) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /tenants/{tenantId}/rules/violated , tenantId: " + tenantId);
        ResultObject<RuleRank> ruleRanks = cIssuesService.findCIssuesListByTenantId(tenantId, IssueStatus.VIOLATED);
        return CodeCheckUtils.transform2ResponseObject(ruleRanks);
    }

    @RequestMapping(value = "/{tenantId}/rules/ignored", method = RequestMethod.GET)
    public ResponseObject getIgnoredRules(@PathVariable String tenantId) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /tenants/{tenantId}/rules/ignored , tenantId: " + tenantId);
        ResultObject<RuleRank> ruleRanks = cIssuesService.findCIssuesListByTenantId(tenantId, IssueStatus.IGNORED);
        return CodeCheckUtils.transform2ResponseObject(ruleRanks);
    }

    @RequestMapping(value = "/{tenantId}/rules/revised", method = RequestMethod.GET)
    public ResponseObject getRevisedRules(@PathVariable String tenantId) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /tenants/{tenantId}/rules/revised , tenantId: " + tenantId);
        ResultObject<RuleRank> ruleRanks = cIssuesService.findCIssuesListByTenantId(tenantId, IssueStatus.REVISED);
        return CodeCheckUtils.transform2ResponseObject(ruleRanks);
    }


}