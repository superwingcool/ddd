package com.thoughtworks.devcloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * Standard response object for rule ranking.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {

    private static final long serialVersionUID = 1L;

    private ResultObject result = new ResultObject();
}
