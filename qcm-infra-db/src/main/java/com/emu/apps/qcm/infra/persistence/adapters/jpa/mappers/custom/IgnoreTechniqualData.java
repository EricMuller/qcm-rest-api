package com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.custom;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "uuid", ignore = true)
@Mapping(target = "dateCreation", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "dateModification", ignore = true)
@Mapping(target = "lastModifiedBy", ignore = true)
public @interface IgnoreTechniqualData {


}
