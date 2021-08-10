package com.emu.apps.qcm.domain.model.user;

import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends DomainId<AccountId> implements Principal {

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

    public Account(String email) {
        this.email = email;
    }

    public Account(AccountId accountId) {
        this.setId(accountId);
    }

    @Override
    public String getName() {
        return getId().toUuid();
    }
}
