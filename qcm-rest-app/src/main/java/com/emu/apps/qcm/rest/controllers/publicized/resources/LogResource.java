package com.emu.apps.qcm.rest.controllers.publicized.resources;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.security.Principal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Log")
@ToString()
public class LogResource {

    private String remoteHost;

    private String remoteAddr;

    private String message;

    private String[] additional;

    private Integer level;

    private ZonedDateTime timestamp;

    private String fileName;

    private String lineNumber;

}
