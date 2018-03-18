package com.thesys.base.basemanager.baseparameter.parameterbasebean.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.architecture.statemachine.ClassDataManagerUtil;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.controller.ParameterBaseBeanAuthority;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.dao.ParameterBaseBeanMappper;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.ParameterBaseBean;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.domain.state.ParameterBaseBeanClassDataManager;
import com.thesys.base.basemanager.baseparameter.parameterbasebean.service.ParameterBaseBeanService;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.DateUtil;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;

@Service("parameterBaseBeanService")
@ServiceMapper(ParameterBaseBeanMappper.class)
public class ParameterBaseBeanServiceImpl extends BaseServiceImpl<ParameterBaseBean> implements ParameterBaseBeanService {

	/**
	 * 上下层级最高层次
	 */
	public final static int PARAMETERBASEBEAN_MAX_LEVEL = 5;
	
	@SuppressWarnings("unchecked")
	@Override
	protected ParameterBaseBeanMappper getMapper() {
		return super.getMapper();
	}
	
//----------Start --------------新增/修改--------------------Start----------------------------------
	
	@Override
	public Integer addEntity(ParameterBaseBean entity) {
		validateEntity(entity);
		doOpDealParameterBaseBean(entity, true);
		return super.addEntity(entity);
	}
	
	@Override
	public void updateEntity(ParameterBaseBean entity) {
		validateEntity(entity);
		doOpDealParameterBaseBean(entity, false);
		super.updateEntity(entity);
	}
	
	/**
	 * 检查约束字段是否需要更新,如果更新约束字段,就检查是否已经存在约束冲突
	 */
	private void validateEntity(ParameterBaseBean entity) {
		validatorEmptyProperty(entity);
		ParameterBaseBean parameterBaseBean = new ParameterBaseBean();
		parameterBaseBean.setId(entity.getId()==null?null:entity.getId());
		parameterBaseBean.setCode(entity.getCode());
		if(getMapper().findCountByPropertyNeId(parameterBaseBean)>0){
			throw new RuntimeException("重复参数代码!更新失败!");
		}
		parameterBaseBean.setCode(null);
		parameterBaseBean.setCname(entity.getCname());
		parameterBaseBean.setDtype(entity.getDtype());
		if(getMapper().findCountByPropertyNeId(parameterBaseBean)>0){
			throw new RuntimeException("重复参数名,参数类型!更新失败!");
		}
		if (!ValidateUtil.isEmpty("parentcode", entity)) {
			validatorParameterBaseBeanParent(entity.getDtype(),entity.getParentcode());
		}
	}

	private void validatorEmptyProperty(ParameterBaseBean entity){
		if (ValidateUtil.isEmpty("code", entity)) {
			throw new RuntimeException("参数编码不能为空");
		}
		if (ValidateUtil.isEmpty("cname", entity)) {
			throw new RuntimeException("参数名称不能为空");
		}
		if (ValidateUtil.isEmpty("level", entity)) {
			throw new RuntimeException("参数显示序号不能为空");
		}
	}

	// 验证父类是否存在死循环，如果父类超过 PARAMETERBASEBEAN_MAX_LEVEL 级就认为存在死循环
	private void validatorParameterBaseBeanParent(String dtype,String parentCode) {
		ParameterBaseBean parameterBaseBean = this.getMapper().findParameterBaseBeanByDtypeAndCode(dtype,parentCode);
		int index = 0;
		while (!ValidateUtil.isEmpty("parentcode", parameterBaseBean)) {
			parameterBaseBean = this.getMapper().findParameterBaseBeanByDtypeAndCode(dtype,parameterBaseBean.getParentcode());
			index++;
			if (index > PARAMETERBASEBEAN_MAX_LEVEL) {
				throw new RuntimeException("参数类型的上下级不能大于"+PARAMETERBASEBEAN_MAX_LEVEL+"级");
			}
		}
	}
	
	private void doOpDealParameterBaseBean(ParameterBaseBean entity,Boolean isAdd) {
		if (isAdd) {
			entity.setStatus(ValidateUtil.isEmpty(entity.getStatus()) ? ParameterBaseBeanClassDataManager.STATE_N: entity.getStatus());
			entity.setVersion(ValidateUtil.isEmpty(entity.getVersion()) ? 0	: entity.getVersion());
			entity.setCreateTime(ValidateUtil.isEmpty(entity.getCreateTime()) ? DateUtil.getNow(): entity.getCreateTime());
		}
		entity.setParentcode(ValidateUtil.isEmpty("parentcode", entity) ? null : entity.getParentcode());
		entity.setCreateBy_id(ValidateUtil.isEmpty("createBy_id", entity) ? null : entity.getCreateBy_id());
		entity.setLastModifyBy_id(ValidateUtil.isEmpty("lastModifyBy_id",	entity) ? null : entity.getLastModifyBy_id());
	}
	
//----------------------END--------------------新增/修改----------------------END-----------------------	
	
	
	
	@Override
	public List<ParameterBaseBean> findParameterBaseBeanByDType(String dtype) {
		return  getMapper().findParameterBaseBeanByDType(dtype);
	}

	@Override
	public ParameterBaseBean findParameterBaseBeanById(String id) {
		return getMapper().findParameterBaseBeanById(id);
	}

	@Override
	public JqGridResultDataDto findFlexiPageParameterBaseBeans(ParameterBaseBean entity,Integer pageSize, Integer pageNumber) {
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().doOpFindCountByPageLike(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<ParameterBaseBean> fonds = getMapper().doOpFindByPageLike(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(ParameterBaseBeanAuthority.doOpInitButtons(showListData));
		return showListData;
	}

	@Override
	public void updateParameterBaseBeanStatus(Integer id, IEvent event) {
		ParameterBaseBean found = getMapper().findParameterBaseBeanById(id.toString());
		ParameterBaseBeanStateOwner stateOwner = createStateOwner(found);
		String status = stateOwner.doOpValidator(event);
		this.getMapper().updateParameterBaseBeanStatus(id.toString(), status);
	}
	
	private String getStatusKey() {
		return ParameterBaseBean.class.getName().toLowerCase().concat("_").concat(ParameterBaseBeanStateOwner.PARAMETERBASEBEAN_STATEOWNER_KEY);
	}
	
	private ParameterBaseBeanStateOwner createStateOwner(ParameterBaseBean found) {
		String key = getStatusKey();
		if (null == ClassDataManagerUtil.getRegisterClassDataManager(key)) {
			ClassDataManagerUtil.registerClassDataManager(ParameterBaseBeanClassDataManager.class, key);
		}

		return new ParameterBaseBeanStateOwner(found, "status");
	}
	
	@Override
	public ParameterBaseBean findParameterBaseBeanByDtypeAndCode(String dtype, String code) {
		return this.getMapper().findParameterBaseBeanByDtypeAndCode(dtype, code);
	}

	@Override
	public List<ParameterBaseBean> findParameterBaseBeansByParamPrecise(ParameterBaseBean parameterBaseBean) {
		return getMapper().findParameterBaseBeansByParamPrecise(parameterBaseBean);
	}

	@Override
	public List<ParameterBaseBean> findParameterBaseBeanByParent(String dtype,String parentcode) {
		return getMapper().findParameterBaseBeanByParentCode(parentcode,dtype);
	}

	@Override
	public Boolean doOpCheckHasSubParam(String dtype, String paramCode) {
		Integer startindex = 1;
		Integer nextindex = this.getMapper().doOpCalNextSeq(dtype,paramCode);
		return !startindex.equals(nextindex);
	}

	@Override
	public Integer doOpCalNextSeq(String dtype, String parentCode) {
		return this.getMapper().doOpCalNextSeq(dtype,parentCode);
	}	
}
