package com.thesys.base.sysmag.aclmodule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.core.dto.JqGridUiTreeItem;
import com.thesys.architecture.dao.impl.BaseProvider;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.architecture.core.dto.CriteriaDto;
import com.thesys.architecture.core.dto.FlexiPageDto;

public interface AclModuleMapper extends BaseMapper<AclModule> {

	/**
	 * 更新模块的状态
	 * @param code
	 * @param status
	 */
	public void updateAclModuleStatus(@Param("id")Integer id,@Param("status")String status);
	/**
	 * 计算制定父模块下面的最新下一个序号
	 * @param parentid
	 * @return
	 */	
	public Integer doOpCalNextSeq(@Param("parentid") String parentid);
	/**
	 * 根据模块code 进行查询
	 * @return
	 */
	@ResultMap("AclModuleResultMap")
	public AclModule findAclModulebycode(@Param("code")String code);
	
	
	/**
	 * 根据模块id 进行查询
	 * @return
	 */
	@ResultMap("AclModuleResultMap")
	public AclModule findAclModulebyId(@Param("id")String id);	
	
	/**
	 * 根据父模块的Id查询模块列表
	 * @param parentid
	 * @return
	 */
	@ResultMap("AclModuleResultMap")
	public List<AclModule> findAclModuleByParentId(@Param("parentid")String parentid);
	/**
	 * 根据父模块Id，查询它的子模块的数量
	 * @param parentid
	 * @return
	 */
	public Integer findAclModuleCountByParentId(@Param("parentid")Integer parentid);
	/**
	 * 查询分页条件的总记录数
	 */
	@ResultType(Long.class)
	public Long findFlexiPageAclModuleCount(@Param("aclModule") AclModule aclModule);
	/**
	 * 模块分页查询
	 * @param aclModule
	 * @param flexiPageDto
	 * @return
	 */
	@ResultMap("AclModuleResultMap")
	public List<AclModule> findFlexiPageAclModules(@Param("aclModule") AclModule aclModule,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	/**
	 * 分配权限使用 查询所有模块信息
	 * @return
	 */
	public List<JqGridUiTreeItem> findAclModuleTreeDatas();
	/**
	 * 根据Id查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityById")
	public AclModule findEntityById(CriteriaDto<AclModule> criteriaDto);	
	/**
	 * 根据模块编码和Id(可能为空)查询模块编码的数量
	 * @param modulecode
	 * @param moduleId
	 * @return
	 */
	public Integer findAclModuleCountByCode(@Param("modulecode")String modulecode,@Param("moduleId")Integer moduleId);
	/**
	 * 查询所有数据 根据序号排序
	 * @return
	 */
	public List<AclModule> findAllAclModule();
}
