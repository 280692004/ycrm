package com.thesys.common.security.token;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.thesys.architecture.core.util.CusAccessObjectUtil;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String SPRING_SECURITY_FORM_DATABASETYPE_KEY = "j_database";
	public static final String SPRING_SECURITY_DB_NAME="db";
	private String databaseNameParameter = SPRING_SECURITY_FORM_DATABASETYPE_KEY;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
		
		//解决中文诗句的post乱码问题
		try{
			request.setCharacterEncoding("UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String username = obtainUsername(request).toUpperCase().trim();
		String password = obtainPassword(request);
		
		String ip=CusAccessObjectUtil.getIpAddress(request);
		System.out.println(ip);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}

    protected String obtainDataBaseName(HttpServletRequest request) {
        return request.getParameter(databaseNameParameter);
    }

}
