<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add Make</title>
    <style type="text/css">
        table {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
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
                document.getElementById('makeName').style.background = 'White';
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
<body>
<form:form method="POST" commandName="makeForm" name="makeForm" >
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div class="container">
        <div class="wrap">
            <div class="panel panel-primary">
                <div class="panel-heading">Add Make</div>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="makeName" class="control-label">
                                Make Name :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input cssClass="form-control" path="currentMakeAndModeVO.makeName" id="makeName"/>
                            <form:errors path="currentMakeAndModeVO.makeName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="makeName" class="control-label">
                                Description :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input cssClass="form-control" path="currentMakeAndModeVO.description" id="description"/>
                            <form:errors path="currentMakeAndModeVO.description"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn btn-primary btn-success" value="Save" type="button" onclick="javascript:save();" />
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut();" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form:form>

</body>
</html>
