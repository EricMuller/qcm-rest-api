package com.emu.apps.qcm.web.rest.dtos;

import io.swagger.annotations.*;

/**
 * Created by eric on 14/06/2017.
 */
@ApiModel(value = "Message")
public class MessageDto {

    String message;

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
