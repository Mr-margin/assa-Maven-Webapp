$(function () {
	
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	table();
});
var obj;
var myChart_2;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart_2){
			myChart_2.resize();
		}
	}catch(e){
	}
};
//表格数据
function table(){
	var data = ajax_async_t("/assa/getBfdxHuc_14.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'asc'},"json");
	var html = "";
	var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0;
	$.each(data,function(i,item){
		
			html +='<tr><td>'+(i+1)+'</td><td>'+item.name+'</td><td>'+item.z_hu+'</td>'+
			'<td>'+item.value+'</td>'+
			'<td>'+item.v2+'</td>'+
			'<td>'+item.v3+'</td>'+
			'<td>'+item.v4+'</td>'+
			'<td>'+item.v5+'</td>'+
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
//			A2+=parseFloat(item.value)
//			A3+=parseInt(item.v2)
//			A4+=parseInt(item.v3)
//			A5+=parseInt(item.v4)
//			A6+=parseInt(item.v5)
			
		
	})
	html +='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td>'+
	'<td><strong>'+parseInt(A1)+'</strong></td>'+
	'<td><strong>'+parseFloat(A2).toFixed(2)+'</strong></td>'+
	'<td><strong>'+parseInt(A3)+'</strong></td>'+
	'<td><strong>'+parseInt(A4)+'</strong></td>'+
	'<td><strong>'+parseInt(A5)+'</strong></td>'+
	'<td><strong>'+parseInt(A6)+'</strong></td>'+
	'</tr>';
	$("#sr_table").html(html);
	$("#sr_table td").css("text-align","center");
	
	var option = {
	    title : {
	        text: '贫困户入户路情况',
	        x:'center',
	        textStyle:{fontWeight:'500',fontSize:'17'}
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b} : <br/>{c} ({d}%)"
	    },
	    legend: {
			orient : 'vertical',
			x : 'left',
			data:['普通泥土路','砂石公路','水泥路面公路','沥青路面公路']
		},
	    calculable : true,
	    series : [
	        {
	            type:'pie',
	            radius : [30, 110],
	            center : ['50%', '50%'],
	            roseType : 'area',
	            data:[
	                {value:A3, name:'普通泥土路'},
	                {value:A4, name:'砂石公路'},
	                {value:A5, name:'水泥路面公路'},
	                {value:A6, name:'沥青路面公路'}
	            ]
	        }
	    ]
	};
	myChart_2 = echarts.init(document.getElementById('pie'));
	//点击事件
	myChart_2.on('click', function (params) {
		console.log(params);
	    diqu=params.name;
	    parent.setSelVal(diqu);
	});
	myChart_2.setOption(option);
	
}