$(function() {
	console.log(jsondata);
	cha_bfgl(jsondata.Login_map.COM_NAME,jsondata.Login_map.SYS_COM_CODE,jsondata.Login_map.ROLE_ID);//帮扶单位、驻村工作队、驻村工作干部、
	cha_shu(jsondata.Login_map.COM_NAME,jsondata.Login_map.SYS_COM_CODE,jsondata.Login_map.ROLE_ID);//查询当前行政区划的数据
	
	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
		/*$("#v2").append("<option></option>");
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
		$("#v2").append("<option value='152900000000'>阿拉善盟</option>");*/
	}else if(jsondata.Login_map.COM_VD=='V3'){
		$("#v2").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
		$("#v3").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:jsondata.Login_map.SYS_COM_CODE}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
	}else if(jsondata.Login_map.COM_VD=='V5'){
		var number=jsondata.Login_map.SYS_COM_CODE.substring(0,4)+'00000000';
		var t = document.getElementById("v2"); 
	    for(i=0;i<t.length;i++){//给select赋值  
	        if(number == t[i].value){  
	        	 $("#v2").html("<option value='"+number+"'>"+t.options[i].text+"</option>");
	        }
	    }
	    $("#v3").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
	    $("#v4").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
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
		
		/*if($("#v5").find("option:selected").val()!=null&&$("#v5").find("option:selected").val()!=""){*/
			var bfrname = $("#bfrname").val();
			var pkhname = $("#pkhname").val();
		/*	alert($("#v5").find("option:selected").val());*/
			var v2 = $("#v2").find("option:selected").val();
			var v3 = $("#v3").find("option:selected").val();
			var v4 = $("#v4").find("option:selected").val();
			var v5 = $("#v5").find("option:selected").val();
			/*alert(v2.length);*/
			//判断行政区划是否选择 市 旗县 村 并且把等级标记传给后台 以便查询使用
			if(v2.length>0 && v3.length==0 ){
				code = v2;
				level = 2;
				toastr["warning"]("", "至少选择到旗区级。");
				return;
			}else if(v3!=undefined && v3.length>0 && v4.length==0 ){
				code=v3;
				level = 3;
			}else if(v4!=undefined && v4.length>0 && v5.length==0){
				code=v4;
				level = 4;
			}else if(v4!=undefined && v5.length>0){
				code = v5;
				level = 5;
			}else{
				code = '150000000000';
				level=1;
				toastr["warning"]("", "至少选择到旗区级。");
				return;
			}
			
			chaxun.code = code;
	    	chaxun.bfrname= bfrname;
	    	chaxun.pkhname= pkhname;
	    	chaxun.level= level;
	    	BFR_table.bootstrapTable('destroy');//销毁现有表格数据
	    	BFR_initialization();//重新初始化数据
			/*var json = {code:code,bfrname:bfrname,pkhname:pkhname,level:level}
			show_pinkuncun(json);*/
			
			var display =$('#diyihang').css('display');
			if(display == 'none'){
			   
			}else{
				$("#diyihang").toggle(500);
				$("#disanhang").toggle(500);
				$("#disihang").toggle(500);
				$("#biaoge").show();
			}
		/*}else{
			toastr["warning"]("", "至少选择到村级单位。");
		}*/
	});
	
	//清空
	$("#qing_but").click(function(e) {
		if(jsondata.Login_map.COM_VD=="V1"){
			$("#v2").get(0).selectedIndex=0;
			$("#v3").empty();
			$("#v4").empty();
			$("#v5").empty();
			$("#bfrname").empty();
			$("#pkhname").empty();
		}else if(jsondata.Login_map.COM_VD=="V3"){
			$("#v3").get(0).selectedIndex=0;
			$("#v4").empty();
			$("#v5").empty();
		}else if(jsondata.Login_map.COM_VD=="V5"){
			$("#v4").get(0).selectedIndex=0;
			$("#v5").empty();
			
		}
		var display =$('#diyihang').css('display');
		if(display == 'none'){
			$("#biaoge").hide();
			$("#diyihang").toggle(500);
			$("#disanhang").toggle(500);
			$("#disihang").toggle(500);
		}
	});
	
	var BFR_table = $('#BFR_table');
	var chaxun = {};//存储表格查询参数

	//贫困户初始化
	function BFR_initialization(){
		BFR_table.bootstrapTable({
			method: 'POST',
			height: "506",
			url: "/assa/getWyApp_y4_1.do",
			dataType: "json",//从服务器返回的数据类型
			striped: true,	 //使表格带有条纹
			pagination: true,	//在表格底部显示分页工具栏
			pageSize: 10,	//页面大小
			pageNumber: 1,	//页数
			pageList: [10, 20, 50, 100],
			showToggle: true,   //名片格式
			showColumns: true, //显示隐藏列  
			toolbar: "#exampleToolbar_st",
			iconSize: "outline",
	        icons: {
	            refresh: "glyphicon-repeat",
	            toggle: "glyphicon-list-alt",
	            columns: "glyphicon-list"
	        },
			showRefresh: true,  //显示刷新按钮
			singleSelect: true,//复选框只能选择一条记录
			search: false,//是否显示右上角的搜索框
			clickToSelect: true,//点击行即可选中单选/复选框
			sidePagination: "server",//表格分页的位置 client||server
			queryParams: queryParams, //参数
			queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
			silent: true,  //刷新事件必须设置
			contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
			onLoadError: function (data) {
				BFR_table.bootstrapTable('removeAll');
				toastr["info"]("info", "没有找到匹配的记录");
			},
			onClickRow: function (row, $element) {
				$('.success').removeClass('success');
				$($element).addClass('success');
			}
		});
	}
	
	function queryParams(params) {  //配置参数
		var temp = {};
	    temp.pageSize = params.limit;
	    temp.pageNumber = params.offset;
	    temp.code = chaxun.code;
	    temp.level = chaxun.level;
	    temp.bfrname = chaxun.bfrname;
	    temp.pkhname = chaxun.pkhname;
	    
	    return temp;
	}
	//显示贫困户列表数据
	/*function show_pinkuncun(com_code){
		$("#neirong").hide();
		 $("#exportExcel_all_dengdai_1").show();
		
		 $.ajax({                 
		        url: "../getWyApp_y4_1.do",
		        type: "POST",
		        async:true,
		        dataType: "json",
		        data:com_code,
		        success: function (data) {
			        if(data!=0){
			        	$("#neirong").html("<table class=\"table table-striped\">" +
			        			"<thead><tr>" +
			        			" <th>序号</th><th>户主姓名</th><th>家庭人口（人）</th><th>脱贫属性</th><th>贫困类型</th><th>主要致贫原因</th><th>二维码</th><th>帮扶情况</th>" +
			        			"</tr></thead>" +
			        			"<tbody id=\"zpyy\"></tbody></table>")
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
			    				info_val = "<a onclick=\"riji_info('"+item.v6+"','"+item.card+"');\">查看详细</a>";
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
			        	}else{
			        		$("#neirong").show();
				        	$("#exportExcel_all_dengdai_1").hide();
				        	$("#neirong").html('<img class="center-block" src="../img/wu.png">');
			        	}
		        	}		
		        })*/
		   
/*	}*/
	
});

//点击弹出日记详细内容
function riji_info(name,house_card){
//	$("#xiangxi_Modal").modal();
	var data = JSON.parse(ajax_async_t("../getWyApp_y4_2.do", {name:name,card:house_card }));
	var riji_html = "";
	
	$.each(data,function(i,item){
		
		riji_html+='<li><div class="cbp_tmicon cbp_tmicon-phone"></div><div class="cbp_tmlabel"><h2 style="font-size:14px; color:#1D1D1D;">&nbsp;&nbsp;&nbsp;';
		riji_html+='<span style="padding-left:19px;background:url(img/2.png) no-repeat;">&nbsp;&nbsp;&nbsp;'+item.v1+'  </span>&nbsp;&nbsp;｜&nbsp;&nbsp;';
		riji_html+='<span style="padding-left:19px;background:url(img/4.png) no-repeat;">&nbsp;&nbsp;&nbsp;帮扶干部：'+item.col_name+' </span>&nbsp;&nbsp;｜&nbsp;&nbsp;';
		riji_html+='<span style="padding-left:19px;background:url(img/1.png) no-repeat;">&nbsp;&nbsp;&nbsp;贫困户：'+item.danwei+' </span></h2>';
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
/**
 * 表格数据
 * @param name
 * @param code
 * @param role_id
 */
function cha_shu (name,code,role_id) {
	if( role_id == '2'  || role_id == '1') {
		return ;
	}
	
	var data = JSON.parse(ajax_async_t("../getWyApp_y1_y.do", {name:name,code:code,role_id:role_id }));
	var html = "";
	var zong_num=0;var zong_bfr=0;
	$.each(data.data1,function(i,item){
		html += '<tr><td>'+item.xzqh+'</td> <td>'+item.num+'</td><td>'+item.bfr+'</td><td>100%</td></tr>';
		zong_num = parseInt(zong_num)+parseInt(item.num);
		zong_bfr = parseInt(zong_bfr)+parseInt(item.bfr);
	});
	html+='<tr><td>汇总</td> <td>'+zong_num+'</td><td>'+zong_bfr+'</td><td>100%</td></tr>'
	$("#bfr_table").html(html);
	$("#hu_num").text(zong_num);
	$("#zrr_num").text(zong_bfr);
	$("#liu_num").text(data.data2[0].liu);
}
//帮扶单位、驻村工作队、驻村工作干部
function cha_bfgl(name,code,role){
	var data = JSON.parse(ajax_async_t("../getWyApp_y1_bf.do", {name:name,code:code,role:role }));
	$("#bfdw").text(data[0].dw);
	$("#zcd").text(data[0].zcd);
	$("#zcgb").text(data[0].zcgb);
}