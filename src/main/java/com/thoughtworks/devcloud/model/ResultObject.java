package com.thoughtworks.devcloud.model;

import com.thoughtworks.devcloud.exception.NullObjectException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Result object for rule rank.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject<T> {

    private static final long serialVersionUID = 1L;

    /** total count of info list**/
    private String total;

    private List<T> info;

    /** count of repo be checked**/
    private String repoCheckedCount;

    /** count of project be checked**/
    private String projectCount;



}
