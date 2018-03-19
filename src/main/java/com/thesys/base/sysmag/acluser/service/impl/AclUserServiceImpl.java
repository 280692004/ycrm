package com.thesys.base.sysmag.acluser.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.architecture.statemachine.ClassDataManagerUtil;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.MD5Util;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.acluser.controller.AclUserAuthority;
import com.thesys.base.sysmag.acluser.dao.AclUserMapper;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.domain.state.AclUserClassDataManager;
import com.thesys.base.sysmag.acluser.service.AclUserService;
import com.thesys.base.sysmag.acluser.service.impl.state.AclUserStateOwner;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;

@Service("aclUserService")
@ServiceMapper(AclUserMapper.class)
public class AclUserServiceImpl extends BaseServiceImpl<AclUser> implements AclUserService {

	@Override
	public Integer addEntity(AclUser entity) {
		Integer num=doOpIsExist(entity.getName());
		if(num==-1){
			return num;
		}
		doOpDealAclUser(entity,true);
		doOpValiatorAclUser(entity);
		entity.setPassword(MD5Util.md5(entity.getPassword()));//密码MD5加密
		return super.addEntity(entity);
	}
	@Override
	public void deleteById(String id) {
		super.deleteById(id);
	}

	@Override
	public void updateEntity(AclUser entity) {
		
		if(!MD5Util.isMd5(entity.getPassword())){
			entity.setPassword(MD5Util.md5(entity.getPassword()));
		}
		
		doOpDealAclUser(entity,true);
		doOpValiatorAclUser(entity);
		super.updateEntity(entity);
	}

	@Override
	public AclUser findAclUserById(String id){
		return this.getMapper().findAclUserById(id);
	}
	@Override
	public AclUser findAclUserByName(String name){
		AclUserMapper mapper= this.getMapper();
		return mapper.findAclUserByName(name.trim().toString());
	}
	
	@Override
	public boolean doOpChargeIsSuperUser(String userId) {

		AclUser user = this.findAclUserById(userId);
		if (null == user) {
			return false;
		}
		return ADMIN.equals(user.getName()) && "1".equals(userId);
	}
	@Override
	public JqGridResultDataDto findFlexiPageAclUsers(AclUser entity,Integer pageSize, Integer pageNumber) {
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().doOpFindCountByPageLike(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<AclUser> fonds = getMapper().doOpFindByPageLike(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(AclUserAuthority.doOpInitButtons(showListData));
		return showListData;
	}
	
	@Override
	public List<AclModule> findAclUserHasRightAclModule(String aclUserId){
		return this.getMapper().findAclUserHasRightAclModule(aclUserId);
	}
	
	@Override
	public List<AclButton> findAclButtons(String uname){
		return this.getMapper().findAclButtons(uname);
	}
	private void doOpDealAclUser(AclUser entity, boolean isAdd) {
		if(isAdd){
			entity.setStatus(ValidateUtil.isEmpty(entity.getStatus())?AclUserClassDataManager.STATE_N:entity.getStatus());
			entity.setVersion(ValidateUtil.isEmpty(entity.getVersion())?0:entity.getVersion());	
			entity.setLastmodfypwddate(new Date());
		}		
	}
	
	@SuppressWarnings("all")
	@Override
	protected AclUserMapper getMapper() {
		return (AclUserMapper)super.getMapper();
	}
	
	
	@Override
	public void updateAclUserStatus(String id, IEvent event) {
		AclUser found = this.findEntityById(id);	
		AclUserStateOwner stateOwner= createStateOwner(found);	
		String status=stateOwner.doOpValidator(event);
		this.getMapper().updateAclUserStatus(id, status);
	}
	
	@Override
	public void enableAclUser(Integer id) {
		this.getMapper().enableAclUser(id);		
	}
	@Override
	public void logoutAclUser(Integer id) {
		this.getMapper().logoutAclUser(id);				
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	private void doOpValiatorAclUser(AclUser entity) {
		String personName = entity.getPersonName();
		String name = entity.getName();
		String pwd = entity.getPassword();
		
		if(ValidateUtil.isEmpty(name)){
			throw new RuntimeException("用户名称不能为空");
		}
		
		Integer icount = this.getMapper().findAclUserCountCount(entity.getName(), entity.getId());
		if(icount>0){
			throw new RuntimeException("用户名称不能重复");
		}
		if(ValidateUtil.isEmpty(personName)){
			throw new RuntimeException("人员姓名不能为空");
		}		

		if(ValidateUtil.isEmpty(pwd)){
			throw new RuntimeException("账号或密码不能为空,人员姓名也不能为空!");
		}
	}
	
	private AclUserStateOwner createStateOwner(AclUser found) {
		
		String key = getStatusKey();
		
		if (null==ClassDataManagerUtil.getRegisterClassDataManager(key)) {
			ClassDataManagerUtil.registerClassDataManager(AclUserClassDataManager.class,key);	
		}
		
		return new AclUserStateOwner(found,"status");
	}
	private String getStatusKey(){
		return AclUser.class.getName().toLowerCase().concat("_").concat(AclUserStateOwner.ACLUSER_STATEOWNER_KEY);
	}
	@Override
	public void updatePassWord(String password,String userName) {
		this.getMapper().updatePassWord(MD5Util.md5(password),userName);
	}
	
	private Integer doOpIsExist(String name) {
		AclUser aclUser=this.findAclUserByName(name);
		if(!ValidateUtil.isEmpty(aclUser)){
			return -1;
		}
		return 1;
	}
	@Override
	public AclUser findAclUserByPersonName(String name) {
		return this.getMapper().findAclUserByPersonName(name);
	}
	@Override
	public AclUser findLoginAclUserByName(String name) {
		return this.getMapper().findLoginAclUserByName(name);
	}


}
