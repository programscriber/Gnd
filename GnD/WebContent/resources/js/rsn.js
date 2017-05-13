function validateForm(){
	 var x = document.forms["rsnsearch"]["rsn"].value;
	    if (x == null || x == "") {
	        alert("RSN should not be empty");
	        return false;
	    }
	    document.rsnsearch.submit();  
//	    if (x.length == 10) {
//			return true;
//		} else {
//			alert("RSN number must be 10 digits");
//			return false;
//		}


}
function resetOne(){
	$('#rsn').val('');
	return false;
}