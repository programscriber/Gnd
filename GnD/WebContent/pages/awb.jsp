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
        <link href="<c:url value='/resources/css/icon.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
        	
        <script type="text/javascript" src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/jquery-1.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/blazer.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/resources/js/blazer.js'/>"></script>
<%-- 		<script type="text/javascript" src="<c:url value='/resources/js/awb.js'/>"></script> --%>
        <script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
        <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
         <script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
          <script src="<c:url value='/resources/js/jquery.easyui.min.js'/>"></script>
<!--  		<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script> -->
<!--  		<script type="text/javascript" src="/resources/js/jquery.min.js.js"></script> -->
        <link href="<c:url value='/resources/css/infragistics.theme.css'/>" rel="stylesheet" />
        <link href="<c:url value='/resources/css/infragistics.css'/>" rel="stylesheet" />


        <!-- Ignite UI Required Combined JavaScript Files -->
        <script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
        <script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>
        
        <script type="text/javascript">
	function validate() {
		var radio1 = document.getElementById("fn").checked;
		var radio2 = document.getElementById("range").checked;
		var x = document.forms["awbform"]["from"].value;
		var y = document.forms["awbform"]["to"].value;		
		
		if(radio1 == false && radio2 == false) {			
			alert("please select any one option");
			return false;
		}
		else {
			if(radio1 == true) {			
				if(document.getElementById("xls").value == "") {
					alert("Please upload file");
					document.getElementById('xls').focus();
					return false;
				}				
				else {
					
					var servicecheck = service();
					if(servicecheck) {
						document.awbform.action="/GnD/upload/importawbmaster";
 						return true;
					
					}
					else { 
						return false;
					}
					
				}
					
			}
			else if(radio2 == true && (x == "" || y == "")) {				

				alert("please enter from and to values");
				return false;
							
			}
			else {
				var servicecheck = service();
				if(servicecheck) {
					document.awbform.action="/GnD/upload/importawbrange";
					return true;
				}
				else { 
					return false;
				}
			}
		}
	}
		function service() {
		var service = document.forms.awbform.service;
		for (var i=0; i < service.length; i++) {
		if (service[i].checked)
			return true;
		}
			alert("Please select any one service");
			return false;
		}
        </script>
    </head>


    <body>
        <style>
details { 
    display: block;
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

            .cen{
                padding-left: 15px ;
            }
            #checkboxSelectCombo,#checkboxSelectCombo:hover,#checkboxSelectCombo1,#checkboxSelectCombo1:hover{
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
            .ui-igcombo-clear{
                display: none !important;
            }
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
                    <div  class="demo-left-nav" id="left-nav">
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
                                <li  class="selected"><a href="/GnD/upload/getserviceprovider">AWB</a> </li>                                    <ul>
                            	<li><a href="/GnD/master/getServiceproviders">export AWB</a></li>
                            	</ul>
                                
                                </details>
                        </div>
                    </div>
                    
                </div>


                <div class="right-content record">
                <h2><c:if test="${msg ne null }">
					<c:out value = "${msg}"/>
				</c:if>	</h2>
                    <h2 class="blue-text">Import New AWB</h2>
                   <form name ="awbform" onsubmit="return validate()" method="post" enctype="multipart/form-data">
                        <div class="col-md-12">
                            <div class="col-md-8">
                                <div class="col-md-12">
                                    <table>
                                        <tr>
                                            <td>
                                                <label class="label">
                                                    <input type="radio" id="fn" class="radiobutton" name="my-radiobutton-group">
                                                    <span class="custom-radiobutton"></span>
                                                </label>
                                            </td>
                                            <td class="cen">
                                                <span class="blue-text" >File Upload</span>
                                                <div class="select-menu select-menu-native select-menu-light">
                                                    <input type="file" name="file" id="xls"/>
                                                </div> 
                                            </td>
                                            
                                        </tr>
                                        
                                                                 
                                        <tr>
                                            <td>
                                                <label class="label">
                                                    <input type="radio" class="radiobutton" name="my-radiobutton-group" id="range">
                                                    <span class="custom-radiobutton"></span>
                                                </label>
                                            </td>
                                            <td>
                                                <div class="col-md-12" >
                                                    <span class="blue-text" >Start Range </span>
                                                    <div class="select-menu select-menu-native select-menu-light">
                                                        <input type="text" class="text-field" name ="from" id = "from"/>
                                                    </div>                        
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-md-12">
                                                    <span class="blue-text" >End Range</span>
                                                    <div class="select-menu select-menu-native select-menu-light">
                                                        <input type="text" class="text-field" name="to" id = "to" />
                                                    </div>                        
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                        	<td>
                                        	 
                                               <button  class="btn btn-l btn-blue" type="submit" value="Import">Import</button>
                                             		
                                            </td>
                                        </tr>
                                    </table>
									</form>
                                </div>
<!--                                 <div class="col-md-12"> -->
<!--                                     <button class="btn" >Add Manual</button> -->
<!--                                     <br><br> -->
<!--                                     <div class="code-example"> -->
<!--                                         <div class="progress" data-percentage="38"> -->
<!--                                             <div class="progress-indicator"></div> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </div> -->

                            </div>
                            <div class="col-md-2 border-class">
                                <label class="label">
                                											
										<c:forEach var="listValue" items="${service}">
														
											<input type="radio" name="service" value="${listValue.serviceProviderName}"/>${listValue.serviceProviderName} 				
											<br>
										</c:forEach>
										
									
<!--                                     <input type="radio" class="radiobutton" name="my-radiobutton-group-1" id="service"> -->
<!--                                     <span class="custom-radiobutton"></span> -->
<!--                                     India Post -->
<!--                                 </label> -->
<!--                                 <label class="label"> -->
<!--                                     <input type="radio" class="radiobutton" name="my-radiobutton-group-1" id="service" > -->
<!--                                     <span class="custom-radiobutton"></span> -->
<!--                                     Blue Dart -->
                                </label>
                            </div>
                        </div>
                        <div class="col-md-12">

                            <table class="table">
								<thead>
									<tr>
										<td>Service Provider Name</td>
										<td>Number of AWB used</td>
										<td>Number of AWB unused</td>
										<td>Start value of Available</td>
										<td>End value of Available</td>
									</tr>
								</thead>
									
									
									<c:forEach items="${awbcount}" var="s">
									<tr>
									<td>${s.serviceprovidername}</td>
									<td>${s.used}</td>
									<td>${s.unused}</td>
									<td>${s.endvalue}</td>
									<td>${s.startvalue }</td>
									</tr>
									</c:forEach>



		</table>            
					<div class="clear"></div>
                    <div class="table-header">
<!--                         <h3>Search Results : 14 Records</h3> -->
                        <button class="btn btn-only-img btn-only-img1 btn-s" type="button">
                            <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print">
                        </button>
                    </div>
                    <div class="clear"></div>
<!--                     <table class="table"> -->
<!--                         <thead> -->
<!--                             <tr> -->
<!--                                 <th>Service Provider</th> -->
<!--                                 <th>AWB</th> -->
<!--                                 <th>Status</th> -->
<!--                             </tr> -->
<!--                         </thead> -->
<!--                         <tbody> -->
<!--                             <tr> -->
<!--                                 <td><a href="record-details.html">SP 1</a></td> -->
<!--                                 <td><a href="record-details.html">AWB 1</a></td> -->
<!--                                 <td><a href="record-details.html">123456</a></td></tr>                                                                          -->
<!--                             <tr>                                                                         -->
<!--                             <tr> -->
<!--                                 <td><a href="record-details.html">SP 2</a></td> -->
<!--                                 <td><a href="record-details.html">AWB 2</a></td> -->
<!--                                 <td><a href="record-details.html">123456</a></td></tr>                                                                          -->
<!--                             <tr>                                                                         -->
<!--                             <tr> -->
<!--                                 <td><a href="record-details.html">SP 3</a></td> -->
<!--                                 <td><a href="record-details.html">AWB 3</a></td> -->
<!--                                 <td><a href="record-details.html">123456</a></td></tr>                                                                          -->
<!--                             <tr>                                                                         -->
<!--                         </tbody> -->
<!--                     </table> -->
<!--                     <div class="pagination"> -->
<!--                         <ul> -->
<!--                             <li><a href="#">1</a></li> -->
<!--                             <li><a href="#">2</a></li> -->
<!--                             <li><a href="#">3</a></li> -->
<!--                             <li><a href="#">4</a></li> -->
<!--                             <li class="selected"><a href="#">5</a></li> -->
<!--                             <li><a href="#">6</a></li> -->
<!--                             <li><a href="#">7</a></li> -->
<!--                             <li><a href="#">8</a></li> -->
<!--                             <li><a href="#">9</a></li> -->
<!--                             <li><a href="#">10</a></li> -->
<!--                             <li><a href="#">20</a></li> -->
<!--                             <li><a href="#">30</a></li> -->
<!--                             <li><a href="#">100</a></li> -->
<!--                             <li><a href="#">200</a></li> -->
<!--                             <li class="meta-navigation"><a href="#">Next ></a></li> -->
<!--                         </ul> -->
<!--                     </div> -->
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

                var start = [
                    "Start 1",
                    "Start 2",
                    "Start 3",
                    "Start 4"
                ];
                var end = [
                    "End 1",
                    "End 2",
                    "End 3",
                    "End 4"
                ];

                $(".start").autocomplete({
                    source: start,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
                $(".end").autocomplete({
                    source: end,
                    minLength: 0,
                    focus: function () {
                        $(this).autocomplete("search");
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });
            });


            var colors = [
                {Name: "All"},
                {Name: "Status - 1"},
                {Name: "Status - 2"},
                {Name: "Status - 3"},
                {Name: "Status - 4"}
            ];

            $(function () {
                $("#checkboxSelectCombo").igCombo({
                    width: "166px",
                    dataSource: colors,
                    textKey: "Name",
                    valueKey: "Name",
                    multiSelection: {
                        enabled: true,
                        showCheckboxes: true
                    }
                });
            });
        </script>
    </body>
</html>