$(document).ready(function(){
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
})
var title='';
$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	a1();
})
var obj;
var myChart_1,myChart_2;
var title="";
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
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_4.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
		
	}else{
	var com_name;
	var count = [];
	var count_2 = [];
	var count_3 = [];
	var count_4 = [];
	var count_5 = [];
	var count_6 = [];
	var count_7 = [];
	var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0;
	if(data[0].V1=="合计"){
		com_name="内蒙古自治区";
	}else{
		com_name="内蒙古自治区";
	}
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
		if(typeof item.V7_1 == 'undefined'){
			item.V7_1='';
		}
		if(typeof item.V8_1 == 'undefined'){
			item.V8_1='';
		}
		count[i]=item.V1;
		count_2[i]=item.V3_1;
		count_3[i]=item.V4_1;
		count_4[i]=item.V5_1;
		count_5[i]=item.V6_1;
		count_6[i]=item.V7_1;
		count_7[i]=item.V8_1;
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

	var option = {//柱状图
			title: {
				text: '贫困人口文化程度组成情况',
				subtext: '单位：百分比',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center'
			},
			legend: {
				data:['学龄前儿童','文盲半文盲','小学','初中','高中','大专以上'],
				y:'bottom',
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}:{c0}%<br/>{a1}:{c1}%<br/>{a2}:{c2}%<br/>{a3}:{c3}%<br/>{a4}:{c4}%<br/>{a5}:{c5}%'
			},
			color:['#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80'],
			grid: {
				left: '0%',
				right: '0%',
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
			        	 name: "学龄前儿童",
			        	 type: 'bar',
			        	 stack: '疾病',
			        	 data: count_2
			         },
			         {
			        	 name: "文盲半文盲",
			        	 type: 'bar',
			        	 stack: '疾病',
			        	 data: count_3
			         },
			         {
			        	 name: "小学",
			        	 type: 'bar',
			        	 data: count_4
			         },
			         {
			        	 name: "初中",
			        	 type: 'bar',
			        	 data: count_5
			         },
			         {
			        	 name: "高中",
			        	 type: 'bar',
			        	 stack: '疾病',
			        	 data: count_6
			         },
			         {
			        	 name: "大专以上",
			        	 type: 'bar',
			        	 stack: '疾病',
			        	 data: count_7
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
				text: title,
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
				data:['学龄前儿童','文盲半文盲','小学','初中','高中','大专以上'],
			},
			calculable : true,
			color:['#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80'],
			series : [{
				type:'pie',
				radius: ['40%', '60%'],
				center: ['50%', '60%'],
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
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
				data:[{value:A1 , name:"学龄前儿童"},
				      {value:A2 , name:"文盲半文盲"},
				      {value:A3 , name:"小学"},
				      {value:A4 , name:"初中"},
				      {value:A5 , name:"高中"},
				      {value:A6 , name:"大专以上"},
				      ]
			}]
	}
	myChart_2.setOption(option);
	}
}
