package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.thesys.architecture.core.dto.CriteriaDto;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.dao.BaseMapper;
import com.thesys.architecture.dao.impl.BaseProvider;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.ParameterBaseBeanType;

public interface ParameterBaseBeanTypeMapper extends BaseMapper<ParameterBaseBeanType> {
	
	
	/**
	 * 更新参数类型的状态
	 * @param code
	 * @param status
	 */
	public void updateParameterBaseBeanTypeStatus(@Param("id")String id,@Param("status")String status);
	/**
	 * 根据Id查询实体
	 */
	@SelectProvider(type = BaseProvider.class, method = "findEntityById")
	public ParameterBaseBeanType findEntityById(CriteriaDto<ParameterBaseBeanType> criteriaDto);
	
	@Select("select * from base_parameterbasebeantype where cname=#{cname}")
	public ParameterBaseBeanType findParameterBaseBeanTypeByName(@Param("cname")String cname);
	/**
	 * 查询所有数据
	 */
	@SelectProvider(type = BaseProvider.class, method = "findAll")
	public List<ParameterBaseBeanType> findAll(CriteriaDto<ParameterBaseBeanType> criteriaDto);
	/**
	 * 查询分页条件的总记录数:模糊
	 */
	@ResultType(Long.class)
	public Long doOpFindCountByPageLike(@Param("parameterBaseBeanType") ParameterBaseBeanType parameterBaseBeanType);
	/**
	 * 模块分页查询
	 * @param aclModule
	 * @param flexiPageDto
	 * @return
	 */
	public List<ParameterBaseBeanType> doOpFindByPageLike(@Param("parameterBaseBeanType") ParameterBaseBeanType parameterBaseBeanType,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	
	/**
	 * @Title: findRootParameterBaseBeanTypeByDtype
	 * @Description: TODO(根据dtype查询根参数类型)
	 * @param dtype
	 * @return
	 */
	@Select("SELECT * FROM base_parameterbasebeantype where parent_id is null and dtype=#{dtype}")
	public List<ParameterBaseBeanType> findRootParameterBaseBeanTypeByDtype(@Param("dtype")String dtype);
	/**
	 * 
	 * @Title: getListSingleDtype
	 * @Description: TODO(分组查询dtype)
	 * @return
	 */
	@ResultType(List.class)
	@Select("SELECT * FROM base_parameterbasebeantype GROUP BY dtype ORDER BY createTime asc")
	public List<ParameterBaseBeanType> getListSingleDtype();
	
	/**
	 * 
	 * @Title: findParameterBaseBeanTypebyId
	 * @Description: TODO(根据ID查询参数类型)
	 * @param id
	 * @return
	 */
	public ParameterBaseBeanType findParameterBaseBeanTypeById(@Param("id")String id);
	/**
	 * 根据编码+名称查询
	 * @param entity
	 * @return
	 */
	public ParameterBaseBeanType doOpFindQqByParameterBaseBeanType(@Param("entity")ParameterBaseBeanType entity);
	/**
	 * 根据序号查询 是否重复
	 * @param orderNo
	 * @return
	 */
	public int findCountByOrderNo(@Param("orderNo")Integer orderNo);
	/**
	 * 查询全部 根据序号正序排序
	 * @return
	 */
	@Select("SELECT * FROM base_parameterbasebeantype ORDER BY orderNo")
	public List<ParameterBaseBeanType> findAllParameterBaseBeanType();
}
