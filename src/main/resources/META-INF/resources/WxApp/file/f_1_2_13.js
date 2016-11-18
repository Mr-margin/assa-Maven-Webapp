$(function () {
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
	canshu=Request['canshu']; 
	obj = JSON.parse(canshu);
	console.log(Request)

	table();
	bar();
});
var obj;
var myChart;
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
	var data = ajax_async_t("/assa/getBfdxHu_13.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:'a1.PKID'},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.jpg" class="center-block">')
	}else{
		var html = "";
		var A1=0,A2=0;
		var A3,A4;
		$.each(data,function(i,item){

			html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td><td class="text-center">'+item.z_hu+'</td>'+
			'<td class="text-center">'+item.v1+'</td>'+
			'<td class="text-center">'+item.v2+'</td>'+
			'</tr>';
			if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
				
			}else{
				A1+=parseInt(item.z_hu)
				
			}
			if(item.v1 == '' || item.v1 == null || item.v1 == undefined){
				
			}else{
				A2+=parseInt(item.v1);
				
			}
//			A1+=parseInt(item.z_hu)
//			A2+=parseInt(item.v1)

		})
		A3=A2/A1*100
		A4=parseFloat(A3).toFixed(2);
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
		'<td class="text-center"><strong>'+A1+'</strong></td>'+
		'<td class="text-center"><strong>'+A2+'</strong></td>'+
		'<td class="text-center"><strong>'+A4+'%</strong></td>'+
		'</tr>';
		$("#bfqk_table").html(html);
	}
	 
}

//环形图
function bar(){
	var data = ajax_async_t("/assa/getBfdxHu_13.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"a1.V2"},"json");
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.jpg" class="center-block">')
	}else{
		var com_name = [];
		var num = [];
		$.each(data,function(i,item){
			com_name[i] = item.com_name;
			num[i]=item.v2;
		})
		var option = {
			title: {
				x: 'center',
				text: '贫困户党员情况',
				subtext: '单位：户',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			tooltip: {
				trigger: 'item'
			},
			calculable: true,
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
			grid: {
				left: '0%',
				right: '3%',
				bottom: '15%',
				containLabel: true
			},
			series: {
				name: '贫困户帮扶责任人落实情况',
				type: 'bar',
				itemStyle: {
					normal: {
						color:'#b5c334'
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

		};
		myChart = echarts.init(document.getElementById('bar'));
		//点击事件
		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart.setOption(option);         
	}
	           
}
