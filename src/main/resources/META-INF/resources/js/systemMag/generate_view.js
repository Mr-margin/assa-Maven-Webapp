var editor_one = CodeMirror.fromTextArea(document.getElementById("code1"), {
	lineNumbers: true,
	matchBrackets: true,
	styleActiveLine: true,
	theme: "ambiance"
});
$.validator.setDefaults({
	debug: true,
    highlight: function(e) {
        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
    },
    success: function(e) {
        e.closest(".form-group").removeClass("has-error").addClass("has-success")
    },
    errorElement: "span",
    errorPlacement: function(e, r) {
        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
    },
    errorClass: "help-block m-b-none",
    validClass: "help-block m-b-none"
});
$.validator.addMethod("isZipCode", function(value, element) {   
    var tel = /^\w+$/;
    return this.optional(element) || (tel.test(value));
}, "只能填写英文字母、数字和下划线");

toastr.options = {
	"closeButton": true,
	"debug": false,
	"progressBar": false,
	"positionClass": "toast-top-right",
	"onclick": null,
	"showDuration": "400",
	"hideDuration": "1000",
	"timeOut": "6000",
	"extendedTimeOut": "1000",
	"showEasing": "swing",
	"hideEasing": "linear",
	"showMethod": "fadeIn",
	"hideMethod": "fadeOut"
}
var $metView = $('#metView');
var $metView_data = $('#metView_data');
var view_name_en;//选中的视图ID
var implement_sql;//记录下执行的sql语句
$(function () {
	metView_initialization();//初始化表格
    $("#metView_div").hide();//从表隐藏
    
    //添加数据表保存按钮
    $('#add_view_button').click(function () {
    	//提交表单进行验证
		var validator = $("#add_view_form").validate();
		if(validator.form()){
			add_metView($("#add_name_en_view").val(), $("#add_name_cn_view").val());
		}
    });
    
    
    //修改数据表按钮，对模态窗口赋值并初始化
    $('#up_button_1').click(function () {
    	var row = getSelectedRow();//获取选中的行
    	if (typeof row != "undefined") {
    		$("#up_name_en_view").val(row.name_en);
    		$("#up_name_cn_view").val(row.name_cn);
    		$("#up_button_2").click();
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    //修改已有数据表保存按钮
    $('#up_view_button').click(function () {
    	//提交表单进行验证
		var validator = $("#up_view_form").validate();
		if(validator.form()){
			up_metView($("#up_name_en_view").val(), $("#up_name_cn_view").val(), editor_one.getValue());
		}
    });
    
    
    //删除数据表按钮
    $('#del_button_view').click(function () {
    	var row = getSelectedRow();//获取选中的行
    	if (typeof row != "undefined") {
    		swal({
	            title: "您确定要删除这条信息吗",
	            text: "删除后将无法恢复，请谨慎操作！",
	            type: "warning",
	            showCancelButton: true,
	            confirmButtonColor: "#DD6B55",
	            confirmButtonText: "删除",
	            closeOnConfirm: false
	        },
	        function() {
	            del_metView(row.pkid, row.name_en, row.sql_val);
	        })
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
    //执行sql语句
    $('#implement_view').click(function () {
    	if(editor_one.getValue()!=""){
    		implement_sql = editor_one.getValue();
    		$metView_data.bootstrapTable('destroy');
    		metView_data_implement();
    	}else{
    		toastr["info"]("info", "sql语句一定要正确哦");
    	}
    });
    
    
    //保存sql语句
    $('#save_view').click(function () {
    	var row = getSelectedRow();//获取选中的行
    	if (typeof row != "undefined") {
    		save_view(row.pkid, row.name_en, editor_one.getValue());
    	}else{
    		toastr["info"]("info", "必须选择一张视图");
    	}
    });
});

//保存sql语句
function save_view(pkid, name_en, sql_val){
	$.ajax({  		       
	    url: save_view_sql,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	pkid:pkid,
	    	name_en:name_en,
	    	sql_val:sql_val
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "语句已保存");
	    		//销毁现有表格数据
	    		//$metView.bootstrapTable('destroy');
	    		//重新初始化数据
	    		//metView_initialization();
	    		implement_sql = editor_one.getValue();
	    		$metView_data.bootstrapTable('destroy');
	    		metView_data_implement();
	    		Refresh_view();
	    		
	    		//销毁现有表格数据
	    		$metView.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metView_initialization();
	    	}else{
	    		toastr["warning"]("", "保存失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
}

//删除方法
function del_metView(pkid, name_en, sql_val){
	$.ajax({  		       
	    url: del_view,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	pkid:pkid,
	    	name_en:name_en,
	    	sql_val:sql_val
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "数据已删除");
	    		swal("删除成功！", "您已经永久删除了这条信息。", "success");
	    		//销毁现有表格数据
	    		$metView.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metView_initialization();
	    	}else{
	    		toastr["warning"]("", "删除失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
}

//修改方法
function up_metView(name_en, name_cn, sql_val){
	var row = getSelectedRow();//获取选中的行
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: up_view,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	pkid:row.pkid,
		    	name_en:name_en,
		    	name_cn:name_cn,
		    	sql_val:sql_val
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "记录已修改");
		    		//销毁现有表格数据
		    		$metView.bootstrapTable('destroy');
		    		//重新初始化数据
		    		metView_initialization();
		    		Refresh_view();
		    	}else{
		    		toastr["warning"]("", "修改失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
		$("#close_up_view_button").click();
	}else{
		toastr["info"]("info", "必须选择一张视图");
		$("#close_up_view_button").click();
	}
}

//添加方法
function add_metView(name_en, name_cn){
	$.ajax({  		       
	    url: add_view,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	name_en:name_en,
	    	name_cn:name_cn
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "新数据添加");
	    		//销毁现有表格数据
	    		$metView.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metView_initialization();
	    		Refresh_view();
	    	}else{
	    		toastr["warning"]("", "添加失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
	$("#close_add_view_button").click();
}

//刷新主表
function Refresh_view(){
	$("#add_name_en_view").val("");
	$("#add_name_cn_view").val("");
	$("#up_name_en_view").val("");
	$("#up_name_cn_view").val("");
}

//初始化主表
function metView_initialization(){
	$metView.bootstrapTable({
		method: 'POST',
		height: "506",
		url: get_view_list,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
		singleSelect: true,//复选框只能选择一条记录
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		toolbar: "#Toolbar_view", //设置工具栏的Id或者class
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metView.bootstrapTable('removeAll');
			toastr["info"]("info", "还是空表哦");
		},
		onClickRow: function (row, $element) {
			$("table#metView .success").removeClass('success');
			$($element).addClass('success');
			$metView_data.bootstrapTable('destroy');
			view_name_en = row.name_en;
			if (typeof row.sql_val != "undefined") {
				metView_data_initialization();
				editor_one.setValue(row.sql_val);//将选中行的sql语句赋值给代码编辑器
			}else{
				editor_one.setValue("");
			}
		}
	});
}

//从表初始化
function metView_data_initialization(){
	$metView_data.bootstrapTable({
		method: 'POST',
		height: "506",
		url: get_view_data,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
		showToggle: false,   //名片格式
		showColumns: false, //显示隐藏列  
		showRefresh: false,  //显示刷新按钮
		toolbar: "#Toolbar_view_data",
		singleSelect: true,//复选框只能选择一条记录
		search: false,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_view, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		columns: view_col(), //列
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metView_data.bootstrapTable('removeAll');
			toastr["info"]("info", "还是空表哦");
		},
		onLoadSuccess: function (data) {
			$("#metView_div").show();//从表显示
		}
	});
}

//执行sql语句
function metView_data_implement(){
	if(typeof implement_sql != "undefined"){//sql语句已经记录
		$metView_data.bootstrapTable({
			method: 'POST',
			height: "506",
			url: get_view_data_implement,
			dataType: "json",//从服务器返回的数据类型
			striped: true,	 //使表格带有条纹
			pagination: true,	//在表格底部显示分页工具栏
			pageSize: 10,	//页面大小
			pageNumber: 1,	//页数
			pageList: [10, 20, 50, 100],
			showToggle: false,   //名片格式
			showColumns: false, //显示隐藏列  
			showRefresh: false,  //显示刷新按钮
			toolbar: "#Toolbar_view_data",
			singleSelect: true,//复选框只能选择一条记录
			search: false,//是否显示右上角的搜索框
			clickToSelect: true,//点击行即可选中单选/复选框
			sidePagination: "server",//表格分页的位置 client||server
			queryParams: queryParams_implement, //参数
			queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
			columns: view_implement(), //列
			silent: true,  //刷新事件必须设置
			contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
			onLoadError: function (data) {
				$metView_data.bootstrapTable('removeAll');
				toastr["info"]("info", "还是空表哦");
			},
			onLoadSuccess: function (data) {
				$("#metView_div").show();//从表显示
			}
		});
	}else{
		toastr["error"]("error", "要执行的语句没有正确记录，请保存后刷新页面重试");
	}
}

//副表显示列
function view_col(){
	var view_col_obj;
	if(typeof view_name_en != "undefined"){
		$.ajax({  		       
		    url: get_view_col,
		    type: "POST",
		    async:false,
		    dataType: "json",
		    data:{
		    	view_name_en : view_name_en
	        },
		    success: function (data) {
		    	if (typeof data != "undefined") {
		    		if(data.state==0){
		    			var value_col = JSON.stringify(data.result);
			    		value_col = value_col.replace(/"false"/gm,'false');//将字符串中所有带双引号的true和false替换
			    		value_col = value_col.replace(/"true"/gm,'true');
			    		view_col_obj = jQuery.parseJSON(value_col);
			    		
		    		}else if(data.state==1){
		    			toastr["error"]("error", data.error);
		    		}
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("error", "服务器异常");
		    }
		});
	}else{
		toastr["info"]("info", "必须选择一个视图");
	}
	return view_col_obj;
}

//执行sql语句列显示
function view_implement(){
	var view_col_obj;
	$.ajax({  		       
	    url: get_view_implement,
	    type: "POST",
	    async:false,
	    dataType: "json",
	    data:{
	    	implement_sql : implement_sql
        },
	    success: function (data) {
	    	if (typeof data != "undefined") {
	    		if(data.state==0){
	    			var value_col = JSON.stringify(data.result);
		    		value_col = value_col.replace(/"false"/gm,'false');//将字符串中所有带双引号的true和false替换
		    		value_col = value_col.replace(/"true"/gm,'true');
		    		view_col_obj = jQuery.parseJSON(value_col);
		    		
	    		}else if(data.state==1){
	    			toastr["error"]("error", data.error);
	    		}else if(data.state==2){
	    			toastr["info"]("info", data.prompt);
	    		}
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }
	});
	return view_col_obj;
}

//配置参数
function queryParams(params){
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    return temp;
}

function queryParams_view(params) {  //配置参数
	var row = getSelectedRow();//子表的查询参数需要增加父表选中的内容
	if (typeof row != "undefined") {
		var temp = {};
		temp.pageSize = params.limit;
		temp.pageNumber = params.offset;
		temp.table_name = row.name_en;
		return temp;
	}else{
		toastr["info"]("info", "必须选择一张数据表");
	}
}

function queryParams_implement(params){//配置参数
	var temp = {};
	temp.pageSize = params.limit;
	temp.pageNumber = params.offset;
	temp.implement_sql = implement_sql;
	return temp;
}

//获取选中行数据
function getSelectedRow() {
    var index = $metView.find('tr.success').data('index');
    return $metView.bootstrapTable('getData')[index];
}