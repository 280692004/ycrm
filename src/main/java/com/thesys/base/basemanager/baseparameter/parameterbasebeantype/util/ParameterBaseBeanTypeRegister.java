package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.util;

import com.thesys.base.basemanager.baseparameter.parametercapitalflowtype.ParameterCapitalFlowType;
import com.thesys.base.basemanager.baseparameter.parameterunit.ParameterUnitType;

/**
 * 参数类型注册
 * @author Kyle
 *
 */
public enum ParameterBaseBeanTypeRegister {

	ParameterType_ParameterCapitalFlowType(ParameterCapitalFlowType.class.getName(),"资金流转类型",1),
	ParameterType_ParameterUnitType(ParameterUnitType.class.getName(),"单位类型",2)
	;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 序号
	 */
	private Integer seq;
	
	
	private ParameterBaseBeanTypeRegister() {
	}

	private ParameterBaseBeanTypeRegister(String code, String name, Integer seq) {
		this.code = code;
		this.name = name;
		this.seq = seq;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
