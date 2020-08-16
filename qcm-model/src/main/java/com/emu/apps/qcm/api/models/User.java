package com.emu.apps.qcm.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class User {

    private String uuid;

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

}
