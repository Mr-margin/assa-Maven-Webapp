$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	table();
//	map();
	bar();
});
var obj;
var mapChart,myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(mapChart){
			mapChart.resize();
		}
	}catch(e){
	}
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHu_11.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"asc"},"json");
	var html = "";
	var v1=0;
	var v2=0;
	var v3=0;
	var v4=0;
	var v5=0;
	var v6=0;
	var xu=0;
	$.each(data,function(i,item){
//		if(i == data.length-1){
//		html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center"><strong>'+item.com_name+'</strong></td>'+
//		'<td class="text-center"><strong>'+item.z_hu+'</strong></td>'+
//		'<td class="text-center"><strong>'+item.v1+'</strong></td>'+
//		'<td class="text-center"><strong>'+item.v2+'</strong></td>'+
//		'<td class="text-center"><strong>'+item.v4+'</strong></td>'+
//		'<td class="text-center"><strong>'+item.v6+'</strong></td>'+
//		'<td class="text-center"><strong>'+item.v8+'</strong></td>'+
//		'</tr>';
//		}else{
		html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td>'+
		'<td class="text-center">'+item.z_hu+'</td>'+
		'<td class="text-center">'+item.v1+'</td>'+
		'<td class="text-center">'+item.v2+'</td>'+
		'<td class="text-center">'+item.v4+'</td>'+
		'<td class="text-center">'+item.v6+'</td>'+
		'<td class="text-center">'+item.v8+'</td>'+
		'</tr>';
		if(item.z_hu == ''  || item.z_hu == undefined){
			
		}else{
			v1+=parseInt(item.z_hu);
			
		}
		if(item.v1 == '' || item.v1 == undefined){
			
		}else{
			v2+=parseFloat(item.v1);
			
		}
		if(item.v2 == ''  || item.v2 == undefined){
			
		}else{
			v3+=parseFloat(item.v2);
			
		}
		if(item.v4 == ''  || item.v4 == undefined){
			
		}else{
			v4+=parseFloat(item.v4);
			
		}
		if(item.v6 == ''  || item.v6 == undefined){
			
		}else{
			v5+=parseInt(item.v6);
			
		}
		if(item.v8 == ''  || item.v8 == undefined){
			
		}else{
			v6+=parseFloat(item.v8);
			
		}
//			v5+=parseFloat(item.v6);
			
		
		
			
			
		
		
//		v1+=parseInt(item.z_hu);
//		v2+=parseFloat(item.v1);
//		v3+=parseFloat(item.v2);
//		v4+=parseFloat(item.v4);
//		v5+=parseFloat(item.v6);
//		v6+=parseFloat(item.v8);

		xu=i+1;
	})
	xu=xu+1;
	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td>'+
	'<td class="text-center"><strong>'+v1+'</strong></td>'+
	'<td class="text-center"><strong>'+parseFloat(v2).toFixed(2)+'</strong></td>'+
	'<td class="text-center"><strong>'+parseFloat(v3).toFixed(2)+'</strong></td>'+
	'<td class="text-center"><strong>'+parseFloat(v4).toFixed(2)+'</strong></td>'+
	'<td class="text-center"><strong>'+parseFloat(v5).toFixed(2)+'</strong></td>'+
	'<td class="text-center"><strong>'+parseFloat(v6).toFixed(2)+'</strong></td>'+
	'</tr>';
	$("#jbqk_table").html(html);


}
var option_map = {
		title: {//标题
			text : '',//com_name+'-'+shujv+'人均年收入人数'
			x:'center',
			textStyle:{
				color:'#333',
				fontWeight:'bolder',
				fontWeight:'500',fontSize:'17'
			}
		},
		tooltip : {//提示框组件相关的行为，必须引入提示框组件后才能使用。
			trigger: 'item'//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
		},
		dataRange: {//感觉这个是和visualMap一样，对范围进行确定。\
			show:true,//是否显示范围条
			left: 'right',
//			color:["#F57B00","#F4B800","#FFFFBE","#de9611"],
			text:['高','低'],// 文本，默认为数值文本
			calculable : true//是否启用值域漫游，即是否有拖拽用的手柄，以及用手柄调整选中范围。
		},
		series : [{
			name: '',// shujv+'人数',//这里是鼠标放在上面，出现的提示。
			type: 'map',
			scaleLimit:{max:2, min:1},//滚轮缩放的极限控制，通过min, max最小和最大的缩放值，默认的缩放为1。
			mapType:'', //map_name,
			selectedMode : 'single',//图例选择的模式，默认开启图例选择，可以设成 false 关闭。除此之外也可以设成 'single' 或者 'multiple' 使用单选或者多选模式。
			itemStyle:{//地图区域的多边形 图形样式，有 normal 和 emphasis 两个状态，normal 是图形正常的样式，emphasis 是图形高亮的样式，比如鼠标悬浮或者图例联动高亮的时候会使用 emphasis 作为图形的样式。
				normal:{label:{show:true}},
				emphasis:{label:{show:true}}
			},
			itemStyle: {
				normal: {
					label: {
						show: true,
						textStyle: {color: "black"},//地图上的字体
					},
					areaStyle : "#E0ECF6",
					borderColor: "#E0ECF6",
					borderWidth: "2.0",
					color: "#6CAFED"
				},
				emphasis: {
					label: {
						show: true,
						textStyle: {color: "black"}
					},
					borderColor: "#fff",
					borderWidth: "1.0",
					color: "#BADB58"
				}
			},
			data:[]
		}]
};
function map(){
	var mapdatajson;//定义地图JSON
	var map_name;//定义地图名称
	mapdatajson='../../mapData/neimenggu.json';//地图JSON
	map_name='neimenggu';//地图名称
	mapChart = echarts.init(document.getElementById('pkh_fb'),'infographic');//声明id为mapChart的div为图形dom
	mapChart.showLoading();//此方法是显示加载动画效果
	var data = JSON.parse(ajax_async_t("/assa/getBfdxHu_11_map.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	$.getJSON(mapdatajson, function (geoJson) {//获取已经定义好的json
		mapChart.hideLoading();//隐藏加载动画
		echarts.registerMap(map_name, geoJson);//注册可用的地图，必须在包括 geo 组件或者 map 图表类型的时候才能使用
		option_map.title.text="贫困户平均转移性收入分布";//标题名称
		option_map.series[0].name='贫困户平均转移性收入分布';
		option_map.series[0].data=data;
		option_map.series[0].mapType=map_name;
		var min = 0,max = 0;	//计算地图中下标最大值最小值
		$.each(data, function(i,item) {
			if(Math.ceil(item.value)>max){
				max = Math.ceil(item.value);
			}
		});

		option_map.dataRange.max = max;
		option_map.dataRange.min = min;
		mapChart.setOption(option_map);
	});
}
//条形图

function bar(){
	myChart = echarts.init(document.getElementById('bar'));//声明id为mapChart的div为图形dom
	var data = JSON.parse(ajax_async_t("/assa/getBfdxHu_11.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"})); //调用ajax通用方法
	var count = [];
	var count_2 = [];
	var count_3 = [];
	var count_4 = [];
	var count_5 = [];
	var count_6 = [];
	$.each(data,function(i,item){
		count[i]=item.com_name;
		count_2[i]=item.v1;
		count_3[i]=item.v2;
		count_4[i]=item.v4;
		count_5[i]=item.v6;
		count_6[i]=item.v8;
	});

	var option = {//柱状图
			title: {
				text:' 贫困户上年度转移性收入构成情况',
				subtext: '单位： 元',
				subtextStyle: {
					color: 'black'
				},
				x:'center',
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['户均转移性收入','领取计划生育', '领取低保','领取养老金','领取生态补偿金'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0} 元<br/>{a1}: {c1} 元<br/>{a2}: {c2} 元<br/>{a3}: {c3} 元<br/>{a4}: {c4} 元'
			},
			color:['#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80'],
			grid: {
				left: '0%',
				right: '3%',
				bottom: '10%',
				containLabel: true
			},
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
			        	 name: "户均转移性收入",
			        	 type: 'bar',
			        	 data: count_2
			         },
			         {
			        	 name: "领取计划生育",
			        	 type: 'bar',
			        	 stack: '转移性收入',
			        	 data: count_3
			         },
			         {
			        	 name: "领取低保",
			        	 type: 'bar',
			        	 stack: '转移性收入',
			        	 data: count_4
			         },
			         {
			        	 name: "领取养老金",
			        	 type: 'bar',
			        	 stack: '转移性收入',
			        	 data: count_5
			         },{
			        	 name: "领取生态补偿金",
			        	 type: 'bar',
			        	 stack: '转移性收入',
			        	 data: count_6
			         }
			         ]
	};
	//点击事件
	myChart.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart.setOption(option);
}
