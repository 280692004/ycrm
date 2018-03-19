package com.thesys.base.sysmag.acluser.dto;


import java.util.ArrayList;
import java.util.List;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;

@SuppressWarnings("serial")
public class AclUserDto extends BaseEntity {
	
	public AclUserDto(){		
	}

	public AclUserDto(List<ParameterBaseBean> statuses,
			List<ParameterBaseBean> types, List<ParameterBaseBean> activedays) {
		super();
		this.statuses = statuses;
		this.types = types;
		this.activedays = activedays;
	}



	/**
	 * 状态
	 */
	private List<ParameterBaseBean> statuses = new ArrayList<ParameterBaseBean>();
	/**
	 * 用户类型
	 */
	private List<ParameterBaseBean> types=new ArrayList<ParameterBaseBean>();
	/***
	 * 有效期
	 */
	private List<ParameterBaseBean> activedays=new ArrayList<ParameterBaseBean>();

	public List<ParameterBaseBean> getTypes() {
		return types;
	}

	public void setTypes(List<ParameterBaseBean> types) {
		this.types = types;
	}

	public List<ParameterBaseBean> getStatuses() {
		return statuses;
	}

	public AclUserDto setStatuses(List<ParameterBaseBean> statuses) {
		
		this.statuses = statuses;
		return this;
	}
	public List<ParameterBaseBean> getActivedays() {
		return activedays;
	}
	public void setActivedays(List<ParameterBaseBean> activedays) {
		this.activedays = activedays;
	}
	
}
