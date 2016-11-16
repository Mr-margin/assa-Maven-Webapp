$(function() {
	
	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
		$("#v2").append("<option></option>");
		$("#v2").append("<option value='150100000000'>呼和浩特市</option>");
		$("#v2").append("<option value='150200000000'>包头市</option>");
		$("#v2").append("<option value='150700000000'>呼伦贝尔市</option>");
		$("#v2").append("<option value='152200000000'>兴安盟</option>");
		$("#v2").append("<option value='150500000000'>通辽市</option>");
		$("#v2").append("<option value='150400000000'>赤峰市</option>");
		$("#v2").append("<option value='152500000000'>锡林郭勒盟</option>");
		$("#v2").append("<option value='150900000000'>乌兰察布市</option>");
		$("#v2").append("<option value='150600000000'>鄂尔多斯市</option>");
		$("#v2").append("<option value='150800000000'>巴彦淖尔市</option>");
		$("#v2").append("<option value='150300000000'>乌海市</option>");
		$("#v2").append("<option value='152900000000'>阿拉善盟</option>");
	}else{
		$("#v2").append("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
		$("#v3").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:jsondata.Login_map.SYS_COM_CODE}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
	}
	
	//市级下拉框选择事件
	$("#v2").change(function(){
		$("#v3").empty();
		$("#v4").empty();
		$("#v5").empty();
		if($("#v2").find("option:selected").text()==""){
			
		}else{
			$("#v3").append("<option></option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
			});
		}
		
	});
	
	//县级下拉框选择事件
	$("#v3").change(function(){
		$("#v4").empty();
		$("#v5").empty();
		if($("#v3").find("option:selected").text() == ''){
		}else{
			$("#v4").append("<option></option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
			});
		}
	});
	
	//乡级下拉框选择事件
	$("#v4").change(function(){
		$("#v5").empty();
		$("#v5").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:$("#v4").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v5").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
		});
	});
	
	
	//查询
	$("#cha_but").click(function (){
		
		if($("#v5").find("option:selected").val()!=null&&$("#v5").find("option:selected").val()!=""){
			show_pinkuncun($("#v5").find("option:selected").val());
			
			var display =$('#diyihang').css('display');
			if(display == 'none'){
			   
			}else{
				$("#diyihang").toggle(500);
				$("#disanhang").toggle(500);
				$("#disihang").toggle(500);
				$("#biaoge").show();
			}
		}else{
			toastr["warning"]("", "至少选择到村级单位。");
		}
	});
	
	//清空
	$("#qing_but").click(function(e) {
//		$("#v2").get(0).selectedIndex=0;
//		$("#v3").empty();
//		$("#v4").empty();
//		$("#v5").empty();
		
		var display =$('#diyihang').css('display');
		if(display == 'none'){
			$("#biaoge").hide();
			$("#diyihang").toggle(500);
			$("#disanhang").toggle(500);
			$("#disihang").toggle(500);
		}
	});
	
	//显示贫困户列表数据
	function show_pinkuncun(com_code){
		$("#neirong").hide();
		 $("#exportExcel_all_dengdai_1").show();
		
		 $.ajax({                 
		        url: "../getWyApp_y4_1.do",
		        type: "POST",
		        async:true,
		        dataType: "json",
		        data:{code:com_code},
		        success: function (data) {
		        	
		        	$("#neirong").show();
		        	$("#exportExcel_all_dengdai_1").hide();
		        	var zpyy_data = "";
		    		$.each(data,function(i,item){
		    			var v22;
		    			if(item.v22=="一般贫困户"){
		    				v22 = "<span class=\"badge badge-success\">"+item.v22+"</span>";
		    			}else if(item.v22=="低保贫困户"){
		    				v22 = "<span class=\"badge badge-warning\">"+item.v22+"</span>";
		    			}else if(item.v22=="低收入农户"){
		    				v22 = "<span class=\"badge badge-primary\">"+item.v22+"</span>";
		    			}else if(item.v22=="低保户"){
		    				v22 = "<span class=\"badge badge-info\">"+item.v22+"</span>";
		    			}else if(item.v22=="五保户"){
		    				v22 = "<span class=\"badge badge-info\">"+item.v22+"</span>";
		    			}else{
		    				v22 = "<span class=\"badge\">暂无</span>";
		    			}
		    			var info_val = "";
		    			if(item.riji_info=="0"){
		    				info_val = "暂无";
		    			}else{
		    				info_val = "<a onclick=\"riji_info('"+item.pkid+"');\">查看详细</a>";
		    			}
		    			var erweima = "";
		    			if(item.erweima=="0"){
		    				erweima = "暂无";
		    			}else{
		    				erweima = "<div class=\"gallerys\" style=\"display: none;\"><img src=\""+item.erweima+"\" class=\"gallery-pic\"" +
		    						" id=\"erwei_pic_"+item.pkid+"\" style=\"width:265px;\" onclick=\"$.openPhotoGallery(this)\" /></div>" +
		    						"<a onclick=\"erweima_tupian("+item.pkid+");\">扫描微信二维码</a>";
		    			}
		    	    	zpyy_data += "<tr><td>"+(i+1)+"</td>";
		    	    	zpyy_data += "<td>"+item.v6+"</td><td><i class=\"fa fa-user\"></i> &nbsp;&nbsp;"+item.v9+"</td><td>"+item.v21+"</td><td>"+v22+"</td><td>"+item.v23+"</td><td>"+erweima+"</td><td>"+info_val+"</td></tr>";
		    		});
		    		$("#zpyy").html(zpyy_data);
		        	}
		        })
	}
	
});

//点击弹出日记详细内容
function riji_info(hu_pkid){
	var data = JSON.parse(ajax_async_t("../getWyApp_y4_2.do", {pkid:hu_pkid}));
	var riji_html = "";
	
	$.each(data,function(i,item){
		
		riji_html+='<li><div class="cbp_tmicon cbp_tmicon-phone"></div><div class="cbp_tmlabel"><h2 style="font-size:14px; color:#1D1D1D;">&nbsp;&nbsp;&nbsp;';
		riji_html+='<span style="padding-left:19px;background:url(img/2.png) no-repeat;">&nbsp;&nbsp;&nbsp;'+item.v1+'  </span>&nbsp;&nbsp;｜&nbsp;&nbsp;';
		riji_html+='<span style="padding-left:19px;background:url(img/4.png) no-repeat;">&nbsp;&nbsp;&nbsp;帮扶干部：'+item.col_name+' </span>&nbsp;&nbsp;｜&nbsp;&nbsp;';
		riji_html+='<span style="padding-left:19px;background:url(img/1.png) no-repeat;">&nbsp;&nbsp;&nbsp;干部单位：'+item.danwei+' </span></h2>';
		riji_html+='<p><table width="100%" border="0" cellspacing="0" cellpadding="0"><tbody><tr>';
		riji_html+='<th scope="col"  style="border:solid 1px #f1f1f1 ; color:#121212; padding:10px; font-size:12px; background-color:#fdfdfd;" >';
		riji_html+='<span style="padding-left:19px;background:url(img/3.png) no-repeat;">&nbsp;&nbsp;&nbsp;'+item.v3+'</span></th></tr>';
		riji_html+='<tr><th scope="row" style="border:solid 0px #fff; padding:10px; font-size:12px; background-color:#; color:#bbb; text-align:right;">走访照片（点击查看）：&nbsp;&nbsp;';
		$.each(item.nodes,function(j,pic){
			riji_html+='<div class="gallerys" style="display: none;"><img src="'+pic.path+'" class="gallery-pic" id="img_pic_'+pic.pkid+'" style="width:265px;" onclick="$.openPhotoGallery(this)" /></div>';
			riji_html+='<a onclick="tu_click('+pic.pkid+');">照片'+(j+1)+'</a>&nbsp;&nbsp;';
		});
		riji_html+='</th></tr></tbody></table></p></div></li>';
	});
	
	$("#container").html(riji_html);
	$("#xiangxi_Modal").modal();
}

//打开微信二维码图片
function erweima_tupian(pkid){
	$("#erwei_pic_"+pkid).click();
}

//图片点击
function tu_click(pkid){
	$("#img_pic_"+pkid).click();
}
