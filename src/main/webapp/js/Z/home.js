$(document).ready(function(){
	
	$('#z_z_1').click(function(){
		$('#iframe0').attr('src','Z1.html');
		$('#z_z_1').addClass('zidingyiA');
		$('#z_z_2').removeClass('zidingyiA');
		$('#z_z_3').removeClass('zidingyiA');
		$('#z_z_4').removeClass('zidingyiA');
    });
	
	$('#z_z_2').click(function(){
		$('#iframe0').attr('src','Z2.html');
		$('#z_z_2').addClass('zidingyiA');
		$('#z_z_1').removeClass('zidingyiA');
		$('#z_z_3').removeClass('zidingyiA');
		$('#z_z_4').removeClass('zidingyiA');
    });
	
	$('#z_z_3').click(function(){
		$('#iframe0').attr('src','Z3.html');
		$('#z_z_3').addClass('zidingyiA');
		$('#z_z_1').removeClass('zidingyiA');
		$('#z_z_2').removeClass('zidingyiA');
		$('#z_z_4').removeClass('zidingyiA');
    });
	
	$('#z_z_4').click(function(){
		$('#iframe0').attr('src','Z4.html');
		$('#z_z_4').addClass('zidingyiA');
		$('#z_z_1').removeClass('zidingyiA');
		$('#z_z_2').removeClass('zidingyiA');
		$('#z_z_3').removeClass('zidingyiA');
    });
	
});