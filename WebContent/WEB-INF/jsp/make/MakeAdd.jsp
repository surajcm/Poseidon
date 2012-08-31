<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add Make</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
    <script type="text/javascript">
        function save(){
            if(document.getElementById('makeName').value.length == 0) {
                document.getElementById('makeName').style.background = 'Yellow';
                alert(" Please enter the Make name");
            }  else if(document.getElementById('description').value.length == 0) {
                document.getElementById('makeName').style.background = 'White';
                document.getElementById('description').style.background = 'Yellow';
                alert(" Please enter the description");
            } else{
                document.getElementById('modelName').style.background = 'White';
                document.getElementById('description').style.background = 'White';
                document.forms[0].action = "saveMake.htm";
                document.forms[0].submit();
            }
        }

        function clearOut(){
            document.getElementById('makeName').value = "";
            document.getElementById('description').value = "";
        }
    </script>

</head>
<body  style="background: #A9A9A9 ;">
<form:form method="POST" commandName="makeForm" name="makeForm" >
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset style="text-align:right;">
                <legend>Add Make</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="makeName" style="font-size: .70em;">
                                Make Name :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentMakeAndModeVO.makeName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="makeName"/>
                            <form:errors path="currentMakeAndModeVO.makeName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="makeName" style="font-size: .70em;">
                                Description :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentMakeAndModeVO.description" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="description"/>
                            <form:errors path="currentMakeAndModeVO.description"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn" value="Save" type="button" onclick="javascript:save();" />
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn" value="Clear" type="button" onclick="javascript:clearOut();" />
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>