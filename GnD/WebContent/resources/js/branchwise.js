$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#branch").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0  && e.which!=13
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});

$(function() {
	$("#dateFrom").datepicker({
		maxDate : new Date(),
		minDate: -1095,
		 onSelect: function(event) {
			 if (event.which == 13)
					event.preventDefault();
	    }
	});
});


function resetOne(){
	$('#dateFrom').val('');
	$('#branch').val('');
    $('#bank').val('');
	return false;
}

function validateForm() {
    var branch = document.forms["branchwise"]["branch"].value;
    var dateFrom = document.forms["branchwise"]["dateFrom"].value;
    var bank = document.forms["branchwise"]["bank"].value;
    if ((branch == null || branch == "")&&(dateFrom == null || dateFrom == "")&&(bank == null || bank == "") ) {
        alert("fields should not be empty");
        return false;
    }
    if((bank == null || bank == "")){
    	alert("bank should not be empty");
        return false;
    }
    if (branch == null || branch == "") {
        alert("Branch should not be empty");
        return false;
    }
    if (dateFrom == null || dateFrom == "") {
        alert("Date should not be empty");
        return false;
    }
    document.branchwise.submit();
}
//    if(branch.length==6){
//    	return true;
//    }else{
//    	alert("Branch number must be 6 digits");
//    	return false;
//    }



