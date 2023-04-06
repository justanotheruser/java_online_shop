<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>
<div id="content" class="container">
    <h3>Hello: ${loggedInUser.username}</h3>

    User Name: <b>${loggedInUser.fullName}</b>
    <br />
</div>
</body>

<jsp:include page="includes/footer.jsp"/>