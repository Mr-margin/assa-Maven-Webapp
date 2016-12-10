$(function() {
	switch (jsondata.Login_map.SYS_COM_CODE) {
	case "150000000000"://内蒙古
		$('#pkrk').html("802068");
		$('#pkh').html("357581");
		$('#bfr').html("152072");
		break;
	case "150100000000"://呼和浩特市
		$('#pkrk').html("25359");
		$('#pkh').html("12236");
		$('#bfr').html("25359");
		break;
	case "150200000000"://包头市
		$('#pkrk').html("13527");
		$('#pkh').html("6572");
		$('#bfr').html("4470");
		break;
	case "150300000000"://乌海市
		$('#pkrk').html("1517");
		$('#pkh').html("697");
		$('#bfr').html("606");
		break;
	case "150400000000"://赤峰市
		$('#pkrk').html("246296");
		$('#pkh').html("112382");
		$('#bfr').html("39080");
		break;
	case "150500000000"://通辽市
		$('#pkrk').html("135478");
		$('#pkh').html("54099");
		$('#bfr').html("5411");
		break;
	case "150600000000"://鄂尔多斯市
		$('#pkrk').html("12933");
		$('#pkh').html("5194");
		$('#bfr').html("4508");
		break;
	case "150700000000"://呼伦贝尔市
		$('#pkrk').html("61677");
		$('#pkh').html("25666");
		$('#bfr').html("14575");
		break;
	case "150800000000"://巴彦淖尔市
		$('#pkrk').html("37028");
		$('#pkh').html("19409");
		$('#bfr').html("8826");
		break;
	case "150900000000"://乌兰察布市
		$('#pkrk').html("136621");
		$('#pkh').html("61104");
		$('#bfr').html("25345");
		break;
	case "152200000000"://兴安盟
		$('#pkrk').html("105173");
		$('#pkh').html("48309");
		$('#bfr').html("19234");
		break;
	case "152500000000"://锡林郭勒盟
		$('#pkrk').html("20594");
		$('#pkh').html("9156");
		$('#bfr').html("3510");
		break;
	case "152900000000"://阿拉善盟
		$('#pkrk').html("5865");
		$('#pkh').html("2757");
		$('#bfr').html("1148");
		break;
	default:
		var data=JSON.parse(ajax_async_t("/assa/getBfdx_home.do", {dqname:jsondata.Login_map.COM_VS,dqcode:jsondata.Login_map.SYS_COM_CODE}));
		$.each(data, function(i,item) {
			$('#pkrk').html(item.pkr);
			$('#pkh').html(item.pkh);
			$('#bfr').html(item.bfr);
		})
		break;
	}
	
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