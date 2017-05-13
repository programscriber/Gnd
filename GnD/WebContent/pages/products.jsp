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
                         <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                         <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                       --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<%-- 							<li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>						  --%>
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
							<li >
                                    <a href="<c:url value='/master/branchSearch'/>">Branch</a>
                                </li>
                                <li >
                                    <a href="<c:url value='/master/bank'/>">Bank</a>
                                </li>
                                <li class="selected">
                                    <a href="<c:url value='/upload/getProduct'/>">Products</a>
                                </li>
                                <li >
                                    <a href="/GnD/upload/getserviceprovider">AWB</a>
                                </li>
						</ul>
					</div>
				</div>
			</div>
			<%-- 				 <h2><c:if test="${msg ne null }"> --%>
			<%-- 			<c:out value = "${msg}"/> --%>
			<%-- 		</c:if>	</h2> --%>
			<div class="right-content record">
				<h2 class="blue-text">Import New Product</h2>
				<form id="form1" action="/GnD/upload/importmaster" method="post"
					enctype="multipart/form-data">
					<div class="col-md-12">
						<div class="col-md-8">
							<div class="col-md-12">
								<span class="blue-text">File Upload</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input path="fileData" type="file" name="file" required />
									<div class="col-md-2 border-class">
										DCMS<br> <label class="label"> <input
											type="radio" class="radiobutton" name="dcms" value="ELECTRA"
											checked="checked"> <span class="custom-radiobutton"></span>
											Electra
										</label> <label class="label"> <input type="radio"
											class="radiobutton" name="dcms" value="FSS"> <span
											class="custom-radiobutton"></span> FSS
										</label>
									</div>
									<button class="btn btn-l btn-blue" type="submit" form="form1"
										value="Import">Import</button>
								</div>
				</form>
			</div>
			<div class="col-md-12">
				<span class="divider">------ OR -----</span>
			</div>
			<!--    <a href="/GnD/master/prerequesttoadd" style="text-decoration:none;" color: black;>Add Manual</a>                             <a href="addproducts.jsp" style="text-decoration:none;" color: black;></a> -->
			<div class="col-md-12">
				<button class="btn">
					<a href="/GnD/master/prerequesttoadd"
						style="text-decoration: none;" color:black;>Add Manual</a>
				</button>
				<br>
				<br>
				<!--                                     <div class="code-example"> -->
				<!--                                         <div class="progress" data-percentage="38"> -->
				<!--                                             <div class="progress-indicator"></div> -->
				<!--                                         </div> -->
				<!--                                     </div> -->
			</div>


		</div>
		<!--                             <div class="col-md-2 border-class"> -->
		<!--                                 DCMS<br> -->
		<!--                                 <label class="label"> -->
		<!--                                     <input type="radio" class="radiobutton" name="my-radiobutton-group"> -->
		<!--                                     <span class="custom-radiobutton"></span> -->
		<!--                                     Electra -->
		<!--                                 </label> -->
		<!--                                 <label class="label"> -->
		<!--                                     <input type="radio" class="radiobutton" name="my-radiobutton-group" checked="checked"> -->
		<!--                                     <span class="custom-radiobutton"></span> -->
		<!--                                     FSS -->
		<!--                                 </label> -->
		<!--                             </div> -->
	</div>

	
	<div class="col-md-12">

		<h2 class="blue-text">Product Search</h2>
		<div class="row right-con">

			<div class="col-md-2">
				<span class="blue-text">Bank Code</span>
				<form action="/GnD/master/productSearch" method="post">
					<div class="select-menu select-menu-native select-menu-light">
						<select name="bank">
							<option>%%</option>
							<c:forEach var="critMasBankVal" items="${critMasBank}">
								<option value="${critMasBankVal.bankId}">${critMasBankVal.shortCode}</option>
							</c:forEach>
						</select> <span class="addon"> <span class="select-menu-icon"></span>
						</span>
					</div>
			</div>
			<div class="col-md-2">
				<span class="blue-text">Card Type</span>
				<div class="select-menu select-menu-native select-menu-light">
					<select name="cardType">
						<option>%%</option>
						<c:forEach var="masTypeVal" items="${masType}">
							<option value="${masTypeVal.typeId}">${masTypeVal.typeDescription}</option>
						</c:forEach>
						
					</select> <span class="addon"> <span class="select-menu-icon"></span>
					</span>
				</div>
			</div>
			<div class="col-md-2">
				<span class="blue-text">DCMS Name</span>
				<div class="select-menu select-menu-native select-menu-light">
					<select name="dcms">
						<option>%%<option>
							<c:forEach var="masDcmsVal" items="${masDcms}">
								<option value="${masDcmsVal.dcmsId}">${masDcmsVal.dcmsName}</option>
							</c:forEach>
							
					</select> <span class="addon"> <span class="select-menu-icon"></span>
					</span>
				</div>
			</div>
			<div class="col-md-2">
				<span class="blue-text">Product Name</span>
				<div class="select-menu select-menu-native select-menu-light">
					<input type="text" class="text-field product" name="productName" />
				</div>
			</div>
		</div>


		<div class="clear"></div>
		<div class="row">
			<div class="">
				<input type="submit" value="Search" class="btn btn-l btn-blue" />
				<button type="reset" class="btn btn-l">Reset</button>
				<!--                                     <button type="button" class="btn btn-l btn-blue">Search</button> -->
			</div>
		</div>
		</form>
		<div class="clear"></div>
		<div class="table-header">
			<!--                         <h3>Search Results : 14 Records</h3> -->
			<button class="btn btn-only-img btn-only-img1 btn-s" type="button">
				<img src="<c:url value='/resources/img/icons/download.png'/>"
					alt="Print">
			</button>
		</div>
		<div class="clear"></div>
		<table class="table">
			<thead>
				<tr>
					<th>Product Code</th>
					<th>Product Desc</th>
					<th>BIN</th>
					<th>STATUS</th>
					<th>FOURTH STATUS</th>
					<th>PHOTO CARD</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ProductRef" items="${Product}">

					<tr>

						<td>${ProductRef.shortCode}</td>
						<td>${ProductRef.productName}</td>
						<td>${ProductRef.bin}</td>
						<td>${ProductRef.statusString}</td>
						<td>${ProductRef.fourthLineReqString}</td>
						<td>${ProductRef.photoCardString}</td>
					</tr>

					<tr>
				</c:forEach>
				
			</tbody>
		</table>
		<!--                     <div class="pagination"> -->
		<!--                         <ul> -->
		<!--                             <li><a href="#">1</a></li> -->
		<!--                             <li><a href="#">2</a></li> -->
		<!--                             <li><a href="#">3</a></li> -->
		<!--                             <li><a href="#">4</a></li> -->
		<!--                             <li class="selected"><a href="#">5</a></li> -->
		<!--                             <li><a href="#">6</a></li> -->
		<!--                             <li><a href="#">7</a></li> -->
		<!--                             <li><a href="#">8</a></li> -->
		<!--                             <li><a href="#">9</a></li> -->
		<!--                             <li><a href="#">10</a></li> -->
		<!--                             <li><a href="#">20</a></li> -->
		<!--                             <li><a href="#">30</a></li> -->
		<!--                             <li><a href="#">100</a></li> -->
		<!--                             <li><a href="#">200</a></li> -->
		<!--                             <li class="meta-navigation"><a href="#">Next ></a></li> -->
		<!--                         </ul> -->
		<!--                     </div> -->
	</div>
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>


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