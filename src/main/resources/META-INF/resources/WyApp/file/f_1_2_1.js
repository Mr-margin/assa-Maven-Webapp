$(document).ready(function(){
	code='150';
	type = "index";//首页判断 点击全部的时候
	str = "type";
	//日期区间初始化
	$("#bdate .input-daterange").datepicker({keyboardNavigation:!1,forceParse:!1,autoclose:!0});
	
	title='走访记录情况统计';
	if(parent.shi!=''){
		title=title+" ("+parent.shi;
		if(parent.xian!=''){
			
			title=title +"-"+parent.xian;
			if(parent.xiang !=''){
				title=title+"-"+parent.xiang+")";
			}else{
				title+=")";
			}
		}else{
			title+=")";
		}
	}
	

	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
		
	}else if(jsondata.Login_map.COM_VD=='V3'){ //市级权限数据
		$("#v2").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
		/*tree_canshu.com=jsondata.Login_map.COM_NAME;*/
		$("#v3").append("<option>全部旗区县</option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
		code=$("#v2").find("option:selected").val();
		a2(code.substring(0,4),type);
		code=code.substring(0,4);
		shi=$("#v2").find("option:selected").text();
	}
	
	//市级下拉框选择事件
	$("#v2").change(function(){
		$("#v3").empty();
		$("#v4").empty();
		$("#v5").empty();
		if($("#v2").find("option:selected").text()=="全部盟市"){
			level=1;//层级
			code='150000000000';
			a2(code.substring(0,3),type);
			code = code.substring(0,3);
		}else{
			level=2;//层级
			$("#v3").append("<option>全部旗区县</option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:$("#v2").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
			});
			code=$("#v2").find("option:selected").val();
			a2(code.substring(0,4),type);
			code = code.substring(0,4);
			shi=$("#v2").find("option:selected").text();
		}
	});
	
	//县级下拉框选择事件
	$("#v3").change(function(){
		$("#v4").empty();
		$("#v5").empty();
		if($("#v3").find("option:selected").text() == '全部旗区县'){
			level=2;
			code=$("#v2").find("option:selected").val();
			a2(code.substring(0,4),type);
			code = code.substring(0,4);
			
		}else{
			level=3;//层级
			$("#v4").append("<option>全部苏木乡镇</option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
			});
			code=$("#v3").find("option:selected").val();
			a2(code.substring(0,6),type);
			code = code.substring(0,6);
			
			
		}
		
	});
	
	//乡级下拉框选择事件
	$("#v4").change(function(){
		$("#v5").empty();
		if($("#v4").find("option:selected").text() == '全部苏木乡镇'){
			level=3
			code=$("#v3").find("option:selected").val();
			a2(code.substring(0,6),type);
			code = code.substring(0,6);
		
		}else{
			$("#v5").append("<option>请选择</option>");
			var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:$("#v4").find("option:selected").val()}, "text");
			var val = eval("("+data+")");
			$.each(val,function(i,item){
				$("#v5").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
			});
			level=4;//层级
			code=$("#v4").find("option:selected").val();
			a2(code.substring(0,9),type);
			code = code.substring(0,9);
			shi=$("#v2").find("option:selected").text();
			xian=$("#v3").find("option:selected").text();
			xiang=$("#v4").find("option:selected").text();
		}
		
		
	});
	
})


$(function () {
	
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	obj.com = Request['com'];
	obj.year = Request['year'];
	obj.sTime=$("input[name='start_date']").val();
	obj.eTime=$("input[name='end_date']").val();
	showWaitTime();
	setTimeout(function(){
		a3('first',code,type);
	},500);
	
})


var count=[];//区划名称
var count0=[];//总记录数

var obj = {};

var myChart_ring,myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};

$("#the_submit").click(function(){
	obj.sTime=$("input[name='start_date']").val();
	obj.eTime=$("input[name='end_date']").val();
	showWaitTime();
	setTimeout(function(){
		a3(6,code,type);
	},500);
})

//动画等待
function showWaitTime(){
	var choose1="<div class=\"ibox-content\"><div class=\"spiner-example\"><div class=\"sk-spinner sk-spinner-cube-grid\">"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"</div>"
        +"</div>"
        +"<span id=\"waitTime\" style=\"font-weight: bold;padding: 5px 20px;color: #1797F6;height: 20px;line-height: 20px;text-align: center;display: block;\">加载中,请稍后...</span></div>";
		$("#tu_1").html(choose1);
}



//code 行政区划 截串后的子符  dateType 传过来的条件类型 day week month然后后天根据判断查询条件
function a2(code,type){
	myChart = echarts.init(document.getElementById('tu_1'));//声明id为myChart的div为图形dom
	count=['其他帮扶活动','了解基本情况','填写扶贫手册','制定脱贫计划','落实资金项目','宣传扶贫政策','节日假日慰问'];
	count0=[];
	data = JSON.parse(ajax_async_t("../../getF_1_2_1.do", {name:obj.com,sTime:obj.sTime,eTime:obj.eTime,code:code,type:type,str:"xzqh"},"text","1","0")); //调用ajax通用方法
		if(data==0){
			$("#z").html('<img class="center-block" src="../../img/wu.png">');
		}else{
			var V10=0,V11=0,V12=0,V13=0,V14=0,V15=0,V16=0;
			if(data.length>0){
				for(var i=0; i<data.length; i++){

					if(typeof data[i].V10 == 'undefined'){
						V10 += 0;
						count0[i]= V10;
					}else{
						V10 += parseInt(data[i].V10);
						count0[i]= V10;
					}
					if(typeof data[i].V11 == 'undefined'){
						V11 += 0;
						count0[i+1]= V11;
					}else{
						V11 += parseInt(data[i].V11);
						count0[i+1]= V11;
					}
					if(typeof data[i].V12 == 'undefined'){
						V12 += 0;
						count0[i+2]= V12;
					}else{
						V12 += parseInt(data[i].V12);
						count0[i+2]= V12;
					}
					if(typeof data[i].V13 == 'undefined'){
						V13 += 0;
						count0[i+3]= V13;
					}else{
						V13 += parseInt(data[i].V13);
						count0[i+3]= V13;
					}
					if(typeof data[i].V14 == 'undefined'){
						V14 += 0;
						count0[i+4]= V14;
					}else{
						V14 += parseInt(data[i].V14);
						count0[i+4]= V14;
					}
					if(typeof data[i].V15 == 'undefined'){
						V15 += 0;
						count0[i+5]= V15;
					}else{
						V15 += parseInt(data[i].V15);
						count0[i+5]= V15;
					}
					if(typeof data[i].V16 == 'undefined'){
						V16 += 0;
						count0[i+6]= V16;
					}else{
						V16 += parseInt(data[i].V16);
						count0[i+6]= V16;
					}
				}
		
			
		}
	}

	/*alert("排序前汉字="+count);
	alert("排序前数字="+count0);*/
	for(i=0;i<count0.length;i++){		
		for(j=0;j<count0.length-i-1;j++){
			if(count0[j]<count0[j+1]){
				var s='';
				var ss='';
				s=count0[j];
				ss=count[j];
				count0[j]=count0[j+1];
				count[j]=count[j+1]
				count0[j+1]=s;
				count[j+1]=ss;
			}						
		}
	  
	}


	var option = {//柱状图
			title: {
				text: title,
				x:'center',
				subtext: '单位：次',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['走访记录数'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0} 次'
			},
			grid: {
				left: '0%',
				right: '9%',
				bottom: '10%',
				containLabel: true
			},
			color:['#6495ED'],
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:count
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [{
	        	 name: '走访记录数',
	        	 type: 'bar',
	        	 data: count0,
	         }
		         ]
	};	
	myChart.setOption(option);

}

//全部
$("#the_all").click(function(){
	$("#bdate").hide();
	$("#the_all").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");
	type="index";
	a3(7,code,"index");
});
//当天
$("#the_day").click(function(){
	$("#bdate").hide();
	$("#the_day").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");
	type="day";
	a3(1,code,type);
});
//近一周
$("#the_one_week").click(function(){
	$("#bdate").hide();
	$("#the_one_week").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	type="week";
	a3(2,code,type);
});
//近两周
$("#the_two_week").click(function(){
	$("#bdate").hide();
	$("#the_two_week").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	type="2week";
	a3(3,code,type);
});
//近一月
$("#the_one_month").click(function(){
	$("#bdate").hide();
	$("#the_one_month").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	type="month";
	a3(4,code,type);
});
//近三月
$("#the_three_month").click(function(){
	$("#bdate").hide();
	$("#the_three_month").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	type="3month";
	a3(5,code,type);
});
//自定义
$("#the_randow_date").click(function(){
	$("#bdate").show();
	$("#the_randow_date").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	type="rageTime";
	a3(6,code,type);
});

function a3(i,code,type){
	myChart = echarts.init(document.getElementById('tu_1'));//声明id为myChart的div为图形dom
	count=['其他帮扶活动','了解基本情况','填写扶贫手册','制定脱贫计划','落实资金项目','宣传扶贫政策','节日假日慰问'];
	count0=[];
	//如果是数字i  说明直接从缓存查询
	if(!isNaN(i) && code=='150'){
		arr = data3[i-1];
		count0[0]=arr.V10;
		count0[1]=arr.V11;
		count0[2]=arr.V12;
		count0[3]=arr.V13;
		count0[4]=arr.V14;
		count0[5]=arr.V15;
		count0[6]=arr.V16;
	}else{
		
		data2 = JSON.parse(ajax_async_t("../../getF_1_2_1.do",{sTime:obj.sTime,eTime:obj.eTime,code:code,type:type,str:"type"}, "text","1","0")); //调用ajax通用方法
		if(data2==0){
			$("#z").html('<img class="center-block" src="../../img/wu.png">');
			//fisrst为第一次加载标志
		}else if(i=='first'){
			var V10=0,V11=0,V12=0,V13=0,V14=0,V15=0,V16=0;
			if(data2.length>0){
					if(typeof data2[data2.length-1].V10 == 'undefined'){
						V10 += 0;
						count0[0]= V10;
					}else{
						V10 += parseInt(data2[data2.length-1].V10);
						count0[0]= V10;
					}
					if(typeof data2[data2.length-1].V11 == 'undefined'){
						V11 += 0;
						count0[1]= V11;
					}else{
						V11 += parseInt(data2[data2.length-1].V11);
						count0[1]= V11;
					}
					if(typeof data2[data2.length-1].V12 == 'undefined'){
						V12 += 0;
						count0[2]= V12;
					}else{
						V12 += parseInt(data2[data2.length-1].V12);
						count0[2]= V12;
					}
					if(typeof data2[data2.length-1].V13 == 'undefined'){
						V13 += 0;
						count0[3]= V13;
					}else{
						V13 += parseInt(data2[data2.length-1].V13);
						count0[3]= V13;
					}
					if(typeof data2[data2.length-1].V14 == 'undefined'){
						V14 += 0;
						count0[4]= V14;
					}else{
						V14 += parseInt(data2[data2.length-1].V14);
						count0[4]= V14;
					}
					if(typeof data2[data2.length-1].V15 == 'undefined'){
						V15 += 0;
						count0[5]= V15;
					}else{
						V15 += parseInt(data2[data2.length-1].V15);
						count0[5]= V15;
					}
					if(typeof data2[data2.length-1].V16 == 'undefined'){
						V16 += 0;
						count0[6]= V16;
					}else{
						V16 += parseInt(data2[data2.length-1].V16);
						count0[6]= V16;
					}
					data3=data2;
				}
			
		}else if(!isNaN(i) && code!='150'){
			var V10=0,V11=0,V12=0,V13=0,V14=0,V15=0,V16=0;
			if(data2.length>0){
				count0[0]=data2[0].V10;
				count0[1]=data2[0].V11;
				count0[2]=data2[0].V12;
				count0[3]=data2[0].V13;
				count0[4]=data2[0].V14;
				count0[5]=data2[0].V15;
				count0[6]=data2[0].V16;
				}
		}
	}
	//排序
	for(i=0;i<count0.length;i++){		
		for(j=0;j<count0.length-i-1;j++){
			if(count0[j]<count0[j+1]){
				var s='';
				var ss='';
				s=count0[j];
				ss=count[j];
				count0[j]=count0[j+1];
				count[j]=count[j+1]
				count0[j+1]=s;
				count[j+1]=ss;
			}						
		}
	  
	}
	
	var option = {//柱状图
			title: {
				text: title,
				x:'center',
				subtext: '单位：次',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['走访记录数'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0} 次'
			},
			grid: {
				left: '0%',
				right: '9%',
				bottom: '10%',
				containLabel: true
			},
			color:['#6495ED'],
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:count
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [{
	        	 name: '走访记录数',
	        	 type: 'bar',
	        	 data: count0,
	         }
		         ]
	};	
	myChart.setOption(option);
}
