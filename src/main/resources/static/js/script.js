

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
	$("#addMasterKey").show();
	$("#stop").show();
	$("#changePass").show();
}


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

window.addEventListener("click", function(event) {
  if (event.target === modal1) {
    modal1.style.display = "none";
  }
})

const modal1 = document.getElementById("Modal");

$('#recoverKey').click(function(e) {
	debugger;
	e.preventDefault();

	let userId = document.getElementById('userId').value;
	var confirmation = confirm("You are about to change the Master Key. Please note that once the Master Key is changed, "
		+ "all new transactions will be approved with the new key. "
		+ "Consequently, any verification attempt of older transactions against this new key during reconciliation will result in a mismatch. "
		+ "arguments You must understand this before proceeding with the Master Key recovery process. "
		+ "If unsure, please consult your system administrator or contact our support team.");
		
	if (confirmation) {
		var count = 0;
		initiateMasterKeyRecovery1(userId, count);
	} else {
		window.location.href = "/home";
	}

});

function initiateMasterKeyRecovery1(userId, count) {
	modal1.style.display = "block";
	$('#verify').click(function(e) {
		var adminPassword = document.getElementById('admin1Password').value;
		validateAdmin1Passwords(userId, adminPassword, count);
	});
}

function validateAdmin1Passwords(userId, adminPassword, count) {
	if (adminPassword != null && adminPassword != "") {
		$.ajax({
			url: "/validateAdmin1Passwords",
			type: "POST",
			data: JSON.stringify({ userId: userId, adminPassword: adminPassword }),
			contentType: 'application/json',
			success: function() {
				$("#div2").show();
				$("#div1").hide();
				initiateMasterKeyRecovery2(userId, count);
				$("#admin1Password").val("");
			},
			error: function() {
				alert("Please Enter Correct Passwords.");
			}
		});
	} else {
		alert("Enter Admin1 Password");
	}
}

function initiateMasterKeyRecovery2(userId, count) {
	$('#verifyadmin').click(function(e) {
		var adminPassword = document.getElementById('admin2Password').value;
		count++;
		validateAdmin2Passwords(userId, adminPassword, count);
	});
}

function validateAdmin2Passwords(userId, adminPassword, count) {
	if (adminPassword != null && adminPassword != "") {
		$.ajax({
			url: "/validateAdmin2Passwords",
			type: "POST",
			data: JSON.stringify({ userId: userId, adminPassword: adminPassword }),
			contentType: 'application/json',
			success: function() {
				if (count == 2) {
					$("#label").show();
					$("#masterKey").show();
					$("#addKey").show();
					$("#recoverKey").hide();
					modal1.style.display = "none";
					$("#div2").hide();
					$("#div1").hide();
				} else {
					$("#div2").hide();
					$("#div1").show();
					initiateMasterKeyRecovery1(userId, count);
				}
				$("#admin2Password").val("");
			},
			error: function() {
				alert("A System must have exactly two admin users. Please make sure You have provided the correct passwords.");
			}
		});
	} else {
		alert("Enter Admin2 Password");
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
	let confirmPassword = document.getElementById('confirmPassword').value;
	
	if (newPassword != null && newPassword != "" && oldPassword != null && oldPassword != "" && confirmPassword != null && confirmPassword != "") {
		$.ajax({
			url: "/users/changeAdminPassword",
			type: 'PUT',
			data: JSON.stringify({ userId: userId, oldPassword: oldPassword, newPassword: newPassword, confirmPassword: confirmPassword }),
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

function updateServerStatus(userId, status) {
	$.ajax({
		type: "GET",
		url: "/startStopServer/" + userId + "/" + status,
		success: function(response) {
			console.log(response);
			if (status === "start") {
				localStorage.setItem("start", "true");
				$("#stop").show();   
				$("#start").hide();
				$("#home").show();
				$("#out").show();
				$("#changePass").show();
				$("#addMasterKey").show();
				$("#addLocation").show();
				$("#slog").show();
				$("#div").show();
				$("#klog").show();
				$("#tlog").show();              
                
            } else {
				localStorage.setItem("start", "false");
				$("#start").show();   
				$("#stop").hide();
				$("#home").hide();
				$("#out").hide();
				$("#changePass").hide();
				$("#addMasterKey").hide();
				$("#addLocation").hide();
				$("#slog").hide();
				$("#div").hide();
				$("#klog").hide();
				$("#tlog").hide(); 				       
            }
		},
		error: function(error) {
			console.log(error);
		}
	});
}

if (localStorage.getItem('start') == "false") {
	$("#start").show();   
	$("#stop").hide();
	$("#home").hide();
	$("#out").hide();
	$("#changePass").hide();
	$("#addMasterKey").hide();
	$("#addLocation").hide();
	$("#slog").hide();
	$("#div").hide();
	$("#klog").hide();
	$("#tlog").hide();      
}



