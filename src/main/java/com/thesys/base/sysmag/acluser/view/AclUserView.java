package com.thesys.base.sysmag.acluser.view;

import com.thesys.base.sysmag.acluser.domain.AclUser;

/**
 * 用户的视图
 * 
 * @author Administrator
 */
@SuppressWarnings("serial")
public class AclUserView extends AclUser{
	/**
	 * 角色名称用于界面显示
	 */
	private String roleName;	
	/**
	 * 用于修改密码
	 */
	private String newpassword;
	/**
	 * 修改时确认密码
	 */
	private String okpassword;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getOkpassword() {
		return okpassword;
	}
	public void setOkpassword(String okpassword) {
		this.okpassword = okpassword;
	}		
}
