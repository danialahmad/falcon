$(function(){
	$('.remove').click(function(){
		var url = '../apppointment_patron_fetch/' + $(this).attr('id');
		console.log(url);
		$.getJSON(url, function(data){
			console.log(data);
			$("#removeappt-box").css("display","block");
			$("#bg").css("display","block");
			$("#deleteDate").html(data.appointmentDate);
			$("#deleteTime").html(data.appointmentTime);
			$("#deleteLocation").html(data.location);
			$("#deleteStaff").html(data.staff);
			$("#deletePatron").html(data.patron);
			$("#deleteId").html(data.id);
			console.log($("#deletePatrons").html());
		});
		$("#delete").click(function(){
			var url = "delete_patron_appointment/" + $("#deleteId").html();
			console.log(url);
			$.ajax({
				  url: url
			}).done(function(){
				window.location = "manage-appointments";
			});
		});
	});
});