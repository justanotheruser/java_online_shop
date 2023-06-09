<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>

<table>
    <tr>
        <th></th>
        <th>Товар</th>
        <th>Фото</th>
        <th>Цена</th>
        <th>Количество</th>
        <th>Итого</th>
    </tr>
    <c:set var="total" value="0"></c:set>
    <c:forEach var="item" items="${sessionScope.cart}">
        <c:set var="total" value="${total + item.product.price * item.quantity }"></c:set>
        <tr>
            <td>
                <a href="${root}/cart?action=remove&id=${item.product.id }"
                   onclick="return confirm('Вы уверены?')">Убрать</a>
            </td>
            <td>${item.product.name }</td>
            <td>
                <img src="data:image/jpg;base64,${item.product.base64Image}" alt="" width="120">
            </td>
            <td>${item.product.price }</td>
            <td>${item.quantity }</td>
            <td>${item.product.price * item.quantity }</td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="5">Total</td>
        <td>${total}</td>
    </tr>
</table>
<br>

<c:if test="${not empty orderProblems}">
<span><h5>Заказ может прийти с задержкой</h5></span><br>
</c:if>
<c:forEach var="orderProblem" items="${orderProblems}">
    <div class="orderProblem">
        <span>Количество товара "${orderProblem.itemName}" больше, чем доступное на складе</span> <br>
        <span>Количество в заказе: ${orderProblem.orderedQuantity}</span> <br>
        <span>Количество в на складе: ${orderProblem.availableQuantity}</span>
    </div>
</c:forEach>

<a href="${root}">Продолжить покупки</a><br>

<h3>Оформить заказ</h3>
<form method="post">
    <label>
        Способ доставки
        <input type="text" name="method">
    </label><br>
    <label>
        Дополнительная информация
        <input type="text" name="additionalNotes">
    </label><br>
    <input type="submit" value="Оформить заказ">
</form>

<jsp:include page="includes/footer.jsp"/>
