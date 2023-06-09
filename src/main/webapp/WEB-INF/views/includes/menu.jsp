<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<c:catch var="exception">
  <c:set var="cartSize" value="${sessionScope.cart.size()}"/>
</c:catch>
<c:if test="${not empty exception}">
  <c:set var="cartSize" value="0"/>
</c:if>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container px-4 px-lg-5">
    <a class="navbar-brand" href="${root}/items">Супер-Пупер Магазин</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
        <c:if test="${not empty loggedInUser}">
          <c:if test="${loggedInUser.role == 'CUSTOMER'}">
            <li class="nav-item"><a class="nav-link" href="<c:out value="${root}/items"/>">Все товары</a></li>
          </c:if>
          <li class="nav-item"><a class="nav-link" href="<c:out value="${root}/items/new"/>">Новинки</a></li>
        </c:if>
        <c:if test="${empty loggedInUser}">
          <li class="nav-item"><a class="nav-link" href="${root}/login">Войти</a></li>
          <li class="nav-item"><a class="nav-link" href="${root}/registration">Регистрация</a></li>
        </c:if>
        <c:if test="${not empty loggedInUser}">
          <li class="nav-item"><a class="nav-link" href="${root}/userInfo">Профиль</a></li>
          <c:if test="${loggedInUser.role == 'ADMIN'}">
            <li class="nav-item"><a class="nav-link" href="${root}/admin/listItems">Товары</a></li>
            <li class="nav-item"><a class="nav-link" href="${root}/admin/listOrders">Заказы</a></li>
            <li class="nav-item"><a class="nav-link" href="${root}/admin/listUsers">Пользователи</a></li>
          </c:if>
          <li class="nav-item"><a class="nav-link" href="${root}/logout">Выйти</a></li>
          <span style="color:red">[ ${loggedInUser.username} ]</span>
        </c:if>
      </ul>
      <c:if test="${not empty loggedInUser}">
        <c:if test="${loggedInUser.role == 'CUSTOMER'}">
          <form class="d-flex" action="${root}/cart">
              <button class="btn btn-outline-dark" type="submit">
                <i class="bi-cart-fill me-1"></i>
                Корзина
                <span class="badge bg-dark text-white ms-1 rounded-pill">${cartSize}</span>
              </button>
          </form>
        </c:if>
      </c:if>
    </div>
  </div>
</nav>