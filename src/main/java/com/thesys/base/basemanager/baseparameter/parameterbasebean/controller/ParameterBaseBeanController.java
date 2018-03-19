package com.thesys.base.basemanager.baseparameter.parameterbasebean.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thesys.architecture.core.dto.ResultDataDto;
import com.thesys.architecture.statemachine.StatusNameSortMap;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.state.ParameterBaseBeanClassDataManager;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.state.ParameterBaseBeanEvent;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.service.ParameterBaseBeanService;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;


/**   
 * 类描述：参数类型
 */
@Controller
@RequestMapping("/base/basemanager/baseparameter/parameterbasebean")
public class ParameterBaseBeanController extends BaseController<ParameterBaseBean> {
	@Autowired
	private ParameterBaseBeanService parameterBaseBeanService;
	@InitBinder("parameterBaseBean")
	public void initBinder1(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("parameterBaseBean.");		
	}	
    /**
     * 查询参数类型信息
     * @return
     */
	@SuppressWarnings("all")
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_QUERY"})
    @RequestMapping(value="/listParameterBaseBean")
	public @ResponseBody JqGridResultDataDto doOplistParameterBaseBean(@ModelAttribute("parameterBaseBean")ParameterBaseBean parameterBaseBean,Integer pageSize, Integer pageNumber){
		JqGridResultDataDto showListData = parameterBaseBeanService.findFlexiPageParameterBaseBeans(parameterBaseBean,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
    }
	
	
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_UPDATE"})
    @SuppressWarnings("static-access")
	@RequestMapping("/doOpUpDateParameter")
    public @ResponseBody ResultDataDto doOpUpDateParameter(@RequestParam("oper")String oper,@ModelAttribute("parameterBaseBean")ParameterBaseBean parameterBaseBean){
    	ResultDataDto resultDataDto = null;
		if("del".equals(oper)){
    		parameterBaseBeanService.deleteById(ValidateUtil.format("id", parameterBaseBean));
    		ResultDataDto.addDeleteSuccess();
    	}else{
    		if(ValidateUtil.isEmpty("id",parameterBaseBean)){
    			parameterBaseBeanService.addEntity(parameterBaseBean);
    			resultDataDto = new ResultDataDto().addAddSuccess();
    		}else{
    			parameterBaseBeanService.updateEntity(parameterBaseBean);
    			resultDataDto = new ResultDataDto().addUpdateSuccess();
    		}
    	}
    	
		return resultDataDto;
    }
    
	/**
     * @Title: findById
     * @Description: TODO(根据ID查询)
     * @param id
     * @return
     */
    @Secured({"ROLE_PARAMETERBASEBEANTYPE_READ"})
    @RequestMapping("/findParameterBaseBeanById")
    public @ResponseBody ResultDataDto findById(@RequestParam("id")String id){
    	ParameterBaseBean parameterBaseBean = this.parameterBaseBeanService.findParameterBaseBeanById(id);
    	return new ResultDataDto(parameterBaseBean);
    }
    
	/**
	 * 参数类型审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_CHECK"})
	@RequestMapping(value="/confirmParameterBaseBean")
	public @ResponseBody ResultDataDto confirm(@RequestParam("id")Integer id){
		parameterBaseBeanService.updateParameterBaseBeanStatus(id,ParameterBaseBeanEvent.CONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	
	/**
	 * 参数类型反审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_ANTICHECK"})
	@RequestMapping(value="/antiConfirmParameterBaseBean")
	public @ResponseBody ResultDataDto anticonfirm(@RequestParam("id")Integer id){
		parameterBaseBeanService.updateParameterBaseBeanStatus(id,ParameterBaseBeanEvent.ANTICONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	
	/**
     * @Title: ParentcodeSelectParam
     * @Description:查询新增参数select下拉数据
     * @return
     */
    @RequestMapping("/getParentcodeSelectParam")
    public @ResponseBody ResultDataDto getParentcodeSelectParam(@RequestParam("dtype")String dtype){
    	List<ParameterBaseBean> list = this.parameterBaseBeanService.findParameterBaseBeanByDType(dtype);
    	return new ResultDataDto(list);
    }
	
    
    /**
     * @Title: 参数状态
     * @Description: TODO(初始化状态下拉框)
     * @return
     */
	protected List<ParameterBaseBean> getParameterbasebeanTypeStatus() {
		StatusNameSortMap statusNameSortMap = ParameterBaseBeanClassDataManager.ClassDataManager_statusmap;
		List<ParameterBaseBean> details = new ArrayList<ParameterBaseBean>();
		details.add(new ParameterBaseBean("","全部"));
		for (String status : statusNameSortMap.getKeyList()) {
			details.add(new ParameterBaseBean(status, statusNameSortMap.get(status)));
		}
		return details;
	}
	
}
