package com.thesys.base.sysmag.aclbutton.service;

import java.util.List;

import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.BaseService;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.core.dto.JqGridResultDataDto;

public interface AclButtonService extends BaseService<AclButton> {
	
	public final static String ROLE_NAME_BEFORE="ROLE_";
	/**
	 * 根据id查询按钮
	 * @param id
	 * @return
	 */
	public AclButton findAclButtonsById(String id);
	/**
	 * 更新按钮的状态
	 * @param code
	 * @param event
	 */
	public void updateAclButtonStatus(String id,IEvent event);
	/**
	 * 根据模块编码
	 * @return
	 */
	public List<AclButton> findAclButtonByModelCode(String code);
	/**
	 * 根据按钮编码和模块Id
	 * @return
	 */
	public List<AclButton> findAclButtonByCodeOrModelId(String code,String modelId);
	/**
	 * 查询该模块下的所有按钮
	 * @param modelId
	 * @return
	 */
	public List<AclButton> findAclButtonsByModelIdAndRoleId(String modelId,String roleId);
	/**
	 * 分页查询aclButton
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageAclButtons(AclButton aclButton,Integer pageSize, Integer pageNumber);
	/**
	 * 根据参数查询按钮的分页数据
	 * @param param
	 * @param flexiPageDto
	 * @return
	 */
	public List<AclButton> findFlexiPageAclButtons(String param,FlexiPageDto flexiPageDto);
	
	/**
	 * 根据模块Id删除对应的按钮
	 * @param moduleId
	 */
	public void deleteAclButtonByModuleId(Integer moduleId);
}
