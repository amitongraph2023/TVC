

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
					if (data.jwtToken != null) {						
						$("#loginCheck").hide();
						localStorage.setItem("status", "loggedIn");
						localStorage.setItem("role", data.role);
	
						window.location.href = "/home";
						
					} else {
						$("#loginCheck").show();
					}

				},
				error: function(data) {
					$("#loginCheck").show();
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
	localStorage.setItem("role", "Norole");
	window.location.href = "/logout";
	
});


if (localStorage.getItem('status') == "loggedIn") {
	$('#log').hide();
	$('#out').show();
	$("#home").show();
	

} else {
	$('#log').show();
	$('#out').hide();
	$("#home").hide();
}

if (localStorage.getItem('role') == "Admin") {
	$("#key").show();
	$("#reg").show();
}


$('#addKey').click(function(e) {
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
				alert("Successfully added MasterKey");
				window.location.href = "/home";
			},
			error: function() {
				alert("User can't update MasterKey");
			}
		});

		return false;
		
	} else {
		alert("Please Enter master key");
	}


});

$('#registerUser').click(function(e) {
	e.preventDefault();
	debugger;
	let userName = document.getElementById('userName').value;
	let password = document.getElementById('password').value;
	let email = document.getElementById('email').value;
	let roleSize  = document.getElementsByName('role');
	let role = null;
	
	roleSize.forEach(val => {
		if(val.checked==true) {
			role = val.value;
		}
	});
	
	let systemId = document.getElementById('systemId').value;

	if (userName != null && userName != "" && password != null && password != "" && email != null && email != ""
		&& role != null && role != "" && systemId != null && systemId != "") {

		$.ajax({
			url: "/user/registerUser",
			type: 'POST',
			data: JSON.stringify({ userName: userName, password: password, email: email, role: role, systemId: systemId }),
			contentType: 'application/json',
			success: function() {
				alert("Successfully Register");
				window.location.href = "/signin";
			},
			error: function(xhr) {
				if (xhr.responseText == null) {
					alert("Error occurred during register");
				} else {
					alert(xhr.responseText);
				}
				
			}
		});
		return false;

	} else {
		alert("Please Enter Register Details");
	}


});