//验证是否登录，跳转页面或者获取session

//定义字符串比对方法，判断以什么子串结尾
String.prototype.endWith = function(endStr) {
	var d = this.length - endStr.length;
	return (d >= 0 && this.lastIndexOf(endStr) == d)
}

var jsondata = get_sys_user();//用户session中的全部信息
$(function(){
	if(jsondata){
		$("#username").html(jsondata.Login_map.COM_NAME);
		$("#signout").click(function(){
			tuichudenglu();
		});
	}
});

//退出登录
function tuichudenglu(){
	var data = ajax_async_t(GISTONE.Loader.basePath+"login_out.do", {}, "text")
	if (data == "1") {
		window.sessionStorage.clear();
		jsondata = null;
		window.location.href = GISTONE.Loader.basePath+"index.html";
	} else {
		toastr["warning"]("", "退出失败，检查数据后重试");
		shuaxin_xiugaimima();
	}
}