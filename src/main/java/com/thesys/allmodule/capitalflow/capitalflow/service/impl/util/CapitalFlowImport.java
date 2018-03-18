package com.thesys.allmodule.capitalflow.capitalflow.service.impl.util;

import java.util.Date;

import com.thesys.architecture.base.entity.BaseEntity;

/**
 * 导入使用的模板
 * @author Administrator
 */
@SuppressWarnings("serial")
public class CapitalFlowImport extends BaseEntity{

	/**
	 * 业务日期
	 */
	private Date billdate;
	/**
	 * 资金类型
	 */
	private String capitalFlowTypeCode;
	/**
	 * 单位
	 */
	private String unitCode;
	/**
	 * 数量
	 */
	private Double qty;
	/**
	 * 单价
	 */
	private Double unitprice;
	/**
	 * 总价
	 */
	private Double totalamount;
	/**
	 * 使用者
	 */
	private String user;
	/**
	 * 摘要
	 */
	private String summary;
	
	public Date getBilldate() {
		return billdate;
	}
	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}
	public String getCapitalFlowTypeCode() {
		return capitalFlowTypeCode;
	}
	public void setCapitalFlowTypeCode(String capitalFlowTypeCode) {
		this.capitalFlowTypeCode = capitalFlowTypeCode;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}
	public Double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
