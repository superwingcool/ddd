package com.thoughtworks.devcloud.utils;

import com.thoughtworks.devcloud.constants.CodeCheckConstants;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.ResultObject;
import com.thoughtworks.devcloud.model.RuleRank;

import java.util.Collections;
import java.util.List;


/**
 * Utils for code check.
 */
public class CodeCheckUtils {
    /**
     * Transforms rule rank list info final response object.
     *
     * @param ruleRankList ruleRankList
     * @param repoCheckCount repo checked count
     * @return response object
     */
    public static ResponseObject transform2ResponseObject(List<RuleRank> ruleRankList, Long repoCheckCount) {
        ResponseObject responseObject = new ResponseObject();
        if (ruleRankList == null) {
            responseObject.setError("");
            return responseObject;
        }

        responseObject.setStatus(CodeCheckConstants.RESPONSE_CODE_SUCCESS);
        ResultObject resultObject = new ResultObject();
        resultObject.setTotal(String.valueOf(ruleRankList.size()));
        resultObject.setInfo(updateRank(ruleRankList));
        resultObject.setRepoCheckedCount(String.valueOf(repoCheckCount));

        responseObject.setResult(resultObject);
        return responseObject;
    }

    /**
     * Sort the ruleRank in the list desc, and update the rank value.
     *
     * @param ruleRankList to be sorted
     * @return sorted rule rank list
     */
    public static List<RuleRank> updateRank(List<RuleRank> ruleRankList) {
        Collections.sort(ruleRankList, Collections.reverseOrder());

        for (int i = 0; i < ruleRankList.size(); i ++) {
            RuleRank ruleRank = ruleRankList.get(i);
            ruleRank.setRank(i + 1);
        }
        return ruleRankList;
    }
}
