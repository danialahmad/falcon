function aprisMonthTextAsInteger(val) {
	var monthText = [];
	monthText['January'] = 0;
	monthText['February'] = 1;
	monthText['March'] = 2;
	monthText['April'] = 3;
	monthText['May'] = 4;
	monthText['June'] = 5;
	monthText['July'] = 6;
	monthText['August'] = 7;
	monthText['September'] = 8;
	monthText['October'] = 9;
	monthText['November'] = 10;
	monthText['December'] = 11;
	return monthText[val];
}

function parseAprisDate(dateString) {
	// 4:12 PM - Sunday, July 14, 2013
	// [4:12 PM][Sunday, July 14, 2013]
	var dateTime = dateString.split(" - ");
	// [4:12] [PM]
	var timeAmpm = dateTime[0].split(" ");
	var ampm = timeAmpm[1];
	var offset = 0;
	if (ampm == "PM") {
		offset = 12;
	}
	// [4][12]
	var hourMinute = timeAmpm[0].split(":");
	var hours = parseInt(hourMinute[0]) + offset;
	var minutes = parseInt(hourMinute[1]);
	var seconds = 0;
	var milliseconds = 0;

	// [Sunday][July 14][2013]
	var dayDateYear = dateTime[1].split(", ");
	var year = parseInt(dayDateYear[2]);

	var monthTextDay = dayDateYear[1].split(" ");
	var monthText = monthTextDay[0];
	var month = aprisMonthTextAsInteger(monthText);
	var day = parseInt(monthTextDay[1]);

	return new Date(year, month, day, hours, minutes, seconds, milliseconds);
}

function aprisDayToText(val) {
	var days = [];
	days[0] = "Sunday";
	days[1] = "Monday";
	days[2] = "Tuesday";
	days[3] = "Wednesday";
	days[4] = "Thursday";
	days[5] = "Friday";
	days[6] = "Saturday";
	return days[val];
}
function aprisMonthToText(val) {
	var months = [];
	months[0] = 'January';
	months[1] = 'February';
	months[2] = 'March';
	months[3] = 'April';
	months[4] = 'May';
	months[5] = 'June';
	months[6] = 'July';
	months[7] = 'August';
	months[8] = 'September';
	months[9] = 'October';
	months[10] = 'November';
	months[11] = 'December';
	return months[val];
}

function aprisFormatDate(date) {
	// 4:12 PM - Sunday, July 14, 2013
	var hours = date.getHours();
	var ampm = "AM";
	if (hours == 0) {
		hours = 12;
	}
	if (hours > 12) {
		hours = hours - 12;
		ampm = "PM";
	}
	var minutes = date.getMinutes();
	if (minutes < 10) {
		minutes = "0" + minutes;
	} else {
		minutes = "" + minutes;
	}
	var dayText = aprisDayToText(date.getDay());
	var monthText = aprisMonthToText(date.getMonth());
	var dayOfMonth = date.getDate();
	var year = date.getFullYear();
	var result = "" + hours + ":" + minutes + " " + ampm + " - " + dayText
			+ ", " + monthText + " " + dayOfMonth + ", " + year;
	return result;
}

function aprisGetDateTime(selector) {
	var elem = $(selector);
	var dateString = elem.text();
	var date = parseAprisDate(dateString);
	var ms = date.getTime();
	ms += 60000;
	var nextDate = new Date();
	nextDate.setTime(ms);
	var newDateString = aprisFormatDate(nextDate);
	elem.text(newDateString);
}

$(function() {
	window.setInterval("aprisGetDateTime('#section-current-time');", 60000);
});