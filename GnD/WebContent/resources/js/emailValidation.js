function validateMail(rejectedemails, holdemails,approvedemails) {
		
		var result = true;		
		if(rejectedemails.length == 0 && holdemails.length == 0 && approvedemails.length == 0) {
			alert("Please enter mail address before submitting");
			return false;
		}
		
		if(rejectedemails.length > 0) {
			var email1 = rejectedemails.split(",");
			for(i = 0;i<email1.length;i++) {
				if(!emailvalidation(email1[i])) {
					result = false;
				}
			}
			
		}
		
		if(holdemails.length > 0) {
			var email2 = holdemails.split(",");
			for(i = 0;i<email2.length;i++) {
				if(!emailvalidation(email2[i])) {
					result = false;
				}
				
			}
		}
		
		if(approvedemails.length > 0) {
			var email3 = approvedemails.split(",");
			for(i = 0;i<email3.length;i++) {
				if(!emailvalidation(email3[i])) {
					result = false;
				}
			}
		}
		
		return result;
		
		
	}
	
	function emailvalidation(email) {
		
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))  
		  {  
		    return true  
		  }  
		    alert(email+" is not a valid mail address");
		    return false 
		
	}
