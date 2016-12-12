$(document).ready(function(){
	title='贫困户土地资源情况';
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
	pie();
	bar();
});
var obj = {};
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
	var data = ajax_async_t("/assa/getBfdxHu_9.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0;
		$.each(data,function(i,item){
				html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td><td>'+item.z_hu+'</td>'+
				'<td>'+item.v1+'</td>'+
				'<td>'+item.v3+'</td>'+
				'<td>'+item.v5+'</td>'+
				'<td>'+item.v7+'</td>'+
				'<td>'+item.v9+'</td>'+
				'<td>'+item.v11+'</td>'+
				'<td>'+item.v13+'</td>'+
				'</tr>';
				if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
					A1=0+parseInt(A1);
				}else{
					A1=parseInt(item.z_hu)+parseInt(A1);
					
				}
				if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
					A2=0+parseInt(A2);
				}else{
					A2=parseInt(item.v1)+parseInt(A2);
					
				}
				if(item.v3 == '' || item.v3 == null || item.v3 == undefined){
					A3=0+parseInt(A3);
				}else{
					A3=parseInt(item.v3)+parseInt(A3);
					
				}
				if(item.v5 == '' || item.v5 == null || item.v5 == undefined){
					A4=0+parseInt(A4);
				}else{
					A4=parseInt(item.v5)+parseInt(A4);
					
				}
				if(item.v7 == '' || item.v7 == null || item.v7 == undefined){
					A5=0+parseInt(A5);
				}else{
					A5=parseInt(item.v7)+parseInt(A5);
					
				}
				if(item.v9 == '' || item.v9 == null || item.v9 == undefined){
					A6=0+parseInt(A6);
				}else{
					A6=parseInt(item.v9)+parseInt(A6);
					
				}
				if(item.v11 == '' || item.v11 == null || item.v11 == undefined){
					A7=0+parseInt(A7);
				}else{
					A7=parseInt(item.v11)+parseInt(A7);
					
				}
				if(item.v13 == '' || item.v13 == null || item.v13 == undefined){
					A8=0+parseInt(A8);
				}else{
					A8=parseInt(item.v13)+parseInt(A8);
					
				}
//				A1+=parseInt(item.z_hu)
//				A2+=parseFloat(item.v1)
//				A3+=parseFloat(item.v3)
//				A4+=parseFloat(item.v5)
//				A5+=parseFloat(item.v7)
//				A6+=parseFloat(item.v9)
//				A7+=parseFloat(item.v11)
//				A8+=parseFloat(item.v13)
		})
		html +='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td>'+
		'<td><strong>'+A1+'</strong></td>'+
		'<td><strong>'+parseFloat(A2).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A3).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A4).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A5).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A6).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A7).toFixed(2)+'</strong></td>'+
		'<td><strong>'+parseFloat(A8).toFixed(2)+'</strong></td>'+
		'</tr>';
		$("#td_table").html(html);
	}
	 
}

//饼图
function pie(){
	var data = ajax_async_t("/assa/getBfdxHu_9.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var v1 = 0; var v3 = 0; var v5 = 0; var v7 = 0; var v9 = 0; var v11 = 0; var v13 = 0; var zong = 0;
		$.each(data,function(i,item){
			if(item.com_name != '汇总'){
				v1 += parseInt(item.v1);
				v3 += parseInt(item.v3);
				v5 +=parseInt(item.v5);
				v7 +=parseInt(item.v7);
				v9 +=parseInt(item.v9);
				v11 += parseInt(item.v11);
				v13 +=parseInt(item.v13);
				zong += parseInt(item.z_hu);
			}
		})
		v1 = v1*(100/zong);
		v3 = v3*(100/zong);
		v5 = v5*(100/zong);
		v7 = v7*(100/zong);
		v9 = v9*(100/zong);
		v11 = v11*(100/zong);
		v13 = v13*(100/zong);
		 $('#container').highcharts({
		        chart: {
		            type: 'pie',
		            options3d: {
		                enabled: true,
		                alpha: 45,
		                beta: 0
		            }
		        },
		        colors: [ '#DAA520','#60C0DD','#FCCE10','#E87C25','#B5C334','#b5c334'],
		        title: {
		            text: title,
		            style: {
		                color: '#333333',
		                fontWeight: 'bold'
		            }
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
//		                color:['#b5c334'],
		                cursor: 'pointer',
		                depth: 35,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.name}'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '所占百分比',
		            color:['#b5c334'],
		            data: [
		                ['耕地面积', v1],
		                ['有效灌溉面积', v3],
		                {
		                    name: '林地面积',
		                    y: v5,
		                    sliced: true,
		                    selected: true
		                },
		                ['退耕还林面积',v7],
		                ['林果面积', v9],
		                ['牧草地面积', v11],
		                ['水域面积', v13]
		            ]
		        }]
		    });
	}
}


//人均占地柱图
function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_10.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var com_name = [];
		var num = [];var v2 = []; var v4 = [];var v6 = [];var v8 = []; var v10 = [];var v12 = []; var v14 = [];
		$.each(data,function(i,item){
				com_name[i] = item.com_name;
				v2[i] =item.v2; v4[i] = item.v4; v6[i] = item.v6; v8[i] = item.v8; v10[i] = item.v10; v12[i] = item.v12; v14[i] = item.v14; 
				num[i] = (parseFloat(item.v2)+parseFloat(item.v4)+parseFloat(item.v6)+parseFloat(item.v8)+parseFloat(item.v10)+parseFloat(item.v12)+parseFloat(item.v14)).toFixed(2);
			
		})
		option = {
			title: {
		        text: '人均耕地面积',
		        textStyle:{fontWeight:'500',fontSize:'17'},
		        subtext: '单位：亩',
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
		        data: ['人均耕地面积'],
		        y:'bottom'
		    },
		    grid: {
		        left: '2%',
		        right: '4%',
		        bottom: '8%',
		        containLabel: true
		    },
		    color:['#DAA520'],
		    xAxis:  {
//		        type: 'value'
		    	axisLabel: {
					formatter: function (value, index) {
						var data ;
						if (index === 0) {
							data=0
						}else{
							data= value;
						}
						return data;
					}
				}
		    },
		    yAxis: {
		        type: 'category',
		        data: com_name,
		        x:'102px'
		    },
		    series: [
		        {
		            name: '人均耕地面积',
		            type: 'bar',
		            label: {
		        		 normal: {
		        			 show: true,
			        		 position: 'right',
			        		 formatter: '{c}',
			        		 textStyle: {
			        			 color:"black"
			        		 }
		        		 }
		        	 },
		            data: v2
		        },
		    ]
	};
		myChart_1 = echarts.init(document.getElementById('bar2'));
		//点击事件
		myChart_1.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart_1.setOption(option);                   
	}
	 
}