/**
 * 
 */
function validate() {
		var radio1 = document.getElementById("fn").checked;
		var radio2 = document.getElementById("range").checked;
		var x = document.forms["awbform"]["from"].value;
		var y = document.forms["awbform"]["to"].value;		
		
		if(radio1 == false && radio2 == false) {			
			alert("please select any one option");
			return false;
		}
		else {
			if(radio1 == true) {			
				if(document.getElementById("xls").value == "") {
					alert("Please upload file");
					document.getElementById('xls').focus();
					return false;
				}				
				else {
					
					var servicecheck = service();
					if(servicecheck) {
						document.awbform.action="/GnD/upload/importawbmaster";
 						return true;
					
					}
					else { 
						return false;
					}
					
				}
					
			}
			else if(radio2 == true && (x == "" || y == "")) {				

				alert("please enter from and to values");
				return false;
							
			}
			else {
				var servicecheck = service();
				if(servicecheck) {
					document.awbform.action="/GnD/upload/importawbrange";
					return true;
				}
				else { 
					return false;
				}
			}
		}
	}
		function service() {
		var service = document.forms.awbform.service;
		for (var i=0; i < service.length; i++) {
		if (service[i].checked)
			return true;
		}
			alert("Please select any one service");
			return false;
		}