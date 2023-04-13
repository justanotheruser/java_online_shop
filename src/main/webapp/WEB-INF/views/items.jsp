<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="includes/header.jsp"/>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<div class="searchBox">
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            Выберите категорию
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <c:forEach var="category" items="${categories}">
                <li><a class="dropdown-item" href="${root}/items?category=${category}">${category}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<form method="POST">
    <label>
        Поиск по партнамберу
        <input name="partNumber" type="text">
    </label>
    <input type="submit" value="Искать">
</form>

<form method="POST">
    <label>
        Поиск по описанию
        <input name="keyword" type="text">
    </label>
    <input type="submit" value="Искать">
</form>


<section class="py-5">
    <jsp:include page="includes/itemsContainer.jsp"/>
</section>


<jsp:include page="includes/footer.jsp"/>