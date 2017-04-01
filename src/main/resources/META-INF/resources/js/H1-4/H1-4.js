var app={};
var duo=[];
var checked;
var zname;
$(document).ready(function() {
	/*$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});//复选框样式
	teshu_xiqian($('#cha_qixian'),$('#add_qixian'));
	chaxun.cha_qixian = jsondata.company.xian_id ;
	$('#exportExcel_all_dengdai').hide();*/
	
	$(".input-group.date").datepicker({
		todayBtn: "linked",
        keyboardNavigation: !1,
        forceParse: !1,
        calendarWeeks: !0,
        autoclose: !0
    });
	
});



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
	$("#v5").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
	$("#v6").append("<option></option>");
	var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:jsondata.Login_map.SYS_COM_CODE}, "text");
	var val = eval("("+data+")");
	$.each(val,function(i,item){
		$("#v6").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
	});
}else if(jsondata.Login_map.COM_VD=='V5'){
	var number=jsondata.Login_map.SYS_COM_CODE.substring(0,4)+'00000000';
	var t = document.getElementById("v8"); 
    for(i=0;i<t.length;i++){//给select赋值  
        if(number == t[i].value){  
        	 $("#v5").html("<option value='"+number+"'>"+t.options[i].text+"</option>");
        }
    }
    $("#v6").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
    $("#v8").append("<option></option>");
	var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v6").find("option:selected").val()}, "text");
	var val = eval("("+data+")");
	$.each(val,function(i,item){
		$("#v8").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
	});
}

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
	$("#xzqh").empty();
	if($("#v2").find("option:selected").text()=="请选择"){
		
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
	$("#xzqh").empty();
	if($("#v3").find("option:selected").text() == "请选择"){
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
	$("#xzqh").empty();
	$("#xzqh").append("<option></option>");
	var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:$("#v4").find("option:selected").val()}, "text");
	var val = eval("("+data+")");
	$.each(val,function(i,item){
		$("#xzqh").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
	});
});



//市级下拉框选择事件
$("#v5").change(function(){
	$("#v6").empty();
	$("#v8").empty();
	$("#xzqh2").empty();
	if($("#v5").find("option:selected").text()=="请选择"){
		
	}else{
		$("#v6").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v5").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v6").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
	}
	
});
//县级下拉框选择事件
$("#v6").change(function(){
	$("#v8").empty();
	$("#xzqh2").empty();
	if($("#v6").find("option:selected").text() == "请选择"){
	}else{
		$("#v8").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v6").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v8").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
		});
	}
});

//乡级下拉框选择事件
$("#v8").change(function(){
	$("#xzqh2").empty();
	$("#xzqh2").append("<option></option>");
	var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:$("#v8").find("option:selected").val()}, "text");
	var val = eval("("+data+")");
	$.each(val,function(i,item){
		$("#xzqh2").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
	});
});

var PKH_table = $('#PKH_table');
var chaxun = {};//存储表格查询参数

//特殊处理的旗县加载下拉菜单
function teshu_xiqian(str,str1){
	
	$("#cha_year").click(function () {
		if ( $("#cha_year").val() == "2016" ) {
			$("#add_PKH_button").hide();
			$("#update_PKH_button").hide();
			$("#delete_bf_button").hide();
			$("#export_button").hide();
		} else {
			$("#add_PKH_button").show();
			$("#update_PKH_button").show();
			$("#delete_bf_button").show();
			$("#export_button").show();
		}
		
	})
	
	
	/*var qixian;
	
	var type = jsondata.company_map.com_type;
	var val = jsondata.company;
	$.ajax({
	    url: "/assa/getSaveQixianController.do",
	    type: "POST",
	    async:false,
	    dataType:"json",
	    data:{
        },
	    success: function (data) {
	    	qixian='<option>请选择</option>';
	    	qixian += '<option value="4">鄂尔多斯市</option>';
	    	$.each(data,function(i,item){
	    		if(type=="单位"){
	    			if(val.com_level == "1"){
	    				qixian += '<option  value="'+item.pkid+'">'+item.com_name+'</option>';
	    			}else if(val.com_level == "2"){
	    				if(val.xian==item.com_name){
	    					qixian='<option  value="'+item.pkid+'">'+item.com_name+'</option>';
	    					qixian += '<option value="4">鄂尔多斯市</option>';
	    				}
	    			}else if(val.com_level == "3"){
	    				if(val.xian==item.com_name){
	    					qixian='<option  value="'+item.pkid+'">'+item.com_name+'</option>';
	    					qixian += '<option value="4">鄂尔多斯市</option>';
	    				}
	    			}else if(val.com_level == "4"){
	    				if(val.xian==item.com_name){
	    					qixian='<option  value="'+item.pkid+'">'+item.com_name+'</option>';
	    					qixian += '<option value="4">鄂尔多斯市</option>';
	    				}
	    			}
	    		}else if(type=="管理员"){
	    			qixian += '<option  value="'+item.pkid+'">'+item.com_name+'</option>';
	    		}
	    		
	    	});
	    	$(str).html(qixian);
	    	$(str1).html(qixian);
	    },
	    error: function () { 
	    }  
	
	});*/
}


$(function () {
    //添加贫困户
   
	$("#add_PKH_button").click(function(){
		
		$("#add_Form").find("input").each(function(){
			var id = $(this).attr("id");
			  	$("#"+id).val("");
	    });
		$("#v2").val("请选择");
    	$("#v3").val("");
    	$("#v4").val("");
    	$("#xzqh").val("");
    	$("#out_pk_property").val("请选择");
		/*getPKH("请选择");*/
		
		
		$("#add_bf #xia_title").html("添加贫困户");
	/*	layer.open({
			  type: 2,
			  area: ['700px', '530px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: '../H1-4/H1-4.html'
			});*/
		$("#add_bf").show();
		$('#bf').modal();
	/*	document.getElementById("add_bf").scrollIntoView();*/
	/*	tc_center();*/
	});


	$("#update_PKH_button").click(function(){
		var row = getSelectedRow(PKH_table);//必须确认先选中一条白细胞数据
		if (typeof row != "undefined") {
			updatePKHInfo(row.PKH_ID);
		}else{
			toastr["info"]("info", "必须选择一条记录");
		}
	});
    
    //修改贫困户
  
    
  //根据条件导出报表
   /* $('#export_button').click(function(){
    	
    	var cha_bfdw = $("#cha_bfdw").val();
    	var cha_bfr = $("#cha_bfr").val();
    	var cha_qixian = $("#cha_qixian").val();
    	var cha_dh = $("#cha_dh").val();
    	var cha_juese = $("#cha_juese").val();
    	var cha_v3 = $("#cha_v3").val();
    	$.ajax({  		       
    	    url: "/assa/exportTerm.do",
    	    type: "POST",
    	    dataType: "json",
    	    data:{
    	    	cha_bfdw : cha_bfdw,
    	    	cha_bfr : cha_bfr,
    	    	cha_qixian : cha_qixian,
    	    	cha_dh : cha_dh,
    	    	cha_juese : cha_juese,
    	    	cha_v3 : cha_v3
            },
            async:true,
    	    dataType: "json",
    	    beforeSend: function(){
    	    	$('#export_button').hide();
    	    	$('#exportExcel_all_dengdai').show();
    	    },
    	    complete: function(){
    	    	$('#export_button').show();
    	    	$('#exportExcel_all_dengdai').hide();
    	    },
    	    success: function (data) {
    	    	if (typeof data != "undefined") {
    	    		if (data == "1") {
    	    			toastr["error"]("error", "服务器异常");
    	    		}else if (data == "0") {
    	    			toastr["error"]("error", "登录超时，请刷新页面重新登录操作");
    	    		}else{
    	    			window.location.href=data.path;
    	    		}
    	    	}
    	    },
    	    error: function () { 
    	    	toastr["error"]("error", "服务器异常");
    	    }  
           
    	});
    });*/

    //删除贫困户
    $("#delete_PKH_button").click(function(){
    	var row = getSelectedRow(PKH_table);//必须确认先选中一条白细胞数据
    	if (typeof row != "undefined") {
    		swal({
	            title: "您确定要删除这条信息吗",
	           /* text: "删除后将无法恢复，请谨慎操作！",*/
	            type: "warning",
	            showCancelButton: true,
	            confirmButtonColor: "#DD6B55",
	            confirmButtonText: "删除",
	            closeOnConfirm: false
	        },
	        function() {
	        	deletePKH(row.PKH_ID);
	        });
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
	//查询按钮
    $('#cha_button').click(function () {
    	chaxun.v2 = $("#v5").val();
    	chaxun.v3= $("#v6").val();
    	chaxun.v4= $("#v8").val();
    	chaxun.xzqh= $("#xzqh2").val();
    	chaxun.hz_name = $("#hz_name").val();
    	chaxun.hz_card = $("#hz_card").val();
    	chaxun.hz_phone = $("#hz_phone").val();
    	PKH_table.bootstrapTable('destroy');//销毁现有表格数据
    	PKH_initialization();//重新初始化数据
    });
    
    //清空查询
    $('#close_cha_button').click(function () {
    	$("#hz_name").val("");
    	$("#hz_card").val("");
    	$("#hz_phone").val("");
    	/*var val = jsondata.company;
		if(val.com_level == "1"){
			$("#cha_qixian").val("请选择");
		}*/
    	$("#v5").val("请选择");
    	$("#v6").val("");
    	$("#v8").val("请选择");
    	$("#xzqh2").val("");
    	$("#add_PKH_button").show();
		$("#update_PKH_button").show();
		$("#delete_bf_button").show();
		/*$("#export_button").show();*/
    	$('#chauxnshiousuo').click();
    	 
    	chaxun = {};
    	PKH_table.bootstrapTable('destroy');//销毁现有表格数据
    	PKH_initialization();//重新初始化数据
    });

	PKH_initialization();
});

//加载贫困户单位
function getPKH(test_ren){
	if(test_ren!="请选择"){
		var $i;
		$i = $('#add_bf #ad_bf_dw').bsSuggest("destroy");
		$("#add_bf #ad_bf_dw").removeAttr("data-id");
		$('#add_bf #ad_bf_dw').val("");
		var bsSuggest = $("#add_bf #ad_bf_dw").bsSuggest({
			url: "/assa/get_bangfudanwei_h5.do?sys_company_id="+test_ren,//请求数据的 URL 地址
			idField: 'pkid',//每组数据的哪个字段作为 data-id，优先级高于 indexId 设置（推荐）
			keyField: 'v1',//每组数据的哪个字段作为输入框内容，优先级高于 indexKey 设置（推荐）
			effectiveFields: ['v1','v3','v4','com_name'],//有效显示于列表中的字段，非有效字段都会过滤，默认全部，对自定义getData方法无效
			effectiveFieldsAlias: {'v1':'单位','v3':'分管领导','v4':'联系方式','com_name':'对口嘎查村'},//有效字段的别名对象，用于 header 的显示
			showHeader: false,//是否显示选择列表的 header。为 true 时，有效字段大于一列则显示表头
			//showBtn: true,	//是否显示下拉按钮
			autoDropup: true	//选择菜单是否自动判断向上展开。设为 true，则当下拉菜单高度超过窗体，且向上方向不会被窗体覆盖，则选择菜单向上弹出
//				allowNoKeyword: false,	//是否允许无关键字时请求数据
//				multiWord: false,               //以分隔符号分割的多关键字支持
//			    separator: ',',                 //多关键字支持时的分隔符，默认为半角逗号
		}).on('onDataRequestSuccess', function (e, result) {
	        console.log('onDataRequestSuccess: ', result);
	    }).on('onSetSelectValue', function (e, keyword, data) {
	        console.log('onSetSelectValue: ', keyword, data);
	    }).on('onUnsetSelectValue', function () {
	        console.log("onUnsetSelectValue");
	    });
	}else{
		var $i;
		$i = $('#add_bf #ad_bf_dw').bsSuggest("destroy");
		$('#add_bf #ad_bf_dw').val("");
		$("#add_bf #ad_bf_dw").removeAttr("data-id");
	}
}


//贫困户初始化
function PKH_initialization(){
	PKH_table.bootstrapTable({
		method: 'POST',
		height: "506",
		url: "/assa/getPKHList.do",
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
			PKH_table.bootstrapTable('removeAll');
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
    temp.v2 = chaxun.v2;
    temp.v3 = chaxun.v3;
    temp.v4 = chaxun.v4;
    temp.xzqh = chaxun.xzqh;
    temp.hz_name = chaxun.hz_name;
    temp.hz_card = chaxun.hz_card;
    temp.hz_phone = chaxun.hz_phone;
    return temp;
}

/**
 * 添加贫困户信息
 */
function addPKHinfo(){
	/*if($("#add_bf #ad_bf_dw").attr("data-id")!=undefined&&$("#add_bf #ad_bf_dw").attr("data-id")!=""){*/
		var form_val = JSON.stringify(getFormJson("#add_Form"));//表单数据字符串
		$.ajax({
		    url: "/assa/addPKHInfo.do",
		    type: "POST",
		    async:false,
		    dataType:"json",
		    data:{
		    	/*dwid: $("#add_bf #ad_bf_dw").attr("data-id"),*/
		    	xzqh : $("#xzqh").find("option:selected").val(),
		    	form_val: form_val
	        },
		    success: function (data) {
		    	
		    	if (data == "1") {
		    		toastr["success"]("success", "贫困户维护成功");
		    		
		    		$("#add_bf").hide();
		    		$('#bf').modal('hide');
			    	
			    	PKH_table.bootstrapTable('destroy');//销毁现有表格数据
			    	PKH_initialization();//重新初始化数据
			    	$("#add_Form").find("input").each(function(){
			    		var id = $(this).attr("id");
			  		  	$("#"+id).val("");
				    });
			    	$("#v2").val("请选择");
			    	$("#v3").val("");
			    	$("#v4").val("");
			    	$("#xzqh").val("");
			    	$("#out_pk_property").val("请选择");
			    	/*$("#duizhang").val("请选择");
			    	$("#diyi_shuji").val("请选择");
			    	$("#zhucun_duiyuan").val("请选择");	*/
			    	/*getPKH("请选择");*/
			    	
		    	}else if(data == '2'){
		    		toastr["warning"]("warning", "操作失败，该贫困户已存在");
		    	}else{
		    		toastr["warning"]("warning", "操作失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("error", "服务器异常");
		    }  
		
		});
	/*}else{
		toastr["info"]("info", "必须指定贫困户单位");
	}*/
}
/**
 * 修改前贫困户信息
 */
function updatePKHInfo(pkh_id){
 	$("#add_bf #xia_title").html("修改贫困户信息");
 	/*document.getElementById("add_bf").scrollIntoView();*/
	
	$.ajax({
	    url: "/assa/updatePKHInfo.do",
	    type: "POST",
	    async:false,
	    dataType:"json",
	    data:{
	    	PKH_ID:pkh_id,
        },
	    success: function (data) {
	    	$('#add_bf #pkh_id').val(data.PKH.pkh_id);
	    	$('#add_bf #hz_name').val(data.PKH.hz_name);
	    	if(data.PKH.v2==undefined||data.PKH.v2==""||data.PKH.v2==null){
	    		$('#add_bf #v2').val("请选择");
	    	}else{
	    		$('#add_bf #v2').val(data.PKH.v2);
	    	}
	    	if(data.PKH.v3==undefined||data.PKH.v3==""||data.PKH.v3==null){
	    		$('#add_bf #v3').val("请选择");
	    	}else{
	    			
	    			$("#v3").append("<option></option>");
	    			var data2 = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:data.PKH.v2}, "text");
	    			var val = eval("("+data2+")");
	    			$.each(val,function(i,item){
	    				$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
	    			});
	    		
	    		$('#add_bf #v3').val(data.PKH.v3);
	    	}
	    	if(data.PKH.v4==undefined||data.PKH.v4==""||data.PKH.v4==null){
	    		$('#add_bf #v4').val("请选择");
	    	}else{
	    		
	    		
    			$("#v4").append("<option></option>");
    			var data2 = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:data.PKH.v3}, "text");
    			var val = eval("("+data2+")");
    			$.each(val,function(i,item){
    				$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
    			});
    		
    		$('#add_bf #v4').val(data.PKH.v4);
	    	}
	    	if(data.PKH.xzqh==undefined||data.PKH.xzqh==""||data.PKH.xzqh==null){
	    		$('#add_bf #xzqh').val("请选择");
	    	}else{
	    	
    			$("#xzqh").append("<option></option>");
    			var data2 = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:data.PKH.v4}, "text");
    			var val = eval("("+data2+")");
    			$.each(val,function(i,item){
    				$("#xzqh").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
    			});
	    		$('#add_bf #xzqh').val(data.PKH.xzqh);
	    	}
	    	$('#add_bf #hz_card').val(data.PKH.hz_card);
	    	
	    	$('#add_bf #hz_phone').val(data.PKH.hz_phone);
	    	if(data.PKH.out_pk_property==undefined||data.PKH.out_pk_property==""||data.PKH.out_pk_property==null){
	    		$('#add_bf #out_pk_property').val("请选择");
	    	}else{
	    		$('#add_bf #out_pk_property').val(data.PKH.out_pk_property);
	    	}
	    	
	    	$('#add_bf #G_FLAG').val(data.PKH.G_FLAG);
	    	$("#add_bf").show();
	    	$('#bf').modal();
	    	
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }  
	});
}

/**
 * 删除贫困户
 * @param BFR_ID
 */
function deletePKH(pkh_id){
	$.ajax({
	    url: "/assa/deletePKHInfo.do",
	    type: "POST",
	    async:false,
	    dataType:"json",
	    data:{
	    	pkh_id:pkh_id,
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("success", "数据已删除");
	    		swal("删除成功！", "您已经删除了这条信息。", "success");
	    		PKH_table.bootstrapTable('destroy');//销毁现有表格数据
	    		PKH_initialization();//重新初始化数据
	    	}else{
	    		toastr["warning"]("warning", "操作失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }  
	
	});
}

//获取选中表格行数据
function getSelectedRow(form) {
    var index = form.find('tr.success').data('index');
    return form.bootstrapTable('getData')[index];
}

