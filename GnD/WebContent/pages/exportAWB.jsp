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
		<link href="<c:url value='/resources/css/easyui.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/jquery-1.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/blazer.js'/>"></script>
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css'/>">
        <script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
        <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
        <script src="<c:url value='/resources/js/jquery.tabletojson.js'/>"></script>
      <script src="<c:url value='/resources/js/jquery.alertable.js'/>"></script>
<script src="<c:url value='/resources/js/velocity.min.js'/>"></script>
<script src="<c:url value='/resources/js/velocity.ui.min.js'/>"></script>
<link href="<c:url value='/resources/css/jquery.alertable.css'/>"
	rel="stylesheet" />
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
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                    --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						 <li id="icons" class="selected"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						 <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<%-- 							<li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>						 --%>
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
                           <div  class="navigation">
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
<!--                                 <details> -->
<!--                                  <summary>AWB</summary> -->
<!--                                 <li  class="selected"> -->
<!--                                    <a href="/GnD/upload/getserviceprovider">AWB</a>                                     <ul> -->
<!--                             			<li><a href="/GnD/master/getServiceproviders">export AWB</a></li> -->
<!--                             		</ul> -->
<!--                                 </li> -->
<!--                                 </details> -->
<!-- <!--                                  <li  data-options="state:'closed'"> --> 
                                 
<!-- <!--                                     <a href="/GnD/master/getServiceproviders">export AWB</a> -->
<!-- <!--                                 </li> -->

<!--                             </ul> -->
                             <details dislay="no">
                                 <summary>AWB</summary>
                                 <ul>
                                <li  ><a href="/GnD/upload/getserviceprovider">AWB</a> </li>                                    <ul>
                            	<li class="selected"><a href="/GnD/master/getServiceproviders">export AWB</a></li>
                            	</ul>
                                
                                </details>
                        </div>
                    </div>
                    <div id="wait" style="display:none;width:69px;height:65px;position:absolute;top:50%;left:50%;padding:2px;"><img src='/GnD/resources/img/loading.gif' width="64" height="64" /><br>Loading..</div>
                </div>
				 

                <div class="right-content record">

 						<div class="col-md-2">
 						<input id="countcount" type="hidden">
 								<p id="countbyid"></p>
						</div>
                 <div class="col-md-2">
                
                                    <span class="blue-text" >Service Provider</span>
                                     <div class="select-menu select-menu-native select-menu-light">
                                      <select id="serviceprovider" name="serviceproviderName">
 										<option value="-1" disabled selected style="display: none;"> select service provider</option> 
							<c:forEach var="serviceVal" items="${serviceprovider}">
								<option value="${serviceVal.id}">${serviceVal.serviceProviderName}</option>
							</c:forEach>
						</select>   
						<span class="addon"> <span class="select-menu-icon"></span>
						</span>  
						</div>   
                                </div>
                                 <div class="col-md-2">
                            <label>count</label><input id="count" name="maxcount" type="number"  class="text-field mobile" max="5000"/>
                                 </div>
                                 <div class="col-md-2">
                                <label>block AWB</label>  <input id="blockawb" type="checkbox" class="text-field mobile" name="blockawb" value="1"/>
                                  </div>
                                 <div class="col-md-2">
                                <button  class="btn btn-l btn-blue"  value="Export" onclick="return getAWB();">Export</button>
                                 </div>
                                 <div id="savebutton" class="col-md-2">
                               
                                 </div>
                                 	<table id="dispAWB" class="table">
									</table>
									<div id="preparing-file-modal" title="Preparing report..." style="display: none;">
    									We are preparing your report, please wait...
 
    <div class="ui-progressbar-value ui-corner-left ui-corner-right" style="width: 100%; height:22px; margin-top: 20px;"></div>
</div>
 
<div id="error-modal" title="Error" style="display: none;">
    There was a problem generating your report, please try again.
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
        $(document).ready(function(){
            $(document).ajaxStart(function(){
                $("#wait").css("display", "block");
            });
            $(document).ajaxComplete(function(){
                $("#wait").css("display", "none");
            });
         
        });
        
        $(function () {
            $(document).on("click", "a.fileDownloadCustomRichExperience", function () {
         
                var $preparingFileModal = $("#preparing-file-modal");
         
                $preparingFileModal.dialog({ modal: true });
         
                $.fileDownload($(this).prop('href'), {
                    successCallback: function (url) {
         
                        $preparingFileModal.dialog('close');
                    },
                    failCallback: function (responseHtml, url) {
         
                        $preparingFileModal.dialog('close');
                        $("#error-modal").dialog({ modal: true });
                    }
                });
                return false; //this is critical to stop the click event which will trigger a normal file download!
            });
        });

        function getAWB(){
        	var count= $('#count').val();
        	var countcount=$('#countcount').val();
        	
        	var serviceProvider=$('#serviceprovider').val(); 
        	$('#dispAWB').empty();
       		if((count&&serviceProvider)==''){
        		alert("fields shold not be empty");
        	} else if(count>5000){
				alert("count should not exceed 5000");
        	}else if(parseInt(count)>parseInt(countcount)){
        		alert("count should not exced avaialble value");
        	} else{
        		 var tabledata = '<tr><th>serviceProviderName</th><th>awbName</th><th>status</th></tr>';
        		var check =$('#blockawb').is(':checked');
        	/* 	if(check==1){
        			$.alertable.alert("card is not in state to return", {
                 		  show: function() {
                 		    $(this.overlay).velocity('transition.fadeIn');        
                 		    $(this.modal).velocity('transition.flipBounceYIn');
                 		  },
                 		  hide: function() {
                 		    $(this.overlay).velocity('transition.fadeOut');
                 		    $(this.modal).velocity('transition.perspectiveUpOut');
                 		  } 
                 		});
        		} */
        	    $.ajax({        	    	
          			type: "post",
          			url: "/GnD/master/getlistAWB?serviceproviderName="+serviceProvider+"&maxcount="+count+"&blockawb="+check,
          			cache: false,				
          			success: function(data){
          				var options = '';
          				 if(data.length>0){
          		            for (var x = 0; x < data.length; x++) {
          		            	tabledata += '<tr><td>'+ data[x].serviceProviderName + '</td><td>'+ data[x].awbName + '</td><td>'+ data[x].statusString + '</td></tr>';
          		            }
          		          $('#dispAWB').html(tabledata);
          		          $('#savebutton').html('<button id=savefile class="btn btn-l btn-blue ">save to file</button>');
          		            }else{
          		            	alert("No AWB is prenst ");
          		            	$('#dispAWB').empty();
          		            	$('#savebutton').empty();
          		            }
          		           
          		          
          				        		            
          			},
          			  error: function(){						
          				alert('Error while request..');
          			} 
          		}); 
        	} 
        }
    	  $("#savebutton").click(function() {
    		  var filepath='';
    			var tableawb = $('#dispAWB').tableToJSON();
    			// window.location.href = '/GnD/master/savetofile'+dataArray;
   			var r = confirm("Click OK to download the file");
    			 if(r==true){
    			 $.ajax({
    				 contentType: 'application/json; charset=utf-8',
    				 dataType: 'json',
    				 type: 'POST',
    				 url: '/GnD/master/savetofile',
    				 data: JSON.stringify(tableawb),
    				 success: function(data){
    					 window.location.href = '/GnD/master/awbReport?filepath='+data;
    				},
    				  error: function(){						
    					alert('Error while request..');
    				} 
    				 });
    					 
    			}else{
    				alert("you have cancelld a request");
    			} 
    			
    			 
    		});
    	  $("#serviceprovider").change(function() {
    		  var serviceProvider=$('#serviceprovider').val();
    		  $('#countbyid').empty();
    		  $('#dispAWB').empty();
    		  $('#savebutton').empty();
    		  $.ajax({
    			 type: "post",
        		 url: "/GnD/master/countawb?serviceproviderName="+serviceProvider,
        		 cache: false,				
 				 success: function(data){
 					$('#countcount').val(data);
 					$('#countbyid').html($('#serviceprovider :selected').text()+"="+data+" available");
 					// alert(data);
 					 //window.location.href = '/GnD/master/awbReport?filepath='+data;
 				},
 				  error: function(){						
 					alert('Error while request..');
 				} 
 				 });
    	  });
        </script>
    </body>
</html>