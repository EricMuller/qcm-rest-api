package com.emu.apps.qcm.domain.model.base;


import java.security.Principal;

public class PrincipalId extends DomainObjectId {

    public PrincipalId(String uuid) {
        super(uuid);
    }

    public static PrincipalId of(Principal principal) {
        return new PrincipalId(principal.getName());
    }
}
