package com.emu.apps.qcm.rest.controllers.secured.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.ZonedDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Upload")
public class UploadResource extends RepresentationModel <UploadResource> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UploadResource that = (UploadResource) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uuid);
    }
}
