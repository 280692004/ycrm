package com.thesys.base.sysmag.acluser.service.impl.state;

import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.core.util.statemachine.IEventHandler;
import com.thesys.base.core.util.statemachine.IState;
import com.thesys.base.core.util.statemachine.IStateOwner;
import com.thesys.base.sysmag.aclbutton.domain.state.AclButtonClassDataManager;
import com.thesys.base.sysmag.aclbutton.domain.state.AclButtonEvent;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.architecture.statemachine.EventHandler;
import com.thesys.architecture.statemachine.SimpleStateOwner;

public class AclUserStateOwner extends SimpleStateOwner{
	public final static String ACLUSER_STATEOWNER_KEY="status";

	public AclUserStateOwner(Object order, String statusognl) {
		super(order, statusognl);
	}
	@Override
	public IEventHandler getEventHander(IEvent event) {
		if (AclButtonEvent.CONFIRM.equals(event)) {
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
		return AclUser.class.getName().toLowerCase().concat("_").concat(ACLUSER_STATEOWNER_KEY);
	}
	
	@Override
	protected String getStartStatus(){
		return AclButtonClassDataManager.STATE_N;
	}	
	

}
