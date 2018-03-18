package com.thesys.util.pdfexport.tablelist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.thesys.base.core.util.OgnlUtil;
import com.thesys.base.core.util.Pair;
import com.thesys.base.core.util.Triple;
import com.thesys.base.core.util.ValidateUtil;

/**
 * 导出PDF工具类
 * 此工具类 为纯Java编写。
 * @author Kyle
 *
 */
public class ExportPdfForTableListUtil {

	/**
	 * 8号字体 
	 */
	private static Font pdf8Font = null;
	/**
	 * 20号字体 
	 */
	private static Font pdf20Font = null;
	
	
	/**
	 *  创建PDF
	 * @param pdfDocumentInfoDto
	 * @return
	 */
	public  boolean exportTableContent(PdfDocumentInfoDto documentInfo) {
		
		Document pdfDocument = new Document(documentInfo.getPageSize(), 50, 50, 50, 50);
		
		try {
			// 构建一个PDF文档输出流程
			FileOutputStream pdfFileOutputStream = new FileOutputStream(new File(documentInfo.getFullFilePath()));
			PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument,pdfFileOutputStream);
			// 设置作者信息
			setCreatorInfo(pdfDocument, documentInfo.getTitle(), documentInfo.getAuthor(), 
					documentInfo.getSubject(), documentInfo.getKeywords(), documentInfo.getCreator());
			
			// 设置文件只读权限 释放报错 需要查
			// PdfFileExportUtil.setReadOnlyPDFFile(pdfWriter);
			// 通过PDF页面事件模式添加文字水印功能
			pdfWriter.setPageEvent(new TextWaterMarkPdfPageEvent(documentInfo.getTextWaterMark()));//文字水印 "恒捷供应链信息技术"
			// 通过PDF页面事件模式添加图片水印功能
			String waterMarkFullFilePath = documentInfo.getImgWaterMarkFilePath();// 水印图片"e:/logo.jpg";
			if(!ValidateUtil.isEmpty(waterMarkFullFilePath)){
				pdfWriter.setPageEvent(new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
			}
			// 通过PDF页面事件模式添加页头和页脚信息功能
			pdfWriter.setPageEvent(new HeadFootInfoPdfPageEvent(documentInfo.getHeadAndFootfondSize(), documentInfo.getLeftHeadContent(),
					documentInfo.getRithtHeadContent(), documentInfo.getLeftFootdContent(), documentInfo.getRithtFootContent(),
					documentInfo.getIsShowPageNumber(), documentInfo.getPageNumberPosition()));
			// 设置中文字体和字体样式
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			//数据列表表头字体大小
			Font titleChinese = new Font(bfChinese, documentInfo.getTableListTitleFontSize(), Font.BOLD); 
			//数据列表字体大小
			Font tableListChinese = new Font(bfChinese, 10, Font.NORMAL);
			// 打开PDF文件流
			pdfDocument.open();
			
			//编写数据抬头部分，
			for (Paragraph headInformation: documentInfo.getHeadInformations()) {
				pdfDocument.add(headInformation);
			}
			
			// 创建一个N列的表格控件
			PdfPTable pdfTable = new PdfPTable(documentInfo.getTableTitelContentAndField().size());
			
			if (!ValidateUtil.isEmpty(documentInfo.getTableCellWidths())) {
				pdfTable.setWidths(documentInfo.getTableCellWidths());
			}
			// 设置表格占PDF文档100%宽度
			pdfTable.setWidthPercentage(100);
			// 水平方向表格控件左对齐
			pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			// 创建一个表格的表头单元格
			PdfPCell pdfTableHeaderCell = new PdfPCell();
			// 设置表格的表头单元格颜色
			pdfTableHeaderCell.setBackgroundColor(documentInfo.getBaseColor());
			pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableHeaderCell.setFixedHeight(20);
			pdfTableHeaderCell.setPaddingTop(3);
			for (Triple<String, String, Pair<String, String>> tableTitelContentAndField : documentInfo.getTableTitelContentAndField()) {
				//表头
				String tableHeaderContext = tableTitelContentAndField.getLeft();
				pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderContext, titleChinese));
				pdfTable.addCell(pdfTableHeaderCell);
			}
			// 创建一个表格的正文内容单元格
			PdfPCell pdfTableContentCell = new PdfPCell();
			pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			// 表格内容行数的填充
			int rowsNumber = documentInfo.getTableListDatas().size();
			
			int i = 0;
			
			for (Object t : documentInfo.getTableListDatas()) {
				for (Triple<String, String, Pair<String, String>> tableTitelContentAndField : documentInfo.getTableTitelContentAndField()) {
					
					String showFiled = tableTitelContentAndField.getMiddle();
					String showValue = "";
					Boolean notForMat = true;
					if(!ValidateUtil.isEmpty(tableTitelContentAndField.getRight()) && ExportPdfFieldFormat.FORMAT_TYPE_DATE.equals(tableTitelContentAndField.getRight().getLeft())){
						SimpleDateFormat sdf = new SimpleDateFormat(tableTitelContentAndField.getRight().getRight());
						if(!ValidateUtil.isEmpty(showFiled, t)){
							showValue = sdf.format((Date)OgnlUtil.evalOgl(showFiled, t));
							notForMat = false;
						}
					}
					
					if(!ValidateUtil.isEmpty(tableTitelContentAndField.getRight()) && ExportPdfFieldFormat.FORMAT_TYPE_NUMBER.equals(tableTitelContentAndField.getRight().getLeft())){
						if(!ValidateUtil.isEmpty(showFiled, t)){
							showValue = doOpNumberformat((Double)OgnlUtil.evalOgl(showFiled, t),tableTitelContentAndField.getRight().getRight());
							notForMat = false;
						}
					}
					if(notForMat){
						showValue = ValidateUtil.format(showFiled, t);
					}
					pdfTableContentCell.setPhrase(new Paragraph(showValue, tableListChinese));
					pdfTable.addCell(pdfTableContentCell);
				}
				
				// 表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
				if ((i % documentInfo.getSubmitAmount()) == 0) {
					pdfDocument.add(pdfTable);
					pdfTable.deleteBodyRows();
				} else if (i == rowsNumber) {
					// 如果全部类容完毕且又没达到某个行数限制，则也要写入物理文件中。
					pdfDocument.add(pdfTable);
					pdfTable.deleteBodyRows();
				}
				
				i++;
			}
			
			//编写数据结尾部分，
			for (Paragraph footInformation: documentInfo.getFootInformations()) {
				pdfDocument.add(footInformation);
			}
			
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
	 * 获取中文字符集且是8号字体，常用作表格内容的字体格式
	 * 
	 * @param fullFilePath
	 */
	public static Font getChinese8Font() throws DocumentException, IOException {
		if (pdf8Font == null) {
			// 设置中文字体和字体样式
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			pdf8Font = new Font(bfChinese, 8, Font.NORMAL);
		}
		return pdf8Font;
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
	 * 设置成只读权限
	 * 
	 * @param pdfWriter
	 */
	public static PdfWriter setReadOnlyPDFFile(PdfWriter pdfWriter)throws DocumentException {
		
		pdfWriter.setEncryption(null, null, PdfWriter.ALLOW_PRINTING,PdfWriter.STANDARD_ENCRYPTION_128);
		return pdfWriter;
		
	}
	
	/**
	 * 变更一个图片对象的展示位置和角度信息
	 * 
	 * @param waterMarkImage
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	public static Image getWaterMarkImage(Image waterMarkImage,float xPosition, float yPosition) {
		
		waterMarkImage.setAbsolutePosition(xPosition, yPosition);// 坐标
		waterMarkImage.setRotation(-20);// 旋转 弧度
		waterMarkImage.setRotationDegrees(-45);// 旋转 角度
		waterMarkImage.scalePercent(100);// 依照比例缩放
		return waterMarkImage;
		
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
	 * 为PDF分页时创建添加图片水印的事件信息
	 */
	class PictureWaterMarkPdfPageEvent extends PdfPageEventHelper {

		private String waterMarkFullFilePath;
		private Image waterMarkImage;

		public PictureWaterMarkPdfPageEvent(String waterMarkFullFilePath) {
			this.waterMarkFullFilePath = waterMarkFullFilePath;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			try {
				float pageWidth = document.right() + document.left();// 获取pdf内容正文页面宽度
				float pageHeight = document.top() + document.bottom();// 获取pdf内容正文页面高度
				PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
				// 仅设置一个图片实例对象，整个PDF文档只应用一个图片对象，极大减少因为增加图片水印导致PDF文档大小增加
				if (waterMarkImage == null) {
					waterMarkImage = Image.getInstance(waterMarkFullFilePath);
				}
				// 添加水印图片，文档正文内容采用横向三列，竖向两列模式增加图片水印
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,
						pageWidth * 0.2f, pageHeight * 0.1f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,
						pageWidth * 0.2f, pageHeight * 0.4f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,
						pageWidth * 0.2f, pageHeight * 0.7f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,
						pageWidth * 0.6f, pageHeight * 0.1f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,
						pageWidth * 0.6f, pageHeight * 0.4f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage,
						pageWidth * 0.6f, pageHeight * 0.7f));
				PdfGState gs = new PdfGState();
				gs.setFillOpacity(0.2f);// 设置透明度为0.2
				waterMarkPdfContent.setGState(gs);
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
	 * 数字千位符显示
	 * @param formatStr 转换格式
	 * @param number
	 * @return
	 */
	public static String doOpNumberformat(Double number,String formatStr) {
		DecimalFormat df = new DecimalFormat(formatStr);
		return df.format(number);
	}
	
}
