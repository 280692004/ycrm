var var_action_url ="/base/basemanager/sysmag/aclbutton/";
var var_aclmodule_action_url ="/base/basemanager/sysmag/aclmodule/";
var moduleparameters="";//模块下拉数据定义
/**
 * 当前gridtable句柄
 */
function gridTableHandle(){
	return $('#table_list');
};

/**
 * 格式化搜索框 点击框外不隐藏
 */
doOpInitSerchWin('aclButtonSearchMondal');

$(document).ready(function () {
	
	startSetInterval();
	initParentSelectParam();
	
    $.jgrid.defaults.styleUI = 'Bootstrap';
    gridTableHandle().jqGrid({
    	url:'',
        mtype: 'POST',
        height: 500,
        autowidth: true,
        shrinkToFit: true,
        rowNum: 20,
        rowList: [10, 20, 30],
        colNames: ['序号', '模块名称', '编码', '名称', '按钮图标', '状态', '备注','id','statuscode'],
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
                name: 'module.cname',
                index: 'module.cname',
                width: 50,
                editable: true,
                edittype:"select",
                editrules:{required:true},
                editoptions:{size:1},
                sortable: false,
                align: "left"
            },
            {
                name: 'code',
                index: 'code',
                width: 50,
                editable: true,
                sortable: false,
                align: "left"
            },
            {
                name: 'cname',
                index: 'cname',
                width: 50,
                editable: true,
                sortable: false,
                align: "left",
                sorttype: "float"
            },
            {
                name: 'iconName',
                index: 'iconName',
                width: 50,
                editable: true,
                sortable: false,
                align: "left",
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
                width: 150,
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
        editurl:var_action_url+"addOrUpdateAclButton.action?",
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
		    	url:var_action_url+"listAclButton.action",
		        postData:datas, //发送数据 
		    }); 
        },
		loadComplete: function (data) {  
			//是否显示新增  如果有数据默认选择第一行
			doOpIsHasAddJurisdiction(data);
			gridTableHandle().setColProp('module.cname', { editoptions: { value: moduleparameters} });  
        },
        onSelectRow: function(rowId){   
        	var rowData = gridTableHandle().jqGrid('getRowData',rowId);
			doAddOtherOperationButton(rowData.id);
		} 

    });
    
   	// 初始化JQ自带按钮
    doOpInitJqButtons();
    //初始化查询下拉数据
    doOpInitSelectParams();

	// Add responsive to jqGrid
    $(window).resize(function(){  
    	gridTableHandle().setGridWidth($(window).width());
    });
    
    $("#pg_pager_list .ui-pg-div").click(function(event){
    	setTimeout(doInitAddOrUpdateForm,1000);
	});
	
	gridTableHandle().setColProp('module.cname', { editoptions: { value: moduleparameters} });  
	//默认查询
	setTimeout(doOpQueryDatas,500);
	
});	


/**
 * 初始化搜索下拉数据
 */
function doOpInitSelectParams(){
	var serchSelectParamsUrl = var_action_url+"findAllAclModuleParams.action";
	$.ajax({
		type:"get",
		url:serchSelectParamsUrl,
		async:true,
		success:function(json){
			//清空下拉数据
			$(".module_select").empty();
			//加载模块搜索下拉
			$(".module_select").append("<option value=''>全部</option>"); 
			$.each(json.datas, function (n, value) {
				$(".module_select").append("<option value='"+value.id+"'>"+value.cname+"</option>"); 
			});
			
		},
		error:function(er){
			layer.msg('查询模块数据失败', {icon: 5});
		}
	});
	
}

/**
 * kyle
 * 初始化需要变更的字段名
 * 在使用gqgrid 自带新增,修改功能时，需要替换字段  如实体字段type,为了显示就是格式化一个typeName,
 * 这样使用自带的更新功能，组装出来的form对应的字段就是typeName,而不是我们期望的type,所以需要处理一下，
 * 也可以去后台去处理。这里处理后台就不需要那么麻烦。
 */
function getNeedChangeFieldMap(){
	needChangeFieldMap = {
	    'module.cname':'module_id'
	};
}
	
/**
 * 查询按钮点击查询事件
 */
function doOpQueryDatas(){ 
 	
 	doOpHandleFormPages();
   	var datas = getFormToParam('searchForm');
   	
    gridTableHandle().jqGrid('setGridParam',{ 
    	url:var_action_url+"listAclButton.action",
        datatype:'json', 
        postData:datas, //发送数据 
        page:1 
    }).trigger("reloadGrid"); //重新载入 
    
    //关闭窗口
    doOpCloseSearchMondal('aclButtonSearchMondal');
}

/**
 * 初始化新增,修改form表单Name
 */
function doInitAddOrUpdateForm(){
	doOpInitAddOrUpdateForm('aclButton.');
}

/**
 * 确认
 */
function doOpconfirm(varid){
	var confirmUrl = var_action_url+"confirmAclButton.action?id="+varid;
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
	var anitConfirmUrl = var_action_url+"antiConfirmAclButton.action?id="+varid;
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


/**
 * 初始化 新增修改模块下拉数据
 */
function initParentSelectParam(){
	$.ajax({  
	    url: var_aclmodule_action_url+'getParentSelectParam.action',//请求路径  
	    async: false, 
	    success: function(data, result) {  
	        moduleparameters = data.message;  
	        if (!result){
	        	alert('参数类型加载失败...');   
	        }
		}
	});
};