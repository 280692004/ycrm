package com.thesys.base.basemanager.baseparameter.parameterbasebean.domain;


import javax.persistence.Transient;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.state.ParameterBaseBeanClassDataManager;

/**
 * 参数实体基类
 */
@SuppressWarnings("serial")
@BaseEntityMapper(tableName="base_parameterbasebean")
public class ParameterBaseBean extends BaseEntity{
	public ParameterBaseBean(){		

	}
	public ParameterBaseBean(String code) {
		this.code = code;
	}
	
	public ParameterBaseBean(String code,String cname){	
		this.code = code;
		this.cname = cname;
	}
	public ParameterBaseBean(String code,String cname,Integer id){	
		this.code = code;
		this.cname = cname;
		this.setId(id);
	}	
	/**
	 * 父code
	 */
	private String parentcode;
	/**
	 * 类型
	 */
	private String dtype;
	/**
	 * 参数代码（编程时使用code引用） 同一种参数类型与代码不能相同
	 */	
	private String code;	
	/**
	 * 名称
	 */
	private String cname;
	/**
	 * 状态N【待定】,Y【有效】,C【取消】
	 */
	private String status;
	/**
	 * 参数备注 @Column(length = 50)
	 */	
	private String remark;// 新增参数描述
	/**
	 * 排序优先级 0>1>2
	 */
	private Integer level;
	/**
	 * 特殊编码 
	 */
	private String additionalCode;
	/**
	 * 参数类型名称【界面显示】
	 * @return
	 */
	@Transient
	private String dtypeName;
	/**
	 * 父级节点名称【界面显示】
	 * @return
	 */
	@Transient
	private String parentName;
	
	public String getParentcode() {
		return parentcode;
	}
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getAdditionalCode() {
		return additionalCode;
	}
	public void setAdditionalCode(String additionalCode) {
		this.additionalCode = additionalCode;
	}
	public String getDtypeName() {
		return dtypeName;
	}
	public void setDtypeName(String dtypeName) {
		this.dtypeName = dtypeName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * 根据状态编码查询名称
	 * @return
	 */
	public String getStatusName(){
		return ParameterBaseBeanClassDataManager.getStatusName(status);
	}
	
}