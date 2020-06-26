
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
                    <li class='nav-item' id='user'><a href='#' class='nav-link' onclick='javascript:listMe();' >User<span class="sr-only">(current)</span></a></li>
                </c:when>
            </c:choose>
            <li class="nav-item py-0" id="make"><a class="nav-link" href="#" onclick="javascript:MakeMe();" >Make</a></li>
            <li class="nav-item py-0" id="customer"><a class="nav-link" href="#" onclick="javascript:fetchCustomers();" >Customer</a></li>
            <li class="nav-item py-0" id="company"><a class="nav-link" href="#" onclick="javascript:fetchCompany();" >Company</a></li>
            <li class="nav-item py-0" id="transaction"><a class="nav-link" href="#" onclick="javascript:fetchTransactions();" >Transactions</a></li>
            <li class="nav-item py-0" id="inventory"><a class="nav-link" href="#" onclick="javascript:fetchInvoice();" >Invoice</a></li>
            <li class="nav-item py-0" id="report"><a class="nav-link" href="#" onclick="javascript:fetchReport();" >Report</a></li>
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">
            <li class="nav-item dropdown">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' role="button" data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>${pageContext.request.userPrincipal.name} </a>
                    </c:when>
                    <c:otherwise>
                        <a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' role="button" data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Unknown User</a>
                    </c:otherwise>
                </c:choose>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                    <a href="#" class="dropdown-item" onclick="javascript:LogMeOut();">Log Out</a>
                </div>
            </li>
        </ul>
    </div><!--/.nav-collapse -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</nav>
<br />
