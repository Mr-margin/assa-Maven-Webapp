
var title='';
var obj = {};
$(document).ready(function() {
	title='贫困村和非贫困村分布';
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
	pkcsbbz();
	biaoge()
});

function biaoge(){
	var v1=0;
	var v2=0;
	var v3=0;
	var aa;//名称
	var bb;//人数
	var cc;//户数
	var dd;//落实帮扶责任人
	var ee;//制定帮扶计划
	var ff;//帮扶措施
	var gg;//走访记录

	var data = ajax_async_t("/assa/getpkc.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data == '0'){
		$("#biaoge").html('');
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
			if(item.b1=="0"||item.b1==null){
				bb='0';
			}else{
				bb=''+item.b1+'';
			}
			//落实帮扶责任人
			if(item.b2=="0"||item.b2==null){
				dd='0';
			}else{
				dd=''+item.b2+'';
			}
			//制定帮扶计划
			if(item.b3=="0"||item.b3==null){
				ee='0';
			}else{
				ee=''+item.b3+'';
			}
			//帮扶措施
			//二维码
			v1=parseInt(item.b1)+parseInt(v1);
			v2=parseInt(item.b2)+parseInt(v2);
			v3=parseInt(item.b3)+parseInt(v3);
			//循环赋值
			html+='<tr><td class="text-center">'+aa+'</td><td class="text-center">'+cc+'</td><td class="text-center">'+bb+'</td><td class="text-center">'+dd+'</td><td class="text-center">'+ee+'</td></tr>';
		});
		aa=aa+1
		html+='<tr><td class="text-center">'+aa+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v2+'</strong></td><td class="text-center"><strong>'+v3+'</strong></td></tr>'
		//对下方的表格赋值
		$("#xiafangzongbiao").html(html);
	}
}

var myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}

};
//统计图方法
function pkcsbbz(){
	myChart = echarts.init(document.getElementById('sbbz'));//声明id为mapChart的div为图形dom 
	var data = ajax_async_t("/assa/getpkc.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	if(data == '0'){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}else{
		var count=[];
		var count2=[];
		var count3=[];
		var count4=[];
		var count5=[];
	
		var option = {
				title: {
					text: title,
					subtext: '单位：个',
					subtextStyle: {
						color: 'black'
					},
					x:'center',
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					data:['贫困村数','非贫困村数'],
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
				color:['#FE8463','#9BCA63','#FAD860'],
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
			            formatter: '{value}'
			        }
				},
				series: [
				         
				         {
				        	 name: '贫困村数',
				        	 type: 'bar',
				        	 data: []
				         },
				         {
				        	 name: '非贫困村数',
				        	 type: 'bar',
				        	 data: []
				         },
				         ]
		};
		$.each(data,function(i,indx){
			count[i]=indx.name;
			
			count3[i]=indx.b2;
			count4[i]=indx.b3;
	
		});
		//option_tiao.color=['#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'];
		option.xAxis.data=count;
		option.series[0].data=count3;
		option.series[1].data=count4;
	
	
		//点击事件
		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart.setOption(option);
	}
}