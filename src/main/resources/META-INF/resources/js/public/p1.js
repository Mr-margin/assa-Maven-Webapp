//验证是否登录，跳转页面或者获取session

//定义字符串比对方法，判断以什么子串结尾
String.prototype.endWith = function(endStr) {
	var d = this.length - endStr.length;
	return (d >= 0 && this.lastIndexOf(endStr) == d)
}

var jsondata;//用户session中的全部信息
var company_tree;//行政区划数据

$(function(){
	var url = window.location.href;
	if(url.endWith("assa/")){
		window.location.href = "index.html";
	}else if(url.endWith("assa")){
		window.location.href = "index.html";
	}
	
	var user = JSON.parse(window.sessionStorage.getItem("user"));
	if(user){//已登录
		jsondata = eval("("+user+")");
		if(url.endWith("index.html")){
			window.location.href = "home.html";
		}else{
//			$("#yonghuxiangguan").html('<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i><div style="display:inline;" id="yonghumingzi">'+jsondata.company.COM_NAME+'</div><div id="yh_xinxi" style="display:none;"></div><span class="caret"></span></a><ul role="menu" class="dropdown-menu"><li><a href="orig_data/manual.pdf">使用手册</a></li><li id=""><a onclick="xiugaimima()">修改密码</a></li><li id=""><a onclick="fanhuipingtai()">返回平台</a></li><li id=""><a onclick="tuichudenglu()">退出登录</a></li></ul>');
//			$("#signout").html('<ul><li><a><img src="N/images/lgan6.png" title="user-name" /><span id="w_username">'+jsondata.company.COM_NAME+'</span></a></li><li>|</li><li><a onclick="fanhuipingtai()"><span>返回平台</span></a></li><li>|</li><li><a onclick="tuichudenglu()"><span>退出登录</span></a></li></ul>');
			
			$("#username").html(jsondata.company.COM_NAME);
			$("#signout").click(function(){
				tuichudenglu();
			}); 
		}
	}else{//缓存中没有登录信息
		var data = ajax_async_t("getLogin_massage.do",{},"text");
		if (data == "weidenglu") {//如果未登录
			if(!url.endWith("index.html")){
				window.location.href = "index.html";
			}
		}else{//如果登录成功，显示用户信息
			jsondata = eval("("+data+")");
			window.sessionStorage.setItem("user",JSON.stringify(data));
			if(url.endWith("index.html")){
				window.location.href = "home.html";
			}else{
//				$("#yonghuxiangguan").html('<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i><div style="display:inline;" id="yonghumingzi">'+jsondata.company.com_name+'</div><div id="yh_xinxi" style="display:none;"></div><span class="caret"></span></a><ul role="menu" class="dropdown-menu"><li><a href="orig_data/manual.pdf">使用手册</a></li><li id=""><a onclick="xiugaimima()">修改密码</a></li><li id=""><a onclick="fanhuipingtai()">返回平台</a></li><li id=""><a onclick="tuichudenglu()">退出登录</a></li></ul>');
//				$("#w_title_user").html('<ul><li><a><img src="N/images/lgan6.png" title="user-name" /><span id="w_username">'+jsondata.company.com_name+'</span></a></li><li>&nbsp;&nbsp;|</li><li><a onclick="fanhuipingtai()"><span>返回平台</span></a></li><li>&nbsp;&nbsp;|</li><li><a onclick="tuichudenglu()"><span>退出登录</span></a></li></ul>');
				
				$("#username").html(jsondata.company.COM_NAME);
				$("#signout").click(function(){
					tuichudenglu();
				}); 
			}
		}
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