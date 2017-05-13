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
	<%!
	public static final int Card_Dispatch = 20;
	public static final int Card_Delievered = 21;
	public static final int Card_Returned = 22;
	public static final int Card_ReDispatched = 23;
	public static final int Card_Destroy = 24;

	public static final int PIN_STATUS_MAILER = 70;
	public static final int PIN_STATUS_DISPATCH = 71;
	public static final int PIN_STATUS_DELIVER = 72;
	public static final int PIN_STATUS_RTO = 73;
	public static final int PIN_STATUS_REDISPATCH = 74;
	public static final int PIN_STATUS_TODESTROY = 75;
	public static final int PIN_STATUS_DESTROY = 76;
	public static final int Manual_Destroy=91;
	
	%>
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
                        <li id="prototype" class="selected"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype" ><a href="/GnD/shopfloor/home">Shop floor</a></li>
					</c:if>		
					 <c:if test="${username eq 'helpdesk'}">
						 <li id="icons" ><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						 <li id="prototype" class="selected"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if>			  
					 <c:if test="${username eq 'shopfloor'}">
						    <li id="prototype" ><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					  <c:if test="${username eq 'warehouse'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						  <li id="prototype" class="selected"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
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
							<li id="prototype"   class="selected"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						</ul>
					</div>
				</div>
			</div>
						 <c:url value="/j_spring_security_logout" var="logoutUrl" />
			 			<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>	
						 <div class="right-content" >
<!-- 						 <p> -->
<%--  							 <c:if test="${msg ne null }"> --%>
<%-- 								<c:out value = "${msg}"/> --%>
<%-- 							</c:if>	  --%>
<!-- 						</p> -->
                        <h2 class="blue-text">MIS Import</h2>
                        <form id ="cardMISImport" name="cardMISImport" action="/GnD/upload/processMISImport" method="post" enctype="multipart/form-data">
                           <div class="row right-con">
						 
								
										<table><tr>
												<td>Import</td>
												<td>Dispatch Type</td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To Status</td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Remarks</td></tr>
											<tr>
												<td><input id="file" path="fileData" type="file" name="file" /></td>
												<td><input id="dispatchType1" type="radio" name="dispatchType" value="CARD"/>Card<br>
													<input id="dispatchType2" type="radio" name="dispatchType" value="PIN"/>Pin</td>					
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select id="tostatus" name="tostatus">
													</select>
												</td> 
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="remark" id="remark"></textarea></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td><input type="submit"  class="btn btn-only-img btn-only-img1 btn-s" value="IMPORT"/></td>
											</tr>
 										</table>
								


						</div>
						</form>
										<div id="txt"></div>
						<div id="wait" style="display:none;width:69px;height:89px;position:absolute;top:50%;left:50%;padding:2px;"><img src="<c:url value='/resources/img/loading.gif'/>" width="90" height="90" /></div>
						
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
		
		$("#dispatchType1").click(function() {
			var dispatchType = $("#dispatchType1").val();
			
			 var options = '';
			 if(dispatchType=="CARD") {
				 options += '<option value="<%=Card_Delievered%>">Card Dispatch </option>';
				 options += '<option value="<%=Card_ReDispatched%>">Card Redispatch</option>';
				 $('#tostatus').html(options);
			 }
		});
			 
			 $("#dispatchType2").click(function() {
					var dispatchType = $("#dispatchType2").val();
					
					 var options = '';
					 if(dispatchType=="PIN") {
						 options += '<option value="<%=PIN_STATUS_DELIVER%>">Pin Dispatch</option>';
						 options += '<option value="<%=PIN_STATUS_REDISPATCH%>">Pin Redispatch</option>';
						 $('#tostatus').html(options);
					 }

			
		});
		




		function formSubmit() {
			document.getElementById("logoutForm").submit();
	  }

// 			function check() {
// 				var file = document.getElementById("file").value;
// 				var status = document.getElementById("tostatus").value;
// 				var remarks = document.getElementById("remark").value;
// 				if (file=='') {
// 					alert("pls upload the file");
// 					document.getElementById("file").focus();
// 					return false;
// 				}
				
// 				var dispatchCheck = dispatchType();
// 				if(dispatchCheck) {
					
// 					if(status == '') {
// 						alert("Please select status to be update");
// 						document.getElementById("tostatus").focus();
// 						return false;
// 					}
// 					if(remarks.length == 0 || remarks.length>30) {
// 						alert("Please enter remarks Maximum 30 characters allowed");
// 						return false;
// 					}
					
// 				} else {
// 					return false;
// 				}
// 				return true;
// 			}
// 			function dispatchType() {
//  			var dispatchType = document.forms.cardMISImport.dispatchType;
// 			for (var i=0; i < dispatchType.length; i++) {
// 				if (dispatchType[i].checked)
// 					return true;
// 				}
// 					alert("Please select any one dispatch type");
// 					return false;
// 			}
			
			$(document).ajaxStart(function(){
		        $("#wait").css("display", "block");
		    });
			
			$(document).ajaxComplete(function(){
		        $("#wait").css("display", "none");
		    });
			
			$('#cardMISImport').submit(function(e) {
				var file = document.getElementById("file").value;
				var status = document.getElementById("tostatus").value;
				var remarks = document.getElementById("remark").value;
				if (file=='') {
					alert("pls upload the file");
					document.getElementById("file").focus();
					return false;
				}
				var dispatchCheck = false;
				var dispatchType = document.forms.cardMISImport.dispatchType;
				for (var i=0; i < dispatchType.length; i++) {
					if (dispatchType[i].checked) {
						dispatchCheck =  true;
						break;
					}
				}
				if(dispatchCheck == false) {
					alert("Please select any one dispatch type");
					return false;
				}
					
					if(status == '') {
						alert("Please select status to be update");
						document.getElementById("tostatus").focus();
						return false;
					}
					if(remarks.length == 0 || remarks.length>30) {
						alert("Please enter remarks Maximum 30 characters allowed");
						return false;
					}
// 					var data = new FormData();
// 					jQuery.each(jQuery('#file')[0].files, function(i, file) {
// 					    data.append('file-'+i, file);
// 					});
					
					 
					  //grab all form data  
					  var formData = new FormData($(this)[0]);
					
					$.ajax({
						type: 'Post',
	  					url: $('#cardMISImport').attr('action'),
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
			
			
			$(window).load(function() {
			     $('#txt').hide();
			});	
		</script>
</body>
</html>
