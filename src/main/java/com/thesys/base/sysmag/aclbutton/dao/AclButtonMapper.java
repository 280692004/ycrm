package com.thesys.base.sysmag.aclbutton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;

import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;

public interface AclButtonMapper extends BaseMapper<AclButton>  {
	/**
	 * 根据id查询按钮
	 * @param id
	 * @return
	 */
	@ResultMap("AclButtonOrModelResultMap")
	public AclButton findAclButtonsById(@Param("id")String id);
	
	
	/**
	 * 根据模块编码
	 * @param code 编码
	 * @return
	 */
	@ResultMap("AclButtonResultMap")
	public List<AclButton> findAclButtonByModelCode(@Param("code")String code);
	/**
	 * 根据按钮编码和模块Id
	 * @param code 编码
	 * @param modelId 模块Id
	 * @return
	 */
	@ResultMap("AclButtonResultMap")
	public List<AclButton> findAclButtonByCodeOrModelId(@Param("code")String code,@Param("modelId")String modelId);
	/**
	 * 更新按钮状态
	 * @param code
	 * @param modelId
	 * @param status
	 * @return
	 */
	public void updateAclButtonStatus(@Param("id")String id,@Param("status")String status);
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	/***
	 * 分页查询Button
	 * @param aclButton
	 * @param flexiPageDto
	 * @return
	 */
	@ResultMap("AclButtonOrModelResultMap")
	public List<AclButton> findFlexiPageAclButtons(@Param("aclButton")AclButton aclButton,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	/**
	 * 查询总记录数
	 * @param aclButton
	 * @return
	 */
	@ResultType(Long.class)
	public Long findFlexiPageAclButtonCount(@Param("aclButton")AclButton aclButton);
	
	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 查询总记录数
	 * @param aclButton
	 * @return
	 */
	@ResultType(Long.class)
	public Long findFlexiPageAclButtonByParamCount(@Param("param")String param);
	
	/***
	 * 分页查询Button
	 * @param param
	 * @param flexiPageDto
	 * @return
	 */
	@ResultMap("AclButtonOrModelResultMap")
	public List<AclButton> findFlexiPageAclButtonByParam(@Param("param")String param,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 查询该模块下的所有按钮
	 * @param modelId
	 * @param roleId
	 * @return
	 */
	public List<AclButton> findAclButtonsByModelIdAndRoleId(@Param("modelId")String modelId,@Param("roleId")String roleId);
	
	/**
	 * sql待补全
	 * @param modelId
	 * @return
	 */
	public List<AclButton> findAclButtonsByModelId(@Param("modelId")String modelId);

}
