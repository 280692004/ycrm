package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.ParameterBaseBeanType;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.core.dto.JqGridResultDataDto;

public interface ParameterBaseBeanTypeService extends BaseService<ParameterBaseBeanType> {
	/***
	 * @Title: findFlexiPageParameterBaseBeanTypes
	 * @Description: TODO(分页模糊查询)
	 * @param parameterBaseBeanType
	 * @param flexiPageDto
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageParameterBaseBeanTypes(ParameterBaseBeanType parameterBaseBeanType,Integer pageSize, Integer pageNumber);
	/**
	 * @Title: findParameterBaseBeanTypeById
	 * @Description: TODO(根据id查询)
	 * @param id
	 * @return
	 */
	public ParameterBaseBeanType findParameterBaseBeanTypeById(String id);
	/**
	 * @Title: getListSingleDtype
	 * @Description: TODO(分组查询参数类型)
	 * @return
	 */
	public List<ParameterBaseBeanType> getListSingleDtype();
	/**
	 * @Title: updateParameterBaseBeanTypeStatus
	 * @Description: TODO(更新参数类型状态)
	 * @param id
	 * @param event
	 */
	public void updateParameterBaseBeanTypeStatus(Integer id,IEvent event);
}
