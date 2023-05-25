

$(document).ready(function() {
	$('#login').click(function(e) {
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
	$("#changePass").show();
}

$('#registerUser').click(function(e) {
	e.preventDefault();
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

$('#addMerchant').click(function(e) {
	e.preventDefault();

	let merchantId = document.getElementById('merchantId').value;
	let merchantName = document.getElementById('merchantName').value;

	if (merchantId != null && merchantId != "" && merchantName != null && merchantName != "") {
		$.ajax({
			url: "/addMerchant",
			type: 'POST',
			data: JSON.stringify({ merchantId: merchantId, merchantName: merchantName }),
			contentType: 'application/json',
			success: function() {
				alert("Merchant successfully added");
				window.location.href = "/getLocation";

			},
			error: function() {
				alert("Merchant Already Exist With this MerchantId");
			}
		});
		return false;

	} else {
		alert("Please Enter Merchant details");
	}


});

function deleteMerchant(merchantId) {
	$.ajax({
		url: "/deleteMerchant/" + merchantId,
		type: 'GET',
		success: function() {
			alert("Merchant successfully removed");
			window.location.href = "/getLocation";
		},
		error: function() {
			alert("Failed to remove Merchant");
		}
	});
}


const configurationButton = document.getElementById("key");
const modal = document.getElementById("myModal");

if(configurationButton!=null) {
	configurationButton.addEventListener("click", function() {
	  modal.style.display = "block";
	});	
}

window.addEventListener("click", function(event) {
  if (event.target === modal) {
    modal.style.display = "none";
  }
});

$('#recoverKey').click(function(e) {
	e.preventDefault();

	let userId = document.getElementById('userId').value;
	initiateMasterKeyRecovery(userId);

});

function initiateMasterKeyRecovery(userId) {
	var confirmation = confirm("You are about to change the Master Key. Please note that once the Master Key is changed, "
		+ "all new transactions will be approved with the new key. "
		+ "Consequently, any verification attempt of older transactions against this new key during reconciliation will result in a mismatch. "
		+ "arguments You must understand this before proceeding with the Master Key recovery process. "
		+ "If unsure, please consult your system administrator or contact our support team.");

	if (confirmation) {
		var admin1 = window.prompt("Enter the password for admin1:");
		if (admin1) {
			var admin2 = window.prompt("Enter the password for admin2:");

		}

		if (admin2) {
			var admin1Password = window.prompt("Re Enter the password for admin1:");
			if (admin1Password) {
				var admin2Password = window.prompt("Re Enter the password for admin2:");
			}
		}

		if (admin2Password) {
			$.ajax({
				url: "/validatePasswords",
				type: "POST",
				data: { userId: userId, admin1Password: admin1Password, admin2Password: admin2Password },
				success: function() {
					$("#label").show();
					$("#masterKey").show();
					$("#addKey").show();
					$("#recoverKey").hide();

				},
				error: function() {
					alert("A System must have exactly two admin users. Please make sure You have provided the correct passwords.");
				}
			});


		} else {
			alert("Without Entering password you can't save master key");
		}

	} else {
		window.location.href = "/home";
	}
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
				alert("Master Key Successfully Recovered");
				window.location.href = "/home";
			},
			error: function() {
				alert("User can't Recover MasterKey");
			}
		});

		return false;

	} else {
		alert("Please Enter master key");
	}

});


$('#change').click(function(e) {
	e.preventDefault();
	
	let userId = document.getElementById('userId').value;
	let oldPassword = document.getElementById('oldPassword').value;
	let newPassword = document.getElementById('newPassword').value;
	
	if (newPassword != null && newPassword != "") {
		$.ajax({
			url: "/users/changeAdminPassword",
			type: 'PUT',
			data: JSON.stringify({ userId: userId, oldPassword: oldPassword, newPassword: newPassword }),
			contentType: 'application/json',
			success: function() {
				$("#passwordCheck").hide();
				alert("Password changed successfully");
				window.location.href = "/home";
			},
			error: function() {
				$("#passwordCheck").show();
			}
		});
		return false;

	} else {
		alert("Please Enter New Password");
	}
	
});