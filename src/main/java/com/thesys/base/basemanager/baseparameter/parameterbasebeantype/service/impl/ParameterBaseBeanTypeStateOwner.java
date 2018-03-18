/**  
* @Title: ParameterBaseBeanTypeStateOwner.java
* @Package com.thesys.base.basemanager.parameter.parameterbasebeantype.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author Athos   
* @date 2016-5-19 下午11:06:16
* @version   
*/ 
package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.service.impl;

import com.thesys.architecture.statemachine.EventHandler;
import com.thesys.architecture.statemachine.SimpleStateOwner;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.ParameterBaseBeanType;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state.ParameterBaseBeanTypeClassDataManager;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state.ParameterBaseBeanTypeEvent;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.core.util.statemachine.IEventHandler;
import com.thesys.base.core.util.statemachine.IState;
import com.thesys.base.core.util.statemachine.IStateOwner;


/**   
 * 类描述：流程变更管理器
 * 修改备注：   
 * @version      
 */

public class ParameterBaseBeanTypeStateOwner extends SimpleStateOwner {
	
	public final static String PARAMETERBASEBEANTYPE_STATEOWNER_KEY="status";

	public ParameterBaseBeanTypeStateOwner(Object order, String statusognl) {
		super(order, statusognl);
	}
	@Override
	public IEventHandler getEventHander(IEvent event) {
		if (ParameterBaseBeanTypeEvent.CONFIRM.equals(event)) {
			return new ConfirmEventHandler(event);
		}
		if (ParameterBaseBeanTypeEvent.ANTICONFIRM.equals(event)) {
			return new AnitConfirmEventHandler(event);
		}
		
		return super.getEventHander(event);
	}
	
	class ConfirmEventHandler extends  EventHandler{

		public ConfirmEventHandler(IEvent event) {
			super(event);
		}

		@Override
		public void handle(IStateOwner stateOwner, IState leaveState, IState enterState) {
			ParameterBaseBeanType entity = (ParameterBaseBeanType)stateOwner.getOwner();
			if(!ParameterBaseBeanTypeClassDataManager.STATE_N.equals(entity.getStatus())){
				throw new RuntimeException("单据状态异常...");
			}
			super.handle(stateOwner, leaveState, enterState);
		}
	}
	
	class AnitConfirmEventHandler extends  EventHandler{

		public AnitConfirmEventHandler(IEvent event) {
			super(event);
		}

		@Override
		public void handle(IStateOwner stateOwner, IState leaveState, IState enterState) {
			ParameterBaseBeanType entity = (ParameterBaseBeanType)stateOwner.getOwner();
			if(!ParameterBaseBeanTypeClassDataManager.STATE_Y.equals(entity.getStatus())){
				throw new RuntimeException("单据状态异常...");
			}
			super.handle(stateOwner, leaveState, enterState);
		}
	}
	
	@Override
	protected String getClassDataManagerRegisterKey() {	
		return ParameterBaseBeanType.class.getName().toLowerCase().concat("_").concat(PARAMETERBASEBEANTYPE_STATEOWNER_KEY);
	}
	
	@Override
	protected String getStartStatus(){
		return ParameterBaseBeanTypeClassDataManager.STATE_N;
	}	
}
