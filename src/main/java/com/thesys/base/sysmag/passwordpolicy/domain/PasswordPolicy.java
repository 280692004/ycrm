package com.thesys.base.sysmag.passwordpolicy.domain;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
@SuppressWarnings("all")
@BaseEntityMapper(tableName = "base_passwordpolicy")
public class PasswordPolicy extends BaseEntity {
	private String name;
	private Integer activeday;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getActiveday() {
		return activeday;
	}
	public void setActiveday(Integer activeday) {
		this.activeday = activeday;
	}
	  

}
