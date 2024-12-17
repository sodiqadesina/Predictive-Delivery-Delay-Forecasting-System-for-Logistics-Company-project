<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>User Login</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        <label>Username: <input type="text" name="username" /></label><br/>
        <label>Password: <input type="password" name="password" /></label><br/>
        <input type="submit" value="Login" />
    </form>
</body>
</html>
