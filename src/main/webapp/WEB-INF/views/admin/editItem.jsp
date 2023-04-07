<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="../includes/header.jsp"/>

<body>
<jsp:include page="../includes/menu.jsp"/>
<h1>Изменение товара</h1>
<form method="POST">
  <label>
    Название
    <input name="name" type="text" value="${item.name}"/>
  </label><br>
  <label>
    Категория
    <input name="category" type="text" value="${item.category}"/>
  </label><br>
  <label>
    Марка
    <input name="brand" type="text" value="${item.brand}"/>
  </label><br>
  <label>
    Производитель
    <input name="manufacturer" type="text" value="${item.manufacturer}"/>
  </label><br>
  <label>
    Код товара
    <input name="partNumber" type="number" value="${item.partNumber}"/>
  </label><br>
  <label>
    Описание
    <textarea rows="10" cols="45" name="description" value="${item.description}"></textarea>
  </label><br>
  <label>
    Цена
    <input name="price" type="number" value="${item.price}"/>
  </label><br>
  <label>
    Количество
    <input name="quantity" type="number" value="${item.quantity}"/>
  </label><br>
  <input type="submit" value="Сохранить"/>
</form>

<form enctype="multipart/form-data" method="POST">
  <label>
    Фото
    <input name="image" type="file" accept="image/png, image/jpeg"/>
    <input type="submit" value="Сохранить изображение"/>
  </label><br>
</form>

<jsp:include page="../includes/footer.jsp"/>