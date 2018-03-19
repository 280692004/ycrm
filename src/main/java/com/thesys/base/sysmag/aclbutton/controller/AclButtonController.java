package com.thesys.base.sysmag.aclbutton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thesys.architecture.core.dto.ResultDataDto;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclbutton.domain.state.AclButtonEvent;
import com.thesys.base.sysmag.aclbutton.service.AclButtonService;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.aclmodule.service.AclModuleService;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;
@Controller
@RequestMapping("/base/basemanager/sysmag/aclbutton")
@SuppressWarnings("all")
public class AclButtonController extends BaseController<AclButton> {
	
	@Autowired
	private AclButtonService aclButtonService;
	@Autowired
	private AclModuleService aclModuleService;
	
    @InitBinder("aclButton")
    public void initBinder1(WebDataBinder binder){
    	binder.setFieldDefaultPrefix("aclButton.");
    }	

    /**
     * 按钮列表
     * @return
     */
    @RequestMapping(value="/listAclButton")
    public @ResponseBody JqGridResultDataDto list(@ModelAttribute("aclButton")AclButton aclButton,Integer pageSize, Integer pageNumber){
		JqGridResultDataDto showListData = aclButtonService.findFlexiPageAclButtons(aclButton,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
    }
	
	/**
	 * 保存
	 * @param aclModule
	 * @return
	 */
	@RequestMapping(value="/addOrUpdateAclButton")
    public @ResponseBody ResultDataDto doOpUpDateAclRole(@RequestParam("oper")String oper,@ModelAttribute("aclButton")AclButton aclButton){
    	ResultDataDto resultDataDto = null;
		if("del".equals(oper)){
			aclButtonService.deleteById(ValidateUtil.format("id", aclButton));
    		ResultDataDto.addDeleteSuccess();
    	}else{
    		if(ValidateUtil.isEmpty("id",aclButton)){
    			aclButtonService.addEntity(aclButton);
    			resultDataDto = new ResultDataDto().addAddSuccess();
    		}else{
    			aclButtonService.updateEntity(aclButton);
    			resultDataDto = new ResultDataDto().addUpdateSuccess();
    		}
    	}
    	
		return resultDataDto;
    }

	/**
	 * 根据id查询按钮信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findAclButton")
	public @ResponseBody ResultDataDto findAclButtonById(String id){
		AclButton found=this.aclButtonService.findAclButtonsById(id);
		return new ResultDataDto(found);
	}
	
	/**
	 * 按钮确认
	 * @param aclModule
	 * @return
	 */
	@RequestMapping(value="/confirmAclButton")
	public @ResponseBody ResultDataDto confirm(@ModelAttribute("aclButton")AclButton aclButton){
		aclButtonService.updateAclButtonStatus(aclButton.getId().toString(),AclButtonEvent.CONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	/**
	 * 按钮反确认
	 * @param aclModule
	 * @return
	 */
	@RequestMapping(value="/antiConfirmAclButton")
	public @ResponseBody ResultDataDto anticonfirm(@ModelAttribute("aclButton")AclButton aclButton){
		
		aclButtonService.updateAclButtonStatus(aclButton.getId().toString(),AclButtonEvent.ANTICONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	
	/**
	 * 返回界面参数
	 * @param modelId
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/findAllAclModuleParams")
	public @ResponseBody ResultDataDto findAllAclModuleParams(){
		List<AclModule> details = this.aclModuleService.findAll();
		return new ResultDataDto(details);
	}
	
	/**
	 * 根据角色id查询模块
	 * @param modelId
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/findAclModuleButDatas")
	public @ResponseBody ResultDataDto findAclModuleButDatas(String modelId,String roleId){
		List<AclButton> details = this.aclButtonService.findAclButtonsByModelIdAndRoleId(modelId,roleId);
		return new ResultDataDto(details);
	}
	
}
