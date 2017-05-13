function validateForm() {
	var bank = document.forms["backacc"]["bank"].value;
	var acctNo = document.forms["backacc"]["acctNo"].value;

	if ((bank == null || bank == "") && (acctNo == null || acctNo == "")) {
		alert("fields should not be empty");
		return false;
	}
	if (bank == null || bank == "") {
		alert("bank should not be empty");
		return false;
	}

	if (acctNo == null || acctNo == "") {
		alert("acctNo should not be empty");
		return false;
	}
	 document.backacc.submit();  

//	if ((acctNo.length == 17)) {
//		return true;
//	} else {
//		alert("Account number must be 17 digits");
//		return false;
//	}

}
$('#backacc').submit(function(event){
	if (event.which == 13)
	event.preventDefault();
});


function resetOne(){
	
	$('#acctNo').val('');
	$('#bankid1').val('');
	
	return false;
}
