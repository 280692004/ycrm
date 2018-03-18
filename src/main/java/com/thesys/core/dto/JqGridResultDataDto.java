package com.thesys.core.dto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.NonTransientDataAccessException;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.base.core.util.Pair;


/**
 * Bootstrap 查询列表统一返回结果，
 * 因为使用 Bootstraptable 返回结果分页时，返回的结果中必须包含 tatol 跟 rows字段。否则无法显示分页
 */
public class JqGridResultDataDto {

	/**
	 * 200-成功
	 */
	public final static String CODE_SUCCESS = "200";
	/**
	 * 503-未知其它
	 */
	public final static String CODE_ERROR_OTHER = "503";
	
	public JqGridResultDataDto() {
		super();
	}
	
	public JqGridResultDataDto(String code, Long totalpages, Integer currpage,Long records, Object rows) {
		super();
		this.code = code;
		this.totalpages = totalpages;
		this.currpage = currpage;
		this.records = records;
		this.rows = rows;
	}



	public JqGridResultDataDto(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public JqGridResultDataDto(String message) {
		super();
		this.code = CODE_SUCCESS;
		this.message = message;
	}	

	/**
	 * 返回单个实体
	 */
	public<T extends BaseEntity> JqGridResultDataDto(T entity) {
		super();
		this.code = CODE_SUCCESS;
		this.rows = entity;
	}

	/**
	 * 异常
	 */
	public JqGridResultDataDto(Exception ex) {
		super();
		this.code = CODE_ERROR_OTHER;
		this.message = getErrorMessage(ex);
		ex.printStackTrace();
	}
	/**
	 * 运行时异常
	 */
	public JqGridResultDataDto(Throwable tx) {
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
     * 查出的总页数
     */
	private Long totalpages; 
	/**
	 * 当前页 页码
	 */
	private Integer currpage;
	/**
	 * 查询出的记录数
	 */
	private Long records;
	/**
	 * 包含实际数据的数组
	 */
	private Object rows;
	/**
	 * 行id
	 */
	private Integer id;
	/**
	 *当前行的所有单元格
	 */
	private Object cell;
	/**
	 * 是否有新增权限
	 */
	private Boolean hasAdd;
	/**
	 * 拥有权限的buts
	 */
	private Map<String, Pair<List<String>, List<ButtonDto>>> butsMap;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTotalpages() {
		return totalpages;
	}

	public void setTotalpages(Long totalpages) {
		this.totalpages = totalpages;
	}

	public Integer getCurrpage() {
		return currpage;
	}

	public void setCurrpage(Integer currpage) {
		this.currpage = currpage;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Object getCell() {
		return cell;
	}

	public void setCell(Object cell) {
		this.cell = cell;
	}
	
	public Map<String, Pair<List<String>, List<ButtonDto>>> getButsMap() {
		return butsMap;
	}
	
	public void setButsMap(Map<String, Pair<List<String>, List<ButtonDto>>> butsMap) {
		this.butsMap = butsMap;
	}

	public Boolean getHasAdd() {
		return hasAdd;
	}

	public void setHasAdd(Boolean hasAdd) {
		this.hasAdd = hasAdd;
	}
	
}
