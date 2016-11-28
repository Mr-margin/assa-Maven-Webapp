$(document).ready(function(){
	$("#map").css("height",$("#zhuquyu").height()-$("#quyu1").height()+"px");
	
	var cunJson;
	var company_tree =jsondata.company_tree;
	// 百度地图API功能
	var map = new BMap.Map("map",{
		mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]
	});    // 创建Map实例
	map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));  //添加地图类型控件
	map.centerAndZoom("内蒙古",6);   // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	var cun_code;//当前选择的贫困村
	//折叠框折叠隐藏
	$("#fenge").click(function(e) {
		if($("#chaxuntiaojian").is(":hidden")){
			$("#fenge").html("【隐藏搜索区域】");
			$("#quyu1").css("height", "74px");
//			$("#quyu2").css("height", "calc(100% - 268px)");
		}else{
			$("#fenge").html("【展开搜索区域】");
			$("#quyu1").css("height", "20px");
//			$("#quyu2").css("height", "calc(100% - 78px)");
		}
		$("#chaxuntiaojian").toggle(500);
		$("#map").css("height",$("#zhuquyu").height()-$("#quyu1").height()+"px");
	});
	
//	var mycars1 = {};
//	show_SYS_COMPANYF();
	
	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
		/*$("#v2").append("<option value='0'></option>");
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
		area(jsondata.Login_map.COM_VD);
	}else if(jsondata.Login_map.COM_VD=='V3'){
		$("#v2").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
		$("#v3").append("<option value='0'></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:jsondata.Login_map.SYS_COM_CODE}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
		area(jsondata.Login_map.COM_VD,jsondata.Login_map.SYS_COM_CODE,'2','内蒙古自治区'+jsondata.Login_map.COM_NAME);
		$("#qing_but").html('<i class="fa fa-search"></i> 全市');//按键名称
	}else if(jsondata.Login_map.COM_VD=='V5'){
		var number=jsondata.Login_map.SYS_COM_CODE.substring(0,4)+'00000000';//获取市级名称
		var t = document.getElementById("v2"); 
	    for(i=0;i<t.length;i++){//给select赋值  
	        if(number == t[i].value){  
	        	$("#v2").html("<option value='"+number+"'>"+t.options[i].text+"</option>"); //获取选项中市的名称
	        }
	    }
	    $("#v3").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
	    $("#v4").append("<option value='0'></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
		});
		area(jsondata.Login_map.COM_VD,jsondata.Login_map.SYS_COM_CODE,'2','内蒙古自治区'+jsondata.Login_map.COM_NAME);
		$("#qing_but").html('<i class="fa fa-search"></i> 全县');//按键名称
	}
	
	//市级下拉框选择事件
	$("#v2").change(function(){
		$("#v3").empty();
		$("#v4").empty();
		if($("#v2").val()!='0'){
			$("#v3").append("<option value='0'></option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
			});
			getBoundary($("#v2").find("option:selected").val(),'2','内蒙古自治区'+$("#v2").find("option:selected").text());
		}else{
			getBoundary('150000000000','1','内蒙古自治区');
		}
	});
	
	//县级下拉框选择事件
	$("#v3").change(function(){
		$("#v4").empty();
		if($("#v3").val()!='0'){
			$("#v4").append("<option value='0'></option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
			});
			getBoundary($("#v3").find("option:selected").val(),'3','内蒙古自治区'+$("#v2").find("option:selected").text()+$("#v3").find("option:selected").text());
		}else{
			getBoundary($("#v2").find("option:selected").val(),'2','内蒙古自治区'+$("#v2").find("option:selected").text());
		}
	});
	
	//乡级下拉框选择事件
	$("#v4").change(function(){
		if($("#v4").val()!='0'){
			getBoundary($("#v4").find("option:selected").val(),'4','内蒙古自治区'+$("#v2").find("option:selected").text()+$("#v3").find("option:selected").text());
		}else{
			getBoundary($("#v3").find("option:selected").val(),'3','内蒙古自治区'+$("#v2").find("option:selected").text()+$("#v3").find("option:selected").text());
		}
	});
	
	//清空
	$("#qing_but").click(function(e) {
		if(jsondata.Login_map.COM_VD=="V1"){
			$("#v2").get(0).selectedIndex=0;
			$("#v3").empty();
			$("#v4").empty();
			$('#def_info_win').hide();
			getBoundary('150000000000','1','内蒙古自治区');
		}else if(jsondata.Login_map.COM_VD=="V3"){
			$("#v3").get(0).selectedIndex=0;
			$("#v4").empty();
			getBoundary(jsondata.Login_map.SYS_COM_CODE,'2','内蒙古自治区'+jsondata.Login_map.COM_NAME);
		}else if(jsondata.Login_map.COM_VD=="V5"){
			$("#v4").get(0).selectedIndex=0;
			getBoundary(jsondata.Login_map.SYS_COM_CODE,'2','内蒙古自治区'+jsondata.Login_map.COM_NAME);
			$("#qing_but").html('<i class="fa fa-search"></i> 全县');//按键名称
		}
		
	});
	
//	function show_SYS_COMPANYF(){
//		if(jsondata.company_tree){
//			mycars1 = $.extend(true,{},jsondata.company_tree);
//		}else{
//			var user = JSON.parse(window.sessionStorage.getItem("user"));
//			if(user){
//				jsondata = eval("("+user+")");
//				mycars1 = $.extend(true,{},jsondata.company_tree);
//			}
//		}
//	}
	
	//查询贫困户详细清单
	$("#pinkunhu_info").click(function (){
		show_pinkuncun(cun_code);
		$("#myModal4").modal();
	});
	
	function show_pinkuncun(c_code){
		
		 $("#neirong").hide();
		 $("#exportExcel_all_dengdai_1").show();
		
		 $.ajax({                 
		        url: "../getWyApp_y3_1.do",
		        type: "POST",
		        async:true,
		        dataType: "json",
		        data:{pkid:c_code},
		        success: function (data) {
		        	if(data!=0){
			        		$("#neirong").html("<table class=\"table table-striped\">" +
				        			"<thead><tr>" +
				        			" <th>序号</th><th>户主姓名</th><th>脱贫属性</th><th>贫困类型</th><th>主要致贫原因</th>" +
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
				    			}else{
				    				v22 = "<span class=\"badge\">暂无</span>";
				    			}
				    	    	zpyy_data += "<tr><td>"+(i+1)+"</td>";
				    	    	zpyy_data += "<td><a onclick=\"chakan_info('"+item.pkid+"','"+item.acid+"','"+item.code+"');\">"+item.v6+"</a></td><td>"+item.v21+"</td><td>"+v22+"</td><td>"+item.v23+"</td></tr>";
				    		});
				    		$("#zpyy").html(zpyy_data);
		        		}else{
		        			$("#neirong").show();
				        	$("#exportExcel_all_dengdai_1").hide();
				        	$("#neirong").html('<img class="center-block" src="../img/wu.jpg">');
		        		}
		        	}
		        })
	}
	
	/*//村点json
	var cunJson;
	var company_tree =jsondata.company_tree;
	// 百度地图API功能
	var map = new BMap.Map("map",{
		mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]
	});    // 创建Map实例
	map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));  //添加地图类型控件
	map.centerAndZoom("内蒙古",6);   // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		
	map.addEventListener("load",function(){
	 	var bdary = new BMap.Boundary();  
	    bdary.get("内蒙古自治区", function(rs){       //获取行政区域  
	        var count = rs.boundaries.length; //行政区域的点有多少个  
	        for(var i = 0; i < count; i++){  
	            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 4, strokeColor: "#ef0b0b"}); //建立多边形覆盖物  
	            ply.setFillOpacity(0.0001); 
	            map.addOverlay(ply);  //添加覆盖物  
	        }
	        map.centerAndZoom(new BMap.Point(117.5352285, 45.455135), 6);
	    });
	});*/
	
	/**
	 * 根据pkid 和 level 设置范围
	 */
 	function getBoundary(com_code,level,xzqh){
 		
 		if(level=="4"){
 			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:com_code}, "text");
			cunJson = eval("("+data+")");
//			console.log(data);
 		}
 		
		//添加区域覆盖物
		map.clearOverlays();//清除地图覆盖物       
	 	var bdary = new BMap.Boundary();  
	    bdary.get(xzqh, function(rs){//获取行政区域  
	        var count = rs.boundaries.length; //行政区域的点有多少个  
	    	var pointArray= [];
	        for(var i = 0; i < count; i++){  
	            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 4, strokeColor: "#ef0b0b"}); //建立多边形覆盖物  
	            ply.setFillOpacity(0.0001); 
	            map.addOverlay(ply);  //添加覆盖物  
	            pointArray = pointArray.concat(ply.getPath());
	        }
	        //定位最大矩形边界
	        map.setViewport(pointArray); 
	       
	    	var zoom =map.getZoom();;
	    	var myGeo = new BMap.Geocoder();
	    	// 将地址解析结果显示在地图上,并调整地图视野
	    	myGeo.getPoint(xzqh, function(point){
	    		if (point) {
	    			map.centerAndZoom(point, zoom);
	    			if(level=="4"){
	    				addCunPoint();//加载村点;
	    			}
	    		}else{
	    			alert("您选择地址没有解析到结果!");
	    		}
	    	}, xzqh);
	    });
	}
 	//区划
 	function area(id,com_code,level,xzqh){
 		//村点json
	 		
 		if(id=="V1"){
 			
 	 		map.addEventListener("load",function(){
 	 		 	var bdary = new BMap.Boundary();  
 	 		    bdary.get("内蒙古自治区", function(rs){       //获取行政区域  
 	 		        var count = rs.boundaries.length; //行政区域的点有多少个  
 	 		        for(var i = 0; i < count; i++){  
 	 		            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 4, strokeColor: "#ef0b0b"}); //建立多边形覆盖物  
 	 		            ply.setFillOpacity(0.0001); 
 	 		            map.addOverlay(ply);  //添加覆盖物  
 	 		        }
 	 		        map.centerAndZoom(new BMap.Point(117.5352285, 45.455135), 6);
 	 		    });
 	 		});
 		}else{
 			map.addEventListener("load",function(){
	 			var bdary = new BMap.Boundary();  
	 		    bdary.get(xzqh, function(rs){//获取行政区域  
	 		        var count = rs.boundaries.length; //行政区域的点有多少个  
	 		    	var pointArray= [];
	 		        for(var i = 0; i < count; i++){  
	 		            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 4, strokeColor: "#ef0b0b"}); //建立多边形覆盖物  
	 		            ply.setFillOpacity(0.0001); 
	 		            map.addOverlay(ply);  //添加覆盖物  
	 		            pointArray = pointArray.concat(ply.getPath());
	 		        }
	 		        //定位最大矩形边界
	 		        map.setViewport(pointArray); 
	 		       
	 		    	var zoom =map.getZoom();;
	 		    	var myGeo = new BMap.Geocoder();
	 		    	// 将地址解析结果显示在地图上,并调整地图视野
	 		    	myGeo.getPoint(xzqh, function(point){
	 		    		if (point) {
	 		    			map.centerAndZoom(point, zoom);
	 		    			if(level=="4"){
	 		    				addCunPoint();//加载村点;
	 		    			}
	 		    		}else{
	 		    			alert("您选择地址没有解析到结果!");
	 		    		}
	 		    	}, xzqh);
	 		    });
 			});
 		}
 		
 	}
 	//加载村点
 	function addCunPoint(){
 		if(!cunJson){
 			return;
 		}
 		
 		$.each(cunJson,function(i,item){
 			if(item.LAT&&item.LNG){
 				
	 			var point= new BMap.Point(item.LNG, item.LAT);
	 			var img="../img/fei.png";
	 			if(item.STATUS){
	 				img="../img/pin.png";//贫困
	 			}else{
	 				img="../img/fei.png";//非贫困
	 			}
	 			var myIcon = new BMap.Icon(img, new BMap.Size(40,40));
	 			var marker = new BMap.Marker(point,{icon:myIcon}); 
	 			
				//marker.setTitle(item.text);
	 			var offx=-20;
	 			if(item.V9){
	 				offx=-item.V9.length*14/2+12;
	 			}
				var label = new BMap.Label(item.V9,{offset:new BMap.Size(offx,13)});
				label.setStyle({
					 color : "#000",
					 fontSize : "12px",
					 height : "20px",
					 lineHeight : "20px",
					 fontFamily:"微软雅黑",
					 borderColor:"rgba(255,255,255,0)",
					 background:"rgba(255,255,255,0)"
				 });
				marker.setLabel(label);
				marker.addEventListener("click",function(e) {
					$("h3.search-title").html(item.V9);
					cun_code = item.V10;//记录当前选择村的ID
					
					if(item.STATUS){
		 				$("span.search-sign").show();//贫困
		 			}else{
		 				$("span.search-sign").hide()//非贫困
		 			}
					//console.log(item);
	//				alert(item.href);
				    $.ajax({
						url:"../getW3map.do",
						type:"POST",
						dataType:"json",
						data:{"code":item.V10},
						success:function(data){
							if(data&&data.length>0){
								var one = data[0];
								
								$("#pinkunhu").html(one.hushu);//贫困户数
								$("#pinkunhu_f").html("&nbsp;户");
								
								$("#pinkunrenkou").html(one.renshu);//贫困 人数
								$("#pinkunrenkou_f").html("&nbsp;人");
								
								if(one.csr){
									$("#cunshouru").html(one.csr);//村集体收入
									$("#cunshouru_f").html("万元");
								}else{
									$("#cunshouru").html("&nbsp;");//村集体收入
									$("#cunshouru_f").html("&nbsp;");
								}
								
								if(one.zcgzd){
									$("#cungongzuodui").html("是");//驻村工作队
								}else{
									$("#cungongzuodui").html("否");//驻村工作队
								}
								
								if(one.zirancun){
									$("#zirancun").html(one.zirancun);//自然村
									$("#zirancun_f").html("&nbsp;个");
								}else{
									$("#zirancun").html("&nbsp;");//自然村
									$("#zirancun_f").html("&nbsp;");
								}
								
								if(one.aad001){
									$("#cunbianhao").html(one.aad001);//贫困村编号
								}else{
									$("#cunbianhao").html("&nbsp;");//贫困村编号
								}
								
								if(one.aar011){
									$("#fuzerenname").html(one.aar011);//负责人姓名
								}else{
									$("#fuzerenname").html("&nbsp;");//负责人姓名
								}
								if(one.aar012){
									$("#lianxidianhua").html(one.aar012);//联系电话
								}else{
									$("#lianxidianhua").html("&nbsp;");//联系电话
								}
								if(one.aad301){
									$("#renjunchunshouru").html(one.aad301);//农民人均纯收入
									$("#renjunchunshouru_f").html("元");
								}else{
									$("#renjunchunshouru").html("&nbsp;");//农民人均纯收入
									$("#renjunchunshouru_f").html("&nbsp;");
								}
								$("#bangfudanwei").html("有");//帮扶单位//有无
								$('#def_info_win').show();
							    $('#def_info_win').removeClass().addClass('bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
							    	$(this).removeClass();
								});
							}
						},
						error:function(){
							}
						});
				});
				map.addOverlay(marker);  
 			}
 		});
 		
 	};

 	//弹出框关闭时间
 	$("a.aui_close").click(function(){
	    $('#def_info_win').removeClass().addClass('fadeOutRight animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	    	$(this).removeClass();
	    	 $('#def_info_win').hide();
		});
	   
 	});
});

//查看贫苦户的详细信息
function chakan_info(pkid,acid,code){
	//先清空
	$("#bfzrr_table").html('');
	$('#show_hz_phone').text("");//电话
	$('#show_hz_bank').text("");//开户银行
	$('#show_hz_banknum').text("");//银行卡号
	$('#show_hz_pkgsx').text("");//贫困户属性
	$('#show_hz_sfjls').text("");//是否军烈属
	$('#show_hz_dsznh').text("");//是否独生子女户
	$('#show_hz_snh').text("");//是否双女户
	$('#show_zyzp').text("");//主要致贫原因
	$('#show_qtzp').text("");//其他致贫原因
	$('#show_hz_sbbz').text("");//识别标准
	$("#show_gzxsr").text("");//工资性收入
	$("#show_jhsy").text("");//计划生育金
	$("#show_stbc").text("");//生态补偿金
	$("#show_scjyx").text("");//生产经营性收入
	$("#show_dbj").text("");//低保金
	$("#show_qtzy").text("");//其他转移性收入
	$("#show_ccxsr").text("");//财产性收入
	$("#show_wbj").text("");//五保金
	$("#shoow_zyxsr").text("");//转移性收入
	$("#show_ylbx").text("");//养老保险金
	$("#show_scjy").text("");//生产经营性支出
	$('#show_gdmj').text("");//耕地面积
	$('#show_ggmj').text("");//有效灌溉面积
	$('#show_ldmj').text("");//林地面积
	$('#show_tghlmj').text("");//退耕还林面积
	$('#show_lgmj').text("");//林果面积
	$('#show_mcdmj').text("");//牧草地面积
	$('#show_smmj').text("");//水面面积
	$('#show_sfscyd').text("");//是否通生产用电
	$('#show_shyd').text("");//是否通生活用电
	$('#show_zgljl').text("");//与村主干路距离
	$('#show_rullx').text("");//入户路类型
	$('#show_zfmj').text("");//住房面积
	$('#show_yskn').text("");//饮水是否困难
	$('#show_ysaq').text("");//饮水是否安全
	$('#show_zyrl').text("");//主要燃料类型
	$('#show_zyhzs').text("");//是否加入农民专业合作社
	$('#show_wscs').text("");//有无卫生厕所
	$('#show_sfbqh').text("");//是否搬迁户
	$('#show_bqfs').text("");//搬迁方式
	$('#show_azfs').text("");//安置方式
	$('#shoow_azd').text("");//安置地
	$('#show_czkn').text("");//搬迁可能存在的困难
	$("#yeqian").show();
	$("#yeqian ul>li:eq(0) a").trigger("click");//跳转第一个页签
	$("#neirong_jiben").hide();
	$("#myModal5").modal();
	$("#exportExcel_all_dengdai").show();
	$("#tablew4").hide();
	
	savePoorMessage(pkid,acid,code);
	//document.getElementById('yeqian').scrollIntoView();
	
}
var all_pkid;
function savePoorMessage(pkid,acid,code){
	$("#myModal5").modal();
	all_pkid=pkid;
	var html="";
	 $.ajax({                 
	        url: "../getTz_1.do",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{pkid:pkid,acid:acid,code:code},
	        success: function (data) {
	        	//基本信息
	        	$('#show_hz_address2').text((data.data6[0].SHI));
	        	$('#show_hz_address3').text((data.data6[0].XIAN));
	        	$('#show_hz_address4').text((data.data6[0].XIANG)); 
	        	$('#show_hz_address5').text(data.data6[0].CUN); //地址
	        	
	        	$('#show_hz_phone').text(data.data1[0].V25);//电话
	        	$('#show_hz_bank').text(data.data1[0].V26);//开户银行
	        	$('#show_hz_banknum').text(data.data1[0].V27);//银行卡号
	        	$('#show_hz_pkgsx').text(data.data1[0].V22);//贫困户属性
	        	$('#show_hz_sfjls').text(data.data1[0].V29);//是否军烈属
	        	$('#show_hz_dsznh').text(data.data1[0].V30);//是否独生子女户
	        	$('#show_hz_snh').text(data.data1[0].V31);//是否双女户
	        	$('#show_zyzp').text(data.data1[0].V23);//主要致贫原因
	        	$('#show_qtzp').text(data.data1[0].V33);//其他致贫原因
	        	$('#show_hz_sbbz').text(data.data1[0].SYS_STANDARD);//识别标准
	        	
	        	//家庭成员
	        	var jtcy;
	        	
	        	/*jtcy+='<tr><td><code style="color: #000">1</code></td><td><code style="color: #000">'+data.data1[0].V6+'</code></td>';
	        	if(data.data1[0].V7==undefined||data.data1[0].V7==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V7+'</code></td>';
	        	}
	        	if(data.data1[0].V8==undefined||data.data1[0].V8==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V8+'</code></td>';
	        	}
	        	if(data.data1[0].V10==undefined||data.data1[0].V10==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V10+'</code></td>';
	        	}
	        	if(data.data1[0].V11==undefined||data.data1[0].V11==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V11+'</code></td>';
	        	}
//	        	jtcy+='<td><code style="color: #000">'+data.data1[0].v11+'</code></td>';
	        	if(data.data1[0].V28==null||data.data1[0].V28==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V28+'</code></td>';
	        	}
	        	
	        	if(data.data1[0].V12==null||data.data1[0].V12==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V12+'</code></td>';
	        	}
	        	if(data.data1[0].V13==null||data.data1[0].V13==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V13+'</code></td>';
	        	}
	        	if(data.data1[0].V14==null||data.data1[0].V14==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V14+'</code></td>';
	        	}
	        	if(data.data1[0].V15==null||data.data1[0].V15==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V15+'</code></td>';
	        	}
	        	if(data.data1[0].V16==null||data.data1[0].V16==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V16+'</code></td>';
	        	}
	        	if(data.data1[0].V17==null||data.data1[0].V17==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V17+'</code></td>';
	        	}
	        	if(data.data1[0].V32==null||data.data1[0].V32==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V32+'</code></td>';
	        	}	
	        	if(data.data1[0].V19==null||data.data1[0].V19==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V19+'</code></td>';
	        	}	
	        	jtcy+='</tr>';*/
	        	if(data.data2==""||data.data2==null||data.data2==undefined){
	        		
	        	}else{
	        		var v6;var v7;var v8;var v10;var v11;var v12;var v13;var v14;var v28;var v15;var v16;var v17;var v32;var v19;
	        		
	        		$.each(data.data2,function(i,item){
	        			if(item.V6==null||item.V6==undefined){
	        				v6="";
	        			}else{
	        				v6=item.V6;
	        			}
	        			if(item.V7==null||item.V7==undefined){
	        				v7="";
	        			}else{
	        				v7=item.V7;
	        			}
	        			if(item.V8==null||item.V8==undefined){
	        				v8="";
	        			}else{
	        				v8=item.V8;
	        			}
	        			if(item.V10==null||item.V10==undefined){
	        				v10="";
	        			}else{
	        				v10=item.V10;
	        			}
	        			if(item.V11==null||item.V11==undefined){
	        				v11="";
	        			}else{
	        				v11=item.V11;
	        			}
	        			if(item.V12==null||item.V12==undefined){
	        				v12="";
	        			}else{
	        				v12=item.V12;
	        			}
	        			if(item.V13==null||item.V13==undefined){
	        				v13="";
	        			}else{
	        				v13=item.V13;
	        			}
	        			if(item.V14==null||item.V14==undefined){
	        				v14="";
	        			}else{
	        				v14=item.V14;
	        			}
	        			if(item.V15==null||item.V15==undefined){
	        				v15="";
	        			}else{
	        				v15=item.V15;
	        			}
	        			if(item.V16==null||item.V16==undefined){
	        				v16="";
	        			}else{
	        				v16=item.V16;
	        			}
	        			if(item.V17==null||item.V17==undefined){
	        				v17="";
	        			}else{
	        				v17=item.V17;
	        			}
	        			if(item.V19==null||item.V19==undefined){
	        				v19="";
	        			}else{
	        				v19=item.V19;
	        			}
	        			if(item.V28==null||item.V28==undefined){
	        				v28="";
	        			}else{
	        				v28=item.V28;
	        			}
	        			if(item.V32==null||item.V32==undefined){
	        				v32="";
	        			}else{
	        				v32=item.V32;
	        			}
	            		jtcy+='<tr><td><code style="color: #000">'+(i+1)+'</code></td><td><code style="color: #000">'+v6+'</code></td><td><code style="color: #000">'+v7+'</code></td><td><code style="color: #000">'+v8+'</code></td><td><code style="color: #000">'
	        	    			+v10+'</code></td></code></td><td><code style="color: #000">'+v11+'</code></td>'+
	        	    			'<td><code style="color: #000">'+v28+'</code></td><td><code style="color: #000">'+v12+'</code></td><td><code style="color: #000">'+v13+'</code></td><td><code style="color: #000">'+v14+'</code></td><td><code style="color: #000">'
	            				+v15+'</code></td>'+
	            				'<td><code style="color: #000">'+v16+'</code></td><td><code style="color: #000">'+v17+'</code></td><td><code style="color: #000">'+v32+'</code></td><td><code style="color: #000">'+v19+'</code></td></tr>';
	            		
	            	});
	            	
	        	}
	        	$("#jtcy_table").html(jtcy);
	        	
	        	//收入情况
	        	var v28;var v30;
	        	if(data.data5[0].V28==""||data.data5[0].V28==null){
	        		v28=0;
	        	}else{
	        		v28=data.data5[0].V28;
	        	}
	        	if(data.data5[0].V30==""||data.data5[0].V30==null){
	        		v30=0;
	        	}else{
	        		v30=data.data5[0].V30;
	        	}
	        	$("#show_gzxsr").text(v28+v30);//工资性收入
	        	$("#show_jhsy").text(data.data5[0].v41);//计划生育金
	        	$("#show_stbc").text(data.data5[0].v12);//生态补偿金
	        	$("#show_scjyx").text(data.data5[0].v10);//生产经营性收入
	        	$("#show_dbj").text(data.data5[0].v16);//低保金
	        	$("#show_qtzy").text(data.data5[0].v20);//其他转移性收入
	        	var v24;var v26;
	        	if(data.data5[0].V24==""||data.data5[0].V24==null){
	        		v24=0;
	        	}else{
	        		v24=data.data5[0].V24;
	        	}
	        	if(data.data5[0].V26==""||data.data5[0].V26==null){
	        		v26=0;
	        	}else{
	        		v26=data.data5[0].V26;
	        	}
	        	$("#show_ccxsr").text(v24+v26);//财产性收入
	        	$("#show_wbj").text(data.data5[0].V43);//五保金
	        	$("#shoow_zyxsr").text(data.data5[0].V22);//转移性收入
	        	$("#show_ylbx").text(data.data5[0].V14);//养老保险金
	        	/*var v2;var v4;var v6;var v8;var v10;var v12;var v14;var v16;var v18;var v20;
	        	if(data.data8[0].V2==""||data.data8[0].V2==null){
	        		v2=0;
	        	}else{
	        		v2=data.data8[0].V2
	        	}
	        	if(data.data8[0].V4==""||data.data8[0].V4==null){
	        		v4=0;
	        	}else{
	        		v4=data.data8[0].V4;
	        	}
	        	if(data.data8[0].V6==""||data.data8[0].V6==null){
	        		v6=0;
	        	}else{
	        		v6=data.data8[0].V6;
	        	}
	        	if(data.data8[0].V8==""||data.data8[0].V8==null){
	        		v8=0;
	        	}else{
	        		v8=data.data8[0].V8;
	        	}
	        	if(data.data8[0].V10==""||data.data8[0].V10==null){
	        		v10=0;
	        	}else{
	        		v10=data.data8[0].V10;
	        	}
	        	if(data.data8[0].V12==""||data.data8[0].V12==null){
	        		v12=0;
	        	}else{
	        		v12=data.data8[0].V12;
	        	}
	        	if(data.data8[0].V14==""||data.data8[0].V14==null){
	        		v14=0;
	        	}else{
	        		v14=data.data8[0].V14;
	        	}
	        	if(data.data8[0].V16==""||data.data8[0].V16==null){
	        		v16=0;
	        	}else{
	        		v16=data.data8[0].V16;
	        	}
	        	if(data.data8[0].V18==""||data.data8[0].V18==null){
	        		v18=0;
	        	}else{
	        		v18=data.data8[0].V18;
	        	}*/
	        	$("#show_scjy").text(data.data5[0].V44);//生产经营性支出
	        	
	        	
	        	//生产条件
	        	if(data.data3==""||data.data3==null||data.data3==undefined){
	        	}else{
	        		$('#show_gdmj').text(data.data3[0].V1);//耕地面积
	            	$('#show_ggmj').text(data.data3[0].V2);//有效灌溉面积
	            	$('#show_ldmj').text(data.data3[0].V3);//林地面积
	            	$('#show_tghlmj').text(data.data3[0].V4);//退耕还林面积
	            	$('#show_lgmj').text(data.data3[0].V13);//林果面积
	            	$('#show_mcdmj').text(data.data3[0].V5);//牧草地面积
	            	$('#show_smmj').text(data.data3[0].V14);//水面面积
	        	}
	        	//生活条件
	        	if(data.data4==""||data.data4==null||data.data4==undefined){
	        	}else{
	        		if(data.data4[0].V5==""||data.data4[0].V5==null||data.data4[0].V5=="-"||data.data4[0].V5==undefined){
	        			$('#show_sfscyd').text("否");//是否通生产用电
	        			$('#show_shyd').text("否");//是否通生活用电
	        		}else{
	        			$('#show_sfscyd').text("是");//是否通生产用电
	        			$('#show_shyd').text("是");//是否通生活用电
	        		}
	        		
	        		$('#show_zgljl').text(data.data4[0].V7);//与村主干路距离
	            	$('#show_rullx').text(data.data4[0].V6);//入户路类型
	            	$('#show_zfmj').text(data.data4[0].V1);//住房面积
	            	
	            	$('#show_yskn').text(data.data4[0].V8);//饮水是否困难
	            	$('#show_ysaq').text(data.data4[0].V9);//饮水是否安全
	            	$('#show_zyrl').text(data.data4[0].V10);//主要燃料类型
	            	$('#show_zyhzs').text(data.data4[0].V11);//是否加入农民专业合作社
	            	$('#show_wscs').text(data.data4[0].V12);//有无卫生厕所
	        	}
	        	
	        	/*//易地搬迁户需求
	        	if(data.data6==""||data.data6==null||data.data6==undefined){
	        	}else{
	        		$('#show_sfbqh').text(data.data6[0].VV3);//是否搬迁户
	        		$('#show_bqfs').text(data.data6[0].V1);//搬迁方式
	            	$('#show_azfs').text(data.data6[0].MOVE_TYPE);//安置方式
	            	$('#shoow_azd').text(data.data6[0].V2);//安置地
	            	$('#show_czkn').text(data.data6[0].V3);//搬迁可能存在的困难
	        	}
	        	//帮扶责任人
	        	var html1;
	        	var bcol_name="";var bv1 ="";var bv2="";var bv3="";var bcom_name="";var bv4="";var bv5=""; var bv6="";var bv7="";var btelephone="";
	        	$.each(data.data7,function(i,item){
	        			if(item.COL_NAME==undefined||item.COL_NAME==""||item.COL_NAME==null){
	        			}else{
	        				
	        				if(item.COL_NAME==null||item.COL_NAME==undefined){
	        					bcol_name="";
	        				}else{
	        					bcol_name=item.COL_NAME;
	        				}
	        				if(item.V1==null||item.V1==undefined){
	        					bv1="";
	        				}else{
	        					bv1=item.V1;
	        				}
	        				if(item.V2==null||item.V2==undefined){
	        					bv2="";
	        				}else{
	        					bv2=item.V2;
	        				}
	        				if(item.V3==null||item.V3==undefined){
	        					bv3="";
	        				}else{
	        					bv3=item.V3;
	        				}
	        				if(item.V4==null||item.V4==undefined){
	        					bv4="";
	        				}else{
	        					bv4=item.COL_NAME;///??????
	        				}
	        				if(item.V5==null||item.V5==undefined){
	        					bv5="";
	        				}else{
	        					bv5=item.V5;
	        				}
	        				if(item.V6==null||item.V6==undefined){
	        					bv6="";
	        				}else{
	        					bv6=item.V6;
	        				}
	        				if(item.V7==null||item.V7==undefined){
	        					bv7="";
	        				}else{
	        					bv7=item.V7;
	        				}
	        				if(item.COM_NAME==null||item.COM_NAME==undefined){
	        					bcom_name="";
	        				}else{
	        					bcom_name=item.COM_NAME;
	        				}
	        				if(item.TELEPHONE==null||item.TELEPHONE==undefined){
	        					btelephone="";
	        				}else{
	        					btelephone=item.TELEPHONE;
	        				}
	        				html1+='<tr><td><code style="color: #000">'+(i+1)+'</code></td><td><code style="color: #000">'+bcol_name+'</code></td>'+
	        					'<td><code style="color: #000">'+bv1+'</code></td>'+
	        					'<td><code style="color: #000">'+bv2+'</code></td>'+
	        					'<td><code style="color: #000">'+bv3+'</code></td>'+
	        					'<td><code style="color: #000">'+bcom_name+'</code></td>'+
	        					'<td><code style="color: #000">'+bv4+'</code></td>'+
	        					'<td><code style="color: #000">'+bv5+'</code></td>'+
	        					'<td><code style="color: #000">'+bv6+'</code></td>'+
	        					'<td><code style="color: #000">'+bv7+'</code></td>'+
	        					'<td><code style="color: #000">'+btelephone+'</code></td>'+
	        					'</tr>';
	        			}
	        	});
	        	$("#bfzrr_table").html(html1);*/
	        	$("#neirong_jiben").show();
	        	$("#exportExcel_all_dengdai").hide();
	        	$("#tablew4").show();
	        },
	        error: function () { 
	            toastr["error"]("error", "服务器异常");
	        }  
	    });
}

//脱贫计划、帮扶措施、工作台账、贫困户收入监测表   
function showStanding(year,num){
	$("#show_tpv2_"+num).text("");
	$("#show_tpv3_"+num).text("");
	$("#show_tpv4_"+num).text("");
	$("#show_tpv5_"+num).text("");
	$("#show_tpv6_"+num).text("");
	$("#show_tpv7_"+num).text("");
	$("#show_tpv8_"+num).text("");
	$("#show_tpv9_"+num).text("");
	$("#show_tpv10_"+num).text("");
	$("#show_tpv11_"+num).text("");
	$("#show_tpv12_"+num).text("");
	$("#show_tpv13_"+num).text("");
	$("#show_tpv14_"+num).text("");
	$("#show_tpv15_"+num).text("");
	$("#show_tpv16_"+num).text("");
	$("#show_tpv17_"+num).text("");
	$("#show_tpv18_"+num).text("");
	$("#show_tpv19_"+num).text("");
	$("#bfcs_table_"+num).html("");
	$("#gztz_table_"+num).html("");
	if(all_pkid==""||all_pkid==null){
		return;
	}
	var data = ajax_async_t("../getTz_2.do",{pkid:all_pkid,year:year},"json");

	//脱贫计划
	if(data.data1==""||data.data1==null){
		
	}else{
		$("#show_tpv2_"+num).text(data.data1[0].v2);
    	$("#show_tpv3_"+num).text(data.data1[0].v3);
    	$("#show_tpv4_"+num).text(data.data1[0].v4);
    	$("#show_tpv5_"+num).text(data.data1[0].v5);
    	$("#show_tpv6_"+num).text(data.data1[0].v6);
    	$("#show_tpv7_"+num).text(data.data1[0].v7);
    	$("#show_tpv8_"+num).text(data.data1[0].v8);
    	$("#show_tpv9_"+num).text(data.data1[0].v9);
    	$("#show_tpv10_"+num).text(data.data1[0].v10);
    	$("#show_tpv11_"+num).text(data.data1[0].v11);
    	$("#show_tpv12_"+num).text(data.data1[0].v12);
    	$("#show_tpv13_"+num).text(data.data1[0].v13);
    	$("#show_tpv14_"+num).text(data.data1[0].v14);
    	$("#show_tpv15_"+num).text(data.data1[0].v15);
    	$("#show_tpv16_"+num).text(data.data1[0].v16);
    	$("#show_tpv17_"+num).text(data.data1[0].v17);
    	$("#show_tpv18_"+num).text(data.data1[0].v18);
    	$("#show_tpv19_"+num).text(data.data1[0].v19);
	}
	//帮扶措施
	try {
		if(data.data2==""||data.data2==null){
    		
    	}else{
    		var hj=0;var hjy=0;var v4;var v5;var v6;var v5_1;
        	var html;
    		$.each(data.data2,function(i,item){
    			hj=i+1;
    			if(item.v4==null||item.v4==undefined){
    				v4="";
    			}else{
    				v4=item.v4;
    			}
    			if(item.v5==null||item.v5==undefined||item.v5==""){
    				v5="";
    				v5_1="0"
    			}else{
    				v5=item.v5;
    				v5_1=item.v5;
    			}
    			if(item.v6==null||item.v6==undefined){
    				v6="";
    			}else{
    				v6=item.v6;
    			}
    			if(isNaN(parseInt(v5_1))){
    			}else{
    				hjy+=parseInt(v5_1);
    			}
    			
    			html+='<tr><th><code style="color: #000">'+(i+1)+'</code></th><th><code style="color: #000">'+item.v1+'<code style="color: #000"></th><th><code style="color: #000">'+item.v2+'</code></th><th><code style="color: #000">'+item.v3+'</code></th>'+
    			'<th><code style="color: #000">'+v4+'</code></th><th><code style="color: #000">'+v5+'</code></th><th><code style="color: #000">'+v6+'</code></th></tr>'
    		});
    		 html+='<tr><th style="text-align:center;"  colspan="4">合计</th><th><code style="color: #000">'+hj+'项</code></th><th><code style="color: #000">'+hjy+'元</code></th><th></th></tr>';
	        	$("#bfcs_table_"+num).html(html);
    	}
		
	} catch (e) {
		
	}
	//工作台账
	if(data.data3==""||data.data3==null){
	}else{
		var html1;
		$.each(data.data3,function(i,item){
			html1+='<tr><th><code style="color: #000">'+item.v6+'</code></th><th><code style="color: #000">'+item.v1+'</code></th><th><code style="color: #000">'+item.v2+'</code></th><th><code style="color: #000">'+item.v3+
			'</code></th><th><code style="color: #000">'+item.v4+'</code></th></tr>';
		});
		$("#gztz_table_"+num).html(html1);
	}

}
