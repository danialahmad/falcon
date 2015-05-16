$(function() {
	
	$("#resetform").submit(function(){
		var url = "reset-password";
	    $.ajax({
	           type: "POST",
	           url: url,
	           data: $("#resetform").serialize()
	         }).done(function(data){
	        	 if(data == 'valid'){
	        		 console.log('success!!');
	        		 window.location.href = '../index';
	        	 }else {
	        		 $('#errorMsg').html(data);
	        		 $("#error").css('visibility','visible');
	        	 }
	         }).fail(function(){
	        	 $('#errorMsg').html('Sorry, there was a problem trying to reset your password. Please contact admin');
        		 $("#error").css('visibility','visible');
	         });

	    return false;
	});
});