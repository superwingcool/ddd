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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    private String error;

    private ResultObject result;
}
