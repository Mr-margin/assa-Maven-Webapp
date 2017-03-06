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
function ajax_async_t(url,data,dataType,async,err){
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
	    	if(ret.length==0){
	    		if(!err){
	    			toastr["warning"]("", "查询数据为空");
	    		}
	    	}else{
	    		rel = ret;
	    	}
	    },
	    error: function (ret) { 
	    	toastr["error"]("", "服务器异常或未查询到数据");
	    }  
	});
	return rel;
}

//获取单位行政区划树，默认从本地获取，没有读取浏览器
function get_sys_company(){
	var company_data = JSON.parse(localStorage.getItem("company_data"));
	if(company_data){
		return company_data;
	}else{
		var data = ajax_async_t("cache_1.do", {}, "json");
		window.localStorage.setItem("company_data",JSON.stringify(data));
		return data;
	}
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

//刷新
//function shuaxin(form){
//	$(form).find("input").each(function(){
//		  var id = $(this).attr("id");
//		  $("#"+id).val("");
//	});
//	$(form).find("textarea").each(function(){
//		var id = $(this).attr("id");
//		$("#"+id).val("")
//	});
//}
