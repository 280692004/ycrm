package com.thesys.base.sysmag.aclrole.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.architecture.statemachine.ClassDataManager;
import com.thesys.architecture.statemachine.ClassDataManagerUtil;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclrole.controller.AclRoleAuthority;
import com.thesys.base.sysmag.aclrole.dao.AclRoleMapper;
import com.thesys.base.sysmag.aclrole.domain.AclRole;
import com.thesys.base.sysmag.aclrole.domain.state.AclRoleClassDataManager;
import com.thesys.base.sysmag.aclrole.service.AclRoleService;
import com.thesys.base.sysmag.aclrole.service.impl.state.AclRoleStateOwner;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.domain.AclUserTypeEnum;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;

@Service("aclRoleService")
@ServiceMapper(AclRoleMapper.class)
public class AclRoleServiceImpl extends BaseServiceImpl<AclRole> implements AclRoleService {
	
	@Override
	public Integer addEntity(AclRole entity) {
		doOpDealAclRole(entity);
		doOpValidatorAclRole(entity);
		return super.addEntity(entity);
	}
	
	@Override
	public void updateEntity(AclRole entity) {
		doOpValidatorAclRole(entity);
		super.updateEntity(entity);
	}

	
	@Override
	public void updateAclRoleStatus(String roleId,IEvent event){
		
		AclRole found = this.findEntityById(roleId);	
		AclRoleStateOwner stateOwner= createStateOwner(found);	
		String status=stateOwner.doOpValidator(event);
		
		this.getMapper().updateAclRoleStatus(roleId, status);		
	}
	
	@Override
	public JqGridResultDataDto findFlexiPageAclRoles(AclRole entity,Integer pageSize, Integer pageNumber) {
		
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().findFlexiPageAclRoleCount(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<AclRole> fonds = getMapper().findFlexiPageAclRoles(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(AclRoleAuthority.doOpInitButtons(showListData));
		return showListData;
	}
	
	@Override
	public List<AclRole> findAclRole(AclUser aclUser){
		
		if(AclUserTypeEnum.GLOBAL.getTypecode().equals(aclUser.getType())){
			AclRole aclRole=new AclRole();
			aclRole.setStatus(ClassDataManager.STATE_Y);
			return this.findByPage(aclRole, FlexiPageDto.createMaxPageDto());
		}
		
		return this.getMapper().findAclRoleByCreateByAclUser(aclUser);		
	}

	@Override
	public void enableAclRole(Integer roleId) {
		this.getMapper().enableAclRole(roleId);		
	}

	@Override
	public void logoutAclRole(Integer roleId) {
		this.getMapper().logoutAclRole(roleId);		
	}	
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("all")
	@Override
	protected AclRoleMapper getMapper() {
		return (AclRoleMapper)super.getMapper();
	}


	private void doOpDealAclRole(AclRole entity) {
		
		entity.setVersion(ValidateUtil.isEmpty(entity.getVersion())?0:entity.getVersion());
		entity.setStatus(ValidateUtil.isEmpty(entity.getStatus())?AclRoleClassDataManager.STATE_N:entity.getStatus());
	}
	
	
	private AclRoleStateOwner createStateOwner(AclRole found) {
		
		String key = getStatusKey();
		
		if (null==ClassDataManagerUtil.getRegisterClassDataManager(key)) {
			ClassDataManagerUtil.registerClassDataManager(AclRoleClassDataManager.class,key);	
		}
		
		return new AclRoleStateOwner(found,"status");
	}
	

	private String getStatusKey(){
		return AclRole.class.getName().toLowerCase().concat("_").concat(AclRoleStateOwner.STATUS_KEY);
	}		

	//1、验证角色名称不为空、不重复
	
	private void doOpValidatorAclRole(AclRole entity) {
		
		if(ValidateUtil.isEmpty(entity.getCname())){
			throw new RuntimeException("角色名称不能为空");
		}
		
		List<AclRole>  details= this.getMapper().findAclRoleByCName(entity.getCname());
		if(details.isEmpty()){
			return ;
		}
		
		if(details.size()>1){
			throw new RuntimeException("角色不能重复");
		}
		
		AclRole found = details.get(0);
		
		if(!ValidateUtil.isEmpty("id", found)&&!ValidateUtil.format("id", entity).equals(ValidateUtil.format("id", found))){
			throw new RuntimeException("角色不能重复");
		}
		
	}

}
