package com.emu.apps.shared.security;

import java.security.Principal;

public final class AccountContextHolder<T extends Principal> {

    private static ThreadLocal <Principal> context = new ThreadLocal <>();

    private AccountContextHolder() {
    }

    public static Principal getPrincipal() {
        return context.get();
    }

    public static Principal setPrincipal(Principal user) {
        context.set(user);
        return context.get();
    }



}
