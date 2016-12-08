var title='';
$(document).ready(function() {
	title='帮扶责任人统计';
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
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	pkcsbbz();
	biaoge()
});



function biaoge(){
	var v1=0;
	var v2=0;
	var v3=0;
	var v4=0;
	var v5=0;
	var v6=0;
	var v7=0;
	var v8=0;
	var v9=0;
	var v10=0;
	var xu=0;
	var data = ajax_async_t("/assa/getBfzrr.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data==0){
		$("#biaoge").html('');
	}else{
		var html="";
		var html1="";
    	$.each(data, function(i,item) {
    		if(item.vh3=="0"||item.vh3==null){
    			item.vh3="0";
    		}
    		if(item.vh4=="0"||item.vh4==null){
    			item.vh4="0";
    		}
    		if(item.vh5=="0"||item.vh5==null){
    			item.vh5="0";
    		}
    		if(item.vh6=="0"||item.vh6==null){
    			item.vh6="0";
    		}
    		v1+=parseInt(item.vh1);
    		v2+=parseInt(item.vh2);
    		v3+=parseInt(item.vh3);
    		v4+=parseInt(item.vh4);
    		v5+=parseInt(item.vh5);
    		v6+=parseInt(item.vh6);
    		v7+=parseInt(item.vh7);
    		v8+=parseInt(item.vh8);
    		v9+=parseInt(item.vh9);
    		v10+=parseInt(item.vh10);
    		
    		xu=i+1;
    		html+='<tr><td class="text-center">'+xu+'</td><td class="text-center">'+item.name+'</td>'+
    		'<td class="text-center">'+item.vh1+'</td>'+
    		'<td class="text-center">'+item.vh2+'</td>'+
    		'<td class="text-center">'+item.vh3+'</td>'+
    		'<td class="text-center">'+item.vh4+'</td>'+
    		'<td class="text-center">'+item.vh5+'</td>'+
    		'<td class="text-center">'+item.vh6+'</td>'+
    		'<td class="text-center">'+item.vh7+'</td>'+
    		'<td class="text-center">'+item.vh8+'</td>'+
    		'<td class="text-center">'+item.vh9+'</td>'+
    		'<td class="text-center">'+item.vh10+'</td></tr>';
    		
    	});
    	xu=xu+1;
    	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v2+'</strong></td><td class="text-center"><strong>'+v3+'</strong></td><td class="text-center"><strong>'+v4+'</strong></td><td class="text-center"><strong>'+v5+'</strong></td><td class="text-center"><strong>'+v6+'</strong></td><td class="text-center"><strong>'+v7+'</strong></td><td class="text-center"><strong>'+v8+'</strong></td><td class="text-center"><strong>'+v9+'</strong></td><td class="text-center"><strong>'+v10+'</strong></td></tr>';
    	html1='显示本辖区合计的落实帮扶责任人数，各类型帮扶干部数量。'
		$("#biaozhu").html(html1);
    	//对下方的表格赋值
    	$("#xiafangzongbiao").html(html);
		$("#ren").html(v1);
		$("#hu").html(v2);
		$("#a").html(v3);
		$("#b").html(v4);
		$("#c").html(v5);
		$("#d").html(v6);
		$("#e").html(v7);
		$("#f").html(v8);
		$("#g").html(v9);
		$("#h").html(v10);
		
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
	var data = ajax_async_t("/assa/getBfzrr.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json"); //调用ajax通用方法
	if(data==0){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}
	var count=[];//名称
	var count2=[];//省级
	var count3=[];//省级贫困户
	var count4=[];//市级
	var count5=[];//市级贫困户
	var count6=[];//县级领导
	var count7=[];//县级贫困户
	var count8=[];//县级以下干部
	var count9=[];//县级以下干部贫困户
	
	$.each(data,function(i,item){
		count[i]=item.name;
		count2[i]=item.vh3;
		count3[i]=item.vh4;
		count4[i]=item.vh5;
		count5[i]=item.vh6;
		count6[i]=item.vh7;
		count7[i]=item.vh8;
		count8[i]=item.vh9;
		count9[i]=item.vh10;
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
				subtext: '单位：户',
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
		    color:['#C1232B','#B5C334','#FCCE10','#E87C25',
                   '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                   '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'],
		    legend: {
		        data:['省级领导','省级帮扶户数','市级领导','市级帮扶户数','县级领导','县级帮扶户数','县级以下干部','县级以下帮扶户数',],
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
		            name:'省级领导',
		            type:'bar',
		            stack: '1',
		            data:count2,
		           
		            itemStyle:itemStyle
		        },
		        {
		            name:'省级帮扶户数',
		            type:'bar',
		            stack: '1',
		            data:count3,
		            
		            itemStyle:itemStyle
		        },
		        {
		            name:'市级领导',
		            type:'bar',
		            stack: '1',
		            data:count4,
		           
		            itemStyle:itemStyle
		        },
		        {
		            name:'市级帮扶户数',
		            type:'bar',
		            stack: '1',
		            data:count5,
		            
		            itemStyle:itemStyle
		        },
		        {
		            name:'县级领导',
		            type:'bar',
		            stack: '1',
		            data:count6,
		           
		            itemStyle:itemStyle
		        },
		        {
		            name:'县级帮扶户数',
		            type:'bar',
		            stack: '2',
		            data:count7,
		            
		            itemStyle:itemStyle
		        },
		        {
		            name:'县级以下干部',
		            type:'bar',
		            stack: '1',
		            data:count2,
		           
		            itemStyle:itemStyle
		        },
		        {
		            name:'县级以下帮扶户数',
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