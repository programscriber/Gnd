<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html
	class=" js backgroundsize cssanimations csstransitions pointerevents"
	lang="en">
<head>
</head>
<body>
	<ul>
                    <c:if test="${username eq 'admin'}">
                    	<li id="icons"  class="selected"><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                        <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                        <li id="styleguide"><a href="#">I/O Config</a></li>
                        <li id="screens"><a href="#">Time-Out Config</a></li>
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
                        <li id="prototype"><a href="/GnD/search/getReport">Reports</a></li> 
                         <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                       
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons"  class="selected"><a href="<c:url value='/loginuser/home'/>">Home</a></li>
						  <li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
						  <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>
					  </c:if>  
					   <c:if test="${username eq 'warehouse' || username eq 'shopfloor'}">
						  <li id="icons"  class="selected"><a href="<c:url value='/loginuser/home'/>">Home</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					      <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
					   
                    </ul>
	

</body>
</html>