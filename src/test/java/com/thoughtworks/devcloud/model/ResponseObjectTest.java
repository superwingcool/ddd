package com.thoughtworks.devcloud.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ResponseObject}.
 */
public class ResponseObjectTest {
    @Test
    public void shouldInitializeResultInsteadOfNull() {
      ResponseObject response = new ResponseObject();

      Assert.assertNotNull(response.getResult());
    }
}
