<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<div id="content" class="container">
    <h3>${loggedInUser.username}</h3>
    ФИО: <b>${loggedInUser.fullName}</b><br />
    Email: <b>${loggedInUser.email}</b><br />
    <c:if test='${loggedInUser.companyName != ""}'>
        Компания: <b>${loggedInUser.companyName}</b> <br />
    </c:if>
    <c:if test="${loggedInUser.role != 'ADMIN'}">
    <a href="${root}/history">История заказов</a>
    </c:if>

</div>
</body>

<jsp:include page='includes/footer.jsp'/>