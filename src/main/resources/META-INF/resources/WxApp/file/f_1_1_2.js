$(document).ready(function(){
	title='贫困人口年龄分组';
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
	obj.com = Request['com'];
	obj.year = Request['year'];
	obj.q1 = Request['q1'];
	obj.q2 = Request['q2'];
	obj.q3 = Request['q3'];
	obj.q4 = Request['q4'];
	obj.q5 = Request['q5'];
	obj.t1 = Request['t1'];
	obj.t2 = Request['t2'];
	a_1();
	a_2();
	a_3();
})
var obj = {};
var myChart_1,myChart_2;
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
};
function a_1(){
	myChart_1 = echarts.init(document.getElementById('tu_1'));//声明id为mapChart的div为图形dom
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_2.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}else{
		var V2=0,V3=0,V4=0,V5=0,V6=0,V7=0,V8=0;
		var V1 = obj.com;
		if(data.length>0){
			for(var i=0; i<data.length; i++){

				if(typeof data[i].V3 == 'undefined'){
					V3 += 0;
				}else{
					V3 += parseInt(data[i].V3);
				}
				if(typeof data[i].V4 == 'undefined'){
					V4 += 0;
				}else{
					V4 += parseInt(data[i].V4);
				}
				if(typeof data[i].V5 == 'undefined'){
					V5 += 0;
				}else{
					V5 += parseInt(data[i].V5);
				}
				if(typeof data[i].V6 == 'undefined'){
					V6 += 0;
				}else{
					V6 += parseInt(data[i].V6);
				}
				if(typeof data[i].V7 == 'undefined'){
					V7 += 0;
				}else{
					V7 += parseInt(data[i].V7);
				}
				if(typeof data[i].V8 == 'undefined'){
					V8 += 0;
				}else{
					V8 += parseInt(data[i].V8);
				}
			}
		}
		
		var option = {//柱状图
				title: {
					text: title,
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
					formatter: "{a}<br/>{b}: {c} 人"
				},
				grid: {
					left: '0%',
					right: '2%',
					bottom: '8%',
					containLabel: true
				},
				color:['#26C0C0'],
				xAxis: {
					axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
					data:['≤16岁','16-30岁','30-40岁','40-50岁','50-60岁','≥60岁']
				},
				yAxis: {
					axisLabel: {
						formatter: '{value}'
					}
				},
				series: [
				         {
				        	 name: V1,
				        	 type: 'bar',
				        	 itemStyle: {
				        		 normal: {
				        			 color: function(params) {
				        			 // build a color map as your need.
				        			 var colorList = [
				        			 '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0','#FCCE10',
				        			 '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
				        			 '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
				        			 ];
				        			 return colorList[params.dataIndex]
				        			 },
				        		 }
				        	 },
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
				        	 data: [V3,V4,V5,V6,V7,V8]
				         }
				         ]
		};
		myChart_1.setOption(option);
	}
}
function a_2(){
	myChart_2 = echarts.init(document.getElementById('tu_2'));//声明id为mapChart的div为图形dom
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_2_2.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}else{
		var com_name;
		var count = [];
		var count_2 = [];
		if(data[0].V1=="合计"){
			com_name="内蒙古自治区";
		}else{
			com_name="内蒙古自治区";
		}
		$.each(data,function(i,item){
			if(typeof item.V1 == 'undefined'){
				item.V1='';
			}
			count[i]=item.V1;
			if(typeof item.V8_1 == 'undefined'){
				item.V8_1='';
			}
			count_2[i]=item.V8_1;
		});

		var option = {
				title: {
					text: '60岁以上贫困人口占比顺位排序',
					textStyle:{fontWeight:'500',fontSize:'17'},
					subtext: '单位：百分比',
					subtextStyle: {
						color: 'black'
					},
					x:'center'
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					},
					formatter: "{a}<br/>{b}: {c}%"
				},
				grid: {
					left: '0%',
					right: '2%',
					bottom: '8%',
					containLabel: true
				},
				color:['#F0805A'],
				xAxis: {
					axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:30,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
					data:count
				},
				yAxis: {
					axisLabel: {
						formatter: '{value}%'
					}
				},
				
				series: [
				         {
				        	 name: com_name,
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
				        	 data: count_2
				         }
				         ]
		};
		myChart_2.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
//		    $('#v2', window.parent.document).val(diqu);
		    parent.setSelVal(diqu);
		   

		});
		myChart_2.setOption(option);
	}
}
function a_3(){
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_2_3.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	var tables="";//对表格赋值的变量
	var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0;
	$.each(data,function(i,item){
		if(typeof item.V1 == 'undefined'){
			item.V1='';
		}
		if(typeof item.V3 == 'undefined'){
			item.V3='';
		}
		if(typeof item.V4 == 'undefined'){
			item.V4='';
		}
		if(typeof item.V5 == 'undefined'){
			item.V5='';
		}
		if(typeof item.V6 == 'undefined'){
			item.V6='';
		}
		if(typeof item.V7 == 'undefined'){
			item.V7='';
		}
		if(typeof item.V8 == 'undefined'){
			item.V8='';
		}
		
		tables+='<tr><td class="text-center">'+Number(i+1)+'</td><td class="text-center">'+item.V1+'</td><td class="text-center">'+item.V3+'</td>'
		tables+='<td class="text-center">'+item.V4+'</td><td class="text-center">'+item.V5+'</td>'
		tables+='<td class="text-center">'+item.V6+'</td><td class="text-center">'+item.V7+'</td>'
		tables+='<td class="text-center">'+item.V8+'</td></tr>'
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
		if(item.V7 == '' || item.V7 == null || item.V7 == undefined){
			
		}else{
			A5=parseInt(item.V7)+parseInt(A5);
		}
		if(item.V8 == '' || item.V8 == null || item.V8 == undefined){
			
		}else{
			A6=parseInt(item.V8)+parseInt(A6);
		}
		
	});
	tables+='<tr><td class="text-center"><strong>'+Number(data.length+1)+'</strong></td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+A1+'</strong></td><td class="text-center"><strong>'+A2+'</strong></td><td class="text-center"><strong>'+A3+'</strong></td><td class="text-center"><strong>'+A4+'</strong></td><td class="text-center"><strong>'+A5+'</strong></td><td class="text-center"><strong>'+A6+'</strong></td></tr>';
	$("#tableChart").html(tables);
}