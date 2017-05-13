<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html
	class=" js backgroundsize cssanimations csstransitions pointerevents"
	lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">

<title>G & D</title>
<link href="<c:url value='/resources/css/blazer.css'/>"
	media="screen, projection" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/css/style.css'/>"
	media="screen, projection" rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/blazer.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/css/jquery-ui.css'/>">
<script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
<script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>


<link href="<c:url value='/resources/css/infragistics.theme.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/infragistics.css'/>"
	rel="stylesheet" />


<!-- Ignite UI Required Combined JavaScript Files -->
<script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
<script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>

<style type="text/css">     
    select {
        width:200px;
    }
    
    #loading-div-background{
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    background: #fff;
    width: 100%;
    height: 100%;
}

#loading-div{
    width: 300px;
    height: 150px;
    background-color: #fff;
    border: 5px solid #1468b3;
    text-align: center;
    color: #202020;
    position: absolute;
    left: 50%;
    top: 50%;
    margin-left: -150px;
    margin-top: -100px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    behavior: url("/css/pie/PIE.htc"); /* HANDLES IE */
}
</style>
</head>



<body>

	<style>
#checkboxSelectCombo, #checkboxSelectCombo:hover {
	/*background-color: #f7f8f8 !important;*/
	border: 1px solid #9db5cd !important;
}

.ui-igcombo-buttonicon, .ui-igcombo-clearicon {
	margin-left: 3px;
	margin-top: -7px;
	position: absolute;
	top: 50%;
}

.ui-icon-triangle-1-s {
	background-position: 0;
}

.ui-icon {
	background-position: 0;
	height: 16px;
	width: 16px;
}

#tableid {
	width: 100%;
	max-width: 100%;
	overflow-x: scroll;
	overflow-y: scroll;
}

.ui-igcombo-clear {
	display: none !important;
}
</style>


	<div class="page-header" style="position: fixed width:100%">
		<div class="page-header-wrapper container">
			<div class="main-logo">Giesecke &amp; Devrient</div>
				<div class="tab-menu dark">
		 	
                <ul>
                <li id="icons" ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                <li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    	<li id="icons" class="selected"><a href="<c:url value='/qcprocess'/>">QC</a></li>
					<li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>
					 <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
				</ul>
                	</div>  
				 <c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
       </div>
		 
                	</div>  

	</div>
	<div class="main" style="">
		<div class="container main-conten">
			<div class="left-content">
				<div class="demo-left-nav" id="left-nav">
					<div class="navigation">
						<ul>
							<c:if test="${username eq 'datagen'}">
<!-- 							<li><a href="/GnD/process">Process Files</a></li> -->
							<li class="selected"><a href="/GnD/reprocess">Reprocess File</a></li>
<!-- 							<li><a href="/GnD/qcsummary">QC summary</a></li> -->
							<li><a href="/GnD/pincoveringnote">Pin Covering Note(New AUF)</a></li>
						</c:if>
						</ul>
					</div>
				</div>
			</div>
			<div class="right-content record">
				<c:if test="${msg ne null }">
						<c:out value = "${msg}"/>
				</c:if>
				<h2 class="blue-text">Reprocess Files</h2>
				<form id="reprocess" method="post" >
					<div class="row right-con">

								
										<table><tr>
												<td>Core File Received Date</td>
												<td>Core File Name</td>
												<td>Status</td>
												<td>Remarks</td></tr>
											<tr>
												<td><input type="text" id="receiveddate"/> 	</td>						
												<td><select id="filename" name="fileid"></select></td>
												<td><select id="status" name="status">
														<option value="Reject">Rejected</option>
														<option value="Hold">Hold</option>
														<option value="Both">Both</option>
													</select>
												</td> 
												<td><textarea name="remark" id="remark"></textarea></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td><input type="submit" value="Reprocess" class="btn btn-only-img btn-only-img1 btn-s"/></td>
											</tr>
 										</table>
								


						</div>
				</form>
				
						<div id="txt"></div>
						<div id="wait" style="display:none;width:69px;height:89px;position:absolute;top:50%;left:50%;padding:2px;"><img src="<c:url value='/resources/img/loading.gif'/>" width="90" height="90" /></div>

					
	
			</div>
			<!--</div>-->
		</div>
	</div>
	<div class="clear"></div>
	<div id="footer" class="page-footer">
		<div class="container">
			<div class="footer-columns">
				<div class="footer-left-column">
					<div class="footer-version">
						<strong>Web Tracking Tool</strong> Version 1.0.14
					</div>
					<ul class="footer-links">
						<li><a href="#">Contact</a></li>
						<li><a href="#">Site Terms</a></li>
					</ul>
				</div>
				<div class="footer-right-column">
					<div class="footer-logo"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
     
     function formSubmit() {
 		document.getElementById("logoutForm").submit();
 	}
             	
     $(document).ajaxStart(function(){
	        $("#wait").css("display", "block");
	    });
		
		$(document).ajaxComplete(function(){
	        $("#wait").css("display", "none");
	    });
	$("#receiveddate").datepicker({

	    onSelect: function(date, instance) {

	        $.ajax
	        ({
	              type: "Post",
	              url:"/GnD/getfilesbydate?receiveddate="+$("#receiveddate").val(),
	              success: function(data)
	              {		
	            	  var options = '';
	            	  if(data.length == 0) {
	            		  document.getElementById("filename").value="";
	            		  alert("No file processed on selected date");	            		  
	            		  return false;
	            	  }
  		            for (var x = 0; x < data.length; x++) {
  		                options += '<option value="' + data[x]['id'] + '">' + data[x]['filename'] + '</option>';
  		            }
  		            $('#filename').html(options);
	       
	              },
	              error: function(){                        
	                     alert('Error while request..');
	                 }
	         });  
	     }
	});
	
	
	function checkRemarks(fromid) {
		
		var remark =	document.getElementById("remark").value;
		var receiveddate =	document.getElementById("receiveddate").value;
		var filename = document.getElementById("filename").value;
		if(receiveddate.length==0) {
			 alert("Please select received date");
			return false;				 
		 }
		if(filename == "") {
			alert("No files are present to be reprocess");
			return false;
		}
		if(remark.length==0) {
			 alert("Please enter remarks ");
			return false;				 
		 }
	}
	$(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
	
	$(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
	
	$("#reprocess").submit(function(e) {
		var remark =	document.getElementById("remark").value;
		var receiveddate =	document.getElementById("receiveddate").value;
		var filename = document.getElementById("filename").value;
		if(receiveddate.length==0) {
			 alert("Please select received date");
			return false;				 
		 }
		if(filename == "") {
			alert("No files are present to be reprocess");
			return false;
		}
		if(remark.length==0) {
			 alert("Please enter remarks ");
			return false;				 
		 }
 	       
	    $.ajax({
	           type: "Post",
	           url: "/GnD/reprocessfile?fileid="+filename+"&status="+$("#status").val()+"&remark="+remark,

	           success: function(data)
	           {
	        	   alert(data.result);
	               location.reload(true);
	               // show response from the php script.
	           },
	    		error: function(){                        
           	 		alert('Error while request..');
       		 	}
	         });

	    e.preventDefault(); // avoid to execute the actual submit of the form.
	});
	
// 	$(document).ready(function (){
//         $("#loading-div-background").css({ opacity: 1.0 });
//     });

//     function ShowProgressAnimation(){
//         $("#loading-div-background").show();
//     }

	</script>

</body>
</html>