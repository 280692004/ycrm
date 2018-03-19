package com.thesys.base.sysmag.aclmenu.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.sysmag.aclmenu.domain.AclMenu;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;

/**
 * 用户菜单
 * @author Administrator
 *
 */
public interface AclMenuService extends BaseService<AclMenu>{

	/**
	 * 根据用户Id查询该用户拥有的所有模块清单
	 * @param acluserId
	 * @return
	 */
	public List<AclModule> findUserAclMenu(String acluserId);
	/**
	 * 根据用户Id 查询对应的菜单列表
	 * @param acluser_id  用户Id
	 * @param parent_code  父菜单code
	 * @param withsubmenu  是否查询子菜单
	 * @return
	 */
	public List<AclModule> findUserAclMenu(String acluser_id,String parent_code,Boolean withsubmenu);
	
	/**
	 * 验证用户是否有对应的模块的权限
	 * @param modulecode
	 * @param acluserId
	 * @return
	 */
	public Boolean hasRight(String modulecode,String acluserId);
	
	/**
	 * 增加或者更新一个用户的菜单
	 * @param acluserId
	 */
	public void doOpAddOrUpdateAclMenu(String acluserId);
	/**
	 * 根据用户Id 和模块清单增加菜单列表
	 * @param acluserId
	 * @param details
	 */
	public void addAclMenu(String acluserId,List<AclModule> details);
	
	/**
	 * 根据用户Id和模块列表删除菜单列表
	 * @param acluserId
	 * @param details
	 */
	public void delAclMenu(String acluserId,List<AclModule> details);
	/**
	 * 根据模块Id 删除对应的菜单
	 * @param moduleId
	 */
	public void deleteAclMenuByModuleId(Integer moduleId);	
}
