$.validator.addMethod("isZipCode", function(value, element) {   
    var tel = /^\w+$/;
    return this.optional(element) || (tel.test(value));
}, "只能填写英文字母、数字和下划线");
$.validator.addMethod("qingxuanze", function(value, element) {
	var valtext = value != '请选择';
	return this.optional(element) || (valtext);
}, "必选选择");
$.validator.addMethod("chinese", function(value, element) {
	var chinese = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (chinese.test(value));
}, "只能输入中文");
$.validator.addMethod("cp", function(value, element) {//修改密码时，验证输入的旧密码是否正确
	var cf="";
	var pkid = jsondata.Login_map.pkid;
	var data=ajax_async_t("o_password.do",{pkid:pkid,val:value},"text");
	if (data == "0") {
		cf=data;
	}else{
		return false
	}
	return this.optional(element) || cf!="0";
}, "原密码错误");

$(function(){
	
	b();

    $('#gotop').click(function(){
        $(document).scrollTop(0);
    });

    $('#code').hover(function(){
        $(this).attr('id','code_hover');
        $('#code_img').show();
        $('#code_img').addClass('a-fadeinL');
    },function(){
        $(this).attr('id','code');
        $('#code_img').hide();
    });

	
	$(document).keydown(function(event){ 
		if(event.keyCode==13){ 
			$("#loginFormButton").click(); 
		} 
	}); 
	$('#captchaImage').click(function(){
		$('#captchaImage').attr("src", "img.do?timestamp=" + (new Date()).valueOf());
	}); 
	$("#loginFormButton").click(function () {
		var data = ajax_async_t("loginin.do",{add_account:$("#name").val(),add_password:$("#pass").val()},"json");
		var zhi= $("#yzm").val();
		var data1=ajax_async_t("yzm.do",{zhi:zhi},"json");  
		if (data == "1" && data1=="1") {
			window.location.href="home.html"
		}else if (data == "0"){
			toastr["error"]("", "失败，账号或密码错误");
		}else if (data == "2"){
			toastr["error"]("", "失败，用户名不存在");
		}else if (data1 == "0"){
			toastr["error"]("", "失败，验证码错误");
		}else{
			toastr["warning"]("warning", "网络异常");
		}
		return false;
		
	});
	//验证是否登录，需要跳转
	login_tiaozhuan();
});

$(window).scroll(function(e){
    b();
});


//var jsondata;//用户session中的全部信息

function login_tiaozhuan(){
	var data = ajax_async_t("getLogin_massage.do",{},"text");
	if (data == "weidenglu") {//如果未登录
		window.sessionStorage.clear();
		jsondata = null;
	}else{//如果登录成功，显示用户信息
		jsondata = eval("("+data+")");
		window.sessionStorage.setItem("user",JSON.stringify(data));
		window.location.href = "home.html";
	}
}

function b(){
    h = $(window).height();
    t = $(document).scrollTop();
    if(t > h){
        $('#gotop').show();
    }else{
        $('#gotop').hide();
    }
}