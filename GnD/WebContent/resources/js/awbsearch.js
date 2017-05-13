


function validateForm(){
	
	 var card = document.forms["awbsearch"]["cardawb"].value;
	
	    if (card == null || card == "") {
	        alert("card awb should not be empty");
	        return false;
	    } 
	    document.awbsearch.submit();  
//	    if(card.length==15){
//	    	return true;
//	    }else{
//	    	alert("card awb number must be 15 digits");
//	    	return false;
//	    }
	    
}

$("#awbsearch").submit(function(event){
	if (event.which == 13)
	event.preventDefault();
});

function resetOne(){
	$('#cardawb').val('');
	return false;
}

$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#cardawb").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0 && e.which!=13
								&& ((e.which < 48) || (e.which > 57))
								&& ((e.which < 65) || (e.which > 90))
								&& ((e.which < 97) || (e.which > 122))) {
							// display error message
							$("#carderrmsg").html("AlphaNumeric").show().fadeOut(
									"slow");
							return false;
						}
					});
		});

