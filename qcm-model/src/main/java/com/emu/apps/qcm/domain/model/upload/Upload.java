package com.emu.apps.qcm.domain.model.upload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class Upload {

    private String uuid;

    private ZonedDateTime dateCreation;

    @JsonProperty("contentType")
    private String contentType;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private byte[] data;

    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("type")
    private String type;

}
