$(document).ready(function(){
	//日期区间初始化
	$("#bdate .input-daterange").datepicker({keyboardNavigation:!1,forceParse:!1,autoclose:!0});
	
	title='走访记录情况';
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
	setTimeout(function(){
		a1();
	},500);
	
})
//json排序
function sortByKey(array, key) {
    return array.sort(function(a, b) {
        var x = a[key]; var y = b[key];
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
var diqu="";
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
        +"<span id=\"waitTime\" style=\"font-weight: bold;padding: 5px 20px;color: #1AB394;height: 20px;line-height: 20px;text-align: center;display: block;\">加载中,请稍后...</span></div>";
		$("#tu_1").html(choose1);
}
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
	if(v){
		
		$.each(data,function(i,item){
			data = JsonSort(data, v);
			
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
			count=[];
			count0=[];
			$.each(data,function(i,item){
				if(t){
					data = JsonSort(data, t);
					count0[i]=item[t];//值
				}else{
					data = sortByKey(data, 'V0');
					count0[i]=item.V0;//值
				}
				
				count[i]=item.V1;//区域
			});
			
		}
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
			tooltip: {
				trigger: 'axis',
				axisPointer : {// 坐标轴指示器，坐标轴触发有效
					type : 'shadow'// 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : '{b}<br/>{a0}: {c0} 篇'
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
				}
			},
			series: [
			         {
			        	 name: '走访记录数',
			        	 type: 'bar',
			        	 data: count0
			         }
		         ]
	};
	myChart = echarts.init(document.getElementById('tu_1'));//声明id为myChart的div为图形dom
	myChart.on('click', function (params) {
		showWaitTime();
		obj.com=params.name;
		if(a>0&&a<3){
			titleNames+="-"+params.name;
		}else if(a>=3){
			return;
		}else{
			titleNames+=params.name;
		}
		title="走访记录情况("+titleNames+")";
		setTimeout(function(){
			a1();
		},500);
		a++;
	});
	
	myChart.setOption(option);
	if(!v){
		//重新排序
		$("#the_all").click();
	}
}
//表格数据
function table  () {
//	tables +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+ item.V1 +'</td>'+
	var data = ajax_async_t("/assa/getBfdxHu_1.do",{name:obj.com,year:obj.year,q1:obj.q1,q2:obj.q2,q3:obj.q3,q4:obj.q4,q5:obj.q5,t1:obj.t1,t2:obj.t2,desc:"asc"},"json");
	var html = "";
	if(data=='' || data == null || data==undefined){
		$("#pd").html('<img src="../../img/wu.png" class="center-block">')
	}else{
		var A1=0,A2=0;
		$.each(data,function(i,item){
				html +='<tr><td class="text-center">'+(i+1)+'</td><td class="text-center">'+item.com_name+'</td>'+
				'<td class="text-center">'+item.z_hu+'</td>'+
				'<td class="text-center">'+item.z_ren+'</td>'+
				'</tr>';
				if(item.z_hu == '' || item.z_hu == null || item.z_hu == undefined){
					
				}else{
					A1=parseInt(item.z_hu)+parseInt(A1);
					
				}
				if(item.z_ren == '' || item.z_ren == null || item.z_ren == undefined){
					
				}else{
					A2=parseInt(item.z_ren)+parseInt(A2);
					
				}
		})
		html +='<tr><td class="text-center">'+Number(data.length+1)+'</td><td class="text-center"><strong>汇总</strong></td>'+
				'<td class="text-center"><strong>'+A1+'</strong></td>'+
				'<td class="text-center"><strong>'+A2+'</strong></td>'+
				'</tr>';
		$("#tableChart").html(html);
	}

}