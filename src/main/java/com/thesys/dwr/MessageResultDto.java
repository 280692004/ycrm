package com.thesys.dwr;

import java.util.List;

/**
 * 消息推送 信息传递类
 * @author Administrator
 *
 */
public class MessageResultDto {

	/**
	 * 推送消息类型
	 */
	private Integer sendType;
	/**
	 * 展示消息
	 */
	private String message;
	/**
	 * 时间
	 */
	private String sendTime;
	/**
	 * 其他数据
	 */
	private List<Object> details;
	/**
	 * 预留字段，备用字段
	 * 可用于传递临时使用信息
	 */
	private String spare;
	
	public MessageResultDto() {
		super();
	}
	
	public MessageResultDto(Integer sendType, String message, String sendTime) {
		super();
		this.sendType = sendType;
		this.message = message;
		this.sendTime = sendTime;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public List<Object> getDetails() {
		return details;
	}
	public void setDetails(List<Object> details) {
		this.details = details;
	}
	public String getSpare() {
		return spare;
	}
	public void setSpare(String spare) {
		this.spare = spare;
	}
	
}
