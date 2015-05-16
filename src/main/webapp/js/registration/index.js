$(function() {
	$("#registrationform").validationEngine({
		ajaxFormValidation: true,
		onBeforeAjaxFormValidation: function(){
			$("#confirmreg-box").css("display","block");
			$("#bg").css("display","block");
			$("#confirm-patron-name").html($('#fullname').val());
			$("#confirm-patron-nric").html($('#identificationnum-patron').val());
			$("#confirm-patron-hptel").html($('#mobilenum-patron').val());
			$("#confirm-patron-email").html($('#email-patron').val());
			$("#confirm-patron-org").html($('#organization option:selected').text());
			$("#confirm-patron-yes").click(function(){
				$('#registrationform').validationEngine('detach');
				$('#registrationform').submit();
				$("#confirmreg-box").css("display","none");
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
	$.getJSON('list-organizations/', function(data) {
		setSelectOptions($('#organization'), data, 'username', 'name', '');
	});
	
	$('#savePatron').click(function(){
		$('#registrationform').submit();
		return false;
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