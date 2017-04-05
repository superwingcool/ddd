package com.thoughtworks.devcloud.controller;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.model.DuplicatedLineRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.service.CProjectsService;
import com.thoughtworks.devcloud.service.DuplicatedLineRankService;
import com.thoughtworks.devcloud.utils.CodeCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Duplicated line rank api.
 */
@RestController
@RequestMapping(value = "/duplicatedLineRank")
public class DuplicatedLineRankApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DuplicatedLineRankService duplicatedLineRankService;

    @Autowired
    private CProjectsService cProjectsService;

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(value = "/project/{devcloudProjectUuid}", method = RequestMethod.GET)
    public ResponseObject getViolatedRules(@PathVariable String devcloudProjectUuid) {
        logger.info(CodeCheckConstants.LOGGER_PREFIX +
                "Visit /duplicatedLineRank/project/{devcloudProjectUuid}, devcloudProjectUuid: " + devcloudProjectUuid);
        List<DuplicatedLineRank> duplicatedLineRankList =
                duplicatedLineRankService.findMeasureListByDevcloudProjectId(devcloudProjectUuid);
        Long repoCheckCount = cProjectsService.countDistinctByGitUrl(devcloudProjectUuid);
        return CodeCheckUtils.transform2ResponseObject(duplicatedLineRankList, repoCheckCount);
    }

}
