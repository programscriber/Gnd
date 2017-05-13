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
                    	<li id="icons"  ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype" class="selected"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%-- 						 <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li> --%>
					</c:if>					  
					   <c:if test="${username eq 'shopfloor'}">
						  <li id="prototype"  class="selected"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					  <c:if test="${username eq 'warehouse'}">
						  <li id="prototype"  class="selected"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						   <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if> 
					   <c:if test="${username eq 'rto'}">
						  <li id="prototype"  class="selected"><a href="/GnD/shopfloor/home">RTO</a></li>
					  </c:if> 
                        <li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>                        
					      <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
					   
                    </ul>
			</div>
		</div>
	</div>
	<div class="main" style="">
		<div class="container main-conten">
			<div class="left-content">
				<div class="demo-left-nav" id="left-nav">
					<div class="navigation">
						<ul>
							<c:if test="${username eq 'admin'}">
										<li id="prototype" ><a href="/GnD/shopfloor/carddispatch">Card Production</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/productionUpload">UploadRSNList</a></li>
									 
										<li id="prototype"><a href="/GnD/shopfloor/carddilivery">card Delivery</a></li>
<!-- 										<li id="prototype" ><a href="/GnD/shopfloor/cardreturned">card Returned</a></li> -->
										<li id="prototype" ><a href="/GnD/shopfloor/cardredispatch">card Redispatch</a></li>
										<li id="prototype" ><a href="/GnD/shopfloor/carddestroy">Card Destroy</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/carddeliverbyAWB">Card Delivery By AWB</a></li>
										<!--<li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>-->
										<li id="prototype" ><a href="/GnD/shopfloor/changeAWB">Change AWB</a></li>
										<li id="prototype"  ><a href="/GnD/shopfloor/scanbyRSNtorto">RTO Scan by RSN</a></li>
										<li id="prototype" ><a href="/GnD/shopfloor/scanbyAWBtorto">RTO Scan By AWB</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/changeRTOAWB">Change RTO AWB</a></li>
									
									<li id="prototype" ><a href="/GnD/shopfloor/pindispatch">Pin Dispatch</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/pindelivery">Pin Delivery</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pinreturned">Pin Returned</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/pinredispatch">Pin Redispatch</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/pindestroy">Pin Destroy</a></li>
							</c:if>
									<c:if test="${username eq 'shopfloor'}">
										<li id="prototype"><a href="/GnD/shopfloor/carddispatch">Card Production</a></li>
										<li id="prototype" ><a href="/GnD/shopfloor/productionUpload">UploadRSNList</a></li>
									</c:if>
									 <c:if test="${username eq 'warehouse'}">
										<li id="prototype"><a href="/GnD/shopfloor/carddilivery">card Delivery</a></li>
										<!--<li id="prototype"><a href="/GnD/shopfloor/cardreturned">card Returned</a></li>-->
										<li id="prototype"><a href="/GnD/shopfloor/cardredispatch">card Redispatch</a></li>
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/carddestroy">Card Destroy</a></li> -->
										<li id="prototype"><a href="/GnD/shopfloor/carddeliverbyAWB">Card Delivery By AWB</a></li>
<!-- 										<!--<li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>--> 
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/changeAWB">Change AWB</a></li> -->
										
									</c:if>
									<c:if test="${username eq 'rto'}">
										<li id="prototype"  ><a href="/GnD/shopfloor/scanbyRSNtorto">RTO Scan by RSN</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/scanbyAWBtorto">RTO Scan By AWB</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/changeRTOAWB">Change RTO AWB</a></li>
									</c:if>
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pindispatch">Pin Dispatch</a></li> -->
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pindelivery">Pin Delivery</a></li> -->
<!-- 									<li id="prototype" ><a href="/GnD/shopfloor/pinreturned">Pin Returned</a></li> -->
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pinredispatch">Pin Redispatch</a></li> -->
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pindestroy">Pin Destroy</a></li> -->

						</ul>
					</div>
				</div>
				<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
			</div>
			
		<div class="clear"></div>
		<br />
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

			function checkRsn() {
				var rsn = document.getElementById("rsn").value;
				var pathcardDelive = document.getElementById("rsnbuttonacn").href;
				if (rsn.length == 0) {
					alert("pls enter the rsn number");
					return false;
				}
				document.getElementById("rsnbuttonacn").href = pathcardDelive
						+ rsn;

			}
		</script>
</body>
</html>