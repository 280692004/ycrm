package com.thesys.base.sysmag.aclright.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.dao.BaseMapper;

public interface AclRightMapper extends BaseMapper<BaseEntity> {
	/**
	 * 根据模块、按钮、用户名 判断用户是否有对应的权限
	 * @param module
	 * @param button
	 * @param username
	 * @return
	 */
	public Long hasRightByUserName(@Param("modulecode")String modulecode,@Param("buttoncode")String buttoncode,@Param("username")String username);
	
	/**
	 * 根据模块、按钮、用户Id 判断用户是否有对应的权限
	 * @param module
	 * @param button
	 * @param userid
	 * @return
	 */
	public Long hasRightByUserId(@Param("modulecode")String modulecode,@Param("buttoncode")String buttoncode,@Param("userid")Integer userid);
	
	/**
	 * 查询一个用户对一个模块拥有的所有的权限
	 * @param module
	 * @param username
	 * @return
	 */
	public List<AclButton> findUserModuleHasRight(@Param("modulecode")String modulecode,@Param("username")String username);
}
