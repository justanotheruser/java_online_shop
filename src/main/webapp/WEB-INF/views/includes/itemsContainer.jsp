<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="root" value="${pageContext.request.contextPath}" />
<div class="items-container">
    <c:forEach var="item" items="${items}">
        <div class="item-preview">
            <a href="<c:out value="${root}/item?id=${item.id}"/>">${item.name}</a>
        </div>
    </c:forEach>
</div>