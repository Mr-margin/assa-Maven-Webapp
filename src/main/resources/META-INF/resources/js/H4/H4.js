
var metTable_g = $('#metTable_g');
var metTable_s = $('#metTable_s');
$(function () {
	document.getElementById("H4_li").setAttribute("class","active");//头部样式
	guoding_initialization();
	shiding_initialization();
	
	$("#tuichu_g").click(function(){
		toastr["info"]("info", "退出功能暂不开放");
		var row = metTable_g.bootstrapTable('getSelections');
		if (row.length>0 ) {
			var str = "";
			$.each(row,function(i,item){
				str += item.pkid+",";
 			});
			str = str.substring(0,str.length-1);
//			alert(str);
		}
		
	});
	
	$("#tuichu_s").click(function(){
		toastr["info"]("info", "退出功能暂不开放");
		var row = metTable_s.bootstrapTable('getSelections');
		if (row.length>0 ) {
			var str = "";
			$.each(row,function(i,item){
				str += item.pkid+",";
 			});
			str = str.substring(0,str.length-1);
//			alert(str);
		}
		
	});
});

//国定标准
function guoding_initialization(){

	metTable_g.bootstrapTable({
		method: 'POST',
//		height: "850",
		url: "getSbtc_Country_init.do",
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 20,	//页面大小
		pageNumber: 1,	//页数
		pageList: [20, 50, 100],
		showToggle: true,   //名片格式
		showColumns: true, //显示隐藏列  
		toolbar: "#exampleToolbar_g",
		iconSize: "outline",
        icons: {
            refresh: "glyphicon-repeat",
            toggle: "glyphicon-list-alt",
            columns: "glyphicon-list"
        },
		showRefresh: true,  //显示刷新按钮
		singleSelect: false,//复选框只能选择一条记录
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
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

function queryParams_g(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    
    return temp;
}

//市定标准
function shiding_initialization(){

	metTable_s.bootstrapTable({
		method: 'POST',
//		height: "790",
		url: "getSbtc_City_init.do",
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 20,	//页面大小
		pageNumber: 1,	//页数
		pageList: [20, 50, 100],
		showToggle: true,   //名片格式
		showColumns: true, //显示隐藏列  
		toolbar: "#exampleToolbar_s",
		iconSize: "outline",
        icons: {
            refresh: "glyphicon-repeat",
            toggle: "glyphicon-list-alt",
            columns: "glyphicon-list"
        },
		showRefresh: true,  //显示刷新按钮
		singleSelect: false,//复选框只能选择一条记录
		search: true,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_s, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			metTable_s.bootstrapTable('removeAll');
			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}
	});
}

function queryParams_s(params) {  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    
    return temp;
}