$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	
	table();
	bar();
});
var obj;
var myChart_1;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart_1){
			myChart_1.resize();
		}
	}catch(e){
	}
};
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHuc_13.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	var html = "";
	var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0;
	$.each(data,function(i,item){
		
			html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td><td>'+item.z_hu+'</td>'+
			'<td>'+item.v1+'</td>'+
			'<td>'+item.v3+'</td>'+
			'<td>'+item.v5+'</td>'+
			'<td>'+item.v7+'</td>'+
			'<td>'+item.v9+'</td>'+
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
			
//			A1+=parseInt(item.z_hu)
//			A2+=parseInt(item.v1)
//			A3+=parseInt(item.v3)
//			A4+=parseInt(item.v5)
//			A5+=parseInt(item.v7)
//			A6+=parseInt(item.v9)
		
		
	})
	html +='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td>'+
	'<td><strong>'+A1+'</strong></td>'+
	'<td><strong>'+A2+'</strong></td>'+
	'<td><strong>'+A3+'</strong></td>'+
	'<td><strong>'+A4+'</strong></td>'+
	'<td><strong>'+A5+'</strong></td>'+
	'<td><strong>'+A6+'</strong></td>'+
	
	
	'</tr>';
	$("#sr_table").html(html);
	$("#sr_table td").css("text-align","center");

var option = {
   
    title: {
        text: '贫困户主要燃料',
        textStyle:{fontWeight:'500',fontSize:'17'},
    },
    tooltip : {
		trigger: 'item',
		formatter: "{b} : {c}"
	},
    color:[
	       '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	       '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
	       ],
    series: [{
        type: 'pie',
        selectedMode: 'single',
        selectedOffset: 30,
        clockwise: true,
        label: {
            normal: {
                textStyle: {
                    fontSize: 18,
                    color: '#235894'
                }
            }
        },
        labelLine: {
            normal: {
                lineStyle: {
                    color: '#235894'
                }
            }
        },
        data:[
            {value:A2, name:'柴草户数'},
            {value:A3, name:'干畜粪户数'},
            {value:A4, name:'煤炭户数'},
            {value:A5, name:'清洁能源户数'},
            {value:A6, name:'其它'}
        ],
    }]
};
	myChart_1 = echarts.init(document.getElementById('pie'));
	myChart_1.setOption(option);  
	
}

//柱状图
function bar (){
	var data = ajax_async_t("/assa/getBfdxHuc_13.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	var v1 = []; var v3 = []; var v5 = []; var v7 = []; var v9 = []; var com_name = [];
	$.each(data,function(i,item){
		if(item.com_name != '汇总'){
			v1 [i]=parseInt(item.v1);
			v3 [i]= parseInt(item.v3);
			v5 [i]=parseInt(item.v5);
			v7 [i]=parseInt(item.v7);
			v9 [i]=parseInt(item.v9);
			com_name [i] = item.com_name;
		}
	})
	var option = {
			title: {
				text: '贫困户使用清洁能源户数',
				x:'center',
				subtext: '单位：户',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['清洁能源户数'],
				y:'bottom'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				}
			},
			grid: {
				left: '0%',
				right: '1%',
				bottom: '10%',
				containLabel: true
			},
			color:['#9bca63'],
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:com_name
			},
			yAxis: {
				axisLabel: {
		            formatter: '{value}'
		        }
			},
			series: [
			         {
			        	 name: '清洁能源户数',
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
			        	 data: v7
			         }
			         ]
	};
	   
	    myChart_1 = echarts.init(document.getElementById('container'));
	  //点击事件
		myChart_1.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
	    myChart_1.setOption(option);
}
