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
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_8.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2})); //调用ajax通用方法
	
	var V2=0,V3=0,V4=0,V5=0,V6=0,V7=0,V8=0,V20=0,V11=0,V12=0,V13=0,V14=0,V15=0,V16=0,V17=0,V18=0,V19=0;
	var V1 = obj.com;
	
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.jpg">');
	}else{
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
				if(typeof data[i].V20 == 'undefined'){
					V20 += 0;
				}else{
					V20 += parseInt(data[i].V20);
				}
				if(typeof data[i].V11 == 'undefined'){
					V11 += 0;
				}else{
					V11 += parseInt(data[i].V11);
				}
				if(typeof data[i].V12 == 'undefined'){
					V12 += 0;
				}else{
					V12 += parseInt(data[i].V12);
				}
				if(typeof data[i].V13 == 'undefined'){
					V13 += 0;
				}else{
					V13 += parseInt(data[i].V13);
				}
				if(typeof data[i].V14 == 'undefined'){
					V14 += 0;
				}else{
					V14 += parseInt(data[i].V14);
				}
				if(typeof data[i].V15 == 'undefined'){
					V15 += 0;
				}else{
					V15 += parseInt(data[i].V15);
				}
				if(typeof data[i].V16 == 'undefined'){
					V16 += 0;
				}else{
					V16 += parseInt(data[i].V16);
				}
				if(typeof data[i].V17 == 'undefined'){
					V17 += 0;
				}else{
					V17 += parseInt(data[i].V17);
				}
				if(typeof data[i].V18 == 'undefined'){
					V18 += 0;
				}else{
					V18 += parseInt(data[i].V18);
				}
				if(typeof data[i].V19 == 'undefined'){
					V19 += 0;
				}else{
					V19 += parseInt(data[i].V19);
				}
			}
		}

		var option = {//柱状图
				title: {
					text: '贫困人口在校生情况',
					x:'center',
					textStyle:{fontWeight:'500',fontSize:'17'},
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
				color:['#60C0DD'],
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
					data:['学前教育','小学','七年级','八年级','九年级','高中一年级','高中二年级','高中三年级','中职一年级','中职二年级','中职三年级','高职一年级','高职二年级','高职三年级','大专及以上']
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
				        	 data: [V4,V5,V6,V7,V8,V20,V11,
				        	        V12,V13,V14,V15,V16,V17,
				        	        V18,V19],
				         }
				         ]
		};
		myChart_1.setOption(option);
	}
}
