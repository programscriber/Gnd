<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%> 
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
<link href="<c:url value='/resources/css/recordsearch.css'/>"
	rel="stylesheet" />
<link rel="stylesheet" href="/resources/css/bootstrap-multiselect.css"
	type="text/css" />

<!-- Ignite UI Required Combined JavaScript Files -->
<script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
<script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>
<script src="<c:url value='/resources/js/bootstrap-multiselect.js'/>"></script>
<script src="<c:url value='/resources/js/awbsearch.js'/>"></script>



<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/resources/js/bootstrap-multiselect.js"></script>
<!-- //////checked  -->

</head>



<body>

	<div class="page-header" style="position: fixed width:100%">
		<div class="page-header-wrapper container">
			<div class="main-logo">Giesecke &amp; Devrient</div>

			<!-- <div class="tab-menu dark">

				<ul>
												<li id="prototype"><a href="/GnD/loginuser/logout">Logout</a></li>
				</ul>
			</div> -->
				<!-- <div class="adress-text">
				
				</div> -->
			<p style="float: right; margin-top: 0px;"></p>
		</div>

	</div>
	<div class="main" style="">
		<div class="container main-conten">
		
		
		
	 <center style="margin:200px;">
	
	 <%--  <c:out value = "${msg}"/> --%>
	 
	

		  <font face="Arial, Helvetica, sans-serif" size="10" color="Red" >
	    <%--  <c:out value = "${exception.msg}"/> --%>
		<h2>  <c:if test="${exception.msg ne null}">
	          <c:out value = "${exception.msg}"/>
	         </c:if></h2>
			        <button class="btn  btn-blue back-button" onclick="history.back()">Back</button>
			<!-- <div class="adtexception">			
				
				</div>	 -->
			  
		</font> 
	</center> 
	
	
	
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
					
				</div>
				<div class="footer-right-column">
					<div class="footer-logo"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>