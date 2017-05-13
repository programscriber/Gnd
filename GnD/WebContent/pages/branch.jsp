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
    </head>

    <body>
        <style>
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 50%;
    height: 100%;
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
            .col-md-8{
                padding: 10px;
            }

            .border-class{
                border: 1px solid #bfbfc1;
                float: right;
                padding: 10px 25px;
            }
            .table-header{
                width: 100%;
            }

            /*    table{
                    max-width: 200px !important;
                    overflow-x: auto;
                }*/
        </style>
        <div class="page-header">
            <div class="page-header-wrapper container">
                <div class="main-logo">
                    Giesecke &amp; Devrient

                </div>

                <div class="tab-menu dark">
                    <ul>
                    <li id="icons" ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
                       
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                         --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons" class="selected"><a href="<c:url value='/master/branchSearch'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						<!-- <li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>-->
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
                           	 	<li >
                                    <a href="<c:url value='/master/bank'/>">Bank</a>
                                </li>
                                
                                <li>
                                    <a href="<c:url value='/upload/getProduct'/>">Products</a>
                                </li>
                                <li>
                                    <a href="/GnD/upload/getserviceprovider">AWB</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
				 

                <div class="right-content record">
                <h3>
				 	<c:if test="${msg ne null }">
						<c:out value = "${msg}"/>
					</c:if>	
				</h3>
                    <h2 class="blue-text">Import New Branches</h2>
                    
                        <div class="col-md-12">
                            <div class="col-md-8">
                                <div class="col-md-12">
                                <form id="form1" action="/GnD/upload/importbranch" method="post" enctype="multipart/form-data">
                                    <table>
                                        <tr>
                                            <td class="cen">
                                                <span class="blue-text" >File Upload</span>
                                                <div class="select-menu select-menu-native select-menu-light">
                                                    <input path="fileData" type="file" name="file" required/>
                                                </div> 
                                            </td>
                                            <td>
                                                <button  class="btn btn-l btn-blue" type="submit" form="form1" value="Import">Import</button>
                                            </td>
                                        </tr>
                                    </table>
                                 </form>
                                </div>
                                </br>
                                </br>
                <button class="btn">
					<a href="/GnD/master/addBranch"
						style="text-decoration: none;" color:black;>Add Branch</a>
				</button>
<%-- 								<a href="<c:url value='/master/addBranch'/>">Add Branch</a> --%>
<!-- 											 <div class="table-header"> -->
<!-- 											  <button  id=" " type="button"  class="btn btn-only-img btn-only-img1 btn-s" onclick="location.href='/GnD/master/addBranch'"> -->
     
<!--                         </button> -->
<!--                        <h3>Search Results : 14 Records</h3> 
                        <button  id=" " type="button"  class="btn btn-only-img btn-only-img1 btn-s" onclick="location.href='/sbi/pinawbReport'">
     <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print">
                        </button>
                                           
<!--                     </div> -->
                                <div class="col-md-12">
                                    <div class="code-example">
                                        <br><br>
<!--                                         <div class="progress" data-percentage="38"> -->
<!--                                             <div class="progress-indicator"></div> -->
<!--                                         </div> -->
                                    </div>
                                </div>


                            </div>

                        </div>
                        <div class="col-md-12">
								<form action="/GnD/master/searchbranch" method="post">
                            <h2 class="blue-text">Branch Search</h2>
                            <div class="row right-con">

                                <div class="col-md-2">
                                    <span class="blue-text" >Bank Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
						<select name="bankCode" id ="bankCode">
<!-- 							<option value="%%" disabled selected style="display: none;"> select Bank code..</option>  -->
								<option value="%%"> select Bank code..</option>
							<c:forEach var="bankSearVal" items="${bankSear}">
								<option value="${bankSearVal.bankId}">${bankSearVal.shortCode}</option>
							</c:forEach>
						</select> <span class="addon"> <span class="select-menu-icon"></span>
						</span>
					</div>
                                </div>
                                <div class="col-md-2">
                                    <span class="blue-text" >Branch Name</span>
                                     
                                   <div class="select-menu select-menu-native select-menu-light">
                                  <!-- <input type="text" class="text-field" name ="branchName" id = "branchName"/> -->
						                <select  id = "branchName" name="branchName" >
						                <option value="%%"> select branch name </option> 
						                </select> 
						                
						               <span class="addon"> <span class="select-menu-icon"></span>
						               </span>
					               </div> 
                                </div>
                                
                                
                                
                                <div class="col-md-2">
                                    <span class="blue-text" >Branch Code</span>
                                     <div class="select-menu select-menu-native select-menu-light">
                                       <select id = "branchCode" name="branchCode">
							               <option value="%%"> select branch code</option> 
						                </select>   
						             <span class="addon"> <span class="select-menu-icon"></span>
						            </span>  
						           </div>   
                                </div>
                                
                                
                                
                                <div class="col-md-2">
                                    <span class="blue-text" >IFSC</span>
								    <div class="select-menu select-menu-native select-menu-light">
									    <select id = "ifscCode" name="ifscCode">
										    <option value="%%">select ifsc code </option> 
									     </select>
									     <span class="addon"> <span class="select-menu-icon"></span>
						                 </span>
								    </div>
                                   </div>
</div>
                            <div class="clear"></div>
                            <div class="row">
                                <div class="">
<!--                                     <button type="reset" class="btn btn-l">Reset</button> -->
									<input type="submit" class="btn btn-l btn-blue" value="Search"/>
                                    <button type="reset" class="btn btn-l ">Reset</button>
                                </div>
                            </div>
                    </form>
                    <div class="clear"></div>
                    <div class="table-header">
<!--                         <h3>Search Results : 14 Records</h3> -->
                        <button class="btn btn-only-img btn-only-img1 btn-s" type="button">
                            <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print">
                        </button>
                    </div>
                   
                    <div class="clear"></div>
                    <table class="table extra-table">
                    	<c:choose> 
									<c:when test="${msg ne null }">
										<h2><center><c:out value = "${msg}"/></center></h2>
									</c:when>
									<c:otherwise>
										<h3>Search Results : <c:out value = "${searchbranch.size()}"/></h3>
									</c:otherwise>
						</c:choose>
                        <thead>
                            <tr>
                            	
                                <th>Bank Code</th>
                                <th>Branch Code</th>
                                <th>Branch Name</th>
                                <th>Circle Code</th>
                                <th>Circle</th>
                                <th>Live</th>
                                <th>IFSC</th>
                                <th>District</th>
                                <th>State</th>
                                <th>Phone</th>
                                <th>MICR</th>
                                <th>ADD1</th>
                                <th>ADD2</th>
                                <th>ADD3</th>
                                <th>ADD4</th>
                                <th>IS NON CARD ISSUE BRANCH</th>
                            </tr>
                        </thead>
                        <tbody>
                      
                        <c:forEach var="searchbranchVal" items="${searchbranch}">
                            <tr  value="${searchbranchVal.bankId},${searchbranchVal.shortCode},${searchbranchVal.districtCode},${searchbranchVal.stateCode},${searchbranchVal.status}">
<!--                          		<td><input id="myBtn" type="button" value="edit" /><td> -->
                        		<td >${searchbranchVal.bankNameString}</td> 
                                <td >${searchbranchVal.shortCode}</td>                                                                    
                                <td>${searchbranchVal.branchName }</td>                                                                
                                <td>${searchbranchVal.circleCode }</td>                                                                   
                                <td>${searchbranchVal.circle }</td>                                                                
                                <td>${searchbranchVal.liveStatus }</td>                                                                     
                                <td>${searchbranchVal.ifscCode }</td>                                                                         
                                <td >${searchbranchVal.districtString }</td>                                                                         
                                <td >${searchbranchVal.stateString }</td>                                                                         
                                <td>${searchbranchVal.phoneNumber }</td>                                                                         
                                <td>${searchbranchVal.micr }</td>                                                                         
                                <td>${searchbranchVal.address1 }</td>                                                                         
                                <td>${searchbranchVal.address2 }</td>                                                                       
                                <td>${searchbranchVal.address3 }</td>                                                                         
                                <td>${searchbranchVal.address4 }</td>
                               <c:if test="${searchbranchVal.isNonCardIssueBranch == 0}"> 
                               	<td>No</td>
                               	</c:if>
                               	<c:if test="${searchbranchVal.isNonCardIssueBranch == 1}"> 
                               	<td>Yes</td>
                               	</c:if>
                                                                                                     
                            </tr> 
                            </c:forEach>                                                                     
                        </tbody>
                    </table>

                </div>    

				<c:url value="/j_spring_security_logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</form>

            </div>
        </div>
         <div id="myModal" class="modal">
        <div class="modal-content">
    <span class="close">×</span>
  
    <p>Branch Details</p>
    <table>
    <tr>
    <th>Field Name</th>
    <th>Values</th>
    </tr>
    <tr>
    <th>Branch short code</th><td><input type="text" id="namesubWin" readonly="readonly"/></td>
    </tr>
    <tr>
	<th>Branch Name</th><td><input type="text" id="namesubWinbranhName" /></td>
	</tr>
	<tr>
	<th>Bank Name</th><td><input type="text" id="namesubWinbank" readonly="readonly"/>
	</td>
	</tr>
<!-- 	<input type="text" id="namesubWinbank" /> -->
	<tr>
	<th>Status</th><td><select id="statusOption"></select></td>
	</tr>
	<tr>
	<th>Lcpc code</th><td><input type="text" id="namesubWinlcpccode" /></td>
	</tr>
	<tr>
	<th>Lcpc Branch</th><td><input type="text" id="namesubWinlcpcbranch" /></td>
	</tr>
	<tr>
	<th>Address line 1</th><td><textarea rows="4" cols="20" id="namesubWinaddress1" ></textarea></td>
	</tr>
	<tr>
	<th>Address line 2</th><td><textarea rows="4" cols="20" id="namesubWinaddress2" ></textarea></td>
	</tr>
	<tr>
	<th>Address line 3</th><td><textarea rows="4" cols="20"  id="namesubWinaddress3" ></textarea></td>
	</tr>
	<tr>
	<th>Address line 4</th><td><textarea rows="4" cols="20" id="namesubWinaddres4" ></textarea></td>
	</tr>
	<tr>
	<th>State Code</th><td><select id="stateOption"></select></td>
	</tr>
	<tr>
	<th>District Code</th><td><select id="districtOption"></select> </td>
	</tr>
	<tr>
	<th>pin Code</th><td><input type="text" id="namesubWinpincode" /> </td>
	</tr>
	<tr>
	<tr>
	<th>phone Number</th><td><input type="text" id="namesubWinphoneNum" /></td>
	</tr>
	<tr>
	<th>email</th><td><input  type="text" id="namesubWinemail" /></td> 
	</tr>
	<tr>
	<th>isNonCardIssueBranch</th><td><select id="namesubWinisNonCardIssueBranch"></select></td> 
	</tr>
	<tr></tr>
	</table>
	<center><input type="button" value="save"  class="btn btn-l btn-blue" onclick="return editBranch();"/></center>
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
        var arr='';
        
        function getLogin()
        {
        	var objUserInfo = new ActiveXObject("WScript.network");
        	document.write(objUserInfo.ComputerName+"<br>"); 
        	document.write(objUserInfo.UserDomain+"<br>"); 
        	document.write(objUserInfo.UserName+"<br>");  
        	var uname =  objUserInfo.UserName;
        	alert(uname);
        }
        
        
        
        
        
         $(".table").on('click','tr',function(e){
            e.preventDefault();
          var tabledata=$(this).children("td").map(function(){return $(this).text();}).get();
          arr = $(this).attr('value').split(',');  
         
           var bank='';
           var state='';
           var district='';
           var statusitr='';
           var yesno='';
     	  $.ajax({
     		  	type:"POST",
    			url: "/GnD/master/editBranchTo?bank="+arr[0]+"&branchshortcode="+arr[1]+"&district="+arr[2]+"&state="+arr[3]+"&status="+arr[4],
    			cache: false,				
    			success: function(itemList){
    			var bankBo=itemList[0];
    			var stateBo=itemList[1];
    			var districtBo=itemList[2];
    			var statusVa=itemList[3];
    			var enumlist=itemList[4];
    			var yesnolist=itemList[5];
    			for(var x = 0; x < bankBo.length; x++){
    				
    				 bank += '<option value="' + bankBo[x]['bankId'] + '">' + bankBo[x]['shortCode'] + '</option>';
    			}
    			$('#bankOption').html(bank);
    				for(var x = 0; x < stateBo.length; x++){
        				
        				 state += '<option value="' + stateBo[x]['stateCode'] + '">' + stateBo[x]['stateName'] + '</option>';
        			}
    			 $('#stateOption').html(state);
    			 for(var x = 0; x < districtBo.length; x++){
     		
     				 district += '<option value="' + districtBo[x]['districtCode'] + '">' + districtBo[x]['districtName'] + '</option>';
     			}
    			 $('#districtOption').html(district);
    			 
    			 for(var x = 0; x < statusVa.length; x++){
    				 statusitr+=  '<option value="' +statusVa[x]['key']  + '">' + statusVa[x]['value']  + '</option>';
    			}
    			 	$('#statusOption').html(statusitr);
    			 	
    			 	
    				 for(var x = 0; x < yesnolist.length; x++){
    					 yesno+=  '<option value="' +yesnolist[x]['key']  + '">' + yesnolist[x]['value']  + '</option>';
        			}
    			 	
    			 	$('#namesubWinisNonCardIssueBranch').html(yesno);
    			 	$("#namesubWin").val(enumlist.shortCode);
    	            $("#namesubWinbank").val(tabledata[0]);
    	        	$("#namesubWinlcpccode").val(enumlist.lcpcName);
    	        	$("#namesubWinaddress1").val(enumlist.address1);
    	        	$("#namesubWinaddress2").val(enumlist.address2);
    	        	$("#namesubWinaddress3").val(enumlist.address3);
    	        	$("#namesubWinaddres4").val(enumlist.address4);
    	        	$("#namesubWinpincode").val(enumlist.pinCode);
    	        	$("#namesubWinemail").val(enumlist.emailAddress);
    	        	$("#namesubWinbranhName").val(enumlist.branchName);   	     
    	        	$("#namesubWinlcpcbranch").val(enumlist.lcpcBranch);    	        
    	        	$("#namesubWinphoneNum").val(enumlist.phoneNumber);	
    	        	
    			 	modal.style.display = "block";
    			  
    			},
    			  error: function(){						
    				alert('Error while request..');
    			} 
    		}); 
        	 span.onclick = function() {
                 modal.style.display = "none";
             }
        }); 
         $("#stateOption").click(function(){
      	   $('#districtOption').empty();
      	   
      	   if($("#stateOption").val()!="%%"){
      	    $.ajax({
      			type: "post",
      			url: "/GnD/master/district.htm?stateId="+$("#stateOption").val(),
      			cache: false,				
      			success: function(data){
      				 var options = '';
      		            for (var x = 0; x < data.length; x++) {
      		                options += '<option value="' + data[x]['districtCode'] + '">' + data[x]['districtName'] + '</option>';
      		            }
      		            $('#districtOption').html(options);
      			},
      			  error: function(){						
      				alert('Error while request..');
      			} 
      		}); 
      	   }
         }); 
         function editBranch(){
        	// alert($("#stateOption :selected").val());
       	  $.ajax({
       		  	method:"post",
     			url: "/GnD/master/editBranchToadd?branchshortcode="+arr[1]+"&bank="+arr[0]+"&lcpcCode="+$("#namesubWinlcpccode").val()+"&address1="+$("#namesubWinaddress1").val()+"&addres3="+$("#namesubWinaddress3").val()+"&state="+$("#stateOption :selected").val()+"&pincode="+$("#namesubWinpincode").val()+"&branchName="+$("#namesubWinbranhName").val()+"&status="+$("#statusOption :selected").val()+"&lcpcbranch="+$("#namesubWinlcpcbranch").val()+"&address2="+$("#namesubWinaddress2").val()+"&addres4="+$("#namesubWinaddres4").val()+"&district="+$("#districtOption :selected").val()+"&phone="+$("#namesubWinphoneNum").val()+"&email="+$("#namesubWinemail").val()+"&isNonCardIssueBranch="+$("#namesubWinisNonCardIssueBranch :selected").val(),
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
        var modal = document.getElementById('myModal');

     // Get the button that opens the modal
     var btn = document.getElementById("myBtn");

     // Get the <span> element that closes the modal
     var span = document.getElementsByClassName("close")[0];



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

            
            	  $("#bankCode").change(function(){
            		  var inputBankID = document.getElementById("bankCode").value;
            		  $.ajax
          	          ({
          	              type: "Post",
          	              url:"/GnD/master/getBranchDetails?bankCode="+$("#bankCode").val(),
          	              success: function(data)
          	              {
          	            	$('#branchName').find('option').remove();
      	            		$('#branchCode').find('option').remove();
      	            		$('#ifscCode').find('option').remove();
      	            		var branchNameOptions = '<option value="%%">select branch name </option>';
      	            		var branchCodeOptions = '<option value="%%">select branch code </option>';
      	            		var branchIFSCOptions = '<option value="%%">select ifsc code </option>';
          	            	 if(data.length > 0) {
          	            		
          	            		for (var x = 0; x < data.length; x++) {
    	      	            		branchNameOptions += '<option value="' + data[x]['branchName'] + '">' + data[x]['branchName'] + '</option>';
    	      	            		branchCodeOptions += '<option value="' + data[x]['shortCode'] + '">' + data[x]['shortCode'] + '</option>';
    	      	            		branchIFSCOptions += '<option value="' + data[x]['ifscCode'] + '">' + data[x]['ifscCode'] + '</option>';
    	      	            	}
    	      	            	
          	            		}   
          	            	$('#branchName').html(branchNameOptions);
	      	            	$('#branchCode').html(branchCodeOptions);
      		                $('#ifscCode').html(branchIFSCOptions);
          	            	 },
            	 		error: function(){                        
    	                     alert('Error while request..');
    	                 }
              		});
            		  
            		  
            	  });
              
              
              
//               $(document).ready(function(){
//               	  $("#branchName").change(function(){
              		 
//               		  var inputBranchName = document.getElementById("branchName").value;
              		  
//               		  $.ajax
//             	        ({
//             	              type: "Post",
//             	              url:"/GnD/master/getBranchNameDetails?branchName="+$("#branchName").val(),
//             	              success: function(data)
//             	              {
//             	            	$('#branchCode').find('option').remove();
//       	            		$('#ifscCode').find('option').remove();
//                                  if(data.length > 0) {
//           	            		var branchCodeOptions = '';
//           	            		var branchIFSCOptions = '';
//     	      	            	for (var x = 0; x < data.length; x++) {
    	      	            		
//     	      	            		branchCodeOptions += '<option value="' + data[x]['shortCode'] + '">' + data[x]['shortCode'] + '</option>';
//     	      	            		branchIFSCOptions += '<option value="' + data[x]['ifscCode'] + '">' + data[x]['ifscCode'] + '</option>';
//     	      	            	}
//     	      	            	$('#branchCode').html(branchCodeOptions);
//           		            $('#ifscCode').html(branchIFSCOptions);
//           	            	}      
            	            	
//             	            }       	 		 
              	 		
//                 		});
              		  
              		  
//               	  });
//                 });
        </script>
    </body>
</html>