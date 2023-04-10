<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="../includes/header.jsp"/>

<body>
<jsp:include page="../includes/menu.jsp"/>
<button><a href="${root}/admin/createItem">Добавить</a></button>
<table>
    <tr>
        <th>Имя</th>
        <th>Категория</th>
        <th>Брэнд</th>
        <th>Производитель</th>
        <th>Партнамбер</th>
        <th>Количество</th>
        <th>Рейтинг</th>
        <th>Цена</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td><a href="<c:out value="${root}/admin/editItem?id=${item.id}"/>">${item.name}</a></td>
            <td>${item.category}</td>
            <td>${item.brand}</td>
            <td>${item.manufacturer}</td>
            <td>${item.partNumber}</td>
            <td>${item.quantity}</td>
            <td>${item.averageRating}</td>
            <td>${item.price}₽</td>
        </tr>
    </c:forEach>
</table>



<jsp:include page="../includes/footer.jsp"/>