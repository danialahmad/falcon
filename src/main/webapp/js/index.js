$(function() {
	$("#signupbutton").click(function() {
		var location = $("#registration-link").attr("href");
		window.location = location;
	});
	$('#forgotpwd').click(function(){
		$("#resetpasswordappt-box").css("display","block");
		$("#bg").css("display","block");
	});
	
	$("#verifyIdentity").submit(function(){
		var url = "verify-identity";
	    $.ajax({
	           type: "POST",
	           url: url,
	           data: $("#verifyIdentity").serialize()
	         }).done(function(data){
	        	 if(data == 'success'){
	        		 $('#resetAlert').html('An Email has been sent to your email address for verification');
	        		 $('#resetAlert').css('visibility','visible');
	        		 $('#nric').val('');
	        	 }else {
	        		 $('#resetAlert').html('Sorry, invalid ic');
	        		 $("#resetAlert").css('visibility','visible');
	        	 }
	         }).fail(function(){
	        	 $('#resetAlert').html('Sorry, there was a problem trying to reset your password. Please contact admin');
	         });

	    return false;
	});
});


function closeDialog(){
	//document.getElementById('resetpasswordappt-box').style.display='none'; 
	//document.getElementById('bg').style.display='none'; 
	window.location = 'index';
}