package com.thesys.allmodule.capitalflow.capitalflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thesys.allmodule.capitalflow.capitalflow.domain.CapitalFlow;
import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.CapitalFlowImport;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.dao.BaseMapper;

public interface CapitalFlowMapper extends BaseMapper<CapitalFlow> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public CapitalFlow findCapitalFlowById(@Param("id")String id);
	/**
	 * 根据用户code查询
	 * @param userCode
	 * @return
	 */
	public List<CapitalFlow> doOpFindByUserCode(@Param("userCode")String userCode);
	/**
	 * 分页查询总记录数
	 * @param entity
	 * @return
	 */
	public Long findFlexiPageCapitalFlowCount(@Param("entity")CapitalFlow entity);
	/**
	 * 分页查询
	 * @param entity
	 * @param flexiPageDto
	 * @return
	 */
	public List<CapitalFlow> findFlexiPageCapitalFlows(@Param("entity")CapitalFlow entity,@Param("flexiPageDto")FlexiPageDto flexiPageDto);
	/**
	 * 导入方式批量 新增
	 * @param capitalFlowImports
	 */
	public void doOpBatchAddEntity(@Param("importDetails")List<CapitalFlowImport> importDetails);

}
