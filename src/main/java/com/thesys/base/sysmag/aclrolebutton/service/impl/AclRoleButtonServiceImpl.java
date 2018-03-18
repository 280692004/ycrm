package com.thesys.base.sysmag.aclrolebutton.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.aclrolebutton.dao.AclRoleButtonMapper;
import com.thesys.base.sysmag.aclrolebutton.domain.AclRoleButton;
import com.thesys.base.sysmag.aclrolebutton.service.AclRoleButtonService;

@Service("aclRoleButtonService")
@ServiceMapper(AclRoleButtonMapper.class)
public class AclRoleButtonServiceImpl extends BaseServiceImpl<AclRoleButton> implements AclRoleButtonService {
	
	
	@Override
	public void addAclRoleButton(List<AclRoleButton> details){
		doOpDealAclRoleButton(details);
		doOpValidatorAclRoleButton(details);
		this.getMapper().addAclRoleButton(details);
	}

	@Override
	public List<AclRoleButton> findModuleAclRoleButton(AclRoleButton aclRoleButton){
		return getMapper().findModuleAclRoleButton(aclRoleButton);
	}
	
	@Override
	public void deleteAclRoleButtonByButtonId(Integer buttonId){		
		this.getMapper().deleteAclRoleButtonByButtonId(buttonId);
	}

	@Override
	public void deleteByRoleIdAndModuleIdAndbutIds(String aclroleId,String aclmoduleId, String butIds) {
		this.getMapper().deleteByRoleIdAndModuleIdAndbutIds(aclroleId,aclmoduleId,butIds);
		
	}
	
	@Override
	public AclRoleButton findEntityById(String id) {
		
		return this.getMapper().findEntityById(Integer.valueOf(id));
	}	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------
	@SuppressWarnings("all")
	@Override
	protected AclRoleButtonMapper getMapper() {
		return (AclRoleButtonMapper)super.getMapper();
	}

	private void doOpValidatorAclRoleButton(List<AclRoleButton> details) {
		for(AclRoleButton detail:details){
			AclRoleButton found = this.getMapper().findAclRoleButton(detail);
			if(!ValidateUtil.isEmpty("id", found)){
				throw new RuntimeException("不能重复增加");
			}
		}		
	}
	
	private void doOpDealAclRoleButton(List<AclRoleButton> details) {
	
		for(AclRoleButton aclRoleButton:details){
			aclRoleButton.setVersion(0);
		}
		
	}
	
}
