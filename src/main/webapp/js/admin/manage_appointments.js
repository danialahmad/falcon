$(function() {
	var currentuser = $('#username').html();
	currentuser = encodeURIComponent(currentuser).replace(/[!'().]/g, escape).replace(/\*/g, "%2A");
	$('.remove').click(function(){
		var url = '../apppointment_fetch/' + $(this).attr('id');
		console.log(url);
		$.getJSON(url, function(data){
			console.log(data);
			$("#removeappt-box").css("display","block");
			$("#bg").css("display","block");
			$("#deleteDate").html(data.appointmentDate);
			$("#deleteTime").html(data.appointmentTime);
			$("#deleteLocation").html(data.location);
			$("#deleteStaff").html(data.staff);
			var list = "";
			for(var i = 0; i < data.patrons.length; i++){
				if(i > 0){
					list = list + ', ';
				}
				list = list + data.patrons[i].name;
			}
			$("#deletePatrons").html(list);
			$("#deleteId").html(data.id);
			console.log($("#deletePatrons").html());
		});
		$("#delete").click(function(){
			var url = "delete_appointment/" + $("#deleteId").html();
			console.log(url);
			$.ajax({
				  url: url
			}).done(function(){
				window.location = "manage-appointments";
			});
		});
	});
	$('.addPatron').click(function(){
		var appoitmentId = $(this).attr('id');
		var url = '../apppointment_fetch/' + appoitmentId;
		$.getJSON(url, function(data){
			var selectedKeys = new Array();
			for(var i = 0; i < data.patrons.length ; i++){
				console.log('patronz: ' + data.patrons[i].key);
				selectedKeys[i] = data.patrons[i].key;
			}
			console.log('patrons:' + data.patrons);
			$("#appointmentId").val(data.id);
			$("#managePatronDate").html(data.appointmentDate);
			$("#managePatronTime").html(data.appointmentTime);
			$("#managePatronTimeEnd").html(data.appointmentTimeEnd);
			$("#managePatronVenue").html(data.location);
			$("#managePatronStaff").html(data.staff);
			var date = data.appointmentDate.replace(/-/g, '');
			var starttime = data.appointmentTime.replace(/:/g, '');
			var endtime =  data.appointmentTimeEnd.replace(/:/g, '');
			if(starttime.substring(5,7) == 'pm' || starttime.substring(5,7) == 'PM'){
				starttime = starttime.substring(0,4);
				starttime = parseInt(starttime) + 1200;
			}else{
				starttime = starttime.substring(0,4);
			}
			if(endtime.substring(5,7) == 'pm' || endtime.substring(5,7) == 'PM'){
				endtime = endtime.substring(0,4);
				console.log(parseInt(endtime));
				if(parseInt(endtime) <= 1200 ){
					console.log("adding to pm");
					endtime = parseInt(endtime) + 1200;
					console.log(endtime);
				}
			}else{
				endtime = endtime.substring(0,4);
			}
			console.log("current user:" + currentuser);
			console.log("date:" + date);
			console.log("start:" + starttime + " end: "+ endtime );
			var url = '../list-patient/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/' + appoitmentId;
			$.getJSON(url, function(data) {
				setSelectOptions($('#managePatronPatrons'), data, 'username', 'name', selectedKeys);
				$(".chzn-select").chosen();
				$(".chzn-select").trigger("liszt:updated");
			});
		});
		$("#createappt-box").css("display","block");
		$("#bg").css("display","block");
		$('#updateAppointment').click(function(){
			$("#appointmentupdateform").submit();
		});
	});
	$('.reschedule').click(function(){
		var initialLocationId = 0;
		console.log('clicked');
		var selectedAppointment = $(this).attr('id');
		var url = '../apppointment_fetch/' + selectedAppointment;
		$.getJSON(url, function(data){
			console.log(data);
			$("#rescheduleappt-box").css("display","block");
			$("#bg").css("display","block");
			
			$("#rescheduleDate").val(data.appointmentDate);
			var date = $("#rescheduleDate").val().replace(/-/g, '');
			console.log(date + ':' + date.length);
			if(date.length == 7 ){
				console.log('add padding 0');
				date = "0".concat(date);
				console.log('DATE:' + date);
			}

			$("#rescheduleTime").val(data.appointmentTime);
			var starttime = $('#rescheduleTime').val().replace(/:/g, '');
			if(starttime.substring(5,7).toLowerCase() == 'pm' ){
				starttime = starttime.substring(0,4);
				starttime = parseInt(starttime) + 1200;
			}else{
				starttime = starttime.substring(0,4);
			}

			$("#rescheduleTimeEnd").val(data.appointmentTimeEnd);
			var endtime =  $('#rescheduleTimeEnd').val().replace(/:/g, '');
			if(endtime.substring(5,7).toLowerCase() == 'pm'){
				endtime = endtime.substring(0,4);
				console.log(parseInt(endtime));
				if(parseInt(endtime) <= 1200 ){
					console.log("adding to pm");
					endtime = parseInt(endtime) + 1200;
					console.log(endtime);
				}
			}else{
				endtime = endtime.substring(0,4);
			}
			initialLocationId = data.locationId;
			console.log("on select row appointment id is:" + selectedAppointment );
			$.getJSON('../list-location/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/' + selectedAppointment, function(data) {
				setSelectOptions($('#rescheduleVenue'), data, 'id', 'name', [initialLocationId]);
			});
			
			$("#rescheduleVenue").val(data.locationId);
			$("#rescheduleStaff").html(data.staff);
			var list = "";
			for(var i = 0; i < data.patrons.length; i++){
				if(i > 0){
					list = list + ', ';
				}
				list = list + data.patrons[i].name;
			}
			$("#reschedulePatrons").html(list);
			$("#rescheduleId").html(data.id);
			$("#rescheduleDate").datepicker({
				dateFormat: 'dd-mm-yy'
			});
			$("#rescheduleDate").change(function(){
				$("#updateTime").html('true');
				if($.trim($('#rescheduleTime').val()) != ""){
					console.log('the current location id:' + initialLocationId);
					var date = $("#rescheduleDate").val().replace(/-/g, '');
					console.log(date + ':' + date.length);
					if(date.length == 7 ){
						console.log('add padding 0');
						date = "0".concat(date);
						console.log('DATE:' + date);
					}
					var starttime = $('#rescheduleTime').val().replace(/:/g, '');
					var endtime =  $('#rescheduleTimeEnd').val().replace(/:/g, '');
					if(starttime.substring(5,7).toLowerCase() == 'pm' ){
						starttime = starttime.substring(0,4);
						starttime = parseInt(starttime) + 1200;
					}else{
						starttime = starttime.substring(0,4);
					}
					if(endtime.substring(5,7).toLowerCase() == 'pm'){
						endtime = endtime.substring(0,4);
						console.log(parseInt(endtime));
						if(parseInt(endtime) <= 1200 ){
							console.log("adding to pm");
							endtime = parseInt(endtime) + 1200;
							console.log(endtime);
						}
					}else{
						endtime = endtime.substring(0,4);
					}
					console.log("date:" + date);
					console.log("start:" + starttime + " end: "+ endtime );
					console.log("on click appointment id is:" + $("#rescheduleId").html());
					$.getJSON('../list-location/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/' + $("#rescheduleId").html(), function(data) {
						setSelectOptions($('#rescheduleVenue'), data, 'id', 'name', [initialLocationId]);
					});

					url = '../check-staff-availability/' + $("#rescheduleId").html() +  '/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/';
					$.ajax({
						url: url
					}).done(function(data){
						console.log('response is:' + data);
						if(data == 'busy'){
							$("#errorMessageStaff").html('Current staff is not available between the time selected');
							$("#errorMessageStaff").css('visibility','visible');
							$("#update").attr('disabled', 'disabled');
						}else{
							$("#errorMessageStaff").css('visibility','hidden');
							url = '../check-patron-availability/'  + $("#rescheduleId").html() +  '/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/';
							$.ajax({
								url: url
							}).done(function(data){
								console.log('response is:' + data);
								if(data == ''){
									$("#errorMessagePatron").css('visibility','hidden');
									$("#update").removeAttr('disabled');
								}else{
									$("#errorMessagePatron").html(data);
									$("#errorMessagePatron").css('visibility','visible');
									$("#update").attr('disabled', 'disabled');
								}
							});
						}
						
					});
				}
			});
//			$('#rescheduleTime').timepicker({
//				controlType: 'select',
//				timeOnly: true,
//				timeFormat: 'hh:mm tt',
//				defaultValue:	function(){
//					var now = new Date();
//					var hours = now.getHours();
//					var hour = hours % 12;
//					var min = now.getMinutes();
//					var ampm = hours >= 12 ? 'pm' : 'am';
//					min = min < 10 ? '0' + min : min;
//					console.log(hour + ':' + min + ' ' + ampm);
//					return hours + ':' + min + ' ' + ampm;
//				}
//			});
//			$('#rescheduleTimeEnd').timepicker({
//				controlType: 'select',
//				timeOnly: true,
//				timeFormat: 'hh:mm tt',
//				minTime: $('#appointmenttime').val(),
//				maxTime: '12:00pm',
//				defaultValue:	function(){
//					var now = new Date();
//					var hours = now.getHours();
//					var hour = hours % 12;
//					var min = now.getMinutes();
//					var ampm = hours >= 12 ? 'pm' : 'am';
//					min = min < 10 ? '0' + min : min;
//					console.log(hour + ':' + min + ' ' + ampm);
//					return hours + ':' + min + ' ' + ampm;
//				}
//			});
			
			$('#rescheduleTimeEnd').change(function(){
				$("#updateTime").html('true');
				if($.trim($('#rescheduleTime').val()) != ""){
					console.log('the current location id:' + initialLocationId);
					var date = $("#rescheduleDate").val().replace(/-/g, '');
					console.log(date + ':' + date.length);
					if(date.length == 7 ){
						console.log('add padding 0');
						date = "0".concat(date);
						console.log('DATE:' + date);
					}
					var starttime = $('#rescheduleTime').val().replace(/:/g, '');
					var endtime =  $('#rescheduleTimeEnd').val().replace(/:/g, '');
					if(starttime.substring(5,7).toLowerCase() == 'pm' ){
						starttime = starttime.substring(0,4);
						starttime = parseInt(starttime) + 1200;
					}else{
						starttime = starttime.substring(0,4);
					}
					if(endtime.substring(5,7).toLowerCase() == 'pm'){
						endtime = endtime.substring(0,4);
						console.log(parseInt(endtime));
						if(parseInt(endtime) <= 1200 ){
							console.log("adding to pm");
							endtime = parseInt(endtime) + 1200;
							console.log(endtime);
						}
					}else{
						endtime = endtime.substring(0,4);
					}
					console.log("date:" + date);
					console.log("start:" + starttime + " end: "+ endtime );
					console.log("on click appointment id is:" + $("#rescheduleId").html());
					$.getJSON('../list-location/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/' + $("#rescheduleId").html(), function(data) {
						setSelectOptions($('#rescheduleVenue'), data, 'id', 'name', [initialLocationId]);
					});

					url = '../check-staff-availability/' + $("#rescheduleId").html() +  '/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/';
					$.ajax({
						url: url
					}).done(function(data){
						console.log('response is:' + data);
						if(data == 'busy'){
							$("#errorMessageStaff").html('Current staff is not available between the time selected');
							$("#errorMessageStaff").css('visibility','visible');
							$("#update").attr('disabled', 'disabled');
						}else{
							$("#errorMessageStaff").css('visibility','hidden');
							url = '../check-patron-availability/'  + $("#rescheduleId").html() +  '/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/';
							$.ajax({
								url: url
							}).done(function(data){
								console.log('response is:' + data);
								if(data == ''){
									$("#errorMessagePatron").css('visibility','hidden');
									$("#update").removeAttr('disabled');
								}else{
									$("#errorMessagePatron").html(data);
									$("#errorMessagePatron").css('visibility','visible');
									$("#update").attr('disabled', 'disabled');
								}
							});
						}
						
					});
				}
			});
		});
		console.log(currentuser);
//		$.getJSON('../list-location/' + currentuser + '/99999999', function(data) {
//			setSelectOptions($('#rescheduleVenue'), data, 'id', 'name', '');
//		});
		
		$('#update').click(function(){
			var selectedVenue =  $("#rescheduleVenue").val();
			console.log('venue is:' + selectedVenue);
			if(selectedVenue == ''){
				$("#errorMessageLocation").html('Please select a valid location');
				$("#errorMessageLocation").css('visibility','visible');
				console.log('not selected');
				return false;
			}else{
				$("#errorMessageLocation").css('visibility','hidden');
			}
			var flag = $("#updateTime").html();
			console.log('action ? :' + flag);
			if(flag == 'true'){
				if($.trim($('#rescheduleTime').val()) != ""){
					console.log('the current location id:' + initialLocationId);
					var date = $("#rescheduleDate").val().replace(/-/g, '');
					console.log(date + ':' + date.length);
					if(date.length == 7 ){
						console.log('add padding 0');
						date = "0".concat(date);
						console.log('DATE:' + date);
					}
					var starttime = $('#rescheduleTime').val().replace(/:/g, '');
					var endtime =  $('#rescheduleTimeEnd').val().replace(/:/g, '');
					if(starttime.substring(5,7).toLowerCase() == 'pm' ){
						starttime = starttime.substring(0,4);
						starttime = parseInt(starttime) + 1200;
					}else{
						starttime = starttime.substring(0,4);
					}
					if(endtime.substring(5,7).toLowerCase() == 'pm'){
						endtime = endtime.substring(0,4);
						console.log(parseInt(endtime));
						if(parseInt(endtime) <= 1200 ){
							console.log("adding to pm");
							endtime = parseInt(endtime) + 1200;
							console.log(endtime);
						}
					}else{
						endtime = endtime.substring(0,4);
					}
					console.log("date:" + date);
					console.log("start:" + starttime + " end: "+ endtime );
					console.log("on click appointment id is:" + $("#rescheduleId").html());
					$.getJSON('../list-location/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/' + $("#rescheduleId").html(), function(data) {
						setSelectOptions($('#rescheduleVenue'), data, 'id', 'name', [initialLocationId]);
					});

					url = '../check-staff-availability/' + $("#rescheduleId").html() +  '/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/';
					$.ajax({
						url: url
					}).done(function(data){
						console.log('response is:' + data);
						if(data == 'busy'){
							$("#errorMessageStaff").html('Current staff is not available between the time selected');
							$("#errorMessageStaff").css('visibility','visible');
							$("#update").attr('disabled', 'disabled');
						}else{
							$("#errorMessageStaff").css('visibility','hidden');
							url = '../check-patron-availability/'  + $("#rescheduleId").html() +  '/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime + '/';
							$.ajax({
								url: url
							}).done(function(data){
								console.log('response is:' + data);
								if(data == ''){
									$("#errorMessagePatron").css('visibility','hidden');
									$("#update").removeAttr('disabled');
									console.log('the selected venus is:' + selectedVenue);
									url = "reschedule_appointment/" + $("#rescheduleId").html() + "/" + $("#rescheduleDate").val() + "/" + $("#rescheduleTime").val() + "/" + $("#rescheduleTimeEnd").val() + "/" + selectedVenue + "/";
									console.log(url);
									$.ajax({
										  url: url
									}).done(function(){
										window.location = "manage-appointments";
									});
								}else{
									$("#errorMessagePatron").html(data);
									$("#errorMessagePatron").css('visibility','visible');
									$("#update").attr('disabled', 'disabled');
								}
							});
						}
						
					});
				}
			}else{
				console.log('updating');
				var url = "reschedule_appointment/" + $("#rescheduleId").html() + "/" + $("#rescheduleDate").val() + "/" + $("#rescheduleTime").val() + "/" + $("#rescheduleTimeEnd").val() + "/" + $("#rescheduleVenue").val() + "/";
				console.log(url);
				$.ajax({
					  url: url
				}).done(function(){
					window.location = "manage-appointments";
				});
			}
		});
	});
	$.getJSON('../list-staff/'+ currentuser + '/99999999', function(data) {
		console.log('in here');
		setSelectOptions($('#staffs'), data, 'id', 'name', '');
	});
	$.getJSON('../list-patient/' + currentuser + '/99999999', function(data) {
		setSelectOptions($('#patrons'), data, 'username', 'name', '');
	});

	$.getJSON('../list-location/' + currentuser + '/99999999', function(data) {
		setSelectOptions($('#locations'), data, 'id', 'name', '');
	});
	
	$.getJSON('../list-services/' + currentuser + '/99999999', function(data) {
		setSelectOptions($('#services'), data, 'id', 'name', '');
	});
	$("#appointmentdate").datepicker({
		dateFormat: 'dd-mm-yy'
	});
	$('#appointmenttime').timepicker({
		controlType: 'select',
		timeOnly: true,
		timeFormat: 'hh:mm tt',
		defaultValue:	function(){
			var now = new Date();
			var hours = now.getHours();
			var hour = hours % 12;
			var min = now.getMinutes();
			var ampm = hours >= 12 ? 'pm' : 'am';
			min = min < 10 ? '0' + min : min;
			console.log(hour + ':' + min + ' ' + ampm);
			return hours + ':' + min + ' ' + ampm;
		}
	});
	$('#searchform').validationEngine();
	$('#querybutton').click(function(){
		$('#searchform').submit();
	});
	
	$("#dialog").dialog({
		autoOpen: false,
		width: 175,
		buttons: [ { text: "Ok", click: function(){
			var id = $("#source-id").text();
			var value = $("#hh").val() + ":" + $("#mm").val() + " " + $("#ampm").val(); 
			$(id).val(value);
			$(id).change();
			if (id == "#rescheduleTime"){
				$("#rescheduleTimeEnd").val(value);
				$("#rescheduleTimeEnd").change();
			}
			$(this).dialog("close");
			$("#rescheduleTimeEnd").change();
		}}]
	});
	
	$("#rescheduleTime").click(function(){
		$("#source-id").text("#rescheduleTime");
		parse_picker($(this).val());
		$("#dialog").dialog("open");
		$("#ui-id-1").parent().parent().css("zIndex", 5000);
	});

	$("#rescheduleTimeEnd").click(function(){
		$("#source-id").text("#rescheduleTimeEnd");
		parse_picker($(this).val());
		$("#dialog").dialog("open");
		$("#ui-id-1").parent().parent().css("zIndex", 5000);
	});

	$("#button-down-hh").click(function(){
		var hh = $("#hh").val();
		try{
			hh = parseInt(hh);
			hh--;
			if (hh <= 0) hh = 12;
			hh = (hh < 10 ? "0" + hh:"" + hh);
		}catch(e){
			hh = "12";
		}
		$("#hh").val(hh);
	});
	
	$("#button-up-hh").click(function(){
		var hh = $("#hh").val();
		try{
			hh = parseInt(hh);
			hh++;
			if (hh > 12) hh = 1;
			hh = (hh < 10 ? "0" + hh:"" + hh);
		}catch(e){
			hh = "12";
		}
		$("#hh").val(hh);
	});
	
	$("#button-up-mm").click(function(){
		var mm = $("#mm").val();
		try{
			mm = parseInt(mm);
			mm = mm + 15;
			if (mm >= 60) mm = 0;
			mm = (mm < 10 ? "0" + mm:"" + mm);
		}catch(e){
			mm = "00";
		}
		$("#mm").val(mm);
	});
	
	$("#button-down-mm").click(function(){
		var mm = $("#mm").val();
		try{
			mm = parseInt(mm);
			mm = mm - 15;
			if (mm < 0) mm = 45;
			mm = (mm < 10 ? "0" + mm:"" + mm);
		}catch(e){
			mm = "00";
		}
		$("#mm").val(mm);
	});
	
	$("#button-down-ampm, #button-up-ampm").click(function(){
		var ampm = $("#ampm").val();
		if (ampm == "AM"){
			ampm = "PM";
		}else{
			ampm = "AM";
		}
		$("#ampm").val(ampm);
	});
	
	function parse_picker(text){
		if (text.length == 8){
			var hh;
			var mm;
			var ampm;
			var thh = text.substring(0, 2);
			var tmm = text.substring(3, 5);
			var tampm = text.substring(6, 8);
			try{
				hh = parseInt(thh);
			}catch(e){
				hh = 12;
			}
			hh = (hh < 10 ? "0" + hh:"" + hh);
			$("#hh").val(hh);
			try{
				mm = parseInt(tmm);
			}catch(e){
				mm = 12;
			}
			mm = (mm < 10 ? "0" + mm:"" + mm);
			$("#mm").val(mm);
			if (tampm == "PM"){
				ampm = "PM";
			}else{
				ampm = "AM";
			}
			$("#ampm").val(ampm);
		}else{
			$("#hh").val("12");
			$("#mm").val("00");
			$("#ampm").val("AM");
		}
	}
});


function setSelectOptions(selectElement, values, valueKey, textKey, defaultValue) {
    if (typeof (selectElement) == "string") {
        selectElement = $(selectElement);
    }
    selectElement.empty();
    if (typeof (values) == 'object') {
        if (values.length) {
            var type = typeof (values[0]);
            var html = '<option value=""></option>'; ;

            if (type == 'object') {
                // values is array of hashes
                $.each(values, function () {
                	console.log('key:' + this[valueKey] + ' val:' + this[textKey]);
                    html += '<option value="' + this[valueKey] + '">' + this[textKey] + '</option>';                    
                });

            } else {
                $.each(values, function () {
                    var value = this.toString();
                    html += '<option value="' + value + '">' + value + '</option>';                    
                });
            }
            selectElement.html(html);
        }
        // select the defaultValue is one was passed in
        console.log('default: ' +defaultValue);
        if (typeof defaultValue != 'undefined') {
        	console.log('default value length:' + defaultValue.length);
        	for(var i = 0; i < defaultValue.length ; i++){
				var key = defaultValue[i];
				selectElement.children('option[value="' + key + '"]').attr('selected', 'selected');
			}
        }
    }
    return false;
}

function closeDialog(){
	window.location = "manage-appointments";
}