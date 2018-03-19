var var_action_url ="/base/basemanager/sysmag/aclrole/";
var var_aclmodule_action_url ="/base/basemanager/sysmag/aclmodule/";
var var_aclbutton_action_url="/base/basemanager/sysmag/aclbutton/";
var var_aclrolebutton_action_url="/base/basemanager/sysmag/aclrolebutton/";

var aclModuleTreeDatas=[];
var aclModuleButs = [];
var aclmoduleId;
var oldAclmoduleId;
var aclroleId;
/**
 * 当前gridtable句柄
 */
function gridTableHandle(){
	return $('#table_list');
};

/**
 * 格式化搜索框 点击框外不隐藏
 */
doOpInitSerchWin('aclRoleSearchMondal');

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
        colNames: ['序号', '编码', '名称', '过期日期', '状态', '备注','id','statuscode'],
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
                name: 'code',
                index: 'code',
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
                name: 'expirateDate',
                index: 'expirateDate',
                width: 30,
                editable:false,
                sortable: false,
                formatter:function(cellValue,options,rowObject){  
                    return unixToDate(rowObject.expirateDate);
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
        editurl:var_action_url+"doOpUpDateAclRole.action?",
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
		    	url:var_action_url+"listAclRole.action",
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
    	url:var_action_url+"listAclRole.action",
        datatype:'json', 
        postData:datas, //发送数据 
        page:1 
    }).trigger("reloadGrid"); //重新载入 
    
    doOpCloseSearchMondal('aclRoleSearchMondal');
}

/**
 * 初始化新增,修改form表单Name
 */
function doInitAddOrUpdateForm(){
	doOpInitAddOrUpdateForm('aclRole.');
}

/**
 * 确认
 */
function doOpconfirm(varid){
	var confirmUrl = var_action_url+"confirmAclRole.action?id="+varid;
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
	var anitConfirmUrl = var_action_url+"antiConfirmAclRole.action?id="+varid;
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
 * 权限分配
 */
function doOpauthority(varid){
	
	var rowdatas = getSelectRow();
	aclroleId = rowdatas.id;
	$("#currentRoleName").html(rowdatas.cname);
	
	$("#varFunSaveBut").click(function(event){
    	doOpFunSaveRoleAuthority(getSelctIds(),aclmoduleId);
	});
	
	doOpInitAclModuleTree();
	
	//默认选择第一个
	doOpInitThisAclModuleButs();
	
	$("#assigningRoleMondal").modal('show');
}

/**
 * 关闭权限分配模态框
 */
function funCloseAssigningRoleMondal(){
	$("#assigningRoleMondal").modal('hide');	
}

/**
 * 初始化模块
 */
function doOpInitAclModuleTree(){
	
	getAclModuleTreeDatas();
	
 	$('#aclModuleTree').treeview({
        color: "#428bca",
        data:aclModuleTreeDatas,
        onNodeSelected: function (event, node) {
        	if ($("#event_output .i-checks:checked").length>0){
        		oldAclmoduleId = aclmoduleId;
        		doOpPromptSave(getSelctIds());
        	}
        	aclmoduleId = node.id;
        	doOpInitThisAclModuleButs();
        }
    });
}

function doOpPromptSave(butsIds){
	//判断是否有值，
	if ($("#event_output .i-checks:checked").length>0){
	    return swal({
	        title: "是否对当前的操作保存?",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "保存",
	        closeOnConfirm: false
	    }, function () {
	    	doOpFunSaveRoleAuthority(butsIds,oldAclmoduleId);
	    });
	}
}

/**
 * 获取选中的but
 */
function getSelctIds(){
	var butsIds = "";
  	$("#event_output .i-checks:checked").each(function(i){
       	if(0==i){
       		butsIds = $(this).attr('k');
    	}else{
        	butsIds += (","+ $(this).attr('k'));
       	}
  	});
    return butsIds;
}


/**
 * 初始化模块按钮事件
 * @param {Object} node
 */
function doOpInitThisAclModuleButs(){
	
	$('#event_output').html('');
	var aclModuleButsHtml = '';
	
	getAclModuleButDatas(aclmoduleId,aclroleId);
	
	$(aclModuleButs).each(function(){
		aclModuleButsHtml += doOpInitAclModuleButInput(this);
    });    
	
	$('#event_output').prepend(aclModuleButsHtml);
	
	funValidateIsSelectAllAclModuleButs();
}

/**
 * 创建事件按钮 并处理默认值
 * @param {Object} obj
 */
function doOpInitAclModuleButInput(obj){
	var varIdFlag = obj.id +'_'+obj.code;
	var aAclModuleButInputhtml = '<div class="col-sm-2"><input class="i-checks" type="checkbox" onclick="funValidateIsSelectAllAclModuleButs()" k="'+ 
	obj.id +'" id="'+ varIdFlag +'"';
	if(obj.actionmethodright){
		aAclModuleButInputhtml += " checked = 'checked' ";
	}
	aAclModuleButInputhtml += ' /><label for="' + varIdFlag + '">&nbsp;&nbsp;' + obj.cname + '</label></div>';
	return aAclModuleButInputhtml;
}


/**
 * 全选
 */
function funSelectAllAclModuleButs(){
	if ($("#varSelectAllAclModuleButs").prop("checked")) {
		$("#event_output .i-checks").prop("checked", "checked");
	} else{
		$("#event_output .i-checks:checked").removeAttr("checked");
	};
}
/**
 * 判断是否全选
 */
function funValidateIsSelectAllAclModuleButs(){
	if ($("#event_output .i-checks:checked").length == $("#event_output .i-checks").length) {
    	$(".allchk").prop("checked", "checked");
   	}else{
   		$(".allchk").removeAttr("checked");
   	}
}

/**
 * 获取模块数据
 */
function getAclModuleTreeDatas(){
	
	var findAclModuleTreeDatasUrl = var_aclmodule_action_url+"findAclModuleTreeDatas.action";
	$.ajax({
		type:"get",
		url:findAclModuleTreeDatasUrl,
		async:false,
		success:function(json){
			aclModuleTreeDatas = json.datas.nodes;
		},
		error:function(er){
			layer.msg('获取模块信息失败', {icon: 5});
		}
	});
}

/**
 * 获取当前模块事件按钮
 */
function getAclModuleButDatas(){
	
	var findAclModuleCodeDatasUrl = var_aclbutton_action_url+"findAclModuleButDatas.action?modelId=" + aclmoduleId +"&roleId=" + aclroleId;
	$.ajax({
		type:"get",
		url:findAclModuleCodeDatasUrl,
		async:false,
		success:function(json){
			aclModuleButs = json.datas;
		},
		error:function(er){
			layer.msg('获取模块信息失败', {icon: 5});
		}
	});
}

/**
 * 保存权限分配
 * @param {Object} butsIds
 * @param {Object} aclmoduleId
 */
function doOpFunSaveRoleAuthority(butsIds,aclmoduleId){
	
	var addOrUpdateUrl = var_aclrolebutton_action_url+"addOrUpdateAclRoleButton.action?aclmoduleId=" + 
		aclmoduleId +"&aclroleId=" + aclroleId+"&butIds=" + butsIds;
	$.ajax({
		type:"get",
		url:addOrUpdateUrl,
		async:false,
		success:function(json){
			swal("保存成功！");;
		},
		error:function(er){
			layer.msg('获取模块信息失败', {icon: 5});
		}
	});
}

