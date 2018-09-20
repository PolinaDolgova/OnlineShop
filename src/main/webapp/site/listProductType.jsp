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

    <title>All products</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/CssForShop.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <style>
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

                        <li><a href="/profile">Profile</a></li>

                        <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                            <li class="active"><a href='/listProductType'>Product Management<span class="sr-only">(current)</span></a>
                            </li>
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

<div class="container">
    <span style="color:green">${message}</span>
    <form:form method="POST" action="/listProductType" modelAttribute="productTypeListForm"
               name="productTypeListForm">
        <div class="adminPanelForWorkWithProductType">
            <div class="fieldInAdminPanelFor">
                <input type="submit" name="operation" class="btn btn-lg btn-primary btn-block"
                       value="Create new"/>
                </br>
            </div>
            <div class="form-group ${error != null ? 'has-error' : ''}">
                <span style="color:red">${error}</span>
                <div class="fieldInAdminPanel">
                    <input type="submit" name="operation" class="btn btn-md btn-primary btn-block"
                           value="Modify"/>
                    </br>
                </div>
                <div class="fieldInAdminPanel">
                    <input type="submit" name="operation" class="btn btn-md btn-primary btn-block"
                           value="Delete"
                           onclick="return confirm('Do you want to delete productType?')"/>
                    </br>
                </div>
            </div>
        </div>
        <h3 class="form-heading">All products:</h3>
        <table class="table table-hover">
            <tbody>
            <c:forEach items="${allProductTypes}" var="productType">
                <tr>
                    <td><input type="radio" name="productTypeId" value="${productType.id}"/></td>
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
                                            <td><b>Price:</b></td>
                                            <td>${productType.price}</td>
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
    </form:form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!--<script src="${contextPath}/resourses/js/bootstrap.min.js"></script>-->
</body>

</html>
