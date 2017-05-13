$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#pinawb").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0&& e.which!=13 
								&& ((e.which < 48) || (e.which > 57))
								&& ((e.which < 65) || (e.which > 90))
								&& ((e.which < 97) || (e.which > 122))) {
							// display error message
							$("#pinerrmsg").html("Alphanumeric").show().fadeOut(
									"slow");
							return false;
						}
					});
		});
function resetOne(){
	
	$('#pinawb').val('');
	return false;
}


$('#awbsearch1').submit(function(event){
	if (event.which == 13)
	event.preventDefault();
});

function validateForm(){
	
	   var pin=document.forms["awbsearch1"]["pinawb"].value;
	   
	    if (pin == null || pin == "") {
	        alert("pin awb should not be empty");
	        return false;
	    }
	    document.awbsearch1.submit(); 
//	    if(pin.length<=13){
//	    	return true;
//	    }else{
//	    	alert("pin awb number must be 13 digits");
//	    	return false;
//	    }
}
