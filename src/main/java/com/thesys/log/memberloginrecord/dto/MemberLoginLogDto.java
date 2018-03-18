package com.thesys.log.memberloginrecord.dto;

import javax.servlet.http.HttpServletRequest;

import com.thesys.core.dto.BaseDto;
import com.thesys.core.util.IpUtil;

@SuppressWarnings("serial")
public class MemberLoginLogDto extends BaseDto {
	//如果发送失败保存进待发送信息的TITLE
	public final static String TITLE="memberloginlog";
	
	public MemberLoginLogDto(){
		super();
	}
	
	public MemberLoginLogDto(String accessTime){
		super();
		this.accessTime = accessTime;
	}
	
	public MemberLoginLogDto(String accessTime,String memberId,String loginTime,String loginIp,String loginArea,String logoutTime){
		super();
		this.accessTime = accessTime;
		this.memberId = memberId;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.loginArea = loginArea;
		this.logoutTime = logoutTime;
	}
	
	public MemberLoginLogDto(HttpServletRequest request,MemberLoginLogDto memberLoginRecordDto){
		this.setId(memberLoginRecordDto.getId());
		this.setVersion(memberLoginRecordDto.getVersion());
		this.setCreateByUserCode(memberLoginRecordDto.getCreateByUserCode());
		this.setCreateByUserName(memberLoginRecordDto.getCreateByUserName());
		this.setCreateTime(memberLoginRecordDto.getCreateTime());
		this.setLastModifyByUserCode(memberLoginRecordDto.getLastModifyByUserCode());
		this.setLastModifyByUserName(memberLoginRecordDto.getLastModifyByUserName());
		this.setLastModifyTime(memberLoginRecordDto.getLastModifyTime());
		this.accessTime = memberLoginRecordDto.getAccessTime();
		this.memberId = memberLoginRecordDto.getMemberId();
		this.loginTime = memberLoginRecordDto.getLoginTime();
		this.loginIp = IpUtil.getIpAddr(request);
		this.logoutTime = memberLoginRecordDto.getLogoutTime();
	}
	
	
	/**
	 * 访问时间
	 */
	private String accessTime;
	
	/**
	 * 会员编码
	 */
	private String memberId;
	/**
	 * 登录时间
	 */
	private String loginTime;
	/**
	 * 登录IP
	 */
	private String loginIp;
	/**
	 * 登录地区
	 */
	private String loginArea;
	/**
	 * 登出时间
	 */
	private String logoutTime;

	
	//*************************************************Getter Setter**********************************************************
	
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public MemberLoginLogDto setMemberId(String memberId) {
		this.memberId = memberId;
		return this;
	}

	/**
	 * @return the loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime the loginTime to set
	 */
	public MemberLoginLogDto setLoginTime(String loginTime) {
		this.loginTime = loginTime;
		return this;
	}

	/**
	 * @return the loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * @param loginIp the loginIp to set
	 */
	public MemberLoginLogDto setLoginIp(String loginIp) {
		this.loginIp = loginIp;
		return this;
	}

	/**
	 * @return the loginArea
	 */
	public String getLoginArea() {
		return loginArea;
	}

	/**
	 * @param loginArea the loginArea to set
	 */
	public MemberLoginLogDto setLoginArea(String loginArea) {
		this.loginArea = loginArea;
		return this;
	}

	/**
	 * @return the logoutTime
	 */
	public String getLogoutTime() {
		return logoutTime;
	}

	/**
	 * @param logoutTime the logoutTime to set
	 */
	public MemberLoginLogDto setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
		return this;
	}

	/**
	 * @return the accessTime
	 */
	public String getAccessTime() {
		return accessTime;
	}

	/**
	 * @param accessTime the accessTime to set
	 */
	public MemberLoginLogDto setAccessTime(String accessTime) {
		this.accessTime = accessTime;
		return this;
	}
	
	/**
	 * 匿名用户
	 */
	public MemberLoginLogDto setAnonymous() {
		this.setMemberId("AM");
		this.setCreateByUserCode("AM");
		this.setCreateByUserName("AM");
		this.setLastModifyByUserCode("AM");
		this.setLastModifyByUserName("AM");
		return this;
	}
	
}

