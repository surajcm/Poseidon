<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Edit Terms</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <script type="text/javascript">
        function update(){
            document.forms[0].action = "updateTerms.htm";
            document.forms[0].submit();
        }

        function cancel(){
            document.forms[0].action = "List.htm";
            document.forms[0].submit();
        }
    </script>

</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="companyTermsForm" name="companyTermsForm">
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
     <div id="content">
        <div class="wrap">
            <fieldset style="text-align:right;">
                <legend>Edit Terms And Conditions</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="termsAndConditions" style="font-size: .70em;">
                                Terms
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCompanyTermsVO.termsAndConditions" rows="5" cols="30" cssClass="textboxes" id="termsAndConditions" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn" value="Update" type="button" onclick="javascript:update()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn" value="Cancel" type="button" onclick="javascript:cancel()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>