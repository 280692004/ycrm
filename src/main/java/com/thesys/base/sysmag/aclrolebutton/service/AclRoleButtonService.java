package com.thesys.base.sysmag.aclrolebutton.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.sysmag.aclrolebutton.domain.AclRoleButton;

public interface AclRoleButtonService extends BaseService<AclRoleButton>{
	
	/**
	 * 批量增加
	 * @param details
	 */
	public void addAclRoleButton(List<AclRoleButton> details);
	/**
	 * 根据角色和模块查询
	 * @param aclRoleButton
	 * @return
	 */
	public List<AclRoleButton> findModuleAclRoleButton(AclRoleButton aclRoleButton);
	/**
	 * 根据按钮的Id删除对应的角色按钮列表
	 * @param buttonId
	 */
	public void deleteAclRoleButtonByButtonId(Integer buttonId);
	/**
	 * 分配权限时删除去掉的权限按钮
	 * @param aclroleId
	 * @param aclmoduleId
	 * @param butids 按钮ids 格式 '1,5,3'
	 */
	public void deleteByRoleIdAndModuleIdAndbutIds(String aclroleId,String aclmoduleId, String butids);
}
