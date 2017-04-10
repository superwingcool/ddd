package com.thoughtworks.devcloud.advice;

import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice(basePackages = "com.thoughtworks.devcloud.controller")
public class CodeCheckControllerAdvice {

    @ExceptionHandler(NullObjectException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseObject handleException(NullObjectException exception, ServletWebRequest request) {
        return new ResponseObject();
    }


}