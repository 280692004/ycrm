/**  
* @Title: ParameterBaseBeanTypeClassDataManager.java
* @Package com.thesys.base.basemanager.parameter.parameterbasebeantype.domain.state
* @Description: TODO(用一句话描述该文件做什么)
* @author Athos   
* @date 2016-5-18 上午11:58:25
* @version   
*/ 
package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state;

import com.thesys.architecture.statemachine.ClassDataManager;
import com.thesys.architecture.statemachine.StatusNameSortMap;


/**
 * 状态管理器   
 * @version      
 */

public class ParameterBaseBeanTypeClassDataManager extends ClassDataManager {

	
	@Override
	protected void registerTransition() {
		registerTransition(STATE_N,  ParameterBaseBeanTypeEvent.CONFIRM, STATE_Y);
		registerTransition(STATE_Y,  ParameterBaseBeanTypeEvent.ANTICONFIRM, STATE_N);
	}	
	
	@Override
	protected void regsiterStateCollection() {
		registerState(STATE_N,"待定");
		registerState(STATE_Y,"有效");
	}	
	
	public static String getStatusName(String status){
		return ClassDataManager.getStatusName(status);
	}		
	
	
	static public StatusNameSortMap ClassDataManager_statusmap = new StatusNameSortMap();
	static {
		ClassDataManager_statusmap.put(STATE_Y, "有效");
		ClassDataManager_statusmap.put(STATE_N, "待定");
	}
}
