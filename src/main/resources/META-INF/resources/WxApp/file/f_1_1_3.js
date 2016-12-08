$(document).ready(function(){
	title='贫困人口身体健康情况';
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
})

$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	a_2();
//	a_3();
})
var obj;
var myChart_1,myChart_2,myChart_3;
var title='';
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart_1){
			myChart_1.resize();
		}
	}catch(e){
	}
	try{
		if(myChart_2){
			myChart_2.resize();
		}
	}catch(e){
	}
	try{
		if(myChart_3){
			myChart_3.resize();
		}
	}catch(e){
	}
};
function a_2(){
	myChart_2 = echarts.init(document.getElementById('tu_2'));//声明id为mapChart的div为图形dom
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_3.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.jpg">');
		
	}else{
	var com_name;
	var count = [];
	var count_2 = [];
	var A1=0,A2=0,A3=0,A4=0;
	$.each(data,function(i,item){
		if(typeof item.V1 == 'undefined'){
			item.V1='';
		}
		if(typeof item.V4 == 'undefined'){
			item.V4='';
		}
		count[i]=item.V1;
		count_2[i]=item.V4;
		
		if(item.V3 == '' || item.V3 == null || item.V3 == undefined){
			
		}else{
			A1=parseInt(item.V3)+parseInt(A1);
			
		}
		if(item.V4 == '' || item.V4 == null || item.V4 == undefined){
			
		}else{
			A2=parseInt(item.V4)+parseInt(A2);
		}
		if(item.V5 == '' || item.V5 == null || item.V5 == undefined){
			
		}else{
			A3=parseInt(item.V5)+parseInt(A3);
		}
		if(item.V6 == '' || item.V6 == null || item.V6 == undefined){
			
		}else{
			A4=parseInt(item.V6)+parseInt(A4);
			
		}
	});
	var option = {//柱状图
			title: {
				text: '长期慢性病人群分布',
				subtext: '单位：人',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
			},
			color:['#d87a80'],
			grid: {
				left: '0%',
				right: '1%',
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
			        	 name: "长期慢性病人数",
			        	 type: 'bar',
			        	 label: {
			        		 normal: {
			        			 show: true,
				        		 position: 'top',
				        		 formatter: '{c}',
				        		 textStyle: {
				        			 color:"black"
				        		 }
			        		 }
			        	 },
			        	 data: count_2,
			         }
			         ]
	};
	//点击柱状图触发事件
	myChart_2.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart_2.setOption(option);
	
	myChart_1 = echarts.init(document.getElementById('tu_1'));//声明id为mapChart的div为图形dom
	var option = {//图
			title : {
				text: title,
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{b} <br/> {c} 人  ({d}%)"
			},
			legend: {
				orient : 'vertical',
				x : 'left',
				y : 'bottom',
				data : ["健康","长期慢性病","大病","残疾"]
			},
			calculable : true,
			color:['#1ab394','#337ab7','#2f4050','#ed5565'],
			series : [{
				type:'pie',
				radius: ['30%', '45%'],
				center: ['53%', '60%'],
				avoidLabelOverlap: true,
	            label: {
	            	normal: {
	            		show: true,
	            		formatter:'{b}: {d}%'
	            	},
	                emphasis: {
	                    show: true,
	                    formatter:'{b}: {d}%',
	                    textStyle: {
	                        fontWeight: 'bold'
	                    }
	                }
	            },
				data:[{value:A1 , name:"健康"},
				      {value:A2 , name:"长期慢性病"},
				      {value:A3 , name:"大病"},
				      {value:A4 , name:"残疾"}
				      ]
			}]
	}
	myChart_1.setOption(option);
}
var option_map = {
		title: {//标题
			text : '',//com_name+'-'+shujv+'人均年收入人数'
			x:'center',
			textStyle:{fontWeight:'500',fontSize:'17'},
		},
		tooltip : {//提示框组件相关的行为，必须引入提示框组件后才能使用。
			trigger: 'item'//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
		},
		dataRange: {//感觉这个是和visualMap一样，对范围进行确定。\
			show:true,//是否显示范围条
			left: 'right',
			color:["#F57B00","#F4B800","#FFFFBE","#de9611"],
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
	}
};
function a_3(){
	var mapdatajson;//定义地图JSON
	var map_name;//定义地图名称
	mapdatajson='../../mapData/neimenggu.json';//地图JSON
	map_name='neimenggu';//地图名称
	myChart_3 = echarts.init(document.getElementById('tu_3'));//声明id为mapChart的div为图形dom
	myChart_3.showLoading();//此方法是显示加载动画效果
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_3_3.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	$.getJSON(mapdatajson, function (geoJson) {//获取已经定义好的json
		myChart_3.hideLoading();//隐藏加载动画
		echarts.registerMap(map_name, geoJson);//注册可用的地图，必须在包括 geo 组件或者 map 图表类型的时候才能使用
		option_map.title.text="患长期慢病贫困人口分布";//标题名称
//		option_map.title.textStyle.color='#333';//标题颜色
		option_map.title.textStyle.fontWeight='bolder';//标题大小
		option_map.series[0].name='长期慢性病人数';
		option_map.series[0].data=data;
		option_map.series[0].mapType=map_name;
		var min = 0,max = 0;	//计算地图中下标最大值最小值
		$.each(data, function(i,item) {
			if(item.value>max){
				max = item.value;
			}
			if(item.value<min){
				min = item.value;
			}
		});
		option_map.dataRange.max = max;
		option_map.dataRange.min = min;
		myChart_3.setOption(option_map);
	});
}