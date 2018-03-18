package com.thesys.base.sysmag.aclmodule.service.impl.stateowner;

import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.core.util.statemachine.IEventHandler;
import com.thesys.base.core.util.statemachine.IState;
import com.thesys.base.core.util.statemachine.IStateOwner;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.aclmodule.domain.state.AclModuleClassDataManager;
import com.thesys.base.sysmag.aclmodule.domain.state.AclModuleEvent;
import com.thesys.architecture.statemachine.EventHandler;
import com.thesys.architecture.statemachine.SimpleStateOwner;

public class AclModuleStateOwner extends SimpleStateOwner {

	public final static String ACLMODULE_STATEOWNER_KEY="status";
	public AclModuleStateOwner(Object order, String statusognl) {
		super(order, statusognl);		
	}

	@Override
	public IEventHandler getEventHander(IEvent event) {
		
		if (AclModuleEvent.CONFIRM.equals(event)) {
			return new ConfirmEventHandler(event);
		}
		
		return super.getEventHander(event);
	}
	
	class ConfirmEventHandler extends  EventHandler{

		public ConfirmEventHandler(IEvent event) {
			super(event);
		}

		@Override
		public void handle(IStateOwner stateOwner, IState leaveState, IState enterState) {
			super.handle(stateOwner, leaveState, enterState);
		}
	}
	
	@Override
	protected String getClassDataManagerRegisterKey() {	
		return AclModule.class.getName().toLowerCase().concat("_").concat(ACLMODULE_STATEOWNER_KEY);
	}
	
	@Override
	protected String getStartStatus(){
		return AclModuleClassDataManager.STATE_N;
	}	
	
}
