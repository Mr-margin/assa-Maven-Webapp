$(function() {
//	document.getElementById("H5-1_li").setAttribute("class","active");//头部样式
	//树形结构
	$('#treeview').treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_tree(),
		onNodeSelected: function(e, o) {
			click_tree(o);
		}
	});
	//显示帮扶单位
	selectAll(1);
	//判断层级
	com_level=jsondata.company.com_level;//用户层级
	code=jsondata.company.com_code;//行政编码
	com_name=jsondata.company.com_name;//名称

	//标题赋值
	$('#title_name').html(com_name);
	shangmiandemingzi=com_name;
	//隐藏帮扶人页签
});

var code;//行政编码
var com_level;//层级
var com_name;//名称
var json_level;//获取区域id
var danwei_name;//选中的单位名字
var shangmiandemingzi="";
//显示帮扶单位列表
function selectAll(pkid){
	var teble_html="";
	if(pkid == undefined){
		pkid="null";
	}
	var data = ajax_async_t("selectAll.do", {
		pkid : pkid
	}, "json")
	if(data!=0){
		$.each(data, function(i,item) {
			teble_html +=
				"<tr>"+
				"<td  width='30%'><a onclick=\"kan_bfdw("+item.PKID+",'"+item.V1+"','"+item.V2+"','"+item.V3+"','"+item.V4+"','"+item.SYS_COMPANY_ID+"');\">"+item.V1+"</a></td>"+
				"<td  width='30%'>"+item.V3+"</td>"+
				"<td  width='40%' style='display:inline;'><button onclick=\"bfdw_xg("+item.PKID+",'"+item.V1+"','"+item.V2+"','"+item.V3+"','"+item.V4+"','"+item.SYS_COMPANY_ID+"');\"class='btn btn-info btn-xs btn-rounded' style='margin-top: 10px;margin-bottom: 0px;'>修改</button>&nbsp&nbsp"+
				"<button onclick=\"bfdw_sc("+item.PKID+","+pkid+",'"+item.V1+"')\" class='btn btn-danger btn-xs btn-rounded btn-outline' style='margin-top: 10px;margin-bottom: 0px;'>删除</button>&nbsp&nbsp"+
				"<button onclick=\"bfdw_ckbfr("+item.PKID+",'"+item.V1+"')\" class='btn btn-success btn-xs btn-rounded' style='margin-top: 10px;margin-bottom: 0px;'>查看帮扶人</button>"+
				"</td></tr>"
		});
	}else{
		toastr["warning"]("warning", "暂无帮扶单位");
	}
	$('#bfdw_tbody').html(teble_html)
}

//点击添加帮扶单位，出现填写框
function addbfdw_1(){
	$("#add_bfdw_Modal").modal();
}
//查看帮扶单位的详细信息
function kan_bfdw(pkid,v1,v2,v3,v4,sys_company_id){
	$("#kan_bfdw_mc").html(v1);
	$("#kan_bfdw_dz").html(v2);
	$("#kan_bfdw_ldxm").html(v3);
	$("#kan_bfdw_lddh").html(v4);
	$("#chakan_bfdw_title").html("帮扶单位-"+v1);
	$("#kan_bfdw_Modal").modal();
}
//添加帮扶单位方法
function addBfdw_2(){
	var bfdw_mc=$('#add_bfdw_mc').val();
	var bfdw_ldxm=$('#add_bfdw_ldxm').val();
	var bfdw_lddh=$('#add_bfdw_lddh').val();
	var	bfdw_dz=$('#add_bfdw_dz').val();
	if(json_level == undefined || json_level==null || json_level==""){
		json_level="1"
	}
	if(bfdw_mc==""){
		toastr["warning"]("warning", "帮扶单位名称不能为空");
	}else{
		//添加
		var data = ajax_async_t("addbfdw.do", {
			bfdw_mc : bfdw_mc,
			bfdw_ldxm : bfdw_ldxm,
			bfdw_lddh : bfdw_lddh,
			bfdw_dz : bfdw_dz,
			json_level : json_level
		}, "json");
		if (data == "1") {
			toastr["success"]("success", "添加帮扶单位完成");
			selectAll(json_level);
			quxiaotianjia_dw();
			$('#add_bfdw_Modal').modal('hide')
		}else{
			toastr["warning"]("warning", "操作失败，检查数据后重试");
		}
	}
}
//修改帮扶单位
function bfdw_xg(pkid,v1,v2,v3,v4,sys_company_id){
	$("#update_dw_id").val(pkid);
	$("#up_bfdw_mc").val(v1);
	$("#up_bfdw_dz").val(v2);
	$("#up_bfdw_ldxm").val(v3);
	$("#up_bfdw_lddh").val(v4);
	$("#xiugai_bfdw_title").html("修改帮扶单位-"+v1);
	$("#up_bfdw_Modal").modal();
}

//修改帮扶单位方法
function upBfdw_2(){
	var update_dw_id= $("#update_dw_id").val();
	var up_bfdw_mc= $("#up_bfdw_mc").val();
	var up_bfdw_dz= $("#up_bfdw_dz").val();
	var up_bfdw_ldxm= $("#up_bfdw_ldxm").val();
	var up_bfdw_lddh= $("#up_bfdw_lddh").val();
	if(json_level == undefined || json_level==null || json_level==""){
		json_level="1"
	}
	if(up_bfdw_mc ==""){
		toastr["warning"]("warning", "帮扶单位不能为空");
	}else{
		var data = ajax_async_t("xiugai_bfdw.do", {
			pkid : update_dw_id,
			bfdw_mc : up_bfdw_mc,
			bfdw_ldxm : up_bfdw_ldxm,
			bfdw_lddh : up_bfdw_lddh,
			bfdw_dz : up_bfdw_dz,
			json_level : json_level
		}, "json");
		if (data == "1") {
			toastr["success"]("success", "修改帮扶单位完成");
			selectAll(json_level);
			quxiaoxiugai_dw();
			$('#up_bfdw_Modal').modal('hide');
		}else{
			toastr["warning"]("warning", "修改失败，检查数据后重试");
		}
	}
}
//删除帮扶单位
function bfdw_sc(pkid,com_f_pkid,dwxm){
	var data='';
	var data_panduan=ajax_async_t("deleteBfdw_panduan.do", {pkid : pkid}, "json");
	if(data_panduan==0){//这个单位里没有帮扶人
		swal({
			title: "删除后将无法恢复，请谨慎！",
			text: "您确定要删除-"+dwxm+"-吗？",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#DD6B55",
			confirmButtonText: "删除",
			closeOnConfirm: false
		},
		function delete_bfdw(){
			data =  ajax_async_t("deleteBfdw.do", {
				pkid : pkid
			}, "json")
			if(data==1){
				swal("删除成功！", "您已经永久删除了-"+dwxm+"-", "success");
				selectAll(com_f_pkid);
			}else{
				toastr["warning"]("warning", "删除失败，检查数据后重试");
			}
		})
	}else if(data_panduan==1){
		toastr["warning"]("warning", "本单位已有帮扶干部，不能删除");
	}else{
		toastr["warning"]("warning", "网络异常，请稍后重试");
	}
	
}

//查看该单位下的帮扶责任人
function bfdw_ckbfr(da_company_id,danwei_name_1){
	$("#bfr_tbody").html("");
	$("#dw_pkid").val("");
	$("#dw_pkid").val(da_company_id);
	danwei_name = danwei_name_1;
	var title = $("#title_name").html();//重新为title赋值
	$("#title_name").html(title+"-"+danwei_name);
	
	$("#bfr_li").show();//打开帮扶人页签
	
	document.getElementById("bfr_a").click();//打开帮扶人页签
	var data = ajax_async_t("chakan_bfr.do", {da_company_id:da_company_id}, "json");
	var bfr_table="";
	if(data!=0){
		$.each(data, function(i,item) {
			bfr_table +=
				"<tr>"+
				"<td  width='30%'><a onclick=\"kan_bfr("+item.pkid+",'"+item.col_name+"','"+item.col_post+"','"+item.telephone+"','"+item.v1+"','"+item.v3+"','"+da_company_id+"');\">"+item.col_name+"</a></td>"+
				"<td  width='30%'>"+item.telephone+"</td>"+
				"<td  width='40%' style='display:inline;'><button onclick=\"bfr_xg("+item.pkid+",'"+item.col_name+"','"+item.col_post+"','"+item.telephone+"','"+item.v1+"','"+item.v3+"','"+da_company_id+"');\"class='btn btn-info btn-xs btn-rounded' style='margin-top: 10px;margin-bottom: 0px;'>修改</button>"+
				"&nbsp&nbsp<button onclick=\"bfr_sc("+item.pkid+",'"+item.col_name+"','"+item.da_company_id+"')\" class='btn btn-danger btn-rounded btn-outline btn-xs' style='margin-top: 10px;margin-bottom: 0px;'>删除</button>"+
				"</td>"+
				"</tr>"
		});
		$("#bfr_tbody").html(bfr_table);
	}else{
		toastr["warning"]("warning", "本单位下暂无帮扶人信息");
	}
}
//查看帮扶人
function kan_bfr(pkid,col_name,col_post,telephone,v1,v3,dw_pkid){
	$("#kan_col_name").html(col_name);
	$("#kan_v1").html(v1);
	$("#kan_telephone").html(telephone);
	$("#kan_v3").html(v3);
	$("#kan_col_post").html(col_post);
	$("#chakan_bfr_title").html(danwei_name+"-"+col_name);
	$('#kan_bfr_Modal').modal();
}
//添加帮扶人
function addbfr_1(){
	$("#add_bfr_title").html(danwei_name+"-添加帮扶干部");
	$("#add_bfr_Modal").modal();
}
//添加帮扶人方法
function add_Bfr_xinxi(){
	var col_name = $("#col_name").val();
	var v1 = $("#v1").val();
	var telephone = $("#telephone").val();
	var v3 = $("#v3").val();
	var col_post = $("#col_post").val();
	var dw_pkid = $("#dw_pkid").val();
	if(v1=="请选择"){
		v1="";
	}
	if(v3=="请选择"){
		v3=="";
	}
	if(col_name==""){
		toastr["warning"]("warning", "请填写帮扶干部姓名");
	}else if(telephone==""){
		toastr["warning"]("warning", "请填写帮扶干部电话");
	}else{
		var data = ajax_async_t("add_bfr.do", {
			col_name:col_name,
			v1:v1,
			telephone:telephone,
			v3:v3,
			col_post:col_post,
			dw_pkid:dw_pkid
			}, "json");
		if(data==1){
			toastr["success"]("success", "添加帮扶干部成功");
			quxiaotianjia_r();
			bfdw_ckbfr(dw_pkid);
			$('#add_bfr_Modal').modal('hide');
		}else{
			toastr["warning"]("warning", "请检查数据后重试");
		}
	}
}
//修改帮扶人
function bfr_xg(pkid,col_name,col_post,telephone,v1,v3,dw_pkid){
	if(v1==""){
		v1="请选择";
	}
	if(v3==""){
		v3=="请选择";
	}
	$("#up_r_pkid").val(pkid);
	$("#up_dw_pkid").val(dw_pkid);
	$("#up_col_name").val(col_name);
	$("#up_v1").val(v1);
	$("#up_telephone").val(telephone);
	$("#up_v3").val(v3);
	$("#up_col_post").val(col_post);
	$("#xiugai_bfr_title").html(danwei_name+"-"+col_name);
	$('#up_bfr_Modal').modal();
}
//修改帮扶人方法
function up_Bfr_xinxi(){
	var up_r_pkid = $("#up_r_pkid").val();
	var up_dw_pkid = $("#up_dw_pkid").val();
	var up_col_name = $("#up_col_name").val();
	var up_v1 = $("#up_v1").val();
	var up_telephone = $("#up_telephone").val();
	var up_v3 = $("#up_v3").val();
	var up_col_post = $("#up_col_post").val();
	if(json_level == undefined || json_level==null || json_level==""){
		json_level="1"
	}
	if(up_v1=="请选择"){
		up_v1="";
	}
	if(up_v3=="请选择"){
		up_v3==""
	}
	if(up_col_name ==""){
		toastr["warning"]("warning", "帮扶干部姓名不能为空");
	}else if(up_telephone == ""){
		toastr["warning"]("warning", "帮扶干部电话不能为空");
	}else{
		var data = ajax_async_t("xiugai_bfr.do", {
			pkid : up_r_pkid,
			col_name:up_col_name,
			v1:up_v1,
			telephone:up_telephone,
			v3:up_v3,
			col_post:up_col_post,
		}, "json");
		if (data == "1") {
			toastr["success"]("success", "修改帮扶人完成");
			quxiaoxiugai_r();
			bfdw_ckbfr(up_dw_pkid)
		}else{
			toastr["warning"]("warning", "操作失败，检查数据后重试");
		}
		$('#up_bfr_Modal').modal('hide');
	}
}
//删除帮扶人
function bfr_sc(pkid,bfrxm,bfdw_id){
	swal({
		title: "删除后将无法恢复，请谨慎！",
		text: "您确定要删除-"+bfrxm+"-吗？",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: "删除",
		closeOnConfirm: false
	},
	function delete_bfr(){
		var data =  ajax_async_t("deleteBfr.do", {
			pkid : pkid
		}, "json")
		if(data==1){
			swal("删除成功！", "您已经永久删除了-"+bfrxm+"-", "success");
			bfdw_ckbfr(bfdw_id);
		}else{
			toastr["warning"]("warning", "删除失败，检查数据后重试");
		}
	})
}

//节点点击事件
function click_tree(val){
	json_level=val.href;
	$("#bfr_li").hide();
	document.getElementById('bfdw_a').click(); 
	quxiaotianjia_dw();//取消添加单位
	quxiaoxiugai_dw();//取消修改单位
	quxiaotianjia_r();//取消添加帮扶人
	quxiaoxiugai_r();//取消修改帮扶人
	$('#title_name').html(val.xzqh);
	shangmiandemingzi=val.xzqh;
	selectAll(json_level);
}

//点击帮扶单位
function dianji_bfdw_button(){
	$("#chazhao_input").attr({"placeholder":"查找帮扶单位"});
	$("#chazhao_button").attr({"onclick":"chazhao_danwei();"});
	$("#addbfdw").attr({"onclick":"addbfdw_1();"});
	$("#addbfdw").html("添加帮扶单位");
	$("#title_name").html(shangmiandemingzi);
}
//点击帮扶人
function dianji_bfr_button(){
	$("#chazhao_input").attr({"placeholder":"查找帮扶人"});
	$("#chazhao_button").attr({"onclick":"chazhao_ren();"});
	$("#addbfdw").attr({"onclick":"addbfr_1();"});
	$("#addbfdw").html("添加帮扶人");
	$("#title_name").html(shangmiandemingzi+"-"+danwei_name);
}
//点击查找帮扶单位按钮
function chazhao_danwei(){
//	alert("单位");
}
//点击查找帮扶人按钮
function chazhao_ren(){
//	alert("人");
}
//取消-添加-帮扶单位
function quxiaotianjia_dw(){
	$('#add_bfdw_mc').val("");
	$('#add_bfdw_ldxm').val("");
	$('#add_bfdw_lddh').val("");
	$('#add_bfdw_dz').val("");
}

//取消-修改-帮扶单位
function quxiaoxiugai_dw(){
	$('#up_bfdw_mc').val("");
	$('#up_bfdw_ldxm').val("");
	$('#up_bfdw_lddh').val("");
	$('#up_bfdw_dz').val("");
}
//取消-查看-帮扶单位
function quxiaochakan_dw(){
	$('#kan_bfdw_mc').val("");
	$('#kan_bfdw_ldxm').val("");
	$('#kan_bfdw_lddh').val("");
	$('#kan_bfdw_dz').val("");
}
//取消-添加-帮扶人
function quxiaotianjia_r(){
	$("#col_name").val("");
	$("#v1").val("请选择");
	$("#telephone").val("");
	$("#v3").val("请选择");
	$("#col_post").val("");
	$("#dw_pkid").val("");
}
//取消-修改-帮扶人
function quxiaoxiugai_r(){
	$("#up_r_pkid").val("");
	$("#up_dw_pkid").val("");
	$("#up_col_name").val("");
	$("#up_v1").val("");
	$("#up_telephone").val("");
	$("#up_v3").val("");
	$("#up_col_post").val("");
}
//取消-修改-帮扶人
function quxiaochakan_r(){
	$("#kan_r_pkid").val("");
	$("#kan_dw_pkid").val("");
	$("#kan_col_name").val("");
	$("#kan_v1").val("");
	$("#kan_telephone").val("");
	$("#kan_v3").val("");
	$("#kan_col_post").val("");
}
//加载行政区划树
function show_tree(){//bootstrap tree 获取数据的方法 
//	var treeData;
//	var data = ajax_async_t("getTreeData.do",{},"json");
//	if (typeof data != "undefined") {
//		treeData = data;
//	}else{
//		toastr["error"]("error", "服务器异常");
//	}
//	return treeData;
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
