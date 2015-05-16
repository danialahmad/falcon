$(function() {
	function handle_item(item) {
		$("#recepient").val(item.name);
		$("#send-name").val(item.name);
		$("#send-phone").val(item.phone);
		$("#send-mail").val(item.mail);

	}

	function do_nothing() {
	}

	$("#recepient").autocomplete({
		"minLength" : 0,
		"source" : $("#link-list-patient").attr("href"),
		"select" : function(event, ui) {
			ui.item ? handle_item(ui.item) : do_nothing();
			return false;
		}
	}).data("ui-autocomplete")._renderItem = function(ul, item) {
		return $("<li>").append("<a>" + item.name + "</a>").appendTo(ul);
	};

	$("#button-submit").click(function() {
		var isMailChecked = $('#notification-mail').is(":checked");
		var isSmsChecked = $('#notification-sms').is(":checked");
		if ($("#send-name").val() == "") {
			alert("please choose a patient.");
			return false;
		}
		if (isMailChecked == false && isSmsChecked == false) {
			alert("No notification method selected.");
			return false;
		}
		if (isMailChecked == false) {
			$("#send-mail").val("");
		}
		if (isSmsChecked == false) {
			$("#send-phone").val("");
		}
		$("#send-message").val($("#message-content").val());
		var options = {
			"success" : function(responseText, statusText, xhr) {

			}
		};
		$("#form-send-notification").ajaxSubmit(options);
		return false;

	});
});