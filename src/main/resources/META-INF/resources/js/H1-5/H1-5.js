var bfgb_table = $('#bfgb_table');//帮扶干部
var chaxun = {};//存储表格查询参数
var PKH_table = $('#PKH_table');//贫困户
var BFR_ID2="";//帮扶干部id
$(function () {
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
    
	//查询按钮
    $('#cha_button').click(function () {
    	chaxun.bfr_name = $("#bfr_name").val();
    	chaxun.bfr_phone = $("#bfr_phone").val();
    	chaxun.bfdw_name = $("#bfdw_name").val();
    	bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
    	bfgb_initialization();//重新初始化数据
    });
    
    //清空查询
    $('#close_cha_button').click(function () {
    	$("#bfr_name").val("");
    	$("#bfdw_name").val("");
    	$("#bfr_phone").val("");
    	$('#chauxnshiousuo').click();
    	 
    	chaxun = {};
    	bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
    	bfgb_initialization();//重新初始化数据
    });

    bfgb_initialization();
});

//帮扶干部初始化
function bfgb_initialization(){
	bfgb_table.bootstrapTable({
		method: 'POST',
		height: "506",
		url: "/assa/getBfgbInfo.do",
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
		queryParams: queryParams_bfr, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			bfgb_table.bootstrapTable('removeAll');
			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
			
		}
	});
}
//帮扶人参数
function queryParams_bfr(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    
    temp.bfr_name = chaxun.bfr_name;
    temp.bfr_phone = chaxun.bfr_phone;
    temp.bfrdw_name = chaxun.bfdw_name;
    return temp;
}
//贫困户参数
function queryParams_pkh(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    return temp;
}


//获取选中表格行数据
function getSelectedRow(form) {
    var index = form.find('tr.success').data('index');
    return form.bootstrapTable('getData')[index];
}
//根据地区得到贫困户信息
function getpkh(){
	toastr["warning"]("", "查询数据中，请稍微等待。。。");
	var v2=$("#v2").val();//选择的市
	var v3=$("#v3").val();//选择的旗区县
	var v4=$("#v4").val();//选择的苏木乡
	var v5=$("#v5").val();//选择的嘎查村
	var $i;
	$i = $('#pinkunhu #add_pkh_test').bsSuggest("destroy");
	var bsSuggest = $("#pinkunhu #add_pkh_test").bsSuggest({
		url: "/assa/getPKHByRegion.do?v2="+v2+"&&v3="+v3+"&&v4="+v4+"&&v5="+v5+"&&BFR_ID="+BFR_ID2,//请求数据的 URL 地址
		idField: 'PKH_ID',//每组数据的哪个字段作为 data-id，优先级高于 indexId 设置（推荐）
		keyField: 'HZ_NAME',//每组数据的哪个字段作为输入框内容，优先级高于 indexKey 设置（推荐）
		effectiveFields: ['V3','V5','V7','V9','HZ_NAME','HZ_CARD','OUT_PK_PROPERTY'],//有效显示于列表中的字段，非有效字段都会过滤，默认全部，对自定义getData方法无效
		effectiveFieldsAlias: {'V3':'盟市','V5':'旗区县','V7':'苏木乡','V9':'嘎查村','HZ_NAME':'户主姓名','HZ_CARD':'身份证号','OUT_PK_PROPERTY':'脱贫属性'},//有效字段的别名对象，用于 header 的显示
		showHeader: false,//是否显示选择列表的 header。为 true 时，有效字段大于一列则显示表头
		//showBtn: false,	//是否显示下拉按钮
		autoDropup: true,	//选择菜单是否自动判断向上展开。设为 true，则当下拉菜单高度超过窗体，且向上方向不会被窗体覆盖，则选择菜单向上弹出
//			allowNoKeyword: true,	//是否允许无关键字时请求数据
//			multiWord: false,               //以分隔符号分割的多关键字支持
//		    separator: ',',                 //多关键字支持时的分隔符，默认为半角逗号
	}).on('onDataRequestSuccess', function (e, result) {

        console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword, data) {
        console.log('onSetSelectValue: ', keyword, data);
    }).on('onUnsetSelectValue', function () {
        console.log("onUnsetSelectValue");
    });

}

//根据选择的帮扶人初始化贫困户数据
function PKH_initialization(BFR_ID){
	$("#pinkunhu").show();
	//$("#v2").val(" ");
	//$("#v3").empty();
	//$("#v4").empty();
	//$("#v5").empty();
	$('#pinkunhu #add_pkh_test').val("");
	$('#pinkunhu #add_pkh_test').bsSuggest("destroy");
	BFR_ID2=BFR_ID;
	PKH_table.bootstrapTable('destroy');
	PKH_table.bootstrapTable({
		method: 'POST',
		height: "506",
		url: "/assa/getPKHByBFR.do?BFR_ID="+BFR_ID,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
//		showToggle: true,   //名片格式
//		showColumns: true, //显示隐藏列  
//		toolbar: "#exampleToolbar_st2",
//		iconSize: "outline",
//        icons: {
//            refresh: "glyphicon-repeat",
//            toggle: "glyphicon-list-alt",
//            columns: "glyphicon-list"
//        },
//		showRefresh: true,  //显示刷新按钮
		singleSelect: true,//复选框只能选择一条记录
		search: false,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_pkh, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			PKH_table.bootstrapTable('removeAll');
			//toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}
	});
}

///解除帮扶干部与贫困户的关系
function release_relationship(PKH_ID,BFR_ID){
	$.ajax({  		       
	    url: "/assa/releaseBFCX.do",
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	PKH_ID: PKH_ID,
	    	BFR_ID: BFR_ID
        },
	    success: function (data) {
	    	var dataJson=JSON.parse(data)
	    	if (dataJson.isSuccess == "1") {
	    		toastr["success"]("", "帮扶责任人与贫困户关系解除");
	    		bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
  	    		bfgb_initialization();
	    		PKH_initialization(dataJson.BFR_ID);
	    	}else{
	    		toastr["warning"]("", "解除失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }  
	});
}

//保存帮扶干部与贫困户的关系
function save_pkh(){
	if($("#pinkunhu #add_pkh_test").attr("data-id")!=undefined&&$("#pinkunhu #add_pkh_test").attr("data-id")!=""&&$("#pinkunhu #add_pkh_test").attr("data-id")!="-"){
  	$.ajax({  		       
  	    url: "/assa/saveBFCX.do",
  	    type: "POST",
  	    async:false,
  	    dataType: "text",
  	    data:{
  	    	PKH_ID: $("#pinkunhu #add_pkh_test").attr("data-id"),
  	    	BFR_ID:BFR_ID2
          },
  	    success: function (data) {
  	    	if (data == "1") {
  	    		toastr["success"]("", "给帮扶干部添加贫困户成功");
  	    		bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
  	    		bfgb_initialization();
  	    		PKH_initialization(BFR_ID2);
  	    	}else{
  	    		toastr["warning"]("", "保存失败，检查数据后重试");
  	    	}
  	    },
  	    error: function () { 
  	    	toastr["error"]("error", "服务器异常");
  	    }  
  	});
	}
}

