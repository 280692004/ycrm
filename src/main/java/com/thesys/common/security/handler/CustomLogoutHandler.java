package com.thesys.common.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.thesys.architecture.core.util.ThreadLocalContextUtil;

public class CustomLogoutHandler implements LogoutHandler{

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,Authentication authentication) {
		ThreadLocalContextUtil.clearContext();
		System.out.println("CustomLogoutSuccessHandler.logout() is called!");
	}
}
