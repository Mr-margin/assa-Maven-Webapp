$(document).ready(function() {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	pkcwtp();
	biaoge()
});

function biaoge(){
	var v1=0;
	var v2=0;
	var v3=0;
	var v4=0;
	var v5=0;
	var v6=0;
	var aa;//名称
	var bb;//人数
	var cc;//户数
	var dd;//落实帮扶责任人
	var ee;//制定帮扶计划
	var ff;//帮扶措施
	var gg;//走访记录
	var hh;//走访记录
	var data = ajax_async_t("/assa/getwtp.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data == '0'){
		$('#sbbz').html('<h3>暂无数据</h3>')
		$("#xiafangzongbiao").html('<h3>暂无数据</h3>');
	}else{
		var html="";
    	$.each(data, function(i,item) {
    		aa=i+1
    		//户数
    		if(item.name=="0"||item.name==null){
    			cc='0';
    		}else{
    			cc=''+item.name+'';
    		}
    		//人数
    		if(item.h1=="0"||item.h1==null){
    			bb='0';
    		}else{
    			bb=''+item.h1+'';
    		}
    		//落实帮扶责任人
    		if(item.r1=="0"||item.r1==null){
    			dd='0';
    		}else{
    			dd=''+item.r1+'';
    		}
    		//制定帮扶计划
    		if(item.h2=="0"||item.h2==null){
    			ee='0';
    		}else{
    			ee=''+item.h2+'';
    		}
    		if(item.r2=="0"||item.r2==null){
    			ff='0';
    		}else{
    			ff=''+item.r2+'';
    		}
    		if(item.h3=="0"||item.h3==null){
    			gg='0';
    		}else{
    			gg=''+item.h3+'';
    		}
    		if(item.r3=="0"||item.r3==null){
    			hh='0';
    		}else{
    			hh=''+item.r3+'';
    		}
    		v1=parseInt(item.r1)+parseInt(v1);
    		v2=parseInt(item.h1)+parseInt(v2);
    		v3=parseInt(item.h2)+parseInt(v3);
    		v4=parseInt(item.r2)+parseInt(v4);
    		v5=parseInt(item.h3)+parseInt(v5);
    		v6=parseInt(item.r3)+parseInt(v6);
    		
    		//循环赋值
    		html+='<tr><td class="text-center">'+aa+'</td><td class="text-center">'+cc+'</td><td class="text-center">'+bb+'</td><td class="text-center">'+dd+'</td><td class="text-center">'+ee+'</td><td class="text-center">'+ff+'</td><td class="text-center">'+gg+'</td><td class="text-center">'+hh+'</td></tr>';
    	});
    	aa=aa+1;
    	html+='<tr><td class="text-center">'+aa+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v2+'</strong></td><td class="text-center"><strong>'+v3+'</strong></td><td class="text-center"><strong>'+v4+'</strong></td><td class="text-center"><strong>'+v5+'</strong></td><td class="text-center"><strong>'+v6+'</strong></td></tr>'
		//对下方的表格赋值
		$("#xiafangzongbiao").html(html);
	}
}
var myChart;
var myChart1;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
	try{
		if(myChart1){
			myChart1.resize();
		}
	}catch(e){
	}
};
//统计图方法
function pkcwtp(){
	 myChart = echarts.init(document.getElementById('wtph'));//声明id为mapChart的div为图形dom 
	 myChart1 = echarts.init(document.getElementById('wtpr'));//声明id为mapChart的div为图形dom 
	var data = ajax_async_t("/assa/getwtp.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	if(data == '0'){
		$('#wtph').html('<h3>暂无数据</h3>')
		$('#wtpr').html('<h3>暂无数据</h3>')
		$("#xiafangzongbiao").html('<h3>暂无数据</h3>');
	}else{
		var count=[];
		var count2=[];
		var count3=[];
		var count4=[];
		var count5=[];
		var count6=[];
		var count7=[];
		
		var option = {
				title: {
					text: '贫困村未脱贫贫困户',
					subtext: '单位：户',
					subtextStyle: {
						color: 'black'
					},
					x:'center',
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					data:['贫困村户数','非贫困村户数'],
					y:'bottom'
				},
				grid: {
					left: '0%',
					right: '2%',
					bottom: '15%',
					containLabel: true
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					}
				},
				color:['#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'],
				xAxis: {
					axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
					data:[]
				},
				yAxis: {
					axisLabel: {
			            formatter:function (value, index) {
							var wan = 10000;
						    var data ;
						    if (index === 0) {
						    	data=0
						    }else{
						    	data= Number(value)/Number(wan)+"万户";
						    }
						    return data;
						}
			        }
				},
				series: [
				         {
				        	 name: '贫困村户数',
				        	 type: 'bar',
				        	 data: []
				         },
				         {
				        	 name: '非贫困村户数',
				        	 type: 'bar',
				        	 data: []
				         }
				         ]
		};
		
		var option1 = {
				title: {
					text: '贫困村未脱贫贫困人数概况',
					x:'center',
					subtext: '单位：人',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					data:['贫困村人数','非贫困村人数'],
					y:'bottom'
				},
				grid: {
					left: '0%',
					right: '2%',
					bottom: '15%',
					containLabel: true
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					}
				},
				color:['#FAD860','#F3A43B','#60C0DD'],
				xAxis: {
					axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
					data:[]
				},
				yAxis: {
					axisLabel: {
			            formatter: function (value, index) {
							var wan = 10000;
						    var data ;
						    if (index === 0) {
						    	data=0
						    }else{
						    	data= Number(value)/Number(wan)+"万人";
						    }
						    return data;
						}
			        }
				},
				series: [
				         {
				        	 name: '贫困村人数',
				        	 type: 'bar',
				        	 data: []
				         },
				         {
				        	 name: '非贫困村人数',
				        	 type: 'bar',
				        	 data: []
				         },
				         ]
		};
		$.each(data,function(i,indx){
			count[i]=indx.name;
			count2[i]=indx.h2;
			count3[i]=indx.h3;
			count4[i]=indx.r2;
			count5[i]=indx.r3;
		});
		option.xAxis.data=count;
		option.series[0].data=count2;
		option.series[1].data=count3;
	
		option1.xAxis.data=count;
		option1.series[0].data=count4;
		option1.series[1].data=count5;
		//点击事件
		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		//点击事件
		myChart1.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart.setOption(option);
		myChart1.setOption(option1);
	}
}