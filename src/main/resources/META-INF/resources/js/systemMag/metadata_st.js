$.validator.setDefaults({
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
var $metTable = $('#metTable');
var $metTable_st = $('#metTable_st');

$(function () {
	metTable_st_initialization();
	
    metTable_initialization();
    
    //添加新记录保存按钮
    $('#add_button').click(function () {
    	//提交表单进行验证
		var validator = $("#addForm").validate();
		if(validator.form()){
			add_metTable($("#add_name_en").val(), $("#add_name_cn").val(), $("#add_data_type").val());
		}
    });
    
    //添加新记录保存按钮
    $('#add_button_st').click(function () {
    	//提交表单进行验证
		var validator = $("#addForm_st").validate();
		if(validator.form()){
			add_metTable_st($("#add_name_en_st").val(), $("#add_name_cn_st").val(), $("#add_data_type_st").val());
		}
    });
    
    
    //修改记录按钮，对模态窗口赋值并初始化
    $('#up_button_1').click(function () {
    	var row = getSelectedRow();//获取选中的行
    	if (typeof row != "undefined") {
    		$("#up_name_en").val(row.name_en);
    		$("#up_name_cn").val(row.name_cn);
    		$("#up_data_type").val(row.data_type);
    		$("#up_button_2").click();
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    //修改已有记录保存按钮
    $('#up_button').click(function () {
    	//提交表单进行验证
		var validator = $("#upForm").validate();
		if(validator.form()){
			up_metTable($("#up_name_en").val(), $("#up_name_cn").val(), $("#up_data_type").val());
		}
    });
    
    
    
    //修改记录按钮，对模态窗口赋值并初始化
    $('#up_button_1_st').click(function () {
    	var row = getSelectedRow_st();//获取选中的行
    	if (typeof row != "undefined") {
    		$("#up_name_en_st").val(row.name_en);
    		$("#up_name_cn_st").val(row.name_cn);
    		$("#up_data_type_st").val(row.data_type);
    		$("#up_button_2_st").click();
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    //修改已有记录保存按钮
    $('#up_button_st').click(function () {
    	//提交表单进行验证
		var validator = $("#upForm_st").validate();
		if(validator.form()){
			up_metTable_st($("#up_name_en_st").val(), $("#up_name_cn_st").val(), $("#up_data_type_st").val());
		}
    });
    
    
    //删除已有记录按钮
    $('#del_button').click(function () {
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
	            del_metTable(row.pkid, row.name_en);
	        })
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
    
    
    //删除已有记录按钮
    $('#del_button_st').click(function () {
    	var row = getSelectedRow_st();//获取选中的行
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
	            del_metTable_st(row.pkid, row.name_en);
	        })
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
});
//删除按钮
function del_metTable(pkid, table_name){
	$.ajax({  		       
	    url: delMetadata_st,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	pkid:pkid,
	    	table_name:table_name
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "数据已删除");
	    		swal("删除成功！", "您已经永久删除了这条信息。", "success");
	    		//销毁现有表格数据
	    		$metTable.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metTable_initialization();
	    	}else{
	    		toastr["warning"]("", "删除失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
}

//删除按钮
function del_metTable_st(pkid, table_name){
	$.ajax({  		       
	    url: delMetadata,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	pkid:pkid,
	    	table_name:table_name
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "数据已删除");
	    		swal("删除成功！", "您已经永久删除了这条信息。", "success");
	    		//销毁现有表格数据
	    		$metTable_st.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metTable_st_initialization();
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
function up_metTable(name_en,name_cn,data_type){
	var row = getSelectedRow();//获取选中的行
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: upMetadata_st,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	pkid:row.pkid,
		    	name_en:name_en,
		    	name_cn:name_cn,
		    	data_type:data_type,
		    	table_name:row.name_en
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "记录已修改");
		    		//销毁现有表格数据
		    		$metTable.bootstrapTable('destroy');
		    		//重新初始化数据
		    		metTable_initialization();
		    		shuaxin();
		    	}else{
		    		toastr["warning"]("", "修改失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
		$("#close_up_button").click();
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_up_button").click();
	}
}


//修改方法
function up_metTable_st(name_en,name_cn,data_type){
	var row = getSelectedRow_st();//获取选中的行
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: upMetadata,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	pkid:row.pkid,
		    	name_en:name_en,
		    	name_cn:name_cn,
		    	data_type:data_type,
		    	table_name:row.name_en
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "记录已修改");
		    		//销毁现有表格数据
		    		$metTable_st.bootstrapTable('destroy');
		    		//重新初始化数据
		    		metTable_st_initialization();
		    		shuaxin_st();
		    	}else{
		    		toastr["warning"]("", "修改失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
		$("#close_up_button_st").click();
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_up_button_st").click();
	}
}



//添加方法
function add_metTable(name_en,name_cn,data_type){
	$.ajax({  		       
	    url: addMetadata_st,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	name_en:name_en,
	    	name_cn:name_cn,
	    	data_type:data_type
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "新数据添加");
	    		//销毁现有表格数据
	    		$metTable.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metTable_initialization();
	    		shuaxin();
	    	}else{
	    		toastr["warning"]("", "修改失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }  
	});
	$("#close_add_button").click();
}

//添加方法
function add_metTable_st(name_en,name_cn,data_type){
	$.ajax({  		       
	    url: addMetadata,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	name_en:name_en,
	    	name_cn:name_cn,
	    	data_type:data_type
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "新数据添加");
	    		//销毁现有表格数据
	    		$metTable_st.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metTable_st_initialization();
	    		shuaxin();
	    	}else{
	    		toastr["warning"]("", "修改失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
	$("#close_add_button_st").click();
}


//刷新
function shuaxin(){
	$("#add_name_en").val("");
	$("#add_name_cn").val("");
	$("#add_data_type").val("varchar(2000)");
	$("#up_name_en").val("");
	$("#up_name_cn").val("");
	$("#up_data_type").val("varchar(2000)");
}

//刷新
function shuaxin_st(){
	$("#add_name_en_st").val("");
	$("#add_name_cn_st").val("");
	$("#add_data_type_st").val("varchar(2000)");
	$("#up_name_en_st").val("");
	$("#up_name_cn_st").val("");
	$("#up_data_type_st").val("varchar(2000)");
}


//控制分页的三个变量
var zhu_page = 0;//初始化的当前记录开始
var zhu_strat = true;//判断是否页面新打开
var zhu_s_or_q = false;
function metTable_st_initialization(){
	zhu_s_or_q = true;
	$metTable_st.bootstrapTable({
		method: 'POST',
		height: "506",
		url: getMetadata_List,
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
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_st, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metTable_st.bootstrapTable('removeAll');
			toastr["error"]("error", "服务器异常");
		},
		onClickRow: function (row, $element) {
			var name_en = row.name_en;
			if(name_en!='pkid'&&name_en!='name_en'&&name_en!='name_cn'&&name_en!='data_type'){
				$('.success').removeClass('success');
				$($element).addClass('success');
			}
		},
		onLoadSuccess: function (data) {
			if(zhu_strat){//页面第一次打开
				zhu_page = data.page;//记录一下当前页数
				zhu_s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
			}else{//通过其他方法刷新，分页，或强制刷新
				if(zhu_s_or_q){//表格整体刷新，用于刷新数据
					if(zhu_page>this.totalPages){
						zhu_page = this.totalPages;
					}
					$metTable_st.bootstrapTable('selectPage',zhu_page);//让报个跳转到指定的页
					zhu_s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
				}else{//单独点击分页
					zhu_page = data.page;//记录一下当前页数
				}
			}
			zhu_strat = false;
		}
	});
}


//控制分页的三个变量
var col_page = 0;//初始化的当前记录开始
var col_strat = true;//判断是否页面新打开
var col_s_or_q = false;
//数据初始化
function metTable_initialization(){
	col_s_or_q = true;
	$metTable.bootstrapTable({
		//data: getsgyju(),
		method: 'POST',
		height: "506",
		url: getMetadata_st_List,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
		//idField: "ProductId",  //标识哪个字段为id主键
		showToggle: true,   //名片格式
		//cardView: false,//设置为True时显示名片（card）布局
		showColumns: true, //显示隐藏列  
		toolbar: "#exampleToolbar",
		iconSize: "outline",
        icons: {
            refresh: "glyphicon-repeat",
            toggle: "glyphicon-list-alt",
            columns: "glyphicon-list"
        },
		showRefresh: true,  //显示刷新按钮
		singleSelect: true,//复选框只能选择一条记录
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		//toolbar: "#toolbar", //设置工具栏的Id或者class
		//columns: column, //列
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metTable.bootstrapTable('removeAll');
			toastr["error"]("error", "服务器异常");
		},
		onClickRow: function (row, $element) {
			var name_en = row.name_en;
			if(name_en!='pkid'&&name_en!='name_en'&&name_en!='name_cn'&&name_en!='data_type'&&name_en!='table_name'){
				$('.success').removeClass('success');
				$($element).addClass('success');
			}
			
		},
		onLoadSuccess: function (data) {
			if(col_strat){//页面第一次打开
				col_page = data.page;//记录一下当前页数
				col_s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
			}else{//通过其他方法刷新，分页，或强制刷新
				if(col_s_or_q){//表格整体刷新，用于刷新数据
					if(col_page>this.totalPages){
						col_page = this.totalPages;
					}
					$metTable.bootstrapTable('selectPage',col_page);//让报个跳转到指定的页
					col_s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
				}else{//单独点击分页
					col_page = data.page;//记录一下当前页数
				}
			}
			col_strat = false;
		}
	});
}

function queryParams(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    return temp;
}

function queryParams_st(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    return temp;
}

//获取选中行数据
function getSelectedRow_st() {
    var index = $metTable_st.find('tr.success').data('index');
    return $metTable_st.bootstrapTable('getData')[index];
}

//获取选中行数据
function getSelectedRow() {
    var index = $metTable.find('tr.success').data('index');
    return $metTable.bootstrapTable('getData')[index];
}