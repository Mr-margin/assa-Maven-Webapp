$(document).ready(function(){
	title='贫困人口分布情况';
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
//	canshu=Request['canshu']; 
//	obj = JSON.parse(canshu);
	obj.com = Request['com'];
	obj.year = Request['year'];
	obj.q1 = Request['q1'];
	obj.q2 = Request['q2'];
	obj.q3 = Request['q3'];
	obj.q4 = Request['q4'];
	obj.q5 = Request['q5'];
	obj.t1 = Request['t1'];
	obj.t2 = Request['t2'];
	a1();
	table();
	
})
var obj = {};

var myChart_ring,myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
var diqu="";
var a=0;
var title='';
function a1(){
	
	
//	myChart_ring = echarts.init(document.getElementById('tu_3'));//声明id为myChart_ring的div为图形dom
	var data = JSON.parse(ajax_async_t("../../getPKC_1_1_1.do", {name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"text","1")); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
		
	}else{
		var tables="";//对表格赋值的变量
		var count=[];//区划名称
		var count2=[];//女性人数
		var count3=[];//少数民族
		var count4=[];//新农合
		var count5=[];//新农保
		var count6=[];//环形图
		var A1=0;//女性人口总数
		var A2=0;//女性人口总数
		var A3=0;//女性人口总数
		var A4=0;//女性人口总数
		
		var count0=[];//总人数
		
		$.each(data,function(i,item){
			
			if(typeof item.V2 == 'undefined'){
				item.V2='';
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
			
			count0[i]=item.V2;
			count[i]=item.V1;
			count2[i]=item.V3;
			count3[i]=item.V4;
			count4[i]=item.V5;
			count5[i]=item.V6;
			count6[i]={value:item.V2 , name:item.V1}
			if(item.V5!='' && typeof item.V3!=''){
				A3=parseInt(item.V5)+parseInt(A3);
			}
			if(item.V6!='' && typeof item.V4!=''){
				A4=parseInt(item.V6)+parseInt(A4);
			}
		});
		
		var a=parent.level;

		if(a =='3' || a =='4'){
			
			$('#tu1').attr("class","center-block col-sm-12");
			$('#table').hide();
		}
		myChart = echarts.init(document.getElementById('tu_1'));//声明id为myChart的div为图形dom

		var title_text;
		if(obj.com=="内蒙古自治区"){
			title_text="各盟市贫困人口占比"
		}else{
			title_text="各区县贫困人口占比"
		}

		var option_ring = {//环形图
				title : {
					text: title_text,
					x:'left',
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				tooltip : {
					trigger: 'item',
					formatter: "{b} 贫困人口 : {c}人"
				},
				legend: {
					orient : 'vertical',
					x : 'left',
					y : 'bottom',
					data : count
				},
				color:[
				       '#C1232B','#27727B','#FCCE10','#E87C25','#B5C334',
				       '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
				       '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
				       ],
				       calculable : true,
				       series : [{
				    	   name:'内蒙古自治区',
				    	   type:'pie',
				    	   radius: ['0%', '70%'],
				    	   center: ['60%', '50%'],
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
				    	   data:count6
				       }]
		}
		var option = {//柱状图
				title: {
					text: title,
					x:'center',
					subtext: '单位：人',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
//					data:['女性人口','少数民族人口','新农合参保','新农保参保'],
					data:['贫困人口数量'],
					y:'bottom'
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					},
					formatter : '{b}<br/>{a0}: {c0} 人'
				},
				grid: {
					left: '0%',
					right: '9%',
					bottom: '10%',
					containLabel: true
				},
				color:['#6495ED'],
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
						formatter: '{value}'
					}
				},
				series: [
				         {
				        	 name: '贫困人口数量',
				        	 type: 'bar',
				        	 data: count0
				         }
//				         {
//				        	 name: '女性人口',
//				        	 type: 'bar',
//				        	 data: count2
//				         },
//				         {
//				        	 name: '少数民族人口',
//				        	 type: 'bar',
//				        	 data: count3
//				         },
//				         {
//				         name: '新农合参保',
//				         type: 'bar',
//				         data: count4
//				         },
//				         {
//				         name: '新农保参保',
//				         type: 'bar',
//				         data: count5
//				         },
				         ]
		};

		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
//		    $('#v2', window.parent.document).val(diqu);
		    parent.setSelVal(diqu);
		   

		});
		
//		myChart_ring.setOption(option_ring);
		myChart.setOption(option);
	}
}
//表格数据
function table  () {
//	tables +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+ item.V1 +'</td>'+
	var data = ajax_async_t("/assa/getBfdxHu_1.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"asc"},"json");
	var html = "";
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var A1=0,A2=0;
		$.each(data,function(i,item){
				html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td>'+
				'<td class="text-center">'+item.z_hu+'</td>'+
				'<td class="text-center">'+item.z_ren+'</td>'+
				'</tr>';
				if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
					
				}else{
					A1=parseInt(item.z_hu)+parseInt(A1);
					
				}
				if(item.z_ren == '' || item.z_ren == null || item.z_ren == undefined){
					
				}else{
					A2=parseInt(item.z_ren)+parseInt(A2);
					
				}
		})
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
				'<td class="text-center"><strong>'+A1+'</strong></td>'+
				'<td class="text-center"><strong>'+A2+'</strong></td>'+
				'</tr>';
		$("#tableChart").html(html);
	}

}