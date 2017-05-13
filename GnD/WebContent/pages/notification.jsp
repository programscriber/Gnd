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
    <%!public static final int REJECTED = 1;
	public static final int HOLD = 2;
	public static final int APPROVED = 3;%>
        <style>

            .col-md-12{
                padding: 10px;
            }

            .center-buttons{
                text-align: center;
                margin-bottom: 0px;
                padding-bottom: 10px;
            }
            .right-content {
                padding-bottom: 502.5em;
            }
            textarea{
                width: 70% !important;
                resize: none;
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
						<li id="prototype" class="selected"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li> --%>
                                              
					</c:if>					  
                        <li id="prototype"><a href="/GnD/search/getReport">Reports</a></li>  
					      <li id="prototype"><a href="javascript:formSubmit()"> Logout</a>
					   
                    </ul>
                </div>
            </div>
        </div><div class="main" style="">
            <div class="container main-conten">  
                <div class="left-content">
                    <div class="demo-left-nav" id="left-nav">
                        <div class="navigation">
                            <ul>
							<li class="selected"><a href="/GnD/notification/getdailymails">Daily
									Notifications</a></li>
							<li><a href="/GnD/notification/getweeklymails">Weekly
									Notifications</a></li>
							<li><a href="/GnD/notification/getexceptionalmails">Exceptional
									Notifications</a></li>
						</ul>
                        </div>
                    </div>
                </div>

                <div class="right-content record">
                    <h2 class="blue-text">Weekly Notifications</h2>
                    <form action="/GnD/notification/daily">
					<div class="col-md-12">
						<label class="label"> <input type="checkbox"
							class="checkbox" name="enableReject" value=<%=REJECTED%> > <span
							class="custom-checkbox"></span> Enable Notification, on REJECTED
							records, send summary report to below email's(comma separated)
						</label>
						<div class="select-menu select-menu-native select-menu-light">
							<textarea class="text-field" name="emailfieldReject"></textarea>
						</div>
					</div>
					<div class="col-md-12">
						<label class="label"> <input type="checkbox" name="enableHold"
							value=<%=HOLD%>>       
							 <span class="custom-checkbox" class="checkbox"></span>
							Enable Notification, on HOLD records, send summary report to
							below email's(comma separated)
						</label>
						<div class="select-menu select-menu-native select-menu-light">
							<textarea class="text-field" name="emailfieldHold"></textarea>
						</div>
					</div>
					<div class="col-md-12">
						<label class="label"> <input type="checkbox"
							class="checkbox" name="enableApproved" value=<%=APPROVED%>> <span
							class="custom-checkbox"></span> Enable Notification, on APPROVED
							records, send summary report to below email's(comma separated)
						</label>
						<div class="select-menu select-menu-native select-menu-light">
							<textarea class="text-field" name="emailfieldApproved"></textarea>
						</div>
					</div>
					<div class="clear"></div>
					<div class="row">
						<div class="center-buttons">
						<p>${result}</p>	
							<input type="submit" class="btn btn-l btn-blue"  value="Submit"/>
							<input type="reset" class="btn btn-l ">Clear</button>
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