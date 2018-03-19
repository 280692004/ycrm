package com.thesys.allmodule.capitalflow.capitalflow.service.impl;

import java.io.FileInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesys.allmodule.capitalflow.capitalflow.service.CapitalFlowImportService;
import com.thesys.allmodule.capitalflow.capitalflow.service.CapitalFlowService;
import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.CapitalFlowImport;
import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.CapitalFlowImportHelper;
import com.thesys.architecture.service.impl.BaseServiceImpl;
@Service("capitalFlowImportService")
public class CapitalFlowImportServiceImpl extends BaseServiceImpl<CapitalFlowImport> implements CapitalFlowImportService {


	@Autowired
	private CapitalFlowService capitalFlowService;
	
	@Override
	public void importCapitalFlowDatas(FileInputStream inputStream){
		CapitalFlowImportHelper capitalFlowImportHelper = new CapitalFlowImportHelper(inputStream);
		List<CapitalFlowImport> capitalFlowImports = capitalFlowImportHelper.importData();
		doOpDealCapitalFlowImport(capitalFlowImports);
	}
	
	
	private void doOpDealCapitalFlowImport(List<CapitalFlowImport> capitalFlowImports){
		validatorCapitalFlowImportImport(capitalFlowImports);
		for(CapitalFlowImport capitalFlowImport:capitalFlowImports){
			doOpDealCapitalFlowImport(capitalFlowImport);
		}
		
		this.capitalFlowService.doOpBatchAddEntity(capitalFlowImports);
	}
	

	/**
	 * 1、验证数据有效性
	 * 2、验证.是否是全角.
	 */
	private void validatorCapitalFlowImportImport(List<CapitalFlowImport> financialAccountImports) {
		//StringBuffer sb =new StringBuffer();
		
	}
	
	/**
	 * 处理关联关系
	 * @param financialAccountImport
	 */
	private void doOpDealCapitalFlowImport(CapitalFlowImport capitalFlowImport){
		//capitalFlowImport.setCapitalFlowTypeCode(CapitalFlowType)
	}
	
}
