<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
</head>
<body>
    <h2>User Login</h2>
    <form action="UserLoginServlet" method="post">
        <label for="name">Username:</label>
        <input type="text" name="name" id="name" required><br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <p>Don't have an account? <a href="signup.jsp">Sign up here</a></p>
</body>
</html>
