package com.thesys.base.basemanager.baseparameter.parameterbasebean.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;

public interface ParameterBaseBeanMappper extends BaseMapper<ParameterBaseBean> {
	/**
	 * 根据id查找参数
	 * @param id
	 * @return
	 */
	ParameterBaseBean findParameterBaseBeanById(@Param("id")String id);
	/**
	 * @Title: findCountByPropertyNeId
	 * @Description: TODO(更新参数检查约束前置条件)
	 * @param parameterBaseBean
	 * @return
	 */
	Integer findCountByPropertyNeId(@Param("parameterBaseBean")ParameterBaseBean parameterBaseBean);
	/**
	 * @Title: findParameterBaseBeanByCode
	 * @Description: TODO(根据编码查询参数)
	 * @param dtype
	 * @param code
	 * @return
	 */
	ParameterBaseBean findParameterBaseBeanByDtypeAndCode(@Param("dtype")String dtype, @Param("code")String code);
	/**
	  * 根据类型查询所有的参数列表
	  * @param dtype
	  * @return
	  */
	List<ParameterBaseBean> findParameterBaseBeanByDType(@Param("dtype")String dtype);
	/**
	 * @Title: findFlexiPageParameterBaseBeanCount
	 * @Description: TODO(条件查询页数:模糊)
	 * @param parameterBaseBean
	 * @return
	 */
	Long doOpFindCountByPageLike(@Param("parameterBaseBean") ParameterBaseBean parameterBaseBean);
	/**
	 * 参数分页查询 模糊
	 */
	List<ParameterBaseBean> doOpFindByPageLike(@Param("parameterBaseBean") ParameterBaseBean parameterBaseBean,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	/**
	 * @Title: updateParameterBaseBeanStatus
	 * @Description: TODO(更新参数状态)
	 * @param id
	 * @param parameterStatus
	 */
	void updateParameterBaseBeanStatus(@Param("id") String id, @Param("status") String status);
	/**
	 * @Title: findParameterBaseBeansByParamPrecise
	 * @Description: TODO(精确条件查询,无分页)
	 * @param parameterBaseBean
	 * @return 
	 */ 
	List<ParameterBaseBean> findParameterBaseBeansByParamPrecise(@Param("parameterBaseBean") ParameterBaseBean parameterBaseBean);
	/**
	* 根据父参数类型+父级编码查询模块列表
	*  @param id
	 * @param parentcode
	 * @return
	 */
	List<ParameterBaseBean> findParameterBaseBeanByParentCode(@Param("parentcode") String parentcode, @Param("dtype") String dtype);
	/**
	 * 查询序号
	 * @param parentCode
	 * @return
	 */
	Integer doOpCalNextSeq(@Param("dtype") String dtype,@Param("paramCode")  String paramCode);
		
}
