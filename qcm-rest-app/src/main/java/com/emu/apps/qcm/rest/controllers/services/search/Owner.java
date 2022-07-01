package com.emu.apps.qcm.rest.controllers.services.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

import static com.emu.apps.qcm.rest.controllers.domain.resources.DateConstants.ZONED_DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "Owner")
public class Owner {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("dateCreation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("userName")
    private String userName;

}
