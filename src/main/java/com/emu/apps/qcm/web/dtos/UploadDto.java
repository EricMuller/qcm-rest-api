package com.emu.apps.qcm.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadDto {

    private Long id;

    private String fileName;

    private LocalDateTime dateCreation;
}
