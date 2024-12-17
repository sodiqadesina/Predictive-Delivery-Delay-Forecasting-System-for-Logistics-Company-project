<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>
    <h2>Welcome, ${sessionScope.user}!</h2>
    <p>Use the form below to predict shipment delays.</p>
    <a href="../shipment/predictionForm.jsp">Predict Shipment Delay</a>
    <br><br>
    <a href="../index.jsp">Logout</a>
</body>
</html>
