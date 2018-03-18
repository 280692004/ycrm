var var_action_url ="/allmodule/capitalflow/capitalflow/";
/**
 * 当前gridtable句柄
 */
function gridTableHandle(){
	return $('#table_list');
};

/**
 * 格式化搜索框 点击框外不隐藏
 */
doOpInitSerchWin('capitalFlowSearchMondal');

$(document).ready(function () {
	
	startSetInterval();
	
    $.jgrid.defaults.styleUI = 'Bootstrap';
    gridTableHandle().jqGrid({
    	url:'',
        mtype: 'POST',
        height: 500,
        autowidth: true,
        shrinkToFit: true,
        rowNum: 20,
        rowList: [10, 20, 30],
        colNames: ['序号', '日期', '资金类型', '单价', '数量', '单位', '总额', '内容', 'id'],
	    colModel: [
	        {
	        	name: 'selectRow',
	            index: 'selectRow',
	            width :30,
	            align: "center",
	            sortable: false,
	            resizable:false
	        },
	        {
	            name: 'billdate',
	            index: 'billdate',
	            width: 45,
	            editable: true,
	            align: "center",
	            sortable: false,
	            formatter:function(cellValue,options,rowObject){  
                    return unixToDate(rowObject.billdate);
                },
	        },
	        {
	            name: 'capitalFlowTypeCode',
	            index: 'capitalFlowTypeCode',
	            width: 60,
	            editable: true,
	            align: "center",
	            sortable: false,
	        },
	        {
	            name: 'unitprice',
	            index: 'unitprice',
	            editable: true,
	            width: 40,
	            align: "right",
	            sortable: false,
	        },
	        {
	            name: 'qty',
	            index: 'qty',
	            width: 40,
	            editable: true,
	            align: "right",
	            sortable: false,
	        },
	        {
	            name: 'unitName',
	            index: 'unitName',
	            width: 40,
	            editable: true,
	            sortable: false,
	            align: "center"
	        },
	        {
	            name: 'totalamount',
	            index: 'totalamount',
	            width: 80,
	            editable: true,
	            align: "right",
	            sortable: false,
	        },
	        {
	            name: 'summary',
	            index: 'summary',
	            width: 260,
	            align: "left",
	          	editable: true,
	            sortable: false
	        },
	        {
	            name: 'id',
	            index: 'id',
	            hidden: true
	        }
	    ],
        pager: "#pager_list",
        hidegrid: false,
        gridComplete : function setPage(){
	       doOpHandleRowNumber();
	    },
	    jsonReader: {
			root: "rows",
			total: "totalpages",
			page: "currpage",
			records: "records",
			repeatitems: false
		},
		beforeRequest: function() {
			initFunButs();
        },
		onPaging: function(pgButton) { 
			//当点击翻页按钮但还为展现数据时触发此事件, 当然这跳转栏输入页
            //码改变页时也同样触发此事件
            doOpHandlePageParam(pgButton,gridTableHandle());
		   	var datas = getFormToParam('searchForm');
		    gridTableHandle().jqGrid('setGridParam',{ 
		    	url:var_action_url+"listCapitalFlow.action",
		        postData:datas, //发送数据 
		    }); 
        },
		loadComplete: function (data) {  
			//是否显示新增  如果有数据默认选择第一行
			doOpIsHasAddJurisdiction(data);
        },
        onSelectRow: function(rowId){   
        	var rowData = gridTableHandle().jqGrid('getRowData',rowId);
			doAddOtherOperationButton(rowData.id);
		} 

    });
    
   	// 初始化JQ自带按钮
    //doOpInitJqButtons();

	// Add responsive to jqGrid
    $(window).resize(function(){  
    	gridTableHandle().setGridWidth($(window).width());
    });
    
    $("#pg_pager_list .ui-pg-div").click(function(event){
    	setTimeout(doInitAddOrUpdateForm,1000);
	});
	
	//绑定弹框【新增】
	$(".add_btn").bind('click', function () {
		$("#varDotype").val('Add');
		doOpOpenEditWind();
	});
	
	//绑定弹框【修改】
	$(".edit_btn").bind('click', function () {
		$("#varDotype").val('Update');
		doOpOpenEditWind();
	});
	
	//删除
	$(".delete_btn").bind('click', function () {
		//获取选中行
		var rowId = $('#table_list').jqGrid('getGridParam','selrow');
		//提示
		var rowData = $('#table_list').jqGrid('getRowData',rowId);
		alert(rowData.name+'--'+rowData.amount);
    });
	
	//默认查询
	setTimeout(doOpQueryDatas,500);
	
});	

/**
 * 查询按钮点击查询事件
 */
function doOpQueryDatas(){ 
 	
 	doOpHandleFormPages();
   	var datas = getFormToParam('searchForm');
   	
    gridTableHandle().jqGrid('setGridParam',{ 
    	url:var_action_url+"listCapitalFlow.action",
        datatype:'json', 
        postData:datas, //发送数据 
        page:1 
    }).trigger("reloadGrid"); //重新载入 
    
    //关闭窗口
    doOpCloseSearchMondal('capitalFlowSearchMondal');
}


/**
 * 弹出新增/修改界面
 */
function doOpOpenEditWind(){
	var index = layer.open({
	  	type: 2,
	  	content: 'edit_capitalflow.html',
	  	area: ['1105px', '450px'],
	  	maxmin: true
	});
	layer.full(index);
	
	$("#varIframeIndex").val(index);
}



//日期范围限制
var start = {
    elem: '#start',
    format: 'YYYY/MM/DD',
    min: '2018-01-01 00:00:00', //设定最小日期为当前日期 laydate.now()
    max: '2099-06-16 23:59:59', //最大日期
    istime: true,
    istoday: false,
    choose: function (datas) {
        end.min = datas; //开始日选好后，重置结束日的最小日期
        end.start = datas //将结束日的初始值设定为开始日
    }
};
var end = {
    elem: '#end',
    format: 'YYYY/MM/DD',
    min: '2018-01-01 00:00:00',
    max: '2099-06-16 23:59:59',
    istime: true,
    istoday: false,
    choose: function (datas) {
        start.max = datas; //结束日选好后，重置开始日的最大日期
    }
};
laydate(start);
laydate(end);