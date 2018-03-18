package com.thesys.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.context.ApplicationContextHolder;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.service.AclUserService;

public class UserContainerSessionUtil {
	//在线的用户ID集合
	private static List<String> onlineMemberIds = null;
	
	//单件模式
	public static List<String> getOnlineMemberIds() {
		if(null == onlineMemberIds){
			synchronized (UserContainerSessionUtil.class) {
				if(null == onlineMemberIds){
					onlineMemberIds = Collections.synchronizedList(new ArrayList<String>());
				}
			}
		}
		return onlineMemberIds;
	}
	
	/**
	 * 匿名用户，未登录
	 */
	private final static String anonymousUser = "anonymousUser";
	
	/**
	 * 获取当前登录用户
	 */
	public static AclUser getAclUser() {
		String name = null;
		if(ValidateUtil.isEmpty(SecurityContextHolder.getContext().getAuthentication())|| !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
			return null;
		}
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)
			name = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		else
			name = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (anonymousUser.equals(name)) {			
			return null;
		}
		
		// 返回的json包括当前用户
		AclUser aclUser = getAclUserService().findAclUserByName(name);
		return aclUser;
	}
	
	public static void doOpDealAddEntityUserAndDateInfo(BaseEntity entity){
		AclUser acluser=getAclUser();
		if(!ValidateUtil.isEmpty(acluser)){
			
			String name = ValidateUtil.isEmpty("personName", acluser)?acluser.getName():acluser.getPersonName();
			
			entity.setCreateBy_id(acluser.getId());
			entity.setCreateByName(name);

			entity.setLastModifyBy_id(acluser.getId());
			entity.setLastModifyByName(name);
		}
		entity.setCreateTime(new Date());
		entity.setLastModifyTime(new Date());
		entity.setVersion(0);
	}
	
	public static void doOpDealUpdateEntityUserAndDateInfo(BaseEntity entity){
		AclUser acluser=getAclUser();
		if(!ValidateUtil.isEmpty(acluser)){
			String name = ValidateUtil.isEmpty("personName", acluser)?acluser.getName():acluser.getPersonName();
			
			entity.setLastModifyBy_id(acluser.getId());
			entity.setLastModifyByName(name);
		}
		entity.setLastModifyTime(new Date());
	}
	
	private static AclUserService getAclUserService() {
		return (AclUserService) ApplicationContextHolder.getBean("aclUserService");
	}	

}
