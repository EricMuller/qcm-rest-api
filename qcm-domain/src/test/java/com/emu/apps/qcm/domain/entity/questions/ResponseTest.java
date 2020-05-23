package com.emu.apps.qcm.domain.entity.questions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {
    private Response pojoObject;

    public ResponseTest() throws Exception {
        this.pojoObject = new Response();
    }

    @Test
    public void testId() {
        Long param = Long.valueOf(123);
        pojoObject.setId(param);
        Object result = pojoObject.getId();
        assertEquals(param, result);
    }

    @Test
    public void testResponse() {
        String param = "123";
        pojoObject.setResponse(param);
        Object result = pojoObject.getResponse();
        assertEquals(param, result);
    }

    @Test
    public void testNumber() {
        Long param = Long.valueOf(123);
        pojoObject.setNumber(param);
        Object result = pojoObject.getNumber();
        assertEquals(param, result);
    }

    @Test
    public void testGood() {
        Boolean param = Boolean.valueOf(true);
        pojoObject.setGood(param);
        Object result = pojoObject.getGood();
        assertEquals(param, result);
    }

}

