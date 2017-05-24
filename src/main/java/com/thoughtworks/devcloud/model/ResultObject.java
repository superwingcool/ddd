package com.thoughtworks.devcloud.model;

import com.thoughtworks.devcloud.exception.NullObjectException;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


/**
 * Result object for rule rank.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject<T> implements Serializable{

    private static final long serialVersionUID = 1457795069278505305L;

    /** total count of info list**/
    private String total = "0";

    private List<T> info = new ArrayList<T>();

    /** count of repo be checked**/
    private String repoCheckedCount = "0";

    /** count of project be checked**/
    private String projectCount = "0";



}
