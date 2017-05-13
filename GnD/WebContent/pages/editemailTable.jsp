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
</head>


<body>
	<style>
.col-md-8 {
	padding: 10px;
}

.border-class {
	border: 1px solid #bfbfc1;
	float: right;
	padding: 10px 25px;
}

.table-header {
	width: 100%;
}
</style>
	<div class="page-header">
		<div class="page-header-wrapper container">
			<div class="main-logo">Giesecke &amp; Devrient</div>

			<div class="tab-menu dark">
				 <ul>
                    	<li id="icons"  ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons" ><a href="<c:url value='/master/branchSearch'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
                         <li id="icons" class="selected"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                     
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
							<li class="selected">
                                    <a href="<c:url value='/emailcont/editemailTable'/>">edit email Config</a>
                                </li>
                               <!-- <li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>-->   
						</ul>
					</div>
				</div>
			</div>
			<div class="right-content record">
			<h2>edit email Configartion</h2>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
<!-- 			    <div class="col-md-12"> -->
<!--                                     <span class="blue-text">LP AWB Branch Group</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
<!--                                        <select name="lpawb" id="lpawbid"> -->
<!--                                         <option value="-1" disabled selected style="display: none;">select LP AWB Branch Group..</option>  -->
<%-- 									<c:forEach var="statusVal" items="${fileDir}"> --%>
<%--                                             <option value="${statusVal.key}">${statusVal.value}</option> --%>
<%--                                     </c:forEach> --%>
<!--                                         </select> -->
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                        
<!--                                     </div>     -->
<!--                                 </div>  -->
<!-- 			<form action="/GnD/emailcont/editemailui" method="post"> -->
<!-- 			<input type="hidden" name="id"/> -->
<!-- 			<table> -->
<!-- 			<tr> -->
<!-- 				<td>User Name</td><td><input type="text" name="userName" value=""/></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td> -->
<!-- 				password</td><td><input type="text" name="password"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td> -->
<!-- 				Subject<td><input type="text" name="subject"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td>From Email</td><td><input type="text" name="fromemail"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td>CC Email</td><td><input type="text" name="ccemail"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td> -->
<!-- 				BCC Email</td><td><input type="text" name="bccemail"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr><td>Destination path</td><td><input type="text" name="destinationpath"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td>Hos</td><td><input type="text" name="host"/></td> -->
<!-- 				</tr> -->
				
<!-- 				<tr> -->
<!-- 				<td>Port</td><td><input type="text" name="port"/></td> -->
<!-- 				<td><input type="submit" value="update"/></td> -->
<!-- 				</tr> -->
				
<!-- 				</table> -->
<!-- 				</form> -->
			</div>
	</div>
	</div>
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
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
					<div class="footer-logo"></div>
				</div>
			</div>
		</div>
	</div>
	<script>

	function formSubmit() {
		document.getElementById("logoutForm").submit();
  	}

	</script>
</body>
</html>