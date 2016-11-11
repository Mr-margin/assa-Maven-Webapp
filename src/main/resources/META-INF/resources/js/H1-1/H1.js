/**
 * 初始化贫困户信息、删除现有的贫困户信息。加载查询中的内容。(此js）
 */
$(document).ready(function(){
	document.getElementById("H1_li").setAttribute("class","active");//头部样式
	cx_html("1");//查询框中的HTML内容
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});//复选框样式
	getinfo_ready($("#cha_minzu"));//民族下拉框（查询）
	getinfo_ready($("#huzhu #v11"));//修改户主的民族
	getinfo_ready($("#jiatingchengyuan #v11"));//家庭成员民族
	getinfo_ready_guanxi($("#jiatingchengyuan #v10"));//与户主关系
	getinfo_ready_zaixiaosheng($("#jiatingchengyuan #v13"));//是否在校
	getinfo_ready_pinkunhushuxing($("#huzhu #v22"));//贫困户属性
	getinfo_ready_wenhuachengdu($("#jiatingchengyuan #v12"));//文化程度
	getinfo_ready_wenhuachengdu($("#huzhu #v12"));//户主文化程度
	getinfo_ready_zaixiaosheng($("#huzhu #v13"));//户主是否在校生
	getinfo_ready($('#cha_mz'));//民族
	getInfo_ready_zhipinyuanyin($('#cha_zpyy'));//致贫原因
	
	if(jsondata){//session中有内容，用户登录状态
		$("#nm_qx").val(jsondata.company.COM_NAME); //获取区域名称
		$("#pkid").attr("value",jsondata.company.PKID); //获取主键
		$("#com_level").attr("value",jsondata.company.COM_LEVEL); //获取层级
		
		chaxun.nm_qx = $("#nm_qx").val(); //获取区域名称
		chaxun.pkid = $("#pkid").attr("value"); //获取主键
		chaxun.com_level = $("#com_level").attr("value"); //获取层级
	}
	//点击输入框弹出 div
	$("#nm_qx").click(function (){
		if(temp=$("#treediv").is(":hidden")){
			$("#treediv").show();
		}else{
			$("#treediv").hide();
		}
	});
	//判断点击的点击的位置是否在窗体内
	$("#nm_qx,#treediv").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
		var   obj=$("#treediv")[0];
		$($("#treediv")).width($("#nm_qx").width()+12);
	});
	//点击div层以外的部分隐藏div
	$(document).click(function() {
		$("#treediv").hide();
	});
	
	
	
	if(jsondata.company_map.com_type=="单位"){
		var com_level = jsondata.company.com_level;
		
		if(com_level == "1"){
			
		}else if(com_level == "2"){
			$("#metTable_bxbxxb #v2").attr("data-visible","false");//盟市用户登录，隐藏盟市列
		}else if(com_level == "3"){
			$("#metTable_bxbxxb #v2").attr("data-visible","false");
			$("#metTable_bxbxxb #v3").attr("data-visible","false");
		}else if(com_level == "4"){
			$("#metTable_bxbxxb #v2").attr("data-visible","false");
			$("#metTable_bxbxxb #v3").attr("data-visible","false");
			$("#metTable_bxbxxb #v4").attr("data-visible","false");
		}else if(com_level == "5"){
			$("#metTable_bxbxxb #v2").attr("data-visible","false");
			$("#metTable_bxbxxb #v3").attr("data-visible","false");
			$("#metTable_bxbxxb #v4").attr("data-visible","false");
			$("#metTable_bxbxxb #v5").attr("data-visible","false");
		}
		
	}else if(jsondata.company_map.com_type=="管理员"){
		
	}else if(jsondata.company_map.com_type=="帮扶人"){
		
	}
	
	
	
//	if(jsondata.company_map.com_type=="单位"){
//		var type = jsondata.company_map.com_type;
//		var val = jsondata.company;
//		var qixian;
//		if(val.com_level == "1"){//市级单位
//			getinfo_xiqian($('#cha_qx'));
//			getinfo_xiqian($('#cha_qx_add'));
//		}else if(val.com_level == "2"){//县级单位
//			$("#metTable_bxbxxb #v3").attr("data-visible","false");
//			$('#cha_qx').html('<option  value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
//			getinfo_xiang(jsondata.company.xian,$('#cha_smx'));
//			
//			$('#cha_qx_add').html('<option  value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
//			getinfo_xiang(jsondata.company.xian,$('#cha_smx_add'));
//			chaxun.cha_qx = jsondata.company.xian;
//		}else if(val.com_level == "3"){//乡级单位
//			$("#metTable_bxbxxb #v3").attr("data-visible","false");
//			$("#metTable_bxbxxb #v4").attr("data-visible","false");
//			
//			$('#cha_qx').html('<option value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
//			$("#cha_smx").html('<option value="'+jsondata.company.xiang+'">'+jsondata.company.xiang+'</option>');
//			getinfo_cun(jsondata.company.xiang,$('#cha_gcc'));
//			
//			$('#cha_qx_add').html('<option value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
//			$("#cha_smx_add").html('<option value="'+jsondata.company.xiang+'">'+jsondata.company.xiang+'</option>');
//			getinfo_cun(jsondata.company.xiang,$('#cha_gcc_add'));
//			
//			chaxun.cha_qx = jsondata.company.xian;
//			chaxun.cha_smx = jsondata.company.xiang;
//		}else if(val.com_level == "4"){//村级单位
//			$("#metTable_bxbxxb #v3").attr("data-visible","false");
//			$("#metTable_bxbxxb #v4").attr("data-visible","false");
//			$("#metTable_bxbxxb #v5").attr("data-visible","false");
//			
//			$('#cha_qx').html('<option  value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
//			$("#cha_smx").html('<option value="'+jsondata.company.xiang+'">'+jsondata.company.xiang+'</option>');
//			$("#cha_gcc").html('<option value="'+jsondata.company.cun+'">'+jsondata.company.cun+'</option>');
//			
//			$('#cha_qx_add').html('<option  value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
//			$("#cha_smx_add").html('<option value="'+jsondata.company.xiang+'">'+jsondata.company.xiang+'</option>');
//			$("#cha_gcc_add").html('<option value="'+jsondata.company.cun+'">'+jsondata.company.cun+'</option>');
//			
//			chaxun.cha_qx = jsondata.company.xian;
//			chaxun.cha_smx = jsondata.company.xiang;
//			chaxun.cha_gcc = jsondata.company.cun;
//		}
//	}else if(jsondata.company_map.com_type=="管理员"){
//		getinfo_xiqian($('#cha_qx'));
//		getinfo_xiqian($('#cha_qx_add'));
//	}else{
//		$("#chauxn_quanxian").hide();
//	}
	
	$("#addForm").validate({//校验
	    onfocusout: function(element){//失去焦点
	        $(element).valid();//验证
	    }
	});
});

var metTable_bxbxxb = $('#metTable_bxbxxb');
var chaxun = {};//存储表格查询参数

$(function () {
	//查询按钮
    $('#cha_button').click(function () {
    	chaxunkuang("1");
    });
    
    //清空查询
    $('#close_cha_button').click(function () {
    	qingkong("1");
    });
	gachacun_initialization();
	$('#save_hu').click(function () {
		//提交表单进行验证
		var validator = $("#addForm").validate();
		if(validator.form()){
			add_hu($("#cha_qx_add").val(), $("#cha_smx_add").val(), $("#cha_gcc_add").val(), $("#huname_add").val());
		}
    });
	//删除贫困户按钮
	$('#del_hu').click(function () {
		var row = metTable_bxbxxb.bootstrapTable('getSelections');
		if (row.length>0 ) {
			var str = "";
			$.each(row,function(i,item){
				str += item.pkid+",";
 			});
			str = str.substring(0,str.length-1);
			
			swal({
	            title: "您确定要删除选定的贫困户吗？",
	            text: "这不是脱贫操作，删除后将无法恢复，请谨慎！",
	            type: "warning",
	            showCancelButton: true,
	            confirmButtonColor: "#DD6B55",
	            confirmButtonText: "删除",
	            closeOnConfirm: false
	        },
	        function() {
	            del_hu_st(str);
	        });
		}
    });
});
//删除贫困户信息
function del_hu_st(str){
	var data = ajax_async_t("getInput_3.do",{str:str},"text");
	if (data == "1") {
		toastr["success"]("success", "删除贫困户");
		swal("删除成功！", "您已经永久删除了这些贫困户。", "success");
		metTable_bxbxxb.bootstrapTable('destroy');// 销毁现有表格数据
		gachacun_initialization();// 重新初始化数据
	} else {
		toastr["warning"]("warning", "操作失败，检查数据后重试");
	}
}

//添加新的贫困户
function add_hu(qx,xaing,cun,huname){
	var data = ajax_async_t("getInput_2.do",{qx:qx,xaing:xaing,cun:cun,huname:huname},"text");
	if (data == "1") {
		toastr["success"]("success", "补充新贫困户");
		metTable_bxbxxb.bootstrapTable('destroy');//销毁现有表格数据
		gachacun_initialization();//重新初始化数据
		$("#huname_add").val("");
	}else{
		toastr["warning"]("warning", "操作失败，检查数据后重试");
    	}
	$("#close_hu").click();
}
//贫困户信息的初始化
function gachacun_initialization(){
	biaoge("getInput_1.do","597");
}
