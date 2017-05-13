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
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                         --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons" class="selected"><a href="<c:url value='/master/branchSearch'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<%-- 						 <li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li> --%>
					  </c:if>  
					   <c:if test="${username eq 'warehouse' || username eq 'shopfloor'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
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

            $(function () {

                var branchname = [
                    "Branch Name 1",
                    "Branch Name 2",
                    "Branch Name 3",
                    "Branch Name 4"
                ];
                var bank = [
                    "Bank Code 1",
                    "Bank Code 2",
                    "Bank Code 3",
                    "Bank Code 4"
                ];
                var branch = [
                    "Branch Code 1",
                    "Branch Code 2",
                    "Branch Code 3",
                    "Branch Code 4"
                ];
                var ifsc = [
                    "IFSC 1",
                    "IFSC 2",
                    "IFSC 3",
                    "IFSC 4"
                ];

                $(".branch-name").autocomplete({
                    source: branchname,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
                $(".bank-code").autocomplete({
                    source: bank,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
                $(".branch-code").autocomplete({
                    source: branch,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
                $(".ifsc").autocomplete({
                    source: ifsc,
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