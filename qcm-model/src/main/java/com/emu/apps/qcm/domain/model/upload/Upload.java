package com.emu.apps.qcm.domain.model.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Upload {

    private String uuid;

    private ZonedDateTime dateCreation;


    private String contentType;


    private String fileName;


    private String status;


    private byte[] data;


    private String libelle;


    private String type;

}
