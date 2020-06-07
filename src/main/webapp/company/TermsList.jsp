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
    <spring:url value="/resources/images/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/jquery-ui.css" type="text/css" />
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Terms And Company details</title>
    <style type="text/css">
        table {
            margin:auto;
            top:50%;
            left:50%;
        }
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript" src="/js/company-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideUpdate()">
    <form:form method="POST" modelAttribute="companyTermsForm">
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="panel panel-primary">
                    <div class="panel-heading">Company Details</div>
                    <table>
                        <tr>
                            <td>
                                <label for="companyName" class="control-label">
                                    Company Name :
                                </label>
                            </td>
                            <td align="left">
                                <form:input path="currentCompanyTermsVO.companyName" cssClass="form-control" id="companyName"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="companyPhoneNumber" class="control-label">
                                    Company Phone Number :
                                </label>
                            </td>
                            <td align="left">
                                <form:input path="currentCompanyTermsVO.companyPhoneNumber" cssClass="form-control" id="companyPhoneNumber"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <label for="companyEmail" class="control-label">
                                    Company email :
                                </label>
                            </td>
                            <td align="left">
                                <form:input path="currentCompanyTermsVO.companyEmail" cssClass="form-control" id="companyEmail"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="companyWebsite" class="control-label">
                                    Company Website :
                                </label>
                            </td>
                            <td align="left">
                                <form:input path="currentCompanyTermsVO.companyWebsite" cssClass="form-control" id="companyWebsite"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <label for="companyAddress" class="control-label">
                                    Company Address :
                                </label>
                            </td>
                            <td colspan="5" align="left">
                                <form:textarea path="currentCompanyTermsVO.companyAddress" rows="5" cols="30" cssClass="form-control"
                                               id="companyAddress" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <label for="companyTerms" class="control-label">
                                    Company Terms & Conditions :
                                </label>
                            </td>
                            <td colspan="5" align="left">
                                <form:textarea path="currentCompanyTermsVO.companyTerms" rows="5" cols="30" cssClass="form-control"
                                               id="companyTerms" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <label for="vat_tin" class="control-label">
                                    Company's VAT TIN :
                                </label>
                            </td>
                            <td align="left">
                                <form:input path="currentCompanyTermsVO.companyVatTin" cssClass="form-control" id="vat_tin"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="cst_tin" class="control-label">
                                    Company's CST TIN :
                                </label>
                            </td>
                            <td align="left">
                                <form:input path="currentCompanyTermsVO.companyCstTin" cssClass="form-control" id="cst_tin"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr>
                        <!--tr>
                            <td>
                                <label for="companylogo" class="control-label">
                                    Company Logo :
                                </label>
                            </td>
                            <td>
                                --------------
                            </td>
                            <td colspan="4" align="left">
                                <input class="btn" value="Upload New Image" type="button" onclick="javascript:uploadImage();"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr-->
                        <tr>
                            <td colspan="4">&nbsp;</td>
                            <td colspan="2">
                                <input class="btn btn-primary btn-success" id="edit" value="Edit" type="button" onclick="editMe();"/>
                                <input class="btn btn-primary btn-success" id="update" value="Update" type="button" onclick="updateCompanyDetails();"/>
                                <input class="btn btn-primary" id="clear" value="Clear" type="button" onclick="clearOut();"/>
                                <input class="btn btn-primary" id="cancel" value="Cancel" type="button" onclick="cancelMe();"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js"></script>
        <script src="/js/core/popper.min.js"></script>
        <script src="/js/core/bootstrap.min.js"></script>
        <script src="/js/core/jquery-ui.min.js"></script>
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
