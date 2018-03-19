package com.thesys.base.sysmag.acluser.domain.state;

import com.thesys.base.core.util.statemachine.IEvent;

/**
 * 按钮事件
 * @author Administrator
 *
 */
public enum AclUserEvent implements IEvent {
	/**
	 * 确认
	 */
	CONFIRM("confirm", "event.confirm"),
	/**
	 * 反确认
	 */
	ANTICONFIRM("anticonfirm", "event.anticonfirm"),
	/**
	 * 注销
	 */
	LOGOUT("logout", "event.logout"),
	/**
	 * 启动
	 */
	ENABLE("enable", "event.enable");

	final private String naturalId;
	final private String description;
	
	private AclUserEvent(String naturalId, String description) {
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
