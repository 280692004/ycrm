package com.thesys.base.sysmag.aclrole.domain.state;

import com.thesys.architecture.statemachine.ClassDataManager;
import com.thesys.architecture.statemachine.StatusNameSortMap;

public class AclRoleClassDataManager extends ClassDataManager{
	
	public static String STATE_R = "R";
	
	@Override
	protected void registerTransition() {

		super.registerTransition();		
		registerTransition(STATE_N, AclRoleEvent.CONFIRM, STATE_Y);
		
		registerTransition(STATE_N, AclRoleEvent.INVALID, STATE_INVALID);
		registerTransition(STATE_Y, AclRoleEvent.INVALID, STATE_INVALID);	
		
		registerTransition(STATE_INVALID, AclRoleEvent.ENABLE, STATE_Y);
	}	
	
	
	@Override
	protected void regsiterStateCollection() {
		super.regsiterStateCollection();
	}	
	
	static public StatusNameSortMap AclRoleClassDataManager_statusmap = new StatusNameSortMap();
	
	static {
		AclRoleClassDataManager_statusmap.put(STATE_N, "待定");
		AclRoleClassDataManager_statusmap.put(STATE_Y, "有效");
		AclRoleClassDataManager_statusmap.put(STATE_R, "已审核");
		AclRoleClassDataManager_statusmap.put(STATE_INVALID, "作废");
	}
	
	public static String getStatusName(String status){
		if (AclRoleClassDataManager_statusmap.containsKey(status)) {
			return AclRoleClassDataManager_statusmap.get(status);
		}
		return ClassDataManager.getStatusName(status);
	}	
}

