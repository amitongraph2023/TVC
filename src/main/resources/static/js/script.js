$(document).ready(function() {
	$('#login').click(function(e) {
		debugger;
		e.preventDefault();

		let userName = document.getElementById('userName').value;
		let password = document.getElementById('password').value;

		if (userName != null && userName != "" && password != null && password != "") {
			$.ajax({
				url: "/user/authenticate",
				type: 'POST',
				data: JSON.stringify({ userName: userName, password: password }),
				dataType: 'json',
				headers: {
					"Accept": "application/json",
					"Content-type": "application/json",
				},
				success: function(data) {
					if (data.jwtToken == null) {
						$(".error-msg").show();
					} else {
						$(".error-msg").hide();
						console.log("success");
						localStorage.setItem("status", "loggedIn");
						window.location.href = "/home";
					}

				},
				error: function(data) {
					console.log("error");
				}
			});
			return false;

		} else {
			alert("Please Enter login details");
		}


	});

});


$('#out').click(function(e){
	debugger;
	e.preventDefault();
	localStorage.setItem("status", "loggedOut");
	window.location.href = "/logout";
	
});


if (localStorage.getItem('status') == "loggedIn") {
	$('#log').hide();
	$('#out').show();

} else {
	$('#log').show();
	$('#out').hide();
}


$('#addKey').click(function(e) {
	debugger;
	e.preventDefault();

	let userId = document.getElementById('userId').value;
	let masterKey = document.getElementById('masterKey').value;

	if (masterKey != null && masterKey != "") {
		$.ajax({
			url: "/user/updateMasterKey",
			type: 'POST',
			data: JSON.stringify({ userId: userId, masterKey: masterKey }),
			contentType: 'application/json',
			success: function() {
				alert("Successfully added User MasterKey");
				window.location.href = "/home";
			},
			error: function() {
				alert("Error occurred while added MasterKey");
			}
		});

		return false;
		
	} else {
		alert("Please Enter master key");
	}


});

     
      