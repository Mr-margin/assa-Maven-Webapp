$(document).ready(function(){
	title='贫困户各类型收入占比';
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
var title='';
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
	table();
//	map();
	bar2();
});
var obj = {};
var myChart_1,myChart_2,myChart_3;
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
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHuc_15.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0,A9=0,A10=0;
		$.each(data,function(i,item){

			html +='<tr><td>'+(i+1)+'</td><td>'+item.name+'</td><td>'+item.z_hu+'</td>'+
			'<td>'+item.v1+'</td>'+
			'<td>'+item.v2+'</td>'+
			'<td>'+item.v3+'</td>'+
			'<td>'+item.v4+'</td>'+
			'<td>'+item.v5+'</td>'+
			'<td>'+item.v6+'</td>'+
			'<td>'+item.v7+'</td>'+
			'<td>'+item.v8+'</td>'+
			'<td>'+item.value+'</td>'+
			'</tr>';
			if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
				
			}else{
				A1+=parseInt(item.z_hu)
				
			}
			if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
				
			}else{
				A2+=parseInt(item.v1);
				
			}
			if(item.v2 == '' || item.v2 == null || item.v2 == undefined){
				
			}else{
				A3+=parseInt(item.v2);
				
			}
			if(item.v3 == '' || item.v3 == null || item.v3 == undefined){
				
			}else{
				A4+=parseInt(item.v3);
				
			}
			if(item.v4 == '' || item.v4 == null || item.v4 == undefined){
				
			}else{
				A5+=parseInt(item.v4);
				
			}
			if(item.v5 == '' || item.v5 == null || item.v5 == undefined){
				
			}else{
				A6+=parseInt(item.v5);
				
			}
			if(item.v6 == '' || item.v6 == null || item.v6 == undefined){
				
			}else{
				A7+=parseFloat(item.v6);
				
			}
			if(item.value == '' || item.value == null || item.value == undefined){
				
			}else{
				A10+=parseInt(item.value);
				
			}
//			A1+=parseInt(item.z_hu);
//			A2+=parseFloat(item.v1);
//			A3+=parseFloat(item.v2);
//			A4+=parseFloat(item.v3);
//			A5+=parseFloat(item.v4);
//			A6+=parseFloat(item.v5);
//			A7+=parseFloat(item.v6);
			A8=parseFloat(item.v7)*parseFloat(item.z_hu)+A8;
			A9=parseFloat(item.v8)*parseFloat(item.z_hu)+A9;
//			A10+=parseInt(item.z_ren);
		})

		A3=(floatDiv(A2,A1)).toFixed(2);
		A8=(floatDiv(A8,A1)).toFixed(2);
		A10=(floatDiv(A9,A10)).toFixed(2);
		A9=(floatDiv(A9,A1)).toFixed(2);


		html +='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td>'+
		'<td><strong>'+A1+'</strong></td>'+
		'<td><strong>'+parseFloat(A2).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A3)+'</strong></td>'+
		'<td><strong>'+parseFloat(A4).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A5).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A6).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A7).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A8).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A9).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A10).toFixed(2)+'</strong></td>'+
		'</tr>';
		$("#sr_table").html(html);
		$("#sr_table td").css("text-align","center");
		
		var option = {//环形图
				title : {
					text: title,
					x:'center',
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				tooltip : {
					trigger: 'item',
					formatter: "{b} : {c}元({d}%)"
				},
				legend: {
					orient : 'vertical',
					x : 'left',
					y : 'bottom',
					data:['生产经营性收入','务工收入','财产性收入','转移性收入']
				},
				color:[
				       '#27727B','#FCCE10','#E87C25','#B5C334',
				       '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
				       '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
				       ],
				       calculable : true,
				       series : [{
				    	   name:'内蒙古自治区',
				    	   type:'pie',
				    	   radius: ['0%', '70%'],
				    	   center: ['50%', '50%'],
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
				    				   fontSize: '15',
				    				   fontWeight: 'bold'
				    			   }
				    		   }
				    	   },
				    	   data:[
				    	         {name:'生产经营性收入',value:parseFloat(A4).toFixed(2)},
				    	         {name:'务工收入',value:parseFloat(A5).toFixed(2)},
				    	         {name:'财产性收入',value:parseFloat(A6).toFixed(2)},
				    	         {name:'转移性收入',value:parseFloat(A7).toFixed(2)}
				    	         ]
				       }]
		}
		
		myChart_2 = echarts.init(document.getElementById('bar1'));
		
		myChart_2.setOption(option);
	}
	 
}

//饼图
function map(){
	var mapdatajson;//定义地图JSON
	var map_name;//定义地图名称
	mapdatajson='../../mapData/neimenggu.json';//地图JSON
	map_name='neimenggu';//地图名称
	myChart_1 = echarts.init(document.getElementById('pkh_fb'),'infographic');//声明id为mapChart的div为图形dom
	myChart_1.showLoading();//此方法是显示加载动画效果
	var data = JSON.parse(ajax_async_t("/assa/getBfdxHuc_15.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	$.getJSON(mapdatajson, function (geoJson) {//获取已经定义好的json
		myChart_1.hideLoading();//隐藏加载动画
		echarts.registerMap(map_name, geoJson);//注册可用的地图，必须在包括 geo 组件或者 map 图表类型的时候才能使用
		option_map.title.text="人均纯收入分别情况";//标题名称
		option_map.series[0].name='人均纯收入';
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
		myChart_1.setOption(option_map);
	});
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
			color:["#BF3EFF","#F5FFFA"],
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
function bar2(){
	var data = ajax_async_t("/assa/getBfdxHuc_15.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var v7 = []; 
		var com_name = [];
		$.each(data,function(i,item){
			com_name [i] = item.name;
			v7 [i] = parseInt(item.v7);
		})

		var option = {
			color: ['#7cb5ec'],
			title : {
				text: '贫困户生产经营性收入',
				subtext: '单位：元',
				subtextStyle: {
					color: 'black'
				},
				x:'center',
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			tooltip : {
				trigger: 'axis',
			},
			legend: {
				data:['住房面']
			},
			grid: {
				left: '0%',
				right: '2%',
				bottom: '8%',
				containLabel: true
			},
			calculable : true,
			xAxis :{
				type: "category",//坐标轴类型，横轴默认为category，纵轴默认为数值型value
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:2,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				splitLine: {show: false},//分隔线，默认是显示，show来控制显示状态
				data:com_name //X轴的坐标
			},
			yAxis : {
				axisLabel: {
					formatter: '{value}'
				}
			},
			series : {
				name:'收入情况',
				type:'bar',
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
				data:v7
			}
		};
		myChart_3 = echarts.init(document.getElementById('bar2'));
		//点击事件
		myChart_3.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart_3.setOption(option);
	}
	
}