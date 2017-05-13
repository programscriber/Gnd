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
<script>
  $(function() {
    $( "#fromdate" ).datepicker({maxDate: new Date(), minDate: -1095});
    $( "#fromdate" ).datepicker("setDate", new Date());
  
  });
  $(function() {
	    $( "#todate" ).datepicker({maxDate: new Date(), minDate: -1095});
	    $( "#todate" ).datepicker("setDate", new Date());
	  });
  </script>
    </head>



    <body>
    
    <%! java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy"); 
    	String today = sdf.format(new java.util.Date());
    %>
    
        <style>
            .right-content{
                /*text-align: center;*/
                margin-left: auto;
                margin-right: auto;
                left: 18%;
                float: none;
                border-left: medium transparent;
            }
            .table-header {
                width: 100%;
            }
            .col-md-4{
                width: 33.333%;
            }
        </style>

        <div class="page-header">
            <div class="page-header-wrapper container">
                <div class="main-logo">
                    Giesecke &amp; Devrient
                </div>
                <div class="tab-menu dark">
                    <ul>
                    	<li id="icons"  ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%-- 						 <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li> --%>
<!--                         <li id="prototype" class="selected"><a href="/GnD/search/getReport">Reports</a></li>                         -->
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						 <!-- <li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>-->
					  </c:if>  
					   <c:if test="${username eq 'shopfloor'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>
					  <c:if test="${username eq 'warehouse'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if>  
					  <c:if test="${username eq 'rto'}">
						  <li id="prototype"><a href="/GnD/shopfloor/scanbyRSNtorto">RTO</a></li>
						
					  </c:if>
					  <c:if test="${username eq 'datagen'}">
                    	<li id="icons" ><a href="<c:url value='/qcprocess'/>">QC</a></li>
					</c:if>			  
					     <li id="prototype"  class="selected"><a href="/GnD/search/getReport">Reports</a></li> 
					      <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
					   
                    </ul>
                </div>                 
            </div>
        </div>
        
        <div class="main" style="">
            <div class="container main-conten">  
             <!--    <div class="left-content">
                    <div class="demo-left-nav" id="left-nav">
                        <div class="navigation">
                            <ul>
                                <li>
                                    <a href="/GnD/reports.jsp" class="selected">Reports</a>
                                </li>                               
                            </ul>
                        </div>
                    </div>
                </div>  -->
         		<div class="right-content record">        
        			<c:if test="${msg ne null }">
					  	<c:out value = "${msg}"/>
					</c:if> <br>
        			<h2 class="blue-text">Reports</h2><br><br>
        				<br>
        				 From Date : <input type="text" id="fromdate" name="fromdate" />&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				 To Date : 	<input type="text" id="todate" name="todate" />&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				 BANK : <input type="hidden" id="banklist" value="${bankList.size()}"/>
        				 <select id="banks" name="banks">
<!--         				 	<option value="ALL">ALL</option> -->
<%--         				 	<%! int bankSize = 1; %> --%>
        				 <c:forEach var="bankList" items="${bankList}">
        				 	<option id ="bankId" value="${bankList.bankId}^${bankList.shortCode}">${bankList.shortCode}</option>
        				 </c:forEach>
        				 
        				 </select>
        				 <br><br>
        				 
         				<c:forEach var="listValue" items="${reports}">      
         				<%! int count = 1; %>   					
        					<form id= "reportform<%=count++ %>" action="/GnD/search/generateReports?reportname=${listValue.reportName}&sourcePath=${listValue.sourcePath}&destinationPath=${listValue.destinationPath}&fromdate=" onsubmit="return checkDateReport(this.id,'reportformaction'+<%=count %>)" method="post">
						<input type="submit" style="height:40px;width:250px" class="btn btn-l btn-blue" value="${listValue.reportName}"/><br><br><br>
						<input type="hidden" id="reportformaction<%=count %>" value="/GnD/search/generateReports?reportname=${listValue.reportName}&sourcePath=${listValue.sourcePath}&destinationPath=${listValue.destinationPath}&fromdate="/>
        					</form>
        					
        				</c:forEach> 
        				<c:if test="${username eq 'datagen'}">
        				<form id="customerreport" action="/GnD/search/generateCustomerReport?fromdate=" onSubmit="return checkDate(this.id)" method="post">
        					<input type="submit" style="height:40px;width:250px" class="btn btn-l btn-blue" value="CustomerReports"/><br><br><br>
        				</form>  
<!--         				<form id="corefilesummery" action="/GnD/search/generateCoreFileSummaryReport?fromdate=" onSubmit="return checkDate(this.id)" method="post"> -->
<!--         					<input type="submit" class="btn btn-l btn-blue" value="CoreFileSummary"/><br><br><br> -->
<!--         				</form>			 -->
<!-- 						<form id="pinmailer" action="/GnD/search/generatePinMailerReport?fromdate=" onSubmit="return checkDate(this.id)" method="post"> -->
<!--         					<input type="submit" style="height:40px;width:250px" class="btn btn-l btn-blue" value="PinMailerReport"/> -->
<!--         					<span class="blue-text">VIP</span> -->
<!--         					<select name="vip"> -->
<!-- 								<option value="1">Yes</option> -->
<!-- 								<option value="0" selected>No</option>  -->
<!-- 							</select> -->
<!--         				</form>	 -->
        				</c:if>
        		</div>
       		 </div>
       		 <c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
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
                            <li><a href="#">Privacy</a></li>
                        </ul>
                    </div>
                    <div class="footer-right-column">
                        <div class="footer-logo">
                        </div>
                    </div>
                </div>
            </div>
        </div> 
        
        <script>
        
 		function checkDateReport(formid,formactionid) {
        	
 			//alert("Inside checkDateTest");
            var fromdate =	document.getElementById("fromdate").value;
        	var todate =	document.getElementById("todate").value;
        	var banks = document.getElementById("banks").value;
        	var bankSize = document.getElementById("banklist").value;
        	//alert("Before retrieving the action data from input hidden" + formactionid);
        	var url = document.getElementById(formactionid).value;
        	//alert("Action value : "+url);
        	// var url = document.getElementById(formid).action;
        	if(fromdate == '' || todate == '') {
        		alert("Please select from and to dates");
        		return false;
        	}
//         	if(banks == "ALL") {
//         		for (var i=1; i <= bankSize; i++) {
//         			bank += document.getElementById("bankId").value;
//         			if(i == bankSize) {
//         				bank += ')';
//         			}
//         			else {
//         				bank += ',';
//         			}
//         		}
//         	}
//         	else {
//         		 bank += banks+')';
//         	}
			
        	url = url+fromdate+"&todate="+todate+"&bankId="+banks;
        	//alert(url);
			document.getElementById(formid).action = url;
// 			document.getElementById(formid).submit();
// 			document.getElementById(formid).reset();
// 			url='';
		  	
        	return true;
        }
        
//         ($("#this.")
        function checkDate(formid) {
        	
            var fromdate =	document.getElementById("fromdate").value;
        	var todate =	document.getElementById("todate").value;
        	var banks = document.getElementById("banks").value;
        	var bankSize = document.getElementById("banklist").value;
        	 var url = document.getElementById(formid).action;
        	if(fromdate == '' || todate == '') {
        		alert("Please select from and to dates");
        		return false;
        	}
//         	if(banks == "ALL") {
//         		for (var i=1; i <= bankSize; i++) {
//         			bank += document.getElementById("bankId").value;
//         			if(i == bankSize) {
//         				bank += ')';
//         			}
//         			else {
//         				bank += ',';
//         			}
//         		}
//         	}
//         	else {
//         		 bank += banks+')';
//         	}
			
        	url = url+fromdate+"&todate="+todate+"&bankId="+banks.substring(0, 1);
			document.getElementById(formid).action = url;
// 			document.getElementById(formid).submit();
// 			document.getElementById(formid).reset();
// 			url='';
		  	
        	return true;
        }
        
        function checkDateAndVIP(formid) {
            var fromdate =	document.getElementById("fromdate").value;
        	var todate =	document.getElementById("todate").value;
        	var vip = document.getElementById("vip").value;
        	var url = document.getElementById(formid).action;
        	if(fromdate == '' || todate == '') {
        		alert("Please select from and to dates");
        		return false;
        	}
        	url = url+fromdate+"&todate="+todate+"&vip="+vip;
        	alert(url);
			document.getElementById(formid).action = url;
        	return true;
        }
        function formSubmit() {
			document.getElementById("logoutForm").submit();
	  }
        
        function reportPath() {
        	window.document.location.href = 'reportlocation.html';        	
        }

        </script> 
        
        </body>
        </html>
