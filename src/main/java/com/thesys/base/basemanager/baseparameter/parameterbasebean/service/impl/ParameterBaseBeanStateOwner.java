package com.thesys.base.basemanager.baseparameter.parameterbasebean.service.impl;

import com.thesys.architecture.statemachine.EventHandler;
import com.thesys.architecture.statemachine.SimpleStateOwner;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.state.ParameterBaseBeanEvent;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.core.util.statemachine.IEventHandler;
import com.thesys.base.core.util.statemachine.IState;
import com.thesys.base.core.util.statemachine.IStateOwner;


/**  
* @Description: TODO(参数流程管理器)
* @version   
*/ 
public class ParameterBaseBeanStateOwner extends SimpleStateOwner{
	public final static String PARAMETERBASEBEAN_STATEOWNER_KEY="status";

	public ParameterBaseBeanStateOwner(Object order, String statusognl) {
		super(order, statusognl);
	}
	@Override
	public IEventHandler getEventHander(IEvent event) {
		if (ParameterBaseBeanEvent.CONFIRM.equals(event)) {
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
		return ParameterBaseBean.class.getName().toLowerCase().concat("_").concat(PARAMETERBASEBEAN_STATEOWNER_KEY);
	}
	
}
