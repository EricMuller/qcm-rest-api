package com.emu.apps.qcm.web.dtos;

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
public class UploadDto {

    private Long id;

    private String fileName;

    private LocalDateTime dateCreation;

    @JsonProperty("status")
    private String status;

    private byte[] data;

    @JsonProperty("contentType")
    private String contentType;

}
