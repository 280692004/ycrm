package com.thesys.util.pdfexport.sleeveprinting;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/*
 *	实现套打，把套打的格式当成一张图片，将要填入的数据按其在图片在坐标来定位*
 * 页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点
 * 
 * 
 * */

public class SleevePrinting implements Printable{

   private String[] value = null;// 所要打印的数据{ "001", "002", "003"};
   private int[][] position = null;// 每个数据在图片中的坐标 { { 10, 50 }, { 30, 70 }, { 50,  90 }};

   /**
    * implements Printable
    * PageFormat类描述了页面的定位信息、它的尺寸以及每英尺可绘制的区域（单位1/72nd）。
    */

   public int print(Graphics g, PageFormat pf, int pageIndex) {

       System.out.println(pageIndex);
       // 只打一页
       if (pageIndex > 0) {
           return Printable.NO_SUCH_PAGE;
       }

       Graphics2D g2d = (Graphics2D) g;

       for (int i = 0; i < value.length; i++) {

           String str = value[i];
           int[] temp = position[i];
           int x = temp[0];
           int y = temp[1];
          // 设置打印字体（字体名称、样式和点大小）
           Font font = new Font("新宋体", Font.BOLD,12);
           g2d.setFont(font); //设置字体
           g2d.drawString(str, x, y);

       }

       return Printable.PAGE_EXISTS;

    }
   
   /**
    * 测试
    * @param args
    */
   public static void main(String[] args) {
       printReport();
    }

   /**
    * 实现套打
    */
   public static void  printReport(){

       PrinterJob pj = PrinterJob.getPrinterJob();//创建一个打印任务
       PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
       Paper paper = pf.getPaper();
       // 设置页面高和宽，A4纸为595,842
       double pageWidth = 595;
       double pageHeight = 822;
       paper.setSize(pageWidth, pageHeight);
       paper.setImageableArea(0, 0, pageWidth, pageHeight);
       pf.setOrientation(PageFormat.LANDSCAPE); //设置打印方向，LANDSCAPE为横向，打印方向默认为纵向
       pf.setPaper(paper);
       
       SleevePrinting  printTest=new SleevePrinting();
       printTest.setValue(new String []{" 粤BBF938", "JL7255", "2018-03-08","屯门洪祥路三号田氏中心","深圳宝安石岩","6","20","易能电子有限公司","深圳市恒捷供应链有限公司","420","3248"});
       	/**
       	 * 粤BBF938
       	 * 1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点
       	 * 横向打印(x,y)(165,150) x算法(可以用尺子量出来58mm 58/25.4*72=165) y算法((可以用尺子量出来53mm 53/25.4*72=150))
       	 * 纵向打印(x,y)(150,165) x算法((可以用尺子量出来53mm 53/25.4*72=150))y算法(可以用尺子量出来58mm 58/25.4*72=165)
       	 */
       printTest.setPosition(new int [][]{{ 165, 150 }, { 326,150}, { 121,170},{ 255,170},
    		   { 442,170},{ 677,170},{ 309,221},{ 439,221},{ 595,221},{ 91,340},{ 238,340}});

       pj.setPrintable(printTest, pf);
       
       if (pj.printDialog()) { //弹出打印对话框，打印对话框，用户可以通过它改变各种选项，例如：设置打印副本数目，页面方向，或者目标打印机。
           try {
                pj.print();
           } catch (PrinterException e) {
                e.printStackTrace();
           }
       }
    }

   /**
    * @return 返回 position。
    */
   public int[][] getPosition() {
       return position;
    }

   /**
    * @param position
    *  要设置的 position。
    */
   public void setPosition(int[][] position) {
       this.position = position;
    }

   /**
    * @return 返回 value。
    */
   public String[] getValue() {
       return value;
    }

   /**
    * @param value
    * 要设置的 value。
    */
   public void setValue(String[] value) {
       this.value = value;
    }
}