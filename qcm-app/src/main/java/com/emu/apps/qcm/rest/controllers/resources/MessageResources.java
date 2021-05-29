package com.emu.apps.qcm.rest.controllers.resources;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 14/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Message")
public class MessageResources {

    public static final String OK = "OK";

    public static final String ERROR = "KO";

    private String status;

    private String message;

    private String data;

    public MessageResources(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
