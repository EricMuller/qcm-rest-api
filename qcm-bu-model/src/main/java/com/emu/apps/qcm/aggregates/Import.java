package com.emu.apps.qcm.aggregates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Import {

    private String uuid;

    private LocalDateTime dateCreation;

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private byte[] data;

    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("type")
    private String type;

}
