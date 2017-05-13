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
	src="<c:url value='/resources/js/jquery-datepicker-1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-datepicker-2.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-datepicker-3.js'/>"></script>
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
<script type="text/javascript">
$(function() {
    $( "#dateFrom" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
  });
$(function() {
    $( "#dateFrom" ).datepicker({maxDate: new Date(), minDate: -1095});
    $( "#dateFrom" ).datepicker("setDate", new Date());
    
  
  });
</script>
<style type="text/css">
select {
	width: 200px;
}
font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
font-size: 62.5%;
</style>
</head>
<body>
<div class="page-header" style="position: fixed width:100%">
		<div class="page-header-wrapper container">
			<div class="main-logo">Giesecke &amp; Devrient</div>
			<div class="tab-menu dark">

				<ul>
					<li id="icons"><a href="<c:url value='/loginuser/home'/>">Home</a></li>
					<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
					<c:if test="${username eq 'datagen'}">
						
						<li id="icons" class="selected"><a
							href="<c:url value='/qcsummary'/>">QC</a></li>
					</c:if>
					<li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>
					<li id="prototype"><a href="javascript:formSubmit()">
							Logout</a>
				</ul>
			</div>
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>

	</div>
<div class="container main-conten">
			<div class="left-content">
				<div class="demo-left-nav" id="left-nav">
					<div class="navigation">
						<ul>
<!-- 							<li><a href="/GnD/process">Process Files</a></li> -->
							<li><a href="/GnD/reprocess">Reprocess File</a></li>
<!-- 							<li><a href="/GnD/qcsummary">QC summary</a></li> -->
							<li class="selected"> <a href="/GnD/pincoveringnote">Pin Covering Note(New AUF)</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row right-conqc">
				<h2 class="blue-text">Pin Assign & PDF Generation</h2>
				<form id="pinconveringnote" method="post">
						<table>
						<tr>
						<th align="left">select Bank</th>
						<th align="left">QC date</th>
						<th><input id = "assignAWB" type= "checkbox" name ="assignPinAWB" checked />AssignNewAWB </th>
						</tr>
							<tr>
								<td>
								<select id="bankid" name="bankId">
									<c:forEach var="critMasBankVal" items="${MasBank}">
										<option value="${critMasBankVal.bankId}">${critMasBankVal.shortCode}</option>
									</c:forEach>
								</select>
								</td>
							<td><input type="text" id="dateFrom" name="dateFrom"
								class="text-field text-field-with-addon" /> 
							</td>
							
							<td>
							<input type="submit" class="btn btn-l btn-blue" value ="SUBMIT" /> 
							</td>
							</tr>
							
						</table>
				
				</form>
				
				<div id="txt"></div>
						<div id="wait" style="display:none;width:69px;height:89px;position:absolute;top:50%;left:50%;padding:2px;"><img src="<c:url value='/resources/img/loading.gif'/>" width="90" height="90" /></div>
				
					
				<div class ="table-header">
					<c:choose>
						<c:when test="${msg ne null }">
							<h3>
								<center>
									<c:out value="${msg}" />
								</center>
							</h3>
						</c:when>
						<c:otherwise>
							
									<c:if test="${listCoreSumBO.size() > 0}">
								<h4>
									Search Results :
									<c:out value="${listCoreSumBO.size()}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button   id="tablelist" class="btn btn-l btn-blue " >Send Email </button>
										
<!--                         <h3>Search Results : 14 Records</h3> -->
                        <button id="downloadclick" class="btn btn-only-img btn-only-img1 btn-s" type="button">
                            <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print">
                        </button>
                    
								</h4>
						<table id="corefileSummary" class="table">
									<thead>
										<tr>
											<th>core_file_name</th>
											<th>QC_Output_Date</th>
											<th>LinkIndicator</th>
											<th>File_name</th>
											<th>File_count</th>
											<th>Record_Counts</th>
										</tr>
									</thead>
									<tbody>
										<%
											int rowCount = 0;
										%>
										<c:forEach var="listValue" items="${listCoreSumBO}">

											<tr>
												<td>${listValue.corefileName}</td>
												<td>${listValue.date}</td>
												<td>${listValue.linkIndicator}</td>
												<td>${listValue.fileName}</td>
												<td>${listValue.fileCount}</td>
												<td>${listValue.recordCount}</td>
										
												<%
													rowCount++;
												%>
											
										</c:forEach>

									</tbody>
								</table>
						
								<table id="recordevent" class="table">
								
								</table>
					</c:if>
						</c:otherwise>
					</c:choose>
				</div>
				
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
	
	$("#pinconveringnote").submit(function(e) {
		var receiveddate =	document.getElementById("dateFrom").value;
		var awbcheck = 0;
		var generatedpin = true;
		if($("#assignAWB").prop("checked") == true) {
			awbcheck = 1;
			
		}
		if(receiveddate.length==0) {
			 alert("Please select received date");
			return false;				 
		 }
// 		if(awbcheck == 1) {
			 $.ajax({
		           type: "Post",
				   url : "/GnD/assignawb?bankid="+$("#bankid").val()+"&qcdate="+receiveddate+"&isawbassign="+awbcheck,
				   success: function(data)
	 	           {
	 	        	   alert(data.result);
	 	           },
		    		error: function(){   
		    			generatedpin = flase;
	           	 		alert('Error while request processing ..');
	       		 	}
			});	
// 		}
// 		if(generatedpin == true) {
// 			alert("In pin generation");
// 	    $.ajax({
// 	           type: "Post",
// 			   url : "/GnD/pingeneration?bankid="+$("#bankid").val()+"&qcdate="+receiveddate,
// 	           success: function(data)
// 	           {
// 	        	   alert(data.result);
// 	           },
// 	    		error: function(){  
	    			
//            	 		alert('Error while request..');
//        		 	}
// 	         });
// 		 }
	    e.preventDefault(); // avoid to execute the actual submit of the form.
		});	
	

		$(document).ready(function() {
			$("#loading-div-background").css({
				opacity : 1.0
			});
		});

		function ShowProgressAnimation() {
			$("#loading-div-background").show();
		}
	</script>

</body>
</html>