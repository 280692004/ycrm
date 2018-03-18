package com.thesys.allmodule.capitalflow.capitalflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesys.allmodule.capitalflow.capitalflow.controller.CapitalFlowAuthority;
import com.thesys.allmodule.capitalflow.capitalflow.dao.CapitalFlowMapper;
import com.thesys.allmodule.capitalflow.capitalflow.domain.CapitalFlow;
import com.thesys.allmodule.capitalflow.capitalflow.service.CapitalFlowService;
import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.CapitalFlowImport;
import com.thesys.architecture.base.naturalno.service.impl.DefaultNaturalIdGenerator;
import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.base.core.util.Calculate;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.core.dto.BootstrapResultDataDto;
import com.thesys.core.dto.JqGridResultDataDto;
import com.thesys.core.util.UserContainerSessionUtil;
/**
 * 资金流转实现
 * @author Kyle
 *
 */
@Service("capitalFlowService")
@ServiceMapper(CapitalFlowMapper.class)
public class CapitalFlowServiceImpl extends BaseServiceImpl<CapitalFlow> implements CapitalFlowService{
	
	@Autowired
	private DefaultNaturalIdGenerator defaultNaturalIdGenerator;
	
	@Override
	public Integer addEntity(CapitalFlow entity) {
		doOpDealBasicsVal(entity);
		return super.addEntity(entity);
	}
	
	@Override
	public void updateEntity(CapitalFlow entity) {
		doOpDealBasicsVal(entity);
		super.updateEntity(entity);
	}



	@Override
	public void deleteById(String id) {
		super.deleteById(id);
	}

	@Override
	public CapitalFlow findEntityById(String id) {
		return this.getMapper().findCapitalFlowById(id);
	}

	@Override
	public List<CapitalFlow> doOpFindByUserCode(String userCode) {
		return this.getMapper().doOpFindByUserCode(userCode);
	}
	
	@Override
	public List<CapitalFlow> findAllDatasByPageLike(CapitalFlow entity) {
		return getMapper().findFlexiPageCapitalFlows(entity,null);
	}

	@Override
	public JqGridResultDataDto findFlexiPageCapitalFlows(CapitalFlow entity,Integer pageSize, Integer pageNumber) {
		FlexiPageDto flexiPageDto = new FlexiPageDto().setPage(pageNumber).setRp(pageSize);
		Long totoalCount = getMapper().findFlexiPageCapitalFlowCount(entity);
		flexiPageDto.setRowCount(totoalCount);
		List<CapitalFlow> fonds = getMapper().findFlexiPageCapitalFlows(entity,flexiPageDto);
		Long totalpages = 1L;
		if(Calculate.greaterThan(new Calculate(totoalCount.toString()), new Calculate(pageSize.toString()))){
			Double pages = Math.ceil(new Double(totoalCount)/pageSize);
			totalpages = pages.longValue();
		}
		JqGridResultDataDto showListData = new JqGridResultDataDto(BootstrapResultDataDto.CODE_SUCCESS, totalpages, pageNumber,totoalCount, (Object)fonds);
		showListData.setButsMap(CapitalFlowAuthority.doOpInitButtons(showListData));
		return showListData;
		
	}
	

	@Override
	public void doOpAddOrUpdateEntity(String creator, Date billdate, List<CapitalFlow> details) {
		
		doOpValidateEntity(creator,billdate,details);
		
		for (CapitalFlow entity : details) {
			entity.setBilldate(billdate);
			entity.setUser(creator);
			if(ValidateUtil.isEmpty("id", entity)){
				this.addEntity(entity);
			}else{
				this.updateEntity(entity);
			}
		}
	}
	

	@Override
	public void doOpBatchAddEntity(List<CapitalFlowImport> capitalFlowImports) {
		getMapper().doOpBatchAddEntity(capitalFlowImports);
	}
	
	
	/**
	 * 验证数据有效性 
	 * @param entitys
	 */
	private void doOpValidateEntity(String creator, Date billdate, List<CapitalFlow> details) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 处理基础数据
	 * @param entity
	 */
	private void doOpDealBasicsVal(CapitalFlow entity) {
		if(ValidateUtil.isEmpty("id", entity)){
			UserContainerSessionUtil.doOpDealAddEntityUserAndDateInfo(entity);
			entity.setNaturalId((String)defaultNaturalIdGenerator.generateNaturalId(entity));			
		}else{
			UserContainerSessionUtil.doOpDealUpdateEntityUserAndDateInfo(entity);			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected CapitalFlowMapper getMapper() {
		return (CapitalFlowMapper)super.getMapper();
	}


}
