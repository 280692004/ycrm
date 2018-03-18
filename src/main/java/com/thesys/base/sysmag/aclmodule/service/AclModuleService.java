package com.thesys.base.sysmag.aclmodule.service;


import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.core.dto.JqGridResultDataDto;
import com.thesys.core.dto.JqGridUiTreeItem;

public interface AclModuleService extends BaseService<AclModule> {

	/**
	 * 分页查询模块列表
	 * @param aclUser
	 * @param flexiPageDto
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageAclModules(AclModule aclModule,Integer pageSize, Integer pageNumber);
	/**
	 * 更新模块的状态
	 * @param code
	 * @param event
	 */
	public void updateAclModuleStatus(String code,IEvent event);
	/**
	 * 根据模块code 查询模块
	 * @param code
	 * @return
	 */
	public AclModule findAclModulebycode(String code);
	/**
	 * 计算制定父模块下面的最新下一个序号
	 * @param parentid
	 * @return
	 */
	public Integer doOpCalNextSeq(String parentid);
	
	/**
	 * 根据模块的父Id查询模块列表
	 * @param parentid
	 * @return
	 */
	public List<AclModule> findAclModuleByParentId(String parentid);	
	/**
	 * 根据模块编码检查模块是否有子模块
	 * @param code
	 * @return
	 */
	public Boolean doOpCheckHasSubModule(String moduleId);
	/**
	 * 根据模块的code查询所有父模块
	 * @param code
	 * @return
	 */
	public List<String> findAclModuleParentIds(String code);
	
	/**
	 * 根据Id删除模块
	 */
	public void deleteById(Integer id);
	/**
	 * 分配权限使用 查询所有模块信息
	 * @return
	 */
	public List<JqGridUiTreeItem> findAclModuleTreeDatas();
}
