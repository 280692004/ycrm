var var_action_url ="/base/basemanager/sysmag/acluser/";
var types = "";//新增修改参数类型下拉数据
/**
 * 当前gridtable句柄
 */
function gridTableHandle(){
	return $('#table_list');
};

/**
 * 格式化搜索框 点击框外不隐藏
 */
doOpInitSerchWin('aclUserSearchMondal');

$(document).ready(function () {
	
	startSetInterval();
	initTypeSelectParam();
	
    $.jgrid.defaults.styleUI = 'Bootstrap';
    gridTableHandle().jqGrid({
    	url:'',
        mtype: 'POST',
        height: 500,
        autowidth: true,
        shrinkToFit: true,
        rowNum: 20,
        rowList: [10, 20, 30],
    
        colNames: ['序号', '名称', '密码', '人员姓名', 'QQ', 'Email', '电话号码','手机号码', '创建日期', '状态', '过期日期', '类型', '备注', 'id', 'statuscode'],
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
                name: 'name',
                index: 'name',
                editrules:{required:false},
                width: 50,
                editable: true,
                sortable: false,
                align: "center"
            },
            {
	            name: 'password',
	            index: 'password',
	            editable: true,
	            sortable: false,
	            edittype:'password',
	            editrules:{edithidden:true, required:false},
	            hidden: true
	        },
            {
                name: 'personName',
                index: 'personName',
                editrules:{required:false},
                width: 50,
                editable: true,
                sortable: false,
                align: "center"
            },
            {
                name: 'qq',
                index: 'qq',
                width: 45,
                editable: true,
                sortable: false,
                
                align: "center"
            },
            {
                name: 'email',
                index: 'email',
                width: 45,
                editable: true,
                editrules:{email:false},
                sortable: false,
                align: "center"
            },
            {
                name: 'phone',
                index: 'phone',
                width: 45,
                editable: true,
                sortable: false,
                align: "center",
                sorttype: "float"
            },
            {
                name: 'mobilePhone',
                index: 'mobilePhone',
                width: 45,
                editable: true,
                sortable: false,
                align: "center",
                sorttype: "float"
            },
             {
                name: 'createTime',
                index: 'createTime',
                width: 30,
                editable:false,
                sortable: false,
                formatter:function(cellValue,options,rowObject){  
                    return unixToDate(rowObject.createTime);
                },
                align:'center'
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
                name: 'expireTime',
                index: 'expireTime',
                width: 30,
                editable:false,
                sortable: false,
                formatter:"date",
                formatter:function(cellValue,options,rowObject){  
                    return unixToDate(rowObject.expireTime);
                },
                align: "center"
            },
            {
                name: 'typeName',
                index: 'typeName',
                width: 25,
                editable: true,
                edittype:"select",
                editrules:{required:true},
                editoptions:{size:1},
                sortable: false,
                align: "left"
            },
            {
                name: 'remark',
                index: 'remark',
                width: 120,
                editable: true,
             	edittype:"textarea",
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
        editurl:var_action_url+"addOrUpdateAclUser.action?",
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
		    	url:var_action_url+"listAclUser.action",
		        postData:datas, //发送数据 
		    }); 
        },
		loadComplete: function (data) {
			//是否显示新增  如果有数据默认选择第一行
			doOpIsHasAddJurisdiction(data);
			gridTableHandle().setColProp('typeName', { editoptions: { value: types} }); 
    		
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
	
	gridTableHandle().setColProp('typeName', { editoptions: { value: types} }); 
	
	//默认查询
	setTimeout(doOpQueryDatas,500);
	
});	


/**
 * kyle
 * 初始化需要变更的字段名
 * 在使用gqgrid 自带新增,修改功能时，需要替换字段  如实体字段type,为了显示就是格式化一个typeName,
 * 这样使用自带的更新功能，组装出来的form对应的字段就是typeName,而不是我们期望的type,所以需要处理一下，
 * 也可以去后台去处理。这里处理后台就不需要那么麻烦。
 */
function getNeedChangeFieldMap(){
	needChangeFieldMap = {
	    'typeName':'type'
	};
}
/**
 * 修改操作时，定义哪些字段不需要修改。
 * 即定义次这段后，在修改操作的时候 设定字段为只读模式
 */
function getNotNeedUpdateFieldList(){
	notNeedUpdateFieldList = new Array("name");
}
	
/**
 * 查询按钮点击查询事件
 */
function doOpQueryDatas(){ 
 	
	doOpHandleFormPages();
   	var datas = getFormToParam('searchForm');
   	
    gridTableHandle().jqGrid('setGridParam',{ 
    	url:var_action_url+"listAclUser.action",
        datatype:'json', 
        postData:datas, //发送数据 
        page:1 
    }).trigger("reloadGrid"); //重新载入 
    
    //doOpCloseSearchMondal('aclUserSearchMondal');
}

/**
 * 初始化新增,修改form表单Name
 */
function doInitAddOrUpdateForm(){
	doOpInitAddOrUpdateForm('aclUser.');
}

/**
 * 确认
 */
function doOpconfirm(varid){
	var confirmUrl = var_action_url+"confirmAclUser.action?id="+varid;
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
	var anitConfirmUrl = var_action_url+"antiConfirmAclUser.action?id="+varid;
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
 * 初始化 新增修改参数类型下拉数据
 */
function initTypeSelectParam(){
	$.ajax({  
	    url: var_action_url + 'findTyeSelectParams.action',//请求路径  
	    async: false, 
	     success: function(data, result) {  
	         types = data.message;  
	         if (!result){
	         	alert('用户类型加载失败...');   
	         }
		}
	});
};
