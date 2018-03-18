package com.thesys.base.sysmag.aclrolebutton.domain;

import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.core.annotation.ManyToOne;
import com.thesys.architecture.base.entity.BaseEntity;
/**
 * 角色权限
 * @author: kyle
 * @createTime：2015-02-14 下午06:27:54
 *
 * @lastUpdateAuthor: (修改代码后请输入)
 * @lastUpdateTime: (修改代码后请输入)
 */
@SuppressWarnings("serial")
@BaseEntityMapper(tableName="base_aclrolebutton")
public class AclRoleButton extends BaseEntity {
	/**
	 * 角色Id
	 */
	private Integer role_id;
	/**
	 * 角色
	 */
	@ManyToOne(name="role_id")
	private AclRole aclRole;
	/**
	 * 模块Id
	 */
	private Integer module_id;
	/**
	 * 模块
	 */
	@ManyToOne(name="module_id")
	private AclModule aclModule;		
	
	/**
	 * 按钮Id
	 */
	private Integer button_id;
	/**
	 * 按钮
	 */
	@ManyToOne(name="button_id")
	private AclButton aclButton;		
	/**
	 * 权限
	 */
	private Integer permission;
	
	public AclRoleButton() {
	}

	public AclRoleButton(Integer role_id, Integer module_id) {
		this.role_id = role_id;
		this.module_id = module_id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public AclRoleButton setRole_id(Integer roleId) {
		role_id = roleId;
		return this;
	}

	public AclRole getAclRole() {
		return aclRole;
	}

	public AclRoleButton setAclRole(AclRole aclRole) {
		this.aclRole = aclRole;
		return this;
	}

	public Integer getButton_id() {
		return button_id;
	}

	public AclRoleButton setButton_id(Integer buttonId) {
		button_id = buttonId;
		return this;
	}

	public AclButton getAclButton() {
		return aclButton;
	}

	public AclRoleButton setAclButton(AclButton aclButton) {
		this.aclButton = aclButton;
		return this;
	}

	public Integer getPermission() {
		return permission;
	}

	public AclRoleButton setPermission(Integer permission) {
		this.permission = permission;
		return this;
	}

	public Integer getModule_id() {
		return module_id;
	}

	public AclRoleButton setModule_id(Integer moduleId) {
		module_id = moduleId;
		return this;
	}

	public AclModule getAclModule() {
		return aclModule;
	}

	public AclRoleButton setAclModule(AclModule aclModule) {
		this.aclModule = aclModule;
		return this;
	}
}
