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

<%-- 	<% --%>

// 	   response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
// 	   response.addHeader("Pragma", "no-cache"); 
// 	   response.addDateHeader ("Expires", 0);
	   
	   
<%--    %> --%>
<%--     <c:if test="${username == null}"> --%>
<!--     	response.sendRedirect("/Gnd/loginuser/logout"); -->
<%-- 	</c:if> --%>
	<%!public static final int Card_Dispatch = 20;
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
					<h4>Search By</h4>
					<div class="navigation">
						<ul>
							<li class="selected"><a href="${pageContext.request.contextPath}/track">Mobile
									No</a></li>
							<li><a href="${pageContext.request.contextPath}/awbsearch">Card AWB</a></li>
							<li><a href="${pageContext.request.contextPath}/pinawbpage">Pin AWB</a></li>
							<li><a href="${pageContext.request.contextPath}/prerequesttoadd">Bank and ac no </a></li>
							<li><a href="${pageContext.request.contextPath}/rsnpage">RSN </a></li>
							<li><a href="${pageContext.request.contextPath}/prerequest">Branchwise</a></li>
						</ul>
					</div>
<!-- 					<div class="navigation"> -->
<!-- 						<ul> -->
<!-- 							<li class="selected"><a>Card/Pin Search</a></li> -->
<!-- 							<li><a href="/GnD/search/filesearch">File Search</a></li> -->
<!-- 						</ul> -->
<!-- 					</div> -->
				</div>
			</div>
			<div class="right-content record">
				
				<h2 class="blue-text">Card/Pin Search</h2>
				<form action="/GnD/search/getRecords" method="post" onsubmit="return validCheck()">
					<div class="row right-con">
						<div class="col-md-4">
							<span class="blue-text">Account Number</span>
							<div class="hero-unit">
								<input id="acctNo" type="text" name="acctNo"
									class="text-field float-left from" /> 
							</div>
						</div>
						<div class="col-md-2">
							<span class="blue-text">Pin AWB#</span>
							<div class="select-menu select-menu-native select-menu-light">
								<input id="pinawb" name ="pinawb" type="text" class="text-field product" /> 
							</div>
						</div>
						<div class="col-md-2">
							<span class="blue-text">Card AWB#</span>
							<div class="select-menu select-menu-native select-menu-light">
								<input id="cardawb" name="cardawb" type="text" class="text-field awb" /> 
							</div>
						</div>
						<div class="col-md-2">
							<span class="blue-text">RSN</span>
							<div class="select-menu select-menu-native select-menu-light">
								<input id="rsn" name="rsn" type="text" class="text-field awb" /> 
							</div>
						</div>
						<br> <br> <br>
						<div class="second-line">
							<div class="col-md-4">
								<span class="blue-text">Core Record Received or processed date in format (mm/dd/yyyy)</span>
								<div class="hero-unit">
									<div class="input-group float-left">
										<input type="date" id="dateFrom"
											class="text-field text-field-with-addon float-left">
											 <input
											type=hidden id="hiddateFrom" name="dateFrom" /><span
											class="addon"> 
											<span class="calendar-icon"> </span>
										</span>
									</div>

									<span class="add-on float-left float-text">to</span>
									<div class="input-group float-right">
										<input type="date" id="dateTo"
											class="text-field text-field-with-addon float-right"> 
											<input type=hidden id="hiddateTo" name="dateTo" /> <span
											class="addon"> <span class="calendar-icon"> </span>
										</span>
									</div>
								</div>
							</div>

							<div class="col-md-2">
								<span class="blue-text">Application SNo</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="applNo" name="applNo" type="text" class="text-field branch" /> 
								</div>
							</div>
							<div class="col-md-2">
								<span class="blue-text">Product Code</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="product" name="product" type="text" class="text-field product" />
								</div>
							</div>

							<div class="col-md-2">
								<span class="blue-text">Bank Code</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="bank" name="bank" type="text" class="text-field bank" /> 
								</div>
							</div>
						</div>
						<br> <br> <br>
						<div class="second-line">

							<div class="col-md-2">
								<span class="blue-text">Home Branch Code</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="branch" name="branch" type="text" class="text-field branch" /> 
								</div>
							</div>
							
							<div class="col-md-2">
								<span class="blue-text">Issue Branch Code</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="issuebranch" name="issuebranch" type="text" class="text-field branch" />
								</div>
							</div>
							<div class="col-md-2">
								<span class="blue-text">Customer Id</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="customerId" name="customerId" type="text" class="text-field customer" /> 
								</div>
							</div>


							<div class="col-md-2">
								<span class="blue-text">Mobile Number #</span>
								<div class="select-menu select-menu-native select-menu-light">
									<input id="mobile" name="mobile" type="text" class="text-field mobile" /> 
								</div>
							</div>

							<div class="col-md-2">
								<span class="blue-text">Record Status</span>
								<div id="checkboxSelectCombo"></div>
							</div>
							<div class="clear">
								<input id="hidstatus" type=hidden name="status" />
							</div>
							
							
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="row">
						<div class="right-buttons">

							<input type="submit" class="btn btn-l btn-blue" onclick="searchParams()" value="Search"/>
							<button type="reset" class="btn btn-l">Reset</button>
							
						</div>
					</div>
					<div class="clear"></div>
					<div class="row">
						<div class="table-header">
							<!-- 							<h3>Search Results :24  Records</h3> -->

							<p id="demo"></p>

							<!-- <table> -->

							</div>
							<div class="clear"></div>
							<div id="tableid">
								<c:choose> 
									<c:when test="${msg ne null }">
										<h2><center><c:out value = "${msg}"/></center></h2>
									</c:when>
									<c:otherwise>
										<h3>Search Results : <c:out value = "${records.size()}"/></h3>
									</c:otherwise>
								</c:choose>
								<br><br><br>
								<table class="table">
									<thead>
										<tr>
<!-- 										<input id="selectall" class="selectall" type="checkbox" name="selectall" /> -->
										

											<th>Account</th>
											<th>Card holder name</th>
											<th>Branch Co</th>
											<th>Product Code</th>
											<th>Card Status</th>
											<th>Approval Status</th>
											<th>Pin Status</th>
											<th>RSN</th>
										</tr>
									</thead>
									<tbody>
										<% int rowCount =0; %>
										<c:forEach var="listValue" items="${records}">

											<tr>
<!-- 												<td> -->
<!-- <!-- 												<input id="changeId" class="messageCheckbox" -->
<!-- <!-- 													type="checkbox" name="changestatus" --> 
<%-- <%-- 													value="${listValue.creditCardDetailsId}" /> --%>
<!-- 												<div> -->
<!-- 														<input id="hidstatus" type=hidden name="status" /> -->
<!-- 													</div></td> -->
												<td><a
													href="/GnD/search/record?creditCardDetailsId=${listValue.creditCardDetailsId}">${listValue.primaryAcctNo}</a></td>
												<td>${listValue.embossName}</td>
												<td>${listValue.homeBranchCode}</td>
												<td>${listValue.product}</td>
												<td>${listValue.cardStatus}</td>
												<td>${listValue.recordStatus}</td>
												<td>${listValue.pinStatusString}</td>
												<td>${listValue.rsn}</td>
												<%rowCount++; %>
											

										</c:forEach>

									</tbody>
								</table>
							</div>


						</div>
				</form>
						<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>
	
			</div>
			<!--</div>-->
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

	$('#selectall').click(function() {
	    $(this.form.elements).filter(':checkbox').prop('checked', this.checked);
	});

	var x=new String();

		
			var colors = [ {
			Name : "All",Value : "0"
		}, {
			Name : "REJECTED",Value : "1"
		}, {
			Name : "HOLD",Value : "2"
		}, {
			Name : "APPROVED",Value : "3"
		} ,{
			Name : "AUF_CONVERTED",Value : "5"
		}];

		$(function() {

			$("#checkboxSelectCombo").igCombo({
				width : "166px",
				dataSource : colors,
				textKey : "Name",
				valueKey : "Value",
				multiSelection : {
					enabled : true,
					showCheckboxes : true
				},
				selectedItems: [
				                {
				                    index: 1 
				                }],
				selectionChanged: function (evt, ui){
				                }
			
			});
			$("#checkboxSelectCombo1").igCombo({
				width : "166px",
				dataSource : colors,
				textKey : "Name",
				valueKey : "Value",
				multiSelection : {
					enabled : true,
					showCheckboxes : true
				},
				selectedItems: [
			                {
			                    index: 1 
			                }],
				selectionChanged: function (evt, ui){
                }
			});
		});
		
		$(document).delegate("#checkboxSelectCombo", "igcomboselectionchanged", function (evt, ui) {
		var itemStr='';
            if (ui.items && ui.items[0]) {
                var items = ui.items;
                for (var index in items) {
                	itemStr =itemStr+items[index].data.Value+",";
                }
                
            }
//             alert(itemStr);
            document.getElementById("hidstatus").value = itemStr;
        });
		
				
		function validCheck() {
			
			var fromdate = document.getElementById("dateFrom").value;
			var todate = document.getElementById("dateTo").value;
			var status  =document.getElementById("hidstatus").value;
			
			var pinawb = document.getElementById("pinawb").value;
			
			 
			 var cardawb = document.getElementById("cardawb").value;
			 
			 var rsn = document.getElementById("rsn").value;
			 
			 var acctNo = document.getElementById("acctNo").value;
			 
			 var applNo = document.getElementById("applNo").value;
			 
			 var dateFrom = document.getElementById("dateFrom").value;
			 
			 var dateTo = document.getElementById("dateTo").value;
			 
			 var product = document.getElementById("product").value;
			 
			 var mobile = document.getElementById("mobile").value;
			 
			 var bank = document.getElementById("bank").value;
			
			 var branch = document.getElementById("branch").value;
			 
			 var issuebranch = document.getElementById("issuebranch").value;
			 
			 var customerId = document.getElementById("customerId").value;

			if(pinawb=="" && cardawb=="" && rsn==""&& acctNo==""&&product==""&&mobile==""&&bank==""&&branch=="" && status =="" && fromdate=="" && todate=="" && customerId=="" && issuebranch=="") {
				alert("Please fill atleast one search creteria");
				return false;
			}
		}

		
		function searchParams(){
			
			
			 var pinawb = document.getElementById("pinawb").value;
			 document.getElementById("hidpinawb").value =pinawb;
			 
			 var cardawb = document.getElementById("cardawb").value;
			 document.getElementById("hidcardawb").value =cardawb;
			 
			 var rsn = document.getElementById("rsn").value;
			 document.getElementById("hidrsn").value =rsn;
			 
			 var acctNo = document.getElementById("acctNo").value;
			 document.getElementById("hidacctNo").value =acctNo;
			 
			 var applNo = document.getElementById("applNo").value;
			 document.getElementById("hidapplNo").value =applNo;
			 
			 var dateFrom = document.getElementById("dateFrom").value;
			 document.getElementById("hiddateFrom").value =dateFrom;
			 
			 var dateTo = document.getElementById("dateTo").value;
			 document.getElementById("hiddateTo").value =dateTo;
			 
			 var product = document.getElementById("product").value;
			 document.getElementById("hidproduct").value =product;
			 
			 var mobile = document.getElementById("mobile").value;
			 document.getElementById("hidmobile").value =mobile;
			 
			 var bank = document.getElementById("bank").value;
			document.getElementById("hidbank").value =bank;
			
			 var branch = document.getElementById("branch").value;
				document.getElementById("hidbranch").value =branch;
				
				
		}
		 
	</script>

</body>
</html>