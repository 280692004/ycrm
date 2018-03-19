package com.thesys.core.dto;

import java.util.List;

import com.thesys.architecture.base.entity.BaseEntity;

/**
 * JqGridUiTree帮助类
 * @author Kyle
 *
 */
@SuppressWarnings("serial")
public class JqGridUiTreeItem extends BaseEntity{
	
	/**
	 * 父节点Id
	 */
	private String parentId;
	/**
	 * 节点名称
	 */
	private String text;
	/**
	 * 参数
	 * 编码
	 */
	private String code;
	/**
	 * 模块显示顺序
	 */
	private Integer orderNo;	
	/**
	 * 是否存在子节点
	 */
	private Boolean hasChildren;
	/**
	 * 子集
	 */
	private List<JqGridUiTreeItem> nodes;
	
	public JqGridUiTreeItem() {
	}

	public JqGridUiTreeItem(List<JqGridUiTreeItem> nodes) {
		this.nodes = nodes;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<JqGridUiTreeItem> getNodes() {
		return nodes;
	}

	public void setNodes(List<JqGridUiTreeItem> nodes) {
		this.nodes = nodes;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
}
