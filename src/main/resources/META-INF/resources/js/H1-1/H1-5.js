$(function () {
	//保存新措施
	$("#zoufangqingkaung #v1_tj_zoufang").click(function () {
		if($("#zoufangqingkaung #v1").val()!=''&&$("#zoufangqingkaung #v2").val()!=''&&$("#zoufangqingkaung #v3").val()!=''){
			var data = ajax_async_t("getInput_31.do",{pkid: $("#shang_yi #hu_pkid").val(),v1 : $("#zoufangqingkaung #v1").val(),v2 : $("#zoufangqingkaung #v2").val(),v3 : $("#zoufangqingkaung #v3").val(),type : 2},"text");
			if (data == "1") {
	    		toastr["success"]("success", "保存走访情况");
	    		data_jiazai_zoufang($("#shang_yi #hu_pkid").val());
	    	} else {
	    		toastr["warning"]("warning", "保存失败，检查数据后重试");
		    }
		}else{
			toastr["warning"]("warning", "走访情况信息必须填写完整");
		}
	});
	
	//修改表格信息
	$("#zoufangqingkaung #v1_bc_zoufang").click(function () {
		var data = ajax_async_t("getInput_32.do",{pkid: $("#zoufangqingkaung #cuoshi_pkid").val(),v1 : $("#zoufangqingkaung #v1").val(),
			v2 : $("#zoufangqingkaung #v2").val(),v3 : $("#zoufangqingkaung #v3").val(),type : 2},"text");
		if (data == "1") {
    		toastr["success"]("success", "修改走访情况");
    		data_jiazai_zoufang($("#shang_yi #hu_pkid").val());
    	} else {
    		toastr["warning"]("warning", "修改失败，检查数据后重试");
	    }
	});
});
//走访情况
function zfqk(id){
	$("#tab_jbqk").hide();//基本情况
	$("#tab_dqszh").hide();//当前收支
	$("#tab_bfdwyzrr").hide();//帮扶单位与责任人
	$("#tab_bfcsh").hide();//帮扶措施
	$("#tab_zfqk").show();//走访情况
	$("#tab_bfcx").hide();//帮扶成效
	$("#tab_bfgshzhfx").hide();//帮扶后收支分析
	
	document.getElementById('tab_zfqk').scrollIntoView();
	
	$('#zoufangqingkaung .blueimp-gallery').attr('id','blueimp-gallery');
	$('#bangfuchengxiao .blueimp-gallery').attr('id','blueimp-gallery-bak');
	$('#yidifupinbanqian .blueimp-gallery').attr('id','blueimp-gallery-bak');
	$('#yidifupinbanqian #poht_list').html("");
	$('#zoufangqingkaung #poht_list').html("");
	$('#bangfuchengxiao #poht_list').html("");
	
	$("#zoufangqingkaung #data_1 .input-group.date").datepicker({
        todayBtn: "linked",
        keyboardNavigation: !1,
        forceParse: !1,
        calendarWeeks: !0,
        autoclose: !0
    }),
	
	$("#shang_yi #hu_pkid").val(id);//记录户主ID
	data_jiazai_zoufang(id);//数据初始化
}


//初始化数据
function data_jiazai_zoufang(pkid){
	$("#zoufangqingkaung #v1_bc_zoufang").hide();//保存按钮
	$("#zoufangqingkaung #v1_tj_zoufang").show();//编辑按钮
	$("#zoufangqingkaung #new_cuoshi").show();
	$("#zoufangqingkaung #poht_list_div").hide();
	$("#zoufangqingkaung #webup_div").hide();
	
	$("#zoufangqingkaung #v1").val("");
	$("#zoufangqingkaung #v2").val("");
	$("#zoufangqingkaung #v3").val("");
	var data = ajax_async_t("getInput_30.do",{pkid:pkid,type:2},"json");

	//表格数据
	var cuoshi_html = "<tr><th>走访时间</th><th style=\"width:150px\">帮扶干部</th><th style=\"width:250px\">走访情况记录</th><th style=\"text-align:center;\">照片</th><th style=\"text-align:center;\">操作</th></tr>";
	$.each(data.table,function(i,item){
		item.v1 = item.v1.replace(/[\r\n]/g,"");
		item.v2 = item.v2.replace(/[\r\n]/g,"");
		item.v3 = item.v3.replace(/[\r\n]/g,"");
		var vls = item.v3;
		if(item.v3.length>20){
			var k = 0;
    		for(var i = 0;i<item.v3.length;){
    			vls = vls.substring(0,k+20)+"<br>"+vls.substring(k+20);
    			k += 24;
    			i += 20;
    		}
		}
		cuoshi_html += "<tr id=\"zoufang_table_"+item.pkid+"\"><td>"+item.v1+"</td><td>"+item.v2+"</td><td>"+vls+"</td>";
		cuoshi_html += "<td  style=\"text-align:center;\" class=\"client-status\"><button type=\"button\" class=\"btn btn-primary btn-xs\" onClick=\"zoufang_pic_show("+item.pkid+");\"><i class=\"fa fa-search\"></i> 查看 </button>  " +
					"<button type=\"button\" class=\"btn btn-primary btn-xs\" onclick='zoufang_pic_load(2,"+item.pkid+");'><i class=\"fa fa-upload\"></i> 上传 </button></td>";
		cuoshi_html += "<td  style=\"text-align:center;\" class=\"client-status\"><button type=\"button\" class=\"btn btn-primary btn-xs\" onclick=\"update_zoufang('"+item.pkid+"','"+item.v1+"','"+item.v2+"','"+item.v3+"');\"><i class=\"fa fa-pencil\"></i> 修改 </button>   " +
				"<button type=\"button\" class=\"btn btn-primary btn-xs\" onclick='del_zoufang("+item.pkid+");'><i class=\"fa fa-remove\"></i> 删除 </button></td></tr>";
	});
	$("#zoufangqingkaung #cuoshi_table").html(cuoshi_html);

}
//删除
function del_zoufang(pkid){
	var data = ajax_async_t("getInput_33.do",{pkid: pkid,type:2},"text");
	if (data == "1") {
		toastr["success"]("success", "删除走访情况");
		data_jiazai_zoufang($("#shang_yi #hu_pkid").val());
	} else {
		toastr["warning"]("warning", "删除失败，检查数据后重试");
    }
}


//修改表格信息
function update_zoufang(pkid,v1,v2,v3){
	$("#zoufangqingkaung #zoufang_table_"+pkid).addClass("success").siblings().removeClass("success"); 
	
	$("#zoufangqingkaung #v1_bc_zoufang").show();//保存按钮
	$("#zoufangqingkaung #v1_tj_zoufang").hide();//编辑按钮
	$("#zoufangqingkaung #new_cuoshi").show();
	$("#zoufangqingkaung #poht_list_div").hide();
	$("#zoufangqingkaung #webup_div").hide();
	
	$("#zoufangqingkaung #cuoshi_pkid").val(pkid);
	$("#zoufangqingkaung #v1").val(v1);
	$("#zoufangqingkaung #v2").val(v2);
	$("#zoufangqingkaung #v3").val(v3);
}

//查看图片
function zoufang_pic_show(pkid){
	$("#zoufangqingkaung #zoufang_table_"+pkid).addClass("success").siblings().removeClass("success"); 
	
	$("#zoufangqingkaung #new_cuoshi").hide();
	$("#zoufangqingkaung #poht_list_div").show();
	$("#zoufangqingkaung #webup_div").hide();
	var data = ajax_async_t("getInput_11.do",{pkid:pkid,type:2},"json");
	//现有图片
	var tupian_html = "";
	$.each(data.pic,function(i,item){
		tupian_html += "<li id=\"pin_li_"+item.pkid+"\"><p class=\"imgWrap\"><a href=\""+item.pic_path+"\" title=\"走访情况图片\" data-gallery=\"\">" +
				"<img src=\""+item.pic_path+"\" style=\"margin:0;vertical-align:baseline;width:130px;height:85px;\"></a></p>" +
				"<div id=\"pin_del_"+item.pkid+"\" class=\"file-panel\" style=\"height: 0px;\"><span class=\"cancel\" onclick='pic_del_zoufang("+item.pkid+","+pkid+");'>删除</span></div></li>";
	});
	
	$('#zoufangqingkaung #poht_list').html(tupian_html);//要先循环一遍加上html后 再循环一遍，添加事件
	$.each(data.pic,function(i,item){
		$("#zoufangqingkaung #pin_li_"+item.pkid).mouseenter(function(){  
			$("#zoufangqingkaung #pin_del_"+item.pkid).stop().animate({
            	height: 30
            });
		}); 
		
		$("#zoufangqingkaung #pin_li_"+item.pkid).mouseleave(function(){
			$("#zoufangqingkaung #pin_del_"+item.pkid).stop().animate({
	                height: 0
	        });
	    });
	});
	

}
//删除图片
function pic_del_zoufang(pkid,fid){
	var data = ajax_async_t("getInput_12.do",{pkid: pkid},"text");
	if (data == "1") {
		toastr["success"]("success", "删除帮扶措施图片");
		zoufang_pic_show(fid);
	} else {
		toastr["warning"]("warning", "删除失败，检查数据后重试");
    }
}


//初始化上传组件
function zoufang_pic_load(type,id){
	$("#zoufangqingkaung #zoufang_table_"+id).addClass("success").siblings().removeClass("success"); 
	
	
	$("#zoufangqingkaung #new_cuoshi").hide();
	$("#zoufangqingkaung #poht_list_div").hide();
	$("#zoufangqingkaung #webup_div").show();
	
	//照片上传初始化
	$("#zoufangqingkaung #cuoshi_img_load").html("<div class=\"page-container\"><p>您可以尝试文件拖拽，使用QQ截屏工具，然后激活窗口后粘贴，或者点击添加图片按钮。支持：gif,jpg,jpeg,png,bmp。</p><div id=\"uploader\" class=\"wu-example\">" +
			"<div class=\"queueList\"><div id=\"dndArea\" class=\"placeholder\"><div id=\"filePicker\"></div><p>或将照片拖到这里，单次最多可选10张，单张照片限10M，总共限20M</p></div>" +
			"</div><div class=\"statusBar\" style=\"display:none;\"><div class=\"progress\"><span class=\"text\">0%</span><span class=\"percentage\"></span></div>" +
			"<div class=\"info\"></div><div class=\"btns\"><div id=\"filePicker2\"></div><div class=\"uploadBtn\">开始上传</div></div></div></div></div>");
	Img_load('2',id,'zoufangqingkaung');
}


