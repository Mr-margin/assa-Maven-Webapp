$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	a1();
})
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
function a1(){
	myChart_1 = echarts.init(document.getElementById('tu_1'));//声明id为mapChart的div为图形dom
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_5.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.jpg">');
		
	}else{
	var count = [];
	var count_2 = [];
	var count_3 = [];
	var count_4 = [];
	var count_5 = [];
	var tables="";//对表格赋值的变量
	var A1=0,A2=0,A3=0,A4=0;
	$.each(data,function(i,item){
		if(typeof item.V1 == 'undefined'){
			item.V1='';
		}
		if(typeof item.V3_1 == 'undefined'){
			item.V3_1='';
		}
		if(typeof item.V4_1 == 'undefined'){
			item.V4_1='';
		}
		if(typeof item.V5_1 == 'undefined'){
			item.V5_1='';
		}
		if(typeof item.V6_1 == 'undefined'){
			item.V6_1='';
		}
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
		count[i]=item.V1;
		count_2[i]=item.V3_1;
		count_3[i]=item.V4_1;
		count_4[i]=item.V5_1;
		count_5[i]=item.V6_1;
		
		tables+='<tr><td class="text-center">'+Number(i+1)+'</td><td class="text-center">'+item.V1+'</td><td class="text-center">'+item.V3+'</td><td class="text-center">'+item.V4+'</td><td class="text-center">'+item.V5+'</td><td class="text-center">'+item.V6+'</td></tr>';
		
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
	tables+='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+A1+'</strong></td><td class="text-center"><strong>'+A2+'</strong></td><td class="text-center"><strong>'+A3+'</strong></td><td class="text-center"><strong>'+A4+'</strong></td></tr>';
	$("#tableChart").html(tables);
	
	var option = {//柱状图
			title: {
				text: '贫困人口劳动能力类型',
				textStyle:{fontWeight:'500',fontSize:'17'},
				subtext: '单位：百分比',
				subtextStyle: {
					color: 'black'
				},
				x:'center'
			},
			legend: {
				data:['普通劳动力','技能劳动力','丧失劳动力','无劳动力'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}:{c0}%<br/>{a1}:{c1}%<br/>{a2}:{c2}%<br/>{a3}:{c3}%'
			},
			color:['#2ec7c9','#b6a2de','#5ab1ef','red','#d87a80'],
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
					formatter: '{value}%'
				}
			},
			series: [
			         {
			        	 name: "普通劳动力",
			        	 type: 'bar',
			        	 data: count_2,
			         },
			         {
			        	 name: "丧失劳动力",
			        	 type: 'bar',
			        	 stack: '技能丧失',
			        	 data: count_4,
			         },
			         {
			        	 name: "无劳动力",
			        	 type: 'bar',
			        	 stack: '技能丧失',
			        	 data: count_5
			         },
			         {
			        	 name: "技能劳动力",
			        	 type: 'bar',
			        	 stack: '技能丧失',
			        	 data: count_3
			         }
			         ]
	};
	//点击柱状图触发事件
	myChart_1.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart_1.setOption(option);
	
	myChart_2 = echarts.init(document.getElementById('tu_2'));//声明id为mapChart的div为图形dom
	var option = {//环形图
			title : {
				text: '贫困人口劳动力类型统计',
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{b}: {c} 人  ({d}%)"
			},
			legend: {
				orient : 'vertical',
				x : 'left',
				y : 'bottom',
				data:['普通劳动力','技能劳动力','丧失劳动力','无劳动力'],
			},
			calculable : true,
			color:['#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80'],
			series : [{
				type:'pie',
				radius: ['50%', '70%'],
				center: ['50%', '57%'],
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
				data:[{value:A1 , name:"普通劳动力"},
				      {value:A2 , name:"技能劳动力"},
				      {value:A3 , name:"丧失劳动力"},
				      {value:A4 , name:"无劳动力"},
				      ]
			}]
	}
	myChart_2.setOption(option);
	}
	
}
