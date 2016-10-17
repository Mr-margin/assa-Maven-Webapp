/**
 * 上传文件
 * @author chendong
 * @daate 2016年8月3日
 * @param url
 * @param agr1
 * @param agr2
 * @param arg3
 * @returns {___anonymous2980_3488}
 */
function ImportData(url,agr1,agr2,arg3){	
	var options  = {
        url:url,
        type:'post',
        success:function(data, jqForm, options){
        	
        	$(agr1).html("");//文件名清空
        	
            var jsondata = eval("("+data+")");
            if(jsondata.error == "0"){
            	 $(agr2).attr("src", jsondata.path);
            	 if(arg3==""){
            	 }else{
            		 $(arg3).val(jsondata.path);
            	 }
            	 
                toastr["success"]("success", jsondata.message);
            }else{
                toastr["error"]("error", jsondata.message);
            }
        }
    };
	return options;
}