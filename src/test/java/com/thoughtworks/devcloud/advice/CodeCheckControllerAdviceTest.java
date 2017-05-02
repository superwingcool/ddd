package com.thoughtworks.devcloud.advice;

import com.thoughtworks.devcloud.exception.NullObjectException;
import com.thoughtworks.devcloud.model.ResponseObject;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;


public class CodeCheckControllerAdviceTest {

    @Test
    public void handleExceptionShouldReturnNullGivenException(){
        CodeCheckControllerAdvice c = new CodeCheckControllerAdvice();
        ResponseObject r = c.handleException(new NullObjectException(), null);
        assertThat(r, notNullValue());
        assertThat(r.getResult(), notNullValue());
    }

}