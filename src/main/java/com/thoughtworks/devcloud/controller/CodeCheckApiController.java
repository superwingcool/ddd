package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.constants.IssueStatus;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.service.TJenkinsJobInfoService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CodeCheckApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private CIssuesService cIssuesService;

    @Autowired
    private TJenkinsJobInfoService tJenkinsJobInfoService;

    @RequestMapping(value = "/violated/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /violated/{devcloudProjectUuid} , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.UNSOLVED);
    }

    @RequestMapping(value = "/ignored/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getIgnoredRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /ignored/{devcloudProjectUuid} , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.IGNORED);
    }

    @RequestMapping(value = "/revised/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getRevisedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /revised/{devcloudProjectUuid} , devcloudProjectUuid: " + devcloudProjectUuid);

        return getRuleRankInfo(devcloudProjectUuid, IssueStatus.SOLVED);
    }

    private ResponseObject getRuleRankInfo(String devcloudProjectUuid, IssueStatus issueStatus) {
        List<RuleRank> ruleRankList = cIssuesService.findCIssuesListByDevcloudProjectId(devcloudProjectUuid,
                issueStatus);
        Long repoCheckCount = tJenkinsJobInfoService.countDistinctByGitUrl();
        return CodeCheckUtils.transform2ResponseObject(ruleRankList, repoCheckCount);
    }

}
