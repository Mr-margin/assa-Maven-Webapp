//点击树触发方法
function click_tree(val){

	$("#com_level").attr({"value":val.com_level});
	$("#pkid").attr({"value":val.href});
	$('#nm_qx').val(val.text);
	$("#treediv").hide(); 
}

//加载行政区划树
function show_tree(){//bootstrap tree 获取数据的方法 
	var mycars=[];
	if(jsondata.company_tree){
		mycars[0]= $.extend(true,{},jsondata.company_tree);
		$.each(mycars[0].nodes,function(i,item){
			$.each(item.nodes,function(j,one){
				delete one.nodes;
			})
		})
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
 * 公用表格信息
 * @param url
 * @param height
 */
function biaoge(url,height){
	metTable_bxbxxb.bootstrapTable({	
		method: 'POST',
//		height: height,
		url:url,
		dataType: "json",//从服务器返回的数据类型
		striped: true,	 //使表格带有条纹
		pagination: true,	//在表格底部显示分页工具栏
		pageSize: 10,	//页面大小
		pageNumber: 1,	//页数
		pageList: [10, 20, 50, 100],
		showToggle: true,   //名片格式
		showColumns: true, //显示隐藏列  
		toolbar: "#fpsc_update_tools",
		iconSize: "outline",
	    icons: {
	        refresh: "glyphicon-repeat",
	        toggle: "glyphicon-list-alt",
	        columns: "glyphicon-list"
	    },
		showRefresh: true,  //显示刷新按钮
		singleSelect: false,//复选框只能选择一条记录
		search: false,//是否显示右上角的搜索框
		clickToSelect: true,//点击行即可选中单选/复选框
		sidePagination: "server",//表格分页的位置 client||server
		queryParams: queryParams_bxbxxb, //参数
		queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		silent: true,  //刷新事件必须设置
		contentType: "application/x-www-form-urlencoded",	//请求远程数据的内容类型。
		onLoadError: function (data) {
			metTable_bxbxxb.bootstrapTable('removeAll');
			toastr["info"]("info", "没有找到匹配的记录");
		},
		onClickRow: function (row, $element) {
			$('.success').removeClass('success');
			$($element).addClass('success');
		}});
}
/**
 * 清空
 * @param arg
 */

function qingkong(arg){
	
	if(jsondata){//session中有内容，用户登录状态
		
		$("#nm_qx").val(jsondata.company.COM_NAME); //获取区域名称
		$("#pkid").attr("value",jsondata.company.PKID); //获取主键
		$("#com_level").attr("value",jsondata.company.COM_LEVEL); //获取层级
	}
	$("#cha_qx").val("请选择");
	$("#cha_smx").val("请选择");
	$("#cha_gcc").val("请选择");
	$("#cha_sbbz").val("请选择");
	$("#cha_pksx").val("请选择");
	$("#cha_zpyy").val("请选择");
	$("#cha_mz").val("请选择");
	$("#cha_renkou").val("请选择");
	$("#cha_bfdw").val("");
	$("#cha_bfzrr").val("");
	$("#cha_v6").val("");
	$("#cha_v8").val("");
	$("#cha_v8_1").val("请选择");
	$("#cha_banqian").val("请选择");
	$('#chauxnshiousuo').click();
	chaxun = {};
	if(arg="1"){
		$("#tab_jbqk").hide();//基本情况
		$("#tab_dqszh").hide();//当前收支
		$("#tab_bfdwyzrr").hide();//帮扶单位与责任人
		$("#tab_bfcsh").hide();//帮扶措施
		$("#tab_zfqk").hide();//帮扶成效
		$("#tab_bfcx").hide();//帮扶成效
		$("#tab_bfgshzhfx").hide();//帮扶后收支分析
	}else if(arg=="2"){
		$("#yeqian").hide();
	}
	metTable_bxbxxb.bootstrapTable('destroy');//销毁现有表格数据
	gachacun_initialization();//重新初始化数据
}
/**
 * 查询
 */
function chaxunkuang(arg){
	chaxun.nm_qx = $("#nm_qx").val(); //获取区域名称
	chaxun.pkid = $("#pkid").attr("value"); //获取主键
	chaxun.com_level = $("#com_level").attr("value"); //获取层级

	chaxun.cha_sbbz = $("#cha_sbbz").val();
	chaxun.cha_pksx = $("#cha_pksx").val();
	chaxun.cha_zpyy = $("#cha_zpyy").val();
	chaxun.cha_mz = $("#cha_mz").val();
	chaxun.cha_renkou = $("#cha_renkou").val();
	chaxun.cha_bfdw = $("#cha_bfdw").val();
	chaxun.cha_bfzrr = $("#cha_bfzrr").val();
	chaxun.cha_banqian = $("#cha_banqian").val();
	
	chaxun.cha_v6 = $("#cha_v6").val();
	chaxun.cha_v8 = $("#cha_v8").val();
	chaxun.cha_v8_1 = $("#cha_v8_1").val();
	
	if(arg=="1"){
		$("#tab_jbqk").hide();//基本情况
		$("#tab_dqszh").hide();//当前收支
		$("#tab_bfdwyzrr").hide();//帮扶单位与责任人
		$("#tab_bfcsh").hide();//帮扶措施
		$("#tab_zfqk").hide();//帮扶成效
		$("#tab_bfcx").hide();//帮扶成效
		$("#tab_bfgshzhfx").hide();//帮扶后收支分析
	}else if(arg=="2"){
		$("#yeqian").hide();
	}
	metTable_bxbxxb.bootstrapTable('destroy');//销毁现有表格数据
	gachacun_initialization();//重新初始化数据
}
/**
 * 参数
 * @param params
 * @returns {___anonymous3258_3259}
 */
function queryParams_bxbxxb(params){  //配置参数
	var temp = {};
    temp.pageSize = params.limit;
    temp.pageNumber = params.offset;
    temp.search = params.search;
    
    temp.com_level = chaxun.com_level;    
    temp.nm_qx = chaxun.nm_qx;
    temp.pkid = chaxun.pkid;

    temp.cha_sbbz = chaxun.cha_sbbz;
    temp.cha_pksx = chaxun.cha_pksx;
    temp.cha_zpyy = chaxun.cha_zpyy;
    temp.cha_mz = chaxun.cha_mz;
    temp.cha_renkou = chaxun.cha_renkou;
    temp.cha_bfdw = chaxun.cha_bfdw;
    temp.cha_bfzrr = chaxun.cha_bfzrr;
    temp.cha_banqian = chaxun.cha_banqian;
    
    temp.cha_v6 = chaxun.cha_v6;
    temp.cha_v8 = chaxun.cha_v8;
    temp.cha_v8_1 = chaxun.cha_v8_1;
	
    return temp;
}
/**
 * w4查询框
 */
function cxw4_html(id){

	var html=
		'<form class="form-horizontal" id="cha_Form">'+

			'<div class="form-group" style="margin-bottom: 0px" id="chauxn_quanxian">'+
				'<div class="col-sm-6">'+
					/*'<label class="col-sm-3 control-label" style="text-align:right;margin-top: 5px;margin-top: 5px;margin-left: -5%;">行政区划</label>'+*/
					'<div class="col-sm-9" style="width:80%;">'+
						'<div><label style="position: absolute;">行政区划</label><input type="text" placeholder="请选择行政区划" id="nm_qx" style="margin-bottom:0px;margin-left:80px;" class="form-control m-b" ></div>'+
						'<div id="treediv"  style="position:absolute;top:40px ;height:300px;overflow:scroll; -moz-border-radius: 10px; -webkit-border-radius: 10px;border-radius: 10px;   padding: 5px;background: #fff;color: #fff;border: 1px solid #cccccc;display: none; z-index: 999"  >'+	
							'<div id="treeview'+id+'" class="test"></div>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</div>'+
		'</form>';
	$("#cx_bg").html(html);
	
	$('#treeview'+id).treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_tree(),
		onNodeSelected: function(e, o) {
			clickw4_tree(o);
		}
	});
}
/**
 * W2查询框
 */
function W2_html(id){
	var html=
		'<form class="form-horizontal" id="cha_Form">'+

			'<div class="form-group" style="margin-bottom: 0px" id="chauxn_quanxian">'+
				'<div class="col-sm-6">'+
				'<div class="input-group col-sm-4">'+
	               '<input type="text" id="cha_v6" name="cha_v6" placeholder="贫困户户主姓名" class="input form-control">' +
	                '<span class="input-group-btn">'+
	               '<button type="button"  id="chapbl_button" name="chapbl_button" class="btn btn btn-primary"> <i class="fa fa-search"></i> 搜索</button>'+
	               '</span></div><br><div class="col-sm-8" id="xzqh_name"></div>'+
				'</div>'+
				'<div id="count" class="col-sm-6">'+
				'</div>'+
			'</div>'+
			'</div>'+
		'</form>';
	$("#cx_bg").html(html);
	
//	$('#treeview'+id).treeview({
//		color: "#428bca",
//		multiSelect: false,
//		highlightSelected: true,
//		data: show_tree(),
//		onNodeSelected: function(e, o) {
//			clickpbl_tree(o);
//		}
//	});
}
/**
 * W5查询框
 */
function cxpbl_html(id){
	var html=
		'<form class="form-horizontal" id="cha_Form">'+

			'<div class="form-group" style="margin-bottom: 0px" id="chauxn_quanxian">'+
				'<div class="col-sm-6">'+
					'<label class="col-sm-3 control-label" style="text-align:right;padding-top:0px;margin-top: 5px;font-size:15px;margin-top: 5px;margin-left: -5%;">行政区划</label>'+
					'<div class="col-sm-9" style="width:80%;">'+
						'<input type="text" placeholder="请选择行政区划" id="nm_qx" class="form-control m-b" > '+
						'<div id="treediv"  style="position:absolute;top:40px ;height:300px;overflow:scroll; -moz-border-radius: 10px; -webkit-border-radius: 10px;border-radius: 10px;   padding: 5px;background: #fff;color: #fff;border: 1px solid #cccccc;display: none; z-index: 999"  >'+	
							'<div id="treeview'+id+'" class="test"></div>'+
						'</div>'+
					'</div>'+
				'</div>'+
				'<div class="col-sm-2">'+
				'<div class="input-group">'+
	                '<span class="input-group-btn">'+
	               '<button type="button"  id="chapbl_button" name="chapbl_button" class="btn btn btn-primary"> <i class="fa fa-search"></i> 进入帮扶日记</button>'+
	               '</span></div>'+
				'</div>'+
			'</div>'+
			'</div>'+
		'</form>';
	$("#cx_bg").html(html);
	
	$('#treeview'+id).treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_tree(),
		onNodeSelected: function(e, o) {
			clickpbl_tree(o);
		}
	});
}
/**
 * 查询框
 */
function cx_html(id){

	var html=
		'<form class="form-horizontal" id="cha_Form">'+

			'<div class="form-group" style="margin-bottom: 0px" id="chauxn_quanxian">'+
				'<div class="col-sm-8">'+
					'<label class="col-sm-3 control-label" style="text-align:right;margin-top: 5px;margin-top: 5px;margin-left: -5%;">内蒙全区</label>'+
					'<div class="col-sm-9" style="width:80%;">'+
						'<input type="text" placeholder="请选择" id="nm_qx" class="form-control m-b" ></input><input id="com_level" style="display: none" value="'+jsondata.COM_LEVEL+'"></input><input id="pkid" style="display: none"></input> '+
						'<div id="treediv"  style="position:absolute;top:40px ;height:300px;overflow:scroll; -moz-border-radius: 10px; -webkit-border-radius: 10px;border-radius: 10px;   padding: 5px;background: #fff;color: #fff;border: 1px solid #cccccc;display: none; z-index: 999"  >'+	
							'<div id="treeview'+id+'" class="test"></div>'+
						'</div>'+
					'</div>'+
				'</div>'+
				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 5px;">识别标准</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_sbbz" name="cha_sbbz">'+
							'<option>请选择</option>'+
							'<option>国家级贫困人口</option>'+
							'<option>市级低收入人口</option>'+
						'</select>'+
					'</div>'+
				'</div>'+
			'</div>'+
			'<div class="form-group" style="margin-bottom: 0px">'+
				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 5px;">户主民族</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_mz" name="cha_mz">'+
						'</select>'+
					'</div>'+
				'</div>'+

				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 5px;">贫困户属性</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_pksx" name="znegjia_xb_0">'+
							'<option>请选择</option>'+
							'<option>一般贫困户</option>'+
							'<option>低保户</option>'+
							'<option>五保户</option>'+
							'<option>一般农户</option>'+
							'<option>低保贫困户</option>'+
							'<option>五保贫困户</option>'+
						'</select>'+
					'</div>'+
				'</div>'+

				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 5px;">主要致贫原因</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_zpyy" name="cha_zpyy">'+
							'<option>请选择</option>'+
							'<option>因病</option>'+
							'<option>因残</option>'+
							'<option>因学</option>'+
						'</select>'+
					'</div>'+
				'</div>'+

			'</div>'+
			'<div class="form-group" style="margin-bottom: 0px">'+

				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 3px;">户主姓名</label>'+
					'<div class="col-sm-7">'+
						'<input type="text" placeholder="请填写户主姓名" class="form-control" id="cha_v6" name="cha_v6">'+
					'</div>'+
				'</div>'+

				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 5px;">贫困户人口</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_renkou" name="cha_renkou">'+
							'<option>请选择</option>'+
							'<option>1人</option>'+
							'<option>2人</option>'+
							'<option>3人</option>'+
							'<option>4人</option>'+
							'<option>5人以上</option>'+
						'</select>'+
					'</div>'+
				'</div>'+

				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 3px;">脱贫属性</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_banqian" name="cha_banqian">'+
							'<option>请选择</option>'+
							'<option>返贫</option>'+
							'<option>未脱贫</option>'+
						'</select>'+
					'</div>'+
				'</div>'+

			'</div>'+
			'<div class="form-group" style="margin-bottom: 0px">'+

				
				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 3px;">身份证号</label>'+
					'<div class="col-sm-7">'+
						'<input type="text" placeholder="请填写身份证号" class="form-control" id="cha_v8" name="cha_v8">'+
					'</div>'+
				'</div>'+

				'<div class="col-sm-4">'+
					'<label class="col-sm-5 control-label" style="text-align:right;margin-top: 3px;">年龄范围</label>'+
					'<div class="col-sm-7">'+
						'<select class="form-control m-b" id="cha_v8_1" name="cha_v8_1">'+
							'<option>请选择</option>'+
							'<option>大于60岁</option>'+
							'<option>小于16岁</option>'+
							'<option>17岁至59岁</option>'+
						'</select>'+
					'</div>'+
				'</div>'+
				'<div class="col-sm-4">'+
					'<div class="col-sm-3"></div>'+
					'<div class="col-sm-9">'+
						'<button type="button" class="btn btn-primary" id="cha_button" name="cha_button" >'+
							'<i class="fa fa-search"></i>&nbsp;&nbsp;<span class="bold">查询</span>'+
						'</button>'+
						'<button type="button" class="btn btn-outline btn-warning pull-right" id="close_cha_button" name="close_cha_button" style="margin-right:50px;">'+
							'<i class="fa fa-remove"></i>&nbsp;&nbsp;<span class="bold">清空</span>'+
						'</button>'+
					'</div>'+
				'</div>'+
			'</div>'+
			
		'</form>';
	$("#cx_bg").html(html);
	
	$('#treeview'+id).treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_tree(),
		onNodeSelected: function(e, o) {
			click_tree(o);
		}
	});
}