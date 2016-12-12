$(document).ready(function(){
	title='贫困户其他致贫原因分析';
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
	var data = ajax_async_t("/assa/getBfdxHu_5.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0,A9=0,A10=0,A11=0,A12=0,A13=0,A14=0;
		$.each(data,function(i,item){
			if (item.com_name == '汇总'){

			}else{
				html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td><td>'+item.z_hu+'</td>'+
				'<td>'+item.v1+'</td><td>'+item.v3+'</td><td>'+item.v5+'</td>'+
				'<td>'+item.v7+'</td>'+
				'<td>'+item.v9+'</td>'+
				'<td>'+item.v11+'</td>'+
				'<td>'+item.v13+'</td>'+
				'<td>'+item.v15+'</td>'+
				'<td>'+item.v17+'</td>'+
				'<td>'+item.v19+'</td>'+
				'<td>'+item.v21+'</td>'+
				'<td>'+item.v23+'</td>'+
				'<td>'+item.v25+'</td>'+
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
				if(item.v23 == '' || item.v23 == null || item.v23 == undefined){
					
				}else{
					A13=parseInt(item.v23)+parseInt(A13);
					
				}
				if(item.v25 == '' || item.v25 == null || item.v25 == undefined){
					
				}else{
					A14=parseInt(item.v25)+parseInt(A14);
					
				}
//				A1=parseInt(item.z_hu)+parseInt(A1);
//				A2=parseInt(item.v1)+parseInt(A2);
//				A3=parseInt(item.v3)+parseInt(A3);
//				A4=parseInt(item.v5)+parseInt(A4);
//				A5=parseInt(item.v7)+parseInt(A5);
//				A6=parseInt(item.v9)+parseInt(A6);
//				A7=parseInt(item.v11)+parseInt(A7);
//				A8=parseInt(item.v13)+parseInt(A8);
//				A9=parseInt(item.v15)+parseInt(A9);
//				A10=parseInt(item.v17)+parseInt(A10);
//				A11=parseInt(item.v19)+parseInt(A11);
//				A12=parseInt(item.v21)+parseInt(A12);
//				A13=parseInt(item.v23)+parseInt(A13);
//				A14=parseInt(item.v25)+parseInt(A14);
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
		'<td><strong>'+A13+'</strong></td>'+
		'<td><strong>'+A14+'</strong></td>'+
		'</tr>';
		$("#zpyy_table").html(html);
	}
	 
}

//环形图
function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_6.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,shi:"0"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		option = {
				color: ['#6495ED'],
				title: {
					text: title,
					subtext: '单位：户',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'},
					x: 'center'
				},
				tooltip : {
					trigger: 'axis',
					axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					},
					formatter: function (params) {
						var tar = params[0];
						return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
					}
				},
				grid: {
					left: '0%',
					right: '5%',
					bottom: '10%',
					containLabel: true
				},
				xAxis :{
				        	 type: "category",//坐标轴类型，横轴默认为category，纵轴默认为数值型value
				        	 name: "致贫原因",//坐标轴名称
				        	 axisLabel:{//坐标轴文本标签选项
				        		 interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
				        		 rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
				        		 margin:2,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				        	 },
				        	 splitLine: {show: false},//分隔线，默认是显示，show来控制显示状态
				        	 data:['因病致贫','因残致贫','因学致贫','因灾致贫','缺土地',
				        	       '缺水', '缺技术', '缺劳力', '缺资金', '交通条件落后','自身发展力不足','因婚致贫','其他']//X轴的坐标
				         },
				         yAxis :{
				        	 type : 'value'
				         },
				         series : [
				                   {
				                	   name:'生活费',
				                	   type:'bar',
				                	   stack: '总量',
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
				                	   data:[data[0].v1, data[0].v3, data[0].v5, data[0].v7, data[0].v9, data[0].v11,data[0].v13,data[0].v15,data[0].v17,data[0].v19,
				                	         data[0].v21,data[0].v23,data[0].v25
				                	         ]
				                   }
				                   ]
		};

		myChart_1 = echarts.init(document.getElementById('bar'));
		myChart_1.setOption(option);     
	}
	               
}
