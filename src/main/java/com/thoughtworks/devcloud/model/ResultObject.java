package com.thoughtworks.devcloud.model;

import com.thoughtworks.devcloud.exception.NullObjectException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.ArrayList;


/**
 * Result object for rule rank.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject<T> {

    private static final long serialVersionUID = 1L;

    /** total count of info list**/
    private String total = "0";

    private List<T> info = new ArrayList<T>();

    /** count of repo be checked**/
    private String repoCheckedCount = "0";

    /** count of project be checked**/
    private String projectCount = "0";



}
