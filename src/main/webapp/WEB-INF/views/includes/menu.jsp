<nav class="navbar navbar-default">
  <div class="container-fluid">
    <ul class="nav navbar-nav">
      <li><a href="${pageContext.request.contextPath}/userInfo">User Info</a></li>
      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
      <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
      <span style="color:red">[ ${loggedInUser.username} ]</span>
    </ul>
  </div>
</nav>