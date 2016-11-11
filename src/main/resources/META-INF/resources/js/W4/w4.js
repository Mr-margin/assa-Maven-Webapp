var app={};
var all_pkid;
$(document).ready(function() {
	//document.getElementById("H_li").setAttribute("class","active");//头部样式
	cx_html();//查询框
	$("#jiatingchengyuan .contact-box").each(function(){animationHover(this,"pulse")});
	getinfo_ready($('#cha_mz'));
	getInfo_ready_zhipinyuanyin($('#cha_zpyy'));
	if(jsondata){//session中有内容，用户登录状态
		$("#nm_qx").val(jsondata.company.COM_NAME); //获取区域名称
		$("#pkid").attr("value",jsondata.company.PKID); //获取主键
		$("#com_level").attr("value",jsondata.company.COM_LEVEL); //获取层级
		
		chaxun.nm_qx = $("#nm_qx").val(); //获取区域名称
		chaxun.pkid = $("#pkid").attr("value"); //获取主键
		chaxun.com_level = $("#com_level").attr("value"); //获取层级
		
		
		//点击输入框弹出 div
		$("#nm_qx").click(function (){
			if(temp=$("#treediv").is(":hidden")){
				$("#treediv").show();
			}else{
				$("#treediv").hide();
			}
		});
		//判断点击的点击的位置是否在窗体内
		$("#nm_qx,#treediv").click(function(e) {
			e?e.stopPropagation():event.cancelBubble = true;
			var   obj=$("#treediv")[0];
			$($("#treediv")).width($("#nm_qx").width()+12);
		});
		//点击div层以外的部分隐藏div
		$(document).click(function() {
			$("#treediv").hide();
		});
		if(jsondata.company_map.com_type=="单位"){
			var type = jsondata.company_map.com_type;
			var val = jsondata.company;
			var qixian;
			if(val.com_level == "1"){
				getinfo_xiqian($('#cha_qx'));
			}else if(val.com_level == "2"){
				$('#cha_qx').html('<option  value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
				getinfo_xiang(jsondata.company.xian,$('#cha_smx'));
				chaxun.cha_qx = jsondata.company.xian;
			}else if(val.com_level == "3"){
				$('#cha_qx').html('<option value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
				$("#cha_smx").html('<option value="'+jsondata.company.xiang+'">'+jsondata.company.xiang+'</option>');
				getinfo_cun(jsondata.company.xiang,$('#cha_gcc'));
				chaxun.cha_qx = jsondata.company.xian;
				chaxun.cha_smx = jsondata.company.xiang;
			}else if(val.com_level == "4"){
				$('#cha_qx').html('<option  value="'+jsondata.company.xian+'">'+jsondata.company.xian+'</option>');
				$("#cha_smx").html('<option value="'+jsondata.company.xiang+'">'+jsondata.company.xiang+'</option>');
				$("#cha_gcc").html('<option value="'+jsondata.company.cun+'">'+jsondata.company.cun+'</option>');
				chaxun.cha_qx = jsondata.company.xian;
				chaxun.cha_smx = jsondata.company.xiang;
				chaxun.cha_gcc = jsondata.company.cun;
			}
		}else if(jsondata.company_map.com_type=="管理员"){
			getinfo_xiqian($('#cha_qx'));
		}else{
			$("#chauxn_quanxian").hide();
		}
	}
});
var metTable_bxbxxb = $('#metTable_bxbxxb');
var chaxun = {};//存储表格查询参数
var number;
var tjian;
$(function () {
	cxw4_html("1");//查询框中的HTML内容
	//点击输入框弹出 div
	$("#nm_qx").click(function (){
		if(temp=$("#treediv").is(":hidden")){
			$("#treediv").show();
		}else{
			$("#treediv").hide();
		}
	});
	//判断点击的点击的位置是否在窗体内
	$("#nm_qx,#treediv").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
		var obj=$("#treediv")[0];
		$($("#treediv")).width($("#nm_qx").width()+12);
		$($("#treediv")).css('margin-left',"80px");
	});
	//查询w4表格内容
	$("#submit-btn").click(function (){
		var sbbz="";
		var pksx="";
		var zpyy="";
		//var renkou="";
		var banqian="";
		//var cha_v8_1="";
		var title="";
		var sum=1;
		$(".selectedInfor label,.selectedInfor span") .each(function(){
			var labelval= $(this)[0].innerText;
			if(sum%2==0){//偶数为值
				if(title=="识别标准"){
					if(sbbz.length>0){
						sbbz+=","+labelval;
					}else{
						sbbz+=labelval;
					}
				}else if(title=="贫困户属性"){
					if(pksx.length>0){
						pksx+=","+labelval;
					}else{
						pksx+=labelval;
					}
				}else if(title=="主要致贫原因"){
					if(zpyy.length>0){
						zpyy+=","+labelval;
					}else{
						zpyy+=labelval;
					}
				}/*else if(title=="贫困人口"){
					if(renkou.length>0){
						renkou+=","+labelval;
					}else{
						renkou+=labelval;
					}
				}*/else if(title=="脱贫标志"){
					if(banqian.length>0){
						banqian+=","+labelval;
					}else{
						banqian+=labelval;
					}
				}/*else if(title=="年龄范围"){
					if(cha_v8_1.length>0){
						cha_v8_1+=","+labelval;
					}else{
						cha_v8_1+=labelval;
					}
				}*/
			}else{//奇数为标题
				title=labelval;
			}
		     sum++;
		});
		//识别标准
		if(sbbz.length>0){
			chaxun.cha_sbbz=sbbz;
		}else{
			chaxun.cha_sbbz=null;
		}
		//贫困户属性
		if(pksx.length>0){
			chaxun.cha_pksx=pksx;
		}else{
			chaxun.cha_pksx=null;
		}
		//主要致贫原因
		if(zpyy.length>0){
			chaxun.cha_zpyy=zpyy;
		}else{
			chaxun.cha_zpyy=null;
		}
		//贫困人口
		//chaxun.cha_renkou=renkou;
		//脱贫标志
		if(banqian.length>0){
			chaxun.cha_banqian=banqian;
		}else{
			chaxun.cha_banqian=null;
		}
		//年龄范围
		//chaxun.cha_v8_1=cha_v8_1;
		
		metTable_bxbxxb.bootstrapTable('destroy');//销毁现有表格数据
		gachacun_initialization();
	});
	
	
	//基本情况页签
	$("#yeqian .active").click(function(){
		$('#tab-1 .blueimp-gallery').attr('id','blueimp-gallery');
		$('#tab-3 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-4 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-6 .blueimp-gallery').attr('id','blueimp-gallery-bak');
	});
	//帮扶人页签
	$("#yeqian .active2").click(function(){
		$('#tab-1 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-3 .blueimp-gallery').attr('id','blueimp-gallery');
		$('#tab-4 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-6 .blueimp-gallery').attr('id','blueimp-gallery-bak');
	});
	//帮扶措施页签
	$("#yeqian .active3").click(function(){
		$('#tab-1 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-3 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-4 .blueimp-gallery').attr('id','blueimp-gallery');
		$('#tab-6 .blueimp-gallery').attr('id','blueimp-gallery-bak');
	});
	//异地搬迁页签
	$("#yeqian .active5").click(function(){
		$('#tab-1 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-3 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-4 .blueimp-gallery').attr('id','blueimp-gallery-bak');
		$('#tab-6 .blueimp-gallery').attr('id','blueimp-gallery');
	});
	//查询按钮
    $('#cha_button').click(function () {
    	chaxunkuang("2");
    });
    
    //清空查询
    $('#close_cha_button').click(function () {
    	qingkong("2"); 
    });
	gachacun_initialization();
});

//点击树触发方法
function clickw4_tree(val){

	chaxun.nm_qx = val.text; //获取区域名称
	chaxun.pkid = val.nodeId; //获取主键
	chaxun.com_level = val.com_level; //获取层级
	/*$("#com_level").attr({"value":val.com_level});
	$("#pkid").attr({"value":val.href});*/
	$('#nm_qx').val(val.text);
	$("#treediv").hide(); 

}
//图片方法
function show_tupian(pkid){
	var data = ajax_async_t("tupian.do", {pkid : pkid} , "json");
	if(data.data1.length==0){
		toastr["warning"]("warning", "暂未生成二维码");
	}else{
		$('#tupian').attr("src",data.data1[0].pic_path);
		$('#myModal22').modal('show');
	}
}

//查看贫苦户的详细信息
function chakan_info(pkid){
	//先清空
	$("#bfzrr_table").html('');
	$('#show_hz_phone').text("");//电话
	$('#show_hz_bank').text("");//开户银行
	$('#show_hz_banknum').text("");//银行卡号
	$('#show_hz_pkgsx').text("");//贫困户属性
	$('#show_hz_sfjls').text("");//是否军烈属
	$('#show_hz_dsznh').text("");//是否独生子女户
	$('#show_hz_snh').text("");//是否双女户
	$('#show_zyzp').text("");//主要致贫原因
	$('#show_qtzp').text("");//其他致贫原因
	$('#show_hz_sbbz').text("");//识别标准
	$("#show_gzxsr").text("");//工资性收入
	$("#show_jhsy").text("");//计划生育金
	$("#show_stbc").text("");//生态补偿金
	$("#show_scjyx").text("");//生产经营性收入
	$("#show_dbj").text("");//低保金
	$("#show_qtzy").text("");//其他转移性收入
	$("#show_ccxsr").text("");//财产性收入
	$("#show_wbj").text("");//五保金
	$("#shoow_zyxsr").text("");//转移性收入
	$("#show_ylbx").text("");//养老保险金
	$("#show_scjy").text("");//生产经营性支出
	$('#show_gdmj').text("");//耕地面积
	$('#show_ggmj').text("");//有效灌溉面积
	$('#show_ldmj').text("");//林地面积
	$('#show_tghlmj').text("");//退耕还林面积
	$('#show_lgmj').text("");//林果面积
	$('#show_mcdmj').text("");//牧草地面积
	$('#show_smmj').text("");//水面面积
	$('#show_sfscyd').text("");//是否通生产用电
	$('#show_shyd').text("");//是否通生活用电
	$('#show_zgljl').text("");//与村主干路距离
	$('#show_rullx').text("");//入户路类型
	$('#show_zfmj').text("");//住房面积
	$('#show_yskn').text("");//饮水是否困难
	$('#show_ysaq').text("");//饮水是否安全
	$('#show_zyrl').text("");//主要燃料类型
	$('#show_zyhzs').text("");//是否加入农民专业合作社
	$('#show_wscs').text("");//有无卫生厕所
	$('#show_sfbqh').text("");//是否搬迁户
	$('#show_bqfs').text("");//搬迁方式
	$('#show_azfs').text("");//安置方式
	$('#shoow_azd').text("");//安置地
	$('#show_czkn').text("");//搬迁可能存在的困难
	$("#yeqian").show();
	$("#yeqian ul>li:eq(0) a").trigger("click");//跳转第一个页签
	$("#neirong_jiben").hide();
	$("#myModal5").modal();
	$("#exportExcel_all_dengdai").show();
	$("#tablew4").hide();
	
	savePoorMessage(pkid);
	//document.getElementById('yeqian').scrollIntoView();
	
}
//贫困户初始化
function gachacun_initialization(){
	biaoge("getTz_3.do","498");
	$("#show-content").css("min-height","0px");
}
//显示贫困户的台账详细信息
function savePoorMessage(pkid){
	all_pkid=pkid;
	var html="";
	 $.ajax({                 
	        url: "getTz_1.do",
	        type: "POST",
	        async:true,
	        dataType: "json",
	        data:{pkid:pkid,},
	        success: function (data) {
	        	//基本信息
	        	$('#show_hz_address3').text((data.data1[0].V3));
	        	$('#show_hz_address4').text((data.data1[0].V4));
	        	$('#show_hz_address5').text(data.data1[0].V5);
	        	
	        	$('#show_hz_phone').text(data.data1[0].V25);//电话
	        	$('#show_hz_bank').text(data.data1[0].V26);//开户银行
	        	$('#show_hz_banknum').text(data.data1[0].V27);//银行卡号
	        	$('#show_hz_pkgsx').text(data.data1[0].V22);//贫困户属性
	        	$('#show_hz_sfjls').text(data.data1[0].V29);//是否军烈属
	        	$('#show_hz_dsznh').text(data.data1[0].V30);//是否独生子女户
	        	$('#show_hz_snh').text(data.data1[0].V31);//是否双女户
	        	$('#show_zyzp').text(data.data1[0].V23);//主要致贫原因
	        	$('#show_qtzp').text(data.data1[0].V33);//其他致贫原因
	        	$('#show_hz_sbbz').text(data.data1[0].SYS_STANDARD);//识别标准
	        	
	        	//家庭成员
	        	var jtcy;
	        	
	        	jtcy+='<tr><td><code style="color: #23c6c8">1</code></td><td><code style="color: #23c6c8">'+data.data1[0].V6+'</code></td>';
	        	if(data.data1[0].V7==undefined||data.data1[0].V7==null){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V7+'</code></td>';
	        	}
	        	if(data.data1[0].V8==undefined||data.data1[0].V8==null){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V8+'</code></td>';
	        	}
	        	if(data.data1[0].V10==undefined||data.data1[0].V10==null){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V10+'</code></td>';
	        	}
	        	if(data.data1[0].V11==undefined||data.data1[0].V11==null){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V11+'</code></td>';
	        	}
//	        	jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].v11+'</code></td>';
	        	if(data.data1[0].V28==null||data.data1[0].V28==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V28+'</code></td>';
	        	}
	        	
	        	if(data.data1[0].V12==null||data.data1[0].V12==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V12+'</code></td>';
	        	}
	        	if(data.data1[0].V13==null||data.data1[0].V13==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V13+'</code></td>';
	        	}
	        	if(data.data1[0].V14==null||data.data1[0].V14==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V14+'</code></td>';
	        	}
	        	if(data.data1[0].V15==null||data.data1[0].V15==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V15+'</code></td>';
	        	}
	        	if(data.data1[0].V16==null||data.data1[0].V16==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V16+'</code></td>';
	        	}
	        	if(data.data1[0].V17==null||data.data1[0].V17==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V17+'</code></td>';
	        	}
	        	if(data.data1[0].V32==null||data.data1[0].V32==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V32+'</code></td>';
	        	}	
	        	if(data.data1[0].V19==null||data.data1[0].V19==undefined){
	        		jtcy+='<td><code style="color: #23c6c8"></code></td>';
	        	}else{
	        		jtcy+='<td><code style="color: #23c6c8">'+data.data1[0].V19+'</code></td>';
	        	}	
	        	jtcy+='</tr>';
	        	if(data.data2==""||data.data2==null||data.data2==undefined){
	        		
	        	}else{
	        		var v6;var v7;var v8;var v10;var v11;var v12;var v13;var v14;var v28;var v15;var v16;var v17;var v32;var v19;
	        		
	        		$.each(data.data2,function(i,item){
	        			if(item.V6==null||item.V6==undefined){
	        				v6="";
	        			}else{
	        				v6=item.V6;
	        			}
	        			if(item.V7==null||item.V7==undefined){
	        				v7="";
	        			}else{
	        				v7=item.V7;
	        			}
	        			if(item.V8==null||item.V8==undefined){
	        				v8="";
	        			}else{
	        				v8=item.V8;
	        			}
	        			if(item.V10==null||item.V10==undefined){
	        				v10="";
	        			}else{
	        				v10=item.V10;
	        			}
	        			if(item.V11==null||item.V11==undefined){
	        				v11="";
	        			}else{
	        				v11=item.V11;
	        			}
	        			if(item.V12==null||item.V12==undefined){
	        				v12="";
	        			}else{
	        				v12=item.V12;
	        			}
	        			if(item.V13==null||item.V13==undefined){
	        				v13="";
	        			}else{
	        				v13=item.V13;
	        			}
	        			if(item.V14==null||item.V14==undefined){
	        				v14="";
	        			}else{
	        				v14=item.V14;
	        			}
	        			if(item.V15==null||item.V15==undefined){
	        				v15="";
	        			}else{
	        				v15=item.V15;
	        			}
	        			if(item.V16==null||item.V16==undefined){
	        				v16="";
	        			}else{
	        				v16=item.V16;
	        			}
	        			if(item.V17==null||item.V17==undefined){
	        				v17="";
	        			}else{
	        				v17=item.V17;
	        			}
	        			if(item.V19==null||item.V19==undefined){
	        				v19="";
	        			}else{
	        				v19=item.V19;
	        			}
	        			if(item.V28==null||item.V28==undefined){
	        				v28="";
	        			}else{
	        				v28=item.V28;
	        			}
	        			if(item.V32==null||item.V32==undefined){
	        				v32="";
	        			}else{
	        				v32=item.V32;
	        			}
	            		jtcy+='<tr><td><code style="color: #23c6c8">'+(i+2)+'</code></td><td><code style="color: #23c6c8">'+v6+'</code></td><td><code style="color: #23c6c8">'+v7+'</code></td><td><code style="color: #23c6c8">'+v8+'</code></td><td><code style="color: #23c6c8">'
	        	    			+v10+'</code></td></code></td><td><code style="color: #23c6c8">'+v11+'</code></td>'+
	        	    			'<td><code style="color: #23c6c8">'+v28+'</code></td><td><code style="color: #23c6c8">'+v12+'</code></td><td><code style="color: #23c6c8">'+v13+'</code></td><td><code style="color: #23c6c8">'+v14+'</code></td><td><code style="color: #23c6c8">'
	            				+v15+'</code></td>'+
	            				'<td><code style="color: #23c6c8">'+v16+'</code></td><td><code style="color: #23c6c8">'+v17+'</code></td><td><code style="color: #23c6c8">'+v32+'</code></td><td><code style="color: #23c6c8">'+v19+'</code></td></tr>';
	            		
	            	});
	            	
	        	}
	        	$("#jtcy_table").html(jtcy);
	        	
	        	//收入情况
	        	var v28;var v30;
	        	if(data.data5[0].V28==""||data.data5[0].V28==null){
	        		v28=0;
	        	}else{
	        		v28=data.data5[0].V28;
	        	}
	        	if(data.data5[0].V30==""||data.data5[0].V30==null){
	        		v30=0;
	        	}else{
	        		v30=data.data5[0].V30;
	        	}
	        	$("#show_gzxsr").text(v28+v30);//工资性收入
	        	$("#show_jhsy").text(data.data5[0].v41);//计划生育金
	        	$("#show_stbc").text(data.data5[0].v12);//生态补偿金
	        	$("#show_scjyx").text(data.data5[0].v10);//生产经营性收入
	        	$("#show_dbj").text(data.data5[0].v16);//低保金
	        	$("#show_qtzy").text(data.data5[0].v20);//其他转移性收入
	        	var v24;var v26;
	        	if(data.data5[0].V24==""||data.data5[0].V24==null){
	        		v24=0;
	        	}else{
	        		v24=data.data5[0].V24;
	        	}
	        	if(data.data5[0].V26==""||data.data5[0].V26==null){
	        		v26=0;
	        	}else{
	        		v26=data.data5[0].V26;
	        	}
	        	$("#show_ccxsr").text(v24+v26);//财产性收入
	        	$("#show_wbj").text(data.data5[0].V43);//五保金
	        	$("#shoow_zyxsr").text(data.data5[0].V22);//转移性收入
	        	$("#show_ylbx").text(data.data5[0].V14);//养老保险金
	        	var v2;var v4;var v6;var v8;var v10;var v12;var v14;var v16;var v18;var v20;
	        	if(data.data8[0].V2==""||data.data8[0].V2==null){
	        		v2=0;
	        	}else{
	        		v2=data.data8[0].V2
	        	}
	        	if(data.data8[0].V4==""||data.data8[0].V4==null){
	        		v4=0;
	        	}else{
	        		v4=data.data8[0].V4;
	        	}
	        	if(data.data8[0].V6==""||data.data8[0].V6==null){
	        		v6=0;
	        	}else{
	        		v6=data.data8[0].V6;
	        	}
	        	if(data.data8[0].V8==""||data.data8[0].V8==null){
	        		v8=0;
	        	}else{
	        		v8=data.data8[0].V8;
	        	}
	        	if(data.data8[0].V10==""||data.data8[0].V10==null){
	        		v10=0;
	        	}else{
	        		v10=data.data8[0].V10;
	        	}
	        	if(data.data8[0].V12==""||data.data8[0].V12==null){
	        		v12=0;
	        	}else{
	        		v12=data.data8[0].V12;
	        	}
	        	if(data.data8[0].V14==""||data.data8[0].V14==null){
	        		v14=0;
	        	}else{
	        		v14=data.data8[0].V14;
	        	}
	        	if(data.data8[0].V16==""||data.data8[0].V16==null){
	        		v16=0;
	        	}else{
	        		v16=data.data8[0].V16;
	        	}
	        	if(data.data8[0].V18==""||data.data8[0].V18==null){
	        		v18=0;
	        	}else{
	        		v18=data.data8[0].V18;
	        	}
	        	$("#show_scjy").text((v2+v4+v6+v8+v10+v12+v14+v16+v18));//生产经营性支出
	        	
	        	
	        	//生产条件
	        	if(data.data3==""||data.data3==null||data.data3==undefined){
	        	}else{
	        		$('#show_gdmj').text(data.data3[0].V1);//耕地面积
	            	$('#show_ggmj').text(data.data3[0].V2);//有效灌溉面积
	            	$('#show_ldmj').text(data.data3[0].V3);//林地面积
	            	$('#show_tghlmj').text(data.data3[0].V4);//退耕还林面积
	            	$('#show_lgmj').text(data.data3[0].V13);//林果面积
	            	$('#show_mcdmj').text(data.data3[0].V5);//牧草地面积
	            	$('#show_smmj').text(data.data3[0].V14);//水面面积
	        	}
	        	//生活条件
	        	if(data.data4==""||data.data4==null||data.data4==undefined){
	        	}else{
	        		if(data.data4[0].V5==""||data.data4[0].V5==null||data.data4[0].V5=="-"||data.data4[0].V5==undefined){
	        			$('#show_sfscyd').text("否");//是否通生产用电
	        			$('#show_shyd').text("否");//是否通生活用电
	        		}else{
	        			$('#show_sfscyd').text("是");//是否通生产用电
	        			$('#show_shyd').text("是");//是否通生活用电
	        		}
	        		
	        		$('#show_zgljl').text(data.data4[0].V7);//与村主干路距离
	            	$('#show_rullx').text(data.data4[0].V6);//入户路类型
	            	$('#show_zfmj').text(data.data4[0].V1);//住房面积
	            	
	            	$('#show_yskn').text(data.data4[0].V8);//饮水是否困难
	            	$('#show_ysaq').text(data.data4[0].V9);//饮水是否安全
	            	$('#show_zyrl').text(data.data4[0].V10);//主要燃料类型
	            	$('#show_zyhzs').text(data.data4[0].V11);//是否加入农民专业合作社
	            	$('#show_wscs').text(data.data4[0].V12);//有无卫生厕所
	        	}
	        	
	        	//易地搬迁户需求
	        	if(data.data6==""||data.data6==null||data.data6==undefined){
	        	}else{
	        		$('#show_sfbqh').text(data.data6[0].VV3);//是否搬迁户
	        		$('#show_bqfs').text(data.data6[0].V1);//搬迁方式
	            	$('#show_azfs').text(data.data6[0].MOVE_TYPE);//安置方式
	            	$('#shoow_azd').text(data.data6[0].V2);//安置地
	            	$('#show_czkn').text(data.data6[0].V3);//搬迁可能存在的困难
	        	}
	        	//帮扶责任人
	        	var html1;
	        	var bcol_name="";var bv1 ="";var bv2="";var bv3="";var bcom_name="";var bv4="";var bv5=""; var bv6="";var bv7="";var btelephone="";
	        	$.each(data.data7,function(i,item){
	        			if(item.COL_NAME==undefined||item.COL_NAME==""||item.COL_NAME==null){
	        			}else{
	        				
	        				if(item.COL_NAME==null||item.COL_NAME==undefined){
	        					bcol_name="";
	        				}else{
	        					bcol_name=item.COL_NAME;
	        				}
	        				if(item.V1==null||item.V1==undefined){
	        					bv1="";
	        				}else{
	        					bv1=item.V1;
	        				}
	        				if(item.V2==null||item.V2==undefined){
	        					bv2="";
	        				}else{
	        					bv2=item.V2;
	        				}
	        				if(item.V3==null||item.V3==undefined){
	        					bv3="";
	        				}else{
	        					bv3=item.V3;
	        				}
	        				if(item.V4==null||item.V4==undefined){
	        					bv4="";
	        				}else{
	        					bv4=item.COL_NAME;///??????
	        				}
	        				if(item.V5==null||item.V5==undefined){
	        					bv5="";
	        				}else{
	        					bv5=item.V5;
	        				}
	        				if(item.V6==null||item.V6==undefined){
	        					bv6="";
	        				}else{
	        					bv6=item.V6;
	        				}
	        				if(item.V7==null||item.V7==undefined){
	        					bv7="";
	        				}else{
	        					bv7=item.V7;
	        				}
	        				if(item.COM_NAME==null||item.COM_NAME==undefined){
	        					bcom_name="";
	        				}else{
	        					bcom_name=item.COM_NAME;
	        				}
	        				if(item.TELEPHONE==null||item.TELEPHONE==undefined){
	        					btelephone="";
	        				}else{
	        					btelephone=item.TELEPHONE;
	        				}
	        				html1+='<tr><td><code style="color: #23c6c8">'+(i+1)+'</code></td><td><code style="color: #23c6c8">'+bcol_name+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv1+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv2+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv3+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bcom_name+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv4+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv5+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv6+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+bv7+'</code></td>'+
	        					'<td><code style="color: #23c6c8">'+btelephone+'</code></td>'+
	        					'</tr>';
	        			}
	        	});
	        	$("#bfzrr_table").html(html1);
	        	$("#neirong_jiben").show();
	        	$("#exportExcel_all_dengdai").hide();
	        	$("#tablew4").show();
	        },
	        error: function () { 
	            toastr["error"]("error", "服务器异常");
	        }  
	    });

	
	
	

}

//脱贫计划、帮扶措施、工作台账、贫困户收入监测表   
function showStanding(year,num){
	$("#show_tpv2_"+num).text("");
	$("#show_tpv3_"+num).text("");
	$("#show_tpv4_"+num).text("");
	$("#show_tpv5_"+num).text("");
	$("#show_tpv6_"+num).text("");
	$("#show_tpv7_"+num).text("");
	$("#show_tpv8_"+num).text("");
	$("#show_tpv9_"+num).text("");
	$("#show_tpv10_"+num).text("");
	$("#show_tpv11_"+num).text("");
	$("#show_tpv12_"+num).text("");
	$("#show_tpv13_"+num).text("");
	$("#show_tpv14_"+num).text("");
	$("#show_tpv15_"+num).text("");
	$("#show_tpv16_"+num).text("");
	$("#show_tpv17_"+num).text("");
	$("#show_tpv18_"+num).text("");
	$("#show_tpv19_"+num).text("");
	$("#bfcs_table_"+num).html("");
	$("#gztz_table_"+num).html("");
	if(all_pkid==""||all_pkid==null){
		return;
	}
	var data = ajax_async_t("getTz_2.do",{pkid:all_pkid,year:year},"json");

	//脱贫计划
	if(data.data1==""||data.data1==null){
		
	}else{
		$("#show_tpv2_"+num).text(data.data1[0].v2);
    	$("#show_tpv3_"+num).text(data.data1[0].v3);
    	$("#show_tpv4_"+num).text(data.data1[0].v4);
    	$("#show_tpv5_"+num).text(data.data1[0].v5);
    	$("#show_tpv6_"+num).text(data.data1[0].v6);
    	$("#show_tpv7_"+num).text(data.data1[0].v7);
    	$("#show_tpv8_"+num).text(data.data1[0].v8);
    	$("#show_tpv9_"+num).text(data.data1[0].v9);
    	$("#show_tpv10_"+num).text(data.data1[0].v10);
    	$("#show_tpv11_"+num).text(data.data1[0].v11);
    	$("#show_tpv12_"+num).text(data.data1[0].v12);
    	$("#show_tpv13_"+num).text(data.data1[0].v13);
    	$("#show_tpv14_"+num).text(data.data1[0].v14);
    	$("#show_tpv15_"+num).text(data.data1[0].v15);
    	$("#show_tpv16_"+num).text(data.data1[0].v16);
    	$("#show_tpv17_"+num).text(data.data1[0].v17);
    	$("#show_tpv18_"+num).text(data.data1[0].v18);
    	$("#show_tpv19_"+num).text(data.data1[0].v19);
	}
	//帮扶措施
	try {
		if(data.data2==""||data.data2==null){
    		
    	}else{
    		var hj=0;var hjy=0;var v4;var v5;var v6;var v5_1;
        	var html;
    		$.each(data.data2,function(i,item){
    			hj=i+1;
    			if(item.v4==null||item.v4==undefined){
    				v4="";
    			}else{
    				v4=item.v4;
    			}
    			if(item.v5==null||item.v5==undefined||item.v5==""){
    				v5="";
    				v5_1="0"
    			}else{
    				v5=item.v5;
    				v5_1=item.v5;
    			}
    			if(item.v6==null||item.v6==undefined){
    				v6="";
    			}else{
    				v6=item.v6;
    			}
    			if(isNaN(parseInt(v5_1))){
    			}else{
    				hjy+=parseInt(v5_1);
    			}
    			
    			html+='<tr><th><code style="color: #23c6c8">'+(i+1)+'</code></th><th><code style="color: #23c6c8">'+item.v1+'<code style="color: #23c6c8"></th><th><code style="color: #23c6c8">'+item.v2+'</code></th><th><code style="color: #23c6c8">'+item.v3+'</code></th>'+
    			'<th><code style="color: #23c6c8">'+v4+'</code></th><th><code style="color: #23c6c8">'+v5+'</code></th><th><code style="color: #23c6c8">'+v6+'</code></th></tr>'
    		});
    		 html+='<tr><th style="text-align:center;"  colspan="4">合计</th><th><code style="color: #23c6c8">'+hj+'项</code></th><th><code style="color: #23c6c8">'+hjy+'元</code></th><th></th></tr>';
	        	$("#bfcs_table_"+num).html(html);
    	}
		
	} catch (e) {
		
	}
	//工作台账
	if(data.data3==""||data.data3==null){
	}else{
		var html1;
		$.each(data.data3,function(i,item){
			html1+='<tr><th><code style="color: #23c6c8">'+item.v6+'</code></th><th><code style="color: #23c6c8">'+item.v1+'</code></th><th><code style="color: #23c6c8">'+item.v2+'</code></th><th><code style="color: #23c6c8">'+item.v3+
			'</code></th><th><code style="color: #23c6c8">'+item.v4+'</code></th></tr>';
		});
		$("#gztz_table_"+num).html(html1);
	}

}
