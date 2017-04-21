$(function() {
	
	//行政区划单位记录
	var mycars = {};
	show_SYS_COMPANY();
	
	//加载市级下拉框
	$("#v2").append("<option></option>");
	$.each(mycars.nodes,function(i,item){
		$("#v2").append("<option value='"+item.href+"'>"+item.text+"</option>");
	});
	
	//市级下拉框选择事件
	$("#v2").change(function(){
		$("#v3").empty();
		$("#v4").empty();
		$("#v5").empty();
		$("#v3").append("<option></option>");
		$.each(mycars.nodes,function(i,item){
			if(item.href==$("#v2").val()){
				$.each(item.nodes,function(j,one){
					$("#v3").append("<option value='"+one.href+"'>"+one.text+"</option>");
				});
			}
		});
	});
	
	//县级下拉框选择事件
	$("#v3").change(function(){
		$("#v4").empty();
		$("#v5").empty();
		$("#v4").append("<option></option>");
		$.each(mycars.nodes,function(i,item){
			if(item.href==$("#v2").val()){
				$.each(item.nodes,function(j,one){
					if(one.href==$("#v3").val()){
						$.each(one.nodes,function(j,san){
							$("#v4").append("<option value='"+san.href+"'>"+san.text+"</option>");
						});
					}
				});
			}
		});
	});
	
	//乡级下拉框选择事件
	$("#v4").change(function(){
		$("#v5").empty();
		$("#v5").append("<option></option>");
		$.each(mycars.nodes,function(i,item){
			if(item.href==$("#v2").val()){
				$.each(item.nodes,function(j,one){
					if(one.href==$("#v3").val()){
						$.each(one.nodes,function(j,san){
							if(san.href==$("#v4").val()){
								$.each(san.nodes,function(j,si){
									$("#v5").append("<option value='"+si.href+"'>"+si.text+"</option>");
								});
							}
						});
					}
				});
			}
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
		$("#v2").get(0).selectedIndex=0;
		$("#v3").empty();
		$("#v4").empty();
		$("#v5").empty();
		$("#biaoge").hide();
		$("#guodu").show();
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

	
	//获取行政区划树
	function show_SYS_COMPANY(){
		if(jsondata.company_tree){
			mycars = $.extend(true,{},jsondata.company_tree);
		}else{
			var user = JSON.parse(window.sessionStorage.getItem("user"));
			if(user){
				jsondata = eval("("+user+")");
				mycars = $.extend(true,{},jsondata.company_tree);
			}
		}
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
