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

    <title>Shop</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/CssForShop.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <style>
        button {
            display: block;
        }
        .price{
            font-size: 16px;
            color: #204d74;
        }
        .innerTable1 {
            width: 200px;
            border-spacing: 7px 11px;
        }
        .innerTable2 {
            width: 140px;
            border-spacing: 7px 11px;
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
                        <div style="margin: 5px 0px 0px 5px;height: 10px;">${pageContext.request.userPrincipal.name}</div>
                        <button class="tableButton" style="margin: -15px 0px 0px 50px;" onclick="document.forms['logoutForm'].submit()">Logout</button>
                    </c:if>
                </div>
            </div>

        </nav>
    </footer>
</div>
<div>
    <input type="hidden" name="userEmail" value="${pageContext.request.userPrincipal.name}"/>
    <form:form method="POST" action="/shop" modelAttribute="orderForm"
               name="orderForm">
        <div class="container">
            <h3 class="form-heading">Product catalog:</h3>
            <table class="table table-hover">
                <tbody>
                <c:forEach items="${allProductTypes}" var="productType">
                    <tr>
                        <td><input type="checkbox" name="product" value="${productType.id}"/></td>
                        <td><a>${productType.name}</a>
                            <table>
                                <tr valign="top">
                                    <td>
                                        <table class="innerTable1">
                                            <tr>
                                                <td><b> Category:</b></td>
                                                <td>
                                                    <c:forEach items="${productType.categories}" var="category">
                                                        ${category.name};
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><b>Producer:</b></td>
                                                <td>${productType.producer.name}</td>
                                            </tr>
                                            <tr>
                                                <td><b class="price">Price:</b></td>
                                                <td><b class="price">${productType.price}</b></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td>
                                        <table class="innerTable2">
                                            <tr>
                                                <td><b>Attribute</b></td>
                                                <td><b>Value</b></td>
                                            </tr>
                                            <c:forEach items="${productType.attributes}" var="attribute" varStatus="status">
                                                <tr>
                                                    <td class="col1">${attribute.name}</td>
                                                    <td>${productType.values[status.index].value}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <img src="../resources/images/${productType.image}" width="200" height="200">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div style="position: fixed; bottom: 15px; right: 15px;">
            <button class="btn btn-lg btn-primary" type="submit">Place an order</button>
        </div>
    </form:form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resourses/js/bootstrap.min.js"></script>
</body>
</html>