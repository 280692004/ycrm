package com.thesys.core.dto;

import com.thesys.base.core.util.ValidateUtil;

/**
 * 控制界面权限按钮帮助类
 * @author Kyle
 *
 */
public class ButtonDto {

	/**
	 * 按钮对应的函数名称
	 */
	private String funName;
	/**
	 * 按钮对应的名称
	 */
	private String cname;
	/**
	 * 按钮图标  需要在本地库中存在
	 */
	private String iconName;
	
	public ButtonDto() {
		super();
	}
	public ButtonDto(String funName, String cname, String iconName) {
		super();
		this.funName = ValidateUtil.isEmpty(funName)?funName:"doOp".concat(funName);
		this.cname = cname;
		this.iconName = iconName;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	
	
}
