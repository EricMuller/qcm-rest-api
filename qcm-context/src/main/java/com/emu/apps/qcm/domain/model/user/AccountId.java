package com.emu.apps.qcm.domain.model.user;


import com.emu.apps.qcm.domain.model.base.DomainObjectId;

public class AccountId extends DomainObjectId {
    public AccountId(String uuid) {
        super(uuid);
    }

    public static AccountId of(String uuid){
        return new AccountId(uuid);
    }
}
