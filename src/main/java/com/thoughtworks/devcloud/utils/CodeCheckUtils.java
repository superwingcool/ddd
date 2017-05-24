package com.thoughtworks.devcloud.utils;

import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.AbstractRank;
import com.thoughtworks.devcloud.model.ResponseObject;
import com.thoughtworks.devcloud.model.ResultObject;
import org.springframework.util.CollectionUtils;

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
    public static ResponseObject transform2ResponseObject(List<? extends AbstractRank> ruleRankList, Long repoCheckCount) {
        ResponseObject responseObject = new ResponseObject();
        if (ruleRankList == null) {
            return responseObject;
        }

        ResultObject resultObject = new ResultObject();
        resultObject.setTotal(String.valueOf(ruleRankList.size()));
        updateRank(ruleRankList);
        resultObject.setInfo(ruleRankList);
        resultObject.setRepoCheckedCount(String.valueOf(repoCheckCount));

        responseObject.setResult(resultObject);
        return responseObject;
    }

    public static ResponseObject transform2ResponseObject(ResultObject<? extends AbstractRank> result){
        ResponseObject responseObject = new ResponseObject();
        if (result == null) {
            return responseObject;
        }
        updateRank(result.getInfo());
        responseObject.setResult(result);
        return responseObject;
    }

    /**
     * Sort the ruleRank in the list desc, and update the rank value.
     *
     * @return sorted rule rank list
     */
    public static void updateRank(List<? extends AbstractRank> rankList) {
        Collections.sort(rankList, Collections.reverseOrder());

        for (int i = 0; i < rankList.size(); i ++) {
            AbstractRank rank = rankList.get(i);
            rank.setRank(i + 1);
        }
    }

    public static void getNullThrowException(List list){
        if(CollectionUtils.isEmpty(list)){
            throw new NullObjectException();
        }
    }
}
