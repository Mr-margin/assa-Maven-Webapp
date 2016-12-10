$(document).ready(function(){
     //扶贫对象
	var yemian=''; //跳转的页面
	if(jsondata.Login_map.COM_VD=="V1"){
		yemian='y1.html';
	}else if(jsondata.Login_map.COM_VD=="V3"){
		yemian='y5.html';
	}else if(jsondata.Login_map.COM_VD=="V5"){
		yemian='y6.html';
	}	
	$('#iframe0').attr('src',yemian);
	$('#iframe0').attr('data-id',yemian);
	$('#y_y_1').click(function(){
		$('#iframe0').attr('src',yemian);
		$('#y_y_1').addClass('zidingyiA');
		$('#y_y_2').removeClass('zidingyiA');
		$('#y_y_3').removeClass('zidingyiA');
		$('#y_y_4').removeClass('zidingyiA');
    });
	
	$('#y_y_2').click(function(){
		$('#iframe0').attr('src','y2.html');
		$('#y_y_2').addClass('zidingyiA');
		$('#y_y_1').removeClass('zidingyiA');
		$('#y_y_3').removeClass('zidingyiA');
		$('#y_y_4').removeClass('zidingyiA');
    });
	
	$('#y_y_3').click(function(){
		$('#iframe0').attr('src','y3.html');
		$('#y_y_3').addClass('zidingyiA');
		$('#y_y_1').removeClass('zidingyiA');
		$('#y_y_2').removeClass('zidingyiA');
		$('#y_y_4').removeClass('zidingyiA');
    });
	
	$('#y_y_4').click(function(){
		$('#iframe0').attr('src','y4.html');
		$('#y_y_4').addClass('zidingyiA');
		$('#y_y_1').removeClass('zidingyiA');
		$('#y_y_2').removeClass('zidingyiA');
		$('#y_y_3').removeClass('zidingyiA');
    });
	
});