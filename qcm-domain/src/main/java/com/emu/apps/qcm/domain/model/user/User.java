package com.emu.apps.qcm.domain.model.user;

import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User extends DomainId {

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

    public User(String email) {
        this.email = email;
    }

}
