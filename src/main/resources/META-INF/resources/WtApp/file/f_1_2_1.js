$(document).ready(function(){
	title='项目资金计划分布情况';
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
	var data = JSON.parse(ajax_async_t("../../getProjectBonusPlan.do", {name:obj.com},"text","1")); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
		$("#z").css("width","100%").css("height","585px");
	}else{
		var com_name;
		var count = [];
		var count_2 = [];
		$.each(data,function(i,item){
			if(typeof item.com_name == 'undefined'){
				item.com_name='';
			}
			count[i]=item.com_name;
			if(item.budget_amount == '' || item.budget_amount == null || item.budget_amount == undefined){
				
			}else{
				count_2[i]=parseInt(item.budget_amount);
				
			}
		});
		
		myChart = echarts.init(document.getElementById('tu_1'));//声明id为myChart的div为图形dom

		var option = {//柱状图
				title: {
					text: title,
					x:'center',
					subtext: '单位：万元',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					data:['奖金计划数量'],
					y:'bottom'
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					},
					formatter : '{b}<br/>{a0}: {c0} 万元'
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
				        	 name: '项目奖金计划',
				        	 type: 'bar',
				        	 data: count_2
				         }
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
	var data = ajax_async_t("/assa/getProjectBonusPlan.do",{name:obj.com,desc:"asc"},"json");
	var html = "";
	if(data=='' || data == null || data==undefined){
		$("#tableChart").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var A1=0;
		$.each(data,function(i,item){
				html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td>'+
				'<td class="text-center">'+item.budget_amount+'</td>'+
				'</tr>';
				if(item.budget_amount == '' || item.budget_amount == null || item.budget_amount == undefined){
					
				}else{
					A1=parseInt(item.budget_amount)+parseInt(A1);
					
				}
		})
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
				'<td class="text-center"><strong>'+A1+'</strong></td>'+
				'</tr>';
		$("#tableChart").html(html);
	}

}