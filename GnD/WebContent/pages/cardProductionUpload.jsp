<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	src="<c:url value='/resources/js/something.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/LockScreen.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/blazer.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/css/jquery-ui.css'/>">
<script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
<script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>

<script src="<c:url value='/resources/js/jquery.alertable.js'/>"></script>
<script src="<c:url value='/resources/js/velocity.min.js'/>"></script>
<script src="<c:url value='/resources/js/velocity.ui.min.js'/>"></script>
<link href="<c:url value='/resources/css/jquery.alertable.css'/>"
	rel="stylesheet" />
<script src="<c:url value='/resources/js/jquery.tabletojson.js'/>"></script>





<link href="<c:url value='/resources/css/infragistics.theme.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/infragistics.css'/>"
	rel="stylesheet" />


<!-- Ignite UI Required Combined JavaScript Files -->
<script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
<script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>

</head>



<body>
	<%!public static final int Card_Dispatch = 20;%>
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
	<script>
								</script>

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
					</c:if>					  
					  <c:if test="${username eq 'shopfloor'}">
						    <li id="prototype" class="selected"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					  <c:if test="${username eq 'warehouse'}">
						  <li id="prototype" class="selected"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
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
										<li id="prototype" class="selected"><a href="/GnD/shopfloor/productionUpload">UploadRSNList</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/carddilivery">card Delivery</a></li>
										<!--<li id="prototype"><a href="/GnD/shopfloor/cardreturned">card Returned</a></li>-->
										<li id="prototype"><a href="/GnD/shopfloor/cardredispatch">card Redispatch</a></li>
										<li id="prototype" ><a href="/GnD/shopfloor/carddestroy">Card Destroy</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/carddeliverbyAWB">Card Delivery By AWB</a></li>
										<!--<li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>-->
										<li id="prototype"><a href="/GnD/shopfloor/changeAWB">Change AWB</a></li>
										<li id="prototype"  ><a href="/GnD/shopfloor/scanbyRSNtorto">RTO Scan by RSN</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/scanbyAWBtorto">RTO Scan By AWB</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/changeRTOAWB">Change RTO AWB</a></li>
									
									<li id="prototype"><a href="/GnD/shopfloor/pindispatch">Pin Dispatch</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pindelivery">Pin Delivery</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/pinreturned">Pin Returned</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/pindestroy">Pin Destroy</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pinredispatch">Pin Redispatch</a></li>
									
							</c:if>
									<c:if test="${username eq 'shopfloor'}">
										<li id="prototype"><a href="/GnD/shopfloor/carddispatch">Card Production</a></li>
										<li id="prototype" class="selected"><a href="/GnD/shopfloor/productionUpload">UploadRSNList</a></li>
									</c:if>
									 <c:if test="${username eq 'warehouse'}">
										<li id="prototype"><a href="/GnD/shopfloor/carddilivery">card Delivery</a></li>
										<!--<li id="prototype"><a href="/GnD/shopfloor/cardreturned">card Returned</a></li>--> 
										<li id="prototype"><a href="/GnD/shopfloor/cardredispatch">card Redispatch</a></li>
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/carddestroy">Card Destroy</a></li> -->
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/carddeliverbyAWB">Card Delivery By AWB</a></li> -->
 										<!--<li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>--> 
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/changeAWB">Change AWB</a></li> -->
									</c:if>
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pindispatch">Pin Dispatch</a></li> -->
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pindelivery">Pin Delivery</a></li> -->
<!-- 									<li id="prototype" ><a href="/GnD/shopfloor/pinreturned">Pin Returned</a></li> -->
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pinredispatch">Pin Redispatch</a></li> -->
<!-- 									<li id="prototype"><a href="/GnD/shopfloor/pindestroy">Pin Destroy</a></li> -->
						</ul>
					</div>
				</div>
			</div>
			 <div class="right-content" >
                        <h2 class="blue-text">Card Production</h2>
                        <audio id="audioid" style="display:none;" controls >
 					   <source src="<c:url value='/resources/audio/failure.mp3'/>" type="audio/mpeg"></source>
  					</audio>
                          <form id ="cardrsnUpload" name="cardrsnUpload" action="/GnD/shopfloor/productionUploadRSNList" method="post" enctype="multipart/form-data">
                           <div class="row right-con">
                           		<div class="col-md-2">
                                    <span class="blue-text">Upload File</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="file" path="fileData" type="file" name="file" />
										<input type="submit"  class="btn btn-only-img btn-only-img1 btn-s" value="UPLOAD"/>
									</div>
								</div>
<!-- 										<table><tr> -->
<!-- 												<td>Import</td> -->
<!-- 												</tr> -->
<!-- 											<tr> -->
<!-- 												<td><input id="file" path="fileData" type="file" name="file" /></td> -->
<!-- 												<td><input type="submit"  class="btn btn-only-img btn-only-img1 btn-s" value="IMPORT"/></td> -->
<!-- 											</tr> -->
<!--  										</table> -->
								


						</div>
						</form>
						 <div id="wait" style="display:none;width:69px;height:65px;position:absolute;top:50%;left:50%;padding:2px;"><img src='/GnD/resources/img/loading.gif' width="64" height="64" /><br>Loading..</div>
							 <div id="savebutton" class="col-md-2">
             </div>
			
			 <div class="clear"></div>

				
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
		$(document).ajaxStart(function(){
	        $("#wait").css("display", "block");
	    });
				
		$(document).ajaxComplete(function(){
	        $("#wait").css("display", "none");
	    });
		
		$('#cardrsnUpload').submit(function(e) {
			var file = document.getElementById("file").value;
			if (file=='') {
				alert("pls upload the file");
				document.getElementById("file").focus();
				return false;
			}
			var formData = new FormData($(this)[0]);
			
			$.ajax({
				type: 'Post',
					url: $('#cardrsnUpload').attr('action'),
				    type: 'POST',
				 	data: formData,
			        cache: false,
			   	    contentType: false,
			        processData: false,
					success: function(data){
	  					 alert(data.result);
	  					 if(data.filepath != null) {
	  						window.location.href = '/GnD/shopfloor/downloadfile?filepath='+data.filepath;
	  					 } else {
	  						location.reload(true);
	  					 }
	  				},
	  				  error: function(){						
	  					alert('Error while request..');
	  				} 
			});
			e.preventDefault();
	});
		
			</script>
</body>
</html>