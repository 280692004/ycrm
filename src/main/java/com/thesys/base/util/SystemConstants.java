package com.thesys.base.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.thesys.base.core.util.ValidateUtil;


public class SystemConstants {
	
	//存放登录用户的session名称
	public static final String SESSIONKEY_USER = "sessionkey_user";
	//存放登录用户公司的session名称
	public static final String SESSIONKEY_USER_ENTERPRISE = "sessionkey_user_enterprise";
	
	private static final Integer ICZOOM_UNITPRICE_DECIMALLENGTH=6;
	
	public static Integer getUnitPriceDecimalLength(){
		return ICZOOM_UNITPRICE_DECIMALLENGTH;
	}
	
	public static Boolean hasLogin(){
		String uname=SecurityContextHolder.getContext().getAuthentication().getName();
		return !ValidateUtil.isEmpty(uname)&&!SPRING_SECURITY_ANONYMOUSUSER.equals(uname);
		
	}
	
	public static final String SPRING_SECURITY_ANONYMOUSUSER="anonymousUser";
}
