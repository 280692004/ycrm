package com.thesys.base.sysmag.acluser.controller;

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
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.domain.AclUserTypeEnum;
import com.thesys.base.sysmag.acluser.domain.state.AclUserClassDataManager;
import com.thesys.base.sysmag.acluser.domain.state.AclUserEvent;
import com.thesys.base.sysmag.acluser.service.AclUserService;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;


/**   
 * 类描述：用户
 */
@Controller
@RequestMapping("/base/basemanager/sysmag/acluser")
public class AclUserController extends BaseController<AclUser> {
	@Autowired
	private AclUserService aclUserService;
	@InitBinder("aclUser")
	public void initBinder1(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("aclUser.");		
	}	
    /**
     * 查询参数类型信息
     * @return
     */
	@SuppressWarnings("all")
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_QUERY"})
    @RequestMapping(value="/listAclUser")
	public @ResponseBody JqGridResultDataDto doOplistAclUser(@ModelAttribute("aclUser")AclUser aclUser,Integer pageSize, Integer pageNumber){
		JqGridResultDataDto showListData = aclUserService.findFlexiPageAclUsers(aclUser,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
    }
	
	
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_UPDATE"})
    @SuppressWarnings("static-access")
	@RequestMapping("/addOrUpdateAclUser")
    public @ResponseBody ResultDataDto addOrUpdateAclUser(@RequestParam("oper")String oper,@ModelAttribute("aclUser")AclUser aclUser){
    	ResultDataDto resultDataDto = null;
		if("del".equals(oper)){
    		aclUserService.deleteById(ValidateUtil.format("id", aclUser));
    		ResultDataDto.addDeleteSuccess();
    	}else{
    		if(ValidateUtil.isEmpty("id",aclUser)){
    			aclUserService.addEntity(aclUser);
    			resultDataDto = new ResultDataDto().addAddSuccess();
    		}else{
    			aclUserService.updateEntity(aclUser);
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
    @RequestMapping("/findAclUserById")
    public @ResponseBody ResultDataDto findById(@RequestParam("id")String id){
    	AclUser aclUser = this.aclUserService.findAclUserById(id);
    	return new ResultDataDto(aclUser);
    }
    
    /**
	 * 参数类型审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_CHECK"})
	@RequestMapping(value="/updatePassWord")
	public @ResponseBody ResultDataDto updatePassWord(@RequestParam("userName")String userName, @RequestParam("oldPwd")String oldPwd, @RequestParam("newPwd")String newPwd){
		//
		/*if(){
			
		}*/
		aclUserService.updatePassWord(newPwd, userName);	
		return ResultDataDto.addUpdateSuccess();
	}
    
	/**
	 * 参数类型审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_CHECK"})
	@RequestMapping(value="/confirmAclUser")
	public @ResponseBody ResultDataDto confirm(@RequestParam("id")String id){
		aclUserService.updateAclUserStatus(id,AclUserEvent.CONFIRM);	
		return ResultDataDto.addUpdateSuccess();
	}
	
	
	/**
	 * 参数类型反审核
	 * @param 
	 * @return
	 */
	//@Secured({"ROLE_PARAMETERBASEBEANTYPE_ANTICHECK"})
	@RequestMapping(value="/antiConfirmAclUser")
	public @ResponseBody ResultDataDto anticonfirm(@RequestParam("id")String id){
		aclUserService.updateAclUserStatus(id,AclUserEvent.ANTICONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	
	/**
     * @Title: ParentcodeSelectParam
     * @Description:查询新增参数select下拉数据
     * @return
     */
    @RequestMapping("/findTyeSelectParams")
    public @ResponseBody ResultDataDto findTyeSelectParams(){
    	
    	StringBuffer countries = new StringBuffer();
    	for (AclUserTypeEnum type : AclUserTypeEnum.values()) {
    		countries = countries.append(type.getTypecode()).append(":").append(type.getTypename()).append(";");
		}
    	return new ResultDataDto(countries.length()>0?countries.substring(0, countries.length()-1):countries.toString());
    }
	
    
    /**
     * @Title: 参数状态
     * @Description: TODO(初始化状态下拉框)
     * @return
     */
	protected List<AclUser> getParameterbasebeanTypeStatus() {
		StatusNameSortMap statusNameSortMap = AclUserClassDataManager.ClassDataManager_statusmap;
		List<AclUser> details = new ArrayList<AclUser>();
		details.add(new AclUser("","全部"));
		for (String status : statusNameSortMap.getKeyList()) {
			details.add(new AclUser(status, statusNameSortMap.get(status)));
		}
		return details;
	}
	
}
