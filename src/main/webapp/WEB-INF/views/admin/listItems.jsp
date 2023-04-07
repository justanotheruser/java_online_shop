<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:include page="../includes/adminHeader.jsp"/>

<body>
<jsp:include page="../includes/menu.jsp"/>
<button><a href="${root}/admin/createItem">Добавить</a></button>
<section class="u-align-center u-clearfix u-palette-5-light-1 u-section-1" id="carousel_0af7">
    <div class="u-clearfix u-sheet u-valign-middle u-sheet-1">
        <div class="u-expanded-width u-list u-list-1">
            <c:forEach var="itemRow" items="${itemRows}">
            <div class="u-repeater u-repeater-1">
                <c:forEach var="item" items="${itemRow}">
                <div class="u-align-center u-container-style u-list-item u-repeater-item">
                    <div class="u-container-layout u-similar-container u-valign-top u-container-layout-3">
                        <img alt="" class="u-border-7 u-border-grey-5 u-expanded-width u-image u-image-default u-image-3" src="data:image/jpg;base64,${item.base64Image}">
                        <h4 class="u-custom-font u-font-raleway u-text u-text-default u-text-5">${item.name}</h4>
                        <h3 class="u-text u-text-6">${item.price}₽</h3><br>
                        <h3 class="u-text u-text-6">Количество ${item.quantity}</h3>
                        <a href="<c:out value="${root}/admin/editItem?id=${item.id}"/>" class="u-btn u-btn-rectangle u-button-style u-white u-btn-3">Изменить</a>
                    </div>
                </div>
                </c:forEach>
            </div>
            </c:forEach>
        </div>
    </div>
</section>

<jsp:include page="../includes/footer.jsp"/>