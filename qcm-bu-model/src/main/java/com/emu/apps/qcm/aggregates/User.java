package com.emu.apps.qcm.aggregates;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private String uuid;

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

    public User(String email) {
        this.email = email;
    }
}
