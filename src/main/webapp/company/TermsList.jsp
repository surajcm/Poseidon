<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Terms And Company details</title>
    <style type="text/css">
        table {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript">

        function editMe(){
            makeEverythingWritable();
            hideEditAndShowUpdate();
        }

        function makeEverythingWritable(){
            document.getElementById("companyName").style.background ="#FFFFFF";
            document.getElementById('companyName').readOnly=false;
            document.getElementById("companyPhoneNumber").style.background ="#FFFFFF";
            document.getElementById('companyPhoneNumber').readOnly=false;
            document.getElementById("companyEmail").style.background ="#FFFFFF";
            document.getElementById('companyEmail').readOnly=false;
            document.getElementById("companyWebsite").style.background ="#FFFFFF";
            document.getElementById('companyWebsite').readOnly=false;
            document.getElementById("companyAddress").style.background ="#FFFFFF";
            document.getElementById('companyAddress').readOnly=false;
            document.getElementById("companyTerms").style.background ="#FFFFFF";
            document.getElementById('companyTerms').readOnly=false;
            document.getElementById("vat_tin").style.background ="#FFFFFF";
            document.getElementById('vat_tin').readOnly=false;
            document.getElementById("cst_tin").style.background ="#FFFFFF";
            document.getElementById('cst_tin').readOnly=false;
        }
        function hideEditAndShowUpdate(){
            document.getElementById('edit').style.visibility='hidden';
            document.getElementById('update').style.visibility='visible';
            document.getElementById('clear').style.visibility='visible';
            document.getElementById('cancel').style.visibility='visible';
        }

        function hideUpdate(){
            document.getElementById('companymgt').text = "Company <span class='sr-only'>Company</span>";
            document.getElementById('update').style.visibility='hidden';
            document.getElementById('clear').style.visibility='hidden';
            document.getElementById('cancel').style.visibility='hidden';
            makeEverythingReadOnly();
        }

        function makeEverythingReadOnly() {
            document.getElementById('companyName').readOnly=true;
            document.getElementById("companyName").style.background ="#A9A9A9";
            document.getElementById('companyPhoneNumber').readOnly=true;
            document.getElementById("companyPhoneNumber").style.background ="#A9A9A9";
            document.getElementById('companyEmail').readOnly=true;
            document.getElementById("companyEmail").style.background ="#A9A9A9";
            document.getElementById('companyWebsite').readOnly=true;
            document.getElementById("companyWebsite").style.background ="#A9A9A9";
            document.getElementById('companyAddress').readOnly=true;
            document.getElementById("companyAddress").style.background ="#A9A9A9";
            document.getElementById('companyTerms').readOnly=true;
            document.getElementById("companyTerms").style.background ="#A9A9A9";
            document.getElementById('vat_tin').readOnly=true;
            document.getElementById("vat_tin").style.background ="#A9A9A9";
            document.getElementById('cst_tin').readOnly=true;
            document.getElementById("cst_tin").style.background ="#A9A9A9";
        }

        function updateCompanyDetails(){
            document.forms[0].action = "updateCompanyDetails.htm";
            document.forms[0].submit();
        }

        function clearOut(){
            document.getElementById('companyName').value = "";
            document.getElementById('companyPhoneNumber').value = "";
            document.getElementById('companyEmail').value = "";
            document.getElementById('companyWebsite').value = "";
            document.getElementById('companyAddress').value = "";
            document.getElementById('companyTerms').value = "";
            document.getElementById('vat_tin').value = "";
            document.getElementById('cst_tin').value = "";
        }

        function cancelMe(){
            document.forms[0].action = "List.htm";
            document.forms[0].submit();
        }
    </script>

</head>
<body onload="hideUpdate()">
<form:form method="POST" modelAttribute="companyTermsForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="../myHeader.jsp" %>
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
                            <form:input path="currentCompanyTermsVO.companyVATTIN" cssClass="form-control" id="vat_tin"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="cst_tin" class="control-label">
                                Company's CST TIN :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyCSTTIN" cssClass="form-control" id="cst_tin"/>
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
</form:form>

</body>
</html>
