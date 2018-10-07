package com.emu.apps.qcm.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by eric on 19/06/2017.
 */
@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="Exception occurred in api")
public class ApiException {

}
