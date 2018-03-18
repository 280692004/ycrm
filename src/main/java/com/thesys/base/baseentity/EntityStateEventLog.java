package com.thesys.base.baseentity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.base.entity.ReferenceEntityObject;
import com.thesys.base.basemanager.common.statemachine.IStateAwareEntity;
import com.thesys.base.core.util.DateUtil;
import com.thesys.base.core.util.collection.ListUtil;
import com.thesys.base.core.util.reflect.IFilter;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.core.util.statemachine.IState;

/**
 * 事件状态日志 
 * leaveState --event--> enterState
 * 
 * @author zeng yuanjin
 * @date 2013-3-9 上午12:09:54
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class EntityStateEventLog extends BaseEntity {

	/**
	 * 状态
	 */
	@Column(nullable=false, length=10)
	private String state;
	/**
	 * 状态描述
	 */
	private String stateDescr;
	
	/**
	 * 事件
	 */
	private String event;
	/**
	 * 事件描述
	 */
	private String eventDesc;
	
	/**
	 * 离开状态
	 */
	private String leaveState;
	/**
	 * 离开状态描述
	 */
	private String leaveStateDesc;
	
	/**
	 * 进入状态
	 */
	private String enterState;
	/**
	 * 进入状态描述
	 */
	private String enterStateDesc;
	
	@Embedded
	private ReferenceEntityObject refObj;
	
	public void init(IStateAwareEntity entity, IEvent event, IState leaveState, IState enterState) {
		this.setState(entity.getStatus());
		this.setStateDescr(entity.getStatusName(entity.getStatus()));
		this.setEvent(event.getNaturalId());
		this.setEventDesc(event.getDescription());
		this.setLeaveState(leaveState.getName());
		this.setLeaveStateDesc(entity.getStatusName(this.getLeaveState()));
		this.setEnterState(enterState.getName());
		this.setEnterStateDesc(entity.getStatusName(this.getEnterState()));
		this.setEntity((BaseEntity)entity);
		this.setRefObj(ReferenceEntityObject.create((BaseEntity)entity));
	}
	
	abstract protected void setEntity(BaseEntity entity);
	
	public String getFormatCreateTime() {
		return DateUtil.format(this.getCreateTime(), DateUtil.C_TIME_PATTON_DEFAULT);
	}
	
	// getter and setter -------------------------------------------------------------------------
	public String getState() {
		return state;
	}

	public EntityStateEventLog setState(String state) {
		this.state = state;
		return this;
	}

	public String getStateDescr() {
		return stateDescr;
	}

	public EntityStateEventLog setStateDescr(String stateDescr) {
		this.stateDescr = stateDescr;
		return this;
	}

	public String getEvent() {
		return event;
	}

	public EntityStateEventLog setEvent(String event) {
		this.event = event;
		return this;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public EntityStateEventLog setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
		return this;
	}

	public String getLeaveState() {
		return leaveState;
	}

	public EntityStateEventLog setLeaveState(String leaveState) {
		this.leaveState = leaveState;
		return this;
	}

	public String getLeaveStateDesc() {
		return leaveStateDesc;
	}

	public EntityStateEventLog setLeaveStateDesc(String leaveStateDesc) {
		this.leaveStateDesc = leaveStateDesc;
		return this;
	}

	public String getEnterState() {
		return enterState;
	}

	public EntityStateEventLog setEnterState(String enterState) {
		this.enterState = enterState;
		return this;
	}

	public String getEnterStateDesc() {
		return enterStateDesc;
	}

	public EntityStateEventLog setEnterStateDesc(String enterStateDesc) {
		this.enterStateDesc = enterStateDesc;
		return this;
	}

	public ReferenceEntityObject getRefObj() {
		return refObj;
	}

	public void setRefObj(ReferenceEntityObject refObj) {
		this.refObj = refObj;
	}
	
	public static EntityStateEventLog getLastTimeEntityStateEventLog(List<EntityStateEventLog> eventlogs, final String event) {
		
		List<EntityStateEventLog> filteredLogs = ListUtil.filter(eventlogs, new IFilter<EntityStateEventLog>(){
			@Override
			public boolean doFilter(EntityStateEventLog obj) {
				return event.equals(obj.getEvent());
			}
		});
		Collections.sort(filteredLogs, new Comparator<EntityStateEventLog>(){
			@Override
			public int compare(EntityStateEventLog o1, EntityStateEventLog o2) {
				return -1*o1.getCreateTime().compareTo(o2.getCreateTime());
			}
		});
		return filteredLogs.isEmpty()?null:filteredLogs.get(0);
	}
	
}