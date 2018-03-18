package com.thesys.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.transformer.XLSTransformer;

import com.thesys.base.core.util.IOUtil;
import com.thesys.base.core.util.ScmsBaseConfiguration;

public class FileDownLoadUtil {
	/**
	 * 获取Excel模板文件路径
	 * @return
	 */
	public static String getExcelTemplatePath() {
		String path = ScmsBaseConfiguration.getWebRootPath() + "iofiles/template/excel/";
		return path;
	}
	
	public static String getWordTemplatePath() {
		String path = ScmsBaseConfiguration.getWebRootPath() + "iofiles/template/word/";
		return path;
	}	
	
	/**
	 * 获取临时文件路径
	 * @return
	 */
	public static String getTempFilePath() {
		String path = ScmsBaseConfiguration.getWebRootPath() + "iofiles/tempfilepath/";
		makeDirExists(path);
		return path;		
	}
	protected static void makeDirExists(String path) {
		IOUtil.makeDirExists(path);
	}	
	
	/**
	 * 下载文件
	 * @param contentType 文件类型
	 * @param fileName 文件名称（浏览器下载时显示） 
	 *  @param bt 文件内容（字节对象）
	 */
	public static void downloadFile(String contentType, String fileName, byte[] bt,HttpServletResponse httpServletResponse) {
		
		IOUtil.revertFile(contentType, fileName, bt, httpServletResponse);
	}
	
	/**
	 * 下载文件
	 * @param contentType 文件类型
	 * @param fileName 文件名称（浏览器下载时显示）
	 * @param file 文件对象
	 */
	public static void downloadFile(String contentType, String fileName, File file,HttpServletResponse httpServletResponse) {
		IOUtil.revertFile(contentType, fileName, file, httpServletResponse);
	}
	
	@SuppressWarnings("unused")
	public static void exportExcel(Map<String,Object> beans,String srcPath,OutputStream os){
        XLSTransformer transformer = new XLSTransformer();
        try {
            //获得模板的输入流
            FileInputStream in = new FileInputStream(srcPath);
            //将beans通过模板输入流写到workbook中
            Workbook workbook =  transformer.transformXLS(in, beans);
            //将workbook中的内容用输出流写出去
            
			Sheet sheet = workbook.getSheetAt(0);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            workbook.write(os);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
