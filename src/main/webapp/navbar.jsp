<link rel="stylesheet" href="/css/core/flag-icon.min.css" crossorigin="anonymous" />
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <spring:url value="/img/Poseidon_Menu.png" var="posIcon2" />
        <a class="navbar-brand" href="#" onclick="goToHome();">
            <img src="${posIcon2}" width="30" height="30" class="d-inline-block align-text-top" alt="Poseidon"/>
            Poseidon
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <li class='nav-item' id='user'><a href='#' class='nav-link' onclick='listMe();' >User</a></li>
                    </c:when>
                </c:choose>
                <li class="nav-item" id="make">
                    <a class="nav-link" href="#" onclick="MakeMe();" >Make</a>
                </li>
                <li class="nav-item" id="customer">
                    <a class="nav-link" href="#" onclick="fetchCustomers();" >Customer</a>
                </li>
                <li class="nav-item" id="company">
                    <a class="nav-link" href="#" onclick="fetchCompany();" >Company</a>
                </li>
                <li class="nav-item" id="transaction">
                    <a class="nav-link" href="#" onclick="fetchTransactions();" >Transactions</a>
                </li>
                <li class="nav-item" id="inventory">
                    <a class="nav-link" href="#" onclick="fetchInvoice();" >Invoice</a>
                </li>
                <li class="nav-item" id="report">
                    <a class="nav-link" href="#" onclick="fetchReport();" >Report</a>
                </li>
            </ul>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item" id="locale_switch">
                    <a class="nav-link" href="#" ><span class="flag-icon flag-icon-us flag-icon-squared" aria-hidden="true"></span>&nbsp;EN</a>
                </li>
                <li class="nav-item dropdown">
                    <c:choose>
                        <c:when test="${pageContext.request.userPrincipal.name != null}">
                            <a class='nav-link dropdown-toggle' href='#' id='dropdownMenu1' role="button" data-bs-toggle='dropdown' aria-expanded='false'>${pageContext.request.userPrincipal.name} </a>
                        </c:when>
                        <c:otherwise>
                            <a class='nav-link dropdown-toggle' href='#' id='dropdownMenu1' role="button" data-bs-toggle='dropdown' aria-expanded='false'>Unknown User</a>
                        </c:otherwise>
                    </c:choose>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenu1">
                        <li>
                            <a class="dropdown-item" href="#" onclick="resetPassword();">Reset Password</a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="#" onclick="LogMeOut();">Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </div>
</nav>
<br />
