$(document).ready(function(){
	
	$('#t_t_1').click(function(){
		$('#iframe0').attr('src','T1.html');
		$('#t_t_1').addClass('zidingyiA');
		$('#t_t_2').removeClass('zidingyiA');
		$('#t_t_3').removeClass('zidingyiA');
	
    });
	
	$('#t_t_2').click(function(){
		$('#iframe0').attr('src','T2.html');
		$('#t_t_2').addClass('zidingyiA');
		$('#t_t_1').removeClass('zidingyiA');
		$('#t_t_3').removeClass('zidingyiA');
		$('#t_t_4').removeClass('zidingyiA');
    });
	
	$('#t_t_3').click(function(){
		$('#iframe0').attr('src','T3.html');
		$('#t_t_3').addClass('zidingyiA');
		$('#t_t_1').removeClass('zidingyiA');
		$('#t_t_2').removeClass('zidingyiA');
		$('#t_t_4').removeClass('zidingyiA');
    });
	
	
	
});