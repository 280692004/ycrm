package com.thesys.base.sysmag.aclmenu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.thesys.base.sysmag.aclmenu.domain.AclMenu;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.architecture.dao.impl.BaseProvider;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.architecture.core.dto.CriteriaDto;

public interface AclMenuMapper extends BaseMapper<AclMenu> {
	/**
	 * 根据单号查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityByNaturalId")
	public AclMenu findEntityByNaturalId(CriteriaDto<AclMenu> criteriaDto);
	/**
	 * 根据Id查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityById")
	public AclMenu findEntityById(CriteriaDto<AclMenu> criteriaDto);
	/**
	 * 查询所有数据
	 */
	@SelectProvider(type = BaseProvider.class, method = "findAll")
	public List<AclMenu> findAll(CriteriaDto<AclMenu> criteriaDto);
	/**
	 * 根据用户id查询菜单
	 * @param user_id
	 * @return
	 */
	@ResultMap("AclModuleResultMap")
	public List<AclModule> findAclMenuByUserId(@Param("user_id")String user_id);
	/**
	 * 根据用户id查询是否拥有该菜单权限
	 * @param modulecode
	 * @param acluserId
	 * @return
	 */
	@Select("select count(*) from base_aclmenu a inner join base_aclmodule b on b.id=a.module_id where a.user_id=#{acluserId} and b.code=#{modulecode}")
	public Long doOpAclMenuCount(@Param("modulecode")String modulecode, @Param("acluserId")String acluserId);
	
	/**
	 * 批量增加用户菜单
	 * @param details
	 */
	public void addAclMenu(@Param("details")List<AclMenu> details);
	/**
	 * 根据用户Id和模块Id删除菜单
	 * @param aclMenu
	 */
	public void delAclMenu(@Param("aclMenu")AclMenu aclMenu);
	/**
	 * 根据模块Id 删除对应的菜单
	 * @param moduleId
	 */
	public void deleteAclMenuByModuleId(@Param("moduleId")Integer moduleId);

}
