package com.emu.apps.qcm.web.rest.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel(value = "Upload")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadDto {

    private Long id;

    private String fileName;

    private LocalDateTime dateCreation;
}
