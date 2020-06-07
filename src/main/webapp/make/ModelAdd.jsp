<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
    <title>Add Model</title>
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
    <script type="text/javascript">
        function save(){
            if (document.getElementById('modelName').value.length == 0) {
                document.getElementById('modelName').style.background = 'Yellow';
                alert(" Please enter the Model name");
            } else{
                document.getElementById('modelName').style.background = 'White';
                document.forms[0].action = "saveModel.htm";
                document.forms[0].submit();
            }
        }

        function clearOut() {
            document.getElementById("makeId").value = document.getElementById('makeId').options[0].value;
            document.getElementById('modelName').value = "";
        }
    </script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body>
    <form:form method="POST" modelAttribute="makeForm" >
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="panel panel-primary">
                    <div class="panel-heading">Add Model</div>
                    <table style="margin:auto;top:50%;left:50%;">
                        <tr>
                            <td>
                                <label for="makeId" class="control-label">
                                    Make Name :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <form:select id="makeId" path="currentMakeAndModeVO.makeId" tabindex="1" cssClass="form-control" onkeypress="handleEnter(event);">
                                    <form:options items="${makeForm.makeAndModelVOs}"
                                                  itemValue="makeId" itemLabel="makeName"/>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="modelName" class="control-label">
                                    Model Name :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <form:input path="currentMakeAndModeVO.modelName" cssClass="form-control" id="modelName"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input class="btn btn-primary btn-success" value="Save" type="button" onclick="javascript:save()"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
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
            $(document).ready(function()
            {
                //Handles menu drop down
                $('.dropdown-menu').find('form').click(function (e) {
                    e.stopPropagation();
                });
            });
        </script>
    </form:form>
</body>
</html>
