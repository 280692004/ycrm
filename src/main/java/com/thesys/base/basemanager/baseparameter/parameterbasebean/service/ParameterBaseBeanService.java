package com.thesys.base.basemanager.baseparameter.parameterbasebean.service;

import java.util.List;

import com.thesys.architecture.service.BaseService;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.core.dto.JqGridResultDataDto;

public interface ParameterBaseBeanService extends BaseService<ParameterBaseBean> {
	/**
	 * 根据类型查询这个类型所有的明细(带层次数据)
	 * @param dtype 类型
	 * @return
	 */
	public List<ParameterBaseBean> findParameterBaseBeanByDType(String dtype);	
	/**
	 * 根据参数id查找参数信息
	 * @param id
	 * @return
	 */
	public ParameterBaseBean findParameterBaseBeanById(String id);
	/**
	 * 分页查询用户列表
	 * @param parameterBaseBean
	 * @param flexiPageDto
	 * @return
	 */
	public JqGridResultDataDto findFlexiPageParameterBaseBeans(ParameterBaseBean parameterBaseBean, Integer pageSize, Integer pageNumber);
	/**
	 * 
	 * @Title: updateParameterBaseBeanStatus
	 * @Description: TODO(更新参数状态)
	 * @param id
	 * @param event
	 */
	public void updateParameterBaseBeanStatus(Integer id,IEvent event);
	/**
	 * 根据父类的编码查询下级的明细（例如根据国家查询所有的省、根据省查询所有的市、根据市查询所有的区、根据市查询所有的县）
	 * @param dtype
	 * @param parentcode
	 * @return
	 */
	public List<ParameterBaseBean> findParameterBaseBeanByParent(String dtype,String parentcode);	
	/**
	 * 根据Dtype和code获取参数名称
	 * @param dtype
	 * @param code
	 * @return
	 */
	public ParameterBaseBean findParameterBaseBeanByDtypeAndCode(String dtype,String code);
	
	/**
	 * @Title: doOpCheckHasSubParam
	 * @Description: TODO(是否有子参数)
	 * @param dtype
	 * @param paramCode
	 * @return
	 */
	public Boolean doOpCheckHasSubParam(String dtype,String paramCode);
	/**
	 * 
	 * @Title: doOpCalNextSeq
	 * @Description: TODO(查找下一个参数)
	 * @param dtype
	 * @param parentCode
	 * @return
	 */
	public Integer doOpCalNextSeq(String dtype,String parentCode);
	/**
	 * @Title: findParameterBaseBeansByParamPrecise
	 * @Description: TODO(精确条件查询,无分页)
	 * @param parameterBaseBean
	 * @return 
	 */ 
	public List<ParameterBaseBean> findParameterBaseBeansByParamPrecise(ParameterBaseBean parameterBaseBean);

}
