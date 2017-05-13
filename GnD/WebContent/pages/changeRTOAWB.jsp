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
<%-- <script type="text/javascript" src="<c:url value='/resources/js/jquery.tabletojson.js'/>"></script> --%>


<link href="<c:url value='/resources/css/infragistics.theme.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/infragistics.css'/>"
	rel="stylesheet" />


<!-- Ignite UI Required Combined JavaScript Files -->
<script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
<script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jtable.css'/>">

<script type="text/javascript" src="<c:url value='/resources/js/jtable.js'/>"></script>
<script>

$('#send').submit(function() {
	$("#wait").show();
    return true;
});

$('#checkall').click(function() {
		 $(document.getElementById("changeAWB").elements).filter(':checkbox').prop('checked', this.checked);
});
// $(function(){
	
	
	 
    // add multiple select / deselect functionality
//     $("#selectall").click(function () {
//           $('.case').attr('checked', this.checked);
//     });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
//     $(".case").click(function(){
 
//         if($(".case").length == $(".case:checked").length) {
//             $("#selectall").attr("checked", "checked");
//         } else {
//             $("#selectall").removeAttr("checked");
//         }
 
//     });
// });

	$(function() {
		$( "#fromdate" ).datepicker({maxDate: new Date(), minDate: -1095});
    	$( "#fromdate" ).datepicker("setDate", new Date());
    });
	$(function() {
		$( "#todate" ).datepicker({maxDate: new Date(), minDate: -1095});
    	$( "#todate" ).datepicker("setDate", new Date());
    });
	
	
</script>
<style type="text/css">

select {
        width:150px;
        height:20px;
    }

</style>
</head>



<body>

	<%!public static final int Card_Returned = 22;%>
	<style>
	#detailsid {
    border-collapse: collapse;
    width: 50%;
    float: right;
    margin-right: 300px;
   
}

#detailsid  {
    text-align: left;
    padding: 8px;
}



th {
    background-color: #006699;
    color: white;
}
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
					</c:if>					  
					  <c:if test="${username eq 'shopfloor'}">
						    <li id="prototype" class="selected"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					  <c:if test="${username eq 'warehouse'}">
						  <li id="prototype" class="selected"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if>
					  <c:if test="${username eq 'rto'}">
						  <li id="prototype" class="selected" ><a href="/GnD/shopfloor/home">RTO</a></li>
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
										<li id="prototype"><a href="/GnD/shopfloor/carddilivery">card Delivery</a></li>
<!-- 										<li id="prototype" ><a href="/GnD/shopfloor/cardreturned">card Returned</a></li> -->
										<li id="prototype" ><a href="/GnD/shopfloor/cardredispatch">card Redispatch</a></li>
										<li id="prototype" ><a href="/GnD/shopfloor/carddestroy">Card Destroy</a></li>
										<li id="prototype"><a href="/GnD/shopfloor/carddeliverbyAWB">Card Delivery By AWB</a></li>
										<!--<li id="prototype" ><a href="/GnD/shopfloor/cardrtobyAWB">Card RTO By AWB</a></li>-->
										<li id="prototype" ><a href="/GnD/shopfloor/changeAWB">Change AWB</a></li>
									<li id="prototype"  ><a href="/GnD/shopfloor/scanbyRSNtorto">RTO Scan by RSN</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/scanbyAWBtorto">RTO Scan By AWB</a></li>
										<li id="prototype" class="selected"><a href="/GnD/shopfloor/changeRTOAWB">Change RTO AWB</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pindispatch">Pin Dispatch</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pindelivery">Pin Delivery</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/pinreturned">Pin Returned</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pinredispatch">Pin Redispatch</a></li>
									<li id="prototype"><a href="/GnD/shopfloor/pindestroy">Pin Destroy</a></li>
							</c:if>
									<c:if test="${username eq 'shopfloor'}">
										<li id="prototype" class="selected"><a href="/GnD/shopfloor/carddispatch">Card Production</a></li>
									</c:if>
									<c:if test="${username eq 'warehouse'}">
										<li id="prototype"><a href="/GnD/shopfloor/carddilivery">card Delivery</a></li>
										<!--<li id="prototype"><a href="/GnD/shopfloor/cardreturned">card Returned</a></li>-->
										<li id="prototype"><a href="/GnD/shopfloor/cardredispatch">card Redispatch</a></li>
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/carddestroy">Card Destroy</a></li> -->
										<li id="prototype"><a href="/GnD/shopfloor/carddeliverbyAWB">Card Delivery By AWB</a></li>
<!-- 										<li id="prototype" class="selected"><a href="/GnD/shopfloor/cardrtobyAWB">Card RTO By AWB</a></li> -->
<!-- 										<li id="prototype"><a href="/GnD/shopfloor/changeAWB">Change AWB</a></li> -->
									</c:if>
									<c:if test="${username eq 'rto'}">
									
									<li id="prototype"><a href="/GnD/shopfloor/scanbyRSNtorto">RTO Scan by RSN</a></li>
									<li id="prototype" ><a href="/GnD/shopfloor/scanbyAWBtorto">RTO Scan By AWB</a></li>
									<li id="prototype"  class="selected"><a href="/GnD/shopfloor/changeRTOAWB">Change RTO AWB</a></li>

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
			
			<div class="right-content">
				
				<h2 class="blue-text">Change AWB By Branch</h2>
					<form id="getList" name="getList" action="/GnD/shopfloor/gettxnbydate" method="post" onsubmit="return checkDates()">
						<div class="row right-con">
						 <table >
						 	<tr>
								<td>From</td>
								<td >To</td>
								<td>Bank</td>
								<td>Status</td>
							</tr>
							<tr>
								<td><input type="text" id="fromdate" name="fromdate" /></td>
								<td><input type="text" id="todate" name="todate" /></td>
								<td>
								<select id="bankid" name="bankid">
									<c:if test="${bankObj ne null }">
										<option value="${bankObj.bankId}">${bankObj.shortCode}</option>
									</c:if>
									<c:forEach var="critMasBankVal" items="${bank}">
										<option value="${critMasBankVal.bankId}">${critMasBankVal.shortCode}</option>
									</c:forEach>
								</select>
								</td>			
								<td><select id="status" name="status">
										<option value="<%=Card_Returned%>">Card Returned</option>
									</select>
								</td>
								<td><input id = "send" type="submit" value="Submit" class="btn btn-only-img btn-only-img1 btn-s" "/></td>
							</tr>
 										</table>
						
					</div>
					
					</form>
					<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
						<div id="txt"></div>
						<div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src="<c:url value='/resources/img/icons/demo_wait_b.gif'/>" width="90" height="90" /><br>Loading..</div>
							<div class="clear"></div>
							<div id="tableid">
							<form id="changeAWB" name="changeAWB"  method="post">
								<c:choose> 
									<c:when test="${msg ne null }">
										<h2><center><c:out value = "${msg}"/></center></h2>
									</c:when>
									<c:otherwise>
										<h4>Search Results : <c:out value = "${records.size()}"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="submit" id="submit" value="ChangeAWB" class="btn btn-l btn-blue"/></h4>

										
								<br><br><br>
								<table id = "datatbl">
									<thead>
										<tr>
										<td><input id="selectall" class="selectall" type="checkbox" name="selectall" />Select</td>

											<td>RSN</td>
											<td>Dispatch Branch Code</td>
											<td>Product Code</td>
											<td>Card AWB</td>
											<td>Card Status</td>
											<td>Bank Code</td>
											<td>Filename</td>
										</tr>
									</thead>
									<tbody>
										<% int rowCount =0; %>
										<c:forEach var="listValue" items="${records}">

											<tr>
												<td><input id="case" class="case"
													type="checkbox" name="changestatus"
													value="${listValue.rsn}" />
												<div>
														<input id="hidstatus" type=hidden name="status" />
													</div></td>
												<td>${listValue.rsn}</td>
												<td>${listValue.processedBranchCode}</td>
												<td>${listValue.product}</td>
												<td>${listValue.cardAWB}</td>
												<td>${listValue.recordStatus}</td>
												<td>${listValue.bankCode}</td>
												<td>${listValue.filename}</td>
												<%rowCount++; %>
											

										</c:forEach>

									</tbody>
								</table>
								</c:otherwise>
								</c:choose>
								</form>
							</div>
						</div>
					</div>
				
			
		<div class="clear"></div>
		<br>
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
		
		$(document).ready(function(){
		    $('#datatbl').DataTable();
		});

		$('#selectall').click(function() {
	   		 $(this.form.elements).filter(':checkbox').prop('checked', this.checked);
		});	
		
		function formSubmit() {
			document.getElementById("logoutForm").submit();
	
	  }
		
		

					
			
		function checkDates() {
			var fromdate = document.getElementById("fromdate").value();
	    	var todate = document.getElementById("todate").value();
	    	if(fromdate == '' || todate =='') {
	    		alert("Please select from and to dates");
	    		return false;
	    	}
	    	if(new Date(fromdate).getTime() > new Date(todate).getTime()) {
	    		alert("From date should not be greater than To date");
	    		return false;
	    	}
	    	return true;
		}
		
		
		$(document).ajaxStart(function(){
	        $("#wait").css("display", "block");
	    });
		
		$(document).ajaxComplete(function(){
	        $("#wait").css("display", "none");
	    });
		
		$("#changeAWB").submit(function(e) {
			 

			var changestatus = document.querySelectorAll('input[name=changestatus]:checked');
			var itemStr = '';
			if(changestatus.length == 0) {
				alert("Please select any one record");
				return false;
				
			}
			
				for (var i=0; i<changestatus.length; i++) {
				     if (changestatus[i].checked) {
				        itemStr=itemStr+changestatus[i].value+",";
				     }
				  }
				
				if(!confirm("Click OK to assign new AWB values")) {
					return false;
				} 	       
		    var url = "/GnD/shopfloor/assignnewawb?bankid="+$('#bankid').val()+"&detailsid="+itemStr; // the script where you handle the form input.

		    $.ajax({
//			           contentType: 'application/json; charset=utf-8',
//			           dataType: 'json',
//			           data: JSON.stringify(table),
		           type: "Post",
		           url: url,
//			           data : $("#txt").load(url),
//			           data: $("#submit").serialize(), // serializes the form's elements.
		           success: function(data)
		           {
		               alert(data.result);
		               location.reload(true);
		               // show response from the php script.
		           },
		    		error: function(){                        
               	 		alert('Error while request..');
           		 	}
		         });

		    e.preventDefault(); // avoid to execute the actual submit of the form.
		});

			
			
		</script>
		</body>
		</html>			
					
				
				
				
				
				
				
				
				
				
				
				
				
				
				
