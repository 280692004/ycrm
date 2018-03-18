package com.thesys.base.sysmag.passwordpolicy.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.sysmag.passwordpolicy.domain.PasswordPolicy;

public interface PasswordPolicyService extends BaseService<PasswordPolicy> {
	/**
	 * 查询所有的
	 */
	public List<PasswordPolicy> findAll();
}
