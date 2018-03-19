package com.thesys.base.sysmag.aclbutton.domain.state;

import com.thesys.architecture.statemachine.ClassDataManager;

public class AclButtonClassDataManager extends ClassDataManager {
	
	
	@Override
	protected void registerTransition() {

		super.registerTransition();		
		
		registerTransition(STATE_N, AclButtonEvent.CONFIRM, STATE_Y);
		registerTransition(STATE_Y, AclButtonEvent.ANTICONFIRM, STATE_N);
	}	
	
	
	@Override
	protected void regsiterStateCollection() {
		super.regsiterStateCollection();
	}	
	
	public static String getStatusName(String status){
		return ClassDataManager.getStatusName(status);
	}		
}
