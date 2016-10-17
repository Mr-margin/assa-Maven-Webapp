var metTable_g = $('#metTable_g');
var metTable_s = $('#metTable_s');
var metTable_b = $('#metTable_b');
$(function () {
	document.getElementById("H9_li").setAttribute("class","active");//头部样式
	
//	get_tj_shi();

//	$("#shi_g").change(function () {
//		$("#qu_g").html("<option>请选择</option>");
//		$("#sum_g").html("<option>请选择</option>");
//		if($("#shi_g").val()==1){
//		}else{
//			get_tj_qiqu($("#shi_g").val(),$('#qu_g'));
//		}
//
//		metTable_g.bootstrapTable('destroy');//销毁现有表格数据
//		guoding_initialization();//重新初始化数据
//	});
//
//	$("#qu_g").change(function () {
//		$("#sum_g").html("<option>请选择</option>");
//		if($("#qu_g").val()=="请选择"){
//		}else{
//			get_tj_xiang($("#qu_g").val(),$('#sum_g'));
//		}
//
//		metTable_g.bootstrapTable('destroy');//销毁现有表格数据
//		guoding_initialization();//重新初始化数据
//	});
//	$("#sum_g").change(function () {
//		metTable_g.bootstrapTable('destroy');//销毁现有表格数据
//		guoding_initialization();//重新初始化数据
//	});
//
//	$("#shi_s").change(function () {
//		$("#qu_s").html("<option>请选择</option>");
//		$("#sum_s").html("<option>请选择</option>");
//		if($("#shi_s").val()==1){
//		}else{
//			get_tj_qiqu($("#shi_s").val(),$('#qu_s'));
//		}
//		metTable_s.bootstrapTable('destroy');//销毁现有表格数据
//		shiding_initialization();//重新初始化数据
//	});
//	$("#qu_s").change(function () {
//		$("#sum_s").html("<option>请选择</option>");
//		if($("#qu_s").val()=="请选择"){
//		}else{
//			get_tj_xiang($("#qu_s").val(),$('#sum_s'));
//		}
//		metTable_s.bootstrapTable('destroy');//销毁现有表格数据
//		shiding_initialization();//重新初始化数据
//	});
//	$("#sum_s").change(function () {
//		metTable_s.bootstrapTable('destroy');//销毁现有表格数据
//		shiding_initialization();//重新初始化数据
//	});
//
	guoding_initialization();//国标
	shiding_initialization();//市标
	bangfuren_initialization();//帮扶人
	
	time()//时间方法
	var html='<div class="col-sm-12">'+
				'<label class="col-sm-3 control-label" style="text-align:right;margin-top: 5px;margin-top: 5px;margin-left: -5%;">内蒙全区</label>'+
				'<div class="col-sm-9" style="width:80%;">'+
					'<input type="text" placeholder="内蒙全区" id="nm_qx" class="form-control m-b" ><input id="com_f_pkid" style="display: none"></input><input id="com_level" style="display: none"></input><input id="pkid" style="display: none"></input> '+
					'<div id="treediv"  style="position:absolute;top:40px ;height:300px;overflow:scroll; -moz-border-radius: 10px; -webkit-border-radius: 10px;border-radius: 10px;   padding: 5px;background: #fff;color: #fff;border: 1px solid #cccccc;display: none; z-index: 999"  >'+	
						'<div id="treeview1" class="test"></div>'+
					'</div>'+
				'</div>'+
			'</div>';
	$("#tab-1 #xingzhengquhua").html(html);
	$("#tab-2 #xingzhengquhua").html(html);
	
	$('#tab-1 #treeview1').treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_tree(),
		onNodeSelected: function(e, o) {
			click_tree(o,"1");
		}
	});
	//点击输入框弹出 div
	$("#tab-1 #nm_qx").click(function (){
		if(temp=$("#tab-1 #treediv").is(":hidden")){
			$("#tab-1 #treediv").show();
		}else{
			$("#tab-1 #treediv").hide();
		}
	});
	//判断点击的点击的位置是否在窗体内
	$("#tab-1 #nm_qx,#tab-1 #treediv").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
		var   obj=$("#tab-1 #treediv")[0];
		$($("#tab-1 #treediv")).width($("#tab-1 #nm_qx").width()+12);
	});
	//点击div层以外的部分隐藏div
	$(document).click(function() {
		$("#tab-1 #treediv,#tab-2 #treediv").hide();
	});
	$('#tab-2 #treeview1').treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_tree(),
		onNodeSelected: function(e, o) {
			click_tree(o,"2");
		}
	});
	//点击输入框弹出 div
	$("#tab-2 #nm_qx").click(function (){
		if(temp=$("#tab-2 #treediv").is(":hidden")){
			$("#tab-2 #treediv").show();
		}else{
			$("#tab-2 #treediv").hide();
		}
	});
	//判断点击的点击的位置是否在窗体内
	$("#tab-2 #nm_qx,#tab-2 #treediv").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
		var   obj=$("#tab-2 #treediv")[0];
		$($("#tab-2 #treediv")).width($("#tab-2 #nm_qx").width()+12);
	});
});

//点击树触发方法
function click_tree(val,ji){
		$("#tab-"+ji+" #com_f_pkid").attr({"value":val.com_f_pkid});
		$("#tab-"+ji+" #com_level").attr({"value":val.com_level});
		$("#tab-"+ji+" #pkid").attr({"value":val.href});
		$("#tab-"+ji+" #nm_qx").val(val.text);
		$("#tab-"+ji+" #treediv").hide(); 
}

//加载行政区划树
function show_tree(){//bootstrap tree 获取数据的方法 
	var mycars=new Array();
	if(jsondata.company_tree){
		mycars[0]= $.extend(true,{},jsondata.company_tree);
	}else{
		var user = JSON.parse(window.sessionStorage.getItem("user"));
		if(user){
			jsondata = eval("("+user+")");
			mycars[0]= $.extend(true,{},jsondata.company_tree);
		}else{
			mycars[0] =  null;
		}
	}
	return mycars;
}

/**
 * 显示刷新时间
 */
function time(){
	var data = ajax_async_t("time_data.do", {}, "text");
	var time = data;
	$(".time").html(time);
}
////下拉框中的值-市
//function get_tj_shi(){
//	var shi;
//	var data = get_sys_company();
//	$.each(data,function(i,item){
//		if(item.com_level=='1' || item.com_level=='2'){
//			shi+='<option value="'+item.pkid+'">'+item.com_name+'</option>';
//		}
//	});
//	$("#shi_g").html(shi);
//	$("#shi_s").html(shi);
//}
///**
// * 获取旗区
// */
//function get_tj_qiqu(chongmingle,str){
//	var qiqu;
//	var data = get_sys_company();
//	qiqu='<option>请选择</option>';
//	$.each(data,function(i,item){
//		if (item.com_f_pkid==chongmingle) {
//			qiqu+='<option value="'+item.pkid+'">'+item.com_name+'</option>';
//		}
//	});
//	str.html(qiqu);
//}
///**
// * 获取乡
// */
//function get_tj_xiang(chongmingle,str){
//	var xiang;
//	var data = get_sys_company();
//	xiang='<option>请选择</option>';
//	$.each(data,function(i,item){
//		if (item.com_f_pkid==chongmingle) {
//			xiang+='<option value="'+item.pkid+'">'+item.com_name+'</option>';
//		}
//	});
//	str.html(xiang);
//}


/**
 * 国定标准
 */
function guoding_initialization(){
	metTable_g.bootstrapTable({
		method: 'POST',
		url: "getSStj_Country_Stat.do",
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: false,	//在表格底部显示分页工具栏
		showToggle: false,   //名片格式
		showColumns: false, //显示隐藏列  
		showRefresh: false,  //显示刷新按钮
		singleSelect: false,//复选框只能选择一条记录
		search: false,//是否显示右上角的搜索框
		clickToSelect: false,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_g, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			metTable_g.bootstrapTable('removeAll');
			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}
	});
}

/**
 * 国定标准-配置参数
 */
function queryParams_g(params) {  
	var temp = {};
	temp.type = "国家级贫困人口";
	temp.qu_name=$("#qu_g option:selected").text();
	temp.sum_name=$("#sum_g option:selected").text();
	temp.shi_g = $("#shi_g").val();
	temp.qu_g = $("#qu_g").val();
	temp.sum_g = $("#sum_g").val();
	temp.order=params.order;
	temp.sort=params.sort;
	return temp;
}


/**
 * 市定标准
 */
function shiding_initialization(){
	metTable_s.bootstrapTable({
		method: 'POST',
		url: "getSStj_Country_Stat.do",
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: false,	//在表格底部显示分页工具栏
		showToggle: false,   //名片格式
		showColumns: false, //显示隐藏列  
		showRefresh: false,  //显示刷新按钮
		singleSelect: false,//复选框只能选择一条记录
		search: false,//是否显示右上角的搜索框
		clickToSelect: false,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_s, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			metTable_s.bootstrapTable('removeAll');
//			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}
	});
}

/**
 * 市定标准-配置参数
 */
function queryParams_s(params) {  
	var temp = {};
	temp.type = "市级低收入人口";
	temp.qu_name=$("#qu_s option:selected").text();
	temp.sum_name=$("#sum_s option:selected").text();
	temp.shi_s = $("#shi_s").val();
	temp.qu_s = $("#qu_s").val();
	temp.sum_s = $("#sum_s").val();
	temp.order=params.order;
	temp.sort=params.sort;
	return temp;
}

/**
 * 页面3帮扶人
 */
function bangfuren_initialization(){
	metTable_b.bootstrapTable({
		method: 'POST',
		url: "getSStj_BangFu_Stat.do",
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: false,	//在表格底部显示分页工具栏
		showToggle: false,   //名片格式
		showColumns: false, //显示隐藏列  
		showRefresh: false,  //显示刷新按钮
		singleSelect: false,//复选框只能选择一条记录
		search: false,//是否显示右上角的搜索框
		clickToSelect: false,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_b, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			metTable_b.bootstrapTable('removeAll');
			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}
	});
}
/**
 * 页面3帮扶人-配置参数
 */
function queryParams_b(params) { 
	var temp = {};
	temp.order=params.order;
	temp.sort=params.sort;
	return temp;
}


/**
 * 点击刷新按钮
 */
function H5_6sxanniu(){
	$('#shuaxin_time').hide();
	$('#exportExcel_all_dengdai').show();
	$('#shuaxin_time1').hide();
	$('#exportExcel_all_dengdai1').show();
	$.ajax({  		       
		url: "updata_xinbiao.do",
		type: "POST",
		async:true,
		dataType: "text",
		success: function (data) {
			if(data==1){
				metTable_g.bootstrapTable('destroy');//销毁现有表格数据
				guoding_initialization();//重新初始化数据
				metTable_s.bootstrapTable('destroy');//销毁现有表格数据
				shiding_initialization();//重新初始化数据
				time();
				$('#shuaxin_time').show();
				$('#exportExcel_all_dengdai').hide();
				$('#shuaxin_time1').show();
				$('#exportExcel_all_dengdai1').hide();
			}else{
			}
		},
		error: function () { 
			toastr["error"]("error", "服务器异常");
		}  
	});
}
