$(function() {
	
	$('#appointmentform').validationEngine();
	$('#searchform').validationEngine();
	$('#organizationform').validationEngine();
	
	
	var currentuser = $('#username').html();
	currentuser = encodeURIComponent(currentuser).replace(/[!'().]/g, escape).replace(/\*/g, "%2A");
	$.getJSON('../list-staff-patron/'+ currentuser + '/99999999', function(data) {
		console.log('in here');
		setSelectOptions($('#staffs'), data, 'id', 'name', '');
		setSelectOptions($('#search_staffs'), data, 'id', 'name', '');
	});
	
	$.getJSON('../list-location-patron/' + currentuser + '/99999999', function(data) {
		setSelectOptions($('#locations'), data, 'id', 'name', '');
		setSelectOptions($('#search_locations'), data, 'id', 'name', '');
	});
	
	$.getJSON('../list-services-patron/' + currentuser + '/99999999', function(data) {
		setSelectOptions($('#services'), data, 'id', 'name', '');
		setSelectOptions($('#search_services'), data, 'id', 'name', '');
	});
	
	$.getJSON('../list-organizations/', function(data) {
		setSelectOptions($('#organization'), data, 'username', 'name', '');
	});
	$("#search_appointmentdate").datepicker({
		dateFormat: 'dd-mm-yy'
	});
	$("#appointmentdate").datepicker({
		dateFormat: 'dd-mm-yy'
	});
	$("#appointmenttime").click(function(){
		$("#source-id").text("#appointmenttime");
		parse_picker($(this).val());
		$("#dialog").dialog("open");
		$("#ui-id-1").parent().parent().css("zIndex", 5000);
	});

	$("#appointmenttimeend").click(function(){
		$("#source-id").text("#appointmenttimeend");
		parse_picker($(this).val());
		$("#dialog").dialog("open");
		$("#ui-id-1").parent().parent().css("zIndex", 5000);
	});
	
	$('.remove').click(function(){
		var url = '../apppointment_patron/' + $(this).attr('id');
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
				$("#deleteId").html(data.patrons[i].patronAppointmentId);
			}
			$("#deletePatrons").html(list);
			console.log($("#deletePatrons").html());
		});
		$("#delete").click(function(){
			var url = "delete_appointment/" + $("#deleteId").html();
			console.log(url);
			$.ajax({
				  url: url
			}).done(function(){
				window.location = "patron_landing";
			});
		});
	});
	
	$('#appointmentform').submit(function(){
		var date = $('#appointmentdate').val();
		var time = $('#appointmenttime').val();
		var timeend = $('#appointmenttimeend').val();
		$('#appointmentdatetime').val(date + " " + time);
		$('#appointmentdatetimeend').val(date + " " + timeend);
	});
	
	$("#dialog").dialog({
		autoOpen: false,
		width: 175,
		buttons: [ { text: "Ok", click: function(){
			var id = $("#source-id").text();
			var value = $("#hh").val() + ":" + $("#mm").val() + " " + $("#ampm").val(); 
			$(id).val(value);
			if (id = "appointmenttime"){
				$("#appointmenttimeend").val(value);
			}
			$(this).dialog("close");
		}}]
	});
	
	
	$('#querybutton').click(function(){
		$('#searchform').submit();
	});

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
        	for(var i = 0; i < defaultValue.length ; i++){
				var key = defaultValue[i];
				selectElement.children('option[value="' + key + '"]').attr('selected', 'selected');
			}
        }
    }
    return false;
}


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

