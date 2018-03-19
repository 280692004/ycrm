package com.thesys.base.sysmag.aclbutton.domain;

import javax.persistence.Transient;

import com.thesys.base.sysmag.aclbutton.domain.state.AclButtonClassDataManager;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.core.annotation.ManyToOne;
import com.thesys.architecture.base.entity.BaseEntity;
/**
 * 按钮
 * @author: alex.luo
 * @createTime：2015-12-14 下午05:58:58
 *
 * @lastUpdateAuthor: (修改代码后请输入)
 * @lastUpdateTime: (修改代码后请输入)
 */
@SuppressWarnings("serial")
@BaseEntityMapper(tableName="base_aclbutton")
public class AclButton extends BaseEntity {

	/**
	 * 模块Id
	 */
	private Integer module_id;
	/**
	 * 模块
	 */	
	@ManyToOne(name = "module_id")
	private AclModule module;
	/**
	 * 按钮的编码（在同一个模块内部，按钮的编码必须唯一，但是全局不唯一）
	 */
	private String code;	
	/**Bug
	 * 按钮名称
	 */
	private String cname;	
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 按钮图标
	 */
	private String iconName;
	/**
	 * 是否有权限
	 * @return
	 */
	@Transient
	private Boolean actionmethodright;

	public String getStatusName(){
		return AclButtonClassDataManager.getStatusName(this.getStatus());
	}

	public Integer getModule_id() {
		return module_id;
	}

	public AclButton setModule_id(Integer moduleId) {
		module_id = moduleId;
		return this;
	}

	public AclModule getModule() {
		return module;
	}

	public AclButton setModule(AclModule module) {
		this.module = module;
		return this;
	}

	public String getCode() {
		return code;
	}

	public AclButton setCode(String code) {
		this.code = code;
		return this;
	}

	public String getCname() {
		return cname;
	}

	public AclButton setCname(String cname) {
		this.cname = cname;
		return this;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getActionmethodright() {
		return actionmethodright;
	}

	public void setActionmethodright(Boolean actionmethodright) {
		this.actionmethodright = actionmethodright;
	}
	
}
