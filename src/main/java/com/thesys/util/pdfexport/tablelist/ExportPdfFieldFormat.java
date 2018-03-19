package com.thesys.util.pdfexport.tablelist;
/**
 * 导出PDF字段格式化类型
 * @author Kyle
 *
 */
public class ExportPdfFieldFormat {
	
	/**
	 * 数值格式化千位符类型
	 */
	public static final String FORMAT_NUMBER_NotDecimal = ",###,###";
	/**
	 * 数值格式化千位符类型
	 * 保留两位小数
	 */
	public static final String FORMAT_NUMBER_Retain2Decimal = ",###,###.00";
	/**
	 * 数字格式化 使用千位符
	 */
	public static final String FORMAT_TYPE_NUMBER = "NUMBER";
	
	
	/**
	 * 日期格式和
	 */
	public static final String FORMAT_TYPE_DATE = "DATE";
	
	
	/**
	 * 日期格式化：yyyy/MM/dd HH:mm:ss
	 */
	public static final String TYPE_DATE_1="yyyy/MM/dd HH:mm:ss";
	/**
	 * 日期格式化：yyyy/MM/dd HH:mm
	 */
	public static final String TYPE_DATE_2="yyyy/MM/dd HH:mm";
	/**
	 * 日期格式化：yyyy/MM/dd HH:mm:ss
	 */
	public static final String TYPE_DATE_3="yyyy/MM/dd";
	/**
	 * 日期格式化：yyyy-MM-dd HH:mm:ss
	 */
	public static final String TYPE_DATE_4="yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式化：yyyy-MM-dd HH:mm
	 */
	public static final String TYPE_DATE_5="yyyy-MM-dd HH:mm";
	/**
	 * 日期格式化：yyyy-MM-dd
	 */
	public static final String TYPE_DATE_6="yyyy-MM-dd";
	
}
