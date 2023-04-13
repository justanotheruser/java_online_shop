<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="../includes/header.jsp"/>

<body>
<jsp:include page="../includes/menu.jsp"/>
<h1>Добавление нового товара</h1>
<form method="POST">
    <label>
        Название
        <input name="name" type="text"/>
    </label><br>
    <label>
        Категория
        <input name="category" type="text"/>
    </label><br>
    <label>
        Марка
        <input name="brand" type="text"/>
    </label><br>
    <label>
        Производитель
        <input name="manufacturer" type="text"/>
    </label><br>
    <label>
        Код товара
        <input name="partNumber" type="text"/>
    </label><br>
    <label>
        Описание
        <textarea rows="10" cols="45" name="description"></textarea>
    </label><br>
    <label>
        Цена
        <input name="price" type="number"/>
    </label><br>
    <label>
        Количество
        <input name="quantity" type="number"/>
    </label><br>
    <input type="submit" value="Создать"/>
</form>
<jsp:include page="../includes/footer.jsp"/>