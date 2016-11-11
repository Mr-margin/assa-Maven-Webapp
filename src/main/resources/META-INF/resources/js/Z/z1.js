$(function () {
	//省级领导
	$("#option1").click(function (){
		$(".shengji").show();
		$(".shiji").hide();
		$(".xianji").hide();
		$(".xianjiyixia").hide();
	});
	
	//市级领导
	$("#option2").click(function (){
		$(".shengji").hide();
		$(".shiji").show();
		$(".xianji").hide();
		$(".xianjiyixia").hide();
	});
	
	//县级领导
	$("#option3").click(function (){
		$(".shengji").hide();
		$(".shiji").hide();
		$(".xianji").show();
		$(".xianjiyixia").hide();
	});
	
	//县级以下干部
	$("#option4").click(function (){
		$(".shengji").hide();
		$(".shiji").hide();
		$(".xianji").hide();
		$(".xianjiyixia").show();
	});
	bar1();
	bar2();
	bar3();
});

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
	
	//var myChart = echarts.init(document.getElementById('zpyy'));//声明id为mapChart的div为图形dom 
	var myChart1 = echarts.init(document.getElementById('hfccs'));//声明id为mapChart的div为图形dom 
	
	var data1=ajax_async_t("/assa/getFcss.do",{},"json");
	var data = ajax_async_t("/assa/getBfdxHu_3.do",{t1:"0",t2:"1",name:"内蒙古自治区"},"json"); //调用ajax通用方法
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
	var count7 =[{value:v2 , name:"产业发展和转移就业"},
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
				  
				  data:count7
			}]
	}
	var option = {
			title: {
				text: '全区主要致贫原因情况统计表（单位：户）',
				x:'center'
			},
			legend: {
				data:['因病致贫','缺资金','因灾致贫','因残致贫'],
				x:'left'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				}
			},
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:0,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:2,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:[]
			},
			yAxis: {
				axisLabel: {
		            formatter: '{value} '
		        }
			},
			series: [
			         {
			        	 name: '因病致贫',
			        	 type: 'bar',
			        	 data: []
			         },
			         {
			        	 name: '缺资金',
			        	 type: 'bar',
			        	 data: []
			         },
			         {
			        	 name: '因灾致贫',
			        	 type: 'bar',
			        	 data: []
			         },
			         {
			        	 name: '因残致贫',
			        	 type: 'bar',
			        	 data: []
			         },
			    
			         ]
	};
	$.each(data,function(i,indx){
		count[i]=indx.com_name;
		count2[i]=indx.v1;//因病
		count3[i]=indx.v17;//缺资金
		count4[i]=indx.v7;//因灾
		count5[i]=indx.v3;//因残
	});
	option.xAxis.data=count;
	option.series[0].data=count2;
	option.series[1].data=count3;
	option.series[2].data=count4;
	option.series[3].data=count5;
	myChart1.setOption(option_ring);
	//myChart.setOption(option);
}
//贫困人口状况
function bar2(){
	var myChart = echarts.init(document.getElementById('pkrkzk'));//声明id为mapChart的div为图形dom
	var option = {//柱状图
			title: {
				text: '贫困人口状况',
				x:'center',
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
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
					rotate:10,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:["国家重点旗县","自治区重点旗县","牧业旗县","革命老区旗县","边境旗县"
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
			        	 data: ["250475","82454","103358","247803","28608"]
			         },
			         {
			        	 name: "贫困人口数",
			        	 type: 'bar',
			        	 data: ["563505","182225","232587","554701","63817"]
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
					rotate:0,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
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