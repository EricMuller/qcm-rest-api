package com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "responses", ignore = true)
public @interface IgnoreResponses {


}
