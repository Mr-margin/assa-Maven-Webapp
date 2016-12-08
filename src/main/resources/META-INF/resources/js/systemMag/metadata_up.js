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
var $metTable = $('#metTable');
var $metTable_col = $('#metTable_col');
var value_col_obj;//元数据表结构
var value_obj;//元数据表结构
$(function () {
    metTable_initialization();//初始化$metTable
    $("#assistant").hide();//从表隐藏
    
    
    //添加数据表保存按钮
    $('#add_button_table').click(function () {
    	//提交表单进行验证
		var validator = $("#addForm").validate();
		if(validator.form()){
			add_metTable_table();
		}
    });
    
    
    //添加元数据表内容保存按钮
    $('#add_button_col').click(function () {
    	//提交表单进行验证
		var validator = $("#addForm_col").validate();
		if(validator.form()){
			add_metTable_col();
		}
    });
    
    
    //修改数据表按钮，对模态窗口赋值并初始化
    $('#up_button_1').click(function () {
    	var row = getSelectedRow();//获取选中的行
    	if (typeof row != "undefined") {
    		$("#upForm").find("input").each(function(){
    			var id = $(this).attr("id");
  			  	for(var m = 0;m<value_obj.length;m++){
  			  		var col_val = value_obj[m];
  			  		if(id=="up_"+col_val.field){
  			  			$("#"+id).val(eval("row."+col_val.field));
  			  		}
  			  	}
	  		});
	  		$("#upForm").find("textarea").each(function(){
	  			var id = $(this).attr("id");
	  			for(var m = 0;m<value_obj.length;m++){
	  				var col_val = value_obj[m];
	  				if(id=="up_"+col_val.field){
	  					$("#"+id).val(eval("row."+col_val.field));
	  				}
	  			}
		  	});
	  		$("#upForm").find("select").each(function(){
	  			var id = $(this).attr("id");
	  			for(var m = 0;m<value_obj.length;m++){
	  				var col_val = value_obj[m];
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
    //修改已有数据表保存按钮
    $('#up_button_table').click(function () {
    	//提交表单进行验证
		var validator = $("#upForm").validate();
		if(validator.form()){
			up_metTable_table();
		}
    });
    
    
    
    //修改元数据表内容按钮，对模态窗口赋值并初始化
    $('#up_col_button_1').click(function () {
    	var row = getSelectedRow_col();//获取选中的行
    	if (typeof row != "undefined") {
    		$("#upForm_col").find("input").each(function(){
    			  var id = $(this).attr("id");
    			  
    			  for(var m = 0;m<value_col_obj.length;m++){
    				  var col_val = value_col_obj[m];
    				  if(id=="up_"+col_val.field+"_col"){
    					  $("#"+id).val(eval("row."+col_val.field));
    				  }
    			  }
    		});
    		$("#upForm_col").find("textarea").each(function(){
    			var id = $(this).attr("id");
	  			  
    			for(var m = 0;m<value_col_obj.length;m++){
    				var col_val = value_col_obj[m];
    				if(id=="up_"+col_val.field+"_col"){
    					$("#"+id).val(eval("row."+col_val.field));
    				}
    			}
	  		});
    		$("#upForm_col").find("select").each(function(){
    			var id = $(this).attr("id");
	  			  
    			for(var m = 0;m<value_col_obj.length;m++){
    				var col_val = value_col_obj[m];
    				if(id=="up_"+col_val.field+"_col"){
    					$("#"+id).val(eval("row."+col_val.field));
    				}
    			}
	  		});
    		$("#up_col_button_2").click();
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    //修改元数据表内容保存按钮
    $('#up_button_col').click(function () {
    	//提交表单进行验证
		var validator = $("#upForm_col").validate();
		if(validator.form()){
			up_metTable_col();
		}
    });
    
    
    
    //删除数据表按钮
    $('#del_button_table').click(function () {
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
	            del_metTable_table(row.pkid, row.name_en);
	        })
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    //删除元数据内容按钮
    $('#del_col_button').click(function () {
    	var row = getSelectedRow_col();//获取选中的行
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
	        	del_metTable_col(row.pkid, row.name_en);
	        })
    	}else{
    		toastr["info"]("info", "必须选择一条记录");
    	}
    });
    
    
    //检测手工导入表格
    $('#retrieval_button_table').click(function () {
    	retrieval_table();
    });
});

//检测方法
function retrieval_table(){
	$.ajax({  		       
	    url: getRetrieval_table,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    success: function (data) {
	    	if (data == "err") {
	    		toastr["warning"]("", "检索失败，检查数据后重试");
	    	}else if (data == "0") {
	    		toastr["info"]("info", "没有新数据");
	    	}else{
	    		toastr["success"]("", "识别   "+data+"  张新表");
	    		//销毁现有表格数据
	    		$metTable.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metTable_initialization();
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
}

//删除数据表方法
function del_metTable_table(pkid, table_name){
	$.ajax({  		       
	    url: delMetadata_up_table,
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
//删除元数据内容方法
function del_metTable_col(pkid, col_name){
	var row_table = getSelectedRow();//获取选中的行
	if (typeof row_table != "undefined") {
		$.ajax({  		       
		    url: delMetadata_up_col,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	pkid:pkid,
		    	col_name: col_name,
		    	table_name: row_table.name_en
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "数据已删除");
		    		swal("删除成功！", "您已经永久删除了这条信息。", "success");
		    		//销毁现有表格数据
		    		$metTable_col.bootstrapTable('destroy');
		    		//重新初始化数据
		    		metTable_col_initialization();
		    		document.getElementById('diermaodian').scrollIntoView();//定位锚点
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


//修改数据表方法
function up_metTable_table(){
	var form_val = JSON.stringify(getFormJson("#upForm"));//表单数据字符串
	var row = getSelectedRow();//获取选中的行
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: upMetadata_up_table,
		    type: "POST",
		    async:false,
		    dataType: "text",
		    data:{
		    	pkid:row.pkid,
		    	form_val:form_val,
		    	table_name:row.name_en
	        },
		    success: function (data) {
		    	if (data == "1") {
		    		toastr["success"]("", "记录已修改");
		    		//销毁现有表格数据
		    		$metTable.bootstrapTable('destroy');
		    		//重新初始化数据
		    		metTable_initialization();
		    		Refresh_table();
		    	}else{
		    		toastr["warning"]("", "修改失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
		$("#close_up_button_table").click();
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_up_button_table").click();
	}
}

//修改元数据表内容方法
function up_metTable_col(){
	var form_val = JSON.stringify(getFormJson("#upForm_col"));//表单数据字符串
	var row = getSelectedRow_col();//获取选中的行
	var row_table = getSelectedRow();//获取选中的行
	if (typeof row != "undefined") {
		if (typeof row_table != "undefined") {
			$.ajax({  		       
			    url: upMetadata_up_col,
			    type: "POST",
			    async:false,
			    dataType: "text",
			    data:{
			    	pkid:row.pkid,
			    	form_val:form_val,
			    	col_name: row.name_en,
			    	table_name: row_table.name_en
		        },
			    success: function (data) {
			    	if (data == "1") {
			    		toastr["success"]("", "记录已修改");
			    		//销毁现有表格数据
			    		$metTable_col.bootstrapTable('destroy');
			    		//重新初始化数据
			    		metTable_col_initialization();
			    		document.getElementById('diermaodian').scrollIntoView();//定位锚点
			    		Refresh_col();
			    	}else{
			    		toastr["warning"]("", "修改失败，检查数据后重试");
			    	}
			    },
			    error: function () { 
			    	toastr["error"]("error", "服务器异常");
			    }  
			});
			$("#close_up_button_col").click();
		}else{
			toastr["info"]("info", "必须选择一条记录");
			$("#close_up_button_col").click();
		}
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_up_button_col").click();
	}
}

//添加数据表方法
function add_metTable_table(){
	var form_val = JSON.stringify(getFormJson("#addForm"));//表单数据字符串
	$.ajax({  		       
	    url: addMetadata_up_table,
	    type: "POST",
	    async:false,
	    dataType: "text",
	    data:{
	    	form_val: form_val
        },
	    success: function (data) {
	    	if (data == "1") {
	    		toastr["success"]("", "新数据添加");
	    		//销毁现有表格数据
	    		$metTable.bootstrapTable('destroy');
	    		//重新初始化数据
	    		metTable_initialization();
	    		Refresh_table();
	    	}else{
	    		toastr["warning"]("", "修改失败，检查数据后重试");
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("", "服务器异常");
	    }  
	});
	$("#close_add_button_table").click();
}

//添加元数据表内容
function add_metTable_col(){
	var row = getSelectedRow();//获取选中的行
	var form_val = JSON.stringify(getFormJson("#addForm_col"));//表单数据字符串
	if (typeof row != "undefined") {
		$.ajax({  		       
		    url: addMetadata_up_col,
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
		    		$metTable_col.bootstrapTable('destroy');
		    		//重新初始化数据
		    		metTable_col_initialization();
		    		document.getElementById('diermaodian').scrollIntoView();//定位锚点
		    		Refresh_col();
		    	}else{
		    		toastr["warning"]("", "添加失败，检查数据后重试");
		    	}
		    },
		    error: function () { 
		    	toastr["error"]("", "服务器异常");
		    }  
		});
		$("#close_add_button_col").click();
	}else{
		toastr["info"]("info", "必须选择一条记录");
		$("#close_add_button_col").click();
	}
}


//刷新主表
function Refresh_table(){
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

//刷新从表
function Refresh_col(){
	$("#addForm_col").find("input").each(function(){
		  var id = $(this).attr("id");
		  $("#"+id).val("");
	});
	$("#addForm_col").find("textarea").each(function(){
		var id = $(this).attr("id");
		$("#"+id).val("")
	});
	$("#upForm_col").find("input").each(function(){
		  var id = $(this).attr("id");
		  $("#"+id).val("");
	});
	$("#upForm_col").find("textarea").each(function(){
		var id = $(this).attr("id");
		$("#"+id).val("")
	});
}

//控制分页的三个变量
var zhu_page = 0;//初始化的当前记录开始
var zhu_strat = true;//判断是否页面新打开
var zhu_s_or_q = false;
//主表数据初始化
function metTable_initialization(){
	zhu_s_or_q = true;
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
		showColumns: true, //显示隐藏列  
		showRefresh: true,  //显示刷新按钮
		toolbar: "#Toolbar_table",
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
		queryParams: queryParams, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		columns: col(), //列
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metTable.bootstrapTable('removeAll');
			toastr["info"]("info", "还是空表哦");
		},
		onClickRow: function (row, $element) {
			$("table#metTable .success").removeClass('success');
			$($element).addClass('success');
			$("#assistant").show();//从表显示
			$metTable_col.bootstrapTable('destroy');
			metTable_col_initialization();
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
					$metTable.bootstrapTable('selectPage',zhu_page);//让报个跳转到指定的页
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
//从表数据初始化
function metTable_col_initialization(){
	col_s_or_q = true;
	$metTable_col.bootstrapTable({
		method: 'POST',
		height: "506",
		url: getMetadata_up_col_List,
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
		search: false,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_col, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		columns: col_col(), //列
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			$metTable_col.bootstrapTable('removeAll');
			toastr["info"]("info", "还是空表哦");
		},
		onClickRow: function (row, $element) {
			var name_en = row.name_en;
			if(name_en!='pkid'){
				$("table#metTable_col .success").removeClass('success');
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
					$metTable_col.bootstrapTable('selectPage',col_page);//让报个跳转到指定的页
					col_s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
				}else{//单独点击分页
					col_page = data.page;//记录一下当前页数
				}
			}
			col_strat = false;
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
	    		//alert(data);
	    		var value_col = JSON.stringify(data);
	    		value_col = value_col.replace(/"false"/gm,'false');//将字符串中所有带双引号的true和false替换
	    		value_col = value_col.replace(/"true"/gm,'true');
	    		value_obj = jQuery.parseJSON(value_col);
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }
	});
	generate_add_up();
	return value_obj;
}

//生成元数据结构的添加与修改页面
function generate_add_up(){
	var add = "<form class=\"form-horizontal\" id=\"addForm\">";
	var up = "<form class=\"form-horizontal\" id=\"upForm\">";
	for(var m = 0;m<value_obj.length;m++){
		var col_val = value_obj[m];
		if(col_val.field!="pkid"){
			add += "<div class=\"form-group\"><label class=\"col-sm-3 control-label\">"+col_val.field+"：</label><div class=\"col-sm-8\">";
			up += "<div class=\"form-group\"><label class=\"col-sm-3 control-label\">"+col_val.field+"：</label><div class=\"col-sm-8\">";
			if(col_val.field=="name_en"){
				add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"\" name=\"add_"+col_val.field+"\" required=\"true\" aria-required=\"true\" isZipCode=\"true\">";
				up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"\" name=\"up_"+col_val.field+"\" required=\"true\" aria-required=\"true\" isZipCode=\"true\">";
			}else if(col_val.field=="name_cn"){
				add += "<textarea id=\"add_"+col_val.field+"\" placeholder=\""+col_val.title+"\" name=\"add_"+col_val.field+"\" class=\"form-control\" required=\"true\" aria-required=\"true\"></textarea>";
				up += "<textarea id=\"up_"+col_val.field+"\" placeholder=\""+col_val.title+"\" name=\"up_"+col_val.field+"\" class=\"form-control\" required=\"true\" aria-required=\"true\"></textarea>";
			}else if(col_val.field=="data_type"){
				add += "<select class=\"form-control m-b\" id=\"add_"+col_val.field+"\" name=\"add_"+col_val.field+"\"><option>varchar(2000)</option><option>varchar(200)</option><option>int(11)</option><option>float(5,2)</option></select>";
				up += "<select class=\"form-control m-b\" id=\"up_"+col_val.field+"\" name=\"up_"+col_val.field+"\"><option>varchar(2000)</option><option>varchar(200)</option><option>int(11)</option><option>float(5,2)</option></select>";
			}else{
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
			}
			add += "</div></div>";
			up += "</div></div>";
		}
	}
	add += "</form>";
	up += "</form>";
	$("#add_Modal_form").html(add);
	$("#up_Modal_form").html(up);
}

//元数据表显示列
function col_col(){
	$.ajax({  		       
	    url: getMetadata_up_List,
	    type: "POST",
	    async:false,
	    dataType: "json",
	    success: function (data) {
	    	if (typeof data != "undefined") {
	    		//alert(data);
	    		var value_col = JSON.stringify(data);
	    		value_col = value_col.replace(/"false"/gm,'false');//将字符串中所有带双引号的true和false替换
	    		value_col = value_col.replace(/"true"/gm,'true');
	    		value_col_obj = jQuery.parseJSON(value_col);
	    	}
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }
	});
	generate_add_up_col();
	return value_col_obj;
}

//生成元数据结构的添加与修改页面
function generate_add_up_col(){
	var add = "<form class=\"form-horizontal\" id=\"addForm_col\">";
	var up = "<form class=\"form-horizontal\" id=\"upForm_col\">";
	for(var m = 0;m<value_col_obj.length;m++){
		var col_val = value_col_obj[m];
		if(col_val.field!="table_name"&&col_val.field!="pkid"){
			add += "<div class=\"form-group\"><label class=\"col-sm-3 control-label\">"+col_val.field+"：</label><div class=\"col-sm-8\">";
			up += "<div class=\"form-group\"><label class=\"col-sm-3 control-label\">"+col_val.field+"：</label><div class=\"col-sm-8\">";
			if(col_val.field=="name_en"){
				add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"_col\" name=\"add_"+col_val.field+"_col\" required=\"true\" aria-required=\"true\" isZipCode=\"true\">";
				up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"_col\" name=\"up_"+col_val.field+"_col\" required=\"true\" aria-required=\"true\" isZipCode=\"true\">";
			}else if(col_val.field=="name_cn"){
				add += "<textarea id=\"add_"+col_val.field+"_col\" placeholder=\""+col_val.title+"\" name=\"add_"+col_val.field+"_col\" class=\"form-control\" required=\"true\" aria-required=\"true\"></textarea>";
				up += "<textarea id=\"up_"+col_val.field+"_col\" placeholder=\""+col_val.title+"\" name=\"up_"+col_val.field+"_col\" class=\"form-control\" required=\"true\" aria-required=\"true\"></textarea>";
			}else if(col_val.field=="data_type"){
				add += "<select class=\"form-control m-b\" id=\"add_"+col_val.field+"_col\" name=\"add_"+col_val.field+"_col\"><option>varchar(2000)</option><option>varchar(200)</option><option>int(11)</option><option>float(5,2)</option></select>";
				up += "<select class=\"form-control m-b\" id=\"up_"+col_val.field+"_col\" name=\"up_"+col_val.field+"_col\"><option>varchar(2000)</option><option>varchar(200)</option><option>int(11)</option><option>float(5,2)</option></select>";
			}else{
				if(col_val.data_type=="int(11)"){
					add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"_col\" name=\"add_"+col_val.field+"_col\" digits=\"true\">";
					up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"_col\" name=\"up_"+col_val.field+"_col\" digits=\"true\">";
				}else if(col_val.data_type=="float(5,2)"){
					add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"_col\" name=\"add_"+col_val.field+"_col\" number=\"true\">";
					up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"_col\" name=\"up_"+col_val.field+"_col\" number=\"true\">";
				}else{
					add += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"add_"+col_val.field+"_col\" name=\"add_"+col_val.field+"_col\">";
					up += "<input type=\"text\" placeholder=\""+col_val.title+"\" class=\"form-control\" id=\"up_"+col_val.field+"_col\" name=\"up_"+col_val.field+"_col\">";
				}
			}
			add += "</div></div>";
			up += "</div></div>";
		}
	}
	add += "</form>";
	up += "</form>";
	$("#add_col_Modal_form").html(add);
	$("#up_col_Modal_form").html(up);
}


function queryParams(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;//页面大小
    temp.pageNumber = params.offset;//页码
    temp.search = params.search;//搜索条件
    //sort: params.sort,  //排序列名
    //sortOrder: params.order//排位命令（desc，asc）
    return temp;
}

function queryParams_col(params) {  //配置参数
	var row = getSelectedRow();//子表的查询参数需要增加父表选中的内容
	if (typeof row != "undefined") {
		var temp = {};
		temp.pageSize = params.limit;
		temp.pageNumber = params.offset;
//		if(col_strat){//页面打开
//			col_page = params.offset;//初始化和分页的时候都记录一下要显示的当前记录开始
//			s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
//		}else{//通过其他方法刷新，分页，或强制刷新
//			if(s_or_q){//表格整体刷新，用于刷新数据
//				temp.pageNumber = col_page;//强制刷新时，用记录的分页记录传到后台，保持显示内容与刷新前一致
//				s_or_q = false;//除非表格整体刷新，否则此状态一直保持非
//			}else{//单独点击分页
//				col_page = params.offset;//初始化和分页的时候都记录一下要显示的当前记录开始
//			}
//		}
//		col_strat = false;
		temp.table_name = row.name_en;
		return temp;
	}else{
		toastr["info"]("info", "必须选择一张数据表");
	}
}

//获取选中行数据
function getSelectedRow() {
    var index = $metTable.find('tr.success').data('index');
    return $metTable.bootstrapTable('getData')[index];
}

//获取选中行数据
function getSelectedRow_col() {
    var index = $metTable_col.find('tr.success').data('index');
    return $metTable_col.bootstrapTable('getData')[index];
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