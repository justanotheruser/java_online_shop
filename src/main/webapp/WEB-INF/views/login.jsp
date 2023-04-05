<jsp:include page="includes/header.jsp"/>

<body>
<jsp:include page="includes/menu.jsp"/>

<h3>Login Page</h3>

<p style="color: red;">${pageContext.request.getParameter("errorMessage")}</p>

<form method="POST" action="${pageContext.request.contextPath}/login">
  <input type="hidden" name="redirectId" value="${param.redirectId}" />
  <table>
    <tr>
      <td>User Name</td>
      <td><input type="text" name="userName" value= "${user.userName}" /> </td>
    </tr>
    <tr>
      <td>Password</td>
      <td><input type="password" name="password" value= "${user.password}" /> </td>
    </tr>

    <tr>
      <td colspan ="2">
        <input type="submit" value= "Submit" />
        <a href="${pageContext.request.contextPath}/">Cancel</a>
      </td>
    </tr>
  </table>
</form>
</body>

<jsp:include page="includes/footer.jsp"/>