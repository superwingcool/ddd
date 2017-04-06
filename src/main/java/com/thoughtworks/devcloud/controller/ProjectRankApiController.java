package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ComplexityRank;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.ComplexityRankService;
import com.thoughtworks.devcloud.service.DuplicatedLineRankService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/projects")
public class ProjectRankApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ComplexityRankService complexityRankService;

    @Autowired
    private DuplicatedLineRankService duplicatedLineRankService;

    @Autowired
    private CIssuesService cIssuesService;

    @Autowired
    private CProjectsService cProjectsService;

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/{devcloudProjectUuid}/repos/complexity", method = RequestMethod.GET)
    public ResponseObject getComplexityRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /projects/{devcloudProjectUuid}/repos/complexity, devcloudProjectUuid: " + devcloudProjectUuid);
        List<ComplexityRank> complexityRankList = complexityRankService.findComplexityListByDevcloudProjectId(devcloudProjectUuid);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);
        return CodeCheckUtils.transform2ResponseObject(complexityRankList, repoCheckCount);
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/{devcloudProjectUuid}/repos/duplicatedLine", method = RequestMethod.GET)
    public ResponseObject getDuplicatedLineRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /project/{devcloudProjectUuid}/repos/duplicatedLine, devcloudProjectUuid: " + devcloudProjectUuid);
        List<DuplicatedLineRank> duplicatedLineRankList =
                duplicatedLineRankService.findMeasureListByDevcloudProjectId(devcloudProjectUuid);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);
        return CodeCheckUtils.transform2ResponseObject(duplicatedLineRankList, repoCheckCount);
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/{devcloudProjectUuid}/rules/violated", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /project/{devcloudProjectUuid}/rules/violated , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.VIOLATED);
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/{devcloudProjectUuid}/rules/ignored", method = RequestMethod.GET)
    public ResponseObject getIgnoredRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /projects/{devcloudProjectUuid}/rules/ignored , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.IGNORED);
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/{devcloudProjectUuid}/rules/revised", method = RequestMethod.GET)
    public ResponseObject getRevisedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /projects/{devcloudProjectUuid}/rules/revised , devcloudProjectUuid: " + devcloudProjectUuid);
        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.REVISED);
    }

    private ResponseObject getRuleRankInfo(String devcloudProjectUuid, IssueStatus issueStatus) {
        List<RuleRank> ruleRankList = cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);
        return CodeCheckUtils.transform2ResponseObject(ruleRankList, repoCheckCount);
    }
}
