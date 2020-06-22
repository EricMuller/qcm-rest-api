package com.emu.apps.shared.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

public class PrincipalUtils {

    private PrincipalUtils() {
    }

    public static String getEmail(Principal principal) {

        if (principal instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) principal).getTokenAttributes().get("email").toString();
        } else {
            return principal.getName();
        }
    }


}
