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
                        <li id="icons" class="selected"><a href="<c:url value='/master/branchSearch'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                      --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons" ><a href="<c:url value='/master/branchSearch'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<%-- 							<li id="icons" class="selected"><a href="<c:url value='/master/email'/>">Email</a></li>						  --%>
					  </c:if>  
					   <c:if test="${username eq 'warehouse' || username eq 'shopfloor'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
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
                                    <a href="<c:url value='/master/email'/>">send email</a>
                                </li>
						</ul>
					</div>
				</div>
			</div>
			<div class="right-content record">
			<button id="sendemail">Send Email</button>
			<table class="table">
                <tr>
                 <th>select</th>
                <th>File Name</th>
                <th>To email address</th>
               
                </tr>
        		<c:forEach var="fileemail" items="${fileNameAndEmail}">
        		<tr>
    			<td><input id="fileNameVal" type="checkbox" checked value="${fileemail.key}_${fileemail.value}"/></td>	<td> ${fileemail.key} </td><td>  ${fileemail.value}</td>
    			</tr>
				</c:forEach>
				</table>
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
	
	$('#sendemail').click(function(){
		var uncheck=[];
		$("input:checkbox:not(:checked)").each(function () {
			uncheck.push($(this).attr("value")+'.txt');
		});
		alert(uncheck);
		$.ajax({
			type:"post",
			cache:false,
			url:'/GnD/master/sendemail?unselectemail='+uncheck,
			sucess:function(data){
				alert(data);
			},
			error:function(){
				
			}
		});
		
		
		
		
		
	});
	
	
	
	
	function formSubmit() {
		document.getElementById("logoutForm").submit();
  }

		$('header a[href^="#"], h1 a[href^="#"]').click(function(e) {
			e.preventDefault();
			var $target = $($(this).attr('href'));
			var top = $target.offset().top;
			$('body, html').animate({
				'scrollTop' : top - 40
			});
		});

		var messageCount = 0;
		setInterval(function() {
			// turn on/off messages for demo purposes
			var $messages = $('.message');
			$messages.eq(messageCount++ % $messages.length).toggleClass(
					'fade-out');
		}, 700);

		setInterval(function() {
			// simulating progress of progress bars for demo purposes.
			var $progresses = $('.progress[data-percentage]');
			$progresses.each(function() {
				var striped = $(this).is('.progress-striped');
				var perc = +$(this).attr('data-percentage');
				var likelihood;
				if (perc % 101 === 100) {
					likelihood = .03;
				} else if (striped) {
					likelihood = .2;
				} else {
					likelihood = .7;
				}
				if (Math.random() < likelihood) {
					perc++;
					$(this).attr('data-percentage', perc % 101);
					hasLayout($(this).find('.progress-indicator'));
				}
			});
		}, 100);

		function hasLayout(el) {
			$(el).each(
					function(index, element) {
						if (element.currentStyle !== undefined
								&& element.currentStyle.hasLayout === false) {
							$(element).css({
								'zoom' : 1
							});
						}
					});
		}

		$(function() {

			//                 var product = [
			//                     "Product 1",
			//                     "Product 2",
			//                     "Product 3",
			//                     "Product 4"
			//                 ];
			var bank = [ "Bank 1", "Bank 2", "Bank 3", "Bank 4" ];

			$(".product").autocomplete({
				source : product,
				minLength : 0,
				focus : function() {
					$(this).autocomplete("search");
				}
			}).focus(function() {
				$(this).autocomplete("search");
			});
			$(".bank").autocomplete({
				source : bank,
				minLength : 0,
				focus : function() {
					$(this).autocomplete("search");
				}
			}).focus(function() {
				$(this).autocomplete("search");
			});
		});
	</script>
</body>
</html>