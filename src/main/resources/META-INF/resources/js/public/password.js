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
/*	console.log(jsondata);*/
	var username = jsondata.Login_map.COL_ACCOUNT;
	var data=ajax_async_t(GISTONE.Loader.basePath+"o_password.do",{username:username,val:value},"text");
	if (data == "1") {
		cf=data;
	}else{
		return false
	}
	return this.optional(element) || cf=="1";
}, "原密码错误");
//窗口水平居中
$(window).resize(function(){
	tc_center();
});

function tc_center(){
	var _top=($(window).height()-$(".popup").height())/2;
	var _left=($(window).width()-$(".popup").width())/2;
	
	$(".popup").css({top:_top,left:_left});
}

//点击修改密码的关闭按钮
function xg_guanbi(){
	$("#gray").hide();
	$("#popup").hide();//查找ID为popup的DIV hide()隐藏
	$("#gray_2").hide();
	$("#popup_2").hide();
}

//点击确认修改
function xiugaimima_anniu(){
	var validator = $("#changepassword_form").validate();
	if(validator.form()){
		up_password(jsondata.Login_map.COL_ACCOUNT,$("#new_pass").val());
	}
}
//修改密码方法
function up_password(pkid,new_pass){
	var data = ajax_async_t(GISTONE.Loader.basePath+"upPassword.do",{pkid:pkid,password:new_pass},"text");
	if (data == "1") {
		shuaxin_xiugaimima();
		$('#p').modal('hide');
		swal({
            title: "密码修改成功",
            text: "您已将密码修改",
            type: "success"
        });
	}else{
		toastr["warning"]("", "修改失败，检查数据后重试");
		shuaxin_xiugaimima();
	};
}

//刷新修改密码弹窗内容
function shuaxin_xiugaimima(){//刷新
	$("#old_pass").val("");
	$("#new_pass").val("");
	$("#new_pass_2").val("");
}

//点击进入修改密码弹窗
function xiugaimima(){
	$('#p').modal();
	
	
	//validate实时验证
	$("#changepassword_form").validate({
		onfocusout: function(element){
			$(element).valid();
		}
	});
	
}

//点击进入页面
function bfgbweihu(){
	window.location.href="../assa/H1-3.html";
	
	
}

//点击进入页面
function pkhweihu(){
	window.location.href="../assa/H1-4.html";
	
	
}

//查询框-点击展开
function title_tle_zhankai(){
	$("#chauxnshiousuo").html('<i class="fa fa-chevron-down"></i><strong onclick="title_tle_shousuo()">收起</strong>');
}
//查询框-点击收起
function title_tle_shousuo(){
	$("#chauxnshiousuo").html('<i class="fa fa-chevron-up"></i><strong  onclick="title_tle_zhankai()">展开</strong>');
}





