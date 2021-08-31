<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <spring:url value="/img/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Terms And Company details</title>
    <script type="text/javascript" src="/js/company-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideUpdate()">
    <form:form method="POST" modelAttribute="companyTermsForm">
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        Company Details
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <div class="form-row">
                                <div class="form-group col-md-3">
                                  <label for="name">Company Name :</label>
                                  <form:input path="currentCompanyTermsVO.companyName" cssClass="form-control" id="companyName"/>
                                </div>
                                <div class="form-group col-md-3">
                                  <label for="loginId">Company Phone Number :</label>
                                  <form:input path="currentCompanyTermsVO.companyPhoneNumber" cssClass="form-control" id="companyPhoneNumber"/>
                                </div>
                                <div class="form-group col-md-3">
                                  <label for="name">Company email :</label>
                                  <form:input path="currentCompanyTermsVO.companyEmail" cssClass="form-control" id="companyEmail"/>
                                </div>
                                <div class="form-group col-md-3">
                                  <label for="loginId">Company Website :</label>
                                  <form:input path="currentCompanyTermsVO.companyWebsite" cssClass="form-control" id="companyWebsite"/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                  <label for="name">Company Address :</label>
                                  <form:textarea path="currentCompanyTermsVO.companyAddress" rows="5" cols="30" cssClass="form-control"
                                               id="companyAddress" />
                                </div>
                                <div class="form-group col-md-6">
                                  <label for="name">Company Terms & Conditions :</label>
                                  <form:textarea path="currentCompanyTermsVO.companyTerms" rows="5" cols="30" cssClass="form-control"
                                               id="companyTerms" />
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                  <label for="name">Company's VAT TIN :</label>
                                  <form:input path="currentCompanyTermsVO.companyVatTin" cssClass="form-control" id="vat_tin"/>
                                </div>
                                <div class="form-group col-md-4">
                                  <label for="loginId">Company's CST TIN :</label>
                                  <form:input path="currentCompanyTermsVO.companyCstTin" cssClass="form-control" id="cst_tin"/>
                                </div>
                                <div class="form-group col-md-4">
                                  <label for="name">Company Logo :</label>
                                  <input class="btn" value="Upload New Image" type="button" onclick="javascript:uploadImage();"/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <input class="btn btn-primary btn-success" id="edit" value="Edit" type="button" onclick="editMe();"/>
                                    <input class="btn btn-primary btn-success" id="update" value="Update" type="button" onclick="updateCompanyDetails();"/>
                                    <input class="btn btn-primary" id="clear" value="Clear" type="button" onclick="clearOut();"/>
                                    <input class="btn btn-primary" id="cancel" value="Cancel" type="button" onclick="cancelMe();"/>     
                                </div>
                            </div>
                        </div>
                    </div>
                </div>            
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js"></script>
        <script src="/js/core/popper.min.js"></script>
        <script src="/js/core/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                //Handles menu drop down
                $('.dropdown-menu').find('form').click(function (e) {
                    e.stopPropagation();
                });
            });
        </script>
    </form:form>
</body>
</html>
