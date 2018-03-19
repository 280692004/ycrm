package com.thesys.base.sysmag.acluser.domain.state;

import com.thesys.architecture.statemachine.ClassDataManager;

public class AclUserClassDataManager extends ClassDataManager {
	
	
	@Override
	protected void registerTransition() {

		super.registerTransition();		
		
		registerTransition(STATE_N, AclUserEvent.CONFIRM, STATE_Y);
		registerTransition(STATE_Y, AclUserEvent.ANTICONFIRM, STATE_N);
	}	
	
	
	@Override
	protected void regsiterStateCollection() {
		super.regsiterStateCollection();
	}	
	
	public static String getStatusName(String status){
		return ClassDataManager.getStatusName(status);
	}		
}
