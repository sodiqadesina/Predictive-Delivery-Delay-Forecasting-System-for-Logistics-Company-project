<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>
<% 
    ec.project.model.User user = (ec.project.model.User) session.getAttribute("user"); 
    if (user != null) {
%>
    <h1>Welcome, <%= user.getName() %>!</h1>
    <p>Use the form below to predict shipment delays.</p>
<%
    } else {
%>
    <p>User not logged in.</p>
<%
    }
%>
    <a href="../shipment/predictionForm.jsp">Predict Shipment Delay</a>
    <br><br>
    <a href="../index.jsp">Logout</a>
</body>
</html>
