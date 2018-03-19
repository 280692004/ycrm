package com.thesys.base.sysmag.aclright.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclright.dao.AclRightMapper;
import com.thesys.base.sysmag.aclright.service.AclRightService;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.service.AclUserService;

@Service("aclRightService")
@ServiceMapper(AclRightMapper.class)
public class AclRightServiceImpl extends BaseServiceImpl<BaseEntity> implements AclRightService {
	@Autowired
	private AclUserService aclUserService;
	
	@Override
	public List<AclButton> findUserModuleHasRight(String modulecode, String username) {		
		return this.getMapper().findUserModuleHasRight(modulecode, username);
	}

	@Override
	public Boolean hasRight(String modulecode, String buttoncode, String username) {
		if(isSupperUser(username)){
			return true;
		}
		
		long icount = this.getMapper().hasRightByUserName(modulecode, buttoncode, username);
		return icount>0;
	}

	@Override
	public Boolean hasRight(String modulecode, String buttoncode, Integer userid) {
		
		long icount = this.getMapper().hasRightByUserId(modulecode, buttoncode, userid);
		if(icount>0){
			return icount>0;
		}
		
		AclUser aclUser=aclUserService.findAclUserById(userid.toString());
		//判断是否是超级用户;
		if(isSupperUser(aclUser.getName())){
			return true;
		}
		
		return icount>0;		
	}

	@SuppressWarnings("all")
	@Override
	protected AclRightMapper getMapper() {
		return (AclRightMapper)super.getMapper();
	}

	private Boolean isSupperUser(String username){
		return AclUserService.ADMIN.equals(username);
	}


}
