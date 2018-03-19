
function doOpInitSerchWin(searchMondal){
	$('#'+searchMondal).modal({backdrop: 'static', keyboard: false});
	$('#'+searchMondal).modal('hide');
}

/**
 * 获取选中行
 */
function getSelectRow(){
	var rowId = gridTableHandle().jqGrid('getGridParam','selrow');
	if(isEmpty(rowId)){
		return null;
	}
	return gridTableHandle().jqGrid('getRowData',rowId);
}

/**
* kyle
 * 定义需要修改的字段
 * Map<需修改字段,修改后字段>
 * 初始化需要变更的字段名
 * 在使用gqgrid 自带新增,修改功能时，需要替换字段  如实体字段type,为了显示就是格式化一个typeName,
 * 这样使用自带的更新功能，组装出来的form对应的字段就是typeName,而不是我们期望的type,所以需要处理一下，
 * 也可以去后台去处理。这里处理后台就不需要那么麻烦。
 */
var needChangeFieldMap;
function getNeedChangeFieldMap(){
	needChangeFieldMap = {};
}
/**
 * 定义修改时 不需要改变的字段
 * 即定义次这段后，在修改操作的时候 设定字段为只读模式
 */
var notNeedUpdateFieldList;
function getNotNeedUpdateFieldList(){
	notNeedUpdateFieldList = [];
}

/**
 * 按钮权限 
 */
var buts = {};
/**
 * 初始化JQ自带按钮
 * 如果需要自定义 则需要重新次方法
 */
function doOpInitJqButtons(){
	gridTableHandle().jqGrid('navGrid', '#pager_list', {
        add: true,
        addtext:'新增',
        edit: true,
        edittext:'修改',
        del: true,
        deltext:'删除',
        search: false,
        refresh:true,
        refreshtext:'刷新'
    }, {
        height: 'auto',
        reloadAfterSubmit: true
    });
}
/**
 * 初始化jqgrid 自带按钮 默认隐藏
 */
function initFunButs(){
	gridTableHandle().jqGrid("clearGridData");
	$("#var_otherOperationButton").html('');
	$("#add_table_list").hide();
	$("#edit_table_list").hide();
	$("#del_table_list").hide();
}

/**
 * 增加其他按钮
 * commonbuts 基础按钮，新增，修改，删除
 */
function doAddOtherOperationButton(rowDataId){
	
	if(isEmptyObject(buts) || isEmptyObject(buts[rowDataId])){
		return;
	}	
		
	var commonbuts = buts[rowDataId].left;
	var otherbuts = buts[rowDataId].right;
	//默认隐藏
	$("#edit_table_list").hide();
    $("#del_table_list").hide();
	//修改
	if(-1 != $.inArray("update", commonbuts)){
		$("#edit_table_list").show();
	}
	//删除
	if(-1 != $.inArray("delete", commonbuts)){
		$("#del_table_list").show();
	}
	var operationButtonhtml = '';
    //funName:函数名称 如确认 就是conFirmObj   cname:界面显示按钮名称 如 确认    iconName 按钮中的图标 
    $(otherbuts).each(function(){  
        operationButtonhtml = '<button type="button" onclick="'+this.funName+'('+rowDataId+')" class="btn btn-info" >'+ this.cname 
		+ '：<i class="glyphicon '+ this.iconName+'" aria-hidden="true"></i></button>';
    });     
 	if(!isEmpty(operationButtonhtml)){
		$("#var_otherOperationButton").html(operationButtonhtml);
	}
	 
}

/**
 * 判断是否有新增权限
 * 有就显示新增按钮，没有就隐藏
 * 
 * 默认选择第一行
 */
function doOpIsHasAddJurisdiction(data){
	buts = {};
	if(!isEmptyObject(data.butsMap)){
		buts = data.butsMap;
	}
	
	if(data.hasAdd){
		$("#add_table_list").show();
	}else{
		$("#add_table_list").hide();
	}
	
	// 默认选中第一行
	var rowsIdArray = gridTableHandle().getDataIDs();
	if(rowsIdArray.length>0){
		gridTableHandle().setSelection(rowsIdArray[0], true);
	}
}

/**
 * 处理查询用到的分页参数
 */
function doOpHandleFormPages(){
	gridTableHandle().jqGrid("clearGridData");
	var pagenumber = gridTableHandle().getGridParam('page');
   	var pagesize = gridTableHandle().jqGrid('getGridParam', 'rowNum');
   	$('#var_pageNumber').val(pagenumber);
   	$('#var_pageSize').val(pagesize);
}

/**
 * 处理列表序号
 */
function doOpHandleRowNumber(){
	var ids = gridTableHandle().jqGrid('getDataIDs');
   	var pagenumber = gridTableHandle().getGridParam('page');
   	var pagesize = gridTableHandle().getGridParam('rowNum');  
   	var rowNumber = (pagenumber-1)*pagesize;
 	for(var i=0;i < ids.length;i++){
      	gridTableHandle().jqGrid('setRowData',ids[i],{selectRow:(i+1)+rowNumber});
    }
}

/**
 * kyle
 * 翻页 页码设置
 * @param {Object} clicktype 点击类型
 * @param {Object} clicktype 分页句柄
 */
function doOpHandlePageParam(clicktype,GridTableHandle){
	//当前页码参数
   	var pagenumber = GridTableHandle.getGridParam('page');
   	var pagesize   = GridTableHandle.getGridParam('rowNum');  
   	var pagetotal  = GridTableHandle.getGridParam('lastpage');
   	var records = $("#pager_list_center select").val();
	 //第一页
    if('first'==clicktype){
    	pagenumber = 1;
    }
    //上一页
    if('prev'==clicktype){
    	pagenumber = pagenumber - 1;
    }
	//下一页
	if('next'==clicktype){
        pagenumber = pagenumber + 1;
    }
	//最后一页
    if('last'==clicktype){
    	pagenumber = pagetotal;
    }
    //调整显示页数
    if('records'==clicktype){
    	pagenumber = 1;
    	pagesize = records;
    }
    $('#var_pageNumber').val(pagenumber);
    $('#var_pageSize').val(pagesize);
}


/**
 * kyle
 * 使用jqgridTble加载数据 自带新增修改功能时使用。
 * 初始化新增,修改form表单Name 提交参数组装对象
 *  @param {Object} varentity 实体对象 ：如：parameterBaseBeanType.
 */
function doOpInitAddOrUpdateForm(varentity){
	//处理更新时不能修改的数据
	getNotNeedUpdateFieldList();
	getNeedChangeFieldMap();
	
	var isAdd = true;
	
	$("#FrmGrid_table_list .FormElement").each(function () {
	    var this_field = $(this).attr("name");
	    
	    //id单独处理 table_list_id 代表Id
	    if("table_list_id" == this_field){
	    	$(this).attr("name",varentity+"id");
	    	var objId = $(this).attr("value");
	    	if(isEmpty(objId)){
	    		$(this).attr("value","");
	    	}else{
	    		isAdd = false;
	    	}
	    }else{
	    	var field = needChangeFieldMap[this_field];
	    	if(!isEmpty(field)){
	    		this_field = field;
	    	}
	    	$(this).attr("name",varentity+this_field);
	    }
	});
	//处理不需要更新的字段
	if(!isAdd){
		var fieldName = "";
		for (x in notNeedUpdateFieldList){
			fieldName = varentity+notNeedUpdateFieldList[x];
			$("#FrmGrid_table_list  input[name='"+fieldName+"']").attr("readOnly",true);
		}
	}
}

/**
 * 关闭搜索模态框
 */
function doOpCloseSearchMondal(searchMondal){
	$('#'+searchMondal).modal('hide');
};

/**
 * 日期格式化
 * @param {Object} format
 * 时间戳转换日期              
 * @param <int> unixTime    待时间戳(秒)              
 * @param <bool> isFull    返回完整时间(Y-m-d 或者 Y-m-d H:i:s)              
 * @param <int>  timeZone   时区              
 */
function unixToDate(unixTime, isFull, timeZone) {
	if(isEmpty(unixTime)){
		return "";
	}
    if (typeof (timeZone) == 'number')
    {
        unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
    }
    var time = new Date(unixTime);
    var ymdhis = "";
    ymdhis += time.getUTCFullYear() + "-";
    ymdhis += (time.getUTCMonth()+1) + "-";
    ymdhis += time.getUTCDate();
    if (isFull === true)
    {
        ymdhis += " " + time.getUTCHours() + ":";
        ymdhis += time.getUTCMinutes() + ":";
        ymdhis += time.getUTCSeconds();
    }
    return ymdhis;
}

