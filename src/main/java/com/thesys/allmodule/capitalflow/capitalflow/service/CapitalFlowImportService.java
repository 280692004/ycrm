package com.thesys.allmodule.capitalflow.capitalflow.service;

import java.io.FileInputStream;

import com.thesys.allmodule.capitalflow.capitalflow.service.impl.util.CapitalFlowImport;
import com.thesys.architecture.service.BaseService;

public interface CapitalFlowImportService extends BaseService<CapitalFlowImport> {
	/**
	 * 从Excel文件中导入FinancialAccount科目
	 * @param inputStream
	 */
	public void importCapitalFlowDatas(FileInputStream inputStream);
}