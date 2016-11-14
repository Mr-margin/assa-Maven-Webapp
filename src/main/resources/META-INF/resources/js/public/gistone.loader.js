var ver = "v=2.0.2";
var GISTONE = GISTONE || {};
GISTONE.Loader = {
		//tag里没有依赖关系
		//[tagName, requireJavascripts, requireCSSs]
		_tags : [
		    ["jquery","js/plugins/jquery/","jquery.min.js?v=2.1.4","linshi.css"],
		    ["jquery.form","js/plugins/jquery/","jquery.form.js"],
		    ["bootstrap","js/plugins/bootstrap/","bootstrap.min.js?v=3.3.5,content.min.js?v=1.0.0","bootstrap.min.css?v=3.3.5,font-awesome.min.css?v=4.4.0,animate.min.css,style.min.css?v=4.0.0,style.css"],//bootstrap基础
		    ["slimscroll","js/plugins/slimscroll/","jquery.slimscroll.min.js"],//滚动条细化
		    ["bootstrap-table","js/plugins/bootstrap-table/","bootstrap-table.min.js,bootstrap-table-mobile.min.js,bootstrap-table-zh-CN.min.js","bootstrap-table.min.css"],//表格
		    ["validate","js/plugins/validate/","jquery.validate.min.js,messages_zh.min.js"],//表单验证
		    ["toastr","js/plugins/toastr/","toastr.min.js","toastr.min.css"],//消息框
		    ["sweetalert","js/plugins/sweetalert/","sweetalert.min.js","sweetalert.css"],//对话框
		    ["iCheck","js/plugins/iCheck/","icheck.min.js","custom.css"],//单选复选框
		    ["blueimp","js/plugins/blueimp/","jquery.blueimp-gallery.min.js","blueimp-gallery.min.css"],//Blueimp相册
		    ["peity","js/plugins/peity/","jquery.peity.min.js"],//jQuery简单图表
		    ["flot","js/plugins/flot/","jquery.flot.js,jquery.flot.tooltip.min.js,jquery.flot.resize.js"],//Flot图表
		    ["echarts","js/plugins/echarts/","echarts.min.3.1.2.js,macarons.js,shine.js,roma.js,vintage.js,infographic.js"],//echarts
		    ["suggest","js/plugins/suggest/","bootstrap-suggest.min.js"],//按钮式下拉菜单组件的搜索建议插件
		    ["webuploader","js/plugins/webuploader/","webuploader.min.js","webuploader.css,webuploader-demo.min.css"],//百度Web Uploader文件上传工具
		    ["datapicker","js/plugins/datapicker/","bootstrap-datepicker.js","datepicker3.css"],//简单好用的日期选择器
		    ["prettyfile","js/plugins/prettyfile/","bootstrap-prettyfile.js"],//文件上传
		    ["treeview","js/plugins/treeview/","bootstrap-treeview.js","bootstrap-treeview.css"],//树形结构
		    ["masonry","js/plugins/masonry/","jQueryColor.js,jquery.masonry.min.js,jQeasing.js","base.css,index.css"],//瀑布流
		    ["photo","js/plugins/photo/","jquery.photo.gallery.js,jquery.artZoom.js","jquery.artZoom.css"],//照片带旋转查看
		    ["openandclose","js/plugins/openandclose/","","style.css,openandclose.css"],//开关按钮样式
		    ["codemirror","js/plugins/codemirror/","codemirror.js,mode/javascript/javascript.js","codemirror.css,ambiance.css"],//在线代码编译器
		    ["shaixuan","js/plugins/select/","shaixuan.js","list.css"],//第三方插件，类似京东类型的查询选择框
		    ["select-tree","js/plugins/select-tree/","select-tree.js,dtreeck.js","dtreeck.css"],//下拉框选择树形结构
		    ["fullPage","js/plugins/fullPage/","jquery.fullPage.js","jquery.fullPage.css,style.css"],//第三方插件，滚屏组件
		    ["labelauty","js/plugins/labelauty/","jquery-labelauty.js","jquery-labelauty.css"],
		    ["prefixfree","js/plugins/prefixfree/","prefixfree.min.js"],
		    
		    ["hplus","js/plugins/hplus/","hplus.min.js?v=4.0.0,contabs.min.js",""],//H++默认结构,将每一页显示在框架内而不是另起一页
		    ["metisMenu","js/plugins/metisMenu/","jquery.metisMenu.js",""],//菜单展开效果
		    ["layer","js/plugins/layer/","layer.min.js",""],//层，H++默认结构
		    
		    ["metadata_st","js/systemMag/","metadata_st.do.js,metadata_st.js"],
		    ["metadata_up","js/systemMag/","metadata_up.do.js,metadata_up.js"],
		    ["datatable","js/systemMag/","datatable.do.js,datatable.js"],
		    ["generate_view","js/systemMag/","generate_view.do.js,generate_view.js"],
		    
		    ["citypicker","js/plugins/citypicker/","city-picker.data.js,city-picker.js","city-picker.css,main.css"],
		    ["an_index","js/android/","an_index.js","an_login.min.css"],
		    ["an_poor","js/android/","an_poor.js"],
		    
		    //["public","js/public/","tanchu.js,Interface.js,Method.js,login.js,select.js"],
		    
		    ["public","js/public/","p0.js,p1.js,p2.js"],
		    
		    ["table","js/public/","table.js"],//后台表格通用
		    ["xialakuang","js/public/","select.js"],//后台下拉框选择条件通用
		    
		    ["currency","js/public/","currency.js"],
		    
		    ["index","js/index/","index.js","style.css,animation.css"],//登录界面
		    ["hd-main","js/index/","","hd-main.css"],//页面头部
		    
//		    ["1-1","js/H1-1/","Img_load.js,H1.js,H1-1.js,H1-2.js,H1-3.js,H1-4.js,H1-5.js,H1-6.js,H1-7.js,import.js","H1-2css.css"],
//		    ["1-2","js/H1-2/","H1-2.js"],
//		    ["4","js/H4/","H4.js"],
//		    ["5-4","js/H5-4/","H5-4.js"],
//		    ["5-5","js/H5-5/","H5-5.js"],
//		    ["5-6","js/H5-6/","H5-6.js"],
//		    ["5-7","js/H5-7/","H5-7.js"],
//		    ["5-9","js/H5-9/","H5-9.js"],
		    
		    ["WtApp_home","js/T/","home.js"],
		    ["t1","js/T/","t1.js"],
		    ["t2","js/T/","t2.js"],
		    ["t3","js/T/","t3.js","css.css"],
		    
		    ["WxApp_home","js/Z/","home.js"],
		    ["z1","js/Z/","z1.js"],
		    ["z2","js/Z/","z2.js"],
		    ["z3","js/Z/","z3.js","css.css"],
		    ["z4","js/Z/","z4.js"],
		    
		    ["WyApp_home","js/Y/","home.js"],
		    ["y1","js/Y/","y1.js"],
		    ["y2","js/Y/","y2.js,bMap.js"],
		    ["y3","js/Y/","y3.js","css.css"],
		    ["y4","js/Y/","y4.js,modernizr.custom.js","component.css,default.css"],
		    
		    ["anuser","js/anuser/","an.js"],
		    ["w4","js/W4/","w4.js"],
		    ["anuserCSS","js/plugins/bootstrap/","","style.min.css"],//不知道什么css
		    
		    ["f_1_2_1","WxApp/file/","f_1_2_1.js"],
		    ["f_1_2_2","WxApp/file/","f_1_2_2.js"],
		    ["f_1_2_3","WxApp/file/","f_1_2_3.js"],
		    ["f_1_2_4","WxApp/file/","f_1_2_4.js"],
		    ["f_1_2_5","WxApp/file/","f_1_2_5.js"],
		    ["f_1_2_6","WxApp/file/","f_1_2_6.js"],
		    ["highcharts","WxApp/highcharts/","highcharts.js,highcharts-3d.js,exporting.js"],
		    ["f_1_2_7","WxApp/file/","f_1_2_7.js"],
		    ["f_1_2_8","WxApp/file/","f_1_2_8.js"],
		    ["f_1_2_9","WxApp/file/","f_1_2_9.js"],
		    ["f_1_2_10","WxApp/file/","f_1_2_10.js"],

		    ["f_1_2_15","WxApp/file/","f_1_2_15.js"],
		    ["f_1_2_14","WxApp/file/","f_1_2_14.js"],
		    ["f_1_2_13","WxApp/file/","f_1_2_13.js"],
		    ["f_1_2_12","WxApp/file/","f_1_2_12.js"],
		    ["f_1_2_11","WxApp/file/","f_1_2_11.js"]
		    
		],
		_js : {},
		_css : {},
		
		/**
		 * 加载脚本文件
		 * @param {String} inc	文件名
		 * @return String
		 */
		_IncludeScript : function(inc){
			var script='<script type="text/javascript" src="'+inc+'"></script>'; 
			return script;
		},
		
		/**
		 * 加载样式文件
		 * @param {String} inc	文件名
		 * @return String
		 */
		_IncludeStyle : function(inc){
			var style='<link type="text/css" rel="stylesheet" href="'+inc+'" />';
			return style;
		},
		
		/**
		 * 向文档节点里添加文件
		 * @param {String} str
		 */
		_add : function(str){
			//window.document.write(str);
			document.writeln(str);
			this._js = {};
			this._css = {};
		},
		
		/**
		 * 加载文件
		 * @example
		 *  <script type="text/javascript" src="js/public/gistone.loader.js"></script> 
		 *  <script type="text/javascript">
		 *   	//立刻执行加载
		 *   	GISTONE.Loader.load("");		//文件路径
		 *  </script>
		 * @param {Array<String>} tags
		 */
		loadScript : function(tags){
			//将tag中的脚本进行整理，去重
			for (var i = 0; i < tags.length; i++) {
				var t = null;
				for (var k = 0; k < this._tags.length; k++) {
					if (this._tags[k][0] == tags[i]){
						t = this._tags[k];
						break;
					}
				}
				if (t){
					if (t[2]){
						var tjs = t[2].split(",");
						for (var j = 0; j < tjs.length; j++) {
							this._js[tjs[j]] = t[1];
						}
					}
				}else{
					alert(tags[i]+" 未能找到该tag。");
				}
			}
			//构建HTML的js css标签
			var ret = [];
			for(key in this._js){
				if (!this.isWinRT) {
					ret.push(this._IncludeScript(this.basePath+this._js[key]+key));
				}else{
					var script = document.createElement("script");
					script.type = "text/javascript";
			        script.src = this.basePath+this._js[key]+key;
			        document.getElementsByTagName("HEAD")[0].appendChild(script);
				}
				
//				var script = document.createElement("script");
//				//script.type = "text/javascript";
//		        script.src = this.basePath+this._js[key]+key;
//		       // document.getElementsByTagName("HEAD")[0].appendChild(script);
//		        document.getElementsByTagName("body")[0].appendChild(script);
		       
			}
			if (!this.isWinRT) {
				this._add(ret.join(""));
			}
		},
		loadStyle : function(tags){
			//将tag中的脚本进行整理，去重
			for (var i = 0; i < tags.length; i++) {
				var t = null;
				for (var k = 0; k < this._tags.length; k++) {
					if (this._tags[k][0] == tags[i]){
						t = this._tags[k];
						break;
					}
				}
				if (t){
					if (t[3]){
						var tcss = t[3].split(",");
						for (var j = 0; j < tcss.length; j++) {
							this._css[tcss[j]] = t[1];
						}
					}
				}else{
					alert(tags[i]+" 未能找到该tag。");
				}
			}
			//构建HTML的js css标签
			var ret = [];
			for(key in this._css){
				if (!this.isWinRT) {
					ret.push(this._IncludeStyle(this.basePath+this._css[key]+key));
				}else{
					var link = document.createElement("link");
			        link.rel = "stylesheet";
			        link.href = this.basePath+this._css[key]+key;
			        document.getElementsByTagName("HEAD")[0].appendChild(link);
				}
			}
			if (!this.isWinRT) {
				this._add(ret.join(""));
			}
		}
		
	};
GISTONE.Loader.isWinRT = (typeof Windows === "undefined") ? false : true;
GISTONE.Loader.basePath = "http://"+window.location.host+"/assa/";

//document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法
//function subSomething() { 
////	0 － Uninitialized（未初始化）还没有调用send()方法 
////	1 － Loading（载入）已调用send()方法，正在发送请求 
////	2 － Loaded（载入完成）send()方法执行完成，已经接收到全部响应内容 
////	3 － Interactive（交互）正在解析响应内容 
////	4 － Completed（完成）响应内容解析完成，可以在客户端调用了
//	if(document.readyState == "Completed"){
//		
//	}
//} 
