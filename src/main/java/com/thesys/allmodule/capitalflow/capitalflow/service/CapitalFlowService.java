package com.thesys.allmodule.capitalflow.capitalflow.service;

import java.util.Date;
import java.util.List;

import com.thesys.allmodule.capitalflow.capitalflow.domain.CapitalFlow;
import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.CapitalFlowImport;
import com.thesys.architecture.service.BaseService;
import com.thesys.core.dto.JqGridResultDataDto;
/**
 * 资金流转Service
 * @author Kyle
 */
public interface CapitalFlowService extends BaseService<CapitalFlow>{

	/**
	 * 根据用户查询资金流转记录
	 * @param userCode
	 */
	public List<CapitalFlow> doOpFindByUserCode(String userCode);
	/**
	 * 模糊查询 不分页，用于PDF打印数据
	 * @param entity
	 * @return
	 */
	public List<CapitalFlow> findAllDatasByPageLike(CapitalFlow entity);
	/**
	 * 分页查询我的资金流水
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageCapitalFlows(CapitalFlow entity,Integer pageSize, Integer pageNumber);
	/**
	 * 新增或者修改
	 */
	public void doOpAddOrUpdateEntity(String creator, Date billdate, List<CapitalFlow> details);
	/**
	 * 导入方式和批量新增
	 * @param capitalFlowImports
	 */
	public void doOpBatchAddEntity(List<CapitalFlowImport> capitalFlowImports);
	
}
