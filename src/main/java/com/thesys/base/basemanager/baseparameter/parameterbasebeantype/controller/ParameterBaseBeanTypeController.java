package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.controller;

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
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.ParameterBaseBeanType;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state.ParameterBaseBeanTypeClassDataManager;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state.ParameterBaseBeanTypeEvent;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.service.ParameterBaseBeanTypeService;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;


/**   
 * 类描述：参数类型
 */
@Controller
@RequestMapping("/base/basemanager/baseparameter/parameterbasebeantype")
public class ParameterBaseBeanTypeController extends BaseController<ParameterBaseBeanType> {
	@Autowired
	private ParameterBaseBeanTypeService parameterBaseBeanTypeService;
	@InitBinder("parameterBaseBeanType")
	public void initBinder1(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("parameterBaseBeanType.");		
	}	
    /**
     * 查询参数类型信息
     * @return
     */
	@SuppressWarnings("all")
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_QUERY"})
    @RequestMapping(value="/listParameterBaseBeanType")
	public @ResponseBody JqGridResultDataDto doOplistParameterBaseBeanType(@ModelAttribute("parameterBaseBeanType")ParameterBaseBeanType parameterBaseBeanType,Integer pageSize, Integer pageNumber){
		JqGridResultDataDto showListData = parameterBaseBeanTypeService.findFlexiPageParameterBaseBeanTypes(parameterBaseBeanType,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
    }
	
	
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_UPDATE"})
    @SuppressWarnings("static-access")
	@RequestMapping("/doOpUpDateParameterType")
    public @ResponseBody ResultDataDto doOpUpDateParameterType(@RequestParam("oper")String oper,@ModelAttribute("parameterBaseBeanType")ParameterBaseBeanType parameterBaseBeanType){
    	ResultDataDto resultDataDto = null;
		if("del".equals(oper)){
    		parameterBaseBeanTypeService.deleteById(ValidateUtil.format("id", parameterBaseBeanType));
    		ResultDataDto.addDeleteSuccess();
    	}else{
    		if(ValidateUtil.isEmpty("id",parameterBaseBeanType)){
    			parameterBaseBeanTypeService.addEntity(parameterBaseBeanType);
    			resultDataDto = new ResultDataDto().addAddSuccess();
    		}else{
    			parameterBaseBeanTypeService.updateEntity(parameterBaseBeanType);
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
    @RequestMapping("/findParameterBaseBeanTypeById")
    public @ResponseBody ResultDataDto findById(@RequestParam("id")String id){
    	ParameterBaseBeanType parameterBaseBeanType = this.parameterBaseBeanTypeService.findParameterBaseBeanTypeById(id);
    	return new ResultDataDto(parameterBaseBeanType);
    }
	
	/**
	 * 参数类型审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_CHECK"})
	@RequestMapping(value="/confirmParameterBaseBeanType")
	public @ResponseBody ResultDataDto confirm(@RequestParam("id")Integer id){
		parameterBaseBeanTypeService.updateParameterBaseBeanTypeStatus(id,ParameterBaseBeanTypeEvent.CONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	
	/**
	 * 参数类型反审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_ANTICHECK"})
	@RequestMapping(value="/antiConfirmParameterBaseBeanType")
	public @ResponseBody ResultDataDto anticonfirm(@RequestParam("id")Integer id){
		parameterBaseBeanTypeService.updateParameterBaseBeanTypeStatus(id,ParameterBaseBeanTypeEvent.ANTICONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	  
    /**
     * @Title: getDeptSelect
     * @Description:新增参数select下拉数据
     * @return
     */
    @RequestMapping("/getDtyeSelect")
    public @ResponseBody ResultDataDto getDtyeSelect(){
    	List<ParameterBaseBeanType> list = this.parameterBaseBeanTypeService.getListSingleDtype();
    	StringBuffer countries = new StringBuffer();
    	for (ParameterBaseBeanType parameter : list) {
    		countries = countries.append(parameter.getDtype()).append(":").append(parameter.getCname()).append(";");
		}
    	return new ResultDataDto(countries.length()>0?countries.substring(0, countries.length()-1):countries.toString());
    }
    
    /**
     * @Title: findAllParameterType
     * @Description:查询参数select下拉数据
     * @return
     */
    @RequestMapping("/findAllParameterType")
    public @ResponseBody ResultDataDto findAllParameterType(){
    	List<ParameterBaseBeanType> list = this.parameterBaseBeanTypeService.findAll();
    	return new ResultDataDto(list);
    }
    
    
    /**
     * @Title: 参数状态
     * @Description: TODO(初始化状态下拉框)
     * @return
     */
	protected List<ParameterBaseBean> getParameterbasebeanTypeStatus() {
		StatusNameSortMap statusNameSortMap = ParameterBaseBeanTypeClassDataManager.ClassDataManager_statusmap;
		List<ParameterBaseBean> details = new ArrayList<ParameterBaseBean>();
		details.add(new ParameterBaseBean("","全部"));
		for (String status : statusNameSortMap.getKeyList()) {
			details.add(new ParameterBaseBean(status, statusNameSortMap.get(status)));
		}
		return details;
	}
	
}
