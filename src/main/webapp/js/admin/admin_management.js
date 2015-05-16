$(function() {
	$("#staffform").validationEngine({
		ajaxFormValidation: true,
		onBeforeAjaxFormValidation: function(){
			$("#confirmreg-box-staff").css("display","block");
			$("#bg").css("display","block");
			$("#confirm-staff-name").html($('#fullname-staff').val());
			$("#confirm-staff-nric").html($('#identificationnum-staff').val());
			$("#confirm-staff-hptel").html($('#mobilenum-staff').val());
			$("#confirm-staff-email").html($('#email-staff').val());
			$("#confirm-staff-yes").click(function(){
				$('#staffform').validationEngine('detach');
				$('#staffform').submit();
				$("#confirmreg-box-staff").css("display","none");
				$("#bg").css("display","none");
				return true;
			});
			return false;
		},
        onAjaxFormComplete: function(status,form) {
        	console.log('ALOOO!');
            if (status === true) {
                console.log('ok!');
            }else{
            	console.log('Tak ok!');
            }
        }
    });
	$('#patronform').validationEngine({
		ajaxFormValidationURL: "../validatenricphone",
		ajaxFormValidation: true,
		onBeforeAjaxFormValidation: function(){
//			$("#confirmreg-box-patron").css("display","block");
//			$("#bg").css("display","block");
//			$("#confirm-patron-name").html($('#fullname-patron').val());
//			$("#confirm-patron-nric").html($('#identificationnum-patron').val());
//			$("#confirm-patron-hptel").html($('#mobilenum-patron').val());
//			$("#confirm-patron-email").html($('#email-patron').val());
//			$("#confirm-patron-yes").click(function(){
//				$('#patronform').validationEngine('detach');
//				$('#patronform').submit();
//				$("#confirmreg-box-patron").css("display","none");
//				$("#bg").css("display","none");
//				return true;
//			});
//			return false;
			$("#patron-error").html("");
			console.log("validating first ajax");
		},
        onAjaxFormComplete: function(status, form, json, options) {
        	console.log('status:' + status);
        	console.log('form:' + form);
        	console.log('json:' + json);
        	var valid = true;
        	for(var i = 0; i < json.length; i++){
        		var content = json[i];
        		if(content[1] == false){
        			$("#patron-error").html("There was a duplicate user with the same nric / phone details"); 
        			valid = false;
        			return false;
        		}
        	}
        	if(valid){
        		$("#confirmreg-box-patron").css("display","block");
				$("#bg").css("display","block");
				$("#confirm-patron-name").html($('#fullname-patron').val());
				$("#confirm-patron-nric").html($('#identificationnum-patron').val());
				$("#confirm-patron-hptel").html($('#mobilenum-patron').val());
				$("#confirm-patron-email").html($('#email-patron').val());
				$("#confirm-patron-yes").click(function(){
					$('#patronform').validationEngine('detach');
					$('#patronform').submit();
					$("#confirmreg-box-patron").css("display","none");
					$("#bg").css("display","none");
					return true;
				});
        	}
        }
//		onValidationComplete:function(form, status){
//			 alert("The form status is: " +status+", it will never submit")
//			if(status === true){
//				alert('im here!');
//				$("#confirmreg-box-patron").css("display","block");
//				$("#bg").css("display","block");
//				$("#confirm-patron-name").html($('#fullname-patron').val());
//				$("#confirm-patron-nric").html($('#identificationnum-patron').val());
//				$("#confirm-patron-hptel").html($('#mobilenum-patron').val());
//				$("#confirm-patron-email").html($('#email-patron').val());
//				$("#confirm-patron-yes").click(function(){
//					$('#patronform').validationEngine('detach');
//					$('#patronform').submit();
//					$("#confirmreg-box-patron").css("display","none");
//					$("#bg").css("display","none");
//					return true;
//				});
//			}else{
//				$("#patron-error").html("There was a duplicate user with the same nric / phone details");
//				return false;
//			}			
//		}
	});
	$('#venueform').validationEngine({
		ajaxFormValidation: true,
		onBeforeAjaxFormValidation: function(){
			$("#confirmreg-box-venue").css("display","block");
			$("#bg").css("display","block");
			$("#confirm-venue-name").html($('#venue').val());
			$("#confirm-venue-yes").click(function(){
				$('#venueform').validationEngine('detach');
				$('#venueform').submit();
				$("#confirmreg-box-venue").css("display","none");
				$("#bg").css("display","none");
				return true;
			});
			return false;
		},
        onAjaxFormComplete: function(status,form) {
        	console.log('ALOOO!');
            if (status === true) {
                console.log('ok!');
            }else{
            	console.log('Tak ok!');
            }
        }
	});
	$('#serviceform').validationEngine({
		ajaxFormValidation: true,
		onBeforeAjaxFormValidation: function(){
			$("#confirmreg-box-service").css("display","block");
			$("#bg").css("display","block");
			$("#confirm-service-name").html($('#service').val());
			$("#confirm-service-yes").click(function(){
				$('#serviceform').validationEngine('detach');
				$('#serviceform').submit();
				$("#confirmreg-box-service").css("display","none");
				$("#bg").css("display","none");
				return true;
			});
			return false;
		},
        onAjaxFormComplete: function(status,form) {
        	console.log('ALOOO!');
            if (status === true) {
                console.log('ok!');
            }else{
            	console.log('Tak ok!');
            }
        }
	});
	console.log('url: '+ document.location);
	var urlParams = $.url(document.location).param();
	var from  = urlParams.f;
	var tabs = ['staff-info' , 'patron-info', 'venue-info', 'service-info'];
	var header = ['staff-hdr' , 'patron-hdr', 'venue-hdr', 'service-hdr'];
	console.log(from);
	if(from){
		hideAllTabs();
		console.log('go to not null');
		from = parseInt(from) - 1;
		var fromForm = tabs[from];
		$('#'+ fromForm).css('display', 'block');
		fromForm = header[from];
		$('#'+ fromForm).attr('class','active');
	}else{
		console.log('go to null');
		$('#staff-info').css('display', 'block');
		$('#staff-hdr').attr('class','active');
	}
	$('#staff-hdr').click(function(){
		hideAllTabs();
		$('#staff-info').attr('class','');
		$('#staff-info').css('display', 'block');
		$('#staff-hdr').attr('class','active');
	});
	$('#patron-hdr').click(function(){
		hideAllTabs();
		$('#patron-info').attr('class','');
		$('#patron-info').css('display', 'block');
		$('#patron-hdr').attr('class','active');
	});
	$('#venue-hdr').click(function(){
		hideAllTabs();
		$('#venue-info').attr('class','');
		$('#venue-info').css('display', 'block');
		$('#venue-hdr').attr('class','active');
	});
	$('#service-hdr').click(function(){
		hideAllTabs();
		$('#service-info').attr('class','');
		$('#service-info').css('display', 'block');
		$('#service-hdr').attr('class','active');
	});
	
	var url = '';
	var currentuser = $('#username').html();
	currentuser = encodeURIComponent(currentuser).replace(/[!'().]/g, escape).replace(/\*/g, "%2A");
	/** for staff **/
	
	url = '../list-staff-name/'+ currentuser + '/99999999';
	$("#fullname-staff").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-staff/' + currentuser + '/99999999' + '?';
			var index = ui.item.value.indexOf("(");
			var theName = ui.item.value.substring(0, index);
			url = url + 'name=' + theName;
			$.getJSON(url, function(data){
				 $('#id-staff').val(data.id);
				 $('#fullname-staff').val(data.name);
				 $('#identificationnum-staff').val(data.nric);
				 $('#email-staff').val(data.email);
				 $('#mobilenum-staff').val(data.hpTel);
				 $('#smsnotification-staff').prop('checked', data.sendSms);
				 $('#emailnotification-staff').prop('checked', data.sendEmail);
			});
		}
	});
	
	url = '../list-staff-nric/'+ currentuser + '/99999999';
	$("#identificationnum-staff").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-staff/' + currentuser + '/99999999' + '?';
			url = url + 'nric=' + ui.item.value;
			$.getJSON(url, function(data){
				 $('#id-staff').val(data.id);
				 $('#fullname-staff').val(data.name);
				 $('#identificationnum-staff').val(data.nric);
				 $('#email-staff').val(data.email);
				 $('#mobilenum-staff').val(data.hpTel);
				 $('#smsnotification-staff').prop('checked', data.sendSms);
				 $('#emailnotification-staff').prop('checked', data.sendEmail);
			});
		}
	});
	
	url = '../list-staff-email/'+ currentuser + '/99999999';
	$("#email-staff").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-staff/' + currentuser + '/99999999' + '?';
			url = url + 'email=' + ui.item.value;
			$.getJSON(url, function(data){
				 $('#id-staff').val(data.id);
				 $('#fullname-staff').val(data.name);
				 $('#identificationnum-staff').val(data.nric);
				 $('#email-staff').val(data.email);
				 $('#mobilenum-staff').val(data.hpTel);
				 $('#smsnotification-staff').prop('checked', data.sendSms);
				 $('#emailnotification-staff').prop('checked', data.sendEmail);
			});
		}
	});
	
	url = '../list-staff-mobile/'+ currentuser + '/99999999';
	$("#mobilenum-staff").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-staff/' + currentuser + '/99999999' + '?';
			url = url + 'mobile=' + ui.item.value;
			$.getJSON(url, function(data){
				 $('#id-staff').val(data.id);
				 $('#fullname-staff').val(data.name);
				 $('#identificationnum-staff').val(data.nric);
				 $('#email-staff').val(data.email);
				 $('#mobilenum-staff').val(data.hpTel);
				 $('#smsnotification-staff').prop('checked', data.sendSms);
				 $('#emailnotification-staff').prop('checked', data.sendEmail);
			});
		}
	});
	
	$('#searchStaff').click(function(){
		var name = $('#fullname-staff').val();
		var hpTel = $('#mobilenum-staff').val();
		var nric = $('#identificationnum-staff').val();
		var email = $('#email-staff').val();
		var url = '../search-staff/' + currentuser + '/99999999' + '/?';
		if(email != ''){
			url = url + 'email=' + email;
		}
		if(nric != ''){
			url = url + '&nric=' + nric;
		}
		if(name != ''){
			url = url + '&name=' + name;
		}
		if(hpTel != ''){
			url = url + '&mobile=' + hpTel;
		}
		$.getJSON(url, function(data){
			 $('#id-staff').val(data.id);
			 $('#fullname-staff').val(data.name);
			 $('#identificationnum-staff').val(data.nric);
			 $('#email-staff').val(data.email);
			 $('#mobilenum-staff').val(data.hpTel);
			 $('#smsnotification-staff').prop('checked', data.sendSms);
			 $('#emailnotification-staff').prop('checked', data.sendEmail);
		});
	});
	
	$('#saveStaff').click(function(){
		$('#staffform').submit();
		return false;
	});
	
	$('#deleteStaff').click(function(){
		if($('#id-staff').val() != null && $('#id-staff').val() != ''){
			$('#valid-staff').val('0');
			$("#confirmreg-box-staff").css("display","block");
			$("#bg").css("display","block");
			$("#confirm-staff-name").html($('#fullname-staff').val());
			$("#confirm-staff-nric").html($('#identificationnum-staff').val());
			$("#confirm-staff-hptel").html($('#mobilenum-staff').val());
			$("#confirm-staff-email").html($('#email-staff').val());
			$("#confirm-staff-yes").click(function(){
				$('#staffform').validationEngine('detach');
				$('#staffform').submit();
				$("#confirmreg-box-staff").css("display","none");
				$("#bg").css("display","none");
				return true;
			});
		}
	});
	
	
	/** For Patron **/
	
	url = '../list-patron-name/'+ currentuser + '/99999999';
	$("#fullname-patron").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			var index = ui.item.value.indexOf("(");
			var theNo = ui.item.value.substring(index + 1, ui.item.value.length - 1);
			url = '../search-patron/' + currentuser + '/99999999' + '?';
			url = url + 'nric=' + theNo;
			$.getJSON(url, function(data){
				$('#username-patron').val(data.username);
				 $('#fullname-patron').val(data.name);
				 $('#identificationnum-patron').val(data.nric);
				 $('#email-patron').val(data.email);
				 $('#mobilenum-patron').val(data.phone);
				 $('#smsnotification-patron').prop('checked', data.sendSms);
				 $('#emailnotification-patron').prop('checked', data.sendEmail);
				 if(data.valid){
					 $('#valid-patron').val(1);
				 }else{
					 $('#valid-patron').val(0);
				 }
				 console.log('this user validty is:' + data.valid);
			});
		}
	});
	
	url = '../list-patron-nric/'+ currentuser + '/99999999';
	$("#identificationnum-patron").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-patron/' + currentuser + '/99999999' + '?';
			url = url + 'nric=' + ui.item.value;
			$.getJSON(url, function(data){
				$('#username-patron').val(data.username);
				 $('#fullname-patron').val(data.name);
				 $('#identificationnum-patron').val(data.nric);
				 $('#email-patron').val(data.email);
				 $('#mobilenum-patron').val(data.phone);
				 $('#smsnotification-patron').prop('checked', data.sendSms);
				 $('#emailnotification-patron').prop('checked', data.sendEmail);
			});
		}
	});
	
	url = '../list-patron-phone/'+ currentuser + '/99999999';
	$("#mobilenum-patron").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-patron/' + currentuser + '/99999999' + '?';
			url = url + 'mobile=' + ui.item.value;
			$.getJSON(url, function(data){
				$('#username-patron').val(data.username);
				 $('#fullname-patron').val(data.name);
				 $('#identificationnum-patron').val(data.nric);
				 $('#email-patron').val(data.email);
				 $('#mobilenum-patron').val(data.phone);
				 $('#smsnotification-patron').prop('checked', data.sendSms);
				 $('#emailnotification-patron').prop('checked', data.sendEmail);
			});
		}
	});
	
	url = '../list-patron-email/'+ currentuser + '/99999999';
	$("#email-patron").autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			console.log(ui.item.value);
			var url = '../search-patron/' + currentuser  + '/99999999' + '?';
			url = url + 'email=' + ui.item.value;
			$.getJSON(url, function(data){
				$('#username-patron').val(data.username);
				$('#fullname-patron').val(data.name);
				$('#identificationnum-patron').val(data.nric);
				$('#email-patron').val(data.email);
				$('#mobilenum-patron').val(data.phone);
				$('#smsnotification-patron').prop('checked', data.sendSms);
				$('#emailnotification-patron').prop('checked', data.sendEmail);
			});
		}
	});
	
	url = '../list-vanues/'+ currentuser + '/99999999';
	$('#venue').autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			$('#venue').val(ui.item.name);
			$('#venue-id').val(ui.item.id);
			console.log('venue:' + ui.item.name + ' id:' +  ui.item.id);
			return false;
		}
	}).data("ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append('<a>' + item.name + '</a>')
		.appendTo( ul );
	};
	
	url = '../list-service/'+ currentuser + '/99999999';
	$('#service').autocomplete({
		source: url,
		minLength: 4,
		select: function(event, ui){
			$('#service').val(ui.item.name);
			$('#service-id').val(ui.item.id);
			console.log('service:' + ui.item.name + ' id:' +  ui.item.id);
			return false;
		}
	}).data("ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append('<a>' + item.name + '</a>')
		.appendTo( ul );
	};
	
	$('#delete-venue').click(function(){
		$('#venue-valid').val('0');
		$('#venueform').submit();
	});
	$('#save-venue').click(function(){
		$('#venue-valid').val('1');
		$('#venueform').submit();
		return true;
	});
	
	
	$('#delete-service').click(function(){
		$('#service-valid').val('0');
		$('#serviceform').submit();
	});
	$('#save-service').click(function(){
		$('#service-valid').val(true);
		$('#serviceform').submit();
	});
	
	
	$('#savePatron').click(function(){
		$('#patronform').submit();
		return false;
	});
	
	$('#deletePatron').click(function(){
		if($('#username-patron').val() != null && $('#username-patron').val() != ''){
			$('#valid-patron').val('0');
			$('#patronform').submit();
		}
	});
});

function beforeCall(form, options){
	if (console) 
	console.log("Right before the AJAX form validation call");
	return true;
}

function ajaxValidationCallback(status, form, json, options){
	if (console) 
	console.log(status);
        
	if (status === true) {
		alert("the form is valid!");
		// uncomment these lines to submit the form to form.action
		// form.validationEngine('detach');
		// form.submit();
		// or you may use AJAX again to submit the data
	}
}

function hideAllTabs(){
	console.log('hiding');
	$('#tab-window ul').each(function(){
		$(this).attr('class','hidden');
		$(this).css('display','none');
	});
	$('#tab-nav a').each(function(){
		$(this).attr('class','');
	});
}