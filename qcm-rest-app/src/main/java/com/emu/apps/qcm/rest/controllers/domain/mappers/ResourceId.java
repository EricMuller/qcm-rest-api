package com.emu.apps.qcm.rest.controllers.domain.mappers;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "uuid", source = "id")
public @interface ResourceId {

}
