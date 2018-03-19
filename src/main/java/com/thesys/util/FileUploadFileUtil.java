package com.thesys.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.thesys.architecture.core.util.IOUtil;
import com.thesys.base.core.common.delimitertype.DelimiterType;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.ValidateUtil;

public class FileUploadFileUtil {
	/**
	 * 在parameter.properties中设置的文件路径前缀 ，目的是把上传文件的路径和程序文件分开,降低程序备份的难度
	 */
	private final static String filebeforepathpropertykey="demanduploadfilebefpath";
	/**
	 * 在parameter.properties中设置的文件路径前缀，目的是把生成的静态的Html文件的路径和程序文件分开,降低程序备份的难度
	 */
	private final static String htmluploadfilepathpropertykey="htmluploadfilepath";
	/**
	 * 文件上传的前面的路径的定义
	 */
	private static String filebeforepath="";
	
	private static String htmluploadfilepath="";
	/**
	 * 5M = 5*1024*
	 */
	@SuppressWarnings("unused")
	private static Integer BUFFER_SIZE= 10*1024*1024;	
	
	/**
	 * @param module 模块名称
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String updoadFileByModule(String module, MultipartFile file){
		String fileName = doOpReplaceFilePrefixName(file.getOriginalFilename(),getRandomName());
		return updoadFileByModuleWithAssignedFileName(module, fileName, true, file);
	}
	
	/**
	 * @param module 模块名称
	 * @param assignedfilename 存储文件名称
	 * @param file
	 * @return
	 */
	public static String updoadFileByModuleWithAssignedFileName(String module,String assignedfilename,Boolean needReplace,MultipartFile file){
    	if(ValidateUtil.isEmpty(file)||ValidateUtil.isEmpty(file.getOriginalFilename())){
    		return null;
    	}
    	
		String filePath = doOpGetUpLoadFilePath(module);
		assignedfilename = needReplace?doOpReplaceFilePrefixName(file.getOriginalFilename(),assignedfilename):file.getOriginalFilename();
		return updoadFile(filePath,assignedfilename, file);
	}	
    /**
     * 上传文件
     * @param filePath 上传文件目录
     * @param fileName 保存的文件名称 
     * @param file 文件流类
     * @param request 请求
     * @throws IOException IO异常
     */
    public static String updoadFile(String filePath,String fileName, MultipartFile file) {
    	if(ValidateUtil.isEmpty(file)||ValidateUtil.isEmpty(file.getOriginalFilename())){
    		return null;
    	}
        //如果文件夹不存在，则创建文件夹
        File dirPath = new File(filePath);
        if(!dirPath.exists()){
            dirPath.mkdir();   
        }
        
        String mkfilename = dirPath +"/"+ fileName;
        File uploadFile = new File(mkfilename);
        try{        	
        	FileCopyUtils.copy(file.getBytes(), uploadFile);	
        }catch(Exception e){
        	throw new RuntimeException(e);
        }
        
        return mkfilename;
            
    }
    
	/**
	 * 为了方便起见，使用单号做为替代的文件名称
	 * 例如上传的test.xls 会转换为PL170503000001.xls
	 * @param filename
	 * @param fileprefixname
	 * @return
	 */
	public static String doOpReplaceFilePrefixName(String filename,String fileprefixname){
		String split= DelimiterType.POINT.getDelimiter();
		int index = filename.lastIndexOf(split);
		
		if(index<0){
			return fileprefixname;
		}
		
		String newfilename = fileprefixname +filename.substring(index);
		
		return newfilename;
	}
	
	/**
	 * 获取模块的路径名称
	 * @param modular
	 * @return
	 */
	public static String doOpGetUpLoadFilePath(String modular){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String stryear= sdf.format(new Date());
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		String strmonth = sdf2.format(new Date());
		String path = doOpGetFileBeforePath() + "iofiles/updownfiles/upload/"+stryear+"/"+strmonth+"/"+modular+"/";
		makeDirExists(path);
		return path;			
	}
	private static String getRandomName(){
		return String.valueOf(System.nanoTime());
	}
	
	private static String doOpGetFileBeforePath(){
		if(!ValidateUtil.isEmpty(filebeforepath)){
			return filebeforepath;
		}
		
		filebeforepath = Configuration.getParameterValue(filebeforepathpropertykey);
		if(ValidateUtil.isEmpty(filebeforepath)){
			throw new RuntimeException("请在parameter.properties设置demanduploadfilebefpath的值");
		}
		if(!filebeforepath.endsWith("/")){
			throw new RuntimeException("请在parameter.properties正确设置demanduploadfilebefpath的值");
		}
		
		return filebeforepath;
	}
	
	
	public static String doOpGetHtmlUploadFilePath(){
		if(!ValidateUtil.isEmpty(htmluploadfilepath)){
			return htmluploadfilepath;
		}
		
		htmluploadfilepath = Configuration.getParameterValue(htmluploadfilepathpropertykey);
		if(ValidateUtil.isEmpty(htmluploadfilepath)){
			throw new RuntimeException("请在parameter.properties设置htmluploadfilepath的值");
		}
		if(!htmluploadfilepath.endsWith("/")){
			throw new RuntimeException("请在parameter.properties正确设置htmluploadfilepath的值");
		}
		
		return htmluploadfilepath;
	}
	
	public static String doOpGetFileSize(File file){
		return dealFileLength(file.length());
	}
	
	private static String dealFileLength(long length){
		Calculate size=Calculate.division(new Calculate(String.valueOf(length)), "1024");
		return size.format(Calculate.FORMAT_SCALE_TOTAL);
	}
	
	protected static void makeDirExists(String path) {
		IOUtil.makeDirExists(path);
	}	
}
