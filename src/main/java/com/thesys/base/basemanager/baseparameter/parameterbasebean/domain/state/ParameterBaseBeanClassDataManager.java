package com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.state;

import com.thesys.architecture.statemachine.ClassDataManager;
import com.thesys.architecture.statemachine.StatusNameSortMap;


/**
 * 参数流程变更管理器
 * @version
 */
public class ParameterBaseBeanClassDataManager extends ClassDataManager {

	@Override
	protected void registerTransition() {
		registerTransition(STATE_N, ParameterBaseBeanEvent.CONFIRM, STATE_Y);
		registerTransition(STATE_Y, ParameterBaseBeanEvent.ANTICONFIRM, STATE_N);
	}

	@Override
	protected void regsiterStateCollection() {
		registerState(STATE_N, "待定");
		registerState(STATE_Y, "有效");
	}

	public static String getStatusName(String status) {
		return ClassDataManager_statusmap.get(status);
	}

	static public StatusNameSortMap ClassDataManager_statusmap = new StatusNameSortMap();
	static {
		ClassDataManager_statusmap.put(STATE_Y, "有效");
		ClassDataManager_statusmap.put(STATE_N, "待定");
	}
}
