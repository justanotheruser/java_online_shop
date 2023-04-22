<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="includes/header.jsp"/>
<body>
    <div class="orderSuccess">
        <h1>Заказ оформлен успешно!</h1>
        <h3>Заявка отправлена администратору на почту</h3>
        <span>Вы будете перенаправлены назад к выбору товаров через 5 секунд</span>
    </div>
<script>
    window.onload = (ev) => {
        setTimeout(redirect, 5000);
    }
    function redirect () {
        window.location.href = '${root}/items';
    }
</script>
</body>
</html>