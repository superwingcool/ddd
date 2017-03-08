package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;
import com.thoughtworks.devcloud.service.CIssuesService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CodeCheckApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private CIssuesService cIssuesService;

    @RequestMapping(value = "/tw/services", method = RequestMethod.GET)
    public String getServices() {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/tw/services, host:" + instance.getHost() + ", port:" + instance.getPort() + ", service_id:"
                + instance.getServiceId());
        return "[Delivery, Consultant]";
    }

    @RequestMapping(value = "/violated/{devProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /violated/{devProjectUuid} , devProjectUuid: " + devProjectUuid);

        List<RuleRank> ruleRankList = cIssuesService.findViolatedCIssuesListByDevcloudProjectId(devProjectUuid, 0);
        return CodeCheckUtils.transform2ResponseObject(ruleRankList);
    }

    @RequestMapping(value = "/ignored/{devProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getIgnoredRules(@PathVariable String devProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /ignored/{devProjectUuid} , devProjectUuid: " + devProjectUuid);

        List<RuleRank> ruleRankList = cIssuesService.findIgnoredCIssuesListByDevcloudProjectId(devProjectUuid, 0);
        return CodeCheckUtils.transform2ResponseObject(ruleRankList);
    }

    @RequestMapping(value = "/revised/{devProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getRevisedRules(@PathVariable String devProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /revised/{devProjectUuid} , devProjectUuid: " + devProjectUuid);

        List<RuleRank> ruleRankList = cIssuesService.findRevisedCIssuesListByDevcloudProjectId(devProjectUuid, 0);
        return CodeCheckUtils.transform2ResponseObject(ruleRankList);
    }

    private ResponseObject generateFakeResponseObject() {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setError("");
        responseObject.setStatus("success");

        ResultObject result = new ResultObject();
        result.setTotal("3");
        List<RuleRank> ruleRankList = new ArrayList<>();

        RuleRank ruleRank1 = new RuleRank();
        ruleRank1.setRuleName("rule 1");
        ruleRank1.setPriority(10);
        ruleRankList.add(ruleRank1);
        RuleRank ruleRank2 = new RuleRank();
        ruleRank2.setRuleName("rule 2");
        ruleRank2.setPriority(100);
        ruleRankList.add(ruleRank2);

        RuleRank ruleRank3 = new RuleRank();
        ruleRank3.setRuleName("rule 3");
        ruleRank3.setPriority(100);
        ruleRankList.add(ruleRank3);

        result.setInfo(ruleRankList);
        responseObject.setResult(result);
        return responseObject;
    }

}
