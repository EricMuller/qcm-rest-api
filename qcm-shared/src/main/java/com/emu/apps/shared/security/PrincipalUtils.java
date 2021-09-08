package com.emu.apps.shared.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

public final class PrincipalUtils {

    public static final String USER_NAME_ATTRIBUTE = "preferred_username";
    public static final String EMAIL_ATTRIBUTE = "email";

    private PrincipalUtils() {
    }

    public static String getAttribute(Object principal, String field) {

        if (principal instanceof Jwt) {
            return ((Jwt) principal).getClaim(field);
        } else if (principal instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) principal).getTokenAttributes().get(field).toString();
        }
        return null;
    }

    public static String getEmailOrName(Object principal) {

        if (principal instanceof Jwt) {
            return ((Jwt) principal).getClaim(EMAIL_ATTRIBUTE);
        } else if (principal instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) principal).getTokenAttributes().get(EMAIL_ATTRIBUTE).toString();
        } else if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else if (principal instanceof Principal) {
            return ((Principal) principal).getName();
        }
        return null;
    }

}
