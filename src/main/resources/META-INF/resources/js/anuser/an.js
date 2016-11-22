$(document).ready(function() {
	show_jbqk();
});
var pkid;
function show_jbqk(){
	var Request = new Object();
	Request = GetRequest();//截取URL的方法

	pkid=Request['pkid']; 
//alert(pkid);
	var data = ajax_async_t("anGetPk.do",{pkid:pkid},"json");
	$('#show_hz_v2').text(data.data4[0].SHI);
	$('#show_hz_v3').text(data.data4[0].XIAN);
	$('#show_hz_v4').text(data.data4[0].XIANG);
	$('#show_hz_v5').text(data.data4[0].CUN);
	$('#show_hz_v6').text(data.data1[0].v6);
	
	$('#show_hz_sbbz').text(data.data1[0].sys_standard);//识别标准
	$('#show_hz_pkgsx').text(data.data1[0].v22);//贫困户属性
	$('#show_hz_rk').text(data.data1[0].v9);//人口
	$('#show_hz_sfjls').text(data.data1[0].v29);//是否军烈属
	$('#show_hz_dszn').text(data.data1[0].v30);//是否独生子女
	$('#show_hz_snh').text(data.data1[0].v31);//是否双女户
//	$('#show_hz_address').html(data.data1[0].basic_address);//家庭住址
	$('#show_hz_zpyy').text(data.data1[0].v23);//致贫原因
	$('#show_hz_qtzpyy').html(data.data1[0].v33);//其他致贫原因
	$('#show_hz_zfyysm').html(data.data1[0].basic_explain);//致贫原因说明
	
	
//	$('#show_hz_phone').text(data.data1[0].v25);//电话
//	$('#show_hz_sfz').text(data.data1[0].v8);//证件号码
//	$('#show_hz_bank').text(data.data1[0].v26);//开户银行名称
//	$('#show_hz_banknum').text(data.data1[0].v27);//银行卡号 
	
	//户主的个人信息
	$('#show_hz_xm').text("户主：     "+data.data1[0].v6);//户主姓名
	$('#shiw_hz_sex').text(data.data1[0].v7);//性别
	$('#show_hz_mz').text(data.data1[0].v11);//民族
	$('#show_hz_whcd').text(data.data1[0].v12);//文化程度
	$('#show_hz_sfzx').text(data.data1[0].v13);//在校生
	$('#show_hz_jkzk').text(data.data1[0].v14);//健康状况
	$('#show_hz_ldl').text(data.data1[0].v15);//劳动力
	$('#show_hz_wgqk').text(data.data1[0].v16);//务工状况
	$('#show_hz_wgsj').text(data.data1[0].v17);//务工时间
	$('#show_hz_cjxnh').text(data.data1[0].v18);//是否参加新农合
	$('#show_hz_ylbx').text(data.data1[0].v19);//是否参加养老保险
	$('#show_hz_zzmm').text(data.data1[0].v28);//政治面貌
	$('#show_hz_xyjr').text(data.data1[0].v32);//是否现役军人
	if(data.data1[0].pic_path==""||data.data1[0].pic_path==null||data.data1[0].pic_path==undefined||data.data1[0].pic_path=="-"){
		
	}else{
		$('#hz_pic').html('<div class="val-md-1"><div class="text-center"><img src="'+data.data1[0].pic_path+'" style="margin:0;vertical-align:baseline;width:200px;height:250px;"></div></div>');
	}
	
	//家庭成员
	html = '';
	$.each(data.data2,function(i,item){
		html += '<div class="row"><div class="col-sm-12"><div class="ibox float-e-margins"><div class="ibox-title">';
		html += '<h5>'+item.cy_v10+':   '+item.cy_v6+'</h5><div class="ibox-tools"></div></div><div class="ibox-content"><div class="row" id="hz_pic">';
		if(item.cy_pic_path==""||item.cy_pic_path==null||item.cy_pic_path==undefined||item.cy_pic_path=="-"){
			
		}else{
			html += '<div class="val-md-1"><div class="text-center"><img src="'+item.cy_pic_path+'" style="margin:0;vertical-align:baseline;width:200px;height:250px;"></div></div>';
		}
		html += '</div>';
		html += '<div class="row show-grid-hui show-grid-white"><div class="col-xs-6">性别</div><div class="val-xs-6"><code>'+item.cy_v7+'</code></div>';
		html += '<div class="col-xs-6">民族</div><div class="val-xs-6"><code>'+item.cy_v11+'</code></div></div>';
		html += '<div class="row show-grid-hui show-grid-white"><div class="col-xs-6">文化程度</div><div class="val-xs-6"><code>'+item.cy_v12+'</code></div>';
		html += '<div class="col-xs-6">是否在校</div><div class="val-xs-6"><code>'+item.cy_v13+'</code></div></div>';
		html += '<div class="row show-grid-hui show-grid-white"><div class="col-xs-6">健康状况</div><div class="val-xs-6"><code>'+item.cy_v14+'</code></div>';
		html += '<div class="col-xs-6">劳动力</div><div class="val-xs-6"><code>'+item.cy_v15+'</code></div>';
		html += '<div class="col-xs-6">务工情况</div><div class="val-xs-6"><code>'+item.cy_v16+'</code></div>';
		html += '<div class="col-xs-6">务工时间</div><div class="val-xs-6"><code>'+item.cy_v17+'</code></div></div>';
		html += '<div class="row show-grid-hui show-grid-white"><div class="col-xs-6">新农合</div><div class="val-xs-6"><code>'+item.cy_v18+'</code></div>';
		html += '<div class="col-xs-6">养老保险</div><div class="val-xs-6"><code>'+item.cy_v19+'</code></div>';
		/*html += '<div class="col-xs-6">政治面貌</div><div class="val-xs-6"><code>'+item.cy_v28+'</code></div>';*/
		html += '<div class="col-xs-6">现役军人</div><div class="val-xs-6"><code>'+item.cy_v32+'</code></div></div>';
		html += '</div></div></div></div>';
	});
	$("#jiatingchengyuan").html(html);
	
	
	//走访情况
    	if (data.data3.length>0) {
    		var html2_1 = '';
			var sjz_pie={};
			$.each(data.data3,function(i,item){
				if(item.da_type=="da_help_visit"){
					html2_1 += '<div class="col-sm-12"><div> 走访干部：<strong>'+item.v2+'</strong></div><div><p>走访时间：<code>'+item.v1+'</code></p></div>';
					if (typeof item.pie != "undefined") {
						$.each(item.pie,function(i,pitem){
							html2_1 += '<img src="'+pitem.pie_path+'" style="margin:0;vertical-align:baseline;width:280px;height:280px;">&nbsp;&nbsp;&nbsp;';
						});
					}
//					sjz_pie=item.pie.split(",");
//					if(sjz_pie.length>0){
//						for(var j=0;j<sjz_pie.length;j++){
//							html2_1 += '<img src="'+sjz_pie[j]+'" style="margin:0;vertical-align:baseline;width:280px;height:280px;">&nbsp;&nbsp;&nbsp;';
//	    				}
//					}
					html2_1 += '<div>走访情况：<p><code>'+item.v3+'</code></p></div></div><div class="col-sm-12">&nbsp;</div>';
				}
			});
			$("#zoufangqingkuang").html(html2_1);
    	}else{
    		$("#zoufang").hide();
    	}
}

function ajax_async_t(url,data,dataType,async){
	var rel;
	if(async==""||async==undefined){
		async=true;
	}else{
		async=false;
	}
	$.ajax({  		       
	    url: url,
	    type: "POST",
	    async:false,
	    dataType: dataType,
	    data: data,
	    success: function (ret) {
	    	rel = ret;
	    },
	    error: function () { 
	    	toastr["error"]("error", "服务器异常");
	    }  
	});
	return rel;
}
function GetRequest() { //截取URL的方法
	   var url = location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	    	  theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]); 
	      }
	   }
	   return theRequest;
	}