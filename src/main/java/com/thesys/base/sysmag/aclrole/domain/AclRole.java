package com.thesys.base.sysmag.aclrole.domain;

import java.util.Date;

import com.thesys.base.sysmag.aclrole.domain.state.AclRoleClassDataManager;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.base.entity.BaseEntity;


/**
 * 角色
 * @author kyle
 */
@SuppressWarnings("serial")

@BaseEntityMapper(tableName="base_aclrole")
public class AclRole extends BaseEntity {
	/**
	 * 
	 */
	private String code;
	/**
	 * 角色中文名称
	 */
	private String cname;	
	/**
	 * 过期日期
	 */
	private Date expirateDate;
	
	/**
	 * 备注 
	 */
	private String remark;
	
	/**
	 * 状态
	 */
	private String status;
	
	public AclRole(){		
	}
	
	public AclRole(Integer id) {
		super(id);		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCname() {
		return cname;
	}
	public AclRole setCname(String cname) {
		this.cname = cname;
		return this;
	}

	public Date getExpirateDate() {
		return expirateDate;
	}

	public void setExpirateDate(Date expirateDate) {
		this.expirateDate = expirateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusName(){
		return AclRoleClassDataManager.getStatusName(this.status);
	}
}

