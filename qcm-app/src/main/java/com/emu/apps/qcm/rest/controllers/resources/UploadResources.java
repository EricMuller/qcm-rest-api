package com.emu.apps.qcm.rest.controllers.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Upload")
public class UploadResources {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("dateCreation")
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
