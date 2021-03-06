var title='';
var obj = {};
$(document).ready(function() {
	title='新增医保';
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
	var xu=0;
	var data = ajax_async_t("/assa/getWfgz.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data==0){
		$("#biaoge").html('<h3>暂无数据</h3>');
	}else{
		var html="";
		var html1="";
    	$.each(data, function(i,item) {

    		v1+=parseInt(item.vg15);
    		v2+=parseInt(item.vg151);
    		xu=i+1;
    		html+='<tr><td class="text-center">'+xu+'</td><td class="text-center">'+item.name+'</td>'+
    		'<td class="text-center">'+item.vg15+'</td>'+
    		'<td class="text-center">'+item.vg151+'</td>'+
    		'<td class="text-center">'+item.b15+'%</td></tr>';
    	});
    	v3=(floatDiv(v2,v1)*100).toFixed(2);
    	if(isNaN(v3)){
    		v3='0';
		}
    	xu=xu+1;
    	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v2+'</strong></td><td class="text-center"><strong>'+v3+'%</strong></td></tr>';
    	html1='常住人口社会保障任务:新增医保'+v1+'人，其中贫困'+v2+'人'
		$("#biaozhu").html(html1);
    	//对下方的表格赋值
    	
		$("#xiafangzongbiao").html(html);
		$("#ren").html(v1);
		$("#hu").html(v2);
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
	var data = ajax_async_t("/assa/getWfgz.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	var count=[];//名称
	var count2=[];//总数
	var count3=[];//贫困户

	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}
	$.each(data,function(i,item){
		count[i]=item.name;
		count2[i]=parseInt(item.vg15)-parseInt(item.vg151);
		count3[i]=item.vg151;
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
				text: title,
				x:'center',
				subtext: '单位：人',
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
		        
		    },
		    color:['#FCCE10','#9BCA63'],
		    legend: {
		        data:['非贫困人数','贫困户人数'],
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
		            name:'非贫困人数',
		            type:'bar',
		            stack: '1',
		            data:count2,
		           
		        },
		        {
		            name:'贫困户人数',
		            type:'bar',
		         	stack: '1',
		            data:count3,
		           
		            itemStyle:itemStyle
		        },
		      
		        
		        
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