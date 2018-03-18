package com.thesys.base.sysmag.aclright.service;

import java.util.List;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.service.BaseService;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;

public interface AclRightService extends BaseService<BaseEntity> {
	/**
	 * 根据模块、按钮、用户名 判断用户是否有对应的权限
	 * @param modulecode
	 * @param buttoncode
	 * @param username
	 * @return
	 */
	public Boolean hasRight(String modulecode,String buttoncode,String username);
	
	/**
	 * 根据模块、按钮、用户Id 判断用户是否有对应的权限
	 * @param modulecode
	 * @param buttoncode
	 * @param userid
	 * @return
	 */
	public Boolean hasRight(String modulecode,String buttoncode,Integer userid);
	
	/**
	 * 查询一个用户对一个模块拥有的所有的权限
	 * @param modulecode
	 * @param username
	 * @return
	 */
	public List<AclButton> findUserModuleHasRight(String modulecode,String username);
}
