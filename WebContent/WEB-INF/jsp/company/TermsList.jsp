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
    <link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript" ></script>
    <script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript" ></script>
    <style type="text/css">
		html {
		  font-size: 100%;
		  -webkit-text-size-adjust: 100%;
		  -ms-text-size-adjust: 100%;
		}
		button,
		input,
		select,
		textarea {
		  margin: 0;
		  font-size: 100%;
		  vertical-align: middle;
		}

		button,
		input {
		  *overflow: visible;
		  line-height: normal;
		}

		button::-moz-focus-inner,
		input::-moz-focus-inner {
		  padding: 0;
		  border: 0;
		}

		button,
		input[type="button"],
		input[type="reset"],
		input[type="submit"] {
		  cursor: pointer;
		  -webkit-appearance: button;
		}
		input[type="search"] {
		  -webkit-box-sizing: content-box;
			 -moz-box-sizing: content-box;
				  box-sizing: content-box;
		  -webkit-appearance: textfield;
		}

		input[type="search"]::-webkit-search-decoration,
		input[type="search"]::-webkit-search-cancel-button {
		  -webkit-appearance: none;
		}

		textarea {
		  overflow: auto;
		  vertical-align: top;
		}

		.clearfix {
		  *zoom: 1;
		}

		.clearfix:before,
		.clearfix:after {
		  display: table;
		  line-height: 0;
		  content: "";
		}

		.clearfix:after {
		  clear: both;
		}

		.hide-text {
		  font: 0/0 a;
		  color: transparent;
		  text-shadow: none;
		  background-color: transparent;
		  border: 0;
		}

		.input-block-level {
		  display: block;
		  width: 100%;
		  min-height: 30px;
		  -webkit-box-sizing: border-box;
			 -moz-box-sizing: border-box;
				  box-sizing: border-box;
		}

		body {
		  margin: 0;
		  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		  font-size: 14px;
		  line-height: 20px;
		  color: #333333;
		  background-color: #ffffff;
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
            <fieldset style="text-align:right;">
                <legend>Company Details </legend>
                <table style="margin:auto;top:50%;left:50%;" >
                    <tr>
                        <td>
                            <label for="companyName" style="font-size: .70em;">
                                Company Name :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="companyName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="companyPhoneNumber" style="font-size: .70em;">
                                Company Phone Number :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyPhoneNumber" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="companyPhoneNumber"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="companyEmail" style="font-size: .70em;">
                                Company email :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyEmail" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="companyEmail"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="companyWebsite" style="font-size: .70em;">
                                Company Website :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyWebsite" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="companyWebsite"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="companyAddress" style="font-size: .70em;">
                                Company Address :
                            </label>
                        </td>
                        <td colspan="5" align="left">
                            <form:textarea path="currentCompanyTermsVO.companyAddress" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 600px;height:40px;"
                                           id="companyAddress" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="companyTerms" style="font-size: .70em;">
                                Company Terms & Conditions :
                            </label>
                        </td>
                        <td colspan="5" align="left">
                            <form:textarea path="currentCompanyTermsVO.companyTerms" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 600px;height:40px;"
                                           id="companyTerms" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="vat_tin" style="font-size: .70em;">
                                Company's VAT TIN :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyVATTIN" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="vat_tin"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="cst_tin" style="font-size: .70em;">
                                Company's CST TIN :
                            </label>
                        </td>
                        <td align="left">
                            <form:input path="currentCompanyTermsVO.companyCSTTIN" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="cst_tin"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <!--tr>
                        <td>
                            <label for="companylogo" style="font-size: .70em;">
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
