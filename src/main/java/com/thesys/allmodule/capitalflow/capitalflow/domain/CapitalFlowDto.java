package com.thesys.allmodule.capitalflow.capitalflow.domain;

import java.util.Date;
import java.util.List;

/**
 * 新增、修改数据传输帮助类
 */
public class CapitalFlowDto {

	/**
	 * 业务日期
	 */
	private Date billdate;
	/**
	 * 操作人
	 */
	private String creator;
	/**
	 * 资金流水
	 */
	private List<CapitalFlow> details;

	public Date getBilldate() {
		return billdate;
	}

	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public List<CapitalFlow> getDetails() {
		return details;
	}

	public void setDetails(List<CapitalFlow> details) {
		this.details = details;
	}
	
}
