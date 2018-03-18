var iCount = null;

function initDwr(){
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setNotifyServerOnPageUnload(true);
	onPageLoad();
}

function onPageLoad(){  
    var userId = $("#var_userID").val();  
	MessagePush.onPageLoad(userId);  
} 

/**
 * 验证账号是否过期了
 */
function startSetInterval() {
	iCount = setInterval(startRequest(), 15*60*1000+30*1000);
}

function startRequest() {
	var aclUser = findLoginAclUser();
	if(null == aclUser) {
		clearInterval(iCount);
		parent.$('#loginMondal').modal('show'); 
	}
}
 
function doReset() {
	$("#var_userPwd").val("");
};

//获取登陆账号
function findLoginAclUser(){
	
	var _url="/getCurrentLoginedUser.action?"+Math.random();
	var aclUser = null;
	
	$.ajax({
        type: "get",
        url: _url,
        dataType: "json",
        async: false,
        contentType: "application/json;charset=UTF-8",
        complete:function (XMLHttpRequest, textStatus) {
        	result = $.parseJSON(XMLHttpRequest.responseText);
            if (textStatus == "success"){
            	aclUser=result.datas;
            }
        }
    });
    
	return aclUser;
}

/**
 * 重新登录
 */
function doLogin() {
	var name = $("#var_userID").val();
	var password=$('#var_userPwd').val();
    if("" == password) {
    	layer.msg('请输入用户密码', {icon: 5});
    	return;
    }
    
	var varurl="/checklogin?name=" + encodeURI(name,"utf-8") + "&password=" + encodeURI(password,"utf-8");
	$.ajax({
        type: "get",
        url: varurl,
        dataType: "json",
        async: false,
        contentType: "application/json;charset=UTF-8",
        complete:function (XMLHttpRequest, textStatus) {
            if (textStatus == "success"){
                result = $.parseJSON(XMLHttpRequest.responseText);
                if(result.datas.status!='Y'){
                	layer.msg('您的账号已失效', {icon: 5});
        			return;
        		}
                if ("200" == result.code) {
                	startSetInterval();
                	//更新登录用户头像
					doInitUserImageAndName(result.datas);
					$("#loginMondal").modal('hide');	
					doReset();
					//重新初始化initDwr
					initDwr();
					return;
                } 
			 }else{ 
			 	layer.msg('您输入的密码错误', {icon: 5});
            	return;
            }
        }
    });
    
} 

/**
 * 初始化用户信息
 */
function doOpInitUserInfo(){
	
	var username = getUrlParam('u');
	$("#var_userID").val(username);
	$("#var_lockscreen_username").html(username);
	
	var aclUser = findLoginAclUser();
	if(isEmpty(aclUser)){
		return;
	}
	//更新登录用户头像
	doInitUserImageAndName(aclUser);
	
}

/**
 * 初始化用户头像及名称
 */
function doInitUserImageAndName(aclUser){
	//更新登录用户头像
	if(!isEmpty(aclUser.avatarImage)){
		$('#var_userimage').attr('src',aclUser.avatarImage);
	}else{
		$('#var_userimage').attr('src','images/defaultuser.gif');
	}
	$("#var_userName").html(aclUser.personName);
}


//推送信息    
function showMessage(messageresult){
	var obj = JSON.parse(messageresult);
	if (0 == obj.sendType){
	    swal({
	        title: obj.message.replace('time',obj.sendTime),
	        text: "若非您自己操作,为确保安全隐患，请及时修改密码！", 
	        closeOnConfirm: false
	    },
	    function () {
	    	//跳转到登录界面
	    	window.location.href = "/login.html";
	    });
	}
} 


/**
 * 跳转到修改界面
 */
function funUpdatePassWord(){
	$("#var_update_username").val($("#var_userID").val());
	parent.$('#updatePwdMondal').modal('show'); 
}

/**
 * 修改密码
 */
function doOpUpdatePwd(){
	var userName = $("#var_userID").val();
	var oldpassword = $('#var_old_userPwd').val();
	var newpassword = $('#var_new_userPwd').val();
	var confirmpassword = $('#var_confirm_userPwd').val();
	if(isEmpty(oldpassword)){
		layer.msg('请输入当前密码', {icon: 5});
    	return;
	}
	if(isEmpty(newpassword)){
		layer.msg('请输入新密码', {icon: 5});
    	return;
	}
	if(isEmpty(confirmpassword)){
		layer.msg('请输入确认密码', {icon: 5});
    	return;
	}
	if(newpassword != confirmpassword){
		layer.msg('两次密码不一致', {icon: 5});
    	return;
	}
    
	var varurl="/base/basemanager/sysmag/acluser/updatePassWord.action?userName=" + encodeURI(userName,"utf-8") + "&oldPwd=" + encodeURI(oldpassword,"utf-8")+ "&newPwd=" + encodeURI(newpassword,"utf-8");
	$.ajax({
        type: "get",
        url: varurl,
        dataType: "json",
        async: false,
        contentType: "application/json;charset=UTF-8",
        complete:function (XMLHttpRequest, textStatus) {
            if (textStatus == "success"){
                result = $.parseJSON(XMLHttpRequest.responseText);
                if(result.datas.status!='Y'){
                	layer.msg('您的账号已失效', {icon: 5});
        			return;
        		}
                if ("200" == result.code) {
					$("#updatePwdMondal").modal('hide');	
                	startSetInterval();
					return;
                } 
			 }else{ 
			 	layer.msg('您输入的密码错误', {icon: 5});
            	return;
            }
        }
    });
}
