<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Terms And Company details</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript" ></script>
    <script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript" ></script>
    <style type="text/css">
		.textfieldMyStyle {
            border:3px double #CCCCCC;
            width: 200px;
            height:20px;
        }
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
        fieldset{
            text-align:right;
        }

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
<body  style="background: #A9A9A9 ;" onload="javascript:hideUpdate()">
<form:form method="POST" commandName="companyTermsForm" name="companyTermsForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Company Details </legend>
                <table>
                    <tr>
                        <td>
                            <label for="companyName">
                                Company Name :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyName" cssClass="textfieldMyStyle" id="companyName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="companyPhoneNumber">
                                Company Phone Number :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyPhoneNumber" cssClass="textfieldMyStyle" id="companyPhoneNumber"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="companyEmail">
                                Company email :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyEmail" cssClass="textfieldMyStyle" id="companyEmail"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="companyWebsite">
                                Company Website :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyWebsite" cssClass="textfieldMyStyle" id="companyWebsite"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="companyAddress">
                                Company Address :
                            </label>
                        </td>
                        <td colspan="5" align="left">
                            <form:textarea path="currentCompanyTermsVO.companyAddress" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="companyAddress" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="companyTerms">
                                Company Terms & Conditions :
                            </label>
                        </td>
                        <td colspan="5" align="left">
                            <form:textarea path="currentCompanyTermsVO.companyTerms" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="companyTerms" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="vat_tin">
                                Company's VAT TIN :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyVATTIN" cssClass="textfieldMyStyle" id="vat_tin"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="cst_tin">
                                Company's CST TIN :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyCSTTIN" cssClass="textfieldMyStyle" id="cst_tin"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <!--tr>
                        <td>
                            <label for="companylogo">
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
                            <input class="btn" id="edit" value="Edit" type="button" onclick="javascript:editMe();"/>
                            <input class="btn" id="update" value="Update" type="button" onclick="javascript:updateCompanyDetails();"/>
                            <input class="btn" id="clear" value="Clear" type="button" onclick="javascript:clearOut();"/>
                            <input class="btn" id="cancel" value="Cancel" type="button" onclick="javascript:cancelMe();"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>
