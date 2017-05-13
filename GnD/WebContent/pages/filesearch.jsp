<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html class=" js backgroundsize cssanimations csstransitions pointerevents" lang="en">

        <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8">
            <meta charset="utf-8">

            <title>G & D</title>
            <link href="<c:url value='/resources/css/blazer.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
            <link href="<c:url value='/resources/css/style.css'/>" media="screen, projection" rel="stylesheet" type="text/css">
            <script type="text/javascript" src="<c:url value='/resources/js/modernizr-2.js'/>"></script>
            <script type="text/javascript" src="<c:url value='/resources/js/jquery-1.js'/>"></script>
            <script type="text/javascript" src="<c:url value='/resources/js/blazer.js'/>"></script>

            <script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>
            <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>

            <link href="<c:url value='/resources/css/infragistics.theme.css'/>" rel="stylesheet" />
            <link href="<c:url value='/resources/css/infragistics.css'/>" rel="stylesheet" />


            <!-- Ignite UI Required Combined JavaScript Files -->
            <script src="<c:url value='/resources/js/infragistics.core.js'/>"></script>
            <script src="<c:url value='/resources/js/infragistics.lob.js'/>"></script>
        </head>




        <body>
            <style>
                #checkboxSelectCombo,
                #checkboxSelectCombo:hover {
                    /*background-color: #f7f8f8 !important;*/
                    
                    border: 1px solid #9db5cd !important;
                }
                .ui-igcombo-buttonicon,
                .ui-igcombo-clearicon {
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
                .ui-igcombo-clear {
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
                    	<li id="icons"  ><a href="<c:url value='/loginuser/home'/>">Home</a></li>
                    	<li id="icons"><a href="<c:url value='/search/searchcard'/>">Search</a></li>
                    <c:if test="${username eq 'admin'}">
                        <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
                        <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
<!--                         <li id="styleguide"><a href="#">I/O Config</a></li> -->
<!--                         <li id="screens"><a href="#">Time-Out Config</a></li> -->
						<li id="prototype"><a href="/GnD/notification/getdailymails">Notifications</a>
						<li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
<%--                          <li id="icons"><a href="<c:url value='/emailcont/editemailTable'/>">Email</a></li>                       --%>
					</c:if>					  
					  <c:if test="${username eq 'helpdesk'}">
						  <li id="icons"><a href="<c:url value='/master/masterdb'/>">Master DB</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
						 <!-- <li id="icons"><a href="<c:url value='/master/email'/>">Email</a></li>-->
					  </c:if>
					  <c:if test="${username eq 'shopfloor'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Shop floor</a></li>
					  </c:if>  
					   <c:if test="${username eq 'warehouse'}">
						  <li id="prototype"><a href="/GnD/shopfloor/home">Warehouse</a></li>
						  <li id="prototype"><a href="/GnD/shopfloor/misImoprt">MIS Import</a>
					  </c:if> 
					  <c:if test="${username eq 'rto'}">
						  <li id="prototype"  ><a href="/GnD/shopfloor/home">RTO</a></li>
					  </c:if>  
					  <c:if test="${username eq 'datagen'}">
                    	<li id="icons" ><a href="<c:url value='/qcprocess'/>">QC</a></li>
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
                                    <li>
                                        <a href="/GnD/loginuser/recordsearch">Card/Pin Search</a>
                                    </li>
                                    <li class="selected">
                                        <a>File Search</a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>

                    <div class="right-content" >
                        <h2 class="blue-text">File Search</h2>
                        <form action="/GnD/search/getFiles" method="post">
                            <div class="row right-con">
                                <div class="col-md-2">
                                    <span class="blue-text">File Name</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="filename" type="text" class="text-field filename" />
                                        <input id="hidfilename" type=hidden name="filename" />
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <span class="blue-text">File Status</span>
                                    <div id="checkboxSelectCombo"></div>
                                    <div class="clear">
                                        <input id="hidstatus" type=hidden name="status" />
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <span class="blue-text">Bank Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="bank" type="text" class="text-field bank" />
                                        <input type=hidden id="hidbank" name="bank" />
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <span class="blue-text">Branch Code</span>
                                    <div class="select-menu select-menu-native select-menu-light">
                                        <input id="branch" type="text" class="text-field branch" />
                                        <input type=hidden id="hidbranch" name="branch" />
                                    </div>
                                </div>
                                <br>
                                <br>
                                <br>
                                <div class="second-line">
                                    <div class="col-md-4">
                                        <span class="blue-text">File Received or processed date</span>
                                        <div class="hero-unit">
                                            <div class="input-group float-left">
                                                <input id="dateFrom" type="date" class="text-field text-field-with-addon float-left" placeholder="Placeholder value" value="2014/11/3">
                                                <input type=hidden id="hiddateFrom" name="dateFrom" /><span class="addon">
                                                <span class="calendar-icon">
                                                </span>
                                                </span>
                                            </div>

                                            <span class="add-on float-left float-text">to</span>
                                            <div class="input-group float-right">
                                                <input id="dateTo" type="date" class="text-field text-field-with-addon float-right" placeholder="Placeholder value" value="2014/11/3">
                                                <input type=hidden id="hiddateTo" name="dateTo" /><span class="addon">
                                                <span class="calendar-icon">
                                                </span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="row">
                                <div class="right-buttons">
                               		 <input type="submit" class="btn btn-l btn-blue" onclick="searchParams()" value="Search" />
                                    <button type="reset" class="btn btn-l">Reset</button>
                                    
                                </div>
                            </div>

                            <div class="clear"></div>
                            <div class="table-header">
                                 <c:choose> 
									<c:when test="${msg ne null }">
										<h2><center><c:out value = "${msg}"/></center></h2>
									</c:when>
									<c:otherwise>
										<h3>Search Results : <c:out value = "${files.size()}"/></h3>
									</c:otherwise>
								</c:choose>
                                <button class="btn btn-only-img btn-only-img1 btn-s" type="button">
                                    <img src="<c:url value='/resources/img/icons/download.png'/>" alt="Print">
                                </button>
                            </div>
                            <div class="clear"></div>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>File Name</th>
                                        <th>Type of File</th>
                                        <th>Received Date</th>
                                        <th>Records in File</th>
                                        <th>Status</th>
                                        <th>Description</th>
                                        <!--                                 <th>Remarks</th> -->
                                        <th class="no-border"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="fileList" items="${files}">
                                        <tr>
                                            <td>${fileList.filename}</td>
                                            <td>${fileList.fileTypeData}</td>
                                            <td>${fileList.receivedDate}</td>
                                            <td>${fileList.lineCount}</td>
                                            <td>${fileList.statusData}</td>
                                            <td>${fileList.description}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
                                    <li><a href="#">Contact</a>
                                    </li>
                                    <li><a href="#">Site Terms</a>
                                    </li>
                                    <li><a href="#">Privacy</a>
                                    </li>
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

                $(function() {
                    var availableTags = [
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7",
                        "8"
                    ];
                    var availableTags1 = [
                        "100",
                        "200",
                        "300",
                        "400",
                        "500",
                        "600",
                        "700",
                        "800"
                    ];
                    var mobile = [
                        "9876543211",
                        "1234567890",
                        "8765433456",
                        "1231231231"
                    ];
                    var filename = [
                        "1.jpg",
                        "2.png",
                        "3.jpg",
                        "4.png"
                    ];
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
                    var branch = [
                        "Branch 1",
                        "Branch 2",
                        "Branch 3",
                        "Branch 4"
                    ];

                    $(".awb").autocomplete({
                        source: availableTags,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                    $(".from,.to").autocomplete({
                        source: availableTags1,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                    $(".mobile").autocomplete({
                        source: mobile,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                    $(".filename").autocomplete({
                        source: filename,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                    $(".product").autocomplete({
                        source: product,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                    $(".branch").autocomplete({
                        source: branch,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                    $(".bank").autocomplete({
                        source: bank,
                        minLength: 0,
                        focus: function() {
                            $(this).autocomplete("search");
                        }
                    }).focus(function() {
                        $(this).autocomplete("search");
                    });
                });

                function getdata(from) {
                        if (from === "record") {
                            $(".file").hide();
                            $(".record").show();
                        } else {
                            $(".record").hide();
                            $(".file").show();
                        }
                    }
                    //             $(document).ready(function () {
                    //$("#s2").dropdownchecklist({icon: {"placement": "right", "toOpen": "ui-icon-triangle-1-s", "toClose": "ui-icon-triangle-1-n"}, width: 150});
                    //            });


                var colors = [{
                    Name: "All",
                    Value: "0"
                }, {
                    Name: "REJECTED",
                    Value: "1"
                }, {
                    Name: "HOLD",
                    Value: "2"
                }, {
                    Name: "APPROVED",
                    Value: "3"
                }, {
                    Name: "ALU_CONVERTED",
                    Value: "4"
                }, {
                    Name: "EMBOSSA MAPPED",
                    Value: "5"
                }];


                $(function() {

                    $("#checkboxSelectCombo").igCombo({
                        width: "166px",
                        dataSource: colors,
                        textKey: "Name",
                        valueKey: "Value",
                        multiSelection: {
                            enabled: true,
                            showCheckboxes: true
                        },
                        selectedItems: [{
                            index: 1
                        }],
                        selectionChanged: function(evt, ui) {}

                    });
                    $("#checkboxSelectCombo1").igCombo({
                        width: "166px",
                        dataSource: colors,
                        textKey: "Name",
                        valueKey: "Value",
                        multiSelection: {
                            enabled: true,
                            showCheckboxes: true
                        },
                        selectedItems: [{
                            index: 1
                        }],
                        selectionChanged: function(evt, ui) {}
                    });
                });
                $(document).delegate("#checkboxSelectCombo", "igcomboselectionchanged", function(evt, ui) {
                    var itemStr = '';
                    if (ui.items && ui.items[0]) {
                        var items = ui.items;
                        for (var index in items) {
                            itemStr = itemStr + items[index].data.Value + ",";
                        }

                    }
                    document.getElementById("hidstatus").value = itemStr;
                });

                function searchParams() {
                    var filename = document.getElementById("filename").value;
                    document.getElementById("hidfilename").value = filename;



                    var dateFrom = document.getElementById("dateFrom").value;
                    document.getElementById("hiddateFrom").value = dateFrom;

                    var dateTo = document.getElementById("dateTo").value;
                    document.getElementById("hiddateTo").value = dateTo;



                    var bank = document.getElementById("bank").value;
                    document.getElementById("hidbank").value = bank;

                    var branch = document.getElementById("branch").value;
                    document.getElementById("hidbranch").value = branch;
                }
            </script>
        </body>

        </html>
