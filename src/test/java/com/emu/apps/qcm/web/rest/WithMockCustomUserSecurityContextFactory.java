package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.services.entity.UserProfile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
	implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		UserDetails principal =	new UserProfile(customUser.username());
		context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities()));
		return context;
	}
}