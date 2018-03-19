package com.thesys.allmodule.capitalflow.capitalflow.domain;

import java.util.Date;

import javax.persistence.Transient;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.core.annotation.NaturalIdHeader;
import com.thesys.architecture.core.annotation.NaturalNoBackTypeContent;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.util.ParameterFindByCodeUtil;
import com.thesys.base.basemanager.baseparameter.parametercapitalflowtype.ParameterCapitalFlowType;
import com.thesys.base.basemanager.baseparameter.parameterunit.ParameterUnitType;

/**
 * 资金流
 * 记录资金的支出与收入情况。
 * 序号', '日期', '资金类型', '单价', '数量', '单位', '总额', '内容',
 * @author Kyle
 *
 */
@SuppressWarnings("serial")
@NaturalIdHeader(header="CF",backType = NaturalNoBackTypeContent.BACKTYPE_DATE, ognl = "naturalId")
@BaseEntityMapper(tableName="f_capitalflow")
public class CapitalFlow extends BaseEntity {

	/**
	 * 记账日期
	 */
	private Date billdate;
	@Transient
	private Date end_billdate;
	/**
	 * 资金流动类型【收入/支出】
	 */
	private String capitalFlowTypeCode;
	/**
	 * 数量
	 */
	private Double qty;
	/**
	 * 单位
	 */
	private String unitCode;
	/**
	 * 单价 保留2位精度
	 */
	private Double unitprice;
	/**
	 * 总额 保留2位精度 
	 */
	private Double totalamount;
	/**
	 * 使用者
	 */
	private String user;
	/**
	 * 摘要/备注
	 */
	private String summary;
	/**
	 * 附件凭证名称 冗余字段
	 */
	private String fileName;
	/**
	 * 附件凭证全路径
	 */
	private String filepath;
	
	private CapitalFlow() {
		super();
	}
	
	public Date getBilldate() {
		return billdate;
	}
	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}
	public Date getEnd_billdate() {
		return end_billdate;
	}
	public void setEnd_billdate(Date end_billdate) {
		this.end_billdate = end_billdate;
	}
	public String getCapitalFlowTypeCode() {
		return capitalFlowTypeCode;
	}
	public void setCapitalFlowTypeCode(String capitalFlowTypeCode) {
		this.capitalFlowTypeCode = capitalFlowTypeCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
	
	public String getFilepath() {
		return filepath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
//-------------------------------  界面code=>name显示
	
	/**
	 * 根据单位编码查询名称
	 * @return
	 */
	public String getUnitName(){
		return ParameterFindByCodeUtil.getParameterNameBycode(ParameterUnitType.class.getName(), this.getUnitCode());
	}
	
	/**
	 * 根据资金编码查询名称
	 * @return
	 */
	public String getCapitalFlowTypeName(){
		return ParameterFindByCodeUtil.getParameterNameBycode(ParameterCapitalFlowType.class.getName(), this.getCapitalFlowTypeCode());
	}
	
}
