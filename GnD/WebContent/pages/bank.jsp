<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class=" js backgroundsize cssanimations csstransitions pointerevents" lang="en"><head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">

        <title>G & D</title>
        <link href="<c:url value='/resources/css/blazer.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
        <link href="<c:url value='/resources/css/style.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
                
        <script type="text/javascript" src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/jquery-1.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/blazer.js'/>"></script>
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css'/>">
        <script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
        <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
        <script type="text/javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/json2.js"></script>
    </head>

    <body>
        <style>
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 50%;
    height: 120%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
.statemodal{
	display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */

}
 
 
 .statemodal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 50%;
    height: 50%;
}

.districtmodal{
	display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */

}
 
 
 .districtmodal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 50%;
    height: 50%;
}

            .col-md-12{
                padding: 10px;
            }

            .center-buttons{
                text-align: center;
                margin-bottom: 0px;
                padding-bottom: 10px;
            }
            .right-content {
                padding-bottom: 0em;
            }


        </style>
        <div class="page-header">
            <div class="page-header-wrapper container">
                <div class="main-logo">
                    Giesecke &amp; Devrient

                </div>

                <div class="tab-menu dark">
                   <ul>
                   <li id="icons"><a href="<c:url value='/loginuser/home'/>">Home</a></li>
					<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
                         
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                        --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
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
                            	
                               <li >
                                    <a href="<c:url value='/master/branchSearch'/>">Branch</a>
                                </li>
                                <li class="selected">
                                    <a href="<c:url value='/master/bank'/>">Bank</a>
                                </li>
                                <li >
                                    <a href="<c:url value='/upload/getProduct'/>">Products</a>
                                </li>
                                <li >
                                    <a href="/GnD/upload/getserviceprovider">AWB</a>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>


                <div class="right-content record">
                 <button id="statemybttn" class="btn btn-l btn-blue" onclick="window.location.href='/GnD/master/addbank';">Add Bank</button>										
                                 <div class="col-md-12">
                                    <span class="blue-text" >Bank Name</span>
                                      <div class="select-menu select-menu-native select-menu-light">
                                        <select id="bankNameid" name="bank">
                                     <option value="-1" disabled selected style="display: none;"> Select Bank..</option> 
									<c:forEach var="critMasBankVal" items="${bankdetails}">
                                            <option value="${critMasBankVal.bankId}">${critMasBankVal.shortCode}</option>
                                    </c:forEach>
                                      <option value="true">ALL</option>   

                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                    </div>    
                                </div>
                                   <table class="table" id="banktabl"></table>   
                        </div>        
                        <div class="clear"></div>  
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

        <script>
   	  $("#bankNameid").change(function() {
   		 var tabledata = '<tr><th>Bank Id</th><th>Bank Name</th><th>Short Code</th><th>Status</th><th>Bank code</th><th>Prefix</th><th>AUF Format</th></tr>';
//    		<th>LP AWB Branch group</th><td>'+data[x].lPAWBBranchGroup+'</td>''
		  var bank=$('#bankNameid').val();
		  $('#banktabl').empty();
		  $.ajax({
			 type: "post",
    		 url: "/GnD/master/getDetailsbank?bank="+bank,
    		 cache: false,				
				 success: function(data){
					 if(data.length>0){
						 
       		            for (var x = 0; x < data.length; x++) {
       		            	tabledata += '<tr><td>'+ data[x].bankId + '</td><td>'+ data[x].bankName + '</td><td>'+ data[x].shortCode + '</td><td>'+ data[x].statusString + '</td><td>'+ data[x].bankCode + '</td><td>'+ data[x].prefix + '</td><td>'+ data[x].aufFormat + '</td></tr>';
       		            }
       		          $('#banktabl').html(tabledata);
       		            }else{
       		            	alert("No Bank is present");
       		            	$('#banktabl').empty();
       		            	
       		            }
				},
				  error: function(){						
					alert('Error while request..');
				} 
				 });
	  });
      
        </script>
    </body>
</html>