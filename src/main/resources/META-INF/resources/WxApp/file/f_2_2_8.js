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
	var v2=0;
	var xu=0;
	var data = ajax_async_t("/assa/getWfgz.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data==0){
		$("#xiafangzongbiao").html('<h3>暂无数据</h3>');
	}else{
		var html="";
		var html1="";
    	$.each(data, function(i,item) {

    		v1+=parseFloat(item.vg6);
    		
    		xu=i+1
    		html+='<tr><td class="text-center">'+xu+'</td><td class="text-center">'+item.name+'</td>'+
    		'<td class="text-center">'+parseInt(item.vg6)+'</td>'+
    		'</tr>';
    	});
    	xu=xu+1;
    	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+parseInt(v1)+'</strong></td></tr>';
    	html1='本辖区街巷硬化总'+parseInt(v1)+'米'
		$("#biaozhu").html(html1);
    	//对下方的表格赋值
		$("#xiafangzongbiao").html(html);
		$("#ren").html(parseInt(v1));
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
	 myChart = echarts.init(document.getElementById('wfgz'));//声明id为mapChart的div为图形dom 
	var data = ajax_async_t("/assa/getWfgz.do",{order:'VG6',name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	var count=[];//名称
	var count2=[];//总数
	
	if(data==0){
		$("#wfgz").html('<h3>暂无数据</h3>');
	}
	
	$.each(data,function(i,item){
		count[i]=item.name;
		count2[i]=parseInt(item.vg6);
		
	});
	
	var itemStyle = {
		    normal: {
		    },
		    emphasis: {
		        barBorderWidth: 1,
		        shadowBlur: 10,
		        shadowOffsetX: 0,
		        shadowOffsetY: 0,
		        shadowColor: 'rgba(0,0,0,0.5)'
		    }
		};
	var option = {

			title: {
				text: '街巷硬化',
				x:'center',
				subtext: '单位：米',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter: '{a}<br/> {b}: {c} 米'
		    },
		    color:["#20B2AA"],
		    legend: {
		        data:['街巷硬化'],
		        y:'bottom'
		    },
		    
		    xAxis : [
		        {
		            type : 'category',
		            data : count,
		            splitLine:{show:false},
		            splitNumber:1,
		            axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
		        }
		    ],
		    yAxis: {
				axisLabel: {
		            formatter: '{value}'
		        }
			},
		    
		    series : [
	
		        {
		            name:'街巷硬化',
		            type:'bar',
		            /*stack: '1',*/
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
		            data:count2,
	
		            itemStyle:itemStyle
		        }
		      
		        
		        
		    ]
};
	
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