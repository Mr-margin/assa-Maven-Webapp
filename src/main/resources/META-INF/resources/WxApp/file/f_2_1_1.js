$(document).ready(function() {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu'];
	obj = JSON.parse(canshu);
	diqu=JSON.stringify(obj.com) ;
	pkcsbbz();
	biaoge();
	

	
});
var canshu
var obj
var diqu

function biaoge(){
	var xu=0;
	var v1=0;
	var v2=0;
	var v3=0;
	var v4=0;
	var v5=0;
	var v6=0;
	var v7=0;
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
    		if(item.fc2=="0"||item.fc2==null){
    			item.fc2="0";
    		}
    		if(item.fc3=="0"||item.fc3==null){
    			item.fc3="0";
    		}
    		if(item.fc4=="0"||item.fc4==null){
    			item.fc4="0";
    		}
    		if(item.fc5=="0"||item.fc5==null){
    			item.fc5="0";
    		}
    		if(item.fc6=="0"||item.fc6==null){
    			item.fc6="0";
    		}
    		//落实帮扶责任人
    		if(item.fc7=="0"||item.fc7==null){
    			item.fc7='0';
    		}
    		xu=i+1;
    		//循环赋值
    		v1=parseInt(item.fc1)+parseInt(v1);
    		v2=parseInt(item.fc2)+parseInt(v2);
    		v3=parseInt(item.fc3)+parseInt(v3);
    		v4=parseInt(item.fc4)+parseInt(v4);
    		v5=parseInt(item.fc5)+parseInt(v5);
    		v6=parseInt(item.fc6)+parseInt(v6);
    		v7=parseInt(item.fc7)+parseInt(v7)
    		html+='<tr><td class="text-center">'+xu+'</td><td class="text-center">'+item.name+'</td>'+
    		'<td class="text-center">'+item.fc1+'</td>'+
    		'<td class="text-center">'+item.fc2+'</td>'+
    		'<td class="text-center">'+item.fc3+'</td>'+
    		'<td class="text-center">'+item.fc4+'</td>'+
    		'<td class="text-center">'+item.fc5+'</td>'+
    		'<td class="text-center">'+item.fc6+'</td>'+
    		'<td class="text-center">'+item.fc7+'</td></tr>';
    	});
    	xu=xu+1;
    	html+='<tr><td class="text-center">'+xu+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td>'+
    	'<td class="text-center"><strong>'+v2+'</strong></td>'+
    	'<td class="text-center"><strong>'+v3+'</strong></td>'+
    	'<td class="text-center"><strong>'+v4+'</strong></td>'+
    	'<td class="text-center"><strong>'+v5+'</strong></td>'+
    	'<td class="text-center"><strong>'+v6+'</strong></td>'+
    	'<td class="text-center"><strong>'+v7+'</strong></td></tr>';
		//对下方的表格赋值
		$("#xiafangzongbiao").html(html);
	}




}
var myChart1 ;
var myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart1){
			myChart_ring.resize();
		}
	}catch(e){
	}
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};

//统计图方法
function pkcsbbz(){
	var data = ajax_async_t("/assa/getFccs.do",{desc:"desc",name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data == '0'){
		$("#z").html('<img class="center-block" src="../../img/wu.jpg">');
	}else{
		var com_name =[];
		var count1 = [];
		var count2 = [];
		var count3 = [];
		var count4 = [];
		var count5 = [];
		var count6 = [];
		var v1=0;
		var v2=0;
		var v3=0;
		var v4=0;
		var v5=0;
		var v6=0;
		var v7=0;
		$.each(data,function(i,item){
			
				com_name [i] = item.name;
				count1[i] = item.fc2;
				
				count2[i] = item.fc3;
				count3[i] = item.fc4;
				count4[i] = item.fc5;
				count5[i] = item.fc6;
				count6[i] = item.fc7;
				v1=parseInt(item.fc1)+parseInt(v1);
	    		v2=parseInt(item.fc2)+parseInt(v2);
	    		v3=parseInt(item.fc3)+parseInt(v3);
	    		v4=parseInt(item.fc4)+parseInt(v4);
	    		v5=parseInt(item.fc5)+parseInt(v5);
	    		v6=parseInt(item.fc6)+parseInt(v6);
	    		v7=parseInt(item.fc7)+parseInt(v7);
				
			
			
		})
		var count7 =[{value:v2 , name:"产业发展和转移就业"},
		             {value:v3 , name:"易地扶贫搬迁"},
		             {value:v4 , name:"生态补偿"},
		             {value:v5 , name:"教育扶贫"},
		             {value:v6 , name:"大病救治"},
		             {value:v7 , name:"社会保障兜底"},
				];
		option = {
				title : {
					text: '贫困人口扶持分类 ',
					subtext: '单位：人',
					subtextStyle: {
						color: 'black'
					},
					x:'center'
				},
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			        data: ['产业发展和转移就业', '易地扶贫搬迁','生态补偿','教育扶贫','大病救治','社会保障兜底'],
			        x : 'center',
			        y:'bottom'
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '15%',
			        containLabel: true
			    },
			    
			    xAxis:  {
			        type: 'value'
			    },
			    yAxis: {
			        type: 'category',
			        data: com_name
			    },
			    color:['#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	                   '#D7504B'],
			    series: [
			        {
			            name: '产业发展和转移就业',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                   
			                    position: 'insideRight'
			                }
			            },
			            data: count1
			        },
			        {
			            name: '易地扶贫搬迁',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: false,
			                    position: 'insideRight'
			                }
			            },
			            data: count2
			        },
			        {
			            name: '生态补偿',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: false,
			                    position: 'insideRight'
			                }
			            },
			            data: count3
			        },
			        {
			            name: '教育扶贫',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: false,
			                    position: 'insideRight'
			                }
			            },
			            data: count4
			        },
			        {
			            name: '大病救治',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: false,
			                    position: 'insideRight'
			                }
			            },
			            data: count5
			        },
			        {
			            name: '社会保障兜底',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: false,
			                    position: 'insideRight'
			                }
			            },
			            data: count6
			        }
			        
			    ]
		};
		var option_ring = {//环形图
				title : {
					text: '六个一批分类扶持',
					textStyle:{fontWeight:'500',fontSize:'17'},
					x:'center',
					y:'80'
				},
				tooltip : {
					trigger: 'item',
					formatter: "{b} : {c}人"
				},
				
				legend: {
					x : 'center',
					y : 'bottom',
					data : ['产业发展和转移就业', '易地扶贫搬迁','生态补偿','教育扶贫','大病救治','社会保障兜底']
				},
				calculable : true,
				color:['#C1232B','#B5C334','#9370DB','#E87C25','#27727B',
	                   '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	                   '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'],
				series : [{
					
					name:'内蒙古自治区',
					  type:'pie',
			            center: ['50%', '60%'],
			            radius: ['30%', '50%'],
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
			                        fontSize: '15',
			                        fontWeight: 'bold'
			                    }
			                }
			            },
					  
					  data:count7
				}]
		}
		 myChart1 = echarts.init(document.getElementById('hfccs'))
		 myChart = echarts.init(document.getElementById('fccs'));
		//点击事件
		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart.setOption(option);
		myChart1.setOption(option_ring);
	}
}