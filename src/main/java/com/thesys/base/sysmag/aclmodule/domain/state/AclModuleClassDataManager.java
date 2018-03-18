package com.thesys.base.sysmag.aclmodule.domain.state;

import com.thesys.architecture.statemachine.ClassDataManager;

public class AclModuleClassDataManager extends ClassDataManager {
	
	
	@Override
	protected void registerTransition() {

		super.registerTransition();		
		
		registerTransition(STATE_N, AclModuleEvent.CONFIRM, STATE_Y);
		registerTransition(STATE_Y, AclModuleEvent.ANTICONFIRM, STATE_N);
	}	
	
	
	@Override
	protected void regsiterStateCollection() {
		super.regsiterStateCollection();
	}	
	
	public static String getStatusName(String status){
		return ClassDataManager.getStatusName(status);
	}		
}
