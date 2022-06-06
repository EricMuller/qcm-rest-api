package com.emu.apps.qcm.rest.controllers.management.resources;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName(value = "Action")
@AllArgsConstructor
public class ActionResource {

    private String uuid;

    private String action;

    private String description;

}
