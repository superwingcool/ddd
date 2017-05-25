package com.thoughtworks.devcloud.controller;

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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="/projects")
public class ProjectRankApiController {

    private static final Logger logger = Logger.getLogger(ProjectRankApiController.class);

    @Autowired
    private ComplexityRankService complexityRankService;

    @Autowired
    private DuplicatedLineRankService duplicatedLineRankService;

    @Autowired
    private CIssuesService cIssuesService;

    @Autowired
    private CProjectsService cProjectsService;

    @RequestMapping(value = "/{devCloudProjectUuid}/repos/complexity", method = RequestMethod.GET)
    public ResponseObject getComplexityRules(@PathVariable String devCloudProjectUuid) {
        logger.info("Visit /projects/{devCloudProjectUuid}/repos/complexity, devCloudProjectUuid: " + devCloudProjectUuid);
        List<ComplexityRank> complexityRankList = complexityRankService.findComplexityListByDevcloudProjectId(devCloudProjectUuid);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(Arrays.asList(devCloudProjectUuid));
        return CodeCheckUtils.transform2ResponseObject(complexityRankList, repoCheckCount);
    }

    @RequestMapping(value = "/{devCloudProjectUuid}/repos/duplicatedLine", method = RequestMethod.GET)
    public ResponseObject getDuplicatedLineRules(@PathVariable String devCloudProjectUuid) {
        logger.info("Visit /project/{devCloudProjectUuid}/repos/duplicatedLine, devCloudProjectUuid: " + devCloudProjectUuid);
        List<DuplicatedLineRank> duplicatedLineRankList =
                duplicatedLineRankService.findMeasureListByDevcloudProjectId(devCloudProjectUuid);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(Arrays.asList(devCloudProjectUuid));
        return CodeCheckUtils.transform2ResponseObject(duplicatedLineRankList, repoCheckCount);
    }

    @RequestMapping(value = "/{devCloudProjectUuid}/rules/violated", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devCloudProjectUuid) {
        logger.info("Visit /project/{devCloudProjectUuid}/rules/violated , devcloudProjectUuid: " + devCloudProjectUuid);

        return getRuleRankInfo(devCloudProjectUuid, IssueStatus.VIOLATED);
    }

    @RequestMapping(value = "/{devCloudProjectUuid}/rules/ignored", method = RequestMethod.GET)
    public ResponseObject getIgnoredRules(@PathVariable String devCloudProjectUuid) {
        logger.info("Visit /projects/{devcloudProjectUuid}/rules/ignored , devCloudProjectUuid: " + devCloudProjectUuid);

        return getRuleRankInfo(devCloudProjectUuid, IssueStatus.IGNORED);
    }

    @RequestMapping(value = "/{devCloudProjectUuid}/rules/revised", method = RequestMethod.GET)
    public ResponseObject getRevisedRules(@PathVariable String devCloudProjectUuid) {
        logger.info("Visit /projects/{devCloudProjectUuid}/rules/revised , devcloudProjectUuid: " + devCloudProjectUuid);
        return getRuleRankInfo(devCloudProjectUuid, IssueStatus.REVISED);
    }

    private ResponseObject getRuleRankInfo(String devCloudProjectUuid, IssueStatus issueStatus) {
        List<RuleRank> ruleRankList = cIssuesService.findCIssuesListByDevcloudProjectId(devCloudProjectUuid,
                issueStatus);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(Arrays.asList(devCloudProjectUuid));
        return CodeCheckUtils.transform2ResponseObject(ruleRankList, repoCheckCount);
    }
}
