var var_action_url ="/base/basemanager/baseparameter/parameterbasebeantype/";
/**
 * 当前gridtable句柄
 */
function gridTableHandle(){
	return $('#table_list');
};

/**
 * 格式化搜索框 点击框外不隐藏
 */
doOpInitSerchWin('parameterBaseBeanTypeSearchMondal');

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
        colNames: ['序号', '参数类型', '类型名称', '排序级别', '状态', '备注','id','statuscode'],
        colModel: [
            {
	        	name: 'selectRow',
	            index: 'selectRow',
	            width :20,
	            sortable: false,
	            align: "center",
	            resizable:false
	        },
            {
                name: 'dtype',
                index: 'dtype',
                width: 60,
                editable: true,
                sortable: false,
                align: "center"
            },
            {
                name: 'cname',
                index: 'cname',
                width: 60,
                editable: true,
                sortable: false,
                align: "center"
            },
            {
                name: 'orderNo',
                index: 'orderNo',
                width: 35,
                editable: true,
                sortable: false,
                align: "center",
                sorttype: "float"
            },
            {
                name: 'statusName',
                index: 'statusName',
                width: 25,
                editable: false,
                sortable: false,
                align: "center"
            },
            {
                name: 'remark',
                index: 'remark',
                width: 200,
                editable: true,
                sortable: false,
                align: "left"
            },
	        {
	            name: 'id',
	            index: 'id',
	            hidden: true
	        },
	        {
	            name: 'status',
	            index: 'status',
	            editable: false,
	            hidden: true
	        }
        ],
        pager: "#pager_list",
        viewrecords: true,
        //caption: "jqGrid 示例2",
        add: true,
        edit: true,
        addtext: 'Add',
        edittext: 'Edit',
        editurl:var_action_url+"doOpUpDateParameterType.action?",
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
		    	url:var_action_url+"listParameterBaseBeanType.action",
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
    doOpInitJqButtons();
    
	// Add responsive to jqGrid
    $(window).resize(function(){  
    	gridTableHandle().setGridWidth($(window).width());
    });
    
    $("#pg_pager_list .ui-pg-div").click(function(event){
    	setTimeout(doInitAddOrUpdateForm,1000);
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
    	url:var_action_url+"listParameterBaseBeanType.action",
        datatype:'json', 
        postData:datas, //发送数据 
        page:1 
    }).trigger("reloadGrid"); //重新载入 
    
    //关闭窗口
    doOpCloseSearchMondal('parameterBaseBeanTypeSearchMondal');
}

/**
 * 初始化新增,修改form表单Name
 */
function doInitAddOrUpdateForm(){
	doOpInitAddOrUpdateForm('parameterBaseBeanType.');
}

/**
 * 确认
 */
function doOpconfirm(varid){
	var confirmUrl = var_action_url+"confirmParameterBaseBeanType.action?id="+varid;
	$.ajax({
		type:"get",
		url:confirmUrl,
		async:true,
		success:function(json){
			layer.msg(json.message, {icon: 6});
			$('#var_searchbtn').click();
		},
		error:function(er){
			layer.msg('更新失败', {icon: 5});
		}
	});
}
/**
 * 反确认
 */
function doOpanticonfirm(varid){
	var anitConfirmUrl = var_action_url+"antiConfirmParameterBaseBeanType.action?id="+varid;
	$.ajax({
		type:"get",
		url:anitConfirmUrl,
		async:true,
		success:function(json){
			layer.msg(json.message, {icon: 6});
			$('#var_searchbtn').click();
		},
		error:function(er){
			layer.msg('更新失败', {icon: 5});
		}
	});
}