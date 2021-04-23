package com.emu.apps.qcm.infra.webmvc.rest.dtos;

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
public class MessageDto {

    public static final String OK = "OK";

    public static final String ERROR = "KO";

    private String status;

    private String message;

    private String data;

    public MessageDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
