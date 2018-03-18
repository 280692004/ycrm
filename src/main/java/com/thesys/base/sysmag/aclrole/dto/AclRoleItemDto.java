package com.thesys.base.sysmag.aclrole.dto;

import com.thesys.architecture.base.entity.BaseEntity;

@SuppressWarnings("serial")
public class AclRoleItemDto extends BaseEntity{

	public AclRoleItemDto(){
	}
	
	public AclRoleItemDto(Integer id,String cname,String remark,Boolean checked){
		this.setId(id);
		this.cname = cname;
		this.remark = remark;
		this.checked = checked;
	}
	
	/**
	 * 角色中文名称
	 */
	private String cname;	
	
	/**
	 * 备注 
	 */
	private String remark;
	
	/**
	 * 是否被选中
	 */
	private Boolean checked;

	public String getCname() {
		return cname;
	}

	public AclRoleItemDto setCname(String cname) {
		this.cname = cname;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public AclRoleItemDto setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public Boolean getChecked() {
		return checked;
	}

	public AclRoleItemDto setChecked(Boolean checked) {
		this.checked = checked;
		return this;
	}
}
