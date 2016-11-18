$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	a1();
})
var obj;
var myChart_1;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart_1){
			myChart_1.resize();
		}
	}catch(e){
	}
};
function a1(){
	myChart_1 = echarts.init(document.getElementById('tu_1'));//声明id为mapChart的div为图形dom
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_7.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.jpg">');
		
	}else{
	var com_name;
	var tables="";//对表格赋值的变量
	if(data[0].V1=="合计"){
		com_name="内蒙古自治区";
	}else{
		com_name="内蒙古自治区";
	}
	
	if(typeof data[0].V1 == 'undefined'){
		data[0].V1='';
	}
	if(typeof data[0].V3 == 'undefined'){
		data[0].V3='';
	}
	if(typeof data[0].V4 == 'undefined'){
		data[0].V4='';
	}
	if(typeof data[0].V5 == 'undefined'){
		data[0].V5='';
	}
	if(typeof data[0].V6 == 'undefined'){
		data[0].V6='';
	}
	if(typeof data[0].V7 == 'undefined'){
		data[0].V7='';
	}
	if(typeof data[0].V8 == 'undefined'){
		data[0].V8='';
	}
	if(typeof data[0].V16 == 'undefined'){
		data[0].V16='';
	}
	if(typeof data[0].V11 == 'undefined'){
		data[0].V11='';
	}
	if(typeof data[0].V12 == 'undefined'){
		data[0].V12='';
	}
	if(typeof data[0].V14 == 'undefined'){
		data[0].V14='';
	}
	if(typeof data[0].V14 == 'undefined'){
		data[0].V14='';
	}
	if(typeof data[0].V15 == 'undefined'){
		data[0].V15='';
	}
	
	var option = {//柱状图
			title: {
				text: '贫困人口上一年度务工时间统计',
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center',
				subtext: '单位：人',
				subtextStyle: {
					color: 'black'
				}
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
			},
			color:['#F0805A'],
			grid: {
				left: '0%',
				right: '3%',
				bottom: '10%',
				containLabel: true
			},
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:['1个月','2个月','3个月','4个月','5个月','6个月','7个月','8个月','9个月','10个月','11个月','12个月']
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
			         {
			        	 name: "人数",
			        	 type: 'bar',
			        	 stack: '务工',
			        	 itemStyle: {
			        		 normal: {
			        			 color: '#B5C334'
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
			        	 data: [data[0].V3,data[0].V4,
			        	        data[0].V5,data[0].V6,
			        	        data[0].V7,data[0].V8,
			        	        data[0].V16,data[0].V11,
			        	        data[0].V12,data[0].V13,
			        	        data[0].V14,data[0].V15,
			        	        ],
			         }
			         ]
	};
	myChart_1.setOption(option);
	}
}
