package com.thesys.base.sysmag.aclmodule.dto;


import java.util.ArrayList;
import java.util.List;

import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.architecture.base.entity.BaseEntity;

@SuppressWarnings("serial")
public class AclModuleDto extends BaseEntity {
	
	public AclModuleDto(){		
	}

	public AclModuleDto(List<ParameterBaseBean> statuses){	
		this.statuses = statuses;
	}
	
	public AclModuleDto(Integer orderNo){	
		this.orderNo = orderNo;
	}	
	/**
	 * 状态
	 */
	private List<ParameterBaseBean> statuses = new ArrayList<ParameterBaseBean>();
	/**
	 * 
	 */
	private AclModule  aclModule;
	/**
	 * 序号
	 */
	private Integer orderNo;
	/**
	 *父级的id间隔
	 */
	private List<String> parentIds;

	public List<ParameterBaseBean> getStatuses() {
		return statuses;
	}

	public AclModuleDto setStatuses(List<ParameterBaseBean> statuses) {
		
		this.statuses = statuses;
		return this;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public AclModuleDto setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
		return this;
	}

	public List<String> getParentIds() {
		return parentIds;
	}

	public AclModuleDto setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
		return this;
	}

	public AclModule getAclModule() {
		return aclModule;
	}

	public AclModuleDto setAclModule(AclModule aclModule) {
		this.aclModule = aclModule;
		return this;
	}
}
