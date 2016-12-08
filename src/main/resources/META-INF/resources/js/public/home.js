$(function() {
	console.log(jsondata);
	var data=JSON.parse(ajax_async_t("/assa/getBfdx_home.do", {dqname:jsondata.Login_map.COM_VS,dqcode:jsondata.Login_map.SYS_COM_CODE}));
	$.each(data, function(i,item) {
		$('#pkrk').html(item.pkr);
		$('#pkh').html(item.pkh);
		$('#bfr').html(item.bfr);
	})
	$('#fpdx').click(function(){      //扶贫对象
		if(jsondata.Login_map.COM_VD=="V1"){
			window.location.href = 'WxApp/home.html';
		}else if(jsondata.Login_map.COM_VD=="V3"){
			window.location.href = 'WxApp/home.html';
		}else if(jsondata.Login_map.COM_VD=="V5"){
			window.location.href = 'WxApp/home.html';
		}	
	})
	$('#fpzt').click(function(){     //扶贫主体
		if(jsondata.Login_map.COM_VD=="V1"){
			window.location.href = 'WyApp/home.html';
		}else if(jsondata.Login_map.COM_VD=="V3"){
			window.location.href = 'WyApp/home.html';
		}else if(jsondata.Login_map.COM_VD=="V5"){
			window.location.href = 'WyApp/home.html';
		}	
	})
})