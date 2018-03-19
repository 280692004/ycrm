package com.thesys.base.sysmag.aclmenu.domain;

import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.base.entity.BaseEntity;
/**
 * 菜单管理
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@BaseEntityMapper(tableName="base_aclmenu")
public class AclMenu extends BaseEntity {
	
	public AclMenu(){		
	}

	public AclMenu(String user_id,String module_id){	
		this.user_id = user_id;
		this.module_id = module_id;
	}
	
	/**
	 * 模块id
	 */
	private String module_id;
	
	/**
	 * 用户Id
	 */
	private String user_id;	
	/**
	 * 显示顺序
	 */
	private Integer seq;
	
	
	public String getUser_id() {
		return user_id;
	}
	public AclMenu setUser_id(String userId) {
		user_id = userId;
		return this;
	}
	public Integer getSeq() {
		return seq;
	}
	public AclMenu setSeq(Integer seq) {
		this.seq = seq;
		return this;
	}
	public String getModule_id() {
		return module_id;
	}
	public AclMenu setModule_id(String moduleId) {
		module_id = moduleId;
		return this;
	}	
}
