package com.thesys.dwr;
/**
 * Dwr消息推送 发送类型
 * @author Administrator
 *
 */
public enum DwrSendTypeEnum {

	/**
	 * 强制下线
	 */
	SENDTYPE_COMPULSORYDOWNLINE(0,"您的账号 time 在其他终端上登陆,已被强制下线!"),
	/**
	 * 简单弹框一则消息
	 */
	SENDTYPE_SHOWMODEL(1,"showModel")
	/**
	 * ....待开发
	 */
	;
	
	private Integer sendType;
	private String sendName;
	
	
	
	private DwrSendTypeEnum(Integer sendType, String sendName) {
		this.sendType = sendType;
		this.sendName = sendName;
	}
	
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	
	
}
