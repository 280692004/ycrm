package com.thesys.allmodule.capitalflow.capitalflow.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.thesys.allmodule.capitalflow.capitalflow.domain.CapitalFlow;
import com.thesys.allmodule.capitalflow.capitalflow.service.CapitalFlowImportService;
import com.thesys.allmodule.capitalflow.capitalflow.service.CapitalFlowService;
import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.ExprotCapitalFlowPdfUtil;
import com.thesys.architecture.core.dto.ResultDataDto;
import com.thesys.base.core.util.DateUtil;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.common.systemmodule.SystemModuleSnConstant;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;
import com.thesys.dwr.DwrSendMessageUtil;
import com.thesys.util.Configuration;
import com.thesys.util.FileDownLoadUtil;
import com.thesys.util.FileUploadFileUtil;
import com.thesys.util.filedownloadutil.FileDownloadUtil;
@Controller
@RequestMapping("/allmodule/capitalflow/capitalflow")
@SuppressWarnings("all")
public class CapitalFlowController extends BaseController<CapitalFlow> {
	
	@Autowired
	private CapitalFlowService capitalFlowService;
	@Autowired
	private CapitalFlowImportService capitalFlowImportService;
	
    @InitBinder("capitalFlow")
    public void initBinder1(WebDataBinder binder){
    	binder.setFieldDefaultPrefix("capitalFlow.");
    }	
    @InitBinder("capitalFlowDto")
    public void initBinder2(WebDataBinder binder){
    	binder.setFieldDefaultPrefix("capitalFlowDto.");
    }	
    
    //模板
  	public static String CAPITALFLOW_EXPORTEMPLATEFILE_PATHNAME = "TemplateCapitalFlow.xls";
  	public static String CAPITALFLOW_EXPORTFILE_PATHNAME = "reportCapitalFlow.xls";

    /**
     * 资金流列表
     * @return
     */
    @RequestMapping(value="/listCapitalFlow")
    public @ResponseBody JqGridResultDataDto list(@ModelAttribute("capitalFlow")CapitalFlow capitalFlow,Integer pageSize, Integer pageNumber){
    	JqGridResultDataDto showListData = capitalFlowService.findFlexiPageCapitalFlows(capitalFlow,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
    }
	
	@RequestMapping(value="/addOrUpdateCapitalFlow")
    public @ResponseBody ResultDataDto doOpUpDateAclRole(@ModelAttribute("creator")String creator, @ModelAttribute("billdate")Date billdate, @RequestBody List<CapitalFlow> details){
		
		this.capitalFlowService.doOpAddOrUpdateEntity(creator,billdate,details);
		
		return ResultDataDto.addAddSuccess();
    }

	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findCapitalFlow")
	public @ResponseBody ResultDataDto findCapitalFlowById(String id){
		CapitalFlow found=this.capitalFlowService.findEntityById(id);
		return new ResultDataDto(found);
	}
	
	/**
	 * 下载Excel模板文件
	 * @return
	 */
	
	@RequestMapping("/doOpexportExcel")
	public @ResponseBody void doOpexportExcel(HttpServletResponse httpServletResponse){		
		
		String outPutFileName = CAPITALFLOW_EXPORTEMPLATEFILE_PATHNAME;
		FileDownLoadUtil.downloadFile("application/octet-stream", outPutFileName, new File(FileDownLoadUtil.getExcelTemplatePath()+"capitalflow/"+outPutFileName),httpServletResponse);		
	
	}	
	
	/**
	 * 导入
	 * @param file
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/importCapitalFlowDatas", method = RequestMethod.POST)
	public void importCapitalFlowDatas(@RequestParam(value = "upFiles", required = false) MultipartFile file, HttpServletResponse response) {
		try {
	        CommonsMultipartFile cf= (CommonsMultipartFile)file; 
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	        File f = fi.getStoreLocation();
	        
			FileInputStream fileInputStream =new FileInputStream(f);
			capitalFlowImportService.importCapitalFlowDatas(fileInputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ResultDataDto.addImportSuccess(response);
	}

	
	/**
	 * 导出
	 */
	@RequestMapping("/exportCapitalFlowForExcel")
	public void exportCapitalFlowForExcel(CapitalFlow entity,HttpServletResponse response) {
		
		List<CapitalFlow> details = this.capitalFlowService.findAllDatasByPageLike(entity);
		Map<String,Object> beanParams=new HashMap<String,Object>();
		beanParams.put("details", details);
		String template= "iofiles/template/excel/capitalflow/" + CAPITALFLOW_EXPORTFILE_PATHNAME;
		 //获得模板路径
		String srcPath =Configuration.getWebRootPath()+template;
		//准备输出流
		OutputStream os = null;
		try {
			String filanem="attachment;filename=" + CAPITALFLOW_EXPORTFILE_PATHNAME;
	           response.setContentType("application/x-excel");
	           response.setHeader("Content-Disposition", filanem);
	           os = response.getOutputStream();
	           FileDownLoadUtil.exportExcel(beanParams,srcPath,os);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	}
	
	
	
	/**
	 * 上传文件【凭证】
	 */
	@RequestMapping(value="/upLoadCapitalFlowFile")	
	public @ResponseBody ResultDataDto upLoadCapitalFlowFile(@RequestParam(value="upFiles",required=false)MultipartFile upFiles,String id) {
		
		CapitalFlow found = this.capitalFlowService.findEntityById(id);
		if(ValidateUtil.isEmpty(upFiles)){
			throw new RuntimeException("上传的附件不能为空");
		}
		String capitalFlowFilePath = FileUploadFileUtil.updoadFileByModule(SystemModuleSnConstant.CAPITALFLOW,upFiles);
		
		found.setFileName(upFiles.getOriginalFilename());
		found.setFilepath(capitalFlowFilePath);
		
		this.capitalFlowService.updateEntity(found);
		
		ResultDataDto resultDataDto=new ResultDataDto("上传成功,文件大小为"+FileUploadFileUtil.doOpGetFileSize(new File(capitalFlowFilePath))+"k");
		
		return resultDataDto;
	}
	
	/**
	 * 下载文件【凭证】
	 */
	@RequestMapping(value="/downloadCapitalFlowFile")	
	public String downloadCapitalFlowFile(String id,HttpServletResponse response) {
		
		CapitalFlow found = this.capitalFlowService.findEntityById(id);
		FileDownloadUtil.downloadFile(found.getFilepath(), found.getFileName(), response, false);
		
		return null;		
	}
	
	
	
	/**
	 * 下载数据 为PDF文件
	 * @return
	 */
	@RequestMapping(value="/downloadCapitalFlowForPdf")	
	public String downloadCapitalFlowForPdf(CapitalFlow entity,HttpServletResponse response) {
		
		List<CapitalFlow> details = this.capitalFlowService.findAllDatasByPageLike(entity);
		String dateStr = DateUtil.format(new Date(),DateUtil.C_DATE_PATTON_NO_TRAVERSE);
		String fileName = "资金流水-".concat(dateStr).concat(".pdf");
		//创建PDF
		String fullFilePath = ExprotCapitalFlowPdfUtil.ExprotPdf(details,fileName);
		FileDownloadUtil.downloadFile(fullFilePath, response, true);
        
		return ResultDataDto.CODE_SUCCESS;
        
	}
	
}
