package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.util;

import com.thesys.base.basemanager.baseparameter.parametercapitalflowtype.ParameterCapitalFlowType;
import com.thesys.base.basemanager.baseparameter.parameterunit.ParameterUnitType;
import com.thesys.base.core.util.ValidateUtil;

/**
 * 参数公告帮助类
 * @author Kyle
 *
 */
public class ParameterFindByCodeUtil {

	/**
	 * 根据参数编码获取中文名称
	 * @param code
	 * @return
	 */
	public static String getParameterNameBycode(String classPathName,String code){
		String resultName = "";
		String qualification = "Type_".concat(code).toUpperCase();
		//资金流转类型
		if(ParameterCapitalFlowType.class.getName().equals(classPathName)){
			resultName = ValidateUtil.format(qualification, new ParameterCapitalFlowType());
		}
		//单位类型参数
		if(ParameterUnitType.class.getName().equals(classPathName)){
			resultName = ValidateUtil.format(qualification, new ParameterUnitType());
		}
		
		return resultName;
	}
	
}

