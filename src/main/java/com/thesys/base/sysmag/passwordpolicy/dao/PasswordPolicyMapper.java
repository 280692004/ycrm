package com.thesys.base.sysmag.passwordpolicy.dao;

import java.util.List;

import com.thesys.architecture.dao.BaseMapper;
import com.thesys.base.sysmag.passwordpolicy.domain.PasswordPolicy;

public interface PasswordPolicyMapper extends BaseMapper<PasswordPolicy> {
	public  List<PasswordPolicy> findAllPasswordPolicy();
}
