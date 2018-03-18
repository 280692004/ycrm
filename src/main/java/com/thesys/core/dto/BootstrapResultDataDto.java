package com.thesys.core.dto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.NonTransientDataAccessException;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.base.baseentity.BaseAuthority;


/**
 * Bootstrap 查询列表统一返回结果，
 * 因为使用 Bootstraptable 返回结果分页时，返回的结果中必须包含 tatol 跟 rows字段。否则无法显示分页
 */
public class BootstrapResultDataDto {

	/**
	 * 200-成功
	 */
	public final static String CODE_SUCCESS = "200";
	/**
	 * 503-未知其它
	 */
	public final static String CODE_ERROR_OTHER = "503";
	
	public BootstrapResultDataDto() {
		super();
	}
	
	public BootstrapResultDataDto(String code,Integer page,Integer records, Object rows, Long total) {
		super();
		this.code = code;
		this.page = page;
		this.records = records;
		this.rows = rows;
		this.total = total;
	}

	public BootstrapResultDataDto(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public BootstrapResultDataDto(String message) {
		super();
		this.code = CODE_SUCCESS;
		this.message = message;
	}	

	/**
	 * 返回单个实体
	 */
	public<T extends BaseEntity> BootstrapResultDataDto(T entity) {
		super();
		this.code = CODE_SUCCESS;
		this.rows = entity;
	}

	/**
	 * 异常
	 */
	public BootstrapResultDataDto(Exception ex) {
		super();
		this.code = CODE_ERROR_OTHER;
		this.message = getErrorMessage(ex);
		ex.printStackTrace();
	}
	/**
	 * 运行时异常
	 */
	public BootstrapResultDataDto(Throwable tx) {
		super();
		this.code = CODE_ERROR_OTHER;
		this.message = tx.getMessage();
	}
	
	/**
	 * 结果编码
	 */
	private String code;
	
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 页码 json中代表当前页码
	 */
	private Integer page;
	/**
	 *  json中代表数据行总数
	 */
	private Integer records;
	/**
	 * 结果数据，单个实体 或 List<T>
	 */
	private Object rows;
	 /**
     * 查出的总数 json中代表页码总数的数据
     */
	private Long total; 
	/**
	 * 权限集
	 */
	private List<BaseAuthority> authorities = new ArrayList<BaseAuthority>();
	
	private static String getErrorMessage(Exception ex) {
		if (ex instanceof ArithmeticException) {
			return "系统异常：计算错误";
		}
		if (ex instanceof NullPointerException) {
			return "系统异常：输入错误，缺少输入值";
		}
		if (ex instanceof ClassCastException) {
			return "系统异常：类型转换错误";
		}
		if (ex instanceof NegativeArraySizeException) {
			return "系统异常：集合负数";
		}
		if (ex instanceof ArrayIndexOutOfBoundsException) {
			return "系统异常：集合超出范围";
		}
		if (ex instanceof FileNotFoundException) {
			return "系统异常：文件未找到";
		}
		if (ex instanceof NumberFormatException) {
			return "系统异常：输入数字错误";
		}
		if (ex instanceof SQLException) {
			return "系统异常：数据库异常";
		}
		if (ex instanceof NonTransientDataAccessException) {
			return "系统异常：数据库异常";
		}
		if (ex instanceof IOException) {
			return "系统异常：文件读写错误";
		}
		if (ex instanceof NoSuchMethodException) {
			return "系统异常：方法找不到";
		}
		return ex.getMessage();
	}
	
	public boolean isSuccessMessage() {
		return CODE_SUCCESS.equals(code);
	}

	// -------------------------- getter and setter -----------------------------
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getRows() {
		return rows;
	}
	public BootstrapResultDataDto setRows(Object rows) {
		this.rows = rows;
		return this;
	}
	public Long getTotal() {
		return total;
	}
	public BootstrapResultDataDto setTotal(Long total) {
		this.total = total;
		return this;
	}
	public String getMessage() {
		return message;
	}

	public BootstrapResultDataDto setMessage(String message) {
		this.message = message;
		return this;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public List<BaseAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<BaseAuthority> authorities) {
		this.authorities = authorities;
	}
}
