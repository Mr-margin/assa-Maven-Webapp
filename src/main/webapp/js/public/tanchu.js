//$.validator.setDefaults({
//    highlight: function(e) {
//        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
//    },
//    success: function(e) {
//        e.closest(".form-group").removeClass("has-error").addClass("has-success")
//    },
//    errorElement: "span",
//    errorPlacement: function(e, r) {
//        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
//    },
//    errorClass: "help-block m-b-none",
//    validClass: "help-block m-b-none"
//});
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
		up_password(jsondata.Login_map.pkid,$("#new_pass").val());
	}
}
//修改密码方法
function up_password(pkid,new_pass){
	var data = ajax_async_t("upPassword.do",{pkid:pkid,password:new_pass},"text");
	if (data == "1") {
		toastr["success"]("success", "密码已修改");
		shuaxin_xiugaimima();
		$(".guanbi").click();//关闭弹窗
	}else{
		toastr["warning"]("warning", "修改失败，检查数据后重试");
		shuaxin_xiugaimima();
	};
}

//刷新修改密码弹窗内容
function shuaxin_xiugaimima(){//刷新
	$("#old_pass").val("");
	$("#new_pass").val("");
	$("#new_pass_2").val("");
}

$(document).ready(function(){ 

	$(".top_nav").mousedown(function(e){ 
		$(this).css("cursor","move");//改变鼠标指针的形状 
		var offset = $(this).offset();//DIV在页面的位置 
		var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离 
		var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离 
		$(document).bind("mousemove",function(ev){ //绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件 
		
			$(".popup").stop();//加上这个之后 
			
			var _x = ev.pageX - x;//获得X轴方向移动的值 
			var _y = ev.pageY - y;//获得Y轴方向移动的值 
		
			$(".popup").animate({left:_x+"px",top:_y+"px"},10); 
		}); 

	}); 

	$(document).mouseup(function() { 
		$(".popup").css("cursor","default"); 
		$(this).unbind("mousemove"); 
	});
	
	//点击关闭按钮
	$("a.guanbi").click(function(){
		$("#gray").hide();
		$("#popup").hide();//查找ID为popup的DIV hide()隐藏
		$("#gray_2").hide();
		$("#popup_2").hide();
	});
}) 
