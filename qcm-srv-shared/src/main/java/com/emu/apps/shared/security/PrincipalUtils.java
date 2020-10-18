package com.emu.apps.shared.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

public final class PrincipalUtils {

    private PrincipalUtils() {
    }

    public static String getEmailOrName(Object principal) {

        if (principal instanceof Jwt) {
            return ((Jwt) principal).getClaim("email");
        } else if (principal instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) principal).getTokenAttributes().get("email").toString();
        } else if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else if (principal instanceof Principal) {
            return ((Principal) principal).getName();
        }
        return null;
    }

}
