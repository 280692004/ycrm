package com.thesys.base.sysmag.aclbutton.service.impl;

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
import com.thesys.base.sysmag.aclbutton.controller.AclButtonAuthority;
import com.thesys.base.sysmag.aclbutton.dao.AclButtonMapper;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclbutton.domain.state.AclButtonClassDataManager;
import com.thesys.base.sysmag.aclbutton.service.AclButtonService;
import com.thesys.base.sysmag.aclrolebutton.service.AclRoleButtonService;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;
@Service("aclButtonService")
@ServiceMapper(AclButtonMapper.class)
public class AclButtonServiceImpl extends BaseServiceImpl<AclButton> implements AclButtonService {

	@Autowired
	private AclRoleButtonService aclRoleButtonService;
	
	//新增的时候界面没有状态和version
	@Override
	public Integer addEntity(AclButton entity) {
		doOpDealAclButton(entity,true);
		doOpAclUserModuleAndButtonCodeRepeat(entity);
		return super.addEntity(entity);
	}	


	@Override
	public void updateEntity(AclButton entity) {
		doOpDealAclButton(entity,false);
		doOpAclUserModuleAndButtonCodeRepeat(entity);
		super.updateEntity(entity);
	}	
	
	@Override
	public AclButton findAclButtonsById(String id){
		return this.getMapper().findAclButtonsById(id);
	}
	
	@Override
	public List<AclButton> findAclButtonsByModelIdAndRoleId(String modelId,String roleId){
		return this.getMapper().findAclButtonsByModelIdAndRoleId(modelId,roleId);
	}
	
	
	@Override
	public List<AclButton> findAclButtonByModelCode(String code){
		return this.getMapper().findAclButtonByModelCode(code);
	}
	
	@Override
	public List<AclButton> findAclButtonByCodeOrModelId(String code,String modelId){
		return this.getMapper().findAclButtonByCodeOrModelId(code, modelId);
	}
	
	
	@Override
	public JqGridResultDataDto findFlexiPageAclButtons(AclButton entity,Integer pageSize, Integer pageNumber) {
		
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().findFlexiPageAclButtonCount(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<AclButton> fonds = getMapper().findFlexiPageAclButtons(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(AclButtonAuthority.doOpInitButtons(showListData));
		return showListData;
	}
	
	
	@Override
	public List<AclButton> findFlexiPageAclButtons(String param,FlexiPageDto flexiPageDto){
		
		Long count=this.getMapper().findFlexiPageAclButtonByParamCount(param);
		flexiPageDto.setRowCount(count);
		
		return this.getMapper().findFlexiPageAclButtonByParam(param, flexiPageDto);
	}
	
	@Override
	public void deleteAclButtonByModuleId(Integer moduleId){
		
		List<AclButton> details = this.getMapper().findAclButtonsByModelId(String.valueOf(moduleId)); 		
		for(AclButton detail:details){
			this.aclRoleButtonService.deleteAclRoleButtonByButtonId(detail.getId());
			super.deleteById(detail.getId().toString());
		}
	}

	@SuppressWarnings("all")
	@Override
	protected AclButtonMapper getMapper() {
		return (AclButtonMapper)super.getMapper();
	}
	@Override
	public void updateAclButtonStatus(String id, IEvent event) {
		AclButton found = this.findAclButtonsById(id);	
		AclButtonStateOwner stateOwner= createStateOwner(found);	
		String status=stateOwner.doOpValidator(event);
		this.getMapper().updateAclButtonStatus(id, status);
	}
	
	private void doOpDealAclButton(AclButton entity, boolean isAdd) {
		
		if(isAdd){
			entity.setStatus(ValidateUtil.isEmpty(entity.getStatus())?AclButtonClassDataManager.STATE_N:entity.getStatus());
			entity.setVersion(ValidateUtil.isEmpty(entity.getVersion())?0:entity.getVersion());	
		}
		
		validatorAclButton(entity);
		
		/*AclModule aclModule=aclModuleService.findEntityById(entity.getModule_id().toString());
		String actionmethodright=AclButtonService.ROLE_NAME_BEFORE+aclModule.getCode().trim().toUpperCase().concat(DelimiterType.BottomCrossed.getDelimiter())
			.concat(entity.getCode().trim().toUpperCase());
		entity.setActionmethodright(actionmethodright);*/
	}
	
	private void validatorAclButton(AclButton entity){
		
		if(ValidateUtil.isEmpty("module_id",entity)){
			throw new RuntimeException("模块不能为空");
		}
		
		if(ValidateUtil.isEmpty("code", entity)){
			throw new RuntimeException("编码不能为空");
		}

		if(ValidateUtil.isEmpty("cname", entity)){
			throw new RuntimeException("名称不能为空");
		}
	}
	
	private AclButtonStateOwner createStateOwner(AclButton found) {
		
		String key = getStatusKey();
		
		if (null==ClassDataManagerUtil.getRegisterClassDataManager(key)) {
			ClassDataManagerUtil.registerClassDataManager(AclButtonClassDataManager.class,key);	
		}
		
		return new AclButtonStateOwner(found,"status");
	}
	
	private void doOpAclUserModuleAndButtonCodeRepeat(AclButton entity){
		
		List<AclButton> aclButtons=findAclButtonByCodeOrModelId(entity.getCode(), entity.getModule_id().toString());
		
		if(!ValidateUtil.isEmpty(aclButtons)&&!aclButtons.isEmpty()&&!ValidateUtil.format("id", entity).equals(ValidateUtil.format("id", aclButtons.get(0)))){
			throw new RuntimeException("同一模块下按钮编码不能相同");
		}
	}
	
	private String getStatusKey(){
		return AclButton.class.getName().toLowerCase().concat("_").concat(AclButtonStateOwner.ACLBUTTON_STATEOWNER_KEY);
	}
	

}
