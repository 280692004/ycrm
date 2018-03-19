package com.thesys.util.pdfexport.tablelist;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.thesys.base.core.util.Pair;
import com.thesys.base.core.util.Triple;
import com.thesys.base.core.util.ValidateUtil;

/**
 * 创建Pdf数据传输帮助类
 * @author Kyle
 *
 */
public class PdfDocumentInfoDto {

	/**
	 * pageSize pdf 大小 A2 A4...
	 */
	private Rectangle pageSize;
	/**
	 * fullFilePath 存放路径
	 */
	private String fullFilePath;
	/**
	 * tableTitelContent 导出的列表表头和对应的实体字段
	 */
	private List<Triple<String, String, Pair<String, String>>> tableTitelContentAndField;
	/**
	 * tableListDatas 导出的列表数据
	 */
	private List<T> tableListDatas; 
	/**
	 * tableCellWidths 列表行每列宽度分配比例格式：[20,60,20]
	 */
	private int[] tableCellWidths;
	/**
	 * 表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
	 */
	private int submitAmount;
	/**
	 * 页头页尾字体大小
	 */
	private Long headAndFootfondSize;
	/**
	 * 页头信息左面
	 */
	private String leftHeadContent;
	/**
	 * 页头信息右面
	 */
	private String rithtHeadContent;
	/**
	 * 页尾信息左面
	 */
	private String leftFootdContent;
	/**
	 * 页尾信息右面
	 */
	private String rithtFootContent;
	/**
	 * 是否显示页数
	 */
	private Boolean isShowPageNumber;
	/**
	 * 分页显示位置 0 代表页头中间显示，1代表页尾中间显示
	 */
	private Integer pageNumberPosition;
	/**
	 * PDF页面 字体 水印
	 */
	private String textWaterMark;
	/**
	 * PDF页面 图片 水印 图片路径
	 */
	private String imgWaterMarkFilePath;
	
//-----------设置PDF创建者信息--------------	 
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 项目名称
	 */
	private String subject; 
	/**
	 * 文档关键字信息
	 */
	private String keywords; 
	/**
	 * 创建者
	 */
	private String creator;
	
//-----------设置PDF创建者信息----end----------		
	
	/**
	 * 列表抬头字体大小 默认12
	 * @return
	 */
	private Long headInformationFontSize;
	
	/**
	 * table列表数据字体大小  默认12
	 * @return
	 */
	private Long tableListTitleFontSize;
	/**
	 * table列表数据字体大小  默认10
	 * @return
	 */
	private Long tableListFontSize;
	/**
	 * 添加非列表数据显示数据 
	 * 抬头信息
	 * 
	 * 例如
	 * Font titleChinese = new Font(bfChinese, documentInfo.getHeadAndFootfondSize(), Font.BOLD); 
	 * Paragraph title3 = new Paragraph("完成日期：2017-08-08", titleChinese);// titleChinese：字体
	        title3.setAlignment(Element.ALIGN_RIGHT);// 设置对齐方式 
	        title3.setIndentationRight(50);
	        title3.setLeading(5f);// 设置行间距 
	        pdfDocument.add(title3); 
	        
	        pdfDocument.add(new Paragraph("\n"));// 换行
	 * @return
	 */
	private List<Paragraph> headInformations;
	/**
	 * 添加非列表数据显示数据 
	 * 尾部信息
	 * @return
	 */
	private List<Paragraph> footInformations;
	/**.
	 * 列表数据表头颜色
	 * new BaseColor(213, 141, 69)
	 * @return
	 */
	private BaseColor baseColor;
	
	public Rectangle getPageSize() {
		return pageSize;
	}
	public void setPageSize(Rectangle a2) {
		this.pageSize = a2;
	}
	public String getFullFilePath() {
		return fullFilePath;
	}
	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}
	public List<Triple<String, String, Pair<String, String>>> getTableTitelContentAndField() {
		return tableTitelContentAndField;
	}
	public void setTableTitelContentAndField(
			List<Triple<String, String, Pair<String, String>>> tableTitelContentAndField) {
		this.tableTitelContentAndField = tableTitelContentAndField;
	}
	public List<T> getTableListDatas() {
		return tableListDatas;
	}
	public void setTableListDatas(List<T> tableListDatas) {
		this.tableListDatas = tableListDatas;
	}
	public int[] getTableCellWidths() {
		return tableCellWidths;
	}
	public void setTableCellWidths(int[] tableCellWidths) {
		this.tableCellWidths = tableCellWidths;
	}
	public int getSubmitAmount() {
		return ValidateUtil.isEmpty(submitAmount)?1:submitAmount;
	}
	public void setSubmitAmount(int submitAmount) {
		this.submitAmount = submitAmount;
	}
	public Long getHeadAndFootfondSize() {
		return headAndFootfondSize;
	}
	public void setHeadAndFootfondSize(Long headAndFootfondSize) {
		this.headAndFootfondSize = headAndFootfondSize;
	}
	public String getLeftHeadContent() {
		return ValidateUtil.isEmpty(leftHeadContent)?"":leftHeadContent;
	}
	public void setLeftHeadContent(String leftHeadContent) {
		this.leftHeadContent = leftHeadContent;
	}
	public String getRithtHeadContent() {
		return ValidateUtil.isEmpty(rithtHeadContent)?"":rithtHeadContent;
	}
	public void setRithtHeadContent(String rithtHeadContent) {
		this.rithtHeadContent = rithtHeadContent;
	}
	public String getLeftFootdContent() {
		return ValidateUtil.isEmpty(leftFootdContent)?"":leftFootdContent;
	}
	public void setLeftFootdContent(String leftFootdContent) {
		this.leftFootdContent = leftFootdContent;
	}
	public String getRithtFootContent() {
		return ValidateUtil.isEmpty(rithtFootContent)?"":rithtFootContent;
	}
	public void setRithtFootContent(String rithtFootContent) {
		this.rithtFootContent = rithtFootContent;
	}
	public Boolean getIsShowPageNumber() {
		return isShowPageNumber;
	}
	public void setIsShowPageNumber(Boolean isShowPageNumber) {
		this.isShowPageNumber = isShowPageNumber;
	}
	public Integer getPageNumberPosition() {
		return pageNumberPosition;
	}
	public void setPageNumberPosition(Integer pageNumberPosition) {
		this.pageNumberPosition = pageNumberPosition;
	}
	public String getTextWaterMark() {
		return textWaterMark;
	}
	public void setTextWaterMark(String textWaterMark) {
		this.textWaterMark = textWaterMark;
	}
	public String getImgWaterMarkFilePath() {
		return imgWaterMarkFilePath;
	}
	public void setImgWaterMarkFilePath(String imgWaterMarkFilePath) {
		this.imgWaterMarkFilePath = imgWaterMarkFilePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getHeadInformationFontSize() {
		return ValidateUtil.isEmpty(headInformationFontSize)?12L:headInformationFontSize;
	}
	public void setHeadInformationFontSize(Long headInformationFontSize) {
		this.headInformationFontSize = headInformationFontSize;
	}
	public Long getTableListTitleFontSize() {
		return ValidateUtil.isEmpty(tableListTitleFontSize)?12L:tableListTitleFontSize;
	}
	public void setTableListTitleFontSize(Long tableListTitleFontSize) {
		this.tableListTitleFontSize = tableListTitleFontSize;
	}
	public Long getTableListFontSize() {
		return ValidateUtil.isEmpty(tableListFontSize)?10L:tableListFontSize;
	}
	public void setTableListFontSize(Long tableListFontSize) {
		this.tableListFontSize = tableListFontSize;
	}
	public List<Paragraph> getHeadInformations() {
		return headInformations;
	}
	public void setHeadInformations(List<Paragraph> headInformations) {
		this.headInformations = headInformations;
	}
	public List<Paragraph> getFootInformations() {
		return footInformations;
	}
	public void setFootInformations(List<Paragraph> footInformations) {
		this.footInformations = footInformations;
	}
	public BaseColor getBaseColor() {
		return baseColor;
	}
	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}
	
	
	
	
}
