package com.thesys.base.sysmag.aclmodule.domain;

public enum AclModuleContent {
	
	MENU("menu","菜单"),	
	OTHER("other","其他");
	
	private AclModuleContent(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	final private String code;
	final private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
