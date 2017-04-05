package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/ruleRank")
public class RuleRankApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private CIssuesService cIssuesService;

    @Autowired
    private CProjectsService cProjectsService;

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/violated/project/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /ruleRank/violated/project/{devcloudProjectUuid} , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.VIOLATED);
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/ignored/project/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getIgnoredRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /ruleRank/ignored/project/{devcloudProjectUuid} , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.IGNORED);
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/revised/project/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getRevisedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /ruleRank/revised/project/{devcloudProjectUuid} , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.REVISED);
    }

    private ResponseObject getRuleRankInfo(String devcloudProjectUuid, IssueStatus issueStatus) {
        List<RuleRank> ruleRankList = cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);
        return CodeCheckUtils.transform2ResponseObject(ruleRankList, repoCheckCount);
    }

}
