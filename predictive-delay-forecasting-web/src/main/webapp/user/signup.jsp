<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Signup</title>
</head>
<body>
    <h2>User Signup</h2>
    <form action="<%= request.getContextPath() %>/UserSignupServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required><br><br>
        <input type="submit" value="Signup">
    </form>
</body>
</html>
