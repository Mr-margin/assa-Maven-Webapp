$(function () {
	$("#quyu2").css("height",$("#zhuquyu").height()-$("#quyu1").height()+"px");
	$("#quyu2").css("width",$("#zhuquyu").width()+20+"px");
	$("#fenge").click(function(e) {
		if($("#chaxuntiaojian").is(":hidden")){
			$("#fenge").html("【隐藏搜索区域】");
		}else{
			$("#fenge").html("【展开搜索区域】");
		}
		$("#chaxuntiaojian").toggle(500);
		setTimeout(function(){
			$("#quyu2").css("height",$("#zhuquyu").height()-$("#quyu1").height()+"px");
		},500);
	});
	//图片预览
	jQuery(function ($) {
		$('.artZoom').artZoom({
			path: './images',	// 设置artZoom图片文件夹路径
			preload: true,		// 设置是否提前缓存视野内的大图片
			blur: true,			// 设置加载大图是否有模糊变清晰的效果
			// 语言设置
			left: '左旋转',		// 左旋转按钮文字
			right: '右旋转',		// 右旋转按钮文字
			source: '看原图'		// 查看原图按钮文字
		});
	});
    
	/*瀑布流开始*/
	var container = $('.waterfull ul');
	var loading=$('#imloading');
	var size = "15";//每页显示的记录，分页用
	var number = 0;//默认第一页
	
	// 初始化loading状态
	loading.data("on",true);
	/*判断瀑布流最大布局宽度，最大为1280*/
	function tores(){
		var tmpWid=$(window).width();
		var column=Math.floor(tmpWid/315);
		tmpWid=column*315;
		$('.waterfull').width(tmpWid);
	}
	tores();
	$(window).resize(function(){
		tores();
		$("#quyu2").css("height",$("#zhuquyu").height()-$("#quyu1").height()+"px");
		$("#quyu2").css("width",$("#zhuquyu").width()+20+"px");
	});
	container.imagesLoaded(function(){
	  container.masonry({
	  	columnWidth: 315,
	    itemSelector : '.item',
	    isFitWidth: true,//是否根据浏览器窗口大小自动适应默认false
	    isAnimated: true,//是否采用jquery动画进行重拍版
	    isRTL:false,//设置布局的排列方式，即：定位砖块时，是从左向右排列还是从右向左排列。默认值为false，即从左向右
	    isResizable: true,//是否自动布局默认true
	    animationOptions: {
			duration: 800,
			easing: 'easeInOutBack',//如果你引用了jQeasing这里就可以添加对应的动态动画效果，如果没引用删除这行，默认是匀速变化
			queue: false//是否队列，从一点填充瀑布流
		}
	  });
	});
	var tur=0;
	$("#quyu2").scroll(function(){
		var $this =$(this),  
        viewH =$(this).height(),//可见高度  
        contentH =$(this).get(0).scrollHeight,//内容高度  
		feideyongzhege = $(this)[0].scrollTop;//滚动高度 
		
		if(feideyongzhege>(tur+20)){
			if((feideyongzhege + viewH) >= contentH){
				loading.data("on",false).fadeIn(800);
				loadData(1);
				number++;//分页数加1
				loading.data("on",true).fadeOut();
				tur = feideyongzhege;
			}
		}
	});
	
	var tree_xzqh = '';
	var tree_level = '';
	
	//加载市级下拉框
	if(jsondata.Login_map.COM_VD=="V1"){
		tree_xzqh = '150000000000';
		tree_level = '1';
	}else if(jsondata.Login_map.COM_VD=="V3"){
		$("#v2").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
		$("#v3").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:jsondata.Login_map.SYS_COM_CODE}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
		});
		tree_xzqh=jsondata.Login_map.SYS_COM_CODE;
		tree_level='2';
	}else if(jsondata.Login_map.COM_VD=="V5"){
		var numbers=jsondata.Login_map.SYS_COM_CODE.substring(0,4)+'00000000';
		var t = document.getElementById("v2"); 
	    for(i=0;i<t.length;i++){//给select赋值  
	        if(numbers == t[i].value){  
	        	 $("#v2").html("<option value='"+numbers+"'>"+t.options[i].text+"</option>");
	        }
	    }
	    $("#v3").html("<option value='"+jsondata.Login_map.SYS_COM_CODE+"'>"+jsondata.Login_map.COM_NAME+"</option>");
	    $("#v4").append("<option></option>");
		var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:$("#v3").find("option:selected").val()}, "text");
		var val = eval("("+data+")");
		$.each(val,function(i,item){
			$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
		});
		tree_xzqh=jsondata.Login_map.SYS_COM_CODE;
		tree_level='3';
	}
	
	
	
	
	
	//加载数据
	function loadData(flag){
		if(flag&&loading.text()=="只有这么多了！"){
			return false;
		}
		console.log({pageSize: size,pageNumber: number,xzqh:tree_xzqh,level:tree_level});
		var sqlJson = ajax_async_t("../getWyApp_y2_1.do",{pageSize: size,pageNumber: number,xzqh:tree_xzqh,level:tree_level},"json",true);
		
		if (typeof sqlJson.error == "undefined") {
			var xzqh_title_html = '&nbsp;&nbsp;&nbsp;当前帮扶日记共计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.data2+'</span> 篇&nbsp;&nbsp;其中&nbsp;&nbsp; 相关贫困户：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.data3+'</span> 户&nbsp;&nbsp;&nbsp; 贫困户帮扶覆盖率：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> %';
			xzqh_title_html += '<br>&nbsp;&nbsp;&nbsp;当日帮扶日记：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 篇&nbsp;&nbsp;&nbsp;&nbsp; 本周帮扶日记共计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 篇&nbsp;&nbsp;&nbsp; 本月帮扶日记累计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 篇';
			$("#xzqh_title").html(xzqh_title_html);
			var sqlJson = sqlJson.data1;
			(function(sqlJson){
				var html="";
				if(sqlJson){
					for(var i in sqlJson){
						html+="<li class='item'><div class=\"gallerys\"><a class='a-img' title='点击查看大图'><img style='width:265px;' class=\"gallery-pic\" src=\""+sqlJson[i].src+"\" onclick=\"$.openPhotoGallery(this)\" /></div></a>";   
						html+="<h5>干部:";
						if( sqlJson[i].title == null || sqlJson[i].title == undefined || sqlJson[i].title == 0){
							html+="&nbsp;&nbsp;&nbsp;&nbsp;";
						} else {
							html += ""+sqlJson[i].title+"";
						}
						if (sqlJson[i].phone == null || sqlJson[i].phone == undefined || sqlJson[i].phone == "" ) {
							html += "&nbsp;&nbsp;&nbsp;&nbsp;"
						} else {
							html += "("+sqlJson[i].phone+")&nbsp;&nbsp;&nbsp;&nbsp;"
						}
						html+="户主:"+sqlJson[i].house+"</h5>";
						if( sqlJson[i].date == null || sqlJson[i].date == undefined ) {
							html+="<span class='sp2'></span>";
						}else {
							html+="<span class='sp2'>"+sqlJson[i].date+"</span>";
						}
						
						html+="<p>"+sqlJson[i].intro+"</p><div class='qianm clearfloat'>";
						var dj = parseFloat(sqlJson[i].lng).toFixed(2);
						var bw = parseFloat(sqlJson[i].lat).toFixed(2);
						html+="<span class='sp2'><img src='../js/plugins/masonry/icon/addr.png'><a onclick=\"open_map('"+sqlJson[i].lng+"','"+sqlJson[i].lat+"','"+sqlJson[i].writer+"')\">东经:"+dj+"  北纬:"+bw+"</a></span></div></li>";
					}
					$(html).find('img').each(function(index){
						loadImage($(this).attr('src'));
					})
					var $newElems = $(html).css({ opacity: 0}).appendTo(container);
					$newElems.imagesLoaded(function(){
						$newElems.animate({ opacity: 1},800);
						container.masonry( 'appended', $newElems,true);
			        });
				}else{
					if(tree_xzqh||tree_level||name){
						container.masonry('destroy');
				    	$("#waterfull").children("ul").children("li").remove();
					}else{
						loading.text('只有这么多了！');
					}
					
				}
			})(sqlJson);
		}else{
			toastr["warning"]("", sqlJson.error);
		}
		$("#quyu2").css("height",$("#zhuquyu").height()-$("#quyu1").height()+"px");
	}
	
	function loadImage(url) {
	     var img = new Image(); 
	     //创建一个Image对象，实现图片的预下载
	      img.src = url;
	      if (img.complete) {
	         return img.src;
	      }
	      img.onload = function () {
	       	return img.src;
	      };
	 };
	 
	 loadData("");
	 number++;//分页数加1
	
	
		
		//市级下拉框选择事件
		$("#v2").change(function(){
			$("#v3").empty();
			$("#v4").empty();
			$("#v5").empty();
			if($("#v2").find("option:selected").text()==""){
				tree_level = "1";
				tree_xzqh = '150000000000';
			}else{
				tree_level = "2";
				tree_xzqh = $("#v2").find("option:selected").val();
				$("#v3").append("<option value='0'></option>");
				var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V5.do", {code:tree_xzqh}, "text");
				var val = eval("("+data+")");
				$.each(val,function(i,item){
					$("#v3").append("<option value='"+item.V6+"'>"+item.V5+"</option>");
				});
			}
		});
		
		//县级下拉框选择事件
		$("#v3").change(function(){
			$("#v4").empty();
			$("#v5").empty();
			if($("#v3").find("option:selected").text() == ''){
				tree_level = "2";
				tree_xzqh = $("#v2").find("option:selected").val();
			}else{
				tree_level = "3";
				tree_xzqh = $("#v3").find("option:selected").val();
				$("#v4").append("<option value='0'></option>");
				var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V7.do", {code:tree_xzqh}, "text");
				var val = eval("("+data+")");
				$.each(val,function(i,item){
					$("#v4").append("<option value='"+item.V8+"'>"+item.V7+"</option>");
				});
			}
		});
		
		//乡级下拉框选择事件
		$("#v4").change(function(){
			$("#v5").empty();
			if($("#v4").find("option:selected").text() == ''){
				tree_level = "3";
				tree_xzqh = $("#v3").find("option:selected").val();
			}else{
				tree_level = "4";
				tree_xzqh = $("#v4").find("option:selected").val();
				$("#v5").append("<option value='0'></option>");
				var data = ajax_async_t(GISTONE.Loader.basePath+"getSYS_COM_V9.do", {code:tree_xzqh}, "text");
				var val = eval("("+data+")");
				$.each(val,function(i,item){
					$("#v5").append("<option value='"+item.V10+"'>"+item.V9+"</option>");
				});
			}
		});
		
		$("#v5").change(function(){
			if($("#v5").find("option:selected").text() == ''){
				tree_level = "4";
				tree_xzqh = $("#v4").find("option:selected").val();
			}else{
				tree_level = "5";//层级
				tree_xzqh = $("#v5").find("option:selected").val();
			}
		})
		//查询
		$("#cha_but").click(function (){
			
			container.masonry('destroy');
	    	$("#waterfull").children("ul").children("li").remove();
	    	$("#xzqh_title").html("");
	    	size = "15";//每页显示的记录，分页用
	    	number = 0;//默认第一页
	    	loadData("");
	   	 	number++;//分页数加1
		});
		
		//清空
		$("#qing_but").click(function(e) {
			if(jsondata.Login_map.COM_VD=="V1"){
				$("#v2").get(0).selectedIndex=0;
				$("#v3").empty();
				$("#v4").empty();
				$("#v5").empty();
			}else if(jsondata.Login_map.COM_VD=="V3"){
				$("#v3").get(0).selectedIndex=0;
				$("#v4").empty();
				$("#v5").empty();
			}else if(jsondata.Login_map.COM_VD=="V5"){
				$("#v4").get(0).selectedIndex=0;
				$("#v5").empty();
				
			}
			var display =$('#diyihang').css('display');
			if(display == 'none'){
				$("#biaoge").hide();
				$("#diyihang").toggle(500);
				$("#disanhang").toggle(500);
				$("#disihang").toggle(500);
			}
		});
});

//弹窗显示地图
function open_map(lng,lat,title){
	//清除覆盖物
	$("#map_Modal").modal();
	map.clearOverlays(); 
	$("#map_title").html("走访地点："+"东经:"+lng+"  北纬:"+lat+"");
	
	setTimeout(function(){
		var point= new BMap.Point(lng, lat);
		map.centerAndZoom(new BMap.Point(lng, lat), 13);
		var marker = new BMap.Marker(point);
		map.addOverlay(marker);
		var content = ""+title+"<br/><br/>经度：" + lng + "<br/>纬度：" + lat;
		var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
		marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	},500);
}