package com.emu.apps.shared.security;

public final class AuthentificationContextHolder {

    private static ThreadLocal <String> context = new ThreadLocal();

    public static String getUser() {
        return context.get();
    }
    public static void setUser(String user) {
         context.set(user);
    }
}
