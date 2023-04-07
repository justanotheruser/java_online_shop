<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<!-- Product section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 my-5">
        <div class="row gx-4 gx-lg-5 align-items-center">
            <div class="col-md-6"><img class="card-img-top mb-5 mb-md-0" src="data:image/jpg;base64,${item.base64Image}" alt="..." /></div>
            <div class="col-md-6">
                <div class="small mb-1">${item.brand}</div>
                <div class="small mb-2">${item.manufacturer}</div>
                <h1 class="display-5 fw-bolder">${item.name}</h1>
                <div class="fs-5 mb-5">
                    <span>${item.price}₽</span>
                </div>
                <p class="lead">${item.description}</p>
                <div class="d-flex">
                    <input class="form-control text-center me-3" id="inputQuantity" type="num" value="1" style="max-width: 3rem" />
                    <a href="${pageContext.request.contextPath}/cart?&action=buy&id=${item.id}">
                        <button class="btn btn-outline-dark flex-shrink-0" type="button">
                            <i class="bi-cart-fill me-1"></i>
                            Добавить в корзину
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="includes/footer.jsp"/>