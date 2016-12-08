$(document).ready(function(){
	title='贫困户生产生活条件困难情况';
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
	table();
	bar();
	bar2();
});
var obj;
//表格数据
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
function table(){
	var data = ajax_async_t("/assa/getBfdxHu_8.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0;
		$.each(data,function(i,item){
			if (item.com_name == '汇总'){
			}else{
				html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td><td>'+item.z_hu+'</td>'+
				'<td>'+item.v1+'</td>'+
				'<td>'+item.v3+'</td>'+
				'<td>'+item.v5+'</td>'+
				'<td>'+item.v7+'</td>'+
				'<td>'+item.v9+'</td>'+
				'<td>'+item.v11+'</td>'+
				'<td>'+item.v13+'</td>'+
				'</tr>';
			}
			if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
				
			}else{
				A1=parseInt(item.z_hu)+parseInt(A1);
				
			}
			if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
				
			}else{
				A2=parseInt(item.v1)+parseInt(A2);
				
			}
			if(item.v3 == '' || item.v3 == null || item.v3 == undefined){
				
			}else{
				A3=parseInt(item.v3)+parseInt(A3);
				
			}
			if(item.v5 == '' || item.v5 == null || item.v5 == undefined){
				
			}else{
				A4=parseInt(item.v5)+parseInt(A4);
				
			}
			if(item.v7 == '' || item.v7 == null || item.v7 == undefined){
				
			}else{
				A5=parseInt(item.v7)+parseInt(A5);
				
			}
			if(item.v9 == '' || item.v9 == null || item.v9 == undefined){
				
			}else{
				A6=parseInt(item.v9)+parseInt(A6);
				
			}
			if(item.v11 == '' || item.v11 == null || item.v11 == undefined){
				
			}else{
				A7=parseInt(item.v11)+parseInt(A7);
				
			}
			if(item.v13 == '' || item.v13 == null || item.v13 == undefined){
				
			}else{
				A8=parseInt(item.v13)+parseInt(A8);
				
			}
//			A1=parseInt(item.z_hu)+parseInt(A1);
//			A2=parseInt(item.v1)+parseInt(A2);
//			A3=parseInt(item.v3)+parseInt(A3);
//			A4=parseInt(item.v5)+parseInt(A4);
//			A5=parseInt(item.v7)+parseInt(A5);
//			A6=parseInt(item.v9)+parseInt(A6);
//			A7=parseInt(item.v11)+parseInt(A7);
//			A8=parseInt(item.v13)+parseInt(A8);
		})
		html+='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td><td><strong>'+A1+'</strong></td><td><strong>'+A2+'</strong></td><td><strong>'+A3+'</strong></td><td><strong>'+A4+'</strong></td><td><strong>'+A5+'</strong></td><td><strong>'+A6+'</strong></td><td><strong>'+A7+'</strong></td><td><strong></strong></td></tr>';
		$("#scsh_table").html(html);
	}
	 
}

//条形图
function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_8.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var com_name = [];
		var num = [];
		var v1 = [];
		var v1_1 = [];  var v3 =[]; var v3_1 = []; var v5 = []; var v5_1 =[]; var v7 =[];var v7_1=[];var v9=[];var v9_1=[];var v11 = [];var v11_1=[];
		$.each(data,function(i,item){
			
				v1[i] = ((item.v1)*(100/ item.z_hu)).toFixed(0);
				v1_1[i]=((item.z_hu-item.v1)*(100/ item.z_hu)).toFixed(0);

				v3[i] = ((item.v3)*(100/ item.z_hu)).toFixed(0);
				v3_1[i]=((item.z_hu-item.v3)*(100/ item.z_hu)).toFixed(0);

				v5[i] = ((item.v5)*(100/ item.z_hu)).toFixed(0);
				v5_1[i]=((item.z_hu-item.v5)*(100/ item.z_hu)).toFixed(0);

				v7[i] = ((item.v7)*(100/ item.z_hu)).toFixed(0);
				v7_1[i]=((item.z_hu-item.v7)*(100/ item.z_hu)).toFixed(0);

				v9[i] = ((item.v9)*(100/ item.z_hu)).toFixed(0);
				v9_1[i]=((item.z_hu-item.v9)*(100/ item.z_hu)).toFixed(0);
				v11[i] = ((item.v11)*(100/ item.z_hu)).toFixed(0);
				v11_1[i]=((item.z_hu-item.v11)*(100/ item.z_hu)).toFixed(0);
				com_name[i] = item.com_name;
			

		})
		var placeHoledStyle = {
			normal:{
				barBorderColor:'rgba(0,0,0,0)',
				color:'rgba(0,0,0,0)'
			},
			emphasis:{
				barBorderColor:'rgba(0,0,0,0)',
				color:'rgba(0,0,0,0)'
			}
		};
		var dataStyle = { 
				normal: {
					show: true,
					position: 'right',
					formatter: '{c}%',
					textStyle: {
						color:"black"
					}
				}
		};
		option = {
				color:['#7B68EE','#0000FF','#F08080','#1E90FF','#2F4F4F','#F08080','#808080'],
				title: {
					text: title,
					subtext: '单位：百分比',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'},
					x:'center',
				},
				tooltip : {
					trigger: 'axis',
					axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					},
					formatter : '{b}<br/>{a0}:{c0}%<br/>{a2}:{c2}%<br/>{a4}:{c4}%<br/>{a6}:{c6}%<br/>{a8}:{c8}%<br/>{a10}:{c10}%'
				},
				legend: {
					y: 55,
//					itemGap : document.getElementById('main').offsetWidth / 8,
					data:['饮水困难','无安全饮水','未通生活用电','未通广播电视','住房是危房','无卫生厕所']
				},
				grid: {
					y: 80,
					y2: 30
				},
				xAxis :{
					type : 'value',
					position: 'top',
					splitLine: {show: false},
					axisLabel: {show: false}
				},
				yAxis :{
					type : 'category',
					splitLine: {show: false},
					data : com_name
				},
				series : [
				          {
				        	  name:'饮水困难',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v1
				          },
				          {
				        	  name:'饮水困难',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v1_1
				          },
				          {
				        	  name:'无安全饮水',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v3
				          },
				          {
				        	  name:'无安全饮水',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v3_1
				          },
				          {
				        	  name:'未通生活用电',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v5
				          },
				          {
				        	  name:'未通生活用电',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v5_1
				          },
				          {
				        	  name:'未通广播电视',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v7
				          },
				          {
				        	  name:'未通广播电视',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v7_1
				          },
				          {
				        	  name:'住房是危房',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v9
				          },
				          {
				        	  name:'住房是危房',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v9_1
				          },
				          {
				        	  name:'无卫生厕所',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v11
				          },
				          {
				        	  name:'无卫生厕所',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v11_1
				          },
				          ]
		};
		myChart_1 = echarts.init(document.getElementById('bar'));
		myChart_1.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart_1.setOption(option);            
	}
	
	         

}
function bar2(){
	var data = ajax_async_t("/assa/getBfdxHu_8.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var com_name = [];
		var v13 = [];
		$.each(data,function(i,item){
			
				com_name[i] = item.com_name;
				v13[i]=item.v13
		

		})
		option = {
			color:['#FA8072'],
			title : {
				text: '贫困户人均住房面积',
				subtext: '单位：平方米',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'},
				x:'center'
			},
			tooltip : {
				trigger: 'axis',
			},
			legend: {
				data:['住房面']
			},
			calculable : true,
			grid: {
				left: '0%',
				right: '5%',
				bottom: '8%',
				containLabel: true
			},
			xAxis : {
				type: "category",//坐标轴类型，横轴默认为category，纵轴默认为数值型value
				name: "行政区划",//坐标轴名称
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				splitLine: {show: false},//分隔线，默认是显示，show来控制显示状态
				data:com_name//X轴的坐标
			},
			yAxis : {
				type : 'value'
			},
			series : [
			          {
			        	  name:'人均住房面积',
			        	  type:'bar',
			        	  data:v13,
			        	  markPoint : {
			        		  label: {
			        			  normal: {
			        				  show: true,
			        				  textStyle: {
			        					  color:"black"
			        				  }
			        			  }
			        		  },
			        		  data : [
			        		          {type : 'max', name: '最大值'},
			        		          {type : 'min', name: '最小值'}
			        		          ]
			        	  },
			        	  markLine : {
			        		  label: {
			        			  normal: {
			        				  show: true,
			        				  textStyle: {
			        					  color:"black"
			        				  }
			        			  }
			        		  },
			        		  data : [
			        		          {type : 'average', name: '平均值'}
			        		          ]
			        	  }
			          }
			          ]
		};
		myChart_2 = echarts.init(document.getElementById('bar2'));
		//点击事件
		myChart_2.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart_2.setOption(option);     
	}
	               

}