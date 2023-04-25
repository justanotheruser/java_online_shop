<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>
<body>
<div class="orderSuccess">
    <h1>Упс!</h1>
    <h3>Что-то пошло не так. Похоже, ссылка для активации не валидна</h3>
</div>
</body>
</html>