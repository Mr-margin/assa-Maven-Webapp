var title='';
$(document).ready(function() {
	title='贫困人口文化程度统计';
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
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	pkcsbbz();
	biaoge()
});

var option_map = {
		title: {//标题
			text : '',//com_name+'-'+shujv+'人均年收入人数'
			x:'center',
			textStyle:{fontWeight:'500',fontSize:'17'}
		
			
		},
		tooltip : {//提示框组件相关的行为，必须引入提示框组件后才能使用。
			trigger: 'item',//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
			formatter: '{a}<br/> {b}: {c} 个'
		},
		legend: {
	        orient: 'vertical',
	        left: 'left',
	        
	    },
		dataRange: {//感觉这个是和visualMap一样，对范围进行确定。\
			show:true,//是否显示范围条
			left: 'right',
			color:["#9BCA63","#E6E6FA"],
			text:['高','低'],// 文本，默认为数值文本
			calculable : true//是否启用值域漫游，即是否有拖拽用的手柄，以及用手柄调整选中范围。
		},
		grid:{
			 left: '3%',
		        right: '4%',
		        bottom: '15%'
		},
		series : [{
			name: '',// shujv+'人数',//这里是鼠标放在上面，出现的提示。
			type: 'map',
			scaleLimit:{max:2, min:1},//滚轮缩放的极限控制，通过min, max最小和最大的缩放值，默认的缩放为1。
			mapType:'', //map_name,
			selectedMode : 'single',//图例选择的模式，默认开启图例选择，可以设成 false 关闭。除此之外也可以设成 'single' 或者 'multiple' 使用单选或者多选模式。
			itemStyle: {
				normal: {
					label: {
						show: true,
						textStyle: {color: "#777",
							fontSize:12},//地图上的字体
					},
					areaStyle : "#E0ECF6",
					borderColor: "#E0ECF6",
					borderWidth: "1.0",
					color: "#6CAFED"
				},
				emphasis: {
					label: {
						show: true,
						textStyle: {color: "black",fontSize:10}
					},
					borderColor: "#fff",
					borderWidth: "1.0",
					color: "#FF6EB4"
				}
			},
//			markPoint: { // markLine 也是同理
//	            data: [{
//	                coord: [] // 其中 5 表示 xAxis.data[5]，即 '33' 这个元素。
//	                // coord: ['5', 33.4] // 其中 '5' 表示 xAxis.data中的 '5' 这个元素。
//	                                      // 注意，使用这种方式时，xAxis.data 不能写成 [number, number, ...]
//	                                      // 而只能写成 [string, string, ...]
//	            }]
//	        },
			data:[]
		}]
};

function biaoge(){
	var v1=0;
	var v2=0;
	var xu=0;
	var data = ajax_async_t("/assa/getSdcsdh.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data==0){
		$("#biaoge").html('');
	}else{
		var html="";
		var html1="";
    	$.each(data, function(i,item) {

    		v1+=parseInt(item.vs2);
    		v2+=parseInt(item.vs3);
    		xu=i+1;
    		html+='<tr><td class="text-center">'+xu+'</td><td class="text-center">'+item.name+'</td>'+
    		'<td class="text-center">'+item.vs2+'</td>'+
    		'<td class="text-center">'+item.vs3+'</td></tr>';
    	});
    	xu=xu+1;
    	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v2+'</strong></td></tr>';
    	html1='显示本辖区三到村三到户覆盖的苏木乡镇个数和嘎查村个数'
		$("#biaozhu").html(html1);
    	//对下方的表格赋值
    	
		$("#xiafangzongbiao").html(html);
		$("#ren").html(v1);
		$("#hu").html(v2);
	}

}

var myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整

	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
function pkcsbbz(){
	 myChart = echarts.init(document.getElementById('tu_03'));//声明id为mapChart的div为图形dom 
	var data = ajax_async_t("/assa/getSdcsdh.do",{order:"VG10",name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}
	var count=[];//名称
	var count2=[];//总数
	var count3=[];//贫困户

	
	$.each(data,function(i,item){
		count[i]=item.name;
		count2[i]=item.vs2;
		count3[i]=item.vs3;
	
	});

	var itemStyle = {
		    normal: {
		    },
		    emphasis: {
		        barBorderWidth: 1,
		        shadowBlur: 10,
		        shadowOffsetX: 0,
		        shadowOffsetY: 0,
		        shadowColor: 'rgba(0,0,0,0.5)'
		    }
		};
	var option = {
			title: {
				text: '三到村三到户覆盖',
				x:'center',
				subtext: '单位：个',
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
		    tooltip : {
		    	 trigger: 'axis',
		         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		         }
		    },
		    color:["#32CD32","#DB7093"],
		    legend: {
		        data:['覆盖乡镇个数','覆盖村个数'],
		        y:'bottom'
		    },
		    
		    xAxis : [
		        {
		            type : 'category',
		            data : count,
		            splitLine:{show:false},
		            splitNumber:1,
		            axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
		        }
		    ],
		    yAxis: {
				axisLabel: {
		            formatter: '{value}'
		        }
			},
		    
		    series : [
	
		        {
		            name:'覆盖乡镇个数',
		            type:'bar',
		            /*stack: '1',*/
		            data:count2,
		            itemStyle:itemStyle
		        },
		        {
		            name:'覆盖村个数',
		            type:'bar',
		            /*stack: '1',*/
		            data:count3,
		            itemStyle:itemStyle
		        }
		    ]
};
	
	//option_tiao.color=['#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'];
	option.xAxis.data=count;
	
	
	//点击事件
	myChart.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart.setOption(option);
}
//统计图方法
//function a_3(){
//	var mapdatajson;//定义地图JSON
//	var map_name;//定义地图名称
//	mapdatajson='../../mapData/neimenggu.json';//地图JSON
//	map_name='neimenggu';//地图名称
//	 myChart = echarts.init(document.getElementById('tu_03'));//声明id为mapChart的div为图形dom
//	myChart.showLoading();//此方法是显示加载动画效果
//	var data =ajax_async_t("/assa/getSdcsdh.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
//	
//	var data_arr=[];
//	$.each(data,function(i,item){
//		data_arr.push({
//			name:item.name,
//			value:item.vs3
//		})
//
//		
//	});
//	$.getJSON(mapdatajson, function (geoJson) {//获取已经定义好的json
//		myChart.hideLoading();//隐藏加载动画
//		echarts.registerMap(map_name, geoJson);//注册可用的地图，必须在包括 geo 组件或者 map 图表类型的时候才能使用
//		option_map.title.text="三到村三到户专题图";//标题名称
//		option_map.series[0].name='覆盖村个数';
//		option_map.series[0].data=data_arr;
//		option_map.series[0].mapType=map_name;
//		var min = 0,max = 0;	//计算地图中下标最大值最小值
//		
//		var cood={};
//		$.each(geoJson.features,function(i,item){
//			cood[item.properties.name]=item.properties.cp;
//		})
//		console.log(cood["乌海市"]);
////		option_map.series[0].markPoint.data[0].coord=cood["乌海市"];
//		$.each(data_arr, function(i,item) {
//			if(item.value>max){
//				max = parseInt(item.value);
//			}
//			if(item.value<min){
//				min =  parseInt(item.value);
//			}
//		});
//		option_map.dataRange.max = max;
//		option_map.dataRange.min = min;
//		myChart.setOption(option_map);
//	});
//}