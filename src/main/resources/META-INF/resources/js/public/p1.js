//验证是否登录，跳转页面或者获取session

//定义字符串比对方法，判断以什么子串结尾
String.prototype.endWith = function(endStr) {
	var d = this.length - endStr.length;
	return (d >= 0 && this.lastIndexOf(endStr) == d)
}

var jsondata;//用户session中的全部信息
var company_tree;//行政区划数据

$(function(){
//	var url = window.location.href;
//	if(url.endWith("assa/")){
//		window.location.href = "index.html";
//	}else if(url.endWith("assa")){
//		window.location.href = "index.html";
//	}
	
	
	
	
	
	var user = JSON.parse(window.sessionStorage.getItem("user"));
	if(user){
		jsondata = eval("("+user+")");
	}else{
		var data = ajax_async_t("getLogin_massage.do",{},"text");
		if (data == "weidenglu") {//如果未登录
			window.sessionStorage.clear();
			jsondata = null;
		}else{//如果登录成功，显示用户信息
			jsondata = eval("("+data+")");
			window.sessionStorage.setItem("user",JSON.stringify(data));
		}
	}
	if(jsondata){
		$("#username").html(jsondata.company.COM_NAME);
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
		toastr["warning"]("warning", "退出失败，检查数据后重试");
		shuaxin_xiugaimima();
	}
}