$(function(){
	shuju(); //读取数据方法
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
});

//获取层级
function cj(com_level){
	$("#nm_qx").attr({"name":com_level});
}

//数据调用的方法
function shuju(){
	var data = get_sys_company();
	mydtree = new dTree('mydtree','img/','no','no');//创建树形结构
	$.each(data,function(i,item){ //循环遍历树形结构数值
		
			if(item.com_level == "1"){
				mydtree.add(0,-1,item.com_name,"javascript:setvalue('0','"+item.com_name+"')",item.com_name,"_self",false,item.com_level); //0是自己的id 而-1是需要层级id，类似于sys_company中的 com_f_pkid
			}else if(item.com_level == "2"){
				mydtree.add(item.pkid,0,item.com_name,"javascript:setvalue('"+item.pkid+"','"+item.com_name+"')",item.com_name,"_self",false,item.com_level);
			}else {
				mydtree.add(item.pkid,item.com_f_pkid,item.com_name,"javascript:setvalue('"+item.pkid+"','"+item.com_name+"')",item.com_name,"_self",false,item.com_level);
			}

	});
	document.getElementById("treediv").innerHTML=mydtree;//将样式付给treediv层
}

//生成弹出层的代码
//点击菜单树给文本框赋值------------------菜单树里加此方法
function setvalue(id,name){
	$("#nm_qx").val(name);
	$("#menu_parent").val(id);
	$("#treediv").hide(); 
}


//树形下拉框页面样式
//<div  class="col-sm-12">
//	<input type="text" id="menu_parent_name" class="form-control " > 
//	<input type="hidden" id="menu_parent" name="menu_parent"><!-- 父菜单id -->
//	<input type="hidden" id="oprate" name="oprate"><!-- radio -->
//	<input type="hidden" id="menu_id" name="menu_id"><!-- 父菜单id -->
//	<div id="treediv"   style="position:absolute;top:40px ;height:300px;overflow:scroll; -moz-border-radius: 10px; -webkit-border-radius: 10px;border-radius: 10px;   padding: 5px;background: #fff;color: #fff;border: 1px solid #cccccc;display: none;z-index: 999"  >			
//	</div>
//</div>