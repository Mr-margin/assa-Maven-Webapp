$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	table();
	huan_pie();
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
	var data = ajax_async_t("/assa/getBfdxHu_3.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	var html = "";
	var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0,A9=0,A10=0,A11=0,A12=0;
	$.each(data,function(i,item){
		html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td>'+
		'<td>'+item.z_hu+'</td>'+
		'<td>'+item.v1+'</td>'+
		'<td>'+item.v3+'</td>'+
		'<td>'+item.v5+'</td>'+
		'<td>'+item.v7+'</td>'+
		'<td>'+item.v9+'</td>'+
		'<td>'+item.v11+'</td>'+
		'<td>'+item.v13+'</td>'+
		'<td>'+item.v15+'</td>'+
		'<td>'+item.v17+'</td>'+
		'<td>'+item.v19+'</td>'+
		'<td>'+item.v21+'</td>'+
		'</tr>';
		if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
			
		}else{
			A1=parseInt(item.z_hu)+parseInt(A1);
			
		}
		if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
			
		}else{
			A2=parseInt(item.v1)+parseInt(A2);
			
		}
		if(item.v3 == '' || item.v3 == null || item.v3 == undefined){
			
		}else{
			A3=parseInt(item.v3)+parseInt(A3);
			
		}
		if(item.v5 == '' || item.v5 == null || item.v5 == undefined){
			
		}else{
			A4=parseInt(item.v5)+parseInt(A4);
			
		}
		if(item.v7 == '' || item.v7 == null || item.v7 == undefined){
			
		}else{
			A5=parseInt(item.v7)+parseInt(A5);
			
		}
		if(item.v9 == '' || item.v9 == null || item.v9 == undefined){
			
		}else{
			A6=parseInt(item.v9)+parseInt(A6);
			
		}
		if(item.v11 == '' || item.v11 == null || item.v11 == undefined){
			
		}else{
			A7=parseInt(item.v11)+parseInt(A7);
			
		}
		if(item.v13 == '' || item.v13 == null || item.v13 == undefined){
			
		}else{
			A8=parseInt(item.v13)+parseInt(A8);
			
		}
		if(item.v15 == '' || item.v15 == null || item.v15 == undefined){
			
		}else{
			A9=parseInt(item.v15)+parseInt(A9);
			
		}
		if(item.v17 == '' || item.v17 == null || item.v17 == undefined){
			
		}else{
			A10=parseInt(item.v17)+parseInt(A10);
			
		}
		if(item.v19 == '' || item.v19 == null || item.v19 == undefined){
			
		}else{
			A11=parseInt(item.v19)+parseInt(A11);
			
		}
		if(item.v21 == '' || item.v21 == null || item.v21 == undefined){
			
		}else{
			A12=parseInt(item.v21)+parseInt(A12);
			
		}
		
		
	})
	html +='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td>'+
		'<td><strong>'+A1+'</strong></td>'+
		'<td><strong>'+A2+'</strong></td>'+
		'<td><strong>'+A3+'</strong></td>'+
		'<td><strong>'+A4+'</strong></td>'+
		'<td><strong>'+A5+'</strong></td>'+
		'<td><strong>'+A6+'</strong></td>'+
		'<td><strong>'+A7+'</strong></td>'+
		'<td><strong>'+A8+'</strong></td>'+
		'<td><strong>'+A9+'</strong></td>'+
		'<td><strong>'+A10+'</strong></td>'+
		'<td><strong>'+A11+'</strong></td>'+
		'<td><strong>'+A12+'</strong></td>'+
		'</tr>';
	$("#zpyy_table").html(html);
}

//环形图
function huan_pie(){
	var data = ajax_async_t("/assa/getBfdxHu_4.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,shi:"0"},"json");
	var zong = data[0].z_hu;
	var bilv = 100/zong;
	var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : '{b}',
					textStyle: {
						baseline : 'bottom'
					}
				},
				labelLine : {
					show : false
				}
			}
	};
	var labelFromatter = {
			normal : {
				label : {
					formatter : function (params){
						return 100 - params.value + '%'
					},
					textStyle: {
						baseline : 'top'
					}
				}
			},
	}
	var labelBottom = {
			normal : {
				color: '#ccc',
				label : {
					show : true,
					position : 'center'
				},
				labelLine : {
					show : false
				}
			},
			emphasis: {
				color: 'rgba(0,0,0,0)'
			}
	};
	var radius = [40, 55];
	option = {
			legend: {
				x : 'center',
				y : '200px',
				data:[
				      '因病致贫','因残致贫','因学致贫','因灾致贫','缺土地',
				      '缺水', '缺技术', '缺劳力', '缺资金', '交通条件落后','自身发展力不足'
				      ]
			},
			title : {
				text: '贫困户主要致贫原因分析',
				textStyle:{fontWeight:'500',fontSize:'17'},
				x: 'center'
			},
			color:[
			       '#C1232B','#27727B','#FCCE10','#E87C25','#B5C334',
			       '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
			       '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
			       ],
			series : [
			          {
			        	  type : 'pie',
			        	  center : ['16.66%', '20%'],
			        	  radius : radius,
			        	  y:'0%',
			        	  x: '0%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v1)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'因病致贫', value:(data[0].v1*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['33.32%', '20%'],
			        	  radius : radius,
			        	  x:'16.66%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v3)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'因残致贫', value:(data[0].v3*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['49%', '20%'],
			        	  radius : radius,
			        	  y:'0%',
			        	  x:'33.32%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v5)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'因学致贫', value:(data[0].v5*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['66.64%', '20%'],
			        	  radius : radius,
			        	  y:'0%',
			        	  x:'49%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v7)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'因灾致贫', value:(data[0].v7*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['83.3%', '20%'],
			        	  radius : radius,
			        	  y:'0%',
			        	  x:'66.64%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v9)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'缺土地', value:(data[0].v9*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['12%', '50%'],
			        	  radius : radius,

			        	  y: '55%',   // for funnel
			        	  x: '0%',    // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v11)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'缺水', value:(data[0].v11*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['27%', '50%'],
			        	  radius : radius,
			        	  y: '55%',   // for funnel
			        	  x:'16%',    // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v13)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'缺技术', value:(data[0].v13*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['42%', '50%'],
			        	  radius : radius,
			        	  y: '55%',   // for funnel
			        	  x:'32%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v15)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'缺劳力', value:(data[0].v15*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['58%', '50%'],
			        	  radius : radius,
			        	  y: '55%',   // for funnel
			        	  x:'48%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v17)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'缺资金', value:(data[0].v17*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['74%', '50%'],
			        	  radius : radius,
			        	  y: '55%',   // for funnel
			        	  x:'64%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v19)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'交通条件落后', value:(data[0].v19*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          },
			          {
			        	  type : 'pie',
			        	  center : ['89%', '50%'],
			        	  radius : radius,
			        	  y: '55%',   // for funnel
			        	  x:'80%', // for funnel
			        	  itemStyle : labelFromatter,
			        	  data : [
			        	          {name:'other', value:((zong-data[0].v21)*bilv).toFixed(0), itemStyle : labelBottom},
			        	          {name:'自身发展力不足', value:(data[0].v21*bilv).toFixed(0),itemStyle : labelTop}
			        	          ]
			          }
			          ]
	};
	myChart_1 = echarts.init(document.getElementById('pie'));
	myChart_1.setOption(option);                    

}
