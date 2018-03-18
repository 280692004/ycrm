
//--- 填充Form
function fillForm2($form, json,fmobjprefix) {
  var jsonObj = json;
  var varfmobjprefix = fmobjprefix || "";
  if (typeof json === 'string') {
    jsonObj = $.parseJSON(json);
  }
  
  fillFormProperty(jsonObj,varfmobjprefix);
}

function fillFormProperty(jsonObj,fmobjprefix){
	 for (var key in jsonObj){	 	
  		if(null==jsonObj[key]){
  		}else{
  			
  			var objtype = jsonObjType(jsonObj[key])
  			var varfieldname=fmobjprefix+key;

  			if(objtype == "class"){
  				fillFormProperty(jsonObj[key],varfieldname+".");
  			}
  			  			
  			if(objtype == "string"){
  				fillFormStringProperty(jsonToHtml(jsonObj[key]),varfieldname);
  			}
  			if(objtype == "number"){
  				fillFormNumberProperty(jsonObj[key],varfieldname);
  			}
  			if(objtype == "boolean"){
  				fillFormBooleanProperty(jsonObj[key],varfieldname);
  			}  			
  			//明细数据
  			if(objtype =="array"){
  				
  			}
  		}
  		
	 }
}
/**
 * 1、input
 * 2、 div
 * 假定Name=propertyfield
 * @param  objval
 * @param  propertyfield
 */
function fillFormStringProperty(objval,propertyfield){
	$("input[name='"+propertyfield+"']").each(function(){
         if ($(this).hasClass('input-date')) {
        	 $(this).val($.UnixToDate(objval/1000,false,08));        	
         }else{
        	$(this).val(objval); 	
         }
	})
	
	$("select[name='"+propertyfield+"']").each(function(){
         if ($(this).hasClass('input-date')) {
        	 $(this).val($.UnixToDate(objval/1000,false,08));        	
         }else{
        	$(this).val(objval); 	
         }
	});
	$("textarea[name='"+propertyfield+"']").each(function(){
       	$(this).val(objval); 	
	});
	$("input[name='"+propertyfield+"']").each(function(){
        if ($(this).hasClass('easyui-datebox')) {
        	$(this).val($.UnixToDate(objval/1000,false,08)); 
        }
	});
	$("span[name='"+propertyfield+"']").each(function(){
		$(this).html(objval);
	});
	
	var divfield=funPropertyFieldDeal(propertyfield);	
	$("."+divfield).html(objval)	
}
/**
 * 1、input
 * 2、 div
 * @param objval
 * @param  propertyfield
 */
function fillFormNumberProperty(objval,propertyfield){
	$("input[name='"+propertyfield+"']").each(function(){
         if ($(this).hasClass('input-date')) {
        	 $(this).val($.UnixToDate(objval/1000,false,08));        	
         }else{
        	$(this).val(objval); 	
         }		
	});
	
	$("select[name='"+propertyfield+"']").each(function(){
         if ($(this).hasClass('input-date')) {
        	 $(this).val($.UnixToDate(objval/1000,false,08));        	
         }else{
        	$(this).val(objval); 	
         }
	});
	
	$("span[name='"+propertyfield+"']").each(function(){
		$(this).html(objval);
	});

	
	var divfield=funPropertyFieldDeal(propertyfield);	
	$("."+divfield).html(objval)
}

function funPropertyFieldDeal(propertyfield){
	propertyfield = propertyfield||"";
	var vardivclassname=propertyfield.toLowerCase();
	vardivclassname=vardivclassname.replace(/\./g,'_');
	return vardivclassname;
}
function fillFormDateProperty(objval,propertyfield){
	
}

function fillFormBooleanProperty(objval,propertyfield){
	$("select[name='"+propertyfield+"']").each(function(){
				if(true==objval){
					 $(this).val("1");	
				}else{
					 $(this).val("0");
				}
         	
	});	
	$("input[type='checkbox'][name='"+propertyfield+"']").each(function(){
		funChangeCheckBoxValue($(this),objval);		
	});
	$("span[name='"+propertyfield+"']").each(function(){
		if(true==objval){
			$(this).html("是");
		}else{
			$(this).html("否");
		}
		
	});
}

function funChangeCheckBoxValue(obj,objval){
		if(true==objval){
			$(obj).attr("checked", true);
			return;
		}
		
		$(obj).attr("checked", false);
		
}


function jsonObjType(obj) {
  if (typeof obj === "object") {
    var teststr = JSON.stringify(obj);
    if (teststr[0] == '{' && teststr[teststr.length - 1] == '}') return "class";
    if (teststr[0] == '[' && teststr[teststr.length - 1] == ']') return "array";
  }
  return typeof obj;
}

function fillFormCheckBoxProperty(jsonObj,fmobjprefix){	
}

//刷新当前页面
function refreshPage() {
	window.location.href=window.location.href;
}

