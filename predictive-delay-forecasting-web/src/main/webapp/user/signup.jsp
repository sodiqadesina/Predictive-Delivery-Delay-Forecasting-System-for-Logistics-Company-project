<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Signup</title>
</head>
<body>
    <h2>User Signup</h2>
    <form action="UserSignupServlet" method="post">
        <label for="name">Username:</label>
        <input type="text" name="name" id="name" required><br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required><br><br>
        <input type="submit" value="Signup">
    </form>
</body>
</html>
