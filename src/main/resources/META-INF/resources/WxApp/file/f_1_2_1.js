$(document).ready(function(){
	title='贫困户组成情况统计';
	if(parent.shi!=''){
		$('#table').attr("class","center-block col-sm-12");
		$('#ditu').hide();
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
	$('#title').html(title);
})
var title='';
$(function () {

	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);

	table();
	map();
	bar();
});
var obj;
var myChart_1,myChart_2;
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
};
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHu_1.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"asc"},"json");
	var html = "";
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var A1=0,A2=0,A3=0,A4=0;
		$.each(data,function(i,item){
				html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td>'+
				'<td class="text-center">'+item.z_hu+'</td>'+
				'<td class="text-center">'+item.v1+'</td>'+
				'<td class="text-center">'+item.v5+'</td>'+
				'<td class="text-center">'+item.v9+'</td>'+
				'</tr>';
				if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
					
				}else{
					A1=parseInt(item.z_hu)+parseInt(A1);
					
				}
				if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
					
				}else{
					A2=parseInt(item.v1)+parseInt(A2);
					
				}
				if(item.v5 == '' || item.v5 == null || item.v5 == undefined){
					
				}else{
					A3=parseInt(item.v5)+parseInt(A3);
					
				}
				if(item.v9 == '' || item.v9 == null || item.v9 == undefined){
					
				}else{
					A4=parseInt(item.v9)+parseInt(A4);
					
				}
				
				
				
		})
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
				'<td class="text-center"><strong>'+A1+'</strong></td>'+
				'<td class="text-center"><strong>'+A2+'</strong></td>'+
				'<td class="text-center"><strong>'+A3+'</strong></td>'+
				'<td class="text-center"><strong>'+A4+'</strong></td>'+
				'</tr>';
		$("#jbqk_table").html(html);
	}
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
			left: 'left',
			color:["#F57B00","#de9611","#F4B800","#FFFFBE"],
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
						show: false,
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
	myChart_1 = echarts.init(document.getElementById('pkh_fb'));//声明id为mapChart的div为图形dom
	myChart_1.showLoading();//此方法是显示加载动画效果
	var data = JSON.parse(ajax_async_t("/assa/getBfdxHu_2.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	$.getJSON(mapdatajson, function (geoJson) {//获取已经定义好的json
		myChart_1.hideLoading();//隐藏加载动画
		echarts.registerMap(map_name, geoJson);//注册可用的地图，必须在包括 geo 组件或者 map 图表类型的时候才能使用
		option_map.title.text="贫困户分布情况";//标题名称
		option_map.series[0].name='贫困户数';
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
//条形图

function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_1.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	var com_name =[];
	var ybpkh = [];
	var dbh = [];
	var wbh = [];
	if ( data == "" || data == null || data == undefined ) {
		$("#pd").html('<img src="../../img/wu.png" class="center-block" >')
	}else{
		$.each(data,function(i,item){
			com_name [i] = item.com_name;
			ybpkh[i] = item.v1;
			dbh[i] = item.v5;
			wbh[i] = item.v9;
	})
	option = {
		title: {
			text: '贫困户组成情况',
			x:'center',
			textStyle:{fontWeight:'500',fontSize:'17'}
		},
		color:['#6495ED','#E87C25','#B5C334'],
		tooltip : {
			trigger: 'axis',
			axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend: {
			data: ['一般贫困户', '低保贫困户','五保贫困户'],
			y:'bottom'
		},
		grid: {
			left: '0%',
			right: '2%',
			bottom: '8%',
			containLabel: true
		},
		xAxis:  {
			type: 'value'
		},
		yAxis: {
			type: 'category',
			data: com_name
		},
		series: [
		         {
		        	 name: '一般贫困户',
		        	 type: 'bar',
		        	 stack: '总量',
		        	 label: {
		        		 normal: {
		        			 show: false,
		        			 position: 'insideRight'
		        		 }
		        	 },
		        	 data: ybpkh
		         },
		         {
		        	 name: '低保贫困户',
		        	 type: 'bar',
		        	 stack: '总量',
		        	 label: {
		        		 normal: {
		        			 show: false,
		        			 position: 'insideRight'
		        		 }
		        	 },
		        	 data: dbh
		         },
		         {
		        	 name: '五保贫困户',
		        	 type: 'bar',
		        	 stack: '总量',
		        	 label: {
		        		 normal: {
		        			 show: false,
		        			 position: 'insideRight'
		        		 }
		        	 },
		        	 data: wbh
		         }

		         ]
	};
	
	myChart_2 = echarts.init(document.getElementById('bar'));
	myChart_2.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart_2.setOption(option);
	}
	
}
