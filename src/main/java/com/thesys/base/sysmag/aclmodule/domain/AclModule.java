package com.thesys.base.sysmag.aclmodule.domain;

import java.util.ArrayList;
import java.util.List;

import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.core.annotation.ManyToOne;
import com.thesys.architecture.core.annotation.Transient;
import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.statemachine.ClassDataManager;

/**
 * 
 * @author Administrator
 *
 */
@SuppressWarnings({ "serial"})
@BaseEntityMapper(tableName="base_aclmodule")
public class AclModule extends BaseEntity {
	public AclModule() {
		super();
	}

	public AclModule(Integer id) {
		super(id);
	}
	
	public AclModule(Integer id, Integer parent_id) {
		super(id);
		this.parent_id = parent_id;
	}
	
	
	public AclModule(String code) {
		super();
		this.code = code;
	}


	/**
	 * 父模块
	 */
	private Integer parent_id;
	
	/**
	 * 父模块Id
	 */
	@ManyToOne(name = "parent_id")
	private AclModule parent;
	
	/**
	 * 模块的编码
	 */
	private String code;
	
	/**
	 * 模块名称
	 */
	private String cname;
	/**
	 * 模块操作地址
	 */
	
	private String url;

	/**
	 * 模块显示顺序
	 */
	private Integer orderNo;	
	
	/**
	 *状态 
	 */
	private String status;
	/**
	 * 子模块列表
	 */
	@Transient
	private List<AclModule> details = new ArrayList<AclModule>();	
	@Override
	public AclModule clone(){
		AclModule aclModule = new AclModule();
		aclModule.setId(this.getId());
		aclModule.setNaturalId(this.getNaturalId());
		aclModule.setCreateBy_id(this.getCreateBy_id());
		aclModule.setCreateTime(this.getCreateTime());
		aclModule.setLastModifyBy_id(this.getLastModifyBy_id());
		aclModule.setLastModifyTime(this.getLastModifyTime());
		aclModule.setVersion(this.getVersion());
		
		aclModule.setUrl(this.getUrl());		
		aclModule.setOrderNo(this.getOrderNo());
		
		return aclModule;
	}

	public String getUrl() {
		return url;
	}

	public AclModule setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getStatusName(){
		return ClassDataManager.STATE_N.equals(this.getStatus())?"待定":"有效";
	}
	public Integer getOrderNo() {
		return orderNo;
	}

	public AclModule setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
		return this;
	}
	// -------------------------------------------------------------------------------------------
	public AclModule setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getCname() {
		return cname;
	}

	public AclModule setCname(String cname) {
		this.cname = cname;
		return this;
	}
	
	public String getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public AclModule setCode(String code) {
		this.code = code;
		return this;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public AclModule setParent_id(Integer parentId) {
		parent_id = parentId;
		return this;
	}

	public List<AclModule> getDetails() {
		return details;
	}

	public AclModule setDetails(List<AclModule> details) {
		this.details = details;
		return this;
	}

	public AclModule getParent() {
		return parent;
	}

	public AclModule setParent(AclModule parent) {
		this.parent = parent;
		return this;
	}
}
