
package com.thesys.base.sysmag.aclrole.dto;

import java.util.ArrayList;
import java.util.List;

import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;
import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.architecture.base.entity.BaseEntity;

@SuppressWarnings("serial")
public class AclRoleDto extends BaseEntity {
	public AclRoleDto(){		
	}

	public AclRoleDto(List<ParameterBaseBean> statuses){	
		this.statuses = statuses;
	}
	
	/**
	 * 状态
	 */
	private List<ParameterBaseBean> statuses = new ArrayList<ParameterBaseBean>();
	/**
	 * 角色
	 */
	private AclRole aclRole;

	public List<ParameterBaseBean> getStatuses() {
		return statuses;
	}

	public AclRoleDto setStatuses(List<ParameterBaseBean> statuses) {
		this.statuses = statuses;
		return this;
	}

	public AclRole getAclRole() {
		return aclRole;
	}

	public AclRoleDto setAclRole(AclRole aclRole) {
		this.aclRole = aclRole;
		return this;
	}
}
