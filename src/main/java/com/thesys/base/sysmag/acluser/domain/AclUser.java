package com.thesys.base.sysmag.acluser.domain;

import java.util.Date;

import com.thesys.base.sysmag.acluser.domain.state.AclUserClassDataManager;
import com.thesys.architecture.core.annotation.BaseEntityMapper;
import com.thesys.architecture.core.annotation.Transient;
import com.thesys.architecture.base.entity.BaseEntity;


/**
 * 用户
 * @author zeng yuanjin
 * 
 */
@SuppressWarnings("serial")
@BaseEntityMapper(tableName = "base_acluser")
public class AclUser extends BaseEntity {

	
	public AclUser() {  
	}
	public AclUser(Integer id) {
		super(id);

	}
	
	public AclUser(String name) {
		this.setName(name);
	}
	
	public AclUser(String name, String password) {
		this.setName(name);
		this.password = password;
	}

	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 帐号密码 (NotNull)
	 */
	private String password;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 手机
	 */
	private String mobilePhone;
	/**
	 * 即时通讯号码
	 */
	private String imNumber;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String avatarImage;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 人员姓名
	 */
	private String personName;	
	/**
	 * 用户类型 全局用户和局部用户
	 */
	private String type;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 过期日期
	 */
	private Date expireTime;	
	/**
	 * 密码最后修改日期
	 */
	private Date lastmodfypwddate;
	/**
	 * 有效天数
	 */
	private Integer activeday;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 是否重定向
	 */
	@Transient
	private Boolean isRedirection;
	/**
	 * 提示信息
	 */
	@Transient
	private String message;
	
	// gettter and setter --------------------------------------------------
	public String getTypeName(){
		return AclUserTypeEnum.getTypeName(this.getType());
	}
	public String getStatusName(){
		return AclUserClassDataManager.getStatusName(this.getStatus());
	}
	public String getPassword() {
		return password;
	}
	public AclUser setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public AclUser setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public AclUser setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getAddress() {
		return address;
	}
	public AclUser setAddress(String address) {
		this.address = address;
		return this;
	}
	public String getStatus() {
		return this.status;
	}
	public AclUser setStatus(String status) {
		this.status = status;
		return this;
	}
	public String getPersonName() {
		return personName;
	}
	public AclUser setPersonName(String personName) {
		this.personName = personName;
		return this;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public AclUser setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
		return this;
	}
	public String getImNumber() {
		return imNumber;
	}
	public AclUser setImNumber(String imNumber) {
		this.imNumber = imNumber;
		return this;
	}
	public String getName() {
		return name;
	}
	public AclUser setName(String name) {
		this.name = name;
		return this;
	}
	public String getType() {
		return type;
	}
	public AclUser setType(String type) {
		this.type = type;
		return this;
	}
	public String getAvatarImage() {
		return avatarImage;
	}
	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public AclUser setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
		return this;
	}
	public Date getLastmodfypwddate() {
		return lastmodfypwddate;
	}
	public void setLastmodfypwddate(Date lastmodfypwddate) {
		this.lastmodfypwddate = lastmodfypwddate;
	}
	public Integer getActiveday() {
		return activeday;
	}
	public void setActiveday(Integer activeday) {
		this.activeday = activeday;
	}
	public Boolean getIsRedirection() {
		return isRedirection;
	}
	public void setIsRedirection(Boolean isRedirection) {
		this.isRedirection = isRedirection;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
