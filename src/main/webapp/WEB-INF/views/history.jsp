<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<h1>Список заказов</h1>
<table>
    <th>#</th>
    <th>Сумма</th>
    <th>Способ доставки</th>
    <th>Статус</th>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><a href="${root}/history/order?id=${order.id}">${order.id}</a></td>
            <td>${order.totalPrice}</td>
            <td>${order.deliveryMethod}</td>
            <td>${order.status}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="includes/footer.jsp"/>