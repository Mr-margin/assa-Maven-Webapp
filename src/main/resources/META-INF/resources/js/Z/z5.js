$(function () {
//	//省级领导
//	$("#option1").click(function (){
//		$(".shengji").show();
//		$(".shiji").hide();
//		$(".xianji").hide();
//		$(".xianjiyixia").hide();
//	});
//
//	//市级领导
//	$("#option2").click(function (){
//		$(".shengji").hide();
//		$(".shiji").show();
//		$(".xianji").hide();
//		$(".xianjiyixia").hide();
//	});
//
//	//县级领导
//	$("#option3").click(function (){
//		$(".shengji").hide();
//		$(".shiji").hide();
//		$(".xianji").show();
//		$(".xianjiyixia").hide();
//	});
//
//	//县级以下干部
//	$("#option4").click(function (){
//		$(".shengji").hide();
//		$(".shiji").hide();
//		$(".xianji").hide();
//		$(".xianjiyixia").show();
//	});
	bar1();
	bar2();
//	bar3();

	getdata_1();
});

var code = jsondata.Login_map.SYS_COM_CODE;

//获取上方四个数据
function getdata_1(){
	var data = ajax_async_t(GISTONE.Loader.basePath+"z5top_1.do", {code:code}, "json");
	$("#pkrk_1").html(data["a_1"]);
	$("#pkrk_2").html("<br>"+data["a_2"]+"人("+data["a_3"]+"%)");
	$("#pkh_1").html(data["b_1"]);
	$("#pkh_2").html("<br>"+data["b_2"]+"户("+data["b_3"]+"%)");



	if(code.substr(4, 8)=="00000000"){
		$("#gjzdqx").html(data["d_1"]);
		$("#zzqzdqx").html("<br>"+data["d_2"]+"个");
	}else{
		if(data["d_1"]=="1"){
			$("#gjzdqx_h").html("<span class='label label-warning' style='font-size: 75%;'>是");
		}else{
			$("#gjzdqx_h").html("<span class='label label-warning' style='font-size: 75%;'>否");
		}
		if(data["d_2"]=="1"){
			$("#zzqzdqx").html("<br>是");
		}else{
			$("#zzqzdqx").html("<br>否");
		}
	}
}


function ajax_async_t(url,data,dataType,async){
	var rel;
	if(async==""||async==undefined){
		async=true;
	}else{
		async=false;
	}
	$.ajax({  		       
		url: url,
		type: "POST",
		async:false,
		dataType: dataType,
		data: data,
		success: function (ret) {
			if(ret.length==0){
				toastr["warning"]("warning", "查询数据为空");
			}else{
				rel = ret;
			}
		},
		error: function (ret) { 
			toastr["error"]("error", "服务器异常或未查询到数据");
		}  
	});
	return rel;
}

function bar1(){
	var myChart1 = echarts.init(document.getElementById('hfccs'));//声明id为mapChart的div为图形dom 
	var data1 = ajax_async_t(GISTONE.Loader.basePath+"z5top_3.do", {code:code}, "json");
	if(data1==0){
		$("#hfccs").html("暂无数据");
	}else{
		var count=[];
		var count2=[];
		var count3=[];
		var count4=[];
		var count5=[];
		var v1=0;
		var v2=0;
		var v3=0;
		var v4=0;
		var v5=0;
		var v6=0;
		var v7=0;
		$.each(data1,function(i,item){
			v1=parseInt(item.fc1)+parseInt(v1);
			v2=parseInt(item.fc2)+parseInt(v2);
			v3=parseInt(item.fc3)+parseInt(v3);
			v4=parseInt(item.fc4)+parseInt(v4);
			v5=parseInt(item.fc5)+parseInt(v5);
			v6=parseInt(item.fc6)+parseInt(v6);
			v7=parseInt(item.fc7)+parseInt(v7);
		});
		var option = {//柱状图
				title: {
					text: "分类扶持比例",
					x:'center',
					subtext: '单位：人',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					data:['人数'],
					y:'top',
					x:'right'
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					},
					formatter : '{b}<br/>{a0}: {c0} %'
				},
				grid: {
					left: '0%',
					right: '1%',
					bottom: '10%',
					containLabel: true
				},
				color:['#FE8463'],
//				color:['#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
//				'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
//				'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'],
				xAxis: {
					axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
					data:["产业发展和转移就业","易地扶贫搬迁","生态补偿","教育扶贫","大病救治","社会保障兜底"]
				},
				yAxis: {
					axisLabel: {
						formatter: '{value}'
					}
				},
				series: [
				         {
				        	 name: '人数',
				        	 type: 'bar',
				        	 data: [v2,v3,v4,v5,v6,v7]
				         }
				         ]
		};
		myChart1.setOption(option);
	}
	
}
//贫困人口状况
function bar2(){
	var myChart = echarts.init(document.getElementById('pkrkzk'));//声明id为mapChart的div为图形dom
	var data = ajax_async_t(GISTONE.Loader.basePath+"z5top_2.do", {code:code}, "json");

	var count=[];//区划名称
	var count0=[];//总人数

	var title_text;

	var i =0;
	for (var prop in data) {  
		if (data.hasOwnProperty(prop)) {   
			count0[i]=data[prop];
			count[i]=prop;
			i += 1;
		}  
	}  
	var option = {//柱状图
			title: {
				text: "贫困人口分布",
				x:'center',
				subtext: '单位：人',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['贫困人口数量'],
				y:'top',
				x:'right'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0} 人'
			},
			grid: {
				left: '0%',
				right: '1%',
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
			series: [
			         {
			        	 name: '贫困人口数量',
			        	 type: 'bar',
			        	 data: count0
			         }
			         ]
	};
	myChart.setOption(option);
}