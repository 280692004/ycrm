package com.thesys.base.sysmag.aclmodule.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.architecture.statemachine.ClassDataManagerUtil;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclbutton.service.AclButtonService;
import com.thesys.base.sysmag.aclmenu.service.AclMenuService;
import com.thesys.base.sysmag.aclmodule.controller.AclModuleAuthority;
import com.thesys.base.sysmag.aclmodule.dao.AclModuleMapper;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.aclmodule.domain.state.AclModuleClassDataManager;
import com.thesys.base.sysmag.aclmodule.service.AclModuleService;
import com.thesys.base.sysmag.aclmodule.service.impl.stateowner.AclModuleStateOwner;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;
import com.thesys.core.dto.JqGridUiTreeItem;
@Service("aclModuleService")
@ServiceMapper(AclModuleMapper.class)
public class AclModuleServiceImpl extends BaseServiceImpl<AclModule> implements AclModuleService {
	
	@Autowired
	private AclButtonService aclButtonService;
	
	@Autowired
	private AclMenuService aclMenuService;
	
	@Override
	public List<AclModule> findAll() {
		return getMapper().findAllAclModule();
	}


	//新增的时候界面没有状态和version
	@Override
	public Integer addEntity(AclModule entity) {
		
		doOpDealAclModule(entity,true);
		doOpValidatorAclModule(entity);
		
		return super.addEntity(entity);
	}	


	@Override
	public void updateEntity(AclModule entity) {
		doOpDealAclModule(entity,false);
		doOpValidatorAclModule(entity);
		super.updateEntity(entity);
	}	
	
	private void doOpDealAclModule(AclModule entity,Boolean isAdd) {
		
		if(isAdd){
			entity.setStatus(ValidateUtil.isEmpty(entity.getStatus())?AclModuleClassDataManager.STATE_N:entity.getStatus());
			entity.setVersion(ValidateUtil.isEmpty(entity.getVersion())?0:entity.getVersion());	
		}	
		
		entity.setParent_id(ValidateUtil.isEmpty("parent_id", entity)?null:entity.getParent_id());
		entity.setCreateBy_id(ValidateUtil.isEmpty("createBy_id", entity)?null:entity.getCreateBy_id());
		entity.setLastModifyBy_id(ValidateUtil.isEmpty("lastModifyBy_id", entity)?null:entity.getLastModifyBy_id());		
	}


	@Override
	public void updateAclModuleStatus(Integer id,IEvent event){
		
		AclModule found = this.findEntityById(id.toString());	
		AclModuleStateOwner stateOwner= createStateOwner(found);	
		String status=stateOwner.doOpValidator(event);
		
		this.getMapper().updateAclModuleStatus(found.getId(), status);
	}
	
	@Override
	public AclModule findAclModulebycode(String code){
		return this.getMapper().findAclModulebycode(code);
	}
	
	@Override
	public List<JqGridUiTreeItem> findAclModuleTreeDatas(){
		return this.getMapper().findAclModuleTreeDatas();
	}
	
	
	@Override
	public void deleteById(Integer id){
		
		int iSubModuleCount = this.getMapper().findAclModuleCountByParentId(id);
		if(iSubModuleCount>0){
			throw new RuntimeException("有子模块, 不能删除");
		}
		super.deleteById(id.toString());
		aclButtonService.deleteAclButtonByModuleId(id);
		aclMenuService.deleteAclMenuByModuleId(id);
	}
	

	@Override
	public AclModule findEntityById(String id) {	
		return this.getMapper().findAclModulebyId(id);
	}
	
	@Override
	public Integer doOpCalNextSeq(String parentid){
		return this.getMapper().doOpCalNextSeq(parentid);
	}
	
	@Override
	public List<AclModule> findAclModuleByParentId(String parentid){
		return this.getMapper().findAclModuleByParentId(parentid);	
	}
	
	@Override
	public Boolean doOpCheckHasSubModule(String moduleId){
		Integer startindex= 1;
		Integer nextindex=this.getMapper().doOpCalNextSeq(moduleId);
		
		return !startindex.equals(nextindex);
	}
	
	public  final static int ACLMODULE_MAX_LEVEL=5;
	@Override
	public List<String> findAclModuleParentIds(String code){
		
		List<String> details = new ArrayList<String>();
		
		AclModule  aclModule = this.findAclModulebycode(code);
		int index=0;
		while(!ValidateUtil.isEmpty("parent_id",aclModule)&&index<ACLMODULE_MAX_LEVEL){
			details.add(ValidateUtil.format("parent_id",aclModule));
			aclModule = this.findEntityById(aclModule.getParent_id().toString());
			index++;
		}
		
		return details;
	}
	
	
	@Override
	public JqGridResultDataDto findFlexiPageAclModules(AclModule entity,Integer pageSize, Integer pageNumber) {
		
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().findFlexiPageAclModuleCount(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<AclModule> fonds = getMapper().findFlexiPageAclModules(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(AclModuleAuthority.doOpInitButtons(showListData));
		return showListData;
	}
	
	@SuppressWarnings("all")
	@Override
	protected AclModuleMapper getMapper() {
		return (AclModuleMapper)super.getMapper();
	}
	
	private AclModuleStateOwner createStateOwner(AclModule found) {
		
		String key = getStatusKey();
		
		if (null==ClassDataManagerUtil.getRegisterClassDataManager(key)) {
			ClassDataManagerUtil.registerClassDataManager(AclModuleClassDataManager.class,key);	
		}
		
		return new AclModuleStateOwner(found,"status");
	}
	

	private void doOpValidatorAclModule(AclModule entity) {
		
		if(ValidateUtil.isEmpty("code", entity)){
			throw new RuntimeException("模块编码不能为空");
		}
		if(ValidateUtil.isEmpty("cname", entity)){
			throw new RuntimeException("模块名称不能为空");
		}
		
		if(ValidateUtil.isEmpty("orderNo", entity)){
			throw new RuntimeException("模块显示序号不能为空");
		}		
		
		if(!ValidateUtil.isEmpty("parent_id", entity)){
			validatorAclModuleParent(entity.getParent_id().toString());
		}
		
		int icount=this.getMapper().findAclModuleCountByCode(entity.getCode(), entity.getId());
		
		if(icount>0){
			throw new RuntimeException("模块编码不能重复");
		}
	}
	
	//验证父类是否存在死循环，如果父类超过 ACLMODULE_MAX_LEVEL 级就认为存在死循环
	private void validatorAclModuleParent(String parentId){
		
		AclModule  aclModule = this.getMapper().findAclModulebyId(parentId);
		int index=0;
		while(!ValidateUtil.isEmpty("parent_id",aclModule)){
			aclModule = this.findAclModulebycode(aclModule.getParent().getCode());
			index++;
			if(index>ACLMODULE_MAX_LEVEL){
				throw new RuntimeException("模块的上级存在死循环");
			}
		}
	}
	
	private String getStatusKey(){
		return AclModule.class.getName().toLowerCase().concat("_").concat(AclModuleStateOwner.ACLMODULE_STATEOWNER_KEY);
	}

}
