<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create new product type</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/CssForShop.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <style>
        table {
            width: 250px;
            border-spacing: 7px 11px;
        }

        .col1 {
            width: 100px;
        }

        td {
            padding: 5px;
        }

        tr {
            width: 100px;
        }

        input {
            width: 200px;
        }

        .col2input {
            width: 255px;
        }

        .block1 {
            width: 150px
        }
    </style>
</head>
<body>
<footer>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="/shop">Shop</a>
                <ul class="nav navbar-nav">

                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                        <li><a href="/list">Users</a></li>
                    </c:if>

                    <li><a href="/profile">Profile</a></li>

                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                        <li><a href="/listProductType">Product Management</a></li>
                    </c:if>
                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN') || pageContext.request.isUserInRole('ROLE_COURIER')}">
                        <li><a href="/listOfCourier">List Courier</a></li>
                    </c:if>
                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')||pageContext.request.isUserInRole('ROLE_STOREKEEPER')}">
                        <li><a href="/warehouse">Warehouse</a></li>
                    </c:if>
                </ul>
            </div>

            <div class="footerButtons">
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <input type="button" class="tableButton" value="Login" onClick='location.href="/login"'>
                    <input type="button" class="tableButton" value="SignUp" onClick='location.href="#"'>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <form id="logoutForm" method="post" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    ${pageContext.request.userPrincipal.name}
                    <button class="tableButton" onclick="document.forms['logoutForm'].submit()">Logout</button>
                </c:if>
            </div>
        </div>

    </nav>
</footer>

<div class="container">
    <span style="color:green">${message}</span>
    <span style="color:red">${error}</span>
    <form:form method="POST" action="/productTypeCreate" modelAttribute="productTypeForm"
               name="productTypeForm" enctype="multipart/form-data">
        <h3 class="form-heading">Create new product type</h3>
        <div>
            <table>
                <tr>
                    <td class="col1"><label class="col-2 col-form-label">Name</label></td>
                    <td><input type="text" name="name" class="col2input" required></td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Category</label></td>
                    <td><input type="text" name="categories" class="col2input" required></td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Producer</label></td>
                    <td><input type="text" name="producer" class="col2input" required></td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Price</label></td>
                    <td><input type="text" name="price" class="col2input" required pattern="\d+(\.\d{2})?"></td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Unit</label></td>
                    <td>
                        <select name="unit_id">
                            <c:forEach items="${unitList}" var="unit">
                                <option name="unit_id" value="${unit.id}">${unit.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Zone</label></td>
                    <td>
                        <select name="zone_id">
                            <c:forEach items="${zoneList}" var="zone">
                                <option name="zone_id" value="${zone.id}">${zone.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Image</label></td>
                    <td><input type="file" name="fileOfProduct"></td>
                </tr>
            </table>
            <table id="myTable">
                <tr>
                    <td><label class="col-2 col-form-label">Attribute</label></td>
                    <td><label class="col-2 col-form-label">Value</label></td>
                </tr>
                <tr>
                    <td class="col1"><input type="text" name="attributes" required></td>
                    <td><input type="text" name="values" required></td>
                </tr>
                <tr>
                    <td class="col1"><input type="text" name="attributes" required></td>
                    <td><input type="text" name="values" required></td>
                </tr>
                <tr>
                    <td class="col1"><input type="text" name="attributes" required></td>
                    <td><input type="text" name="values" required></td>
                </tr>
            </table>
            <script type="text/javascript">
                var rowCount = 3
                function addRow(id) {
                    var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
                    var row = document.createElement("TR")
                    var td1 = document.createElement("TD")
                    var attributeInput = document.createElement('input')
                    attributeInput.name = "attributes"
                    attributeInput.required = "true"
                    td1.appendChild(attributeInput)
                    var td2 = document.createElement("TD")
                    var valueInput = document.createElement('input')
                    valueInput.name = "values"
                    valueInput.required = "true"
                    td2.appendChild(valueInput)
                    row.appendChild(td1);
                    row.appendChild(td2);
                    tbody.appendChild(row);
                    rowCount = rowCount + 1
                }
                function myDeleteFunction() {
                    if (rowCount != 0) {
                        document.getElementById("myTable").deleteRow(rowCount);
                        rowCount = rowCount - 1
                    }
                }
            </script>
            <a href="javascript://" onclick="addRow('myTable');return false;">
                <img src="../resources/css/plusMini.png" width="20" height="20" border="5" alt="add"></a>
            <a href="javascript://" onclick="myDeleteFunction('myTable');return false;">
                <img src="../resources/css/minusMini.png" width="20" height="20" border="5" alt="delete"></a>
        </div>
        <div class="block1">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Create</button>
        </div>
    </form:form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!--<script src="${contextPath}/resourses/js/bootstrap.min.js"></script>-->
</body>
</html>
