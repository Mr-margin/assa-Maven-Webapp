//公用方法，定义ajax方法，提示框等内容

toastr.options = {//弹出提示框
	"closeButton": true,
	"debug": false,
	"progressBar": true,
	"positionClass": "toast-top-right",
	"onclick": null,
	"showDuration": "400",
	"hideDuration": "1000",
	"timeOut": "6000",
	"extendedTimeOut": "1000",
	"showEasing": "swing",
	"hideEasing": "linear",
	"showMethod": "fadeIn",
	"hideMethod": "fadeOut"
}

/**
 * 传参
 * @author chendong
 * @date 2016年8月3日
 * @param url
 * @param data
 * @returns
 */
function ajax_async_t(url,data,dataType,async){
	var rel;
	$.ajax({
	    url: url,
	    type: "POST",
	    async:false,
	    dataType: dataType,
	    data: data,
	    success: function (ret) {
	    	if(ret.length==0){
	    		toastr["warning"]("", "查询数据为空");
	    	}else{
	    		rel = ret;
	    	}
	    },
	    error: function (ret) {
	    	toastr["error"]("", "服务器异常或未查询到数据"+url);
	    }
	});
	return rel;
}
//同步方法
function ajax_async_f(url,data,dataType){
	var rel;
	$.ajax({  		       
	    url: url,
	    type: "POST",
	    async:true,
	    dataType: dataType,
	    data: data,
	    success: function (ret) {
	    	if(ret.length==0){
	    		toastr["warning"]("", "查询数据为空");
	    	}else{
	    		rel = ret;
	    	}
	    },
	    error: function (ret) { 
	    	toastr["error"]("", "服务器异常或未查询到数据"+url);
	    }  
	});
	return rel;
}

//获取单位行政区划树，默认从本地获取，没有读取浏览器
function get_sys_company(){
	var company_data = JSON.parse(window.localStorage.getItem("company_data"));
	if(company_data){
		return eval("("+company_data+")");
	}else{
		var data = ajax_async_t(GISTONE.Loader.basePath+"cache_1.do", {}, "text");
		window.localStorage.setItem("company_data",JSON.stringify(data));
		return eval("("+data+")");
	}
}

//获取登录用户信息，默认本地获取
function get_sys_user(){
	var user = JSON.parse(window.sessionStorage.getItem("user"));
	if(user){
		return eval("("+user+")");
	}else{
		var data = ajax_async_t(GISTONE.Loader.basePath+"getLogin_massage.do",{},"text");
		if (data == "weidenglu") {//如果未登录
			window.sessionStorage.clear();
			return null;
		}else{
			window.sessionStorage.setItem("user",JSON.stringify(data));
			return eval("("+data+")");
		}
	}
}

//js精确计算除法
function floatDiv(arg1,arg2){     
    var t1=0,t2=0,r1,r2;     
    try{t1=arg1.toString().split(".")[1].length}catch(e){}     
    try{t2=arg2.toString().split(".")[1].length}catch(e){}     
      
    r1=Number(arg1.toString().replace(".",""));  
 
    r2=Number(arg2.toString().replace(".",""));     
    return (r1/r2)*Math.pow(10,t2-t1);     
}  

//将form中的值转换为键值对
//如：{Name:'摘取天上星',position:'IT技术'}
//ps:注意将同名的放在一个数组里
function getFormJson(form) {
	var o = {};
	var a = $(form).serializeArray();
	$.each(a, function() {
	    if (o[this.name] !== undefined) {
	        if (!o[this.name].push) {
	            o[this.name] = [o[this.name]];
	        }
	        o[this.name].push(this.value || '');
	    } else {
	        o[this.name] = this.value || '';
	    }
	});
	return o;
}
var obj;//获取url的对象
//获取查询条件
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