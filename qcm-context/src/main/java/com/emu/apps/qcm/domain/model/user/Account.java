package com.emu.apps.qcm.domain.model.user;

import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends DomainId<AccountId> {

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

    public Account(String email) {
        this.email = email;
    }

}
