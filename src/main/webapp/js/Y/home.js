$(document).ready(function(){
	
	$('#y_y_1').click(function(){
		$('#iframe0').attr('src','y1.html');
		$('#y_y_1').addClass('zidingyiB');
		$('#y_y_2').removeClass('zidingyiA');
		$('#y_y_3').removeClass('zidingyiA');
		$('#y_y_4').removeClass('zidingyiA');
    });
	
	$('#y_y_2').click(function(){
		$('#iframe0').attr('src','y2.html?xzqh=内蒙古自治区&level=1&pkid=1');
		$('#y_y_2').addClass('zidingyiA');
		$('#y_y_1').removeClass('zidingyiB');
		$('#y_y_3').removeClass('zidingyiA');
		$('#y_y_4').removeClass('zidingyiA');
    });
	
	$('#y_y_3').click(function(){
		$('#iframe0').attr('src','y3.html');
		$('#y_y_3').addClass('zidingyiA');
		$('#y_y_1').removeClass('zidingyiB');
		$('#y_y_2').removeClass('zidingyiA');
		$('#y_y_4').removeClass('zidingyiA');
    });
	
	$('#y_y_4').click(function(){
		$('#iframe0').attr('src','y4.html');
		$('#y_y_4').addClass('zidingyiA');
		$('#y_y_1').removeClass('zidingyiB');
		$('#y_y_2').removeClass('zidingyiA');
		$('#y_y_3').removeClass('zidingyiA');
    });
	
});