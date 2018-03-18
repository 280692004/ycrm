/**
 * 验证是否为空
 * return  true【空】，false【非空】
 */
function isEmpty(varObj){
	if(undefined==varObj||'undefined'==varObj||null==varObj||'null'==varObj||''==varObj||'_empty'==varObj){
		return true;
	}
	return false;
}

//JavaScript判断map 是否为空.
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
} 



/**
 * 获取url中的参数
 * @param {Object} name
 */
function getUrlParam(name){
  	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  	var r = encodeURI(window.location.search).substr(1).match(reg);
  	if(r!=null)return unescape(r[2]); return null;
}

/**
 * 处理360中文编码
 */
function doOpHandleEncode(varObj){
	var varResult = isEmpty(varObj);
	if(!varResult){
		return encodeURI(varObj);
	}
	return "";
}

//表单转换成URL参数
function getFormToParam(formid) {
	var formparm = $("#"+formid).serialize();
	//处理中文乱码
	formparm = decodeURIComponent(formparm,true);
	$($.find(".checkboxChecked")).each(function(){
		formparm += "&" + $(this).attr("type") + "=" + $(this).attr("typevalue");
	});
	return formparm;
}

//表单转换成URL参数
function getFormToJsonObj(formid) {
	var formjson = $("#"+formid).serializeJSON();
	$($.find(".checkboxChecked")).each(function(){
		formjson[$(this).attr("type")] = $(this).attr("typevalue");
	});
	return formjson;
}

//表单转换成URL参数
function getFormToJsonStr(formid) {
	var formjson = getFormToJsonObj(formid);
	eval("var jsonStr = '"+JSON.stringify(formjson)+"';");
	return jsonStr;
}

/**
 * 初始化CheckBox
 */
function funSetCheckBox(){
  $("body").delegate(".checkbox","click",function(){
        if ($(this).is('.checkboxChecked')) {
            $(this).removeClass("checkboxChecked").attr("checked",null).empty();
        } else {
            $(this).addClass("checkboxChecked").attr("checked","checked").html('<span class="glyphicon glyphicon-ok font10 iconGlyphicon" aria-hidden="true"></span>');
            
        }
  });	
}