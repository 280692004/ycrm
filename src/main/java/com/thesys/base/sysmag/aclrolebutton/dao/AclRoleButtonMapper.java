package com.thesys.base.sysmag.aclrolebutton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.thesys.architecture.dao.BaseMapper;
import com.thesys.base.sysmag.aclrolebutton.domain.AclRoleButton;

public interface AclRoleButtonMapper extends BaseMapper<AclRoleButton> {
	/**
	 * 根据Id查询
	 */
	public AclRoleButton findEntityById(@Param("id")Integer id);
	/**
	 * 批量增加
	 * @param details
	 */
	public void addAclRoleButton(@Param("details")List<AclRoleButton> details);
	/**
	 * 根据角色Id和按钮Id查询AclRoleButton
	 * @param aclRoleButton
	 * @return
	 */
	@ResultMap("AclRoleButtonResultMap")
	public AclRoleButton findAclRoleButton(@Param("aclRoleButton")AclRoleButton aclRoleButton);
	/**
	 * 根据角色Id 和模块Id查询
	 * @param aclRoleButton
	 * @return
	 */
	@ResultMap("AclRoleButtonResultMap")
	public List<AclRoleButton> findModuleAclRoleButton(@Param("aclRoleButton")AclRoleButton aclRoleButton);
	/**
	 * 根据按钮的Id删除对应的角色按钮列表
	 * @param buttonId
	 */
	public void deleteAclRoleButtonByButtonId(@Param("buttonId")Integer buttonId);
	/**
	 * 
	 * @param aclroleId
	 * @param aclmoduleId
	 * @param butIds
	 */
	public void deleteByRoleIdAndModuleIdAndbutIds(@Param("aclroleId")String aclroleId,@Param("aclmoduleId")String aclmoduleId, @Param("butIds")String butIds);	
}
