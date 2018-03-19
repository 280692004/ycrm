package com.thesys.base.sysmag.aclrole.controller;

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
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.base.sysmag.aclrole.domain.state.AclRoleEvent;
import com.thesys.base.sysmag.aclrole.dto.AclRoleDto;
import com.thesys.base.sysmag.aclrole.service.AclRoleService;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;

@Controller
@RequestMapping("/base/basemanager/sysmag/aclrole")
public class AclRoleController extends BaseController<AclUser> {
	@Autowired
	private AclRoleService aclRoleService;
	
	
	@InitBinder("aclRole")
	public void initBinder1(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("aclRole.");
	}	
	
    /**
     * 查询
     * @param aclRole
     * @param rows
     * @param page
     * @return
     */
	//@Secured({"ROLE_ACLROLE_QUERY"})
	@SuppressWarnings("all")
	@RequestMapping(value="/listAclRole")
	public @ResponseBody JqGridResultDataDto list(@ModelAttribute("aclRole")AclRole aclRole,Integer pageSize, Integer pageNumber){
		JqGridResultDataDto showListData = aclRoleService.findFlexiPageAclRoles(aclRole,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
	}    
	
	/**
	 * 根据Id 查询模块
	 * @param quotation
	 * @return
	 */
	@Secured({"ROLE_ACLROLE_SEE"})
	@RequestMapping(value="/findAclRole")
	public @ResponseBody ResultDataDto find(String id){
		
		AclRole found= this.aclRoleService.findEntityById(id);		
		AclRoleDto aclModuleDto = new AclRoleDto().setAclRole(found);
		
		return new ResultDataDto(aclModuleDto);
	}
	
	/**
	 *1、角色修改成功
	 *2、根据角色查找用户
	 *3、根据用户更新菜单
	 * @param aclModule
	 * @return
	 */
	//@Secured({"ROLE_ACLROLE_UPDATE"})
	@SuppressWarnings("static-access")
	@RequestMapping("/doOpUpDateAclRole")
    public @ResponseBody ResultDataDto doOpUpDateAclRole(@RequestParam("oper")String oper,@ModelAttribute("aclRole")AclRole aclRole){
    	ResultDataDto resultDataDto = null;
		if("del".equals(oper)){
			aclRoleService.deleteById(ValidateUtil.format("id", aclRole));
    		ResultDataDto.addDeleteSuccess();
    	}else{
    		if(ValidateUtil.isEmpty("id",aclRole)){
    			aclRoleService.addEntity(aclRole);
    			resultDataDto = new ResultDataDto().addAddSuccess();
    		}else{
    			aclRoleService.updateEntity(aclRole);
    			resultDataDto = new ResultDataDto().addUpdateSuccess();
    		}
    	}
    	
		return resultDataDto;
    }
	
	/**
	 * 角色审核
	 * @param aclRole
	 * @return
	 */
	//@Secured({"ROLE_ACLROLE_CHECK"})
	@RequestMapping(value="/confirmAclRole")
	public @ResponseBody ResultDataDto confirm(@ModelAttribute("aclRole")AclRole aclRole){
		aclRoleService.updateAclRoleStatus(aclRole.getId().toString(),AclRoleEvent.CONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	/**
	 * 角色作废
	 * @param aclRole
	 * @return
	 */
	//@Secured({"ROLE_ACLROLE_INVALID"})
	@RequestMapping(value="/invalidAclRole")
	public @ResponseBody ResultDataDto invalid(@ModelAttribute("aclRole")AclRole aclRole){
		aclRoleService.logoutAclRole(aclRole.getId());		
		return ResultDataDto.addUpdateSuccess();
	}

	/**
	 * 角色启用
	 * @param aclRole
	 * @return
	 */
	//@Secured({"ROLE_ACLROLE_ENABLE"})
	@RequestMapping(value="/enableAclRole")
	public @ResponseBody ResultDataDto enable(@ModelAttribute("aclRole")AclRole aclRole){
		aclRoleService.enableAclRole(aclRole.getId());		
		return ResultDataDto.addUpdateSuccess();
	}		
	
	
	/**
	 * 查看
	 * @param quotation
	 * @return
	 */
	@Secured({"ROLE_ACLROLE_SEE"})
	@RequestMapping(value="/toViewAclRole")
	public @ResponseBody ResultDataDto toView(@ModelAttribute("aclRole")AclRole aclRole){
		AclRole found= this.aclRoleService.findEntityById(aclRole.getId().toString());
		
		return new ResultDataDto(found);
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------	
}
