<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<h1>Заказ #${order.id}</h1>
<table>
  <tr>
    <th>Товар</th>
    <th>Фото</th>
    <th>Цена</th>
    <th>Количество</th>
  </tr>
  <c:forEach var="orderItem" items="${orderItems}">
  <tr>
    <td> <a href="<c:out value="${root}/item?id=${orderItem.item.id}"/>">${orderItem.item.name}</a></td>
    <td><img class="card-img-top mb-5 mb-md-0" width="120" height="100" src="data:image/jpg;base64,${orderItem.item.base64Image}" alt="..." /></td>
    <td>${orderItem.item.price}</td>
    <td>${orderItem.quantity}</td>
  </tr>
  </c:forEach>
</table>

<b>Дата оформления:</b> ${order.dateCreated}<br>
<c:if test="${not empty order.additionalNotes}">
<b>Доп. информация:</b> ${order.additionalNotes}<br>
</c:if>

<jsp:include page="includes/footer.jsp"/>