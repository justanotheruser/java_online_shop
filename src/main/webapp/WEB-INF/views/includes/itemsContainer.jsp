<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="items-container">
    <c:forEach var="item" items="${items}">
        <div class="item-preview">
            <c:url value="item" var="itemUrl">
                <c:param name="id" value="${item.id}"/>
            </c:url>
            <a href="<c:out value="${itemUrl}"/>">${item.name}</a>
        </div>
    </c:forEach>
</div>