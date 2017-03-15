$(document).ready(function(){
	//日期区间初始化
	$("#bdate .input-daterange").datepicker({keyboardNavigation:!1,forceParse:!1,autoclose:!0});
	
	title='走访记录情况统计';
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
$(function () {
	
	var Request = new Object();
	Request = GetRequest();//截取URL的方法
//	canshu=Request['canshu']; 
//	obj = JSON.parse(canshu);
	obj.com = Request['com'];
	obj.year = Request['year'];
	obj.q1 = Request['q1'];
	obj.q2 = Request['q2'];
	obj.q3 = Request['q3'];
	obj.q4 = Request['q4'];
	obj.q5 = Request['q5'];
	obj.t1 = Request['t1'];
	obj.t2 = Request['t2'];
	obj.sTime=$("input[name='start_date']").val();
	obj.eTime=$("input[name='end_date']").val();
	showWaitTime();
	diqu.push(obj.com);
	setTimeout(function(){
		a1();
	},500);
	
})
//json排序
function sortByKey(array, key) {
    return array.sort(function(a, b) {
        var y = a[key]*1; var x = b[key]*1;
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
}
/*
@function     JsonSort 对json排序
@param        json     用来排序的json
@param        key      排序的键值
*/
function JsonSort(json,key){
//console.log(json);
for(var j=1,jl=json.length;j < jl;j++){
    var temp = json[j],
        val  = temp[key],
        i    = j-1;
    while(i >=0 && json[i][key]>val){
        json[i+1] = json[i];
        i = i-1;    
    }
    json[i+1] = temp;
    
}
//console.log(json);
return json;
}
var count=[];//区划名称
var count0=[];//总记录数

var obj = {};

var myChart_ring,myChart;
window.onresize=function () { //浏览器调整大小后，自动对所有的图进行调整
	try{
		if(myChart){
			myChart.resize();
		}
	}catch(e){
	}
};
var yMax = 0;//y轴填充赋值
var diqu=[];
var a=0;//记录下钻次数
var titleNames="";
var title='';
$("#the_submit").click(function(){
	obj.sTime=$("input[name='start_date']").val();
	obj.eTime=$("input[name='end_date']").val();
	showWaitTime();
	setTimeout(function(){
		a1('','V7');
	},500);
})
//动画等待
function showWaitTime(){
	var choose1="<div class=\"ibox-content\"><div class=\"spiner-example\"><div class=\"sk-spinner sk-spinner-cube-grid\">"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"<div class=\"sk-cube\"></div>"
        +"</div>"
        +"</div>"
        +"<span id=\"waitTime\" style=\"font-weight: bold;padding: 5px 20px;color: #1797F6;height: 20px;line-height: 20px;text-align: center;display: block;\">加载中,请稍后...</span></div>";
		$("#tu_1").html(choose1);
}
//日记类型全部
$("#the_type_all").click(function(){
	$("#bdate").hide();
	$("#the_type_all").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V17");
});
//了解基本情况
$("#the_type_one").click(function(){
	$("#bdate").hide();
	$("#the_type_one").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V11");
});
//填写扶贫手册
$("#the_type_two").click(function(){
	$("#bdate").hide();
	$("#the_type_two").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V12");
});



//制定脱贫计划
$("#the_type_three").click(function(){
	$("#bdate").hide();
	$("#the_type_three").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V13");
});

//落实资金项目
$("#the_type_four").click(function(){
	$("#bdate").hide();
	$("#the_type_four").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V14");
});

//宣传扶贫政策
$("#the_type_five").click(function(){
	$("#bdate").hide();
	$("#the_type_five").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V15");
});

//节日假日慰问
$("#the_type_six").click(function(){
	$("#bdate").hide();
	$("#the_type_six").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_seven").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V16");
});

//其他帮扶活动
$("#the_type_seven").click(function(){
	$("#bdate").hide();
	$("#the_type_seven").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_type_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_one").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_three").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_four").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_five").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_six").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_type_two").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V10");
});



//全部
$("#the_all").click(function(){
	$("#bdate").hide();
	$("#the_all").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");
	
	a1("V0");
});
//当天
$("#the_day").click(function(){
	$("#bdate").hide();
	$("#the_day").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	a1("V2");
});
//近一周
$("#the_one_week").click(function(){
	$("#bdate").hide();
	$("#the_one_week").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	a1("V3");
});
//近两周
$("#the_two_week").click(function(){
	$("#bdate").hide();
	$("#the_two_week").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	a1("V4");
});
//近一月
$("#the_one_month").click(function(){
	$("#bdate").hide();
	$("#the_one_month").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	a1("V5");
});
//近三月
$("#the_three_month").click(function(){
	$("#bdate").hide();
	$("#the_three_month").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_randow_date").attr("class","btn btn-outline btn-success btn-xs");

	a1("V6");
});
//自定义
$("#the_randow_date").click(function(){
	$("#bdate").show();
	$("#the_randow_date").removeAttr("class");
	this.className='btn btn-success btn-xs';
	
	$("#the_all").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_day").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_week").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_two_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_one_month").attr("class","btn btn-outline btn-success btn-xs");
	$("#the_three_month").attr("class","btn btn-outline btn-success btn-xs");

	a1("V7");
});
//v 判断是否需要走后台，即是否页面快速查询按钮操作    t代表自定义查询
function a1(v,t){
	count=[];
	count0=[];
	if(v){
		data = sortByKey(data, v);
		$.each(data,function(i,item){
			if(i>0){
			}else{
				yMax=item[v];
			}
			count0[i]=item[v];//值
			count[i]=item.V1;//区域
		});
		
	}else{
		try
		{
			data = JSON.parse(ajax_async_t("../../getF_1_1_1.do", {name:obj.com,sTime:obj.sTime,eTime:obj.eTime},"text","1","0")); //调用ajax通用方法
		}
		catch(err)
		{
			toastr["error"]("error", "不能继续下钻了！！！");
		}
		if(data==0){
			$("#z").html('<img class="center-block" src="../../img/wu.png">');
			
		}else{
			$.each(data,function(i,item){
				if(t){
					data = sortByKey(data, t);
					count0[i]=item[t];//值
					if(i>0){
					}else{
						yMax=item[t];
					}
				}else{
					data = sortByKey(data, 'V0');
					count0[i]=item.V0;//值
					if(i>0){
					}else{
						yMax=item.V0;
					}
				}
				
				count[i]=item.V1;//区域
			});
			
		}
	}
	//填充内容
	var dataShadow = [];

	for (var i = 0; i < data.length; i++) {
	    dataShadow.push(yMax);
	}
	var option = {//柱状图
			title: {
				text: title,
				x:'center',
				subtext: '单位：次',
				subtextStyle: {
					color: 'black'
				},
				textStyle:{fontWeight:'500',fontSize:'17'}
			},
			legend: {
				data:['走访记录数'],
				y:'bottom'
			},
			toolbox: {
				right:'150',
				itemSize:20,
				feature: {
					myTool1: {
		                show: true,
		                title: '初始化(初始化到第一级)',
		                icon: 'image://../../img/arrow_refresh.png',
		                onclick: function (){
		                	obj.com = diqu[0];
		                	titleNames="";//初始化
		                	title="走访记录情况统计";
		                	showWaitTime();
		                	setTimeout(function(){
		                		a1('','V7');
		                	},500);
		                    /*alert(data[0].V8+obj.com+diqu[a]);
		                    if(a==0){
		                    }else{
		                    	a--;
		                    }*/
		                }
		            }
				}
			},
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0} 次'
			},
			grid: {
				left: '0%',
				right: '9%',
				bottom: '10%',
				containLabel: true
			},
			color:['#6495ED'],
			xAxis: {
				axisLabel:{//坐标轴文本标签选项
					interval:0,//小标记显示挑选间隔，默认为'auto'，可选为：'auto'（自动隐藏显示不下的） | 0（全部显示） | {number}（用户指定选择间隔）
					rotate:25,//标签旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90-90
					margin:8,//坐标轴文本标签与坐标轴的间距，默认为8，单位px
				},
				data:count
			},
			yAxis: {
				axisLabel: {
					formatter: '{value}'
				}/*,
				min:0,
				max:yMax*/
			},
			series: [{
	        	 name: '走访记录数',
	        	 type: 'bar',
	        	 data: count0,
	        	 /*markPoint : {
	                 data : [
	                     {type : 'max', name: '最大值'},
	                     {type : 'min', name: '最小值'}
	                 ]
	             },
	             markLine : {
	                 data : [
	                     {type : 'average', name: '平均值'}
	                 ]
	             }*/
	         },{ // For shadow
						name: '走访记录',
			            type: 'bar',
			            itemStyle: {
			                normal: {color: 'rgba(0,0,0,0.05)'}
			            },
			            barGap:'-100%',
			            barCategoryGap:'40%',
			            data: dataShadow,
			            animation: true
			        }
		         ]
	};
	myChart = echarts.init(document.getElementById('tu_1'));//声明id为myChart的div为图形dom
	myChart.on('click', function (params) {
		//记录本次地区名
//		diqu.push(params.name);
		obj.com=params.name;
		if(data[0].V8<5&&a>0){
			titleNames+="-"+params.name;
		}else if(data[0].V8>=5){
			return;
		}else{
			titleNames+=params.name;
		}
		showWaitTime();
		title="走访记录情况统计("+titleNames+")";
		setTimeout(function(){
			a1();
		},500);
		a++;
	});
	
	myChart.setOption(option);
	if(!v){
		//重新排序
		$("#the_all").click();
		$("#the_type_all").click();
	}
}
