package com.emu.apps.qcm.rest.controllers.services;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Task {

    private String uuid;
    private String status;


    private String statusUrl;
    
    private String type;

    private String id;


    public Task(String uuid, String status) {
        this.uuid = uuid;
        this.status = status;
    }
}
