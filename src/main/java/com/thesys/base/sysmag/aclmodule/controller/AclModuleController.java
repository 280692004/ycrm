package com.thesys.base.sysmag.aclmodule.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
import com.thesys.base.core.util.classify.ClassifyUtil;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.aclmodule.domain.state.AclModuleEvent;
import com.thesys.base.sysmag.aclmodule.dto.AclModuleDto;
import com.thesys.base.sysmag.aclmodule.service.AclModuleService;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.dto.JqGridResultDataDto;
import com.thesys.core.dto.JqGridUiTreeItem;
/**
 * 模块控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/base/basemanager/sysmag/aclmodule" )
public class AclModuleController extends BaseController<AclModule>{

	@Autowired
	private AclModuleService aclModuleService;
	
	
	@InitBinder("aclModule")
	public void initBinder1(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("aclModule.");
	}
    
    /**
     * 查询
     * @param aclModule
     * @param rows
     * @param page
     * @return
     */
	@SuppressWarnings("all")
	@RequestMapping(value="/listAclModule")
	public @ResponseBody JqGridResultDataDto list(@ModelAttribute("aclModule")AclModule aclModule,Integer pageSize, Integer pageNumber){
		JqGridResultDataDto showListData = aclModuleService.findFlexiPageAclModules(aclModule,ValidateUtil.isEmpty(pageSize)?20:pageSize,ValidateUtil.isEmpty(pageNumber)?1:pageNumber);
		return showListData;
	}

	@RequestMapping(value="/findAclModuleNextOrderNo")
	public @ResponseBody ResultDataDto findAclModuleNextOrderNo(String parentId){
		Integer nextindex = this.aclModuleService.doOpCalNextSeq(parentId);
		AclModuleDto aclModuleDto = new AclModuleDto(nextindex);
		
		return new ResultDataDto(aclModuleDto);
	}	
	
	
	/**
	 * 根据code 查询模块
	 * @param quotation
	 * @return
	 */
	@RequestMapping(value="/findAclModule")
	public @ResponseBody ResultDataDto find(String code){
		AclModule found= this.aclModuleService.findAclModulebycode(code);
		List<String> parentIds=aclModuleService.findAclModuleParentIds(code);
		
		AclModuleDto aclModuleDto = new AclModuleDto().setAclModule(found).setParentIds(parentIds);
		
		return new ResultDataDto(aclModuleDto);
	}
	
	@RequestMapping(value="/findAclModuleTreeDatas")
	public @ResponseBody ResultDataDto findAclModuleTreeDatas(){
		List<JqGridUiTreeItem> details = this.aclModuleService.findAclModuleTreeDatas();
		JqGridUiTreeItem result = doOpInitAuthorityAclModuleTreeDatas(details);
		List<JqGridUiTreeItem> resultDetails = new ArrayList<JqGridUiTreeItem>(); 
		resultDetails.add(result);
		return new ResultDataDto(new JqGridUiTreeItem(resultDetails));
	}
	
	/**
	 * 处理权限分配模块Tree结构数据
	 */
	private JqGridUiTreeItem doOpInitAuthorityAclModuleTreeDatas(List<JqGridUiTreeItem> details){
		
		//第一步 对查询出的数据分组
		Map<String, List<JqGridUiTreeItem>> map = ClassifyUtil.classify(details, "parentId");
		//根据Id分组,为了取数
		final Map<String, List<JqGridUiTreeItem>> idmap = ClassifyUtil.classify(details, "id");
		//对模块进行排序
		List<Object> idArr = Arrays.asList(map.keySet().toArray());
		
		//处理子集
		for (Object modelId : idArr) {
			//root节点直接跳过
			if(new Integer(modelId.toString())<1){
				continue;
			}
			//获取操作对象
			JqGridUiTreeItem obj = idmap.get(modelId.toString()).get(0);
			//获取操作对象的子节点
			List<JqGridUiTreeItem> nodes = map.get(modelId);
			Collections.sort(nodes, new Comparator<JqGridUiTreeItem>(){
				@Override
				public int compare(JqGridUiTreeItem o1, JqGridUiTreeItem o2) {
					return o1.getOrderNo().compareTo(o2.getOrderNo());
				}			
			});
			obj.setNodes(nodes);
		}
		
		return map.get("0").get(0);
	}
	
    
	
	/**
	 * 保存
	 * @param aclModule
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/doOpUpDateAclModule")
	 public @ResponseBody ResultDataDto doOpUpDateAclModule(@RequestParam("oper")String oper,@ModelAttribute("aclModule")AclModule aclModule){
    	ResultDataDto resultDataDto = null;
		if("del".equals(oper)){
			aclModuleService.deleteById(ValidateUtil.format("id", aclModule));
    		ResultDataDto.addDeleteSuccess();
    	}else{
    		if(ValidateUtil.isEmpty("id",aclModule)){
    			aclModuleService.addEntity(aclModule);
    			resultDataDto = new ResultDataDto().addAddSuccess();
    		}else{
    			aclModuleService.updateEntity(aclModule);
    			resultDataDto = new ResultDataDto().addUpdateSuccess();
    		}
    	}
    	
		return resultDataDto;
    }
	/**
	 * 模块审核
	 * @param aclModule
	 * @return
	 */
	@RequestMapping(value="/confirmAclModule")
	public @ResponseBody ResultDataDto confirm(@ModelAttribute("aclModule")AclModule aclModule){
		aclModuleService.updateAclModuleStatus(aclModule.getCode(),AclModuleEvent.CONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	
	/**
	 * 模块反审核
	 * @param aclModule
	 * @return
	 */
	@RequestMapping(value="/antiConfirmAclModule")
	public @ResponseBody ResultDataDto anticonfirm(@ModelAttribute("aclModule")AclModule aclModule){
		aclModuleService.updateAclModuleStatus(aclModule.getCode(),AclModuleEvent.ANTICONFIRM);		
		return ResultDataDto.addUpdateSuccess();
	}
	

	
	/**
	 * 查看
	 * @param quotation
	 * @return
	 */
	@RequestMapping(value="/toViewAclModule")
	public @ResponseBody ResultDataDto toView(@ModelAttribute("aclModule")AclModule aclModule){
		
		AclModule found= this.aclModuleService.findAclModulebycode(aclModule.getCode());
		
		return new ResultDataDto(found);
	}	
	
	/**
	 * 查询所有模块 新增，修改 父模块参数 下拉参数
	 */
	@RequestMapping("/getParentSelectParam")
    public @ResponseBody ResultDataDto getParentSelectParam(){
    	List<AclModule> list = this.aclModuleService.findAll();
    	StringBuffer countries = new StringBuffer();
    	for (AclModule parameter : list) {
    		countries = countries.append(parameter.getId()).append(":").append(parameter.getCname()).append(";");
		}
    	return new ResultDataDto(countries.length()>0?countries.substring(0, countries.length()-1):countries.toString());
    }
	
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
}
