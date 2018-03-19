package com.thesys.base.sysmag.aclrole.domain.state;

import com.thesys.base.core.util.statemachine.IEvent;

public enum AclRoleEvent implements IEvent {
	/**
	 * 审核 N->Y
	 */
	CONFIRM("confirm", "event.confirm"),
	/**
	 * 作废
	 */
	INVALID("invalid","event.invalid"),
	/**
	 * 启用
	 */
	ENABLE("enable","event.enable");

	final private String naturalId;
	final private String description;
	private AclRoleEvent(String naturalId, String description) {
		this.naturalId = naturalId;
		this.description = description;
	}
	
	@Override
	public String getNaturalId() {
		return this.naturalId;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	public boolean equals(IEvent event) {
		return this.getNaturalId().equals(event.getNaturalId());
	}
}
