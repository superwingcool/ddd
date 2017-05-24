package com.thoughtworks.devcloud.model;

import lombok.*;

import java.io.Serializable;


/**
 * Standard response object for rule ranking.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject implements Serializable{

    private static final long serialVersionUID = 3597727200631815050L;

    private ResultObject result = new ResultObject();
}
