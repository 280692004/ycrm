package com.thesys.util.pdfexport.tablelist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.thesys.base.core.util.Pair;
import com.thesys.base.core.util.Triple;


public class ExprotPdfTest {

	public static void main(String[] args) {
		
		PdfDocumentInfoDto documentInfo = new PdfDocumentInfoDto();
		documentInfo.setPageSize(PageSize.A2);
		documentInfo.setFullFilePath("e:/评估报告.pdf");
		//数据列表表头  Triple<名字, 对应字段 , Pair<格式化类型(日期/数字), 格式化标准(yyyy-dd-MM/yyMMdd)>>
		List<Triple<String, String , Pair<String, String>>> tableTitelContentAndField = new ArrayList<Triple<String, String , Pair<String, String>>>();
		//定义一个不需要格式化的标准
		Pair<String, String> notFormat = null;
		
		tableTitelContentAndField.add(Triple.create("序号", "seq",notFormat));
		tableTitelContentAndField.add(Triple.create("姓名", "personname", notFormat));
		tableTitelContentAndField.add(Triple.create("报告标题", "reporttitle", notFormat));
		tableTitelContentAndField.add(Triple.create("标题(开发计划)", "worktitle", notFormat));
		tableTitelContentAndField.add(Triple.create("工作状态", "workstatusname", notFormat));
		tableTitelContentAndField.add(Triple.create("开始日期", "sdate", Pair.create(ExportPdfFieldFormat.FORMAT_TYPE_DATE, ExportPdfFieldFormat.TYPE_DATE_6)));
		tableTitelContentAndField.add(Triple.create("结束日期", "edate", Pair.create(ExportPdfFieldFormat.FORMAT_TYPE_DATE, ExportPdfFieldFormat.TYPE_DATE_6)));
		tableTitelContentAndField.add(Triple.create("说明", "remark", notFormat));
		tableTitelContentAndField.add(Triple.create("项目功能评估单号", "devProjectAssessmentNaturalId", notFormat));
		tableTitelContentAndField.add(Triple.create("工时", "workhours", notFormat));
		tableTitelContentAndField.add(Triple.create("测试人员", "testperson", notFormat));
		tableTitelContentAndField.add(Triple.create("测试开始时间", "testsdate", Pair.create(ExportPdfFieldFormat.FORMAT_TYPE_DATE, ExportPdfFieldFormat.TYPE_DATE_6)));
		tableTitelContentAndField.add(Triple.create("测试结束时间", "testedate", Pair.create(ExportPdfFieldFormat.FORMAT_TYPE_DATE, ExportPdfFieldFormat.TYPE_DATE_6)));
		tableTitelContentAndField.add(Triple.create("BUG数量", "bugcount", notFormat));
		tableTitelContentAndField.add(Triple.create("返工次数", "reworkcount", notFormat));
		tableTitelContentAndField.add(Triple.create("客户验收评分", "acceptscore", notFormat));
		
		documentInfo.setTableTitelContentAndField(tableTitelContentAndField);
		
		// 按百分比分配单元格宽带
		int[] tableCellWidths = { 3, 4, 10, 14, 5, 5, 5, 10, 8, 4, 6, 6, 6, 4, 4, 6 };
		documentInfo.setTableCellWidths(tableCellWidths);
		
		//列表数据
		/*List<WeeklyDetail> details = new ArrayList<WeeklyDetail>();
		
		WeeklyDetail detail = new WeeklyDetail();
		details.add(detail);
		documentInfo.setTableListDatas(details);*/
		
		documentInfo.setHeadAndFootfondSize(8L);
		documentInfo.setLeftHeadContent("恒捷供应链数据");
		documentInfo.setRithtHeadContent("sz.恒捷供应链有限公司");
		documentInfo.setIsShowPageNumber(true);
		documentInfo.setPageNumberPosition(1);
		
		documentInfo.setTitle("恒捷供应链数据信息");
		documentInfo.setAuthor("深圳恒捷供应链有限公司");
		documentInfo.setSubject("文件导出的信息安全管控");
		documentInfo.setKeywords("文件导出,信息安全");
		documentInfo.setCreator("kyle");
		
		documentInfo.setTextWaterMark("恒捷机密");
		//documentInfo.setImgWaterMarkFilePath("E:/logo.jpg");
		
		//列表抬头字体大小 默认12
		documentInfo.setHeadInformationFontSize(12L);
		//table列表表头字体大小  默认10
		documentInfo.setTableListTitleFontSize(12L);
		//table列表数据字体大小  默认10
		documentInfo.setTableListFontSize(10L);
		documentInfo.setSubmitAmount(1);
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
		List<Paragraph> headInformations = new ArrayList<Paragraph>();
		BaseFont bfChinese;
		Font headChinese = null;
		try {
			// 设置中文字体和字体样式
			bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			headChinese = new Font(bfChinese, documentInfo.getHeadInformationFontSize(), Font.BOLD); 
			Paragraph headContext1 = new Paragraph("部门：工程部", headChinese);// titleChinese：字体
			headContext1.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
			headContext1.setIndentationRight(50);
			headContext1.setLeading(1f);// 设置行间距 
			headInformations.add(headContext1);
			
	        Paragraph headContext2 = new Paragraph("负责人：周景军", headChinese);// titleChinese：字体
			headContext2.setAlignment(Element.ALIGN_CENTER);// 设置对齐方式 
			headContext2.setLeading(1f);// 设置行间距
			headInformations.add(headContext2);
			
	        Paragraph headContext3 = new Paragraph("总结时间：2018-03-08", headChinese);// titleChinese：字体
	        headContext3.setAlignment(Element.ALIGN_RIGHT);// 设置对齐方式 
	        headContext3.setIndentationRight(50);
	        headContext3.setLeading(1f);// 设置行间距 
	        headInformations.add(headContext3);
	        
	        //换行
	        headInformations.add(new Paragraph("\n"));
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		documentInfo.setHeadInformations(headInformations);
		/**
		 * 添加非列表数据显示数据 
		 * 尾部信息
		 * @return
		 */
		List<Paragraph> footInformations = new ArrayList<Paragraph>();
		
		footInformations.add(new Paragraph("\n"));
		Paragraph footContext = new Paragraph("结论：杠杠的666", headChinese);// titleChinese：字体
		footContext.setAlignment(Element.ALIGN_RIGHT);// 设置对齐方式 
		footContext.setIndentationRight(30);
		footContext.setLeading(5f);// 设置行间距 
        footInformations.add(footContext);
        
		documentInfo.setFootInformations(footInformations);
		/**
		 * 列表数据表头颜色
		 * new BaseColor(213, 141, 69)
		 * @return
		 */
		documentInfo.setBaseColor(new BaseColor(213, 141, 69));
		
		//准备pdf数据
		ExportPdfForTableListUtil createpdf = new ExportPdfForTableListUtil();
		createpdf.exportTableContent(documentInfo);
		System.out.println("完成");
		
		//FileDownloadUtil.downloadFile("e:/评估报告.pdf", "评估报告.pdf", response, false);
	}
}
