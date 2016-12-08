$(function () {
	//修改表格信息
	$("#updateModal_tz_cuoshi #save").click(function () {
		var form_val = JSON.stringify(getFormJson("#cuoshi_tz_Form"));//表单数据字符串
		
		var data = ajax_async_t("getInput_25.do",{pkid: $("#shang_yi #hu_pkid").val(),form_val: form_val},"text");
		if (data == "1") {
    		toastr["success"]("", "修改帮扶措施");
    		$("#updateModal_tz_cuoshi #close").click();
    		cuoshi_tz_table.bootstrapTable('destroy');
    		cuoshi_initialization();
    	} else {
    		toastr["warning"]("", "修改失败，检查数据后重试");
	    }
	});
	
	//保存新台账措施
	$("#addModal_tz_cuoshi #save").click(function () {
		var form_val = JSON.stringify(getFormJson("#add_cuoshi_tz_Form"));//表单数据字符串
		var data = ajax_async_t("getInput_26.do",{pkid: $("#shang_yi #hu_pkid").val(),form_val: form_val},"text");
		if (data == "1") {
    		toastr["success"]("", "保存帮扶措施");
    		$("#addModal_tz_cuoshi #close").click();
    		cuoshi_tz_table.bootstrapTable('destroy');
    		cuoshi_initialization();
    	} else {
    		toastr["warning"]("", "保存失败，检查数据后重试");
	    }
	});
});

//删除台账帮扶措施
function del_tz(v1,v2,pkid){
	var data = ajax_async_t("getInput_28.do",{pkid: pkid,v1: v1,v2: v2},"text");
	if (data == "1") {
		toastr["success"]("", "删除帮扶措施");
		cuoshi_tz_table.bootstrapTable('destroy');
		cuoshi_initialization();
	} else {
		toastr["warning"]("", "删除失败，检查数据后重试");
    }
}

//打开台账帮扶措施新建页面
function show_add_tz_cuoshi(){
	var data = ajax_async_t("getInput_27.do",{pkid: $("#shang_yi #hu_pkid").val()},"json");
	var tiaomu = '';
	if(data!=""){
		$.each(data,function(i,item){
			tiaomu+='<option>'+item+'</option>';
    	});
		Refresh_actual_add();
		$("#addModal_tz_cuoshi #v1").html(tiaomu);
		$("#addModal_tz_cuoshi").modal();
	}else{
		toastr["warning"]("", "删除失败，检查数据后重试");
	}
}

//帮扶措施
function bfcs(id){
	$("#tab_jbqk").hide();//基本情况
	$("#tab_dqszh").hide();//当前收支
	$("#tab_bfdwyzrr").hide();//帮扶单位与责任人
	$("#tab_bfcsh").show();//帮扶措施
	$("#tab_zfqk").hide();//走访情况
	$("#tab_bfcx").hide();//帮扶成效
	$("#tab_bfgshzhfx").hide();//帮扶后收支分析
	document.getElementById('tab_bfcsh').scrollIntoView();
	$("#shang_yi #hu_pkid").val(id);//记录户主ID
	cuoshi_tz_table.bootstrapTable('destroy');
	cuoshi_initialization();
	
}
var cuoshi_tz_table = $('#cuoshi_tz_table');

//帮扶措施初始化
function cuoshi_initialization(){

	cuoshi_tz_table.bootstrapTable({
		method: 'POST',
		url: "getInput_29.do",
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		detailView: true, //显示详细页面 
		detailFormatter:detailFormatter,//格式化详细页面模式的视图
		queryParams: queryParams_cuoshi, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			cuoshi_tz_table.bootstrapTable('removeAll');
			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}
	});
}
//格式化详细页面模式的视图
function detailFormatter(index, row) {
    var html = "<table style=\"padding:20px\"><tr><td>2016年</td><td>项目需求量："+row.v4_2016+"</td><td>受益资金/政策："+row.v5_2016+"</td><td>落实时间："+row.v6_2016+"</td></tr>" +
    		"<tr><td>2017年</td><td>项目需求量："+row.v4_2017+"</td><td>受益资金/政策："+row.v5_2017+"</td><td>落实时间："+row.v6_2017+"</td></tr>" +
    		"<tr><td>2018年</td><td>项目需求量："+row.v4_2018+"</td><td>受益资金/政策："+row.v5_2018+"</td><td>落实时间："+row.v6_2018+"</td></tr>" +
    		"<tr><td>2019年</td><td>项目需求量："+row.v4_2019+"</td><td>受益资金/政策："+row.v5_2019+"</td><td>落实时间："+row.v6_2019+"</td></tr></table>";
    return html;
}



function queryParams_cuoshi(params) {  //配置参数
	var temp = {};
    temp.pkid = $("#shang_yi #hu_pkid").val();
    return temp;
}

//编辑台账帮扶措施
function update_tz_cuoshi(v1,v2,v3,v4_2016,v5_2016,v6_2016,v4_2017,v5_2017,v6_2017,v4_2018,v5_2018,v6_2018,v4_2019,v5_2019,v6_2019){
	Refresh_actual();
	
	$("#updateModal_tz_cuoshi #cuoshi_title").html(v1+'-'+v2);
	
	$("#updateModal_tz_cuoshi #v1").val(v1);
	$("#updateModal_tz_cuoshi #v2").val(v2);
	
	if(v3==''){
		$("#updateModal_tz_cuoshi #v3_div input[type=radio]").removeAttr("checked");
		$("#updateModal_tz_cuoshi #v3_div input[type=radio]").parent(".iradio_square-green").removeClass("checked");
	}else{
		$("#updateModal_tz_cuoshi #v3_div input[name='v3'][value='"+v3+"']").prop("checked","true");
		$("#updateModal_tz_cuoshi #v3_div input[name='v3'][value='"+v3+"']").parent(".iradio_square-green").addClass("checked");
	}
	
	$("#updateModal_tz_cuoshi #v4_2016").val(v4_2016);
	$("#updateModal_tz_cuoshi #v5_2016").val(v5_2016);
	$("#updateModal_tz_cuoshi #v6_2016").val(v6_2016);
	
	$("#updateModal_tz_cuoshi #v4_2017").val(v4_2017);
	$("#updateModal_tz_cuoshi #v5_2017").val(v5_2017);
	$("#updateModal_tz_cuoshi #v6_2017").val(v6_2017);
	
	$("#updateModal_tz_cuoshi #v4_2018").val(v4_2018);
	$("#updateModal_tz_cuoshi #v5_2018").val(v5_2018);
	$("#updateModal_tz_cuoshi #v6_2018").val(v6_2018);
	
	$("#updateModal_tz_cuoshi #v4_2019").val(v4_2019);
	$("#updateModal_tz_cuoshi #v5_2019").val(v5_2019);
	$("#updateModal_tz_cuoshi #v6_2019").val(v6_2019);
	
	$("#updateModal_tz_cuoshi").modal();
	
}

function Refresh_actual(){
	$("#cuoshi_tz_Form").find("input").each(function(){
		  var id = $(this).attr("id");
		  if(id!="v3"){
			  $("#cuoshi_tz_Form #"+id).val("");
		  }
		  
	});
	$("#cuoshi_tz_Form input[type=radio]").removeAttr("checked");
	$("#cuoshi_tz_Form input[type=radio]").parent(".iradio_square-green").removeClass("checked");
}

function Refresh_actual_add(){
	$("#add_cuoshi_tz_Form").find("input").each(function(){
		  var id = $(this).attr("id");
		  if(id!="v3"){
			  $("#add_cuoshi_tz_Form #"+id).val("");
		  }
	});
	$("#add_cuoshi_tz_Form input[type=radio]").removeAttr("checked");
	$("#add_cuoshi_tz_Form input[type=radio]").parent(".iradio_square-green").removeClass("checked");
}




