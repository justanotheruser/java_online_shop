<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="../includes/header.jsp"/>

<body>
<jsp:include page="../includes/menu.jsp"/>
<h1>Список пользователей</h1>
<table>
    <th>Имя</th>
    <th>Почта</th>
    <th>Роль</th>
    <th>Активирован</th>
    <th>Заблокирован</th>
    <th>Заблокировать</th>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>${user.isActive}</td>
            <td>${user.isBlocked}</td>
            <td>
                <c:if test="${user.isBlocked}">
                    <a href="${root}/admin/listUsers?action=unblock&id=${user.id}">Разблокировать</a>
                </c:if>
                <c:if test="${not user.isBlocked}">
                    <a href="${root}/admin/listUsers?action=block&id=${user.id}">Заблокировать</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="../includes/footer.jsp"/>