




// 数据初始化
function task_select() {
	var html = "";
	$.ajax({  		       
	    url: '../selectPhone.do',
	    type: "POST",
	    async:false,
	    dataType: 'json',
	    data: {phone:$("#phone").val()},
	    success: function (data) {
	    	$.each(data,function(i,item){
	    		html+='<tr><td>'+item.per_name+'</td><td>'+item.bf_sta_time+'</td><td>'+item.bf_end_time+'</td>';
	    		if(item.per_da_yx == "1" ) {
	    			html += '<td>有效</td>';
	    		}else {
	    			html += '<td>无效</td>';
	    		}
	    		
	    		html += '<td>'+item.da_name+'</td>';
	    		if( item.da_yx == "1" ) {
	    			html += '<td>有效</td>';
	    		}else {
	    			html += '<td>无效</td>';
	    		}
	    		if(item.sc == "1") {
	    			html += '<td>生存</td>'
	    		} else {
	    			html += '<td>死亡</td>'
	    		}
	    		html +='<td>'+item.year+'</td>';
	    		if(item.tp=="0" || item.tp=="3"){
	    			html += '<td>未脱贫</td>';
	    		}else {
	    			html += '<td>已脱贫</td>';
	    		}
	    		html += '</tr>';
	    	})
		    $("#show_da").html(html);
	    	toastr["success"]("","成功");

	    },
	    error: function (data) {
	    	console.log(data);
	    	toastr["warning"]("","失败或者没有");
	    }  
	})
	
}