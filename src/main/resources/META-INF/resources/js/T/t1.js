$(document).ready(function(){
	title='资金类型统计';
	var mydate = new Date();
	year = mydate.getFullYear();
	month = mydate.getMonth()+1;
	day = mydate.getDate();
	$("#year").text(year);
	$("#month").text(month);
	$("#day").text(day);
})

$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	obj.com = Request['com'];
	obj.year = Request['year'];
	obj.q1 = Request['q1'];
	obj.q2 = Request['q2'];
	obj.q3 = Request['q3'];
	obj.q4 = Request['q4'];
	obj.q5 = Request['q5'];
	obj.t1 = Request['t1'];
	obj.t2 = Request['t2'];
	a_2();
//	a_3();
})
var obj = {};
var myChart_1,myChart_2,myChart_3,myChart_4;
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
	var data = JSON.parse(ajax_async_t(GISTONE.Loader.basePath+"getItemCount_WtApp_t1_1_1.do")); //调用ajax通用方法
	if(data.data1==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
		
	}else{
	var com_name;
	var count = [];
	var count_2 = [];
	/*var A1=0,A2=0,A3=0,A4=0;*/
	$.each(data.data1,function(i,item){
		if(typeof item.item_type == 'undefined'){
			item.item_type='';
		}
		count[i]=item.item_type;
		if(item.count_num == '' || item.count_num == null || item.count_num == undefined){
			
		}else{
			count_2[i]=parseInt(item.count_num);
			
		}
	});
	var option = {//柱状图
			title: {
				text: '项目数量统计',
				subtext: '单位：个',
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
			        	 name: "项目个数",
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
/*	myChart_2.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});*/
	myChart_2.setOption(option);
	
	//统计图
	myChart_1 = echarts.init(document.getElementById('tu_1'));//声明id为mapChart的div为图形dom
	var option = {//图
			title : {
				text: title,
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{b} <br/> {c} 万  ({d}%)"
			},
			legend: {
				orient : 'vertical',
				x : 'left',
				
				data : ["中央","省财政","地方"]
			},
			calculable : true,
			color:['#1ab394','#337ab7','#2f4050'],
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
				data:[{value:'334600' , name:"中央"},
				      {value:'130830 ', name:"省财政"},
				      {value:'25231' , name:"地方"},
				      ]
			}]
	}
	myChart_1.setOption(option);
	
}
	if(data.data2==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
		
	}else{
	var com_name;
	var count = [];
	var count_2 = [];
	/*var A1=0,A2=0,A3=0,A4=0;*/
	$.each(data.data2,function(i,item){
		if(typeof item.item_type == 'undefined'){
			item.item_type='';
		}
		count[i]=item.item_type;
		if(item.totalAmount == '' || item.totalAmount == null || item.totalAmount == undefined){
			
		}else{
			count_2[i]=parseInt(item.totalAmount);
			
		}
	});
	//图三
	myChart_3 = echarts.init(document.getElementById('tu_3'));
	var option = {//柱状图
			title: {
				text: '资金分布情况',
				subtext: '单位：万',
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
			        	 name: "资金数量",
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
//	myChart_3.on('click', function (params) {
//		console.log(params);
//	    diqu=params.name;
//	    parent.setSelVal(diqu);
//	});
	myChart_3.setOption(option);
	
	
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
//图4 项目受益的贫困人口
myChart_4 = echarts.init(document.getElementById('tu_4'));
count_5 = ['贫困户','非贫困户','贫困人口','非贫困人口'];
count_6 = [1200,6500,4233,18514];
var option = {//柱状图
		title: {
			text: '项目受益的贫困人口',
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
			data:count_5
		},
		yAxis: {
			axisLabel: {
				formatter: '{value}'
			}
		},
		series: [
		         {
		        	 name: "贫困人口",
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
		        	 data: count_6,
		         }
		         ]
};
//点击柱状图触发事件
/*	myChart_2.on('click', function (params) {
	console.log(params);
    diqu=params.name;
    parent.setSelVal(diqu);
});*/
myChart_4.setOption(option);
};
