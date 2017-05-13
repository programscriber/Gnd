function validateForm() {
    var x = document.forms["search"]["mobile"].value;
    if (x == null || x == "") {
        alert("Mobile number should not be empty");   
        return false;
    }
    document.search.submit();  
//    if(x.trim().length<=10){
//    	return true;
//    }else{
//    	alert("Mobile number must be 10 digits");
//    	return false;
//    }
}
function resetOne(){
	
	$('#mobile').val('');
	return false;
}





/*
 * document.getElementById('mobile').addEventListener('keypress',
 * function(event) { if (event.keyCode == 13) { event.preventDefault(); } });
 */
/*$(document).ready(
		function() {
$('#restVal').on("click", function(event){
	alert("hai");
	   $('#searchfrm input[type="text"]').attr('value','');
	});

		});*/

/*$("#restVal").on("click", function(event){
	alert("hai");
    event.preventDefault();
    $('#search input').val('');
});*/




/*$(document).ready(function(){
    $('#restVal').click(function(){
    	$('#mobile').val('');
    });
});*/

$('#search').submit(function(event){
	if (event.which == 13)
	event.preventDefault();
});

$('#rsnsearch').submit(function(event){
	if (event.which == 13)
	event.preventDefault();
});
$('#branchwise').submit(function(event){
	if (event.which == 13)
	event.preventDefault();
});
$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#mobile").keypress(
					function(e) {
						
						if (e.which != 8 && e.which != 0 && e.which!=13
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});


$(function() {
	$("#dateTo").datepicker({
		maxDate : new Date()
	});

});


$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#product").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0
								&& ((e.which < 65) || (e.which > 90))
								&& ((e.which < 97) || (e.which > 122))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});
$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#acctNo").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0 && e.which!=13
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});

$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#rsn").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0 && e.which!=13
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});

$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#applNo").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0 && e.which!=13
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});

$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#bank").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0 && e.which!=13
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});

$(document).ready(
		function() {
			// called when key is pressed in textbox
			$("#branch").keypress(
					function(e) {
						// if the letter is not digit then display error and
						// don't type anything
						if (e.which != 8 && e.which != 0 && e.which!=13 
								&& ((e.which < 48) || (e.which > 57))) {
							// display error message
							$("#errmsg").html("Digits Only").show().fadeOut(
									"slow");
							return false;
						}
					});
		});










