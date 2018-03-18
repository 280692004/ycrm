package com.thesys.base.sysmag.aclrole.service.impl.state;

import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.core.util.statemachine.IEventHandler;
import com.thesys.base.core.util.statemachine.IState;
import com.thesys.base.core.util.statemachine.IStateOwner;
import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.base.sysmag.aclrole.domain.state.AclRoleClassDataManager;
import com.thesys.base.sysmag.aclrole.domain.state.AclRoleEvent;
import com.thesys.architecture.statemachine.EventHandler;
import com.thesys.architecture.statemachine.SimpleStateOwner;

public class AclRoleStateOwner extends SimpleStateOwner {
	
	public static final String STATUS_KEY = "status";
	
	public AclRoleStateOwner(Object order, String statusognl) {
		super(order, statusognl);
	}
	
	@Override
	public IEventHandler getEventHander(IEvent event) {
		
		if (AclRoleEvent.CONFIRM.equals(event)) {
			return new ConfirmEventHandler(event);
		}
		
		if (AclRoleEvent.INVALID.equals(event)) {
			return new InvalidEventHandler(event);
		}
		
		if (AclRoleEvent.ENABLE.equals(event)) {
			return new EnableEventHandler(event);
		}
		
		return super.getEventHander(event);
	}
	
	class ConfirmEventHandler extends  EventHandler{

		public ConfirmEventHandler(IEvent event) {
			super(event);
		}

		@Override
		public void handle(IStateOwner stateOwner, IState leaveState, IState enterState) {
			validatorConfirm((AclRole)stateOwner.getOwner());
			super.handle(stateOwner, leaveState, enterState);
		}

		private void validatorConfirm(AclRole aclRole) {
			if(!AclRoleClassDataManager.STATE_N.equals(aclRole.getStatus())){
				throw new RuntimeException("角色状态异常，无法进行确定");
			}
			
		}
	}
	
	class InvalidEventHandler extends  EventHandler{

		public InvalidEventHandler(IEvent event) {
			super(event);
		}

		@Override
		public void handle(IStateOwner stateOwner, IState leaveState, IState enterState) {
			validatorInvalid((AclRole)stateOwner.getOwner());
			super.handle(stateOwner, leaveState, enterState);
		}

		private void validatorInvalid(AclRole aclRole) {
			if(AclRoleClassDataManager.STATE_INVALID.equals(aclRole.getStatus())){
				throw new RuntimeException("角色状态异常，无法进行确定");
			}
			
		}
	}
	
	class EnableEventHandler extends  EventHandler{

		public EnableEventHandler(IEvent event) {
			super(event);
		}

		@Override
		public void handle(IStateOwner stateOwner, IState leaveState, IState enterState) {
			validatorEnable((AclRole)stateOwner.getOwner());
			super.handle(stateOwner, leaveState, enterState);
		}

		private void validatorEnable(AclRole aclRole) {
			if(!AclRoleClassDataManager.STATE_INVALID.equals(aclRole.getStatus())){
				throw new RuntimeException("角色状态异常，无法进行确定");
			}
			
		}
	}		
	
	@Override
	protected String getClassDataManagerRegisterKey() {	
		return AclRole.class.getName().toLowerCase().concat("_").concat(STATUS_KEY);
	}
	
	@Override
	protected String getStartStatus(){
		return AclRoleClassDataManager.STATE_N;
	}
}
