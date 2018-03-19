package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain;

import org.apache.ibatis.type.Alias;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state.ParameterBaseBeanTypeClassDataManager;

/**
 * 参数类型
 */
@SuppressWarnings("serial")
@Alias("ParameterBaseBeanType")
@BaseEntityMapper(tableName="base_parameterbasebeantype")
public class ParameterBaseBeanType extends BaseEntity{
	/**
	 * 类型
	 */
	private String dtype;
	/**
	 * 类型名称
	 */
	private String cname;
	/**
	 * 排序号
	 */
	private Integer orderNo;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 备注
	 * @return
	 */
	private String remark;

	public ParameterBaseBeanType(){		
	}
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
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
	
	public String getStatusName(){
		return ParameterBaseBeanTypeClassDataManager.getStatusName(this.getStatus());
	}
}
