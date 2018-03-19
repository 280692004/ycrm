/** 
 *@Title: PDFTempletTicket.java 
 *@Package: org.csun.ns.util 
 *@Description: TODO 
 *@Author: chisj chisj@foxmail.com 
 *@Date: 2016年4月27日上午11:29:52 
 *@Version V1.0 
 */
package com.thesys.util.pdfexport.nolistdatas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * @ClassName: PDFTempletTicket
 * @Description: TODO
 * @Author: chisj chisj@foxmail.com
 * @Date: 2016年4月27日上午11:29:52
 */
public class PDFTempletTicket {

	private String templatePdfPath;
	private String ttcPath;
	private String targetPdfpath;
	private Ticket ticket;

	public PDFTempletTicket() {
		super();
	}

	public PDFTempletTicket(String templatePdfPath, String ttcPath,
			String targetPdfpath, Ticket ticket) {
		this.templatePdfPath = templatePdfPath;
		this.ttcPath = ttcPath;
		this.targetPdfpath = targetPdfpath;
		this.ticket = ticket;
	}

	public void templetTicket(File file) throws Exception {  
                        
	   PdfReader reader = new PdfReader(templatePdfPath);  
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	   PdfStamper ps = new PdfStamper(reader, bos);  
	    
	   /*使用中文字体 */   
	   BaseFont bf= BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H",  
               BaseFont.NOT_EMBEDDED);//创建字体
	   
       ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();   
       fontList.add(bf);   
       AcroFields s = ps.getAcroFields();  
       s.setSubstitutionFonts(fontList);  
       s.setField("ticketId",ticket.getTicketId());  
	   s.setField("ticketCreateTime",ticket.getTicketCreateTime());  
	   s.setField("ticketCompany",ticket.getTicketCompany());  
	   s.setField("sysName",ticket.getSysName());  
	   s.setField("moneyLittle",ticket.getMoneyLittle());  
	   s.setField("moneyBig",ticket.getMoneyBig());  
	   s.setField("accountCompany",ticket.getAccountCompany());  
	   s.setField("bedNumber",ticket.getBedNumber());  
	   s.setField("username",ticket.getUsername());  
	   s.setField("password",ticket.getPassword());  
        
       ps.setFormFlattening(true);  
       ps.close();  
        
       FileOutputStream fos = new FileOutputStream(file);  
       fos.write(bos.toByteArray());  
       fos.close();  
	 }

	/**
	 * @return the templatePdfPath
	 */
	public String getTemplatePdfPath() {
		return templatePdfPath;
	}

	/**
	 * @param templatePdfPath
	 *            the templatePdfPathto set
	 */
	public void setTemplatePdfPath(String templatePdfPath) {
		this.templatePdfPath = templatePdfPath;
	}

	/**
	 * @return the ttcPath
	 */
	public String getTtcPath() {
		return ttcPath;
	}

	/**
	 * @param ttcPath
	 *            the ttcPath to set
	 */
	public void setTtcPath(String ttcPath) {
		this.ttcPath = ttcPath;
	}

	/**
	 * @return the targetPdfpath
	 */
	public String getTargetPdfpath() {
		return targetPdfpath;
	}

	/**
	 * @param targetPdfpath
	 *            the targetPdfpath toset
	 */
	public void setTargetPdfpath(String targetPdfpath) {
		this.targetPdfpath = targetPdfpath;
	}

	/**
	 * @return the ticket
	 */
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * @param ticket
	 *            the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}