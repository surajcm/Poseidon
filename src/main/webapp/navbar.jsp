<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css" integrity="sha512-Cv93isQdFwaKBV+Z4X8kaVBYWHST58Xb/jVOcV9aRsGSArZsgAnFIhMpDoMDcFNoUtday1hdjn0nGp3+KZyyFw==" crossorigin="anonymous" />
<nav class="navbar navbar-dark py-0 bg-primary navbar-expand-lg py-md-0">
    <spring:url value="/img/Poseidon_Menu.png" var="posIcon2" />
    <a class="navbar-brand" href="#" onclick="javascript:goToHome();"><span><img src="${posIcon2}" width="30" height="30" class="d-inline-block align-top" alt="Poseidon"/></span>Poseidon</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <li class='nav-item' id='user'><a href='#' class='nav-link' onclick='listMe();' >User<span class="sr-only">(current)</span></a></li>
                </c:when>
            </c:choose>
            <li class="nav-item py-0" id="make"><a class="nav-link" href="#" onclick="MakeMe();" >Make</a></li>
            <li class="nav-item py-0" id="customer"><a class="nav-link" href="#" onclick="fetchCustomers();" >Customer</a></li>
            <li class="nav-item py-0" id="company"><a class="nav-link" href="#" onclick="fetchCompany();" >Company</a></li>
            <li class="nav-item py-0" id="transaction"><a class="nav-link" href="#" onclick="fetchTransactions();" >Transactions</a></li>
            <li class="nav-item py-0" id="inventory"><a class="nav-link" href="#" onclick="fetchInvoice();" >Invoice</a></li>
            <li class="nav-item py-0" id="report"><a class="nav-link" href="#" onclick="fetchReport();" >Report</a></li>
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">
            <li class="nav-item py-0" id="locale_switch"><a class="nav-link" href="#" ><span class="flag-icon flag-icon-us flag-icon-squared" aria-hidden="true"></span>&nbsp;EN</a></li>
            <li class="nav-item dropdown">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' role="button" data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>${pageContext.request.userPrincipal.name} </a>
                    </c:when>
                    <c:otherwise>
                        <a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' role="button" data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Unknown User</a>
                    </c:otherwise>
                </c:choose>
                <div class="dropdown-menu dropdown-menu-right text-right" aria-labelledby="dropdownMenu1">
                    <a href="#" class="dropdown-item" onclick="resetPassword();">Reset Password</a>
                    <a href="#" class="dropdown-item" onclick="LogMeOut();">Log Out</a>
                </div>
            </li>
        </ul>
    </div><!--/.nav-collapse -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</nav>
<br />
