package com.thesys.allmodule.capitalflow.capitalflow.service.impl.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thesys.base.core.util.excel.excelimport.DefaultExcelImport;
import com.thesys.base.core.util.excel.excelimport.ExcelImportCell;
import com.thesys.base.core.util.excel.excelimport.ExcelImportCellValidatorFactory;

public class CapitalFlowImportHelper extends DefaultExcelImport<CapitalFlowImport> {

	
	private final Long skipRows = 1L;

	private static ExcelImportCell[] excelImportCells = new ExcelImportCell[]{
		new ExcelImportCell("业务日期*", "billdate").setTargetCellType(ExcelImportCell.CELL_TYPE_DATE),
		new ExcelImportCell("资金类型*", "capitalFlowTypeCode",ExcelImportCellValidatorFactory.createStringCanNotBlankValidator("资金类型*")),
		new ExcelImportCell("单位*", "unitCode",ExcelImportCellValidatorFactory.createStringCanNotBlankValidator("单位*")),
		new ExcelImportCell("数量*", "qty",ExcelImportCellValidatorFactory.createPositiveNumberValidator("数量*")),
		new ExcelImportCell("单价*", "unitprice",ExcelImportCellValidatorFactory.createPositiveNumberValidator("单价*")),
		new ExcelImportCell("总额*", "totalamount",ExcelImportCellValidatorFactory.createPositiveNumberValidator("总额*")),
		new ExcelImportCell("使用者*", "user",ExcelImportCellValidatorFactory.createStringCanNotBlankValidator("使用者*")),
		new ExcelImportCell("摘要", "summary"),
	};
			
	public CapitalFlowImportHelper(FileInputStream inputStream) {
		super(Arrays.asList(excelImportCells), inputStream);		
		currentDataReadRow = skipRows;
	}

	@Override
	protected List<Object> readHeaderRow(int columnSize) {
		List<Object> list = readWorkbookRow(skipRows, 0, false);
		return list;
	}
	
	@Override
	public List<CapitalFlowImport> importData(int sheetIndex) {
		
		final List<CapitalFlowImport> result = new ArrayList<CapitalFlowImport>();
		ICreatedDataProcessor<CapitalFlowImport> createdDataProcessor = new ICreatedDataProcessor<CapitalFlowImport>() {
			@Override
			public void process(CapitalFlowImport data) {
				result.add(data);
			}
		};
		importData(createdDataProcessor, sheetIndex);
		return result;
	}
	
	@Override
	protected void dealOneRowData(List<Object> items) {
		
		super.dealOneRowData(items);
	}
}
