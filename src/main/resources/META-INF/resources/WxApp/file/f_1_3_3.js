$(document).ready(function(){
	title='贫困村贫困发生率';
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
var obj = {};
$(document).ready(function() {
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
	pkcsbbz();
	biaoge()
});

function biaoge(){
	var v1=0;
	var v2=0;
	var v3=0;
	var v4=0;
	var aa;//名称
	var bb;//人数
	var cc;//户数
	var dd;//落实帮扶责任人
	var ee;//制定帮扶计划
	var ff;//帮扶措施
	var gg;//走访记录
	
	var data = ajax_async_t("/assa/getfsl.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2},"json");
	if(data==0){
		$("#biaoge").html('');
	}else{
		var html="";
    	$.each(data, function(i,item) {
    		aa=i+1
    		//户数
    		if(item.name=="0"||item.name==null){
    			cc='0';
    		}else{
    			cc=''+item.name+'';
    		}
    		//人数
    		if(item.f1=="0"||item.f1==null){
    			bb='0';
    		}else{
    			bb=''+item.f1+'';
    		}
    		//落实帮扶责任人
    		if(item.f2=="0"||item.f2==null){
    			dd='0';
    		}else{
    			dd=''+item.f2+'';
    		}
    		//制定帮扶计划
    		if(item.f3=="0"||item.f3==null){
    			ee='0';
    		}else{
    			ee=''+item.f3+'';
    		}
    		if(item.f5=="0"||item.f5==null){
    			gg='0';
    		}else{
    			gg=''+item.f5+'';
    		}
    		//帮扶措施
    		
    		v1=parseInt(item.f1)+parseInt(v1);
    		v2=parseInt(item.f2)+parseInt(v2);
    		v3=parseInt(item.f3)+parseInt(v3);
    		v4=parseInt(item.f5)+parseInt(v4);
    		
    		//循环赋值
    		html+='<tr><td class="text-center">'+aa+'</td><td class="text-center">'+cc+'</td><td class="text-center">'+bb+'</td><td class="text-center">'+dd+'</td><td class="text-center">'+ee+'</td><td class="text-center">'+gg+'</td></tr>';
    	})
    	aa=aa+1;
    	html+='<tr><td class="text-center">'+aa+'</td><td class="text-center"><strong>汇总</strong></td><td class="text-center"><strong>'+v1+'</strong></td><td class="text-center"><strong>'+v2+'</strong></td><td class="text-center"><strong>'+v3+'</strong></td><td class="text-center"><strong>'+v4+'</strong></td></tr>'
		//对下方的表格赋值
		$("#xiafangzongbiao").html(html);
	}
}

var myChart
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
//贫困发生率固定数据  2017-4-18
var fslArray = {
	"呼和浩特市" : 0.72,
	"包头市" : 1.02,
	"呼伦贝尔市" : 4.16,
	"兴安盟" : 7.54,
	"通辽市" : 4.50,
	"赤峰市" : 5.23,
	"锡林郭勒盟" : 3.33,
	"乌兰察布市" : 5.52,
	"鄂尔多斯市" : 0.13,
	"巴彦淖尔市" : 1.81,
	"乌海市" : 0.25,
	"阿拉善盟" : 0.07,
	
	
	"土默特左旗" : 0.8,
	"托克托县" : 0.1,
	"和林格尔县" : 1.6,
	"清水河县" : 2.2,
	"武川县" : 0.9,
	
	"土默特右旗" : 0.7,
	"达尔罕茂明安联合旗" : 2.6,
	"固阳县" : 2.3,
	
	"鄂伦春自治旗" : 14.4,
	"扎兰屯市" : 4.7,
	"阿荣旗" : 1.1,
	"鄂温克族自治旗" : 5,
	"额尔古纳市" : 0.4,
	"新巴尔虎左旗" : 0.8,
	"新巴尔虎右旗" : 0.3,
	"莫力达瓦达斡尔族自治旗" : 5.2,
	
	"科尔沁右翼中旗" : 7.5,
	"科尔沁右翼前旗" : 8.2,
	"突泉县" : 8.9,
	"扎赉特旗" : 7.2,
	"阿尔山市" : 2.8,
	"乌兰浩特市" : 0.3,
	
	"科尔沁左翼后旗" : 5.9,
	"库伦旗" : 9.1,
	"奈曼旗" : 8.7,
	"开鲁县" : 2.7,
	"扎鲁特旗" : 3.1,
	"科尔沁左翼中旗" : 4.7,
	"科尔沁区" : 0.4,
	
	"宁城县" : 5.4,
	"敖汉旗" : 5.9,
	"喀喇沁旗" : 5.8,
	"克什克腾旗" : 3,
	"松山区" : 3,
	"元宝山区" : 1.7,
	"翁牛特旗" : 5.9,
	"阿鲁科尔沁旗" : 6.4,
	"林西县" : 4.2,
	"红山区" : 0.3,
	"巴林左旗" : 10,
	"巴林右旗" : 5.5,
	
	"苏尼特左旗" : 0.6,
	"正蓝旗" : 0.7,
	"太仆寺旗" : 5.8,
	"阿巴嘎旗" : 0.8,
	"多伦县" : 0.9,
	"镶黄旗" : 0.7,
	"正镶白旗" : 5.5,
	"苏尼特右旗" : 11.2,
	
	"察哈尔右翼中旗" : 8.2,
	"卓资县" : 4.8,
	"凉城县" : 2.3,
	"丰镇市" : 2.3,
	"四子王旗" : 6.6,
	"察哈尔右翼前旗" : 8.6,
	"化德县" : 7.9,
	"兴和县" : 6.1,
	"察哈尔右翼后旗" : 4.3,
	"商都县" : 6.9,
	
	"准格尔旗" : 0.3,
	"杭锦旗" : 0.2,
	"鄂托克旗" : 0.2,
	"乌审旗" : 0.2,
	"伊金霍洛旗" : 0.1,
	"达拉特旗" : 0.2,
	
	"乌拉特前旗" : 4.7,
	"五原县" : 0.3,
	"临河区" : 0.8,
	"磴口县" : 1.2,
	"杭锦后旗" : 2,
	"乌拉特中旗" : 1.3,
	"乌拉特后旗" : 1.7,
	
	"海南区" : 0.4,
	
	"阿拉善右旗" : 0.5
	
};
//统计图方法
function pkcsbbz(){
	var order="VF41";
	myChart = echarts.init(document.getElementById('fsl'));//声明id为mapChart的div为图形dom 
	var data = ajax_async_t("/assa/getfsl.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2, order:order},"json"); //调用ajax通用方法
	if(data == '0'){
		$("#z").html('<img class="center-block" src="../../img/wu.png">');
	}else{
		var count=[];
		var count2=[];
		var count3=[];
		var count4=[];
		var count5=[];
	
		var option = {
				title: {
					text: title,
					x:'center',
					subtext: '单位：百分比',
					subtextStyle: {
						color: 'black'
					},
					textStyle:{fontWeight:'500',fontSize:'17'}
				},
				legend: {
					data:['贫困村贫困发生率'],
					y:'bottom'
				},
				grid: {
					left: '0%',
					right: '2%',
					bottom: '15%',
					containLabel: true
				},
				tooltip: {
					trigger: 'axis',
					axisPointer : {// 坐标轴指示器，坐标轴触发有效
						type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
					}
				},
				color:['#26C0C0'],
				xAxis: {
					axisLabel:{//坐标轴文本标签选项
						interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
						rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
						margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
					},
					data:[]
				},
				yAxis: {
					axisLabel: {
			            formatter: '{value} %'
			        }
				},
				series: [
				         {
				        	 name: '贫困村贫困发生率',
				        	 type: 'bar',
				        	 label: {
				        		 normal: {
				        			 show: true,
					        		 position: 'top',
					        		 formatter: '{c}%',
					        		 textStyle: {
					        			 color:"black"
					        		 }
				        		 }
				        	 },
				        	 data: []
				         }
				         ]
		};
		$.each(data,function(i,indx){
			count[i]=indx.name;
			if(fslArray[indx.name]){
				count2[i]=fslArray[indx.name];
			}else{
				count2[i]=indx.f4;
			}
			
			
		});
		option.xAxis.data=count;
		option.series[0].data=count2;
		//点击事件
		myChart.on('click', function (params) {
			console.log(params);
		    diqu=params.name;
		    parent.setSelVal(diqu);
		});
		myChart.setOption(option);
	}
}