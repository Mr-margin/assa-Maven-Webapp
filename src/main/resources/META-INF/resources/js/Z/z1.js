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
	bar3();
	map();
	//图片预览
	jQuery(function ($) {
		$('.artZoom').artZoom({
			path: './images',	// 设置artZoom图片文件夹路径
			preload: true,		// 设置是否提前缓存视野内的大图片
			blur: true,			// 设置加载大图是否有模糊变清晰的效果
			// 语言设置
			left: '左旋转',		// 左旋转按钮文字
			right: '右旋转',		// 右旋转按钮文字
			source: '看原图'		// 查看原图按钮文字
		});
	});
	
});

var option_map = {
		title : {
            text : '贫困人口数量',
            padding: [10, 105],
            textStyle:{
				fontSize:'15'
			}
        },
		tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}'
        },
		visualMap: {//感觉这个是和visualMap一样，对范围进行确定。\
			type: 'piecewise',
			orient: 'vertical',
			top: 30,
			pieces: [
	            {min: 200000, label: '大于20万人', color: '#ff0000'},
	            {min: 150000, max: 200000, label: '15万到20万人', color: '#a80c0c'},
	            {min: 100000, max: 150000, label: '10万到15万人', color: '#c42f2f'},
	            {min: 30000, max: 100000, label: '3万到10万人', color: '#e65050'},
	            {min: 10000, max: 30000, label: '1万到3万人', color: '#eaed2b'},
	            {min: 2000, max: 10000, label: '2千到1万人', color: '#e6e874'},
	            {min: 1, max: 2000, label: '小于2千人', color: '#f0dfdf'}
	        ],
	        left: 100,
//	        color: ['#BF3030', '#FF4040', '#FF7373']
//			color: ['black','red','#FF7373']
		},
		series : [{
			name: '贫困人口现状',
			type: 'map',
//			roam: true,
			left: 30,
			top: 20,
			right: 30,
			bottom: 20,
			mapType:'neimenggu',
//			selectedMode : 'false',//图例选择的模式，默认开启图例选择，可以设成 false 关闭。除此之外也可以设成 'single' 或者 'multiple' 使用单选或者多选模式。
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
						show: false,
						textStyle: {color: "black"}
					},
					borderColor: "#fff",
					borderWidth: "1.0",
					color: "#BADB58"
				}
			},
			data:[{name:'呼和浩特市', value:7994},
			      {name:'包头市', value:4963},
			      {name:'呼伦贝尔市', value:37120},
			      {name:'兴安盟', value:81033},
			      {name:'通辽市', value:99139},
			      {name:'赤峰市', value:188297},
			      {name:'锡林郭勒盟', value:16256},
			      {name:'乌兰察布市', value:105978},
			      {name:'鄂尔多斯市', value:749},
			      {name:'巴彦淖尔市', value:14170},
			      {name:'乌海市', value:91},
			      {name:'阿拉善盟', value:54}]
		}]
};
function map(){
	var mapdatajson;//定义地图JSON
	var map_name;//定义地图名称
	mapdatajson='../mapData/neimenggu.json';//地图JSON
	map_name='neimenggu';//地图名称
	myChart_1 = echarts.init(document.getElementById('pkh_fb'));//声明id为mapChart的div为图形dom
	myChart_1.showLoading();//此方法是显示加载动画效果
	$.getJSON(mapdatajson, function (geoJson) {//获取已经定义好的json
		myChart_1.hideLoading();//隐藏加载动画
		echarts.registerMap(map_name, geoJson);//注册可用的地图，必须在包括 geo 组件或者 map 图表类型的时候才能使用
		myChart_1.setOption(option_map);
		myChart_1.on('click', function (params) {
//		    console.log(params);
		    if(params.name=="呼和浩特市"){
		    	$("#shi_1").click();
		    }else if(params.name=="包头市"){
		    	$("#shi_2").click();
		    }else if(params.name=="呼伦贝尔市"){
		    	$("#shi_3").click();
		    }else if(params.name=="兴安盟"){
		    	$("#shi_4").click();
		    }else if(params.name=="通辽市"){
		    	$("#shi_5").click();
		    }else if(params.name=="赤峰市"){
		    	$("#shi_6").click();
		    }else if(params.name=="锡林郭勒盟"){
		    	$("#shi_7").click();
		    }else if(params.name=="乌兰察布市"){
		    	$("#shi_8").click();
		    }else if(params.name=="鄂尔多斯市"){
		    	$("#shi_9").click();
		    }else if(params.name=="巴彦淖尔市"){
		    	$("#shi_10").click();
		    }else if(params.name=="乌海市"){
		    	$("#shi_11").click();
		    }else if(params.name=="阿拉善盟"){
		    	$("#shi_12").click();
		    }
		});
	});
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
	    		toastr["warning"]("", "查询数据为空");
	    	}else{
	    		rel = ret;
	    	}
	    },
	    error: function (ret) { 
	    	toastr["error"]("", "服务器异常或未查询到数据");
	    }  
	});
	return rel;
}

//打开省级领导帮扶干部页面
function open_sheng(){
	$("#sheng_modal").modal();
}

function bar1(){
	var myChart1 = echarts.init(document.getElementById('hfccs'));//声明id为mapChart的div为图形dom 
	
	var data1=ajax_async_t("/assa/getFcss.do",{},"json");
	var v1=0;
	var v2=0;
	var v3=0;
	var v4=0;
	var v5=0;
	var v6=0;
	var v7=0;
//	var html_0="";
//	var html="";
	$.each(data1,function(i,item){
		v1=parseInt(item.fc1)+parseInt(v1);
		v2=parseInt(item.fc2)+parseInt(v2);
		v3=parseInt(item.fc3)+parseInt(v3);
		v4=parseInt(item.fc4)+parseInt(v4);
		v5=parseInt(item.fc5)+parseInt(v5);
		v6=parseInt(item.fc6)+parseInt(v6);
		v7=parseInt(item.fc7)+parseInt(v7);
//		html += "<tr>" +
//				"<td>"+item.name+"</td>" +
//				"<td>"+item.fc1+"</td>" +
//				"<td>"+item.fc2+"</td>" +
//				"<td>"+item.fc3+"</td>" +
//				"<td>"+item.fc4+"</td>" +
//				"<td>"+item.fc5+"</td>" +
//				"<td>"+item.fc6+"</td>" +
//				"<td>"+item.fc7+"</td>" +
//				"</tr>";
	});
//	html_0 = "<tr>" +
//			"<td>全区</td>" +
//			"<td>"+v1+"</td>" +
//			"<td>"+v2+"</td>" +
//			"<td>"+v3+"</td>" +
//			"<td>"+v4+"</td>" +
//			"<td>"+v5+"</td>" +
//			"<td>"+v6+"</td>" +
//			"<td>"+v7+"</td>" +
//			"</tr>";
//	html_0 += html;
	var count8 =[{value:v2 , name:"产业发展和转移就业"},
	             {value:v3 , name:"易地扶贫搬迁"},
	             {value:v4 , name:"生态补偿"},
	             {value:v5 , name:"教育扶贫"},
	             {value:v6 , name:"大病救治"},
	             {value:v7 , name:"社会保障兜底"},
				];
	var option_ring = {//环形图
			title : {
				text: '分类扶持比例',
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center',
				y:'80'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{b} : {c}人"
			},
			
			legend: {
				x : 'center',
				y : 'bottom',
				data : ['产业发展和转移就业', '易地扶贫搬迁','生态补偿','教育扶贫','大病救治','社会保障兜底']
			},
			calculable : true,
			color:['#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                   '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                   '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'],
			series : [{
				
				name:'访问来源',
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center',
	                    formatter:'{b}\r\n{d}%'
	                },
	                emphasis: {
	                    show: true,
	                    textStyle: {
	                        fontSize: '30',
	                        formatter:'{b}\r\n{d}%',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
				  
				  data:count8
			}]
	}
	myChart1.setOption(option_ring);
//	$("#flfc_table").html(html_0);
}
//贫困人口状况
function bar2(){
	var myChart = echarts.init(document.getElementById('pkrkzk'));//声明id为mapChart的div为图形dom
	var option = {//柱状图
			legend: {
				data:['贫困人口状况'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0}户<br/>{a1}: {c1}人'
			},
			grid: {
				left: '0%',
				right: '3%',
				bottom: '10%',
				containLabel: true
			},
			color:['#6495ED','#C1232B'],
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:15,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:10,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:["国家重点旗县","自治区重点旗县","革命老区旗县","边境旗县","牧业旗县","少数民族旗县"
				      ]
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
			         {
			        	 name: "贫困户数",
			        	 type: 'bar',
			        	 data: ["250475","82454","247803","28608","103358","12365"]
			         },
			         {
			        	 name: "贫困人口数",
			        	 type: 'bar',
			        	 data: ["563505","182225","554701","63817","232587","28884"]
			         }
			         ]
	};
	myChart.setOption(option);
}
//各盟市减贫摘帽计划表
function bar3(){
	var myChart = echarts.init(document.getElementById('jpzmjh'));//声明id为mapChart的div为图形dom
	var option = {//柱状图
			title: {
				text: '2016年各盟市减贫摘帽计划',
				subtext: '贫困人口减贫（人）',
				subtextStyle: {
					color: 'black'
				},
				x:'center',
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0}人'
			},
			grid: {
				left: '0%',
				right: '3%',
				bottom: '10%',
				containLabel: true
			},
			color:['#6495ED','#C1232B'],
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:15,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:["呼和浩特市","包头市","呼伦贝尔市","兴安盟","通辽市","赤峰市","锡林郭勒盟","乌兰察布市","鄂尔多斯市","巴彦淖尔市","乌海市","阿拉善盟"
				      ]
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
			         {
			        	 name: "贫困人口减贫",
			        	 type: 'bar',
			        	 data: ["10690","6972","22302","22224","33911","52791","5668","28294","6474","18505","758","2933"]
			         }
			         ]
	};
	myChart.setOption(option);
}