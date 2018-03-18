package com.thesys.base.sysmag.acluser.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.core.dto.JqGridResultDataDto;

public interface AclUserService extends BaseService<AclUser> {
	/**
	 * 超级用户
	 */
	public static final String ADMIN = "admin";
	/**
	 * 判断用户是否是超级用户
	 * @param userId
	 * @return
	 */
	public boolean doOpChargeIsSuperUser(String userId);
	/**
	 * 根据id 更新用户状态
	 * @param id
	 * @param event
	 */
	public void updateAclUserStatus(String id,IEvent event);
	/**
	 * 启用用户
	 * @param id
	 */
	public void enableAclUser(Integer id);
	/**
	 * 注销用户
	 * @param id
	 */
	public void logoutAclUser(Integer id);		
	/**
	 * 根据用户id查找用户信息
	 * @param id
	 * @return
	 */
	public AclUser findAclUserById(String id);
	/**
	 * 根据用户的name 查找用户信息
	 * @param name
	 * @return
	 */
	public AclUser findAclUserByName(String name);
	
	/**
	 * 用于登录的查找
	 * @param name
	 * @return
	 */
	public AclUser findLoginAclUserByName(String name);
	
	
	/**
	 * 根据人员姓名查找用户信息
	 * @param name
	 * @return
	 */
	public AclUser findAclUserByPersonName(String name);
	/**
	 * 分页查询用户列表
	 * @param aclUser
	 * @param flexiPageDto
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageAclUsers(AclUser aclUser,Integer pageSize, Integer pageNumber);
	/**
	 * 根据用户的Id查询有权限的模块
	 * 一个用户对应的角色有对应的模块的AUTHORITY_ISREAD("read","读")权限就判断用户用对应的模块的权限
	 * @param aclUserId
	 * @return
	 */
	public List<AclModule> findAclUserHasRightAclModule(String aclUserId);
	
	/**
	 * 根据用户 name查询所有有权限的按钮
	 * @param uname
	 * @return
	 */
	public List<AclButton> findAclButtons(String uname);
	
	/**
	 * 修改密码
	 * @param password
	 */
	public void updatePassWord(String password,String userName);
}
