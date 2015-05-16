package com.twistlet.falcon.model.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Override
	public String getCurrentUserId() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		return authentication.getName();
	}

	@Override
	public boolean isCurrentUserInRole(String role) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
		for (GrantedAuthority item : list) {
			if (StringUtils.equals(role, item.getAuthority())) {
				return true;
			}
		}
		return false;
	}

}
