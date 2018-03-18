package com.thesys.base.sysmag.acluser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.architecture.dao.impl.BaseProvider;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.architecture.core.dto.CriteriaDto;
import com.thesys.architecture.core.dto.FlexiPageDto;

public interface AclUserMapper extends BaseMapper<AclUser> {
	/**
	 * 根据id查找用户信息
	 * @param id
	 * @return
	 */
	@ResultMap("AclUserResultMap")
	public AclUser findAclUserById(@Param("id")String id);
	/**
	 * 根据用户name 查找用户
	 * @param name
	 * @return
	 */
	@ResultMap("AclUserResultMap")
	public AclUser findAclUserByName(@Param("name")String name);
	/**
	 * 用于登录的验证
	 * @param name
	 * @return
	 */
	@ResultMap("AclUserResultMap")
	public AclUser findLoginAclUserByName(@Param("name")String name);
	
	/**
	 * 根据人员姓名 查找用户
	 * @param name
	 * @return
	 */
	@ResultMap("AclUserResultMap")
	public AclUser findAclUserByPersonName(@Param("name")String name);
	
	/**
	 * 更新用户状态
	 * @param id
	 * @param status
	 */
	public void updateAclUserStatus(@Param("id")String id,@Param("status")String status);
	/**
	 * 分页查询
	 * @param aclUser
	 * @param flexiPageDto
	 * @return
	 */
	@ResultMap("AclUserResultMap")
	public List<AclUser> doOpFindByPageLike(@Param("aclUser")AclUser aclUser,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	/**
	 * 查询总记录数
	 * @param aclUser
	 * @return
	 */
	@ResultType(Long.class)
	public Long doOpFindCountByPageLike(@Param("aclUser")AclUser aclUser);
	
	/**
	 * 检查用户名称是否重复
	 * @param name
	 * @param aclUserId
	 * @return
	 */
	public Integer findAclUserCountCount(@Param("name")String name,@Param("aclUserId")Integer aclUserId);
	/**
	 * 
	 * @param aclUserId
	 * @return
	 */
	@ResultMap("AclModuleResultMap")
	public List<AclModule> findAclUserHasRightAclModule(@Param("aclUserId")String aclUserId);	
	
	/**
	 * 根据用户名查询用户所有AclMethodUrl
	 * @param uname
	 * @return
	 */
	@ResultMap("AclButtonResultMap")
	public List<AclButton> findAclButtons(@Param("uname")String uname);
	/**
	 * 更新用户的密码
	 * @param password
	 * @param id
	 */
	public void updatePassWord(@Param("password")String password,@Param("userName")String userName);
	/**
	 * 启用用户
	 * @param id
	 */
	public void enableAclUser(@Param("id")Integer id);
	/**
	 * 注销用户
	 * @param id
	 */
	public void logoutAclUser(@Param("id")Integer id);		
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 根据单号查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityByNaturalId")
	public AclUser findEntityByNaturalId(CriteriaDto<AclUser> criteriaDto);
	/**
	 * 根据Id查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityById")
	public AclUser findEntityById(CriteriaDto<AclUser> criteriaDto);
	/**
	 * 查询所有数据
	 */
	@SelectProvider(type = BaseProvider.class, method = "findAll")
	public List<AclUser> findAll(CriteriaDto<AclUser> criteriaDto);
	
	/**
	 * 单表模糊查询，暂不支持链接查询
	 */
	@SelectProvider(type = BaseProvider.class, method = "findByLike")
	public List<AclUser> findByLike(CriteriaDto<AclUser> criteriaDto);
	
	/**
	 * 单表模糊分页查询，暂不支持链接查询
	 */
	@SelectProvider(type = BaseProvider.class, method = "findByPage")
	public List<AclUser> findByPage(CriteriaDto<AclUser> criteriaDto);	
	
}
