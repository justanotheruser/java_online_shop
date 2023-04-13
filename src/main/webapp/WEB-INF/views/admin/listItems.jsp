<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="../includes/header.jsp"/>

<body>
<jsp:include page="../includes/menu.jsp"/>

<div class="searchBox">
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            Выберите категорию
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <c:forEach var="category" items="${categories}">
                <li><a class="dropdown-item" href="${root}/admin/listItems?category=${category}">${category}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

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