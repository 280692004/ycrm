package com.thesys.base.sysmag.aclmodule.domain.state;

import com.thesys.base.core.util.statemachine.IEvent;

/**
 * 模块事件
 * @author Administrator
 *
 */
public enum AclModuleEvent implements IEvent {
	/**
	 * 确认
	 */
	CONFIRM("confirm", "event.confirm"),
	/**
	 * 反确认
	 */
	ANTICONFIRM("anticonfirm", "event.anticonfirm");

	final private String naturalId;
	final private String description;
	
	private AclModuleEvent(String naturalId, String description) {
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
