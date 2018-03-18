/**  
* @Title: ParameterBaseBeanTypeEvent.java
* @Package com.thesys.base.basemanager.parameter.parameterbasebeantype.domain.state
* @Description: TODO(用一句话描述该文件做什么)
* @author Athos   
* @date 2016-5-18 上午11:58:38
* @version   
*/ 
package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state;

import com.thesys.base.core.util.statemachine.IEvent;


/**   
 * 事务管理器
 * @version      
 */
public enum ParameterBaseBeanTypeEvent implements IEvent {
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
	
	private ParameterBaseBeanTypeEvent(String naturalId, String description) {
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
