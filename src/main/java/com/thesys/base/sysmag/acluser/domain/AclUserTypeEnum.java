package com.thesys.base.sysmag.acluser.domain;


public enum AclUserTypeEnum {
	/**
	 * 全局用户
	 */
	GLOBAL("global","全局用户"),	
	/**
	 * 局部用户
	 */
	PART("part","局部用户");	
	/**
	 * 报价单的报价类型定义
	 */	
	final private String typecode;
	
	final private String typename;
	
	
	public static String getTypeName(String typecode){
		if(AclUserTypeEnum.GLOBAL.getTypecode().equals(typecode)){
			return AclUserTypeEnum.GLOBAL.getTypename();
		}
		
		return AclUserTypeEnum.PART.getTypename();
	}
	private AclUserTypeEnum(String typecode,String typename){
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
