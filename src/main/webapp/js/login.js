$('#mpanel').pointsVerify({
	defaultNum : 4,	//默认的文字数量
	checkNum : 2,	//校对的文字数量
	vSpace : 1,	//间隔
	imgName : ['verify_1.jpg', 'verify_2.jpg'],
	imgSize : {
		width: '300px',
		height: '150px',
	},
	barSize : {
		width : '300px',
		height : '25px',
	},
	ready : function() {
	},
	success : function() {
		layer.msg('<a>验证成功</a>', {icon: 6});
	},
	error : function() {
		layer.msg('<a>验证失败,请重新验证</a>', {icon: 5});
	}
});

/**
 * 监听回车事件登录
 * @param {Object} event
 */
function disableEnterToLogin(event){
	if (event.keyCode == "13") {
        doLogin();
    }
}


function funLogIn(){
	
    var uname = $('.uname').val();
    var pwd = $('.pword').val();
    
    if(doOpValidateLoginParam(uname,pwd)){
    	return;
    };
    
  	var varurl="/checklogin?name=" + encodeURI(uname,"utf-8") + "&password=" + encodeURI(pwd,"utf-8");
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
        			layer.msg('<a>你的账号已失效！</a>', {icon: 5});
        			$('.verify-refresh').click();
        			return;
        		}
                if ("200" == result.code) {
                	window.location.href = "/toUserMain.action";
                	return false;
                } 
			 }else{ 
			 	layer.msg('<a>请输入正确的用户名和密码错误！</a>', {icon: 5});
			 	$('.verify-refresh').click();
			 	return;
            }
        }
    });
}

/**
 * 登录参数验证
 */
function doOpValidateLoginParam(name,pwd){
	
	if(isEmpty(name)){
		layer.msg('<a>用户名不能为空</a>', {icon: 5});
		return true;
	}
	if(isEmpty(pwd)){
		layer.msg('<a>登录密码不能为空</a>', {icon: 5});
		return true;
	}
	
	var verifycode = $('.verify-msg').html();

	if('验证成功' != verifycode){
		layer.msg('<a>请完成验证码操作</a>', {icon: 5});
		return true;
	}
}
