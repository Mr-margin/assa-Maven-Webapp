$(function() {
	$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
	
	$(':input').labelauty();
	
	//树形结构
	$('#treeview').treeview({
		color: "#428bca",	//设置列表树所有节点的前景色。
		//backColor: "#d1dade",//设置所有列表树节点的背景颜色
		onhoverColor: "#cdeaff",//设置列表树的节点在用户鼠标滑过时的背景颜色。
		selectedBackColor: "#1797f6",//设置被选择节点的背景颜色。
		
		expandIcon: "glyphicon glyphicon-chevron-right",
        collapseIcon: "glyphicon glyphicon-chevron-down",
        nodeIcon: "glyphicon glyphicon-map-marker",
        levels: 1,
		multiSelect:false,
		highlightSelected:true,
		showTags: !0,
		data: show_tree(),
		onNodeSelected: function(event, node) {
			dangqian_url = node.src;
			var fnode = $('#treeview').treeview('getParent', node);
			if($("#you_title_img").attr("src") != "img/"+fnode.png+".png"){
				$("#you_title_img").attr("src", "img/"+fnode.png+".png");
				$("#you_title_img").attr("title", fnode.title);
			}
			$('#iframez1').attr('src',"file/"+node.src+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
//	$('#treeview1').treeview({
//		color: "#428bca",
//		multiSelect: false,
//		highlightSelected: true,
//		data: show_SYS_COMPANY(),
//		onNodeSelected: function(e, o) {
//			clickpbl_tree(o);
//		}
//	});
//	
//	//点击输入框弹出 div
//	$("#nm_qx").click(function (){
//		if(temp=$("#treediv").is(":hidden")){
//			$("#treediv").show();
//		}else{
//			$("#treediv").hide();
//		}
//	});
//	//判断点击的点击的位置是否在窗体内
//	$("#nm_qx,#treediv").click(function(e) {
//		e?e.stopPropagation():event.cancelBubble = true;
//		var obj=$("#treediv")[0];
//		//$($("#treediv")).width($("#nm_qx").width()+12);
//		//$($("#treediv")).css('margin-left',"80px");
//	});
//	//点击div层以外的部分隐藏div
//	$(document).click(function() {
//		$("#treediv").hide();
//	});
	
	
	$("#fenge").click(function(e) {
		if($("#chaxuntiaojian").is(":hidden")){
			$("#fenge").html("【隐藏搜索区域】");
			$("#quyu1").css("height", "220px");
			$("#quyu2").css("height", "calc(100% - 268px)");
		}else{
			$("#fenge").html("【展开搜索区域】");
			$("#quyu1").css("height", "20px");
			$("#quyu2").css("height", "calc(100% - 78px)");
		}
		$("#chaxuntiaojian").toggle(500);
	});
	
	$("#p1").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.q1=='0'){
				tree_canshu.q1='1';
			}else{
				tree_canshu.q1='0';
			}
			p_zhuangtai = "0";
//			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+encodeURIComponent(JSON.stringify(tree_canshu)));
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	$("#p2").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.q2=='0'){
				tree_canshu.q2='1';
			}else{
				tree_canshu.q2='0';
			}
			p_zhuangtai = "0";
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	$("#p3").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.q3=='0'){
				tree_canshu.q3='1';
			}else{
				tree_canshu.q3='0';
			}
			p_zhuangtai = "0";
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	$("#p4").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.q4=='0'){
				tree_canshu.q4='1';
			}else{
				tree_canshu.q4='0';
			}
			p_zhuangtai = "0";
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	$("#p5").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.q5=='0'){
				tree_canshu.q5='1';
			}else{
				tree_canshu.q5='0';
			}
			p_zhuangtai = "0";
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	$("#t1").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.t1=='0'){
				tree_canshu.t1='1';
				tree_canshu.t2='0';
				$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
			}
			p_zhuangtai = "0";
		}
	});
	
	$("#t2").unbind("click").click(function(e) {
		if(p_zhuangtai=="0"){
			p_zhuangtai = "1";
		}else{
			if(tree_canshu.t2=='0'){
				tree_canshu.t2='1';
				tree_canshu.t1='0';
				$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
			}
			p_zhuangtai = "0";
		}
	});
//	//行政区划单位记录
//	show_SYS_COMPANY();
	
	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
		$("#v2").append("<option>全部盟市</option>");
		$("#v2").append("<option value='150100000000'>呼和浩特市</option>");
		$("#v2").append("<option value='150200000000'>包头市</option>");
		$("#v2").append("<option value='150700000000'>呼伦贝尔市</option>");
		$("#v2").append("<option value='152200000000'>兴安盟</option>");
		$("#v2").append("<option value='150500000000'>通辽市</option>");
		$("#v2").append("<option value='150400000000'>赤峰市</option>");
		$("#v2").append("<option value='152500000000'>锡林郭勒盟</option>");
		$("#v2").append("<option value='150900000000'>乌兰察布市</option>");
		$("#v2").append("<option value='150600000000'>鄂尔多斯市</option>");
		$("#v2").append("<option value='150800000000'>巴彦淖尔市</option>");
		$("#v2").append("<option value='150300000000'>乌海市</option>");
		$("#v2").append("<option value='152900000000'>阿拉善盟</option>");
	}else{
		$("#v2").append("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
		tree_canshu.com=jsondata.Login_map.COM_NAME;
		$("#v3").append("<option>全部旗区县</option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
		$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
	}
	
	//市级下拉框选择事件
	$("#v2").change(function(){
		$("#v3").empty();
		$("#v4").empty();
		$("#v5").empty();
		if($("#v2").find("option:selected").text()=="全部盟市"){
			level=1;//层级
			tree_canshu.com=$("#v2").find("option:selected").text();
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}else{
			level=2;//层级
			$("#v3").append("<option>全部旗区县</option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
			});
			tree_canshu.com=$("#v2").find("option:selected").text();
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	//县级下拉框选择事件
	$("#v3").change(function(){
		$("#v4").empty();
		$("#v5").empty();
		if($("#v3").find("option:selected").text() == '全部旗区县'){
			level=2;
			tree_canshu.com=$("#v2").find("option:selected").text();
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}else{
			level=3;//层级
			$("#v4").append("<option>全部苏木乡镇</option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
			});
			tree_canshu.com=$("#v3").find("option:selected").text();
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	//乡级下拉框选择事件
	$("#v4").change(function(){
		$("#v5").empty();
		if($("#v4").find("option:selected").text() == '全部苏木乡镇'){
			level=3
			tree_canshu.com=$("#v3").find("option:selected").text();
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}else{
			$("#v5").append("<option>请选择</option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:$("#v4").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v5").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
			});
			level=4;//层级
			tree_canshu.com=$("#v4").find("option:selected").text();
			$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		}
	});
	
	
	$("#v5").change(function(){
		tree_canshu.com=$("#v5").find("option:selected").text();
		$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
		level=5;//层级
	})
	
//	//清空
//	$("#qing_but").click(function(e) {
//		$("#v2").get(0).selectedIndex=0;
//		$("#v3").empty();
//		$("#v4").empty();
//		$("#v5").empty();
//		$("#biaoge").hide();
//		$("#guodu").show();
//	});
	  
	

//	//获取行政区划树
//	function show_SYS_COMPANY(){
//		if(jsondata.company_tree){
//			mycars = $.extend(true,{},jsondata.company_tree);
//		}else{
//			var user = JSON.parse(window.sessionStorage.getItem("user"));
//			if(user){
//				jsondata = eval("("+user+")");
//				mycars = $.extend(true,{},jsondata.company_tree);
//			}
//		}
//	}
});
var p_zhuangtai = "0";
var dangqian_url = "f_1_1_1.html";
var tree_canshu = {};
tree_canshu.com = "内蒙古自治区";
tree_canshu.year = "2016";
tree_canshu.q1 = "0";
tree_canshu.q2 = "0";
tree_canshu.q3 = "0";
tree_canshu.q4 = "0";
tree_canshu.q5 = "0";
tree_canshu.t1 = "0";
tree_canshu.t2 = "1";

//点击树触发方法
//function clickpbl_tree(val){
//
////	chaxun.nm_qx = val.text; //获取区域名称
////	chaxun.pkid = val.nodeId; //获取主键
////	chaxun.com_level = val.com_level; //获取层级
//	/*$("#com_level").attr({"value":val.com_level});
//	$("#pkid").attr({"value":val.href});*/
//	$('#nm_qx').val(val.text);
//	$("#treediv").hide(); 
//	tree_canshu.com = val.text;
//	
//	$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
//}

//function show_SYS_COMPANY(){//获取行政区划树
//	var mycars=[];
//	if(jsondata.company_tree){
//		mycars[0]= $.extend(true,{},jsondata.company_tree);
//	}else{
//		var user = JSON.parse(window.sessionStorage.getItem("user"));
//		if(user){
//			jsondata = eval("("+user+")");
//			mycars[0]= $.extend(true,{},jsondata.company_tree);
//		}
//	}
//	$.each(mycars[0].nodes,function(i,item){
//		//$.each(item.nodes,function(j,one){
//			delete item.nodes;
//		//})
//	});
//	return mycars;
//}

function show_tree(){//bootstrap tree 获取数据的方法 
	
	var mycars = [{
		text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>对象分析</span>",
        href: "#parent1",
        state: {
		    expanded: true
		},
		icon: "glyphicon glyphicon-folder-close",
		selectable: false,
		title: "对象分析",
        nodes: [{
        	text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>贫困人口</span>",
            href: "#parent1",
            state: {
    		    expanded: true
    		},
    		icon: "glyphicon glyphicon-folder-close",
    		selectable: false,
    		title: "贫困人口综合信息查询",
    		png: "z1_1",
    		tags: ["10"],
            nodes: [{
                text: "基本信息",
                state: {
                	selected: true
        		},
                src: "f_1_1_1.html"
            },
            {
                text: "年龄分组",
                src: "f_1_1_2.html"
            },
            {
                text: "身体健康情况",
                src: "f_1_1_3.html"
            },
            {
                text: "文化程度情况",
                src: "f_1_1_4.html"
            },
            {
                text: "劳动能力类型",
                src: "f_1_1_5.html"
            },
            {
                text: "上一年度务工情况",
                src: "f_1_1_6.html"
            },
            {
                text: "上一年度务工时间统计",
                src: "f_1_1_7.html"
            },
            {
                text: "在校生情况",
                src: "f_1_1_8.html"
            },
            {
                text: "贫困属性",
                src: "f_1_1_9.html"
            },
            {
                text: "适龄教育情况",
                src: "f_1_1_10.html"
            }]
        },
        {
            text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>贫困户</span>",
            href: "#parent2",
            icon: "glyphicon glyphicon-folder-close",
    		tags: ["15"],
    		selectable: false,
    		title: "贫困户综合信息查询",
    		png: "z1_2",
            nodes: [{
                text: "基本信息",
                src: "f_1_2_1.html"
            },
            {
                text: "主要致贫原因",
                src: "f_1_2_2.html"
            },
            {
                text: "其他致贫原因",
                src: "f_1_2_3.html"
            },
            {
                text: "帮扶责任人落实情况",
                src: "f_1_2_4.html"
            },
            {
                text: "生产生活条件",
                src: "f_1_2_5.html"
            },
            {
                text: "土地资源情况",
                src: "f_1_2_6.html"
            },
            {
                text: "人均收入分组情况",
                src: "f_1_2_7.html"
            },
            {
                text: "主要燃料类型",
                src: "f_1_2_8.html"
            },
            {
                text: "入户路情况",
                src: "f_1_2_9.html"
            },
            {
                text: "上年度家庭收入情况统计",
                src: "f_1_2_10.html"
            },
            {
                text: "上年度转移性收入构成情况",
                src: "f_1_2_11.html"
            },
            {
                text: "人口规模情况",
                src: "f_1_2_12.html"
            },
            {
                text: "党员情况",
                src: "f_1_2_13.html"
            },
            {
                text: "住房面积",
                src: "f_1_2_14.html"
            },
            {
                text: "与村主干路距离",
                src: "f_1_2_15.html"
            }]
        },
        {
            text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>贫困村</span>",
            href: "#parent3",
    		tags: ["3"],
    		icon: "glyphicon glyphicon-folder-close",
    		selectable: false,
    		title: "贫困村综合信息查询",
    		png: "z1_3",
            nodes: [{
                text: "基本信息",
                src: "f_1_3_1.html"
            },
            {
                text: "未脱贫贫困人口",
                src: "f_1_3_2.html"
            },
            {
                text: "贫困发生率",
                src: "f_1_3_3.html"
            }]
        }]
	},
	{
		text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>措施分析</span>",
        href: "#parent1",
		icon: "glyphicon glyphicon-folder-close",
		selectable: false,
		title: "措施分析",
        nodes: [{
        	text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>六个一批</span>",
            href: "#parent1",
    		icon: "glyphicon glyphicon-folder-close",
    		selectable: false,
    		title: "六个一批综合信息查询",
    		png: "z2_1",
    		tags: ["7"],
            nodes: [{
                text: "分类扶持措施",
                src: "f_2_1_1.html"
            },
            {
                text: "产业发展和转移就业扶持",
                src: "f_2_1_2.html"
            },
            {
                text: "易地扶贫搬迁",
                src: "f_2_1_3.html"
            },
            {
                text: "生态补偿",
                src: "f_2_1_4.html"
            },
            {
                text: "教育扶贫",
                src: "f_2_1_5.html"
            },
            {
                text: "大病救治",
                src: "f_2_1_6.html"
            },
            {
                text: "社会保障兜底",
                src: "f_2_1_7.html"
            }]
        },
        {
            text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>十项重点工作</span>",
            href: "#parent2",
            icon: "glyphicon glyphicon-folder-close",
    		tags: ["12"],
    		selectable: false,
    		title: "十项重点工作综合信息查询",
    		png: "z2_2",
            nodes: [{
                text: "危房改造情况",
                src: "f_2_2_1.html"
            },
            {
                text: "解决饮水安全",
                src: "f_2_2_2.html"
            },
            {
                text: "解决通电和农网改造",
                src: "f_2_2_3.html"
            },
            {
                text: "解决通广播电视和通讯(户)",
                src: "f_2_2_4.html"
            },
            {
                text: "新增养老",
                src: "f_2_2_5.html"
            },
            {
                text: "新增医保",
                src: "f_2_2_6.html"
            },
            {
                text: "新增低保",
                src: "f_2_2_7.html"
            },
            {
                text: "街巷硬化",
                src: "f_2_2_8.html"
            },
            {
                text: "校舍建设及改造",
                src: "f_2_2_9.html"
            },
            {
                text: "标准卫生室",
                src: "f_2_2_10.html"
            },
            {
                text: "文化活动室",
                src: "f_2_2_11.html"
            },
            {
                text: "便民超市",
                src: "f_2_2_12.html"
            }]
        },
        {
            text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>三到村三到户</span>",
            href: "#parent3",
    		tags: ["1"],
    		icon: "glyphicon glyphicon-folder-close",
    		selectable: false,
    		title: "三到村三到户综合信息查询",
    		png: "z2_3",
            nodes: [{
                text: "三到村三到户",
                src: "f_2_3_1.html"
            }]
        }]
	},
	{
		text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>成效分析</span>",
        href: "#parent1",
		icon: "glyphicon glyphicon-folder-close",
		selectable: false,
		title: "成效分析",
        nodes: [{
        	text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>帮扶责任人落实情况</span>",
            href: "#parent1",
    		icon: "glyphicon glyphicon-folder-close",
    		selectable: false,
    		title: "帮扶责任人落实情况综合信息查询",
    		png: "z3_1",
    		tags: ["2"],
            nodes: [{
                text: "帮扶责任人",
                src: "f_3_1_1.html"
            },
            {
                text: "帮扶户数",
                src: "f_3_1_2.html"
            }]
        },
        {
            text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>贫困户增收情况</span>",
            href: "#parent2",
            icon: "glyphicon glyphicon-folder-close",
    		tags: ["0"],
    		title: "贫困户增收情况综合信息查询",
    		png: "z3_2",
    		selectable: false
        },
        {
            text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>贫困户减贫情况</span>",
            href: "#parent3",
    		tags: ["0"],
    		icon: "glyphicon glyphicon-folder-close",
    		title: "贫困户减贫情况综合信息查询",
    		png: "z3_3",
    		selectable: false
        }]
	}];
	return mycars;
}



var level=1;//全局变量层级
function setSelVal(value){
	//console.log(value);
	 level+=1;
//	 console.log($("#v"+ level +"").html());
	 var t = document.getElementById("v"+ level +""); 
     for(i=0;i<t.length;i++){//给select赋值  
         if(value == t.options[i].text){  
        	 if(level=='2'){// 层级2
        		 t.options[i].selected=true;
        		 $("#v3").empty();
        		 $("#v4").empty();
        		 $("#v5").empty();
        		 if($("#v2").find("option:selected").text()=="全部盟市"){
        			 level=1;//层级
        			 tree_canshu.com=$("#v2").find("option:selected").text();
        			 $('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
        		 }else{
        			level=2;//层级
    				$("#v3").append("<option>全部旗区县</option>");
    				var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
    				var val = eval("("+data+")");
    				$.each(val,function(i,item){
    					$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
    				});
    				tree_canshu.com=$("#v2").find("option:selected").text();
    				$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
        		 }
        	 }else if(level == '3'){ //层级3
        		 t.options[i].selected=true;
        		 $("#v4").empty();
        		 $("#v5").empty();
        		 if($("#v3").find("option:selected").text() == '全部旗区县'){
        			 level=2;
        			 tree_canshu.com=$("#v2").find("option:selected").text();
        			 $('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
        		 }else{
        			level=3;//层级
    				$("#v4").append("<option>全部苏木乡镇</option>");
    				var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
    				var val = eval("("+data+")");
    				$.each(val,function(i,item){
    					$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
    				});
    				tree_canshu.com=$("#v3").find("option:selected").text();
    				$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
        		 }
        	 }else if(level == '4'){
        		 t.options[i].selected=true;
        		 if($("#v4").find("option:selected").text() == '全部苏木乡镇'){
        			 level=3
        			 tree_canshu.com=$("#v3").find("option:selected").text();
        			 $('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
        		 }else{
        			 $("#v5").empty();
         			 $("#v5").append("<option>请选择</option>");
        			 var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:$("#v4").find("option:selected").val()}, "text");
    				 var val = eval("("+data+")");
    				 $.each(val,function(i,item){
    					 $("#v5").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
    				 });
        			level=4;//层级
    				tree_canshu.com=$("#v4").find("option:selected").text();
    				$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
        		 }
        	 }else if(level == '5'){
        		 t.options[i].selected=true;
        		 level=4;
        		
        		 $("#myModal4").modal();
        		 
       		 	 show_pinkuncun($("#v5").find("option:selected").val());
       		 	 
        	 }
         }  
	}  
}

function show_pinkuncun(com_pkid){
	 $("#neirong").hide();
	 $("#exportExcel_all_dengdai_1").show();
	
	 $.ajax({                 
	        url: "../getWyApp_y3_1.do",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{pkid:com_pkid},
	        success: function (data) {
	        	
	        	$("#neirong").show();
	        	$("#exportExcel_all_dengdai_1").hide();
	        	var zpyy_data = "";
	        	$.each(data,function(i,item){ 
	        		var v22;
	        		if(item.v22=="一般贫困户"){
	        			v22 = "<span class=\"badge badge-success\">"+item.v22+"</span>";
	        		}else if(item.v22=="低保贫困户"){
	        			v22 = "<span class=\"badge badge-warning\">"+item.v22+"</span>";
	        		}else if(item.v22=="低收入农户"){
	        			v22 = "<span class=\"badge badge-primary\">"+item.v22+"</span>";
	        		}else if(item.v22=="低保户"){
	        			v22 = "<span class=\"badge badge-info\">"+item.v22+"</span>";
	        		}else if(item.v22=="五保户"){
	        			v22 = "<span class=\"badge badge-info\">"+item.v22+"</span>";
	        		}else{
	        			v22 = "<span class=\"badge\">暂无</span>";
	        		}
	            	zpyy_data += "<tr><td>"+(i+1)+"</td>";
	            	zpyy_data += "<td><a onclick=\"chakan_info('"+item.pkid+"','"+item.acid+"');\">"+item.v6+"</a></td><td>"+item.v21+"</td><td>"+v22+"</td><td>"+item.v23+"</td></tr>";
	        	});
	        	$("#zpyy").html(zpyy_data);
	        	}
	        })
	
}

//查看贫苦户的详细信息
function chakan_info(pkid,acid){
	//先清空
	$("#bfzrr_table").html('');
	$('#show_hz_phone').text("");//电话
	$('#show_hz_bank').text("");//开户银行
	$('#show_hz_banknum').text("");//银行卡号
	$('#show_hz_pkgsx').text("");//贫困户属性
	$('#show_hz_sfjls').text("");//是否军烈属
	$('#show_hz_dsznh').text("");//是否独生子女户
	$('#show_hz_snh').text("");//是否双女户
	$('#show_zyzp').text("");//主要致贫原因
	$('#show_qtzp').text("");//其他致贫原因
	$('#show_hz_sbbz').text("");//识别标准
	$("#show_gzxsr").text("");//工资性收入
	$("#show_jhsy").text("");//计划生育金
	$("#show_stbc").text("");//生态补偿金
	$("#show_scjyx").text("");//生产经营性收入
	$("#show_dbj").text("");//低保金
	$("#show_qtzy").text("");//其他转移性收入
	$("#show_ccxsr").text("");//财产性收入
	$("#show_wbj").text("");//五保金
	$("#shoow_zyxsr").text("");//转移性收入
	$("#show_ylbx").text("");//养老保险金
	$("#show_scjy").text("");//生产经营性支出
	$('#show_gdmj').text("");//耕地面积
	$('#show_ggmj').text("");//有效灌溉面积
	$('#show_ldmj').text("");//林地面积
	$('#show_tghlmj').text("");//退耕还林面积
	$('#show_lgmj').text("");//林果面积
	$('#show_mcdmj').text("");//牧草地面积
	$('#show_smmj').text("");//水面面积
	$('#show_sfscyd').text("");//是否通生产用电
	$('#show_shyd').text("");//是否通生活用电
	$('#show_zgljl').text("");//与村主干路距离
	$('#show_rullx').text("");//入户路类型
	$('#show_zfmj').text("");//住房面积
	$('#show_yskn').text("");//饮水是否困难
	$('#show_ysaq').text("");//饮水是否安全
	$('#show_zyrl').text("");//主要燃料类型
	$('#show_zyhzs').text("");//是否加入农民专业合作社
	$('#show_wscs').text("");//有无卫生厕所
	$('#show_sfbqh').text("");//是否搬迁户
	$('#show_bqfs').text("");//搬迁方式
	$('#show_azfs').text("");//安置方式
	$('#shoow_azd').text("");//安置地
	$('#show_czkn').text("");//搬迁可能存在的困难
	$("#yeqian").show();
	$("#yeqian ul>li:eq(0) a").trigger("click");//跳转第一个页签
	$("#neirong_jiben").hide();
	$("#myModal5").modal();
	$("#exportExcel_all_dengdai").show();
	$("#tablew4").hide();
	
	savePoorMessage(pkid,acid);
	//document.getElementById('yeqian').scrollIntoView();
	
}
var all_pkid;
function savePoorMessage(pkid,acid){ //acid 贫困户编号
	$("#myModal5").modal();
	all_pkid=pkid;
	var html="";
	 $.ajax({                 
	        url: "../getTz_1.do",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{pkid:pkid,acid:acid},
	        success: function (data) {
	        	//基本信息
	        	/*$('#show_hz_address3').text((data.data1[0].V3));
	        	$('#show_hz_address4').text((data.data1[0].V4)); 
	        	$('#show_hz_address5').text(data.data1[0].V5);*/ //地址
	        	
	        	$('#show_hz_phone').text(data.data1[0].V25);//电话
	        	$('#show_hz_bank').text(data.data1[0].V26);//开户银行
	        	$('#show_hz_banknum').text(data.data1[0].V27);//银行卡号
	        	$('#show_hz_pkgsx').text(data.data1[0].V22);//贫困户属性
	        	$('#show_hz_sfjls').text(data.data1[0].V29);//是否军烈属
	        	$('#show_hz_dsznh').text(data.data1[0].V30);//是否独生子女户
	        	$('#show_hz_snh').text(data.data1[0].V31);//是否双女户
	        	$('#show_zyzp').text(data.data1[0].V23);//主要致贫原因
	        	$('#show_qtzp').text(data.data1[0].V33);//其他致贫原因
	        	$('#show_hz_sbbz').text(data.data1[0].SYS_STANDARD);//识别标准
	        	
	        	//家庭成员
	        	var jtcy;
	        	
	        	/*jtcy+='<tr><td><code style="color: #000">1</code></td><td><code style="color: #000">'+data.data1[0].V6+'</code></td>';
	        	if(data.data1[0].V7==undefined||data.data1[0].V7==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V7+'</code></td>';
	        	}
	        	if(data.data1[0].V8==undefined||data.data1[0].V8==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V8+'</code></td>';
	        	}
	        	if(data.data1[0].V10==undefined||data.data1[0].V10==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V10+'</code></td>';
	        	}
	        	if(data.data1[0].V11==undefined||data.data1[0].V11==null){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V11+'</code></td>';
	        	}
//	        	jtcy+='<td><code style="color: #000">'+data.data1[0].v11+'</code></td>';
	        	if(data.data1[0].V28==null||data.data1[0].V28==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V28+'</code></td>';
	        	}
	        	
	        	if(data.data1[0].V12==null||data.data1[0].V12==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V12+'</code></td>';
	        	}
	        	if(data.data1[0].V13==null||data.data1[0].V13==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V13+'</code></td>';
	        	}
	        	if(data.data1[0].V14==null||data.data1[0].V14==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V14+'</code></td>';
	        	}
	        	if(data.data1[0].V15==null||data.data1[0].V15==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V15+'</code></td>';
	        	}
	        	if(data.data1[0].V16==null||data.data1[0].V16==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V16+'</code></td>';
	        	}
	        	if(data.data1[0].V17==null||data.data1[0].V17==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V17+'</code></td>';
	        	}
	        	if(data.data1[0].V32==null||data.data1[0].V32==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V32+'</code></td>';
	        	}	
	        	if(data.data1[0].V19==null||data.data1[0].V19==undefined){
	        		jtcy+='<td><code style="color: #000"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #000">'+data.data1[0].V19+'</code></td>';
	        	}	
	        	jtcy+='</tr>';*/
	        	if(data.data2==""||data.data2==null||data.data2==undefined){
	        		
	        	}else{
	        		var v6;var v7;var v8;var v10;var v11;var v12;var v13;var v14;var v28;var v15;var v16;var v17;var v32;var v19;
	        		
	        		$.each(data.data2,function(i,item){
	        			if(item.V6==null||item.V6==undefined){
	        				v6="";
	        			}else{
	        				v6=item.V6;
	        			}
	        			if(item.V7==null||item.V7==undefined){
	        				v7="";
	        			}else{
	        				v7=item.V7;
	        			}
	        			if(item.V8==null||item.V8==undefined){
	        				v8="";
	        			}else{
	        				v8=item.V8;
	        			}
	        			if(item.V10==null||item.V10==undefined){
	        				v10="";
	        			}else{
	        				v10=item.V10;
	        			}
	        			if(item.V11==null||item.V11==undefined){
	        				v11="";
	        			}else{
	        				v11=item.V11;
	        			}
	        			if(item.V12==null||item.V12==undefined){
	        				v12="";
	        			}else{
	        				v12=item.V12;
	        			}
	        			if(item.V13==null||item.V13==undefined){
	        				v13="";
	        			}else{
	        				v13=item.V13;
	        			}
	        			if(item.V14==null||item.V14==undefined){
	        				v14="";
	        			}else{
	        				v14=item.V14;
	        			}
	        			if(item.V15==null||item.V15==undefined){
	        				v15="";
	        			}else{
	        				v15=item.V15;
	        			}
	        			if(item.V16==null||item.V16==undefined){
	        				v16="";
	        			}else{
	        				v16=item.V16;
	        			}
	        			if(item.V17==null||item.V17==undefined){
	        				v17="";
	        			}else{
	        				v17=item.V17;
	        			}
	        			if(item.V19==null||item.V19==undefined){
	        				v19="";
	        			}else{
	        				v19=item.V19;
	        			}
	        			if(item.V28==null||item.V28==undefined){
	        				v28="";
	        			}else{
	        				v28=item.V28;
	        			}
	        			if(item.V32==null||item.V32==undefined){
	        				v32="";
	        			}else{
	        				v32=item.V32;
	        			}
	            		jtcy+='<tr><td><code style="color: #000">'+(i+1)+'</code></td><td><code style="color: #000">'+v6+'</code></td><td><code style="color: #000">'+v7+'</code></td><td><code style="color: #000">'+v8+'</code></td><td><code style="color: #000">'
	        	    			+v10+'</code></td></code></td><td><code style="color: #000">'+v11+'</code></td>'+
	        	    			'<td><code style="color: #000">'+v28+'</code></td><td><code style="color: #000">'+v12+'</code></td><td><code style="color: #000">'+v13+'</code></td><td><code style="color: #000">'+v14+'</code></td><td><code style="color: #000">'
	            				+v15+'</code></td>'+
	            				'<td><code style="color: #000">'+v16+'</code></td><td><code style="color: #000">'+v17+'</code></td><td><code style="color: #000">'+v32+'</code></td><td><code style="color: #000">'+v19+'</code></td></tr>';
	            		
	            	});
	            	
	        	}
	        	$("#jtcy_table").html(jtcy);
	        	
	        	//收入情况
	        	var v28;var v30;
	        	if(data.data5[0].V28==""||data.data5[0].V28==null){
	        		v28=0;
	        	}else{
	        		v28=data.data5[0].V28;
	        	}
	        	if(data.data5[0].V30==""||data.data5[0].V30==null){
	        		v30=0;
	        	}else{
	        		v30=data.data5[0].V30;
	        	}
	        	$("#show_gzxsr").text(v28+v30);//工资性收入
	        	$("#show_jhsy").text(data.data5[0].v41);//计划生育金
	        	$("#show_stbc").text(data.data5[0].v12);//生态补偿金
	        	$("#show_scjyx").text(data.data5[0].v10);//生产经营性收入
	        	$("#show_dbj").text(data.data5[0].v16);//低保金
	        	$("#show_qtzy").text(data.data5[0].v20);//其他转移性收入
	        	var v24;var v26;
	        	if(data.data5[0].V24==""||data.data5[0].V24==null){
	        		v24=0;
	        	}else{
	        		v24=data.data5[0].V24;
	        	}
	        	if(data.data5[0].V26==""||data.data5[0].V26==null){
	        		v26=0;
	        	}else{
	        		v26=data.data5[0].V26;
	        	}
	        	$("#show_ccxsr").text(v24+v26);//财产性收入
	        	$("#show_wbj").text(data.data5[0].V43);//五保金
	        	$("#shoow_zyxsr").text(data.data5[0].V22);//转移性收入
	        	$("#show_ylbx").text(data.data5[0].V14);//养老保险金
	        	/*var v2;var v4;var v6;var v8;var v10;var v12;var v14;var v16;var v18;var v20;
	        	if(data.data8[0].V2==""||data.data8[0].V2==null){
	        		v2=0;
	        	}else{
	        		v2=data.data8[0].V2
	        	}
	        	if(data.data8[0].V4==""||data.data8[0].V4==null){
	        		v4=0;
	        	}else{
	        		v4=data.data8[0].V4;
	        	}
	        	if(data.data8[0].V6==""||data.data8[0].V6==null){
	        		v6=0;
	        	}else{
	        		v6=data.data8[0].V6;
	        	}
	        	if(data.data8[0].V8==""||data.data8[0].V8==null){
	        		v8=0;
	        	}else{
	        		v8=data.data8[0].V8;
	        	}
	        	if(data.data8[0].V10==""||data.data8[0].V10==null){
	        		v10=0;
	        	}else{
	        		v10=data.data8[0].V10;
	        	}
	        	if(data.data8[0].V12==""||data.data8[0].V12==null){
	        		v12=0;
	        	}else{
	        		v12=data.data8[0].V12;
	        	}
	        	if(data.data8[0].V14==""||data.data8[0].V14==null){
	        		v14=0;
	        	}else{
	        		v14=data.data8[0].V14;
	        	}
	        	if(data.data8[0].V16==""||data.data8[0].V16==null){
	        		v16=0;
	        	}else{
	        		v16=data.data8[0].V16;
	        	}
	        	if(data.data8[0].V18==""||data.data8[0].V18==null){
	        		v18=0;
	        	}else{
	        		v18=data.data8[0].V18;
	        	}*/
	        	$("#show_scjy").text(data.data5[0].V44);//生产经营性支出
	        	
	        	
	        	//生产条件
	        	if(data.data3==""||data.data3==null||data.data3==undefined){
	        	}else{
	        		$('#show_gdmj').text(data.data3[0].V1);//耕地面积
	            	$('#show_ggmj').text(data.data3[0].V2);//有效灌溉面积
	            	$('#show_ldmj').text(data.data3[0].V3);//林地面积
	            	$('#show_tghlmj').text(data.data3[0].V4);//退耕还林面积
	            	$('#show_lgmj').text(data.data3[0].V13);//林果面积
	            	$('#show_mcdmj').text(data.data3[0].V5);//牧草地面积
	            	$('#show_smmj').text(data.data3[0].V14);//水面面积
	        	}
	        	//生活条件
	        	if(data.data4==""||data.data4==null||data.data4==undefined){
	        	}else{
	        		if(data.data4[0].V5==""||data.data4[0].V5==null||data.data4[0].V5=="-"||data.data4[0].V5==undefined){
	        			$('#show_sfscyd').text("否");//是否通生产用电
	        			$('#show_shyd').text("否");//是否通生活用电
	        		}else{
	        			$('#show_sfscyd').text("是");//是否通生产用电
	        			$('#show_shyd').text("是");//是否通生活用电
	        		}
	        		
	        		$('#show_zgljl').text(data.data4[0].V7);//与村主干路距离
	            	$('#show_rullx').text(data.data4[0].V6);//入户路类型
	            	$('#show_zfmj').text(data.data4[0].V1);//住房面积
	            	
	            	$('#show_yskn').text(data.data4[0].V8);//饮水是否困难
	            	$('#show_ysaq').text(data.data4[0].V9);//饮水是否安全
	            	$('#show_zyrl').text(data.data4[0].V10);//主要燃料类型
	            	$('#show_zyhzs').text(data.data4[0].V11);//是否加入农民专业合作社
	            	$('#show_wscs').text(data.data4[0].V12);//有无卫生厕所
	        	}
	        	
	        	/*//易地搬迁户需求
	        	if(data.data6==""||data.data6==null||data.data6==undefined){
	        	}else{
	        		$('#show_sfbqh').text(data.data6[0].VV3);//是否搬迁户
	        		$('#show_bqfs').text(data.data6[0].V1);//搬迁方式
	            	$('#show_azfs').text(data.data6[0].MOVE_TYPE);//安置方式
	            	$('#shoow_azd').text(data.data6[0].V2);//安置地
	            	$('#show_czkn').text(data.data6[0].V3);//搬迁可能存在的困难
	        	}
	        	//帮扶责任人
	        	var html1;
	        	var bcol_name="";var bv1 ="";var bv2="";var bv3="";var bcom_name="";var bv4="";var bv5=""; var bv6="";var bv7="";var btelephone="";
	        	$.each(data.data7,function(i,item){
	        			if(item.COL_NAME==undefined||item.COL_NAME==""||item.COL_NAME==null){
	        			}else{
	        				
	        				if(item.COL_NAME==null||item.COL_NAME==undefined){
	        					bcol_name="";
	        				}else{
	        					bcol_name=item.COL_NAME;
	        				}
	        				if(item.V1==null||item.V1==undefined){
	        					bv1="";
	        				}else{
	        					bv1=item.V1;
	        				}
	        				if(item.V2==null||item.V2==undefined){
	        					bv2="";
	        				}else{
	        					bv2=item.V2;
	        				}
	        				if(item.V3==null||item.V3==undefined){
	        					bv3="";
	        				}else{
	        					bv3=item.V3;
	        				}
	        				if(item.V4==null||item.V4==undefined){
	        					bv4="";
	        				}else{
	        					bv4=item.COL_NAME;///??????
	        				}
	        				if(item.V5==null||item.V5==undefined){
	        					bv5="";
	        				}else{
	        					bv5=item.V5;
	        				}
	        				if(item.V6==null||item.V6==undefined){
	        					bv6="";
	        				}else{
	        					bv6=item.V6;
	        				}
	        				if(item.V7==null||item.V7==undefined){
	        					bv7="";
	        				}else{
	        					bv7=item.V7;
	        				}
	        				if(item.COM_NAME==null||item.COM_NAME==undefined){
	        					bcom_name="";
	        				}else{
	        					bcom_name=item.COM_NAME;
	        				}
	        				if(item.TELEPHONE==null||item.TELEPHONE==undefined){
	        					btelephone="";
	        				}else{
	        					btelephone=item.TELEPHONE;
	        				}
	        				html1+='<tr><td><code style="color: #000">'+(i+1)+'</code></td><td><code style="color: #000">'+bcol_name+'</code></td>'+
	        					'<td><code style="color: #000">'+bv1+'</code></td>'+
	        					'<td><code style="color: #000">'+bv2+'</code></td>'+
	        					'<td><code style="color: #000">'+bv3+'</code></td>'+
	        					'<td><code style="color: #000">'+bcom_name+'</code></td>'+
	        					'<td><code style="color: #000">'+bv4+'</code></td>'+
	        					'<td><code style="color: #000">'+bv5+'</code></td>'+
	        					'<td><code style="color: #000">'+bv6+'</code></td>'+
	        					'<td><code style="color: #000">'+bv7+'</code></td>'+
	        					'<td><code style="color: #000">'+btelephone+'</code></td>'+
	        					'</tr>';
	        			}
	        	});*/
	        	//$("#bfzrr_table").html(html1);
	        	$("#neirong_jiben").show();
	        	$("#exportExcel_all_dengdai").hide();
	        	$("#tablew4").show();
	        },
	        error: function () { 
	            toastr["error"]("error", "服务器异常");
	        }  
	    });
}

//脱贫计划、帮扶措施、工作台账、贫困户收入监测表   
function showStanding(year,num){
	$("#show_tpv2_"+num).text("");
	$("#show_tpv3_"+num).text("");
	$("#show_tpv4_"+num).text("");
	$("#show_tpv5_"+num).text("");
	$("#show_tpv6_"+num).text("");
	$("#show_tpv7_"+num).text("");
	$("#show_tpv8_"+num).text("");
	$("#show_tpv9_"+num).text("");
	$("#show_tpv10_"+num).text("");
	$("#show_tpv11_"+num).text("");
	$("#show_tpv12_"+num).text("");
	$("#show_tpv13_"+num).text("");
	$("#show_tpv14_"+num).text("");
	$("#show_tpv15_"+num).text("");
	$("#show_tpv16_"+num).text("");
	$("#show_tpv17_"+num).text("");
	$("#show_tpv18_"+num).text("");
	$("#show_tpv19_"+num).text("");
	$("#bfcs_table_"+num).html("");
	$("#gztz_table_"+num).html("");
	if(all_pkid==""||all_pkid==null){
		return;
	}
	var data = ajax_async_t("../getTz_2.do",{pkid:all_pkid,year:year},"json");

	//脱贫计划
	if(data.data1==""||data.data1==null){
		
	}else{
		$("#show_tpv2_"+num).text(data.data1[0].v2);
    	$("#show_tpv3_"+num).text(data.data1[0].v3);
    	$("#show_tpv4_"+num).text(data.data1[0].v4);
    	$("#show_tpv5_"+num).text(data.data1[0].v5);
    	$("#show_tpv6_"+num).text(data.data1[0].v6);
    	$("#show_tpv7_"+num).text(data.data1[0].v7);
    	$("#show_tpv8_"+num).text(data.data1[0].v8);
    	$("#show_tpv9_"+num).text(data.data1[0].v9);
    	$("#show_tpv10_"+num).text(data.data1[0].v10);
    	$("#show_tpv11_"+num).text(data.data1[0].v11);
    	$("#show_tpv12_"+num).text(data.data1[0].v12);
    	$("#show_tpv13_"+num).text(data.data1[0].v13);
    	$("#show_tpv14_"+num).text(data.data1[0].v14);
    	$("#show_tpv15_"+num).text(data.data1[0].v15);
    	$("#show_tpv16_"+num).text(data.data1[0].v16);
    	$("#show_tpv17_"+num).text(data.data1[0].v17);
    	$("#show_tpv18_"+num).text(data.data1[0].v18);
    	$("#show_tpv19_"+num).text(data.data1[0].v19);
	}
	//帮扶措施
	try {
		if(data.data2==""||data.data2==null){
    		
    	}else{
    		var hj=0;var hjy=0;var v4;var v5;var v6;var v5_1;
        	var html;
    		$.each(data.data2,function(i,item){
    			hj=i+1;
    			if(item.v4==null||item.v4==undefined){
    				v4="";
    			}else{
    				v4=item.v4;
    			}
    			if(item.v5==null||item.v5==undefined||item.v5==""){
    				v5="";
    				v5_1="0"
    			}else{
    				v5=item.v5;
    				v5_1=item.v5;
    			}
    			if(item.v6==null||item.v6==undefined){
    				v6="";
    			}else{
    				v6=item.v6;
    			}
    			if(isNaN(parseInt(v5_1))){
    			}else{
    				hjy+=parseInt(v5_1);
    			}
    			
    			html+='<tr><th><code style="color: #000">'+(i+1)+'</code></th><th><code style="color: #000">'+item.v1+'<code style="color: #000"></th><th><code style="color: #000">'+item.v2+'</code></th><th><code style="color: #000">'+item.v3+'</code></th>'+
    			'<th><code style="color: #000">'+v4+'</code></th><th><code style="color: #000">'+v5+'</code></th><th><code style="color: #000">'+v6+'</code></th></tr>'
    		});
    		 html+='<tr><th style="text-align:center;"  colspan="4">合计</th><th><code style="color: #000">'+hj+'项</code></th><th><code style="color: #000">'+hjy+'元</code></th><th></th></tr>';
	        	$("#bfcs_table_"+num).html(html);
    	}
		
	} catch (e) {
		
	}
	//工作台账
	if(data.data3==""||data.data3==null){
	}else{
		var html1;
		$.each(data.data3,function(i,item){
			html1+='<tr><th><code style="color: #000">'+item.v6+'</code></th><th><code style="color: #000">'+item.v1+'</code></th><th><code style="color: #000">'+item.v2+'</code></th><th><code style="color: #000">'+item.v3+
			'</code></th><th><code style="color: #000">'+item.v4+'</code></th></tr>';
		});
		$("#gztz_table_"+num).html(html1);
	}

}