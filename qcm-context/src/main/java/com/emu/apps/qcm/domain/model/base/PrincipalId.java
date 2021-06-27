package com.emu.apps.qcm.domain.model.base;


public class PrincipalId extends DomainObjectId {
    public PrincipalId(String uuid) {
        super(uuid);
    }

    public static PrincipalId of(String uuid) {
        return new PrincipalId(uuid);
    }
}
