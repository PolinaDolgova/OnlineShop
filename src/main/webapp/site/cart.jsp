<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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

    <title>Cart</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/CssForShop.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <style>
        table {
            width: 100%;
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

        .total {
            font-size: 20px;
        }
    </style>
</head>
<body>
<div>
    <footer>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/shop">Shop</a>
                    <ul class="nav navbar-nav">

                        <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                            <li><a href="/list">Users</a></li>
                        </c:if>
                        <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')||pageContext.request.isUserInRole('ROLE_STOREKEEPER')||pageContext.request.isUserInRole('ROLE_COURIER') || pageContext.request.isUserInRole('ROLE_CUSTOMER')}">
                            <li><a href="/profile">Profile</a></li>
                        </c:if>
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
                        <input type="button" class="tableButton" value="SignUp" onClick='location.href="/registration"'>
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
</div>
<div>
    <form:form method="POST" action="/cart" modelAttribute="orderForm"
               name="orderForm">
        <div class="container">
            <h3 class="form-heading">Order:</h3>
            <table>
                <td>
                    <table class="table table-hover">
                        <tbody>
                        <c:forEach items="${orderProductList}" var="productType">
                            <tr>
                                <td>
                                    <a>${productType.name}</a>
                                </td>
                                <td>
                                    <input type="checkbox" name="deleteProduct" value="${productType.id}">
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <label class="total">Total: ${total}rub</label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td>
                    <input type="submit" name="operation" class="btn btn-md btn-primary"
                           value="Delete products"/>
                </td>
            </table>
            <table>
                <tr>
                    <td class="col1"><label class="col-2 col-form-label">Name: </label></td>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <td class="col1"><input type="text" name="customerName" class="col2input"
                                                value="${currentUser.username}"></td>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <td class="col1"><input type="text" name="customerName" class="col2input"
                                                value="${orderForm.customerName}"></td>
                    </c:if>
                    <td><form:errors cssClass="error" path="customerName" cssStyle="color:red"/></td>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Surname: </label></td>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <td><input type="text" name="customerSurname" class="col2input"
                                   value="${currentUser.user_surname}"></td>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <td><input type="text" name="customerSurname" class="col2input"
                                   value="${orderForm.customerSurname}"></td>
                    </c:if>
                    <td><form:errors cssClass="error" path="customerSurname" cssStyle="color:red"/>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">e-mail: </label></td>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <td><input type="text" name="email" class="col2input" value="${currentUser.user_email}"></td>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <td><input type="text" name="email" class="col2input" value="${orderForm.email}"></td>
                    </c:if>
                    <td><form:errors cssClass="error" path="email" cssStyle="color:red"/>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Telephone number: </label></td>
                    <td><input type="text" name="phoneNumber" class="col2input" value="${orderForm.phoneNumber}"></td>
                    <td><form:errors cssClass="error" path="phoneNumber" cssStyle="color:red"/>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Arrival time: </label></td>
                    <td>
                            <%--<% String date;
                                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:MM").format(new Date());
                                out.println(date);%>--%>
                        <input type="datetime-local" name="dateTime" class="col2input" min="startCurrentWeek">
                    </td>
                    <td><form:errors cssClass="error" path="arrivalTime" cssStyle="color:red"/>
                    <span style="color:red">${error}</span>
                </tr>
                <tr>
                    <td><label class="col-2 col-form-label">Address: </label></td>
                    <td><input type="text" name="address" class="col2input" value="${orderForm.address}"></td>
                    <td><form:errors cssClass="error" path="address" cssStyle="color:red"/>
                </tr>
            </table>
            <input type="submit" name="operation" class="btn btn-lg btn-primary"
                   value="Submit"/>
        </div>

    </form:form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resourses/js/bootstrap.min.js"></script>
</body>
</html>