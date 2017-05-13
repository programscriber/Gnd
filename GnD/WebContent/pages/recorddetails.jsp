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
    
        <style>
            .right-content{
                /*text-align: center;*/
                margin-left: auto;
                margin-right: auto;
                left: 18%;
                float: none;
                border-left: medium transparent;
            }
            .table-header {
                width: 100%;
            }
            .col-md-4{
                width: 33.333%;
            }
            textarea {
   				 width: 280px;
  				  height: 62px;
}
        </style>

        <div class="page-header">
            <div class="page-header-wrapper container">
                <div class="main-logo">
                    Giesecke &amp; Devrient
                </div>
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
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                       --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if>  
					   <c:if test="${username eq 'shopfloor'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					   <c:if test="${username eq 'warehouse'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if> 
					  <c:if test="${username eq 'rto'}">
						  <li id="prototype"  ><a href="/GnD/shopfloor/home">RTO</a></li>
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
					<div class="navigation">
						<ul>
							<li class="selected" ><a href="/GnD/loginuser/recordsearch">Card/Pin Search</a></li>
							<li><a href="/GnD/search/filesearch">File Search</a></li>
						</ul>
					</div>
				</div>
			</div>
                <div class="right-content record">
                	 <input type="button"  onclick="history.back()" value ="Back"/>
                    <h2 class="blue-text">Record Details</h2>
                    <form>
                                  
                        <div class="row right-con">
                            <div class="col-md-4">
                                <span class="blue-text" >Emboss / Card Name</span>
                                <div class="select-menu select-menu-native select-menu-light">
                                    <input type="text" class="text-field product" value="${record.customer.embossName}" readonly />
                                </div>                        
                            </div>
                            <div class="col-md-4">
                                <span class="blue-text" >Product</span>
                                <div class="select-menu select-menu-native select-menu-light">
                                    <input type="text" class="text-field" value="${record.customer.product}" readonly />
                                </div>                        
                            </div>
                            <div class="col-md-4">
                                <span class="blue-text" >Approval Status</span>
                                <div class="select-menu select-menu-native select-menu-light">
                                    <input type="text" class="text-field" value="${record.customer.recordStatus}" readonly />
                                </div>                        
                            </div>
                            <br><br><br>
                            <div class="second-line">
                                <div class="col-md-4">
                                    <span class="blue-text" >Card Number</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field product" value="${record.customer.cardNumber}" readonly />
                                    </div>                        
                                </div>
                                <div class="col-md-4">
                                    <span class="blue-text" >Bin</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.bank}"  readonly />
                                    </div>                        
                                </div>
 <%--                               <div class="col-md-4">
                                    <span class="blue-text" >Address</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.address}" readonly />
                                    </div>                        
                                </div> --%>
                                <div class="select-menu select-menu-native select-menu-light">
                               				 <textarea readonly>${record.customer.address}&#10;${record.customer.address2}&#10;${record.customer.address3}&#10;${record.customer.address4}&#10;${record.customer.mobileNo}</textarea> 
                                </div>
                            </div>
                            <div class="second-line">
                                <div class="col-md-4">
                                    <span class="blue-text" >Home Branch Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.branch}" readonly />
                                    </div>                     
                                </div>
                                <div class="col-md-4">
                                    <span class="blue-text" >Issue Branch Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.issueBranch}" readonly />
                                    </div>                     
                                </div>
                                 <div class="col-md-4">
                                    <span class="blue-text" >Dispatch Branch Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.processedBranch}" readonly />
                                    </div>                     
                                </div>
                                </div>
                                <br><br><br>
                                 <div class="second-line">
                                <div class="col-md-4">
                                    <span class="blue-text" >Bank</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.bankName}" readonly />
                                    </div>                        
                                </div>
                                <div class="col-md-4">
                                    <span class="blue-text" >Card Dispatch AWB #</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field product" value="${record.customer.cardDispatchAWB}" readonly/>
                                    </div>                        
                                </div>
                                <div class="col-md-4">
                                    <span class="blue-text" >Pin Dispatch AWB #</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field product" value="${record.customer.pinDispatchAWB}" readonly />
                                    </div>                        
                                </div>
                                <br><br><br>
								<div class="second-line">
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Event Date</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
<%--                                         <input type="text" class="text-field product" value="${record.eventDate}"/> --%>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Description</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
<%--                                         <input type="text" class="text-field" value="${record.description}"/> --%>
<!--                                     </div>                         -->
<!--                                 </div> -->
                                <div class="col-md-4">
                                    <span class="blue-text" >Pincode</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.pincode}" readonly />
                                    </div>                        
                                </div>
                                 <div class="col-md-4">
                                    <span class="blue-text" >RSN</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.rsn}" readonly />
                                    </div>                        
                                </div>
                                <div class="col-md-4">
                                    <span class="blue-text" >Customer Id</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input type="text" class="text-field" value="${record.customer.customerId}" readonly />
                                    </div>                        
                                </div>
                            </div>
                        </div>   
                      
                    <div class="clear"></div>
                    <div class="table-header">
<!--                         <h3>Search Results : 24 Records</h3> -->
<!--                         <button class="btn btn-only-img btn-only-img1 btn-s" type="button"> -->
<%--                             <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print"> --%>
<!--                         </button> -->
                    </div>
                    <div class="clear"></div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Date / Time</th>
                                <th>Process Step</th>
                                <th>Result</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach var="record" items="${record.event}">
                            <tr>
                            	<td>${record.eventDate}</td>
                            	<td>${record.eventId}</td>
                            	<td>${record.description}</td>
<!--                                 <td><a href="record-details.html">22/11/2016</a></td> -->
<!--                                 <td><a href="record-details.html">Core File Received</a></td> -->
<!--                                 <td><a href="record-details.html">UniCredit Maestro Plus</a></td> -->
                            </tr>                        
<!--                             <tr> -->
<!--                                 <td><a href="record-details.html">22/11/2016</a></td> -->
<!--                                 <td><a href="record-details.html">Core File Received</a></td> -->
<!--                                 <td><a href="record-details.html">UniCredit Maestro Plus</a></td> -->
<!--                             </tr>                         -->
<!--                             <tr> -->
<!--                                 <td><a href="record-details.html">22/11/2016</a></td> -->
<!--                                 <td><a href="record-details.html">Core File Received</a></td> -->
<!--                                 <td><a href="record-details.html">UniCredit Maestro Plus</a></td> -->
<!--                             </tr>                         -->
							</c:forEach>
                        </tbody>
                    </table>
                    </form>
           <!--          <div class="pagination">
                        <ul>
                            <li><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li class="selected"><a href="#">5</a></li>
                            <li><a href="#">6</a></li>
                            <li><a href="#">7</a></li>
                            <li><a href="#">8</a></li>
                            <li><a href="#">9</a></li>
                            <li><a href="#">10</a></li>
                            <li><a href="#">20</a></li>
                            <li><a href="#">30</a></li>
                            <li><a href="#">100</a></li>
                            <li><a href="#">200</a></li>
                            <li class="meta-navigation"><a href="#">Next ></a></li>
                        </ul>
                    </div> -->
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
                        <div class="footer-logo">
                        </div>
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