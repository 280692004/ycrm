var var_action_url ="/allmodule/capitalflow/capitalflow/";


$(function(){
	
	doOpInitSelect();
	doOpInitDatas();
	doOpInitRowMoneyinput();
	
	/**
	 *绑定保存按钮 
	 */
	$("#savebtn").click(function(){
		doOpSaveDatas();
	});
	/**
	 * 关闭窗口
	 */
	$('#closeIframe').click(function(){
		var index = parent.$("#varIframeIndex").val();
		parent.$("#varIframeIndex").val("");
		parent.layer.close(index);
	});
	
})

/**
 * 保存 
 */
function doOpSaveDatas(){

	//验证数据
	var validateResult = doOpValidateDatas();
	if(!validateResult){
		return;
	}
	
	var billdate = $('#varBilldate').val();
	var creator = $('#vch_creator').html();
	var varSaveDatasStr = "billdate="+billdate+"&creator="+creator;
	
	//提交数据
	var _url = var_action_url + "addOrUpdateCapitalFlow.action?"+varSaveDatasStr;
	$.ajax({
        url: _url,
        type: "POST",
        contentType : 'application/json;charset=utf-8', //设置请求头信息
        dataType:"json",
        data: $.toJSON(getSaveDatas()),            //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
        success: function(data){
            parent.layer.msg(data.message, {icon: 6});
        },
        error: function(res){
            parent.layer.msg('更新失败', {icon: 6});
        }
    });
	
	//刷新数据
	parent.doOpQueryDatas();
	//关闭窗口
	var index = parent.$("#varIframeIndex").val();
	parent.layer.close(index);
}

/**
 *格式化提交数据 
 */
function getSaveDatas(){
	
	var detailsArray = new Array();
	$("#details").find("tr").each(function(i,obj){
        var tdArr = $(obj).children();
        var obj_id = tdArr.eq(0).find('input').val();//id
        var obj_summary = tdArr.eq(1).find('textarea').val();//摘要
        var obj_capitalFlowTypeCode= tdArr.eq(2).find('select').val();//资金类型
        var obj_unitCode = tdArr.eq(3).find('select').val();//单位
        var obj_qty = tdArr.eq(4).find('input').val();//数量
        var obj_unitprice = tdArr.eq(5).find('input').val();//单价
        var obj_totalamount = tdArr.eq(6).find('input').val();//总额
        
        detailsArray.push({id: obj_id, summary: obj_summary, capitalFlowTypeCode: obj_capitalFlowTypeCode, unitCode: obj_unitCode, qty: obj_qty, unitprice: obj_unitprice, totalamount: obj_totalamount});
    });
    
    return detailsArray;
	
}
	
/**
 *验证数据 
 */
function doOpValidateDatas(){
	
	var validateResult = true; 
	
	//日期
	var billdete = $("#varBilldate").val();
	if(isEmpty(billdete)){
		parent.layer.msg('记账日期不能为空', {icon: 5});
		validateResult = false;
	}
	//资金类型
	var amounttype = $("#details .col_amounttype .capitalFlowTypeCode").val();
	if(isEmpty(amounttype)){
		parent.layer.msg('资金类型不能为空', {icon: 5});
		validateResult = false;
	}
	//单位
	var unit = $("#details .col_unit .varUnit").val();
	if(isEmpty(unit)){
		parent.layer.msg('单位不能为空', {icon: 5});
		validateResult = false;
	}
	//数量
	var qty = $("#details .col_qty .varQty").val();
	if(isEmpty(qty)){
		parent.layer.msg('数量不能为空', {icon: 5});
		validateResult = false;
	}
	//单价
	var unitprice = $("#details .unitprice_val").val();
	if(isEmpty(unitprice)){
		parent.layer.msg('单价不能为空', {icon: 5});
		validateResult = false;
	}
	//总额
	var totalamount = $("#details .totalamount_val").val();
	if(isEmpty(totalamount)){
		parent.layer.msg('总额不能为空', {icon: 5});
		validateResult = false;
	}
	
	return  validateResult;
}


/**
 * 初始化数据
 * colNames: ['序号', '日期', '资金类型', '单价', '数量', '单位', '总额', '内容', 'id'],
 */
function doOpInitDatas(){
	var dotype = parent.$("#varDotype").val();
	if('Update'==dotype){
		var rowId = parent.$('#table_list').jqGrid('getGridParam','selrow');
		var rowData = parent.$('#table_list').jqGrid('getRowData',rowId);
		$("#vch_id").val(rowData.id);
		//id
		$("#varRowId").val(rowData.id);
		//日期
		$("#varBilldate").val(rowData.billdate);
		//摘要
		$("#details .val_summary").val(rowData.summary);
		//资金类型
		$("#details .capitalFlowTypeCode").val(rowData.capitalFlowTypeCode);
		$("#details .capitalFlowTypeCode").trigger("chosen:updated");
		//单位
		$("#details .col_unit .varUnit").val(rowData.unitName);
		$("#details .col_unit .varUnit").trigger("chosen:updated");
		//数量
		$("#details .col_qty .varQty").val(rowData.qty);
		//单价
		var unitprice = rowData.unitprice.replace('.','');
		$("#details .unitprice_val").val(unitprice);
		$("#details .unitprice_span").html(unitprice);
		//总额
		var totalamount = rowData.totalamount.replace('.','');
		$("#details .totalamount_val").val(totalamount);
		$("#details .totalamount_span").html(totalamount);
		//制单人
		$("#vch_creator").html('颜家琦');
	}
}

/**
 * 初始化金额输入框事件 
 */
function doOpInitRowMoneyinput(){
	
	$(".moneytdbackground").click(function(event){
		$(event.currentTarget).find("span").hide();
		var $thisInput = $(event.currentTarget).find("input");
		$thisInput.prop('type','text');
		$thisInput.select();
	});
		
	$(".moneyinput").blur(function(event){
		var moneyvalue = $(this).val();
		if(isEmpty(moneyvalue)){
			moneyvalue - 0; 
		}else{
			$(this).val(doOpFunHanldeDecimal(moneyvalue));
		}
		moneyvalue = doOpFunHanldeDecimal(moneyvalue);
		moneyvalue = moneyvalue.replace('.','');
		$(this).next("span:first").html(moneyvalue);
		$(this).prop('type','hidden');
		$(this).next("span:first").show();
	});
	
	$(".unitprice_val,.varQty").keyup(function(event){
		var qty = $(this).parent().parent().parent().find(".varQty").val();
		if(isEmpty(qty)){
			qty = 0;
		}
		var unitprice = $(this).parent().parent().parent().find(".unitprice_val").val();
		if(isEmpty(unitprice)){
			unitprice = 0;
		}else{
			unitprice = doOpFunHanldeDecimal(unitprice);
		}
		var totalAmount = new Number(unitprice*qty).toFixed(2); 
		$(this).parent().parent().parent().find(".totalamount_val").val(totalAmount);
		totalamount = totalAmount.replace('.','');
		$(this).parent().parent().parent().find(".totalamount_span").html(totalamount);
	});
}



function doOpFunHanldeDecimal(x){
	
    var f = parseFloat(x); 
    var f = Math.round(x*100)/100; 
    var s = f.toString(); 
    var rs = s.indexOf('.'); 
    if (rs < 0) { 
    	rs = s.length; 
        s += '.'; 
    } 
    while (s.length <= rs + 2) { 
    	s += '0'; 
    } 
    return s; 
}

/**
 * 初始化下拉 
 */
function doOpInitSelect(){
	var config = {
	    '.chosen-select': {},
	    '.chosen-select-deselect': {
	        allow_single_deselect: true
	    },
	    '.chosen-select-no-single': {
	        disable_search_threshold: 10
	    },
	    '.chosen-select-no-results': {
	        no_results_text: 'Oops, nothing found!'
	    },
	    '.chosen-select-width': {
	        width: "95%"
	    }
	}

	for (var selector in config) {
	    $(selector).chosen(config[selector]);
	}
	
}


//外部js调用
laydate({
    elem: '#varBilldate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
});


/**
 *新增行 
 */
function doOpAddRow(){
	$("#details").append(getRowHtml());
	doOpInitRowMoneyinput();
}

function getRowHtml(){
	
	var rowHtml = '<tr class="h35">'+
		'<td style="border: none;"></td><td align="left" class="col_summary"><div class="cell_val">'+
	    '<textarea class="form-control val_summary w280"  style="resize:none;height: 68px;" placeholder="填写评论..."></textarea>'+
	    '</div></td><td align="center" class="col_amounttype">'+
	    '<div class="cell_val input-group">'+
		'<select name="capitalFlow.amounttype" data-placeholder="选择资金类型..." class="chosen-select varAmounttype w100" tabindex="2">'+
		'<option value="expenditure">支出</option>'+
        '<option value="gainings">收入</option>'+
		'</select></div></td>'+
	    '<td align="center" class="col_unit"><div class="cell_val">'+
	    '<select name="capitalFlow.unit" data-placeholder="选择单位..." class="chosen-select varUnit w100" tabindex="2">'+
		'<option value="笔" hassubinfo="true">笔</option>'+
		'<option value="次" hassubinfo="true">次</option>'+
		'<option value="餐" hassubinfo="true">餐</option>'+
		'<option value="支" hassubinfo="true">支</option>'+
		'<option value="斤" hassubinfo="true">斤</option>'+
		'<option value="件" hassubinfo="true">件</option>'+
		'</select></div></td><td align="center" class="col_qty">'+
	    '<div class="cell_val">'+
	    '<input type="text" class="form-control w70 varQty" name="capitalFlow.qty">'+
	    '</div></td>'+
	    '<td class="col_unitprice moneytdbackground" data-edit="money">'+
	    '<div class="cell_val">'+
		'<input type="hidden" name="capitalFlow.unitprice" class="moneyinput unitprice_val" maxlength="11"/>'+
		'<span class="unitprice_span">00</span></div></td>'+
	    '<td class="col_totalamount moneytdbackground" data-edit="money"><div class="cell_val">'+
		'<input type="hidden" name="capitalFlow.totalamount" class="moneyinput totalamount_val" maxlength="11"/>'+
		'<span class="totalamount_span">00</span></div></td><td style="border: none;">'+
		'<a class="vardelbut glyphicon glyphicon-trash" style="margin-left: 5px;" onclick="doOpDeleteRow(this)"></a></td></tr>';
	
	return rowHtml;
}

/**
 * 删除行 
 * 必须最少保留一行
 */
function doOpDeleteRow(tmpe){
	var trs = $("#details").find('tr');
	if(trs.length<=1){
		layer.msg('最少保留一行数据', {icon: 5});
		return;
	}
	
	//判断是否有值，
	var obj = $(tmpe).parent().parent();
	var objId = obj.find("#varRowId").val();
	if (!isEmpty(objId)){
	    return swal({
	        title: "是否确定删除?",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "保存",
	        closeOnConfirm: false
	    }, function () {
	    	if(doOpDeleteRowDateForAjax(objId)){
	    		swal.close();
	    		obj.remove();
	    	}
	    });
	}
	//移除TR
	obj.remove();
}

/**
 * 后台删除行数据 
 */
function doOpDeleteRowDateForAjax(objId){
	
	//提交数据
	var _url = "/capitalflow/doOpAddOrUpdate.action?" + getFormToParam('form1');
	$.getJSON(_url,function(json){
		parent.layer.msg(json.message, {icon: 6});
	})
	
	layer.msg('删除成功', {icon: 6});
	return true;
}
