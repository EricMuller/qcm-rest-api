package com.emu.apps.qcm.domain.model.account;


import com.emu.apps.qcm.domain.model.base.DomainObjectId;

import java.util.UUID;

public class AccountId extends DomainObjectId {
    private  AccountId(String uuid) {
        super(uuid);
    }

    public static AccountId of(String uuid){
        return new AccountId(uuid);
    }

    public static AccountId of(UUID uuid){
        return new AccountId(uuid.toString());
    }
}
