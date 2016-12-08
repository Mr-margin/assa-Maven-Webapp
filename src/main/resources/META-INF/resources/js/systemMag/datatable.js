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
var $metTable = $('#metTable_table');
var $actual = $('#actual_table');
var actual_table_obj;//实际数据表结构
var value_obj;//元数据表结构
$(function () {
    metTable_initialization();//初始化$metTable
    $("#assistant").hide();//从表隐藏
    
    
    //添加元数据表内容保存按钮
    $('#add_button').click(function () {
    	//提交表单进行验证
		var validator = $("#addForm").validate();
		if(validator.form()){
			add_actual();
		}
    });
    
    
    //修改元数据表内容按钮，对模态窗口赋值并初始化
    $('#up_button_1').click(function () {
    	var row = getSelectedRow_actual();//获取选中的行
    	if (typeof row != "undefined") {
    		$("#upForm").find("input").each(function(){
    			  var id = $(this).attr("id");
    			  
    			  for(var m = 0;m<actual_table_obj.length;m++){
    				  var col_val = actual_table_obj[m];
    				  if(id=="up_"+col_val.field){
    					  $("#"+id).val(eval("row."+col_val.field));
    				  }
    			  }
    		});
    		$("#upForm").find("textarea").each(function(){
    			var id = $(this).attr("id");
	  			  
    			for(var m = 0;m<actual_table_obj.length;m++){
    				var col_val = actual_table_obj[m];
    				if(id=="up_"+col_val.field){
    					$("#"+id).val(eval("row."+col_val.field));
    				}
    			}
	  		});
    		$("#upForm").find("select").each(function(){
    			var id = $(this).attr("id");
	  			  
    			for(var m = 0;m<actual_table_obj.length;m++){
    				var col_val = actual_table_obj[m];
    				if(id=="up_"+col_val.field){
    					$("#"+id).val(eval("row."+col_val.field));
    				}
    			}
	  		});
    		$("#up_button_2").click();
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    //修改元数据表内容保存按钮
    $('#up_button').click(function () {
    	//提交表单进行验证
		var validator = $("#upForm").validate();
		if(validator.form()){
			up_actual();
		}
    });
    
    
    
    //删除数据表按钮
    $('#del_button').click(function () {
    	var row = getSelectedRow_actual();//获取选中的行
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
	            del_actual(row.pkid);
	        })
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
    
    //导入
    $('#Import_button').click(function () {
    	var row_table = getSelectedRow_metTable();//获取选中的行
    	if (typeof row_table != "undefined") {
    		$('#table_name').val(row_table.name_en);
    		var form = $("form[name=ImportForm]");
        	var options  = {
                url:ImportData,
                type:'post',
                success:function(data, jqForm, options){
                    var jsondata = eval("("+data+")");
                    $('#info_title').html();
                    $('#info_message').html();
                    $('#info_errdata').html();
                    $('#info_url').html();
                    $("#close_Import_button").click();//关闭导入框
                    
                    if(jsondata.error == "0"){
                        if ((typeof jsondata.url != "undefined")&&(typeof jsondata.errdata != "undefined")) {
                        	$('#info_url').html(jsondata.url);
                        	$("#info_url").attr("href",jsondata.url);
                        	$('#info_errdata').html(jsondata.errdata);
                        	$('#info_title').html("导入结果");
                            $('#info_message').html(jsondata.message);
                            $("#Info_button").click();//打开结果框
                        }else{
                        	toastr["success"]("success", jsondata.message);
                        }
                        //销毁现有表格数据
    		    		$actual.bootstrapTable('destroy');
    		    		//重新初始化数据
    		    		actual_initialization();
                    }else{
                        toastr["error"]("error", jsondata.message);
                    }
                }
            };
            form.ajaxSubmit(options);
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
    
    //导出
    $('#export_button').click(function (){
    	var row_table = getSelectedRow_metTable();//获取选中的行
    	if (typeof row_table != "undefined") {
    		$.ajax({  		       
    		    url: ExportData,
    		    type: "POST",
    		    async:false,
    		    dataType: "text",
    		    data:{
    		    	table_name: row_table.name_en,
    		    	name_cn: row_table.name_cn
    	        },
    		    success: function (data) {
    		    	var jsondata = eval("("+data+")");
    		    	$('#info_title').html();
                    $('#info_message').html();
                    $('#info_errdata').html();
                    $('#info_url').html();
    		    	if(jsondata.error == "0"){
    		    		$('#info_title').html("导出成功");
    		    		$('#info_url').html(jsondata.url);
    		    		$("#info_url").attr("href",jsondata.url);
    		    		$("#Info_button").click();//打开结果框
    		    	}else{
    		    		toastr["error"]("error", jsondata.message);
    		    	}
    		    },
    		    error: function () { 
    		    	toastr["error"]("error", "服务器异常");
    		    }  
    		});
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
    $('#file-pretty input[type="file"]').prettyFile();
});

function del_actual(pkid){
	var row_table = getSelectedRow_metTable();//获取选中的行
	if (typeof row_table != "undefined") {
		$.ajax({  		       
		    url: delData,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	pkid:pkid,
		    	table_name: row_table.name_en
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "数据已删除");
		    		swal("删除成功！", "您已经永久删除了这条信息。", "success");
		    		//销毁现有表格数据
		    		$actual.bootstrapTable('destroy');
		    		//重新初始化数据
		    		actual_initialization();
		    	}else{
		    		toastr["warning"]("", "删除失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
	}else{
		toastr["info"]("info", "必须选择一条记录");
	}
}

function up_actual(){
	var form_val = JSON.stringify(getFormJson("#upForm"));//表单数据字符串
	var row = getSelectedRow_actual();//获取选中的行
	var row_table = getSelectedRow_metTable();//获取选中的行
	if (typeof row != "undefined") {
		if (typeof row_table != "undefined") {
			$.ajax({  		       
			    url: upData,
			    type: "POST",
			    async:false,
			    dataType: "text",
			    data:{
			    	pkid:row.pkid,
			    	form_val:form_val,
			    	table_name: row_table.name_en
		        },
			    success: function (data) {
			    	if (data == "1") {
			    		toastr["success"]("", "记录已修改");
			    		//销毁现有表格数据
			    		$actual.bootstrapTable('destroy');
			    		//重新初始化数据
			    		actual_initialization();
			    		Refresh_actual();
			    	}else{
			    		toastr["warning"]("", "修改失败，检查数据后重试");
			    	}
			    },
			    error: function () { 
			    	toastr["error"]("error", "服务器异常");
			    }  
			});
			$("#close_up_button").click();
		}else{
			toastr["info"]("info", "必须选择一条记录");
			$("#close_up_button").click();
		}
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_up_button").click();
	}
}

function add_actual(){
	var row = getSelectedRow_metTable();//获取选中的行
	var form_val = JSON.stringify(getFormJson("#addForm"));//表单数据字符串
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: addData,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	form_val: form_val,
		    	table_name: row.name_en
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "新数据添加");
		    		//销毁现有表格数据
		    		$actual.bootstrapTable('destroy');
		    		//重新初始化数据
		    		actual_initialization();
		    		Refresh_actual();
		    	}else{
		    		toastr["warning"]("", "修改失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("error", "服务器异常");
		    }  
		});
		$("#close_add_button").click();
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_add_button").click();
	}
}

//主表数据初始化
function metTable_initialization(){
	$metTable.bootstrapTable({
		method: 'POST',
		height: "506",
		url: getMetadata_up_table_List,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
		showToggle: true,   //名片格式
		showRefresh: true,  //显示刷新按钮
		showColumns: true, //显示隐藏列  
		singleSelect: true,//复选框只能选择一条记录
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_metTable, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		columns: col(), //列
		iconSize: "outline",
        icons: {
            refresh: "glyphicon-repeat",
            toggle: "glyphicon-list-alt",
            columns: "glyphicon-list"
        },
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metTable.bootstrapTable('removeAll');
			toastr["info"]("info", "还是空表哦");
		},
		onClickRow: function (row, $element) {
			$("table#metTable_table .success").removeClass('success');
			$($element).addClass('success');
			$("#assistant").show();//从表显示
			
			$actual.bootstrapTable('destroy');
			actual_initialization();
		}
	});
}

//从表数据初始化
function actual_initialization(){
	$actual.bootstrapTable({
		method: 'POST',
		height: "506",
		url: getData_List,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
		showToggle: true,   //名片格式
		showColumns: true, //显示隐藏列  
		showRefresh: true,  //显示刷新按钮
		toolbar: "#Toolbar_col",
		iconSize: "outline",
        icons: {
            refresh: "glyphicon-repeat",
            toggle: "glyphicon-list-alt",
            columns: "glyphicon-list"
        },
		singleSelect: true,//复选框只能选择一条记录
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_actual, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		columns: col_col(), //列
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$actual.bootstrapTable('removeAll');
			toastr["info"]("info", "还是空表哦");
		},
		onClickRow: function (row, $element) {
			$("table#actual_table .success").removeClass('success');
			$($element).addClass('success');
		}
	});
}


//元数据表显示列
function col(){
	$.ajax({  		       
	    url: getMetadata_table_List,
	    type: "POST",
	    async:false,
	    dataType: "json",
	    success: function (data) {
	    	if (typeof data != "undefined") {
	    		var value_col = JSON.stringify(data);
	    		value_col = value_col.replace(/"false"/gm,'false');//将字符串中所有带双引号的true和false替换
	    		value_col = value_col.replace(/"true"/gm,'true');
	    		value_obj = jQuery.parseJSON(value_col);
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }
	});
	return value_obj;
}

//数据表列
function col_col(){
	var row = getSelectedRow_metTable();
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: getCol_List,
		    type: "POST",
		    async:false,
		    dataType: "json",
		    data:{
		    	table_name: row.name_en
	        },
		    success: function (data) {
		    	if (typeof data != "undefined") {
		    		var value_col = JSON.stringify(data);
		    		value_col = value_col.replace(/"false"/gm,'false');//将字符串中所有带双引号的true和false替换
		    		value_col = value_col.replace(/"true"/gm,'true');
		    		actual_table_obj = jQuery.parseJSON(value_col);
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
		generate_add_up();
		return actual_table_obj;
	}else{
		toastr["info"]("info", "必须选择一条记录");
	}
}

//生成数据结构的添加与修改页面
function generate_add_up(){
	var add = "<form class=\"form-horizontal\" id=\"addForm\">";
	var up = "<form class=\"form-horizontal\" id=\"upForm\">";
	for(var m = 0;m<actual_table_obj.length;m++){
		var col_val = actual_table_obj[m];
		if(col_val.field!="pkid"){
			add += "<div class=\"form-group\"><label class=\"col-sm-3 control-label\">"+col_val.field+"：</label><div class=\"col-sm-8\">";
			up += "<div class=\"form-group\"><label class=\"col-sm-3 control-label\">"+col_val.field+"：</label><div class=\"col-sm-8\">";
			if(col_val.data_type=="int(11)"){
				add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"\" name=\"add_"+col_val.field+"\" digits=\"true\">";
				up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"\" name=\"up_"+col_val.field+"\" digits=\"true\">";
			}else if(col_val.data_type=="float(5,2)"){
				add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"\" name=\"add_"+col_val.field+"\" number=\"true\">";
				up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"\" name=\"up_"+col_val.field+"\" number=\"true\">";
			}else{
				add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"\" name=\"add_"+col_val.field+"\">";
				up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"\" name=\"up_"+col_val.field+"\">";
			}
			add += "</div></div>";
			up += "</div></div>";
		}
	}
	add += "</form>";
	up += "</form>";
	$("#add_data_form").html(add);
	$("#up_data_form").html(up);
}

//刷新从表
function Refresh_actual(){
	$("#addForm").find("input").each(function(){
		  var id = $(this).attr("id");
		  $("#"+id).val("");
	});
	$("#addForm").find("textarea").each(function(){
		var id = $(this).attr("id");
		$("#"+id).val("")
	});
	$("#upForm").find("input").each(function(){
		  var id = $(this).attr("id");
		  $("#"+id).val("");
	});
	$("#upForm").find("textarea").each(function(){
		var id = $(this).attr("id");
		$("#"+id).val("")
	});
}


function queryParams_metTable(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    return temp;
}

function queryParams_actual(params) {  //配置参数
	var row = getSelectedRow_metTable();//子表的查询参数需要增加父表选中的内容
	if (typeof row != "undefined") {
		var temp = {};
		temp.pageSize = params.limit;
		temp.pageNumber = params.offset;
		temp.table_name = row.name_en;
		temp.search = params.search;
		return temp;
	}else{
		toastr["info"]("info", "必须选择一张数据表");
	}
}

//获取选中表格行数据
function getSelectedRow_metTable() {
    var index = $metTable.find('tr.success').data('index');
    return $metTable.bootstrapTable('getData')[index];
}

//获取选中数据行
function getSelectedRow_actual() {
    var index = $actual.find('tr.success').data('index');
    return $actual.bootstrapTable('getData')[index];
}

//将form中的值转换为键值对
//如：{Name:'摘取天上星',position:'IT技术'}
//ps:注意将同名的放在一个数组里
function getFormJson(form) {
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}