 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<script src="<c:url value='/resources/js/recordserach.js'/>"></script>
<%-- <script src="<c:url value='/resources/js/rsn.js'/>"></script> --%>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/resources/js/bootstrap-multiselect.js"></script>
<!-- //////checked  -->

</head>



<body>

	<div class="page-header" style="position: fixed width:100%">
		<div class="page-header-wrapper container">
			<div class="main-logo">Giesecke &amp; Devrient</div>
			<div class="tab-menu dark">
				 <ul>
				 	<c:if test="${pageContext.request.contextPath eq '/GnD'}">
                    	<li id="icons"  ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    </c:if>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                      --%>
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
						  <li id="prototype" ><a href="/GnD/shopfloor/home">RTO</a></li>
					  </c:if> 
					  <c:if test="${username eq 'datagen'}">
                    	<li id="icons" ><a href="<c:url value='/qcprocess'/>">QC</a></li>
					</c:if>
					<c:if test="${pageContext.request.contextPath eq '/GnD'}">
                        <li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>   
					      <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
					 </c:if>
                    </ul>
              
			</div>
			<div class="adress-text">
			
			</div>
			<p style="float: right; margin-top: 0px;"></p>
		</div>

	</div>
	<div class="main" style="">
		<div class="container main-conten">
			<div class="left-content">
				<div class="demo-left-nav" id="left-nav">
					<h4>Search By</h4>
					<div class="navigation">
						<ul>
							<li ><a href="${pageContext.request.contextPath}/track">Mobile
									No</a></li>
							<li><a href="${pageContext.request.contextPath}/awbsearch">Card AWB</a></li>
							<li><a href="${pageContext.request.contextPath}/pinawbpage">Pin AWB</a></li>
							<li><a href="${pageContext.request.contextPath}/prerequesttoadd">Bank and ac no </a></li>
							<li ><a href="${pageContext.request.contextPath}/rsnpage">RSN </a></li>
							<li><a href="${pageContext.request.contextPath}/prerequest">Branchwise</a></li>
							<li class="selected"><a href="${pageContext.request.contextPath}/customerid">Customer Id</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="right-content record">
				<h2 class="blue-text">Customer Id</h2>
				<form id = "customerSearch" name="customerSearch" action="${pageContext.request.contextPath}/getcustomerbycustid" method="post">
					<table id="searchtable">
						<tr>
							<th></th>
						</tr>
						<tr>
						<tr>
							<th>Customer Id</th>
							<td><input id="customerId" type="text" class="text-field awb" value="${customerId}"
								 name="customerId"  autofocus/> <!-- 							<input type=hidden id="hidrsn" name="rsn"/></td> -->
							<td><label id="errmsg"></label></td>
						</tr>

						<tr></tr>
						<tr></tr>
						<tr>
							<td></td>
							<td><input type="submit" class="btn btn-l btn-blue"
								value="search" onclick="return validateCustomerId();"/>
								<input type="reset"  value="reset" class="btn btn-l"/></td>
						</tr>
					</table>


				</form>
			
				<p>${downloadResult}</p>
				<div id="tableid">
					<c:choose>
						<c:when test="${msg ne null }">
							<h2>
								<center>
									<c:out value="${msg}" />
								</center>
							</h2>
						</c:when>
						<c:otherwise>
							<c:if test="${records.size() > 0}">
								<h3>
									Search Results :
									<c:out value="${count}" />
								</h3>

			
<!-- 				 <div class="table-header"> -->
<!-- <!--                        <h3>Search Results : 14 Records</h3> --> 
<!--                         <button  id=" " type="button"  class="btn btn-only-img btn-only-img1 btn-s" onclick="location.href='/sbi/rsnReport'"> -->
<%--      <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print"> --%>
<!--                         </button> -->
                                           
<!--                     </div> -->
						
								<table class="table">
									<thead>
										<tr>
											
											<th>Account</th>
											<th>Card holder name</th>
											<th>Branch Code</th>
											<th>Product Code</th>
											<th>Card dispatch Status</th>
											<th>Approval Status</th>
											<th>Pin dispatch Status</th>
											<th>RSN</th>
										</tr>
									</thead>
									<tbody>
										<%
											int rowCount = 0;
										%>
										<c:forEach var="listValue" items="${records}">

											<tr>
												
												<td><a
													href="${pageContext.request.contextPath}/record?creditCardDetailsId=${listValue.creditCardDetailsId}">${listValue.primaryAcctNo}</a></td>
												<td>${listValue.embossName}</td>
												<td>${listValue.homeBranchCode}</td>
												<td>${listValue.product}</td>
												<td>${listValue.cardStatus}</td>
												<td>${listValue.recordStatus}</td>
												<td>${listValue.pinStatusString}</td>
												<td>${listValue.rsn}</td>
												<%
													rowCount++;
												%>
											
										</c:forEach>

									</tbody>

								</table>
								<tag:paginate max="15" offset="${offset}" count="${count}"
									uri="${pageContext.request.contextPath}/rsn?rsn=${rsn}"
									next="&raquo;" previous="&laquo;" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
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
						<!-- 						<li><p>Helpdesk Contact : 04439915827, Email : -->
						<!-- 								fics@gi-de.com</p></li> -->
						
					</ul>
				</div>
				<div class="footer-right-column">
					<div class="footer-logo"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function formSubmit() {
		document.getElementById("logoutForm").submit();
  }
	
	
	function validateCustomerId(){
		 var x = document.forms["customerSearch"]["customerId"].value;
		    if (x == null || x == "") {
		        alert("Customer Id should not be empty");
		        return false;
		    }
		    document.rsnsearch.submit();  
	}

	</script>
</body>
 <style>
  ul.pagination { 
    display: inline-block;
     padding: 0; 
     margin: 0; 
 } 

 ul.pagination li {display: inline;} 

 ul.pagination li a { 
    color: black; 
    float: left; 
     padding: 8px 16px; 
    text-decoration: none; 
 } 
 </style>
</html>