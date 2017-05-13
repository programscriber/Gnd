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
                    	<li id="icons" ><a href="<c:url value='/loginuser/recordsearch'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
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
                               <li class="selected">
                                    <a href="<c:url value='/master/branchSearch'/>">Branch</a>
                                </li>
                                <li>
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
                 <button id="statemybttn" class="btn btn-l btn-blue" >Add State</button>
                 <input type="button" id="districtmybtn" class="btn btn-l btn-blue" onclick="getstatebyadddist();" value="Add District"/>
                    <h2 class="blue-text">Add New Branch</h2>
                    										
                    <form id="addbranch" action="/GnD/master/addBranchTo" method="post">
                        <div class="col-md-12">

                            <div class="col-md-6">
<!--                                 <div class="col-md-12"> -->
<!--                                     <span class="blue-text" >Bank ID</span> -->
<!--                                     <div class="select-menu select-menu-native select-menu-light"> -->
<!-- 								<input type="text" name="bankid" class="text-field" required/>  -->
                                      
<!--                                     </div>     -->
<!--                                 </div>        -->
                                  <div class="col-md-12">
                                    <span class="blue-text" >Branch short code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="branchshortcodeid" type="text" class="text-field" name="branchshortcode" required/>
                                     
                                    </div>    
                                </div>
                                 
                                 <div class="col-md-12">
                                    <span class="blue-text" >Bank Name</span>
                                      <div class="select-menu select-menu-native select-menu-light">
                                        <select id="bankName" name="bank">
                                      <option>%%</option>
									<c:forEach var="critMasBankVal" items="${MasBank}">
                                            <option value="${critMasBankVal.bankId}">${critMasBankVal.shortCode}</option>
                                            </c:forEach>
                                         

                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                    </div>    
                                </div>
                                 <div class="col-md-12">
                                    <span class="blue-text" >Lcpc code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="lcpcCode" type="text" class="text-field" name="lcpcCode" required/>
                                       
                                    </div>    
                                </div>
                                <div class="col-md-12">
                                    <span class="blue-text" >Address line 1</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="addressLine1" type="text" class="text-field" name="address1" required/>
                                        
                                    </div>    
                                </div> 
                           
                                       <div class="col-md-12">
                                    <span class="blue-text" >Address line 3</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="addressLine3" type="text" class="text-field" name="addres3" required/>
                                    </div>     
                                </div> 
                		<div class="col-md-12">
                                    <span class="blue-text" >State Code</span>
                                     <div class="select-menu select-menu-native select-menu-light">
                                        <select name="state" id="state">
                                        <option placeHolder="State Code"></option>
									<c:forEach var="critMasStateVal" items="${state}">
                                            <option value="${critMasStateVal.stateCode}">${critMasStateVal.stateName}</option>
                                            </c:forEach>
                                         

                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                       
                                    </div>      
                                </div>       
                                <div class="col-md-12">
                                    <span class="blue-text" >pin code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="pincode" type="text" class="text-field" name="pincode" required/>
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>    
                                </div> 
                                 
                            </div>        
                            <div class="col-md-6">
                        <div class="col-md-12">
                                    <span class="blue-text"  >Branch Name</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="branchName" type="text" class="text-field" name="branchName" required/>
                                    </div>     
                                </div>
                                  <div class="col-md-12">
                                    <span class="blue-text" >Status</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                         <select name="status" id="status123">
                                        <option>%%</option>
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
                                    <span class="blue-text" >Lcpc Branch</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="lcpcbranch" type="text" class="text-field" name="lcpcbranch" required/>
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>    
                                </div>
                                
                                     <div class="col-md-12">
                                    <span class="blue-text" >Address line 2</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                       <input id="addressLine2" type="text" class="text-field" name="address2" required/>
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>    
                                </div>
                                 <div class="col-md-12">
                                    <span class="blue-text" >Address line 4</span>
                                   <div class="select-menu select-menu-native select-menu-light">
                                         <input id="addressLine4" type="text" class="text-field" name="addres4" required/>
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>      
                                </div>
                   <div class="col-md-12">
                                    <span class="blue-text" >District Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                       <div class="select-menu select-menu-native select-menu-light">
                                        <select name="district" id="district">
                                       
									
                                         

                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                    </div> 
<!--                                         <input type="text" id="district" class="text-field" name="dcode"/> -->
<!--                                         <span class="addon"> -->
<!--                                           onclick="getdistrict()"   <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>    
                                </div>            
                               
                                <div class="col-md-12">
                                    <span class="blue-text" >phone</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                         <input id="phoneNum" type="text" class="text-field" name="phone" required/>
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>    
                                </div>  
                                      
                            </div>  
                            
                            <div class="col-md-6">
                            	<div class="col-md-12">
                                    <span class="blue-text" >email</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="email" type="text" class="text-field " name="email" width="50px" required/>
<!--                                         <span class="addon"> -->
<!--                                             <span class="select-menu-icon"></span> -->
<!--                                         </span> -->
                                    </div>
                                    </div>
                                  
                                </div>
                           
                              <div class="col-md-6">
                               <span class="blue-text" >isNonCardIssueBranch</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                         <select name="isNonCardIssueBranch" id="isNonCardIssueBranchid">                                 
									<c:forEach var="statusVal" items="${yesNo}">
                                            <option value="${statusVal.key}">${statusVal.value}</option>
                                    </c:forEach>
                                        </select>
                                        <span class="addon">
                                            <span class="select-menu-icon"></span>
                                        </span>
                                    </div>    

                                </div>
                            
                                 
                        </div>        
                        <div class="clear"></div>
                        <div class="row">
                            <div class="center-buttons">
                           
                            <input id="myBtn" type="button"  class="btn btn-l btn-blue" value="SAVE"/>
<!-- 	javascript:openwindow()								<input type="submit"  class="btn btn-l btn-blue">  -->
<!--                                 <button id="submit" type="submit" class="btn btn-l">Save</button> -->
                                <button type="reset" class="btn btn-l">Reset</button>
                            </div>
                        </div>
                    </form> 
                     
                </div>    
                
				<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>


            </div>
        </div>
        <div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">×</span>
  
    <p>Branch Details</p>
    <table>
    <tr>
    <th>Field Name</th>
    <th>Values</th>
    </tr>
    <tr>
    <th>Branch short code</th><td><p id="namesubWin" ></p></td>
    </tr>
    <tr>
	<th>Branch Name</th><td><p id="namesubWinbranhName" ></td>
	</tr>
	<tr>
	<th>Bank Name</th><td><p id="namesubWinbank" ></p></td>
	</tr>
	<tr>
	<th>Status</th><td><p id="namesubWinstatus" ></p></td>
	</tr>
	<tr>
	<th>Lcpc code</th><td><p id="namesubWinlcpccode" ></p></td>
	</tr>
	<tr>
	<th>Lcpc Branch</th><td><p id="namesubWinlcpcbranch" ></p></td>
	</tr>
	<tr>
	<th>Address line 1</th><td><p id="namesubWinaddress1" ></p></td>
	</tr>
	<tr>
	<th>Address line 2</th><td><p id="namesubWinaddress2" ></p></td>
	</tr>
	<tr>
	<th>Address line 3</th><td><p id="namesubWinaddress3" ></p></td>
	</tr>
	<tr>
	<th>Address line 4</th><td><p id="namesubWinaddres4" ></p></td>
	</tr>
	<tr>
	<th>State Code</th><td><p id="namesubWinstatecode" ></p></td>
	</tr>
	<tr>
	<th>District Code</th><td><p id="namesubWindistrictcode" ></p> </td>
	</tr>
	<tr>
	<th>pin Code</th><td><p id="namesubWinpincode" ></p> </td>
	</tr>
	<tr>
	<tr>
	<th>phone Number</th><td><p id="namesubWinphoneNum" ></p> </td>
	</tr>
	<tr>
	<th>email</th><td><p id="namesubWinemail" ></p></td> 
	</tr>
	<center><input type="button" class="btn btn-l btn-blue" value="save" onclick="return saveBranch();"/></center>
	</table>
	
  </div>

</div>
<div id="statemyModal" class="statemodal">

  <!-- Modal content -->
  <div class="statemodal-content">
    <span id="stateclose" class="close">×</span>
  

    <p>Add state</p>

    <table>
    <tr>
    <th>State</th>
    <td><input id="stateNamemodel" type="text"/></td>
    </tr>
    <tr>

	<center><input type="button" class="btn btn-l btn-blue" value="save" onclick="return savestate();"/></center>
	</table>
	
  </div>

</div>

<div id="districtmyModal" class="districtmodal">

  <!-- Modal content -->
  <div class="districtmodal-content">
    <span id="districtclose" class="close">×</span>
  

    <p>Add District</p>

    <table>
    <tr>
    <th>State</th>
   <th>District</th>
    </tr>
    <tr>
     <td><select id="stateIdmodel" name="stateDistrict"></select></td>
         <td><input id="districtName" type="text"/></td>
     </tr>
    <tr>

	<center><input type="button" class="btn btn-l btn-blue" value="save" onclick="return savedistrict();"/></center>
	</table>
	
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
     
        function savestate(){
        	if($("#stateNamemodel").val()==''){
        		alert("state should not be empty")
        	}else{
      	  $.ajax({
    			url: "/GnD/master/addstate?stateNamemodel="+$("#stateNamemodel").val(),
    			cache: false,				
    			success: function(data){
    				 alert("state added sucessfully");
    				 statem.style.display = "none";
    			},
    			  error: function(){						
    				alert('Error while request..pls try again');
    				 statem.style.display = "none";
    			} 
    		}); 
      	  modal.style.display = "none";
        	}
        }
        
        function savedistrict(){
        	if(($("#stateIdmodel option:selected").val()=='')&&($("#districtName").val()=='')){
        		alert("district should not be empty");
        	}else{
        	 $.ajax({
     			url: "/GnD/master/adddistrict?stateDistrict="+$("#stateIdmodel option:selected").val()+"&districtVal="+$("#districtName").val(),
     			cache: false,				
     			success: function(data){
     			if(data==true){
     				alert("District added sucessfully");
     				distModel.style.display = "none";
     			}
     			},
     			  error: function(){						
     				alert('Error while request..pls add once again');
     				distModel.style.display = "none";
     			} 
     		}); 
        	
        	}
          }
        
        
        
     function saveBranch(){
    	  $.ajax({
    		method:"post",
  			url: "/GnD/master/addBranchTo?branchshortcode="+$("#branchshortcodeid").val()+"&bank="+$("#bankName").val()+"&lcpcCode="+$("#lcpcCode").val()+"&address1="+$("#addressLine1").val()+"&addres3="+$("#addressLine3").val()+"&state="+$("#state").val()+"&pincode="+$("#pincode").val()+"&branchName="+$("#branchName").val()+"&status="+$("#status123").val()+"&lcpcbranch="+$("#lcpcbranch").val()+"&address2="+$("#addressLine2").val()+"&addres4="+$("#addressLine4").val()+"&district="+$("#district").val()+"&phone="+$("#phoneNum").val()+"&email="+$("#email").val()+"&isNonCardIssueBranch="+$("#isNonCardIssueBranchid").val(),
  			cache: false,				
  			success: function(data){
  				 alert(data);
  			},
  			  error: function(){						
  				alert('Error while request..');
  			} 
  		}); 
    	  modal.style.display = "none";
        }
     
//Get the modal
var modal = document.getElementById('myModal');
var statem=document.getElementById('statemyModal');
var distModel=document.getElementById('districtmyModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");
var statebtn = document.getElementById("statemybttn");
var districtbtn = document.getElementById("districtmybtn");


// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
var stateclosebtn = document.getElementById("stateclose");
var distclosebtn = document.getElementById("districtclose");


districtbtn.onclick=function(){
   	var statemod='';
	$.ajax({
		url: "/GnD/master/getstatelist",
		cache: false,				
		success: function(stateBo){
			for(var x = 0; x < stateBo.length; x++){
				
				 statemod += '<option value="' + stateBo[x]['stateCode'] + '">' + stateBo[x]['stateName'] + '</option>';
			}
			$('#stateIdmodel').html(statemod);
		},
		  error: function(){						
			alert('Error while request..');
		} 
	}); 

	distModel.style.display = "block";
	
}
distclosebtn.onclick=function(){
	distModel.style.display = "none";
}

statebtn.onclick=function(){
	statem.style.display = "block";
}
stateclosebtn.onclick=function(){
	 statem.style.display = "none";
}


// When the user clicks the button, open the modal 
btn.onclick = function() {
	if(($("#branchshortcodeid").val()&&$("#bankName option:selected").text()&&$("#lcpcCode").val()&&$("#addressLine1").val()&&$("#addressLine3").val()&&$("#addressLine3").val()&&$("#addressLine4").val()&&$("#state option:selected").text()&&$("#pincode").val()&&$("#email").val()&&$("#branchName").val()&&$("#status123 option:selected").text()&&$("#lcpcbranch").val()&&$("#district option:selected").text()&&$("#phoneNum").val())==""){
	alert("fields should not be empty");	
	}else{
	$("#namesubWin").text($("#branchshortcodeid").val());
	$("#namesubWinbank").text($("#bankName option:selected").text());
	$("#namesubWinlcpccode").text($("#lcpcCode").val());
	$("#namesubWinaddress1").text($("#addressLine1").val());
	$("#namesubWinaddress2").text($("#addressLine2").val());
	$("#namesubWinaddress3").text($("#addressLine3").val());
	$("#namesubWinaddres4").text($("#addressLine4").val());
	$("#namesubWinstatecode").text($("#state option:selected").text());
	$("#namesubWinpincode").text($("#pincode").val());
	$("#namesubWinemail").text($("#email").val());
	$("#namesubWinbranhName").text($("#branchName").val());
	$("#namesubWinstatus").text($("#status123 option:selected").text());
	$("#namesubWinlcpcbranch").text($("#lcpcbranch").val());
	$("#namesubWindistrictcode").text($("#district option:selected").text());
	$("#namesubWinphoneNum").text($("#phoneNum").val());
    modal.style.display = "block";
	}
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
   
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if ((event.target == modal)||(event.target==statem)||(event.target==distModel)) {
        modal.style.display = "none";
        statem.style.display = "none";        
        distModel.style.display = "none";
    }
}
           $("#state").click(function(){
        	   $('#district').empty();
        	   if($("#state").val()!="%%"){
        	    $.ajax({
        			type: "post",
        			url: "/GnD/master/district.htm?stateId="+$("#state").val(),
        			cache: false,				
        			success: function(data){
        				 var options = '';
        		            for (var x = 0; x < data.length; x++) {
        		                options += '<option value="' + data[x]['districtCode'] + '">' + data[x]['districtName'] + '</option>';
        		            }
        		            $('#district').html(options);
        			},
        			  error: function(){						
        				alert('Error while request..');
        			} 
        		}); 
        	   }
           }); 
       
          
           var frm = $('#addbranch');
           frm.submit(function (ev) {
               $.ajax({
                   type: frm.attr('method'),
                   url: frm.attr('action'),
                   data: frm.serialize(),
                   success: function (data) {
                	 //  window.open("_blank", "height=400, width=550, status=yes, toolbar=no, menubar=no"); 
                    alert(data);
                    $('#addbranch').trigger("reset");
                    $('#district').val('');
                   }
               });

               ev.preventDefault();
           });

       		 function formSubmit() {
    				document.getElementById("logoutForm").submit();
    		  }

            $('header a[href^="#"], h1 a[href^="#"]').click(function (e) {
                e.preventDefault();
                var $target = $($(this).attr('href'));
                var top = $target.offset().top;
                $('body, html').animate({'scrollTop': top - 40});
            });

            var messageCount = 0;
            setInterval(function () {
                // turn on/off messages for demo purposes
                var $messages = $('.message');
                $messages.eq(messageCount++ % $messages.length).toggleClass('fade-out');
            }, 700);

            setInterval(function () {
                // simulating progress of progress bars for demo purposes.
                var $progresses = $('.progress[data-percentage]');
                $progresses.each(function () {
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
                $(el).each(function (index, element) {
                    if (element.currentStyle !== undefined && element.currentStyle.hasLayout === false) {
                        $(element).css({
                            'zoom': 1
                        });
                    }
                });
            }

            $(function () {

                var product = [
                    "Product 1",
                    "Product 2",
                    "Product 3",
                    "Product 4"
                ];
                var bank = [
                    "Bank 1",
                    "Bank 2",
                    "Bank 3",
                    "Bank 4"
                ];

                $(".product").autocomplete({
                    source: product,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
                $(".bank").autocomplete({
                    source: bank,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
            });
        </script>
    </body>
</html>