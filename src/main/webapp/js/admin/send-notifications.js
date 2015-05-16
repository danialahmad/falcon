$(function() {
	populate_patron_list();
	handle_submit();
	handle_text_limitation();
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	function populate_patron_list() {

		function handle_ajax_success(data) {
			var c = data.length;
			var sel = $("#notification-send-target");
			sel.empty();
			var i;
			for (i = 0; i < c; i++) {
				var item = $("<option>");
				item.attr("value", data[i][0]);
				item.text(data[i][1]);
				sel.append(item);
			}
		}

		var url = $("#link-list-patrons").attr("href");
		$.ajax({
			"url" : url,
			"success" : handle_ajax_success
		});
	}

	function handle_submit() {
		$("#button-submit").click(function() {
			var form = $("#form-send-notification");
			var options = {
				"success" : handle_ajax_success
			};
			form.ajaxSubmit(options);
		});
		function handle_ajax_success(data) {
			alert("SMS Sent : " + data.smsSent + "\n" + "SMS Balance : "
					+ data.smsBalance);
		}
	}

	function handle_text_limitation() {
		function truncate_text(event) {
			var m = parseInt($("#limit-message-size").text());
			var content = $("#message").val();
			if (content.length > m) {
				var truncated = content.substr(0, m);
				$("#message").val(truncated);
				$("#limit-message-remaining").text('0');
			} else {
				$("#limit-message-remaining").text(m - content.length);

			}
		}
		$("#message").change(function(event) {
			truncate_text(event);
		});
		$("#message").keyup(function(event) {
			truncate_text(event);
		});
	}
});