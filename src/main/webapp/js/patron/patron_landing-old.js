$(function() {
	$(".chzn-select").chosen();
	var NO_OF_YEARS = 2;
	var date = new Date();
	var options = "";
	for ( var i = 0; i < NO_OF_YEARS; i++) {
		var yearText = date.getFullYear() + i;
		options += '<option value="' + yearText + '">' + yearText + '</option>';
	}
	$('#chooseyear').html(options);
	$('#chooseyear option:nth(0)').attr("selected", "selected");
	$('#choosemonth').val(date.getMonth());
	renderSelectedMonth(date.getMonth(), date.getFullYear());
	$('#choosemonth').change(function() {
		var year = $('#chooseyear').val();
		var month = $(this).val();
		renderSelectedMonth(month, year);
	});
	$('#chooseyear').change(function() {
		var month = $('#choosemonth').val();
		var year = $(this).val();
		renderSelectedMonth(month, year);
	});
	$('#appointmentform').submit(function(){
		var date = $('#appointmentdate').val();
		var time = $('#appointmenttime').val();
		var timeend = $('#appointmenttimeend').val();
		$('#appointmentdatetime').val(date + " " + time);
		$('#appointmentdatetimeend').val(date + " " + timeend);
	});
	
	var currentuser = $('#admin').html();
	console.log(currentuser);
	$.getJSON('../list-patient/' + currentuser, function(data) {
		setSelectOptions($('#patrons'), data, 'username', 'name', '');
		$(".chzn-select").chosen();
		$(".chzn-select").trigger("liszt:updated");
	});
	
	$.getJSON('../list-staff/'+ currentuser, function(data) {
		setSelectOptions($('#staffs'), data, 'id', 'name', '');
	});
	
	$.getJSON('../list-location/' + currentuser, function(data) {
		setSelectOptions($('#locations'), data, 'id', 'name', '');
	});
	
	$.getJSON('../list-services/' + currentuser, function(data) {
		setSelectOptions($('#services'), data, 'id', 'name', '');
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
            var html = "";

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
        if (typeof defaultValue != 'undefined') {
            selectElement.children('option[value="' + defaultValue + '"]').attr('selected', 'selected');
        }
    }
    return false;
}

function renderSelectedMonth(month, year) {
	var date = new Date(year, month, 1);
	var monthNames = [ "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" ];
	var monthDigits = [ "01", "02", "03", "04", "05", "06",
	       			"07", "08", "09", "10", "11", "12" ];
	var endDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	var endDay = endDays[month];
	if (month == 1) {
		var isLeap = new Date(date.getFullYear(), 1, 29).getMonth() == 1;
		if (isLeap) {
			endDay = endDay + 1;
		}
	}
	$('#selectedmonth').text(monthNames[date.getMonth()]);
	$('#selectedyear').text(year);
	var day = date.getDay();
	var dayCount = 1;
	var cellNo = 0;
	$('#tablebody tr').each(function() {
		$.each(this.cells, function() {
			$(this).attr('class','');
			if (cellNo < day) {
				$(this).text('');
				cellNo = cellNo + 1;
				return true;
			}
			if (dayCount > endDay) {
				$(this).text('');
				return true;
			}
			$(this).html(dayCount);
			dayCount = dayCount + 1;
			
		});
	});

	var dateString = '' + date.getFullYear() + monthDigits[date.getMonth()] + '01';
	console.log(dateString);
	$.getJSON('../list-appointment/' + dateString, function(data){
		$.each(data, function(i, obj){
			var day = obj.day;
			var SLOTS = 99999;
			var total = obj.totalAppointment;
			var percentAvailable = 100;
			if(total >= SLOTS){
				percentAvailable = 0;
			}else{
				percentAvailable = (((SLOTS - total)/SLOTS)*100);
			}
			$('#tablebody tr').each(function() {
				$.each(this.cells, function() {
					var currentDay = $(this).text();
					if(currentDay != ''){
						if(currentDay == day){
							console.log(currentDay + " - " + percentAvailable + '%');
							$(this).html('<span id="theDay">'+ day + '</span>' + ' ' + '<span class="appt-count">' + total +'<img src="../images/clock.png" class="appt-count-img" alt="@" /></span>');
						}
					}else{
						return true;
					}
				});
			});
		});
	});
	
	
	$("tr.calendar-row td").click(function() {
		if ($(this).prop("tagName") == "TD") {
			if ($(this).hasClass("fullhousecolor")) {
				return false;
			}
			var inBox = $(this).text();
			var values = inBox.split(" ");
			var dateValue = values[0];
			console.log('Helmy:' + dateValue);
			var dateInt = parseInt(dateValue);
			if (dateInt >= 1 && dateInt <= 31) {
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
				var currentuser = $('#admin').html();
				$('#appointmenttimeend').timepicker({
					controlType: 'select',
					timeOnly: true,
					timeFormat: 'hh:mm tt',
					minTime: $('#appointmenttime').val(),
					maxTime: '12:00pm',
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
				$('#appointmenttimeend').change(function(){
					if($.trim($('#appointmenttime').val()) != ""){
						var date = $("#appointmentdate").val().replace(/-/g, '');
						console.log(date + ':' + date.length);
						if(date.length == 7 ){
							console.log('add padding 0');
							date = "0".concat(date);
							console.log('DATE:' + date);
						}
						var starttime = $('#appointmenttime').val().replace(/:/g, '');
						var endtime =  $('#appointmenttimeend').val().replace(/:/g, '');
						if(starttime.substring(5,7) == 'pm'){
							starttime = starttime.substring(0,4);
							starttime = parseInt(starttime) + 1200;
						}else{
							starttime = starttime.substring(0,4);
						}
						if(endtime.substring(5,7) == 'pm'){
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
						var url = '../list-patient/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime;
						$.getJSON(url, function(data) {
							console.log(data);
							setSelectOptions($('#patrons'), data, 'username', 'name', '');
							$(".chzn-select").trigger("liszt:updated");
						});
						$.getJSON('../list-location/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime, function(data) {
							setSelectOptions($('#locations'), data, 'id', 'name', '');
						});
						$.getJSON('../list-staff/' + currentuser + '/' +  date + '/' + starttime + '/' + endtime, function(data) {
							setSelectOptions($('#staffs'), data, 'id', 'name', '');
						});
					}
				});
				$("#appointmentdate").datepicker({
					dateFormat: 'dd-mm-yy'
				});
				
				var dateString =  monthDigits[date.getMonth()];
				var currentDay = dateValue + '-' +  dateString + '-' + $("#chooseyear").val();
				console.log(dateValue);
				$("#appointmentdate").val(currentDay);
				$('#createappt-box').css({
					"display" : "block"
				});
				$('#bg').css({
					"display" : "block"
				});
			}
		}
	});
	
};
