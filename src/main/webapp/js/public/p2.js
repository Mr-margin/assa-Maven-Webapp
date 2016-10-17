//页面设置，图标加载，头尾的标题等

$(function () {
	//页面名称初始化
	chushihua();
});

//页面名称初始化
function chushihua(){
	
	var title_name = "内蒙古自治区精准扶贫大数据平台";//项目名称
	var gongsiname = "北京山海础石信息技术有限公司";//技术支持
	var banquan = "内蒙古自治区扶贫开发(革命老区建设)办公室";//版权所有
	var beianhao = "蒙ICP备13001436号";//备案号
	var favicon = "内蒙古-favicon.ico";//favicont图标
	var logo_pic = "logo14.png";//logo+项目名称
	var erweima = "内蒙古-二维码.png";//二维码
	
	//每个页面的头名称
	$(document).attr("title",title_name);
	
	//写入底部信息
	$("#weibu_title").html("<div class=\"pull-right\">技术支持："+gongsiname+"</div><div><strong>Copyright</strong>&nbsp;&nbsp;&nbsp;<i class=\"fa fa-copyright\"></i>&nbsp;&nbsp;&nbsp;2016&nbsp;&nbsp;&nbsp;"+banquan+"&nbsp;&nbsp;&nbsp;版权所有&nbsp;&nbsp;&nbsp;"+beianhao+"</div>");

	//获取图标ico图片
	var tplink= document.createElement("link");
	tplink.rel = "shortcut icon";
	tplink.href = GISTONE.Loader.basePath+"magimg/"+favicon;
    document.getElementsByTagName("HEAD")[0].appendChild(tplink);
    
//	//获取二维码图片
//    $("#ewmimg").html("<img id=\"huzhu_pic_show\" alt=\"image\" class=\"img-responsive\" style=\"width:180px;height:180px;\" src=\"magimg/"+erweima+"\">");
    
    $(".def-nav,.info-i").hover(function(){
		$(this).find(".pulldown-nav").addClass("hover");
		$(this).find(".pulldown").show();
	},function(){
		$(this).find(".pulldown").hide();
		$(this).find(".pulldown-nav").removeClass("hover");
	});
    
}