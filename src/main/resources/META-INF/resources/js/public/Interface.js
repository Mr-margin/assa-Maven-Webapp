var title_name = "内蒙古自治区精准扶贫大数据平台";//项目名称
var gongsiname = "北京山海础石信息技术有限公司";//技术支持
var banquan = "内蒙古自治区扶贫开发(革命老区建设)办公室";//版权所有
var beianhao = "蒙ICP备13001436号";//备案号
var favicon = "内蒙古-favicon.ico";//favicont图标
var logo_pic = "logo14.png";//logo+项目名称
var logo_1 = "内蒙古-logo-1.png";
var erweima = "内蒙古-二维码.png";//二维码

$(function () {
//	$("#show-content").css("min-height",document.body.scrollHeight-90);
	//页面名称初始化
	chushihua();
	//进入页面首先写入头部信息
	title_html();
	
//	$('#in').click(function () {//返回首页
//		window.location.href="login.html?"+ver;
//    });
	
//	$('#out').click(function () {//退出
//		window.location.href="/assa/H/1.html?"+ver;
//    });
	
	$('#H1').click(function () {//录入贫困户信息
		window.location.href="H1-1.html?"+ver;
    });
	
	$('#H2').click(function () {//浏览贫困户信息
		window.location.href="H1-2.html?"+ver;
    });
//	$('#H2_1').click(function () {//浏览贫困户信息
//		window.location.href="H1-3.html?"+ver;
//    });
//	$('#H3-1').click(function () {//综合查询
//		window.location.href="H3-1.html?"+ver;
//    });
//	
//	$('#H3-2').click(function () {//统计汇总
//		window.location.href="H3.html?"+ver;
//    });
//	
//	$('#H6').click(function () {//帮扶日记
//		window.location.href="H6.html?"+ver;
//    });
//	
//	$('#H4').click(function () {//识别与退出
//		window.location.href="H4.html?"+ver;
//    });
	
	$('#H5-1').click(function () {//帮扶干部维护
		window.location.href="H5-9.html?"+ver;
    });
//	$('#H5-3').click(function () {//帮扶单位维护
//		window.location.href="H5-3.html?"+ver;
//	});
//	$('#H5-4').click(function () {//嘎查村管理
//		//window.location.href="H6-1.html?"+ver;
//		window.location.href="H5-4.html?"+ver;
//    });
//	$('#H7').click(function () {//用户管理
//		window.location.href="H7.html?"+ver;
//    });
//	$('#H5-2').click(function () {//维护开关
//		window.location.href="H5-2.html?"+ver;
//    });
//	
//	$('#H7-1').click(function () {//安置点维护
//		window.location.href="H2-1.html?"+ver;
//    });
//	
//	$('#H7-2').click(function () {//工程进度统计
//		window.location.href="H2-2.html?"+ver;
//    });
//	$('#H5-5').click(function () {//工程进度统计
//		window.location.href="H5-5.html?"+ver;
//	});
	$('#H5-6').click(function () {//数据统计
		window.location.href="H5-6.html?"+ver;
	});
//	$('#H5-7').click(function () {//数据统计
//		window.location.href="H5-7.html?"+ver;
//	});
	
});

//页面名称初始化
function chushihua(){
	//每个页面的头名称
	$(document).attr("title",title_name);
	//登录页面名称
	$("#login_title").html("欢迎使用"+title_name);
	//登录界面logo
	$("#top_nav_i").css("background","url(magimg/"+logo_1+")");
	
	//写入底部信息
	$("#weibu_title").html("<div class=\"pull-right\">技术支持："+gongsiname+"</div><div><strong>Copyright</strong>&nbsp;&nbsp;&nbsp;<i class=\"fa fa-copyright\"></i>&nbsp;&nbsp;&nbsp;2016&nbsp;&nbsp;&nbsp;"+banquan+"&nbsp;&nbsp;&nbsp;版权所有&nbsp;&nbsp;&nbsp;"+beianhao+"</div>");

	//获取图标ico图片
	var tplink= document.createElement("link");
	tplink.rel = "shortcut icon";
	tplink.href = "magimg/"+favicon;
    document.getElementsByTagName("HEAD")[0].appendChild(tplink);
    
	//获取页面头部logo
    $("#headerimg").html(" <span><img alt=\"image\" class=\"img\" src=\"magimg/"+logo_pic+"\" /></span>");
    
	//获取二维码图片
    $("#ewmimg").html("<img id=\"huzhu_pic_show\" alt=\"image\" class=\"img-responsive\" style=\"width:180px;height:180px;\" src=\"magimg/"+erweima+"\">");
    
	//赋值于下拉按钮
	$("#chauxnshiousuo").html('<i class="fa fa-chevron-down"></i><strong onclick="title_tle_zhankai()">展开</strong>');
}

/**
 * 头部信息
 * @author chendong
 */
function title_html(){
	var html=
		'<ul class="nav navbar-nav navbar-right">'+
//			'<li class="dropdown" id="H">'+
//		        '<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="in"> 扶贫概况</a>'+
//		    '</li>'+
//		    '<li class="dropdown" id="H_li" style="display:none;">'+
//		    	'<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> 扶贫手册 <span class="caret"></span></a>'+
//		    	'<ul role="menu" class="dropdown-menu">'+
//		    		'<li id="H1_li" style="display:none;"><a id="H1">扶贫手册录入</a></li>'+
//		    		'<li id="H2_li" style="display:none;"><a id="H2">帮扶信息查询</a></li>'+
//		    		'<li id="H2_1_li" style="display:none;" ><a id="H2_1">工作台账</a></li>'+
//		    	'</ul>'+
//		    '</li>'+
		    
		    
		    '<li class="dropdown" id="H1_li">'+
	        	'<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="H1"> 贫困户录入 </a>'+
	        '</li>'+
	        '<li class="dropdown" id="H2_li">'+
        		'<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="H2"> 贫困户查询 </a>'+
        	'</li>'+
        	'<li class="dropdown" id="H5-1_li">'+
    			'<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="H5-1"> 帮扶干部 </a>'+
    		'</li>'+
    		'<li class="dropdown" id="H9_li">'+
				'<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="H5-6"> 数据统计 </a>'+
			'</li>'+
		    
//		    '<li class="dropdown" id="H7_li" style="display:none;">'+
//		    	'<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> 易地扶贫搬迁 <span class="caret"></span></a>'+
//		    	'<ul role="menu" class="dropdown-menu">'+
//		    		'<li id="H7-1_li" style="display:none;"><a id="H7-1">安置点维护</a></li>'+
//		    		'<li id="H7-2_li" style="display:none;"><a id="H7-2">工程进度统计</a></li>'+
//		    	'</ul>'+
//		    '</li>'+
//		    '<li class="dropdown" id="H3_li" style="display:none">'+
//		        '<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="H3-2"> 挂图作战 </a>'+
//		    '</li>'+
//		    '<li class="dropdown" id="H4_li" style="display:none;">'+
//		        '<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown" id="H4"> 识别与退出 </a>'+
//		    '</li>'+
//		    '<li class="dropdown" id="H5_li" style="display:none;">'+
//		       ' <a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> 统计查询 <span class="caret"></span></a>'+
//		       ' <ul role="menu" class="dropdown-menu">'+
////		            '<li id="H5-1_li" style="display:none;"><a id="H5-1">帮扶干部维护</a></li>'+
////		            '<li id="H5-4_li" style="display:none;"><a id="H5-2">帮扶单位维护</a></li>'+
////		            '<li id="H5-2_li" style="display:none;"><a id="H5-3">账号查询</a></li>'+
////		            '<li id="H5-3_li" style="display:none;"><a id="H5-4">维护控制</a></li>'+
////		            '<li id="H8_li" style="display:none;"><a id="H5-5">系统查看</a></li>'+
//		            '<li id="H9_li" style="display:none;"><a id="H5-6">数据统计</a></li>'+
//		            '<li id="H10_li" style="display:none;"><a id="H5-7">查询录入情况</a></li>'+
//		        '</ul>'+
//		    '</li>'+
		    '<li class="dropdown" id="yonghuxiangguan"></li>'+
		'</ul>';
	$("#navbar").html(html);
}

//点击进入修改密码弹窗
function xiugaimima(){
	$("#popup_2").html('<div class="top_nav" ><div align="center"><i></i><span>修改密码</span><a class="guanbi" onclick='+"xg_guanbi();"+'></a></div></div>'
			+'<div class="main">'
			+'<div class="tc_login"><div class="center"><div align="center"><div align="center"><br/>'
			+'<h4 style="float:left;margin-left: 133px;">请输入原密码：</h4>'
			+'</div><br/><div><form  id="changepassword_form" onsubmit="return false" >'
			+'<input type="text" id="yhid" style="display:none;"  class="input_yh">'
			+'<div align="center">&nbsp;&nbsp;&nbsp;</div>'
			+'<div class="form-group" style="text-align:left;">'
			+'<input type="password" name="old_pass" id="old_pass" style="margin-left: 133px;" required="required" cp="true"  placeholder="原密码" autocomplete="off" class="input_mm">'
			+'<div align="center">&nbsp;&nbsp;&nbsp;</div>'
			+'<input type="password" name="new_pass" id="new_pass" style="margin-left: 133px;" required="required"  placeholder="新密码" autocomplete="off" class="input_mm">'
			+'<div align="center">&nbsp;&nbsp;&nbsp;</div>'
			+'<input type="password" name="new_pass_2" id="new_pass_2" style="margin-left: 133px;" required equalTo="#new_pass" placeholder="请再次输入新密码" autocomplete="off" class="input_mm">'
			+'</div></form></div></div>'
			+'<div align="center">'
			+'<input type="submit" class="updatePass" style="color: #fff;padding: 0 50px 0 50px;height: 50px;font-size: 1.2em;" onclick='+"xiugaimima_anniu();"+' value="修&nbsp;改&nbsp;密&nbsp;码">'
			+'</div><hr align="center" /></div></div></div>');
	
	//validate实时验证
	$("#changepassword_form").validate({
		onfocusout: function(element){
			$(element).valid();
		}
	});
	$("#gray_2").show();
	$("#popup_2").show();//查找ID为popup的DIV show()显示#gray
	tc_center();
}

//查询框-点击展开
function title_tle_zhankai(){
	$("#chauxnshiousuo").html('<i class="fa fa-chevron-down"></i><strong onclick="title_tle_shousuo()">收起</strong>');
}
//查询框-点击收起
function title_tle_shousuo(){
	$("#chauxnshiousuo").html('<i class="fa fa-chevron-up"></i><strong  onclick="title_tle_zhankai()">展开</strong>');
}