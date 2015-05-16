package com.twistlet.falcon.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.twistlet.falcon.security.service.SecurityContextService;

public class MainController extends AbstractController {

	private final SecurityContextService securityContextService;
	private final Map<String, String> map;

	public MainController(final SecurityContextService securityContextService,
			final Map<String, String> map) {
		this.securityContextService = securityContextService;
		this.map = Collections.unmodifiableMap(map);
	}

	@Override
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		final List<GrantedAuthority> list = securityContextService
				.getAuthorities();
		final List<String> grantedAuthorityList = new ArrayList<>();
		for (final GrantedAuthority grantedAuthority : list) {
			grantedAuthorityList.add(grantedAuthority.toString());
		}
		final Set<String> roleSet = map.keySet();
		for (final String role : roleSet) {
			if (grantedAuthorityList.contains(role)) {
				final String view = map.get(role);
				final ModelAndView mav = new ModelAndView(view);
				return mav;
			}
		}
		return null;
	}
}
