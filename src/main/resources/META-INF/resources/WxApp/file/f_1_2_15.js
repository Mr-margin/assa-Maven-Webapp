$(document).ready(function(){
	title='贫困户与村主干路距离';
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
var obj;
$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	table();
});
var obj;
var myChart;
var com=[];
var num=[];
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
//表格数据
function table(){
//	console.log(obj.com);
	var data = ajax_async_t("/assa/getBfdxHu_15.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"asc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0;
		$.each(data,function(i,item){

			html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td>'+
			'<td class="text-center">'+item.z_hu+'</td>'+
			'<td class="text-center">'+item.v1+'</td>'+
			'<td class="text-center">'+item.v3+'</td>'+
			'<td class="text-center">'+item.v5+'</td>'+
			'<td class="text-center">'+item.v7+'</td>'+
			'<td class="text-center">'+item.v9+'</td>'+
			'<td class="text-center">'+item.v11+'</td>'+
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
			if(item.v11 == '' || item.v11 == null || item.v11 == undefined){
				
			}else{
				A7+=parseInt(item.v11);
				
			}
//			A1+=parseInt(item.z_hu)
//			A2+=parseInt(item.v1)
//			A3+=parseInt(item.v3)
//			A4+=parseInt(item.v5)
//			A5+=parseInt(item.v7)
//			A6+=parseInt(item.v9)
//			A7+=parseInt(item.v11)
		})
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
		'<td class="text-center"><strong>'+A1+'</strong></td>'+
		'<td class="text-center"><strong>'+A2+'</strong></td>'+
		'<td class="text-center"><strong>'+A3+'</strong></td>'+
		'<td class="text-center"><strong>'+A4+'</strong></td>'+
		'<td class="text-center"><strong>'+A5+'</strong></td>'+
		'<td class="text-center"><strong>'+A6+'</strong></td>'+
		'<td class="text-center"><strong>'+A7+'</strong></td>'+
		'</tr>';
		$("#jbqk_table").html(html);
		
		var rang = 0;
		for(i=2;i>7;i++){
			if(A+i){
				rang+=1;
			}
		}
//		if(rang!=0){
//			for(y=0;y<rang;y++){
//				com[i]=
//			}
//		}
//		com[0]=item.com_name;
//		num[0];
		
		var option = {
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b}: {c} ({d}%)"
				},title : {
					text: title,
					x : 'center',
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					orient: 'vertical',
					x: 'left',
					y: 'bottom',
					data:['1公里以内(不含)户数','1(含)-3公里(不含)户数','3(含)-5公里(不含)户数','5(含)-10公里(不含)户数','10(含)-20公里(不含)户数','20公里(含)以上户数']
				},
				color:[
				       '#C1232B','#27727B','#FCCE10','#E87C25','#B5C334',
				       '#FE8463'
				       ],
				       series: [
				                {
				                	name:title,
				                	type:'pie',
				                	radius: ['30%', '50%'],
				                	center: ['52%', '40%'],
				                	avoidLabelOverlap: true,
				                	label: {
				                		normal: {
				                			show: true,
				                			position: 'outside'
				                		},
				                		emphasis: {
				                			show: true,
				                			textStyle: {
				                				fontWeight: 'bold'
				                			}
				                		}
				                	},
				                	labelLine: {
				                		normal: {
				                			show: true
				                		}
				                	},
				                	data:[
				                	      {value:A2, name:'1公里以内(不含)户数'},
				                	      {value:A3, name:'1(含)-3公里(不含)户数'},
				                	      {value:A4, name:'3(含)-5公里(不含)户数'},
				                	      {value:A5, name:'5(含)-10公里(不含)户数'},
				                	      {value:A6, name:'10(含)-20公里(不含)户数'},
				                	      {value:A7, name:'20公里(含)以上户数'}
				                	      ]
				                }
				                ]
		};
		myChart = echarts.init(document.getElementById('pkh_fb'));
		myChart.setOption(option);
	}
	
}
