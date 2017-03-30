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
var bfgb_table = $('#bfgb_table');
var chaxun = {};//存储表格查询参数

//特殊处理的旗县加载下拉菜单
function teshu_xiqian(str,str1){
	
	$("#cha_year").click(function () {
		if ( $("#cha_year").val() == "2016" ) {
			$("#add_bfgb_button").hide();
			$("#update_bfgb_button").hide();
			$("#delete_bf_button").hide();
			$("#export_button").hide();
		} else {
			$("#add_bfgb_button").show();
			$("#update_bfgb_button").show();
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
    //添加帮扶干部
   
	$("#add_bfgb_button").click(function(){
		$("#add_Form").find("input").each(function(){
			var id = $(this).attr("id");
			  	$("#"+id).val("");
	    });
		$("#sex").val("请选择");
    	$("#bfr_zzmm").val("请选择");
    	$("#relative").val("请选择");
    	$("#zhiwei_level").val("请选择");
    	$("#studying").val("请选择");
    	$("#jishu_techang").val("请选择");
    	$("#duizhang").val("请选择");
    	$("#diyi_shuji").val("请选择");
    	$("#zhucun_duiyuan").val("请选择");
		/*getbfgb("请选择");*/
		
		
		$("#add_bf #xia_title").html("添加帮扶干部");
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


	$("#update_bfgb_button").click(function(){
		var row = getSelectedRow(bfgb_table);//必须确认先选中一条白细胞数据
		if (typeof row != "undefined") {
			updateBfgbInfo(row.BFR_ID);
		}else{
			toastr["info"]("info", "必须选择一条记录");
		}
	});
    
    //修改帮扶干部
  
    
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

    //删除帮扶干部
    $("#delete_bfgb_button").click(function(){
    	var row = getSelectedRow(bfgb_table);//必须确认先选中一条白细胞数据
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
	        	deleteBfgb(row.BFR_ID);
	        });
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
	//查询按钮
    $('#cha_button').click(function () {
    	chaxun.cha_bfdw = $("#cha_bfdw").val();
    	chaxun.cha_bfr = $("#cha_bfr").val();
    	chaxun.cha_qixian = $("#cha_qixian").val();
    	chaxun.cha_dh = $("#cha_dh").val();
    	chaxun.cha_juese = $("#cha_juese").val();
    	chaxun.cha_v3 = $("#cha_v3").val();
    	chaxun.cha_year = $("#cha_year").val();
    	bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
    	bfgb_initialization();//重新初始化数据
    });
    
    //清空查询
    $('#close_cha_button').click(function () {
    	$("#cha_bfdw").val("");
    	$("#cha_bfr").val("");
    	var val = jsondata.company;
		if(val.com_level == "1"){
			$("#cha_qixian").val("请选择");
		}
    	$("#cha_juese").val("请选择");
    	$("#cha_dh").val("");
    	$("#cha_v3").val("请选择");
    	$("#cha_year").val("2017");
    	$("#add_bfgb_button").show();
		$("#update_bfgb_button").show();
		$("#delete_bf_button").show();
		$("#export_button").show();
    	$('#chauxnshiousuo').click();
    	 
    	chaxun = {};
    	bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
    	bfgb_initialization();//重新初始化数据
    });

	bfgb_initialization();
});

//加载帮扶干部单位
function getbfgb(test_ren){
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


//帮扶干部初始化
function bfgb_initialization(){
	bfgb_table.bootstrapTable({
		method: 'POST',
		height: "506",
		url: "/assa/getBfgbList.do",
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
		queryParams: queryParams_bxbxxb, //参数
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


function queryParams_bxbxxb(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    
/*    temp.cha_bfdw = chaxun.cha_bfdw;
    temp.cha_bfr = chaxun.cha_bfr;
    temp.cha_juese = chaxun.cha_juese;
    temp.cha_qixian = chaxun.cha_qixian;
    temp.cha_dh = chaxun.cha_dh;
    temp.cha_v3 = chaxun.cha_v3;
    temp.cha_year = chaxun.cha_year;*/
    return temp;
}

/**
 * 添加帮扶干部信息
 */
function addBfgbinfo(){
	
	/*if($("#add_bf #ad_bf_dw").attr("data-id")!=undefined&&$("#add_bf #ad_bf_dw").attr("data-id")!=""){*/
		var form_val = JSON.stringify(getFormJson("#add_Form"));//表单数据字符串
		/*alert(form_val);*/
		$.ajax({
		    url: "/assa/addBfgbInfo.do",
		    type: "POST",
		    async:false,
		    dataType:"json",
		    data:{
		    	/*dwid: $("#add_bf #ad_bf_dw").attr("data-id"),*/
		    	form_val: form_val
	        },
		    success: function (data) {
		    	
		    	if (data == "1") {
		    		toastr["success"]("success", "帮扶责任人维护成功");
		    		
		    		$("#add_bf").hide();
		    		$('#bf').modal('hide');
			    	
			    	bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
			    	bfgb_initialization();//重新初始化数据
			    	$("#add_Form").find("input").each(function(){
			    		var id = $(this).attr("id");
			  		  	$("#"+id).val("");
				    });
			    	$("#sex").val("请选择");
			    	$("#bfr_zzmm").val("请选择");
			    	$("#relative").val("请选择");
			    	$("#zhiwei_level").val("请选择");
			    	$("#studying").val("请选择");
			    	$("#jishu_techang").val("请选择");
			    	$("#duizhang").val("请选择");
			    	$("#diyi_shuji").val("请选择");
			    	$("#zhucun_duiyuan").val("请选择");	
			    	/*getbfgb("请选择");*/
			    	
		    	}else if(data == '2'){
		    		toastr["warning"]("warning", "操作失败，该帮扶人已存在");
		    	}else{
		    		toastr["warning"]("warning", "操作失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("error", "服务器异常");
		    }  
		
		});
	/*}else{
		toastr["info"]("info", "必须指定帮扶干部单位");
	}*/
}
/**
 * 修改前帮扶干部信息
 */
function updateBfgbInfo(bfr_id){
	/*alert(bfr_id);*/
 	$("#add_bf #xia_title").html("修改帮扶干部信息");
 	/*document.getElementById("add_bf").scrollIntoView();*/
	
	$.ajax({
	    url: "/assa/getUpdateBfgbInfo.do",
	    type: "POST",
	    async:false,
	    dataType:"json",
	    data:{
	    	bfr_id:bfr_id,
        },
	    success: function (data) {
	    	
	    	$('#add_bf #bfr_id').val(data.bfgb.bfr_id);
	    	$('#add_bf #bfr_name').val(data.bfgb.bfr_name);
	    	if(data.bfgb.sex==undefined||data.bfgb.sex==""||data.bfgb.sex==null){
	    		$('#add_bf #sex').val("请选择");
	    	}else{
	    		$('#add_bf #sex').val(data.bfgb.sex);
	    	}
	    	$('#add_bf #bfr_card').val(data.bfgb.bfr_card);
	    	
	    	$('#add_bf #ad_bf_dw').val(data.bfgb.ad_bf_dw);
	    	$('#add_bf #dw_address').val(data.bfgb.dw_address);
	    	$('#add_bf #birthday').val(data.bfgb.birthday);
	    	if(data.bfgb.bfr_zzmm==undefined||data.bfgb.bfr_zzmm==""||data.bfgb.bfr_zzmm==null){
	    		$('#add_bf #bfr_zzmm').val("请选择");
	    	}else{
	    		$('#add_bf #bfr_zzmm').val(data.bfgb.bfr_zzmm);
	    	}
	    	$('#add_bf #bfr_phone').val(data.bfgb.bfr_phone);
	    	if(data.bfgb.relative==undefined||data.bfgb.relative==""||data.bfgb.relative==null){
	    		$('#add_bf #relative').val("请选择");
	    	}else{
	    		$('#add_bf #relative').val(data.bfgb.relative);
	    	/*	getbfgb(data.bfgb.relative)*/
	    	}
	    	if(data.bfgb.zhiwei_level==undefined||data.bfgb.zhiwei_level==""||data.bfgb.zhiwei_level==null){
	    		$('#add_bf #zhiwei_level').val("请选择");
	    	}else{
	    		$('#add_bf #zhiwei_level').val(data.bfgb.zhiwei_level);
	    	}
	    	if(data.bfgb.studying==undefined||data.bfgb.studying==""||data.bfgb.studying==null){
	    		$('#add_bf #studying').val("请选择");
	    	}else{
	    		$('#add_bf #studying').val(data.bfgb.studying);
	    	}
	    	if(data.bfgb.jishu_techang==undefined||data.bfgb.jishu_techang==""||data.bfgb.jishu_techang==null){
	    		$('#add_bf #jishu_techang').val("请选择");
	    	}else{
	    		$('#add_bf #jishu_techang').val(data.bfgb.jishu_techang);
	    	}
	    	if(data.bfgb.duizhang==undefined||data.bfgb.duizhang==""||data.bfgb.duizhang==null){
	    		$('#add_bf #duizhang').val("请选择");
	    	}else{
	    		$('#add_bf #duizhang').val(data.bfgb.duizhang);
	    	}
	    	if(data.bfgb.diyi_shuji==undefined||data.bfgb.diyi_shuji==""||data.bfgb.diyi_shuji==null){
	    		$('#add_bf #diyi_shuji').val("请选择");
	    	}else{
	    		$('#add_bf #diyi_shuji').val(data.bfgb.diyi_shuji);
	    	}
	    	if(data.bfgb.zhucun_duiyuan==undefined||data.bfgb.zhucun_duiyuan==""||data.bfgb.zhucun_duiyuan==null){
	    		$('#add_bf #zhucun_duiyuan').val("请选择");
	    	}else{
	    		$('#add_bf #zhucun_duiyuan').val(data.bfgb.zhucun_duiyuan);
	    	}
	    	$('#add_bf #G_FLAG').val(data.bfgb.G_FLAG);
	    	$("#add_bf").show();
	    	$('#bf').modal();
	    	
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }  
	});
}

/**
 * 删除帮扶干部
 * @param BFR_ID
 */
function deleteBfgb(bfr_id){
	$.ajax({
	    url: "/assa/deleteBfgbInfo.do",
	    type: "POST",
	    async:false,
	    dataType:"json",
	    data:{
	    	bfr_id:bfr_id,
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("success", "数据已删除");
	    		swal("删除成功！", "您已经删除了这条信息。", "success");
	    		bfgb_table.bootstrapTable('destroy');//销毁现有表格数据
	    		bfgb_initialization();//重新初始化数据
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

