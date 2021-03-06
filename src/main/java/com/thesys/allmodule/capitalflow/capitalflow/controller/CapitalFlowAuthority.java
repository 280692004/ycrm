package com.thesys.allmodule.capitalflow.capitalflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thesys.architecture.statemachine.ClassDataManager;
import com.thesys.base.core.util.Pair;
import com.thesys.core.architecture.base.authority.BaseIcSuppAuthority;
import com.thesys.core.architecture.base.authority.BaseIcSuppAuthorityConText;
import com.thesys.core.dto.ButtonDto;
import com.thesys.core.dto.JqGridResultDataDto;
/**
 * @author Administrator
 */
@SuppressWarnings("all")
public class CapitalFlowAuthority extends BaseIcSuppAuthority {
	
	public static String MODULE_KEY_OGNL="id";
	public static String MODULE_CODE = "capitalFlow";
	
	/**
	 * 初始化按钮
	 * @param object
	 * @return
	 */
	public static Map<String, Pair<List<String>, List<ButtonDto>>> doOpInitButtons(JqGridResultDataDto resultDatas){
		return doOpInitButtons(resultDatas, MODULE_CODE, MODULE_KEY_OGNL,statusFilterMap);
	}
	
	//单据状态变更条件
	public static Map<String, String> statusFilterMap = new HashMap<String, String>();
	static {
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_DELETE, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_UPDATE, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_CANCEL, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_IMPORT, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_UPLOADFILE, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_DOWNLOADFILE, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		statusFilterMap.put(BaseIcSuppAuthorityConText.COMMON_EVENT_EXPORTPDF, BaseIcSuppAuthorityConText.FILTER_STATUS_ALL);
		
	}
	
}
