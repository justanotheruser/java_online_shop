<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<div id="content" class="container">
    <c:forEach var="item" items="${items}">
        <div class="item-preview"><c:out value="${item.name}" /></div>
    </c:forEach>
</div>
</body>

<jsp:include page="includes/footer.jsp"/>