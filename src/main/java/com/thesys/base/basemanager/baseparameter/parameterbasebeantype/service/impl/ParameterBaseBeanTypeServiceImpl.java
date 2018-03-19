package com.thesys.base.basemanager.baseparameter.parameterbasebeantype.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.architecture.statemachine.ClassDataManagerUtil;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.controller.ParameterBaseBeanTypeAuthority;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.dao.ParameterBaseBeanTypeMapper;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.ParameterBaseBeanType;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.domain.state.ParameterBaseBeanTypeClassDataManager;
import com.thesys.base.basemanager.baseparameter.parameterbasebeantype.service.ParameterBaseBeanTypeService;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.DateUtil;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.statemachine.IEvent;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;


@Service("parameterBaseBeanTypeService")
@ServiceMapper(ParameterBaseBeanTypeMapper.class)
public class ParameterBaseBeanTypeServiceImpl extends BaseServiceImpl<ParameterBaseBeanType> implements ParameterBaseBeanTypeService {

	@SuppressWarnings("all")
	@Override
	protected ParameterBaseBeanTypeMapper getMapper() {
		return (ParameterBaseBeanTypeMapper)super.getMapper();
	}
	
	@Override
	public List<ParameterBaseBeanType> findAll() {
		return getMapper().findAllParameterBaseBeanType();
	}


	/**
	 * 新增
	 */
	@Override
	public Integer addEntity(ParameterBaseBeanType entity) {
		doOpValidatorParameterBaseBeanType(entity);
		doOpDealParameterBaseBeanType(entity,true);
		return super.addEntity(entity);
	}
	/**
	 * 更新
	 */
	@Override
	public void updateEntity(ParameterBaseBeanType entity) {
		doOpValidatorParameterBaseBeanType(entity);
		doOpDealParameterBaseBeanType(entity,false);
		super.updateEntity(entity);
	}	

	private void doOpDealParameterBaseBeanType(ParameterBaseBeanType entity,Boolean isAdd) {
		if(isAdd){
			entity.setStatus(ValidateUtil.isEmpty(entity.getStatus())?ParameterBaseBeanTypeClassDataManager.STATE_N:entity.getStatus());
			entity.setVersion(ValidateUtil.isEmpty(entity.getVersion())?0:entity.getVersion());	
		}	
		entity.setCreateBy_id(ValidateUtil.isEmpty("createBy_id", entity)?null:entity.getCreateBy_id());
		entity.setLastModifyBy_id(ValidateUtil.isEmpty("lastModifyBy_id", entity)?null:entity.getLastModifyBy_id());
		entity.setCreateTime(ValidateUtil.isEmpty(entity.getCreateTime())?DateUtil.getNow():entity.getCreateTime());
	}
	
	/**
	 * Athos
	 * 2016年6月4日10:53:19
	 * 检查约束字段是否需要更新,如果更新约束字段,就检查是否已经存在约束冲突
	 */
	private void doOpValidatorParameterBaseBeanType(ParameterBaseBeanType entity) {
		if(ValidateUtil.isEmpty("cname", entity)){
			throw new RuntimeException("参数类型名称不能为空!");
		}
		if(ValidateUtil.isEmpty("dtype", entity)){
			throw new RuntimeException("参数类型不能为空!");
		}
		
		ParameterBaseBeanType found = getMapper().doOpFindQqByParameterBaseBeanType(entity);
		if(!ValidateUtil.isEmpty(found)&&!found.getId().equals(ValidateUtil.format("id", entity))){
			throw new RuntimeException("参数编码："+ValidateUtil.format("dtype", entity)+"参数名称："+ValidateUtil.format("cname", entity)+"已存在!");
		}
		
		if(getMapper().findCountByOrderNo(entity.getOrderNo())>0){
			throw new RuntimeException("序号重复");
		}
	}
	
	@Override
	public void updateParameterBaseBeanTypeStatus(Integer id,IEvent event){
		
		ParameterBaseBeanType found = getMapper().findParameterBaseBeanTypeById(id.toString());
		ParameterBaseBeanTypeStateOwner stateOwner= createStateOwner(found);	
		String status=stateOwner.doOpValidator(event);
		
		this.getMapper().updateParameterBaseBeanTypeStatus(id.toString(), status);
	}
	
	private ParameterBaseBeanTypeStateOwner createStateOwner(ParameterBaseBeanType found) {
		
		String key = getStatusKey();
		
		if (null==ClassDataManagerUtil.getRegisterClassDataManager(key)) {
			ClassDataManagerUtil.registerClassDataManager(ParameterBaseBeanTypeClassDataManager.class,key);	
		}
		
		return new ParameterBaseBeanTypeStateOwner(found,"status");
	}
	private String getStatusKey(){
		return ParameterBaseBeanType.class.getName().toLowerCase().concat("_").concat(ParameterBaseBeanTypeStateOwner.PARAMETERBASEBEANTYPE_STATEOWNER_KEY);
	}


	@Override
	public JqGridResultDataDto findFlexiPageParameterBaseBeanTypes(ParameterBaseBeanType entity,Integer pageSize, Integer pageNumber) {
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().doOpFindCountByPageLike(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<ParameterBaseBeanType> fonds = getMapper().doOpFindByPageLike(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(ParameterBaseBeanTypeAuthority.doOpInitButtons(showListData));
		return showListData;
	}
	
	@Override
	public ParameterBaseBeanType findParameterBaseBeanTypeById(String id) {
		return getMapper().findParameterBaseBeanTypeById(id);
	}
	
	@Override
	public List<ParameterBaseBeanType> getListSingleDtype() {
		return getMapper().getListSingleDtype();
	}
}
