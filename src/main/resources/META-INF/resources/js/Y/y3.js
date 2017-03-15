$(function() {
	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
	}else if(jsondata.Login_map.COM_VD=='V3'){ //市级权限数据
		tree_canshu.com=jsondata.Login_map.COM_NAME;
	}else if(jsondata.Login_map.COM_VD=='V5'){ //这是v5
		tree_canshu.com=jsondata.Login_map.COM_NAME;
	}
	
	
	$('#iframez1').attr('src',pinjie_str());
	
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
			$('#iframez1').attr('src',pinjie_str());
		}
	});
	
});
var p_zhuangtai = "0";
var dangqian_url = "f_1_1_1.html";
var shi="";
var xian="";
var xiang="";
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

//拼接字符串方法
function pinjie_str(){
	return "file/"+dangqian_url+"?com="+tree_canshu.com+"&year="+tree_canshu.year+"&q1="+tree_canshu.q1+"&q2="+tree_canshu.q2+"&q3="+tree_canshu.q3+"&q4="+tree_canshu.q4+"&q5="+tree_canshu.q5+"&t1="+tree_canshu.t1+"&t2="+tree_canshu.t2;
}



function show_tree(){//bootstrap tree 获取数据的方法 
	
	var mycars = [{
		text: "<span style='font-size: 16px;font-weight:900;text-shadow: #d1dade 0 1px 0;'>日记统计</span>",
        href: "#parent1",
        state: {
		    expanded: true
		},
		icon: "glyphicon glyphicon-folder-close",
		selectable: false,
		png: "z1_1",
        nodes: [{
            text: "行政区域统计图",
            state: {
            	selected: true
    		},
            src: "f_1_1_1.html"
        }]
	}];
	return mycars;
}
