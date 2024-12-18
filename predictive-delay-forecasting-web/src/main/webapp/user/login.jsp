<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
</head>
<body>
    <h2>User Login</h2>
    <form action="<%= request.getContextPath() %>/UserLoginServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required><br><br> <!-- Updated name attribute -->
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <p>Don't have an account? <a href="signup.jsp">Sign up here</a></p>
</body>
</html>
