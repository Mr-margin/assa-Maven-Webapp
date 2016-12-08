$(document).ready(function(){
	title='贫困户帮扶责任人落实情况';
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
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	table();
	bar();
});
var obj;
var com_name = [];
var num = [];
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
	var data = ajax_async_t("/assa/getBfdxHu_7.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.jpg" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0;
		$.each(data,function(i,item){
			if (item.com_name == '汇总'){
			}else{
				html +='<tr><td>'+(i+1)+'</td><td>'+item.com_name+'</td><td>'+item.z_hu+'</td>'+
				'<td>'+item.v1+'</td><td>'+item.BILI+'%</td>'+
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
//				A1=parseInt(item.z_hu)+parseInt(A1);
//				A2=parseInt(item.v1)+parseInt(A2);
			}
		})
		a1=A2/A1;
		a2=a1*100;
		a3=a2.toFixed(2);
		html+='<tr><td>'+Number(data.length+1)+'</td><td><strong>汇总</strong></td><td><strong>'+A1+'</strong></td><td><strong>'+A2+'</strong></td><td><strong>'+a3+'%</strong></td></tr>';
		$("#bfqk_table").html(html);
	}
}


function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_7.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"desc"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.jpg" class="center-block">')
	}else{
		$.each(data,function(i,item){
			com_name[i] = item.com_name;
			num[i]=item.BILI;

		})
		option = {
			title: {
				x: 'center',
				text: title,
				textStyle:{fontWeight:'500',fontSize:'17'},
				subtext: '单位：百分比',
				subtextStyle: {
					color: 'black'
				}
			},
			tooltip: {
				trigger: 'item',
				formatter: "{b}</br>帮扶人落实比例 : {c}%"
			},
			calculable: true,
			grid: {
				borderWidth: 0,
				y: 80,
				y2: 60
			},
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data: com_name
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}%'
				}
			},
			series: [
			         {
			        	 name: '贫困户帮扶责任人落实情况',
			        	 type: 'bar',
			        	 itemStyle: {
			        		 normal: {
			        			 color: '#0098d9'
			        		 }
			        	 },
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
			        	 data: num,
			         }
			         ]
		};
		myChart_1 = echarts.init(document.getElementById('bar'));
		myChart_1.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart_1.setOption(option);
	}
}
