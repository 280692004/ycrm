package com.thesys.util.pdfexport.nolistdatas;

import java.io.File;

public class TestTempletTicket {

	 public static void main(String[] args) throws Exception {  
         
         Ticket ticket = new Ticket();  
          
         ticket.setTicketId("2016042710000");  
         ticket.setTicketCreateTime("2016年4月27日");  
         ticket.setTicketCompany("武汉日创科技有限公司");  
         ticket.setSysName("智能看护系统");  
         ticket.setMoneyLittle("50,000.00");  
         ticket.setMoneyBig("伍万元整");  
         ticket.setAccountCompany("洪山福利院");  
         ticket.setBedNumber("500床位");  
         ticket.setUsername("qiu");  
         ticket.setPassword("123456");  
          
         PDFTempletTicket pdfTT = new PDFTempletTicket();  
          
         pdfTT.setTemplatePdfPath("e:\\ticket_from.pdf");  
         pdfTT.setTargetPdfpath("e:\\myticket.pdf");  
         pdfTT.setTicket(ticket);  
          
         File file = new File("e:\\myticket.pdf");  
         file.createNewFile();  
         pdfTT.templetTicket(file);  
          
         //OSSConfigureconfig = OSSUtil.getOSSConfigure();  
		//OSSManageUtil.uploadFile(config, file, "aaabbbccc.pdf","pdf", "ticket/" + "aaabbbccc");  
		
		//System.out.println("path = " + config.getAccessUrl());  
          
}  
}
