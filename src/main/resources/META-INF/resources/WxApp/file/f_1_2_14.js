$(document).ready(function(){
	title='贫困户住房面积';
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
	obj.com = Request['com'];
	obj.year = Request['year'];
	obj.q1 = Request['q1'];
	obj.q2 = Request['q2'];
	obj.q3 = Request['q3'];
	obj.q4 = Request['q4'];
	obj.q5 = Request['q5'];
	obj.t1 = Request['t1'];
	obj.t2 = Request['t2'];

	table();
	bar();
});
var obj = {};
var myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHu_14.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0;
		$.each(data,function(i,item){

			html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td><td class="text-center">'+item.z_hu+'</td>'+
			'<td class="text-center">'+item.v1+'</td>'+
			'<td class="text-center">'+item.v3+'</td>'+
			'<td class="text-center">'+item.v5+'</td>'+
			'<td class="text-center">'+item.v7+'</td>'+
			'<td class="text-center">'+item.v9+'</td>'+
			'<td class="text-center">'+item.v11+'</td>'+
			'<td class="text-center">'+item.v13+'</td>'+
			'</tr>';
			if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
				
			}else{
				A1+=parseInt(item.z_hu)
				
			}
			if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
				
			}else{
				A2+=parseInt(item.v1);
				
			}
			if(item.v3 == '' || item.v3 == null || item.v3 == undefined){
				
			}else{
				A3+=parseInt(item.v3);
				
			}
			if(item.v5 == '' || item.v5 == null || item.v5 == undefined){
				
			}else{
				A4+=parseInt(item.v5);
				
			}
			if(item.v7 == '' || item.v7 == null || item.v7 == undefined){
				
			}else{
				A5+=parseInt(item.v7);
				
			}
			if(item.v9 == '' || item.v9 == null || item.v9 == undefined){
				
			}else{
				A6+=parseInt(item.v9);
				
			}
			if(item.v11 == '' || item.v11 == null || item.v11 == undefined){
				
			}else{
				A7+=parseInt(item.v11);
				
			}
			if(item.v13 == '' || item.v13 == null || item.v13 == undefined){
				
			}else{
				A8+=parseInt(item.v13);
				
			}
//			A1+=parseInt(item.z_hu)
//			A2+=parseInt(item.v1)
//			A3+=parseInt(item.v3)
//			A4+=parseInt(item.v5)
//			A5+=parseInt(item.v7)
//			A6+=parseInt(item.v9)
//			A7+=parseInt(item.v11)
//			A8+=parseInt(item.v13)

		})
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
		'<td class="text-center"><strong>'+A1+'</strong></td>'+
		'<td class="text-center"><strong>'+A2+'</strong></td>'+
		'<td class="text-center"><strong>'+A3+'</strong></td>'+
		'<td class="text-center"><strong>'+A4+'</strong></td>'+
		'<td class="text-center"><strong>'+A5+'</strong></td>'+
		'<td class="text-center"><strong>'+A6+'</strong></td>'+
		'<td class="text-center"><strong>'+A7+'</strong></td>'+
		'<td class="text-center"><strong>'+A8+'</strong></td>'+

		'</tr>';
		$("#scsh_table").html(html);
	}
	 
}

//条形图
function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_14.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var com_name = [];
		var num = [];
		var v1 = [];
		var v1_1 = [];  var v3 =[]; var v3_1 = []; var v5 = []; var v5_1 =[]; var v7 =[];var v7_1=[];var v9=[];var v9_1=[];var v11 = [];var v11_1=[];var v13 = [];var v13_1=[];
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

				v13[i] = ((item.v13)*(100/ item.z_hu)).toFixed(0);
				v13_1[i]=((item.z_hu-item.v13)*(100/ item.z_hu)).toFixed(0);

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
				title: {
					text: title,
//					subtext: 'From ExcelHome',
					subtextStyle: {
						color: 'black'
					},
					x:'center',
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				tooltip : {
					trigger: 'axis',
					axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					},
					formatter : '{b}<br/>{a0}:{c0}%<br/>{a2}:{c2}%<br/>{a4}:{c4}%<br/>{a6}:{c6}%<br/>{a8}:{c8}%<br/>{a10}:{c10}%'
				},
				color:['#C1232B','#E9781C','#D76662','#33CC00','#FFC000','#00AF50','#FFCC99'],
				legend: {
					y: 55,
//					itemGap : document.getElementById('main').offsetWidth / 8,
					data:['0平米','10平米以内',/*'10-25平米',*/'25-50平米','50-75平米','75-100平米','100平米以上']
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
				        	  name:'0平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v1
				          },
				          {
				        	  name:'0平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v1_1
				          },
				          {
				        	  name:'10平米以内',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v3
				          },
				          {
				        	  name:'10平米以内',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v3_1
				          },
//				          {
//				          name:'10-25平米',
//				          type:'bar',
//				          stack: '总量',
//				          label : dataStyle,
//				          data:v5
//				          },
//				          {
//				          name:'10-25平米',
//				          type:'bar',
//				          stack: '总量',
//				          itemStyle: placeHoledStyle,
//				          data:v5_1
//				          },
				          {
				        	  name:'25-50平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v7
				          },
				          {
				        	  name:'25-50平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v7_1
				          },
				          {
				        	  name:'50-75平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v9
				          },
				          {
				        	  name:'50-75平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v9_1
				          },
				          {
				        	  name:'75-100平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label : dataStyle,
				        	  data:v11
				          },
				          {
				        	  name:'75-100平米',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v11_1
				          },
				          {
				        	  name:'100平米以上',
				        	  type:'bar',
				        	  stack: '总量',
				        	  label: dataStyle,
				        	  data:v13
				          },
				          {
				        	  name:'100平米以上',
				        	  type:'bar',
				        	  stack: '总量',
				        	  itemStyle: placeHoledStyle,
				        	  data:v13_1
				          }
				          ]
		};
		myChart = echarts.init(document.getElementById('bar'));
		//点击事件
		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart.setOption(option);   
	}
	                 

}
