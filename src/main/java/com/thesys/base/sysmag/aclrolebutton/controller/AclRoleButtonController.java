package com.thesys.base.sysmag.aclrolebutton.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thesys.architecture.core.dto.ResultDataDto;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.classify.ClassifyUtil;
import com.thesys.base.sysmag.aclrolebutton.domain.AclRoleButton;
import com.thesys.base.sysmag.aclrolebutton.service.AclRoleButtonService;
import com.thesys.core.architecture.controller.BaseController;

@Controller
@RequestMapping(value="/base/basemanager/sysmag/aclrolebutton" )
public class AclRoleButtonController extends BaseController<AclRoleButton>{

	@Autowired
	private AclRoleButtonService aclRoleButtonService;
	
	@RequestMapping(value="/addOrUpdateAclRoleButton")
	public @ResponseBody ResultDataDto  addOrUpdate(@RequestParam("aclroleId")String aclroleId, @RequestParam("aclmoduleId")String aclmoduleId, @RequestParam("butIds")String butIds){
		
		//提交的buts
		List<String> butIdArr = doOpValidateAndHanldeRoleButs(aclroleId,aclmoduleId,butIds);
		//需要新增的记录
		List<AclRoleButton> needAddDatas = new ArrayList<AclRoleButton>();
		//需要删除的记录
		List<Object> needDelete = new ArrayList<Object>();
		
		if(butIdArr.isEmpty()){
			//如果为空则删除之前数据
			this.aclRoleButtonService.deleteByRoleIdAndModuleIdAndbutIds(aclroleId,aclmoduleId,null);
		}else{
			//变更前的数据  这里使用mysql 语句直接处理，新增不再的
			List<AclRoleButton> oldAclRoleButtons = this.aclRoleButtonService.findModuleAclRoleButton(new AclRoleButton(Integer.valueOf(aclroleId), Integer.valueOf(aclmoduleId)));
			Map<Integer, List<AclRoleButton>> oldAclRoleButtonMap = ClassifyUtil.classify(oldAclRoleButtons, "button_id");
			
			for (String butId : butIdArr) {
				if(oldAclRoleButtonMap.containsKey(butId)){
					//删除共有的，剩下的就是需要删除的
					oldAclRoleButtonMap.remove(butId);
					continue;
				}
				//新增
				AclRoleButton entity = new AclRoleButton(Integer.valueOf(aclroleId), Integer.valueOf(aclmoduleId));
				entity.setButton_id(Integer.valueOf(butId));
				entity.setPermission(new Integer(1));
				needAddDatas.add(entity);
			}
			if(oldAclRoleButtonMap.size()>0){
				needDelete = Arrays.asList(oldAclRoleButtonMap.keySet().toArray());
			}
		}
		
		//删除
		if(!needDelete.isEmpty()){
			String deleteIds = "";
			for (Object butId : needDelete) {
				deleteIds +=  ((String) butId).concat(",");
			}
			if(!ValidateUtil.isEmpty(deleteIds)){
				deleteIds = deleteIds.substring(0, deleteIds.length()-1);
			}
			
			this.aclRoleButtonService.deleteByRoleIdAndModuleIdAndbutIds(aclroleId,aclmoduleId,deleteIds);
		}
		
		if(!needAddDatas.isEmpty()){
			this.aclRoleButtonService.addAclRoleButton(needAddDatas);
		}
		
		return ResultDataDto.addUpdateSuccess();
	}
	
	/**
	 * 验证分配权限数据，返回按钮id
	 * @return
	 */
	private List<String> doOpValidateAndHanldeRoleButs(String aclroleId,String aclmoduleId,String butIds){
		if(ValidateUtil.isEmpty(aclroleId)){
			throw new RuntimeException("角色key不能为空!");
		}
		if(ValidateUtil.isEmpty(aclmoduleId)){
			throw new RuntimeException("模块key不能为空!");
		}
		
		List<String> resultIds = new ArrayList<String>();
		
		String[] butIdsArr = butIds.split(",");
		if(butIdsArr.length>0){
			resultIds = Arrays.asList(butIdsArr);
		}
		return resultIds;
	}
	
	
}
