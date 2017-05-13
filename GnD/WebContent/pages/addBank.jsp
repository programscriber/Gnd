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
        table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}
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
    height: 90%;
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
                    	<li id="icons" ><a href="<c:url value='/loginuser/recordsearch'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                    <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                        --%>
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
                    <h2 class="blue-text">Add New Bank</h2>
                    										
                    <form id="addbranch" action="/GnD/master/addBank" method="post">
                        <div class="col-md-12">

                            <div class="col-md-6">
<!--                                 <div class="col-md-12"> -->
<!--                                     <span class="blue-text" >Bank ID</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
<!-- 								<input type="text" name="bankid" class="text-field" required/>  -->
                                      
<!--                                     </div>     -->
<!--                                 </div>        -->
                                  <div class="col-md-12">
                                    <span class="blue-text" >Bank Id</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="bankid" type="text" class="text-field" name="bank" required/>
                                     
                                    </div>    
                                </div>
                                 
                                 <div class="col-md-12">
                                    <span class="blue-text" >Bank Name</span>
                                      <div class="select-menu select-menu-native select-menu-light">
                                        <input id="bankNameid" type="text" class="text-field" name="bankName" required/>
                                    </div>    
                                </div>
                                 <div class="col-md-12">
                                    <span class="blue-text" >Short code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="shortcodeid" type="text" class="text-field" name="shortcode" required/>
                                       
                                    </div>    
                                </div>
                                <div class="col-md-12">
                                    <span class="blue-text" >Status</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                       <select name="status" id="status123">
                                       <option value="-1" disabled selected style="display: none;"> select Status..</option> 
									<c:forEach var="statusVal" items="${status}">
                                            <option value="${statusVal.key}">${statusVal.value}</option>
                                    </c:forEach>
                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                        
                                    </div>    
                                </div> 
                           
                                       <div class="col-md-12">
                                    <span class="blue-text" >Bank code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="bankcodeid" type="text" class="text-field" name="bankcode" required/>
                                    </div>     
                                </div> 
                				<div class="col-md-12">
                                    <span class="blue-text" >Prefix</span>
                                     <div class="select-menu select-menu-native select-menu-light">
                                   <input id="prefixid" type="text" class="text-field" name="prefix" required/>
                                       
                                    </div>      
                                </div>       
                                <div class="col-md-12">
                                    <span class="blue-text">LP AWB Branch Group</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                       <select name="lpawb" id="lpawbid">
                                        <option value="-1" disabled selected style="display: none;">select LP AWB Branch Group..</option> 
									<c:forEach var="statusVal" items="${yesNo}">
                                            <option value="${statusVal.key}">${statusVal.value}</option>
                                    </c:forEach>
                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                        
                                    </div>    
                                </div> 
                                  <div class="col-md-12">
                                    <span class="blue-text">AUF Format</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                       <select name="aufformat" id="aufformatid">
                                        <option value="-1" disabled selected style="display: none;">select AUF format..</option> 
									<c:forEach var="statusVal" items="${aufFormat}">
                                            <option value="${statusVal.key}">${statusVal.value}</option>
                                    </c:forEach>
                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                        
                                    </div>    
                                </div>
                                  <div class="center-buttons">
                           
                            <input id=myBtn type="button"  class="btn btn-l btn-blue" value="SAVE"/>
<!-- 	javascript:openwindow()								<input type="submit"  class="btn btn-l btn-blue">  -->
<!--                                 <button id="submit" type="submit" class="btn btn-l">Save</button> -->
                                <button type="reset" class="btn btn-l">Reset</button>
                            </div>
                            </div>        
                            
                           
                        </div>
                    </form> 
                     
                </div>    
                
				<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>

<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">×</span>
    <table >
    <tr><th>Field</th><th>Values</th></tr>
  	<tr>
    <td>Bank Id</td>
    <td><p id="bank"></p></td>
    </tr>
    <tr>
    <td>Bank Name</td>
    <td> <p id="bankName"></td>
    </tr>
    <tr>
    <td>Short code</td>
    <td><p id="shortcode"></td>
    </tr>
    <tr>
    <td>Status</td>
    <td><p id="Status"></td>
    </tr>
    <tr>
    <td>Bank code</td>
    <td><p id="bankcode"></td>
    </tr>
    <tr>
    <td>Prefix</td>
    <td><p id="Prefix"></td>
    </tr>
    <tr>
    <td>LP AWB Branch Group</td>
    <td><p id="lpawb"></td>
    </tr>
    <tr>
    <td>AUF Format</td>
    <td><p id="auffor"></td>
    </tr>
    </table>
    <center><button class="btn btn-l btn-blue" onclick="return saveBank()">SAVE</button></center>
    
  </div>

</div>
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
        
        var modal = document.getElementById('myModal');

     // Get the button that opens the modal
     var btn = document.getElementById("myBtn");

     // Get the <span> element that closes the modal
     var span = document.getElementsByClassName("close")[0];

     // When the user clicks the button, open the modal 
     btn.onclick = function() {
    	$('#bank').text($('#bankid').val());
    	$('#bankName').text($('#bankNameid').val());
    	$('#shortcode').text($('#shortcodeid').val());
    	$('#bankcode').text($('#bankcodeid').val());
    	$('#Prefix').text($('#prefixid').val());
    	$('#lpawb').text($('#lpawbid :selected').text());
    	$('#auffor').text($('#aufformatid :selected').text());
    	$('#Status').text($('#status123 :selected').text());
    	if(($('#bankid').val()&&$('#bankNameid').val()&&$('#shortcodeid').val()&&$('#status123 :selected').val()&&$('#bankcodeid').val()&&$('#prefixid').val()&&$('#lpawbid :selected').val()&&$('#aufformatid :selected').val())==''||($('#bankid').val()&&$('#bankNameid').val()&&$('#shortcodeid').val()&&$('#status123 :selected').val()&&$('#bankcodeid').val()&&$('#prefixid').val()&&$('#lpawbid :selected').val()&&$('#aufformatid :selected').val())==-1){
    		alert("fields should not be empty");
    		
    	}else{
         modal.style.display = "block";
    	}
         
     }

     // When the user clicks on <span> (x), close the modal
     span.onclick = function() {
         modal.style.display = "none";
     }

     // When the user clicks anywhere outside of the modal, close it
     window.onclick = function(event) {
         if (event.target == modal) {
             modal.style.display = "none";
         }
     }
  
     function saveBank(){
    	 
    	if(($('#bankid').val()&&$('#bankNameid').val()&&$('#shortcodeid').val()&&$('#status123 :selected').val()&&$('#bankcodeid').val()&&$('#prefixid').val()&&$('#lpawbid :selected').val()&&$('#aufformatid :selected').val())==''||($('#bankid').val()&&$('#bankNameid').val()&&$('#shortcodeid').val()&&$('#status123 :selected').val()&&$('#bankcodeid').val()&&$('#prefixid').val()&&$('#lpawbid :selected').val()&&$('#aufformatid :selected').val())==-1){
    		alert("fields should not be empty");
    		
    	}else{
     	  $.ajax({
  			url: "/GnD/master/addBankTo?bank="+$('#bankid').val()+"&bankName="+$('#bankNameid').val()+"&shortcode="+$('#shortcodeid').val()+"&status="+$('#status123 :selected').val()+"&bankcode="+$('#bankcodeid').val()+"&prefix="+$('#prefixid').val()+"&lpawb="+$('#lpawbid :selected').val()+"&aufformat="+$('#aufformatid :selected').val(),
  			cache: false,
  			method:"post",
  			success: function(data){
  				if(data){
  				alert("Bank added sucessfully");
  				 modal.style.display = "none";
  				}else{
  					alert("Bank already exits");
  					 modal.style.display = "none";
  				}
  			},
  			  error: function(){						
  				alert('Error while request..');
  			} 
  		});  
    	}
     }
     

        </script>
    </body>
</html>