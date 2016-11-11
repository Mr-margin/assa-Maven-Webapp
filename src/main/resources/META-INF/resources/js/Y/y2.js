$(function () {
	var gohome = '<div class="gohome"><a class="animated bounceInUp" id="xzqh_xuanze" title="选择行政区划"><i class="fa fa-map-marker"></i></a></div>';
	//$("body").append(gohome);
	
//	$('#y_y_2', parent.document).addClass('zidingyiA');
//	$('#y_y_1', parent.document).removeClass('zidingyiA');
//	$('#y_y_3', parent.document).removeClass('zidingyiA');
//	$('#y_y_4', parent.document).removeClass('zidingyiA');
	
//	$('#treeview').treeview({
//		color: "#428bca",
//		multiSelect: false,
//		highlightSelected: true,
//		data: show_SYS_COMPANY(),
//		onNodeSelected: function(event, node) {
//			clickpbl_tree(node);
//		}
//	});
	$("#fenge").click(function(e) {
		if($("#chaxuntiaojian").is(":hidden")){
			$("#fenge").html("【隐藏搜索区域】");
//			$("#quyu1").css("height", "220px");
//			$("#quyu2").css("height", "calc(100% - 268px)");
		}else{
			$("#fenge").html("【展开搜索区域】");
//			$("#quyu1").css("height", "20px");
//			$("#quyu2").css("height", "calc(100% - 78px)");
		}
		$("#chaxuntiaojian").toggle(500);
	});
	
	//截取字符串
	var Request = new Object();
	Request = GetRequest();
	var tree_xzqh = Request['xzqh'];
	var tree_level = Request['level'];
	var tree_pkid = Request['pkid'];
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
	/*注册事件
	if(document.addEventListener){
	document.addEventListener('DOMMouseScroll',loadData,false);
	}//W3C
	window.onmousewheel=document.onmousewheel=loadData;//IE/Opera/Chrome
*/	/*本应该通过ajax从后台请求过来类似sqljson的数据然后，便利，进行填充，这里我们用sqlJson来模拟一下数据*/
	$(window).scroll(function(){
		if(!loading.data("on")) return;
		// 计算所有瀑布流块中距离顶部最大，进而在滚动条滚动时，来进行ajax请求，方法很多这里只列举最简单一种，最易理解一种
		var itemNum=$('#waterfull').find('.item').length;
		var itemArr=[];
		itemArr[0]=$('#waterfull').find('.item').eq(itemNum-1).offset().top+$('#waterfull').find('.item').eq(itemNum-1)[0].offsetHeight;
		itemArr[1]=$('#waterfull').find('.item').eq(itemNum-2).offset().top+$('#waterfull').find('.item').eq(itemNum-1)[0].offsetHeight;
		itemArr[2]=$('#waterfull').find('.item').eq(itemNum-3).offset().top+$('#waterfull').find('.item').eq(itemNum-1)[0].offsetHeight;
		var maxTop=Math.max.apply(null,itemArr);
		if(maxTop<$(window).height()+$(document).scrollTop()){
			//加载更多数据
			loading.data("on",false).fadeIn(800);
			loadData(1);
			number++;//分页数加1
			loading.data("on",true).fadeOut();
		};
	});
	//加载数据
	function loadData(flag){
		if(flag&&loading.text()=="只有这么多了！"){
			return false;
		}
		var sqlJson = ajax_async_t("../getWyApp_y2_1.do",{pageSize: size,pageNumber: number,xzqh:tree_xzqh,level:tree_level,pkid:tree_pkid},"json",true);
		
		if (typeof sqlJson.error == "undefined") {
			var xzqh_title_html = '<span style="font-family:黑体;font-size:200%;color:#000;" >'+tree_xzqh+'</span> &nbsp;&nbsp;&nbsp;当前帮扶日记共计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.data2+'</span> 篇&nbsp;&nbsp;其中&nbsp;&nbsp; 相关贫困户：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.data3+'</span> 户&nbsp;&nbsp;&nbsp; 贫困户帮扶覆盖率：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> %';
			xzqh_title_html += '<br><span style="font-family:黑体;font-size:200%;color:#fff;" >'+tree_xzqh+'</span> &nbsp;&nbsp;&nbsp;当日帮扶日记：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 篇&nbsp;&nbsp;&nbsp;&nbsp; 本周帮扶日记共计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 篇&nbsp;&nbsp;&nbsp; 本月帮扶日记累计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 篇';
//			var xzqh_title_html = '<span style="font-family:黑体;font-size:200%;color:#000;" >'+tree_xzqh+'</span> &nbsp;&nbsp;&nbsp;当前帮扶日记共计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.data2+'</span> 篇&nbsp;&nbsp;其中&nbsp;&nbsp; 相关贫困户：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.data3+'</span> 户&nbsp;&nbsp;&nbsp; 贫困户帮扶覆盖率：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+Math.round(sqlJson.data3 / sqlJson.data4 * 10000) / 100.00+'</span> %';
//			xzqh_title_html += '<br><span style="font-family:黑体;font-size:200%;color:#fff;" >'+tree_xzqh+'</span> &nbsp;&nbsp;&nbsp;当日帮扶日记：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.ri+'</span> 篇&nbsp;&nbsp;&nbsp;&nbsp; 本周帮扶日记共计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.zhou+'</span> 篇&nbsp;&nbsp;&nbsp; 本月帮扶日记累计：<span style="font-family:华文新魏;font-size:200%;color:#f8ac59;" >'+sqlJson.yue+'</span> 篇';
//			
			$("#xzqh_title").html(xzqh_title_html);
			var sqlJson = sqlJson.data1;
			(function(sqlJson){
				var html="";
				if(sqlJson){
					for(var i in sqlJson){
						html+="<li class='item'><div class=\"gallerys\"><a class='a-img' title='点击查看大图'><img style='width:265px;' class=\"gallery-pic\" src=\""+sqlJson[i].src+"\" onclick=\"$.openPhotoGallery(this)\" /></div></a>";   
						html+="<h5>"+sqlJson[i].title+"</h5><span class='sp2'>"+sqlJson[i].date+"</span>";
						html+="<p>"+sqlJson[i].intro+"</p><div class='qianm clearfloat'>";
						html+="<span class='sp2'><img src='../js/plugins/masonry/icon/addr.png'><a onclick=\"open_map('"+sqlJson[i].writer+"')\">"+sqlJson[i].writer+"</a></span></div></li>";
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
			toastr["warning"]("warning", sqlJson.error);
		}
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
	
	//行政区划单位记录
		
		show_SYS_COMPANY();
		
		//加载市级下拉框
		$("#v2").append("<option value='0'></option>");
		$.each(mycars.nodes,function(i,item){
			$("#v2").append("<option value='"+item.href+"'>"+item.text+"</option>");
		});
		
		//市级下拉框选择事件
		$("#v2").change(function(){
			level=2;
			$("#v3").empty();
			$("#v4").empty();
			$("#v5").empty();
			$("#v3").append("<option value='0'></option>");
			$.each(mycars.nodes,function(i,item){
				if(item.href==$("#v2").val()){
					$.each(item.nodes,function(j,one){
						$("#v3").append("<option value='"+one.href+"'>"+one.text+"</option>");
					});
				}
			});
		});
		
		//县级下拉框选择事件
		$("#v3").change(function(){
			level=3;
			$("#v4").empty();
			$("#v5").empty();
			$("#v4").append("<option value='0'></option>");
			$.each(mycars.nodes,function(i,item){
				if(item.href==$("#v2").val()){
					$.each(item.nodes,function(j,one){
						if(one.href==$("#v3").val()){
							$.each(one.nodes,function(j,san){
								$("#v4").append("<option value='"+san.href+"'>"+san.text+"</option>");
							});
						}
					});
				}
			});
		});
		
		//乡级下拉框选择事件
		$("#v4").change(function(){
			level=4;
			$("#v5").empty();
			$("#v5").append("<option value='0'></option>");
			$.each(mycars.nodes,function(i,item){
				if(item.href==$("#v2").val()){
					$.each(item.nodes,function(j,one){
						if(one.href==$("#v3").val()){
							$.each(one.nodes,function(j,san){
								if(san.href==$("#v4").val()){
									$.each(san.nodes,function(j,si){
										$("#v5").append("<option value='"+si.href+"'>"+si.text+"</option>");
									});
								}
							});
						}
					});
				}
			});
		});
		
		$("#v5").change(function(){
			level=5;//层级
		})
		//查询
		$("#cha_but").click(function (){
			
			if($("#v"+level+"").find("option:selected").val()=='0'){
				level = level-1;
			}
			window.location.href="y2.html?xzqh="+$("#v"+level+"").find("option:selected").text()+"&level="+ level +"&pkid="+ $("#v"+level+"").val();
			
			
//			if($("#v5").val()!='0' && $("#v5").val()!=null){
//				window.location.href="y2.html?xzqh="+$("#v"+level+"").find("option:selected").text()+"&level="+ level +"&pkid="+ $("#v"+level+"").val();
//			}else{
//				toastr["warning"]("", "至少选择到村级单位。");
//			}
			
		});
		
		//清空
		$("#qing_but").click(function(e) {
			$("#v2").get(0).selectedIndex=0;
			$("#v3").empty();
			$("#v4").empty();
			$("#v5").empty();
			$("#biaoge").hide();
			$("#guodu").show();
		});

		

		//获取行政区划树
		function show_SYS_COMPANY(){
			if(jsondata.company_tree){
				mycars = $.extend(true,{},jsondata.company_tree);
			}else{
				var user = JSON.parse(window.sessionStorage.getItem("user"));
				if(user){
					jsondata = eval("("+user+")");
					mycars = $.extend(true,{},jsondata.company_tree);
				}
			}
		}
});
var mycars = {};
var level=0;//层级
//弹窗显示地图
function open_map(title){
	//清除覆盖物
	map.clearOverlays(); 
	//根据地名查询经纬度开始
	if(title.indexOf("村")>0){
		title=title.split("村")[0]+"村";
	}
	$("#map_title").html("贫困户位置："+title);
	var keyword = title;
	var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    localSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        if(poi){//到村
        	map.centerAndZoom(poi.point, 13);
            var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map.addOverlay(marker);
            var content = ""+title+"<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
            var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
            marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        }else{//到乡
        	if(title.indexOf("乡")>0){
        		title=title.split("乡")[0]+"乡";
        	}else if(title.indexOf("镇")>0){
        		title=title.split("镇")[0]+"镇";
        	}
        	var Search = new BMap.LocalSearch(map);
    		Search.enableAutoViewport(); //允许自动调节窗体大小
        	$("#map_title").html("贫困户位置："+title);
        	var keyword = title;
        	Search.setSearchCompleteCallback(function (searchResult) {
        		var poi = searchResult.getPoi(0);
        		map.centerAndZoom(poi.point, 13);
                var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
                map.addOverlay(marker);
                var content = ""+title+"<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
                var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
                marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
                marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        	})
        	Search.search(keyword);
        }
    });
    localSearch.search(keyword);
  //根据地名查询经纬度结束
	// 将地址解析结果显示在地图上,并调整地图视野
	/*myGeo.getPoint(title, function(point){
		if (point) {
			map.centerAndZoom(point, 13);
			map.addOverlay(new BMap.Marker(point));
		}else{
			alert("您选择地址没有解析到结果!");
		}
	}, "");*/
	
	$("#map_Modal").modal();
}
function GetRequest() {
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


function clickpbl_tree(node){
//	chaxun.nm_qx = node.text; //获取区域名称
//	chaxun.pkid = node.href; //获取主键
//	chaxun.com_level = node.com_level; //获取层级
	window.location.href="y2.html?xzqh="+node.text+"&level="+node.com_level+"&pkid="+node.href;
}

function show_SYS_COMPANY(){//获取行政区划树
	var mycars=[];
	if(jsondata.company_tree){
		mycars[0]= $.extend(true,{},jsondata.company_tree);
	}else{
		var user = JSON.parse(window.sessionStorage.getItem("user"));
		if(user){
			jsondata = eval("("+user+")");
			mycars[0]= $.extend(true,{},jsondata.company_tree);
		}
	}
	//$.each(mycars[0].nodes,function(i,item){
		//$.each(item.nodes,function(j,one){
			//delete item.nodes;
		//})
	//});
	return mycars;
}

