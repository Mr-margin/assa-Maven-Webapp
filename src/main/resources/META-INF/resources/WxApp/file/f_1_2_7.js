$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	
	table();
	bar();
});
var obj;
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHuc_11.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.jpg" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0;
		$.each(data,function(i,item){
				html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td>'+
				'<td>'+item.v1+'</td>'+
				'<td>'+item.v3+'</td>'+
				'<td>'+item.v5+'</td>'+
				'<td>'+item.v7+'</td>'+
				'<td>'+item.v9+'</td>'+
				'<td>'+item.v11+'</td>'+
				'<td>'+item.v13+'</td>'+
				'</tr>';
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
				if(item.v11 == '' || item.v11 == null || item.v11 == undefined){
					
				}else{
					A7+=parseInt(item.v11);
					
				}
				if(item.v13 == '' || item.v13 == null || item.v13 == undefined){
					
				}else{
					A8+=parseInt(item.v13);
					
				}
//				A2+=parseInt(item.v1)
//				A3+=parseInt(item.v3)
//				A4+=parseInt(item.v5)
//				A5+=parseInt(item.v7)
//				A6+=parseInt(item.v9)
//				A7+=parseInt(item.v11)
//				A8+=parseInt(item.v13)
		})
		html +='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td>'+
		
		'<td><strong>'+A2+'</strong></td>'+
		'<td><strong>'+A3+'</strong></td>'+
		'<td><strong>'+A4+'</strong></td>'+
		'<td><strong>'+A5+'</strong></td>'+
		'<td><strong>'+A6+'</strong></td>'+
		'<td><strong>'+A7+'</strong></td>'+
		'<td><strong>'+A8+'</strong></td>'+
		
		'</tr>';
		$("#sr_table").html(html);
		$("#sr_table td").css("text-align","center");
	}
	 
}

//柱状图
function bar(){
	var data = ajax_async_t("/assa/getBfdxHuc_12.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.jpg" class="center-block">')
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
			}else{
				
			}
		})
		    $('#container').highcharts({
		        chart: {
		            type: 'column',
		            options3d: {
		                enabled: true,
		                alpha: 10,
		                beta: 25,
		                depth: 70
		            }
		        },
		        title: {
		            text: '贫困户人均收入分组情况',
		            textStyle:{fontWeight:'500',fontSize:'17'},
		            
		        },
		        credits:{
		            enabled:false
		        },
		        subtitle: {
		            text: '单位（户）'
		            	
		        },
		        plotOptions: {
		            column: {
		                depth: 25
		            }
		        },
		        xAxis: {
		        	categories: ['500元以下', '500-1000元', '1000-1500元','1500-2000元','2000-2500元','2500-2800元','2800元以上']
			       },
			       legend: { enabled  : false
			    	 },
		        yAxis: {
		            title: {
		                text: null
		            }
		        },
		        series: [{
		            name: '户数（户）',
		            data: [v1, v3, v5, v7, v9, v11, v13],
		        }]
		    });
	}
	 
}

