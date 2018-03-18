package com.thesys.core.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BaseDto implements Serializable {

	/**
	 * Id
	 */
	private Integer id;
	
	/**
	 * 版本
	 */
	private Integer version;
	
	/**
	 * 创建人编码（最好不要使用Id，因为Id在分布式的管理环境中不好处理）
	 */
	private String createByUserCode;
	
	/**
	 * 创建人名称
	 */
	private String createByUserName;
	
	/**
	 * 创建时间
	 */
	private Date  createTime;
	
	/**
	 * 最后修改人编码（最好不要使用Id，因为Id在分布式的管理环境中不好处理）
	 */
	private String lastModifyByUserCode;
	
	/**
	 * 最后修改人名称
	 */
	private String lastModifyByUserName;
	
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;

	// ------------------------------------------ getter and setter ---------------------------------------------
	public Integer getId() {
		return id;
	}

	public BaseDto setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getVersion() {
		return version;
	}

	public BaseDto setVersion(Integer version) {
		this.version = version;
		return this;
	}

	public String getCreateByUserCode() {
		return createByUserCode;
	}

	public BaseDto setCreateByUserCode(String createByUserCode) {
		this.createByUserCode = createByUserCode;
		return this;
	}

	public String getCreateByUserName() {
		return createByUserName;
	}

	public BaseDto setCreateByUserName(String createByUserName) {
		this.createByUserName = createByUserName;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public BaseDto setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getLastModifyByUserCode() {
		return lastModifyByUserCode;
	}

	public BaseDto setLastModifyByUserCode(String lastModifyByUserCode) {
		this.lastModifyByUserCode = lastModifyByUserCode;
		return this;
	}

	public String getLastModifyByUserName() {
		return lastModifyByUserName;
	}

	public BaseDto setLastModifyByUserName(String lastModifyByUserName) {
		this.lastModifyByUserName = lastModifyByUserName;
		return this;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public BaseDto setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}
	
}
