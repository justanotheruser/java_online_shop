<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<h1>Заказ #${orderId}</h1>
<table>
  <tr>
    <th>Товар</th>
    <th>Фото</th>
    <th>Цена</th>
    <th>Количество</th>
  </tr>
  <c:forEach var="orderItem" items="${orderItems}">
  <tr>
    <td>${orderItem.item.name}</td>
    <td><img class="card-img-top mb-5 mb-md-0" src="data:image/jpg;base64,${orderItem.item.base64Image}" alt="..." /></td>
    <td>${orderItem.item.price}</td>
    <td>${orderItem.quantity}</td>
  </tr>
  </c:forEach>
</table>

<jsp:include page="includes/footer.jsp"/>