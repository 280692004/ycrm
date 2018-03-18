package com.thesys.base.sysmag.base;


public enum PermissionConstantEnum {
	
	YES("1","允许"),
	NO("0"," 禁止"),
	UNSET("-1","继承");
	
	final private String typecode;
	final private String typename;
	
	private PermissionConstantEnum(String typecode,String typename){
		this.typecode = typecode;
		this.typename = typename;
	}


	public String getTypecode() {
		return typecode;
	}

	public String getTypename() {
		return typename;
	}
}
