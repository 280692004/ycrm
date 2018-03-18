package com.thesys.base.sysmag.passwordpolicy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.base.sysmag.passwordpolicy.dao.PasswordPolicyMapper;
import com.thesys.base.sysmag.passwordpolicy.domain.PasswordPolicy;
import com.thesys.base.sysmag.passwordpolicy.service.PasswordPolicyService;
@Service("passwordPolicyService")
@ServiceMapper(PasswordPolicyMapper.class)
@SuppressWarnings("all")
public class PasswordPolicyServiceImpl extends BaseServiceImpl<PasswordPolicy> implements PasswordPolicyService {
	@Override
	public List<PasswordPolicy> findAll() {
		
	    return getMapper().findAllPasswordPolicy();
	  }

	  protected PasswordPolicyMapper getMapper() {
		  
	    return ((PasswordPolicyMapper)super.getMapper());
	  }
}
