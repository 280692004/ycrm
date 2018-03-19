package com.thesys.util.pdfexport.complexdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.thesys.base.core.util.ValidateUtil;

/**
 * 纯java 打印复杂数据
 * 带list 穿插 其他数据
 * @author Administrator
 *
 */
public class ComplexPageExportPdf {
	
	
	/**
	 * 20号字体 
	 */
	private static Font pdf20Font = null;
	
	/**
	 * 从数据库中导出数据并以PDF文件形式存储 在文档页头设置公司信息版权信息 添加公司的文字和图片水印信息
	 * 
	 * @param fullFilePath
	 * @param tableContent
	 * @param rowsNumber
	 * @param submitAmount
	 * @return
	 */
	public boolean exportTableContent(String fullFilePath, int rowsNumber, int submitAmount) {
		Document pdfDocument = new Document(new Rectangle(842F,595F), 50, 50, 50, 50);
		try {
			// 构建一个PDF文档输出流程
			FileOutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
			PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,
					pdfFileOutputStream);
			// 设置作者信息
			// 设置作者信息
			setCreatorInfo(pdfDocument, "titel", "author", "subject", "keywords", "creator");
			// 设置文件只读权限
			// PdfFileExportUtil.setReadOnlyPDFFile(pdfWriter);
			// 通过PDF页面事件模式添加文字水印功能
			pdfWriter.setPageEvent(new TextWaterMarkPdfPageEvent(""));
			// 通过PDF页面事件模式添加图片水印功能
			//String waterMarkFullFilePath = "e:/logo.jpg";// 水印图片
			//pdfWriter.setPageEvent(pdfFileExportUtil.new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
			// 通过PDF页面事件模式添加页头和页脚信息功能
			pdfWriter.setPageEvent(new HeadFootInfoPdfPageEvent(8L, "恒捷供应链数据", "sz.恒捷供应链有限公司", "", "", true, 1));
			// 设置中文字体和字体样式
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font f8 = new Font(bfChinese, 10, Font.NORMAL);
			Font titleChinese = new Font(bfChinese, 16, Font.BOLD); 
			Font tabeltitleChinese = new Font(bfChinese, 12, Font.BOLD); 
			// 打开PDF文件流
			pdfDocument.open();
			
			Paragraph title = new Paragraph("内地海关及香港海关陆路进/出*境载货清单", titleChinese);// 设置标题 
	        title.setAlignment(Element.ALIGN_CENTER);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(title);  
	        pdfDocument.add(new Paragraph("\n"));// 换行
	        String aa="F34567";
	        Paragraph title1 = new Paragraph("车牌号码：     (内地车牌：  "+aa+"    香港车牌："+aa+")", titleChinese);// 设置标题 
	        title.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(title1);  
	        pdfDocument.add(new Paragraph("\n"));// 换行
	        Paragraph title2 = new Paragraph("进/出境日期:  "+aa+"  装货地点："+aa+"  卸货地点："+aa+"         此联载货清单共6页", titleChinese);// 设置标题 
	        title.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(title2);  
	        pdfDocument.add(new Paragraph("\n"));// 换行
	        
	        String [] arrheadcell=new String[]{"项目","货物名称及规格","标记及编号","包装方式及数量","重量/净重*(公斤)","价格(币种)","付货人或货物转运代理名称及地址","收货人名称及地址"};
			int[] tableCellWidths = { 4, 14, 8, 8, 8, 8, 25, 25};
			// 创建一个N列的表格控件
			PdfPTable pdfTable = new PdfPTable(tableCellWidths.length);
			
			if (!ValidateUtil.isEmpty(tableCellWidths)) {
				pdfTable.setWidths(tableCellWidths);
			}
			// 设置表格占PDF文档100%宽度
			pdfTable.setWidthPercentage(100);
			// 水平方向表格控件左对齐
			pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			// 创建一个表格的表头单元格
			PdfPCell pdfTableHeaderCell = new PdfPCell();
			// 设置表格的表头单元格颜色
			pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableHeaderCell.setFixedHeight(20);
			pdfTableHeaderCell.setPaddingTop(3);
			for (String tableHeaderInfo : arrheadcell) {
				pdfTableHeaderCell
						.setPhrase(new Paragraph(tableHeaderInfo, tabeltitleChinese));
				pdfTable.addCell(pdfTableHeaderCell);
			}
			// 创建一个表格的正文内容单元格
			PdfPCell pdfTableContentCell = new PdfPCell();
			pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			String [] contentcell=new String[]{"1","SDFSFSD","1","23","65KG","0.231","SDFDSFSDDS","DSFSDFSDSD"};
			
			// 表格内容行数的填充
			for (int i = 1; i < rowsNumber; i++) {
				for (String tableContentInfo : contentcell) {
					pdfTableContentCell.setPhrase(new Paragraph(
							tableContentInfo, f8));
					pdfTable.addCell(pdfTableContentCell);
				}
				// 表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
				if ((i % submitAmount) == 0) {
					pdfDocument.add(pdfTable);
					pdfTable.deleteBodyRows();
				} else if (i == rowsNumber) {
					// 如果全部类容完毕且又没达到某个行数限制，则也要写入物理文件中。
					pdfDocument.add(pdfTable);
					pdfTable.deleteBodyRows();
				}
			}
			
		
			Paragraph foot = new Paragraph("总件数："+aa+"总体积/总重量："+aa+"货柜箱数量/规格/编号", f8);// 设置标题 
	        title.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(foot);  
	        pdfDocument.add(new Paragraph("\n"));// 换行
	        Paragraph foot1 = new Paragraph("承运公司声明：兹证明，上列货物由____________________________________________公司委托承运，保证无忧。", f8);// 设置标题 
	        title.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(foot1); 
	        
	        Paragraph foot2 = new Paragraph("承运公司名称：______________________ 地址及电话：___________________________________________________ 内地运输公司(盖章):", f8);// 设置标题 
	        title.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(foot2);
	        
	        Paragraph foot3 = new Paragraph("司机姓名：(正楷)：________________ 签名：__________ 日期：__________", f8);// 设置标题 
	        title.setAlignment(Element.ALIGN_LEFT);// 设置对齐方式 
	        title.setIndentationLeft(50);
	        title.setLeading(1f);// 设置行间距 
	        pdfDocument.add(foot3);
	        
	        pdfDocument.add(new Paragraph("\n"));// 换行
	        
	    	PdfPTable footTable = new PdfPTable(4);
	        int[] widths = { 10,10,40,40};
	        footTable.setWidths(widths);
	        footTable.setWidthPercentage(100);  
	        footTable.setSpacingBefore(10); 
	        PdfPCell baseTableCell=new PdfPCell(new Paragraph("合同协议号", f8));
	        baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell); 
            baseTableCell = new PdfPCell(new Paragraph( "", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell); 
            baseTableCell = new PdfPCell(new Paragraph("海关关锁号(条形码)NO", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_LEFT); 
            baseTableCell.setColspan(2);
            baseTableCell.setRowspan(2);
            baseTableCell.setFixedHeight(35);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "监管方式", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "原产国(地区)/ \n 最终目的国", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            
            baseTableCell = new PdfPCell(new Paragraph( "(进境地/启运地)海关批注签章", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setColspan(1);  
            baseTableCell.setRowspan(2);
            baseTableCell.setBorderWidthBottom(0);
            baseTableCell.setFixedHeight(35);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "(指运地/出境地)海关批注签章", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setColspan(1);  
            baseTableCell.setRowspan(2);
            baseTableCell.setBorderWidthBottom(0);
            baseTableCell.setFixedHeight(35);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "车辆海关编号", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "进(出)境地/ \n 指(启)运地", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            baseTableCell.setColspan(1);  
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "关员签名：\n 日     期：", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setColspan(1);  
            baseTableCell.setBorderWidthTop(0);
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
            baseTableCell = new PdfPCell(new Paragraph( "关员签名：\n 日    期：", f8));  
            baseTableCell.setHorizontalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setVerticalAlignment(Element.ALIGN_LEFT);  
            baseTableCell.setColspan(1);  
            baseTableCell.setBorderWidthTop(0);
            baseTableCell.setFixedHeight(30);  
            footTable.addCell(baseTableCell);
	       
            pdfDocument.add(footTable);
	        
			return true;
		} catch (FileNotFoundException de) {
			de.printStackTrace();
			System.err.println("pdf file: " + de.getMessage());
			return false;
		} catch (DocumentException de) {
			de.printStackTrace();
			System.err.println("document: " + de.getMessage());
			return false;
		} catch (IOException de) {
			de.printStackTrace();
			System.err.println("pdf font: " + de.getMessage());
			return false;
		} finally {
			// 关闭PDF文档流，OutputStream文件输出流也将在PDF文档流关闭方法内部关闭
			if (pdfDocument != null) {
				pdfDocument.close();
			}
		}
	}
	
	/**
	 * 获取中文字符集且是20号字体，常用作文字水印信息
	 * 
	 * @param fullFilePath
	 */
	public static Font getChinese20Font() throws DocumentException, IOException {
		if (pdf20Font == null) {
			// 设置中文字体和字体样式
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			pdf20Font = new Font(bfChinese, 20, Font.BOLD);
		}
		return pdf20Font;
	}
	
	/**
	 * 设置PDF创建者信息（文档属性）
	 * @param pdfDocument 
	 * @param title 标题
	 * @param author 作者
	 * @param subject 项目名称
	 * @param keywords 文档关键字信息
	 * @param creator 创建者
	 * @return
	 * 例如：
	 * pdfDocument.addTitle("恒捷供应链数据信息");
	 *	pdfDocument.addAuthor("深圳恒捷供应链有限公司");
	 *	pdfDocument.addSubject("文件导出的信息安全管控");
	 *	pdfDocument.addKeywords("文件导出,信息安全");
	 *	pdfDocument.addCreator("kyle");
	 */
	public static Document setCreatorInfo(Document pdfDocument, String title, 
			String author, String subject, String keywords, String creator) {
		
		if (pdfDocument == null) {
			return null;
		}
		pdfDocument.addTitle(title);
		pdfDocument.addAuthor(author);
		pdfDocument.addSubject(subject);
		pdfDocument.addKeywords(keywords);// 文档关键字信息
		pdfDocument.addCreator(creator);// 应用程序名称
		
		return pdfDocument;
	}
	
	/**
	 * 为PDF分页时创建添加文本水印的事件信息
	 */
	class TextWaterMarkPdfPageEvent extends PdfPageEventHelper {

		private String waterMarkText;

		public TextWaterMarkPdfPageEvent(String waterMarkText) {
			this.waterMarkText = waterMarkText;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			try {
				float pageWidth = document.right() + document.left();// 获取pdf内容正文页面宽度
				float pageHeight = document.top() + document.bottom();// 获取pdf内容正文页面高度
				// 设置水印字体格式
				PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
				Phrase phrase = new Phrase(waterMarkText, getChinese20Font());
				ColumnText.showTextAligned(waterMarkPdfContent,
						Element.ALIGN_CENTER, phrase, pageWidth * 0.25f,pageHeight * 0.2f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent,
						Element.ALIGN_CENTER, phrase, pageWidth * 0.25f,pageHeight * 0.5f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent,
						Element.ALIGN_CENTER, phrase, pageWidth * 0.25f,pageHeight * 0.8f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent,
						Element.ALIGN_CENTER, phrase, pageWidth * 0.65f,pageHeight * 0.2f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent,
						Element.ALIGN_CENTER, phrase, pageWidth * 0.65f,pageHeight * 0.5f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent,
						Element.ALIGN_CENTER, phrase, pageWidth * 0.65f,pageHeight * 0.8f, 45);
			} catch (DocumentException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			} catch (IOException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			}
		}
	}
	
	
	/**
	 * 为PDF分页时创建添加header和footer信息的事件信息
	 */
	class HeadFootInfoPdfPageEvent extends PdfPageEventHelper {
		
		private Long fondSize;//字体大小
		private String leftHeadContent;//页头信息左面
		private String rithtHeadContent;//页头信息右面
		private String leftFootdContent;//页尾信息左面
		private String rithtFootContent;//页尾信息右面
		private Boolean isShowPageNumber;//是否显示页数
		private Integer pageNumberPosition;//分页显示位置 0 代表页头中间显示，1代表页尾中间显示
		
		public HeadFootInfoPdfPageEvent(Long fondSize, String leftHeadContent,String rithtHeadContent, String leftFootdContent,
				String rithtFootContent, Boolean isShowPageNumber,Integer pageNumberPosition) {
			this.fondSize = fondSize;
			this.leftHeadContent = leftHeadContent;
			this.rithtHeadContent = rithtHeadContent;
			this.leftFootdContent = leftFootdContent;
			this.rithtFootContent = rithtFootContent;
			this.isShowPageNumber = isShowPageNumber;
			this.pageNumberPosition = pageNumberPosition;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			try {
				PdfContentByte headAndFootPdfContent = writer.getDirectContent();
				headAndFootPdfContent.saveState();
				headAndFootPdfContent.beginText();
				BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
				headAndFootPdfContent.setFontAndSize(bfChinese, fondSize);
				// 文档页头信息设置
				float x = document.top(-20);
				// 页头信息左面
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,leftHeadContent, document.left(),x, 0);
				// 页头信息中间
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,(isShowPageNumber && 0==pageNumberPosition) ? "第" + writer.getPageNumber() + "页":"",
						(document.right() + document.left()) / 2, x, 0);
				// 页头信息右面
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, rithtHeadContent,document.right(), x, 0);
				// 文档页脚信息设置
				float y = document.bottom(-20);
				// 页脚信息左面
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT, leftFootdContent, document.left(), y, 0);
				// 页脚信息中间
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, (isShowPageNumber && 1==pageNumberPosition) ? "第" + writer.getPageNumber() + "页":"",(document.right() + document.left()) / 2, y, 0);
				// 页脚信息右面
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, rithtFootContent, document.right(), y,0);
				headAndFootPdfContent.endText();
				headAndFootPdfContent.restoreState();
			} catch (DocumentException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			} catch (IOException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			}
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 按百分比分配单元格宽带
		ComplexPageExportPdf pdfFileExport = new ComplexPageExportPdf();
		pdfFileExport.exportTableContent("e:/复杂信息.pdf", 3, 1);
		System.out.println("完成");
	}

}