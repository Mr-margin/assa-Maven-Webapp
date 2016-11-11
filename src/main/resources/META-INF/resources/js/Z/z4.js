$(function() {
	$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
	
	//头部样式
	$("#caidanul",window.parent.document).children().removeClass("active");
	$("#z4_li",window.parent.document).addClass("active");
	
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
	
	$('#treeview1').treeview({
		color: "#428bca",
		multiSelect: false,
		highlightSelected: true,
		data: show_SYS_COMPANY(),
		onNodeSelected: function(e, o) {
			clickpbl_tree(o);
		}
	});
	
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
		var obj=$("#treediv")[0];
		//$($("#treediv")).width($("#nm_qx").width()+12);
		//$($("#treediv")).css('margin-left',"80px");
	});
	//点击div层以外的部分隐藏div
	$(document).click(function() {
		$("#treediv").hide();
	});
	
	
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
});

var p_zhuangtai = "0";
var dangqian_url = "f_3_1_1.html";
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
function clickpbl_tree(val){

//	chaxun.nm_qx = val.text; //获取区域名称
//	chaxun.pkid = val.nodeId; //获取主键
//	chaxun.com_level = val.com_level; //获取层级
	/*$("#com_level").attr({"value":val.com_level});
	$("#pkid").attr({"value":val.href});*/
	$('#nm_qx').val(val.text);
	$("#treediv").hide(); 
	tree_canshu.com = val.text;
	$('#iframez1').attr('src',"file/"+dangqian_url+"?canshu="+JSON.stringify(tree_canshu));
}

function show_SYS_COMPANY(){//获取行政区划树
	var mycars=[];
	if(jsondata.company_tree){
		mycars[0]= $.extend(true,{},jsondata.company_tree);
	}else{
		var user = JSON.parse(window.sessionStorage.getItem("user"));
		if(user){
			jsondata = eval("("+user+")");
			mycars[0]= $.extend(true,{},jsondata.company_tree);
		}
	}
	$.each(mycars[0].nodes,function(i,item){
		//$.each(item.nodes,function(j,one){
			delete item.nodes;
		//})
	});
	return mycars;
}

function show_tree(){//bootstrap tree 获取数据的方法 
	
	var mycars = [{
        text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>帮扶责任人落实情况</span>",
        href: "#parent1",
        state: {
		    expanded: true
		},
		icon: "glyphicon glyphicon-folder-close",
		selectable: false,
		title: "帮扶责任人落实情况综合信息查询",
		png: "z3_1",
		tags: ["2"],
        nodes: [{
            text: "帮扶责任人",
            state: {
            	selected: true
    		},
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
    }];
	return mycars;
}