package com.thesys.core.architecture.base.authority;


/**
 * 权限控制【根据单据状态控制】
 * @author Kyle
 *
 */
public class BaseIcSuppAuthorityConText {
	
	//------------基础按钮 需要与系统新增模块对应的按钮 code保持一致--------------------
	//删除
	public static final String COMMON_EVENT_DELETE = "delete";
	//修改
	public static final String COMMON_EVENT_UPDATE = "update";
	//确认
	public static final String COMMON_EVENT_CONFIRM = "confirm";
	//反确认
	public static final String COMMON_EVENT_ANTICONFIRM = "anticonfirm";
	//复核
	public static final String COMMON_EVENT_CHECK = "check";
	//反复核  
	public static final String COMMON_EVENT_ANTICHECK = "anticheck";
	//取消 
	public static final String COMMON_EVENT_CANCEL = "cancel";
	//作废
	public static final String COMMON_EVENT_INVALID = "invalid";
	//启用
	public static final String COMMON_EVENT_ENABLE = "enable";
	
	//上传附件
	public static final String COMMON_EVENT_UPLOADFILE = "upLoadFile";
	//下载下载附件
	public static final String COMMON_EVENT_DOWNLOADFILE = "downloadFile";
	//导入 import
	public static final String COMMON_EVENT_IMPORT = "import";
	//导出excel
	public static final String COMMON_EVENT_EXPORTEXCEL = "exportExcel";
	//导出PDF
	public static final String COMMON_EVENT_EXPORTPDF = "exportPdf";
	//-------------------------------------------------------------------
	
	
	//所有状态下都可以
	public static final String FILTER_STATUS_ALL = "all";
	
}
