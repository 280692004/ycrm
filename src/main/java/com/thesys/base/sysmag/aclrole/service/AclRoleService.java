package com.thesys.base.sysmag.aclrole.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.core.dto.JqGridResultDataDto;

public interface AclRoleService extends BaseService<AclRole> {
	/**
	 * 作废一个角色
	 * @param roleId
	 */
	public void logoutAclRole(Integer roleId);
	/**
	 * 激活一个角色
	 * @param roleId
	 */
	public void enableAclRole(Integer roleId);
	/**
	 * 更新角色的状态
	 * @param roleId
	 */
	public void updateAclRoleStatus(String roleId,IEvent event);
	/**
	 * 分页查询角色列表
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageAclRoles(AclRole aclRole,Integer pageSize, Integer pageNumber);
	/**
	 * 1、如果用户是全局用户就查询全部的角色出来
	 * 2、如果用户是局部用户就只能查询自己创建的用户
	 * @param aclUser
	 * @return
	 */
	public List<AclRole> findAclRole(AclUser aclUser);
}
