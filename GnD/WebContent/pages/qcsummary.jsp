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
<script src="<c:url value='/resources/js/jquery.tabletojson.js'/>"></script>

<%-- <script src="<c:url value='/resources/js/moment.js'/>"></script> --%>
<link href="<c:url value='/resources/css/infragistics.theme.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/infragistics.css'/>"
	rel="stylesheet" />


<!-- Ignite UI Required Combined JavaScript Files -->
<script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
<script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>

<style type="text/css">
select {
	width: 200px;
}

#loading-div-background {
	display: none;
	position: fixed;
	top: 0;
	left: 0;
	background: #fff;
	width: 100%;
	height: 100%;
}

#loading-div {
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


	<div class="page-header" >
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
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>

	</div>

	
	<div class="main" style="">
		<div class="container main-conten">
			<div class="left-content">
				<div class="demo-left-nav" id="left-nav">
					<div class="navigation">
						<ul>
<!-- 							<li><a href="/GnD/process">Process Files</a></li> -->
							<li><a href="/GnD/reprocess">Reprocess Files</a></li>
							<li class="selected"><a href="/GnD/qcsummary">QC summary</a></li>
							<li><a href="/GnD/pincoveringnote">Pin Covering Note(New AUF)</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="right-content record">
				<h2 class="blue-text">QC Summary</h2>
				<form id="reprocess" action="/GnD/getqcSummary" method="post" onsubmit=" return validate()">
						<table>
						<tr>
						<th align="left">select Bank</th>
						<th align="left">Core file date</th>
						<th></th>
						</tr>
							<tr>
								<td>
								<select id="bankid" name="bankId">
									<option value="${bank}">${bankLabel}</option>
									<c:forEach var="critMasBankVal" items="${MasBank}">
										<option value="${critMasBankVal.bankId}">${critMasBankVal.shortCode}</option>
									</c:forEach>
								</select>
								</td>
							<td><input type="text" id="dateFrom" name="date"
								class="text-field text-field-with-addon" value="${dateFrom}"/> <!-- 			<input type=hidden id="hiddateFrom"  data-role="datebox" /> -->
							</td>
							<td>
							<input type="submit" class="btn btn-l btn-blue" /> 
							</td>
							</tr>
							
						</table>
				
				</form>
				
				<div id="txt"></div>
						<div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src="<c:url value='/resources/img/icons/demo_wait_b.gif'/>" width="90" height="90" /><br>Loading..</div>
				
					
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
									<input type="hidden" id ="mapData" value="${listCoreSumBO}"> 
									<c:if test="${listCoreSumBO.Embossa_Summary_Report.size() > 0}">
								<h4>
									Search Results for Embossa Summary :
									<c:out value="${listCoreSumBO.Embossa_Summary_Report.size()}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
											<th>Record_Counts</th>
										</tr>
									</thead>
									<tbody>
										<%
											int rowCount = 0;
										%>
										<c:forEach var="listValue" items="${listCoreSumBO.Embossa_Summary_Report}">

											<tr>
												<td>${listValue.corefileName}</td>
												<td>${listValue.date}</td>
												<td>${listValue.linkIndicator}</td>
												<td>${listValue.fileName}</td>
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
					<br><br>
					<c:if test="${listCoreSumBO.Embossa_Summary_Report.size() > 0}">
								<h4>
									Search Results for Pin Printing :
									<c:out value="${listCoreSumBO.Embossa_Summary_Report.size()}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    
								</h4>
						<table id="corefileSummary" class="table">
									<thead>
										<tr>
											<th>core_file_name</th>
											<th>QC_Output_Date</th>
											<th>LinkIndicator</th>
											<th>File_count</th>
											<th>Record_Counts</th>
										</tr>
									</thead>
									<tbody>
										<%
											int rowCount = 0;
										%>
										<c:forEach var="listValue" items="${listCoreSumBO.Pin_Print_Summary_Report}">

											<tr>
												<td>${listValue.corefileName}</td>
												<td>${listValue.date}</td>
												<td>${listValue.linkIndicator}</td>
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
	$(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
	
	$(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
	
	$("#tablelist").click(function() {
		var table = $('#corefileSummary').tableToJSON();
		var r = confirm("Do you really want to send mail?");
		if(r==true){
		 $.ajax({
			 contentType: 'application/json; charset=utf-8',
			 dataType: 'json',
			 type: 'POST',
			 url: '/GnD/sendemail?bankVal='+$('#bankid option:selected').text(),
			 data: JSON.stringify(table),
			 success: function(data){

				alert("Email Sent Successfully");

				 var options = '<tr><th>Event Description</th><th>Event Date</th></tr>';
		            for (var x = 0; x < data.length; x++) {
		            	var safe = JSON.parse(data[x]['eventDate']);
		            	
		                options += '<tr><td>' + data[x]['description'] + '</td><td>'+ data[x]['eventDateStr'] + '</td><tr>';
		            }
		            $('#recordevent').html(options);
			},
			  error: function(){						
				alert('Error while request..');
			} 
			 });
		}else{
			alert("you have cancelld a request");
		}
	});
	
	
	$("#downloadclick").click(function() {
		var mapinfo = $('#mapData').val();
// 		var table = $('#corefileSummary').tableToJSON();
		var r = confirm("Click OK to download the file");
		if(r==true){
		 $.ajax({
			 contentType: 'application/json; charset=utf-8',
			 dataType: 'json',
			 type: 'POST',
			 url: '/GnD/download?mapdata='+mapinfo,
			 cache: false, // Force requested pages not to be cached by the browser
			 processData: false, // Avoid making query string instead of JSON
			 data:  JSON.stringify(mapinfo),
			 success: function(data){
				alert(data);
				 window.location.href = '/GnD/corefileSuma?filepath='+data;
			},
			  error: function(){						
				alert('Error while request..');
			} 
			 });
		}else{
			alert("you have cancelld a request");
		}
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
		$("#dateFrom").datepicker("setDate", new Date());
	});
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}

		
		$(document).ready(function() {
			$("#loading-div-background").css({
				opacity : 1.0
			});
		});

		function ShowProgressAnimation() {
			$("#loading-div-background").show();
		}
		
		
		function validate() {
			var bankInfo = document.getElementById("bankid").value;
			var fromdate =	document.getElementById("dateFrom").value;
			if(bankInfo == '' || fromdate =='') {
				alert("Please select bank and qc date");
				return false;
			}
			return true;
			
		}
	</script>

</body>
</html>