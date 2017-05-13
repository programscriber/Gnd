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
        <style type="text/css">
        <style>
			.error {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #a94442;
				background-color: #f2dede;
				border-color: #ebccd1;
			}
			
			.msg {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #31708f;
				background-color: #d9edf7;
				border-color: #bce8f1;
			}
			
			#login-box {
				width: 300px;
				padding: 20px;
				margin: 100px auto;
				background: #fff;
				-webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				border: 1px solid #000;
			}
        </style>
        
    </head>

    <body>   	
    	
        <div class='login-left'>
            <img src="<c:url value="/resources/img/logo-white.png"/>" />
            <div class="clear"></div><br><br>
            <span class="version">Version 1.0.14</span>
            <div class="clear"></div><br>
<!--             <span class="text">Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.Some project description here.</span> -->
        </div>
        <div class='login-right'>
<!--        		<form action="/GnD/loginuser/validateuser" method="post"> -->
					<form name='loginForm'
			action="<c:url value='/j_spring_security_check' />" method='POST'>
            <div class="center-box">
                <div class="col-md-12">
                    <div class="col-md-12">
	                    <c:if test="${not empty error}">
							<div class="errorblock">
								Your login attempt was not successful, try again.<br /> Caused :
								${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
							</div>
						</c:if>
                        <h2 class="blue-text padding-l">Sign In</h2>
                        
                        <div class="col-md-8 top-margin">
                            <span class="blue-text" >Username</span>
                            <div class="select-menu select-menu-native select-menu-light">
                                <input type="text" name="username" class="text-field" placeholder="Username" required />
                            </div>                        
                        </div>
                        <div class="col-md-8 top-margin">
                            <span class="blue-text" >Password</span>
                            <div class="select-menu select-menu-native select-menu-light">
                                <input type="password" name="password" class="text-field" placeholder="Password" required/>
                            </div>                        
                        </div>

                    </div>
                    <input type="submit" class="btn btn-blue top-margin padding-l" value="Sign In"/>
                </div>
<!-- 					<p><a href="../j_spring_security_logout">Logout</a></p> -->
                </div>
			</form>
            </div>
             
<!--         </div> -->
       
    </body>
</html>
