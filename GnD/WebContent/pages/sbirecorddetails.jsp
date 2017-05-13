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
<link href="<c:url value='/resources/css/recorddetails.css'/>"
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
        </style>

        <div class="page-header">
            <div class="page-header-wrapper container">
                <div class="main-logo">
                    Giesecke &amp; Devrient
                </div>
<!--                 <div class="tab-menu dark"> -->


<!--                 </div>  -->
               <div class="adress-text">
				
				</div>
			<p style="float: right; margin-top: 0px;"></p>                
            </div>
        </div>
        <div class="main" style="">
            <div class="container main-conten">  
        <button class="btn  btn-blue back-button" onclick="history.back()">Back</button>
                <div class="right-content record">
                    <h2 class="blue-text">Record Details</h2>
                    <form>
                            <table cellspacing="8" cellpadding="20"> 
                            <div id="bold">
                            <tr>
                            <td style="font-weight:bold" align="left" >Emboss / Card Name</td>
                            <td style="font-weight:bold" align="left">Product</td>
                            <td style="font-weight:bold" align="left">Approval Status</td>
                            </tr>
<!--                         <div class="row right-con"> -->
<!--                             <div class="col-md-4"> -->
<!--                                 <span class="blue-text" >Emboss / Card Name</span> -->
								<tr>
<!--                                 <div class="select-menu select-menu-native select-menu-light"> -->
                                  <td>  <input type="text" class="text-field product" value="${record.customer.embossName}" readonly  style="width: 300px;padding: 5px;"/></td>
<!--                                 </div>  -->
                                                  
<!--                             </div> -->
<!--                             <div class="col-md-4"> -->
<!--                                 <span class="blue-text" >Product</span> -->
<!--                                 <div class="select-menu select-menu-native select-menu-light"> -->
                                 <td>   <input type="text" class="text-field" value="${record.customer.product}" readonly style="width: 250px;padding: 5px;"/></td>
<!--                                 </div>                         -->
<!--                             </div> -->
<!--                             <div class="col-md-4"> -->
<!-- <!--                                 <span class="blue-text" >Approval Status</span> --> 
<!--                                 <div class="select-menu select-menu-native select-menu-light"> -->
                               <td>     <input type="text" class="text-field" value="${record.customer.recordStatus}" readonly style="width: 300px;padding: 5px;"/></td>
<!--                                 </div>  -->
                                </tr>                       
<!--                             </div> -->
                            
                          
                             <tr>
                            <td style="font-weight:bold" align="left">Card Number</td>
                            <td style="font-weight:bold" align="left">BIN</td>
                            <td style="font-weight:bold" align="left">Address</td>
                            </tr>
                            <tr>
<!--                             <div class="second-line"> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Card Number</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                 <td>       <input type="text" class="text-field product" value="${record.customer.cardNumber}" readonly style="width: 300px;padding: 5px;"/></td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Bank Code</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                 <td>       <input type="text" class="text-field" value="${record.customer.bank}"  readonly style="width: 250px;padding: 5px;"/></td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Address</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                    <td>    
<%--                                     <input type="text" class="text-field" value="${record.customer.address}" readonly style="width: 300px;padding: 5px;"/> --%>
                                   	
                                   <textarea class="text-field" readonly>${record.customer.address}&#10;${record.customer.address2}&#10;${record.customer.address3}&#10;${record.customer.address4}&#10;${record.customer.mobileNo}</textarea> 
                                    </td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                             </div> -->
                            </tr>
                           
                             <tr>
                            <td style="font-weight:bold" align="left">Home Branch Code</td>
                            <td style="font-weight:bold" align="left">Issue Branch Code</td>
                            <td style="font-weight:bold" align="left">Dispatch Branch Code</td>
                            </tr>
                            <tr>
<!--                             <div class="second-line"> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Card Dispatch AWB #</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                   <td>     <input type="text" class="text-field product" value="${record.customer.branch}" readonly style="width: 300px;padding: 5px;"/></td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Branch Code</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                    <td>    <input type="text" class="text-field" value="${record.customer.issueBranch}" readonly style="width: 250px;padding: 5px;"/></td>
<!--                                     </div>                      -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Branch City</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                   <td>     <input type="text" class="text-field" value="${record.customer.processedBranch}" readonly style="width: 300px;padding: 5px;"/></td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                             </div> -->
                            </tr>
                           
                             <tr>
                            <td style="font-weight:bold">Bank</td>
                            <td style="font-weight:bold"> Card AWB</td> 
                            <td style="font-weight:bold">Pin AWB</td>
                                                  
                            </tr>
                            <tr>
<!--                             <div class="second-line"> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Pin Dispatch AWB #</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                 <td><input type="text" class="text-field product" value="${record.customer.bankName}" readonly /></td>
                                    <td ><input type="text" class="text-field" value="${record.customer.cardDispatchAWB}" readonly /></td>
									<td ><input type="text" class="text-field" value="${record.customer.pinDispatchAWB}" readonly /></td>
<!--                             <td></td> -->
<!--                                     <td></td> -->
<!--                                     <td></td> -->
<!--                                     <td></td> -->
<!--                                     </div>                         -->
<!--                                 </div> -->
<!-- 								<div class="second-line"> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Pincode</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                 
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                         </div> -->
                        </tr>   
                         <tr>
                            <td style="font-weight:bold" align="left">Pin Code</td>
                            <td style="font-weight:bold" align="left">RSN</td>
                            <td style="font-weight:bold" align="left">Customer Id</td>
                            </tr>
                            <tr>
<!--                             <div class="second-line"> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Card Dispatch AWB #</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                   <td>     <input type="text" class="text-field product" value="${record.customer.pincode}" readonly style="width: 300px;padding: 5px;"/></td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Branch Code</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                    <td>    <input type="text" class="text-field" value="${record.customer.rsn}" readonly style="width: 250px;padding: 5px;"/></td>
<!--                                     </div>                      -->
<!--                                 </div> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <span class="blue-text" >Branch City</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
                                   <td>     <input type="text" class="text-field" value="${record.customer.customerId}" readonly style="width: 300px;padding: 5px;"/></td>
<!--                                     </div>                         -->
<!--                                 </div> -->
<!--                             </div> -->
                            </tr>
                          </div>
                      </table>
                    
                    <div class="clear"></div>
                    <div class="table-header">
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

							</c:forEach>
                        </tbody>
                    </table>
                    </form>
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
       
    </body>
    <script type="text/javascript">
    $(document).ready(function(){
    	$('#back').click(function(){
    		parent.history.back();
    		return false;
    	});
    });
    </script>
</html>