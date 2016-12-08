$(document).ready(function() {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	pkcsbbz();
	biaoge()
});



function biaoge(){
	var v1=0;
	var v5=0;
	var data = ajax_async_t("/assa/getFccs.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data==0){
		$("#biaoge").html('');
	}else{
		var html="";
    	$.each(data, function(i,item) {
    		//户数
    		if(item.name=="0"||item.name==null){
    			item.name='0';
    		}
    		//人数
    		if(item.fc1=="0"||item.fc1==null){
    			item.fc1="0";
    		}
    		//落实帮扶责任人
    		if(item.fc5=="0"||item.fc5==null){
    			item.fc5='0';
    		}
    		//循环赋值
    		xu=i+1;
    		v1=parseInt(item.fc1)+parseInt(v1);
    		v5=parseInt(item.fc5)+parseInt(v5);
    		html+='<tr><td class="text-center">'+xu+'</td><td class="text-center">'+item.name+'</td>'+
    		'<td class="text-center">'+item.fc1+'</td>'+
    		'<td class="text-center">'+item.fc5+'</td></tr>';
    	});
    	xu=xu+1;
    	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v5+'</strong></td></tr>';
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
	 myChart = echarts.init(document.getElementById('stbc'));//声明id为mapChart的div为图形dom 
	var data = ajax_async_t("/assa/getFccs.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	var count=[];
	var count2=[];
	var count3=[];
	var count4=[];
	var count5=[];
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}

	var option = {
			title: {
				text: '教育扶贫人口数量',
				x:'center',
				subtext: '单位：人',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['教育扶贫人口数量'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				}
			},
			color:['#FE8463'],
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
			        	 name: '教育扶贫人口数量',
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
			        	 data: []
			         }
			        
			       
			    
			         ]
	};
	$.each(data,function(i,indx){
		count[i]=indx.name;
		count2[i]=indx.fc5;
		
	});
	//option_tiao.color=['#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'];
	option.xAxis.data=count;
	option.series[0].data=count2;
	
	//点击事件
	myChart.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart.setOption(option);
}