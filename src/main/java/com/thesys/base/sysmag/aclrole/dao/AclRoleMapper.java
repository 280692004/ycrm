package com.thesys.base.sysmag.aclrole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.architecture.dao.impl.BaseProvider;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.architecture.core.dto.CriteriaDto;
import com.thesys.architecture.core.dto.FlexiPageDto;

public interface AclRoleMapper extends BaseMapper<AclRole> {

	/**
	 * 根据角色名称查询角色
	 * @param cname
	 * @return
	 */
	@ResultMap("AclRoleResultMap")
	public List<AclRole> findAclRoleByCName(@Param("cname")String cname);
	/**
	 * 根据创建人查询角色
	 * @param aclUser
	 * @return
	 */
	@ResultMap("AclRoleResultMap")
	public List<AclRole> findAclRoleByCreateByAclUser(@Param("aclUser")AclUser aclUser);
	
	/**
	 * 根据Id查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityById")
	public AclRole findEntityById(CriteriaDto<AclRole> criteriaDto);	
	
	/**
	 * 查询分页条件的总记录数
	 */
	@ResultType(Long.class)
	public Long findFlexiPageAclRoleCount(@Param("aclRole") AclRole aclRole);
	
	/**
	 * 模块分页查询
	 * @param aclRole
	 * @param flexiPageDto
	 * @return
	 */
	@ResultMap("AclRoleResultMap")
	public List<AclRole> findFlexiPageAclRoles(@Param("aclRole") AclRole aclRole,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	
	/**
	 * 更新用户角色的状态
	 * @param roleId
	 * @param status
	 */
	public void updateAclRoleStatus(@Param("roleId")String roleId,@Param("status")String status);
	
	/**
	 * 作废一个角色
	 * @param roleId
	 */
	public void logoutAclRole(@Param("roleId")Integer roleId);
	/**
	 * 激活一个角色
	 * @param roleId
	 */
	public void enableAclRole(@Param("roleId")Integer roleId);	
}
