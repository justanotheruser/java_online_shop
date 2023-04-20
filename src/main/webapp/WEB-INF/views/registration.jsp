<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>

<h3>Регистрация</h3>

<p style="color: red;">${pageContext.request.getAttribute("errorMessage")}</p>

<form method="POST">
    <label>
        Имя пользователя
        <input name="username" type="text"/>
    </label><br>
    <label>
        Пароль
        <input name="password" type="password"/>
    </label><br>
    <label>
        Повторите пароль
        <input name="passwordConfirmation" type="password"/>
    </label><br>
    <label>
        E-mail
        <input name="email" type="email"/>
    </label><br>
    <label>
        ФИО
        <input name="fullName" type="text">
    </label><br>
    <label>
        Телефон
        <input name="phone" type="text">
    </label><br>
    <label>
        Компания
        <input name="companyName" type="text">
    </label><br>
    <input type="submit" value="Зарегистрироваться"/>
</form>
</body>

<jsp:include page="includes/footer.jsp"/>