<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shipment Prediction</title>
</head>
<body>
    <h2>Shipment Delay Prediction</h2>
    <form action="../PredictionServlet" method="post">
        <label>Latitude:</label>
        <input type="number" step="any" name="latitude" required><br><br>
        <label>Longitude:</label>
        <input type="number" step="any" name="longitude" required><br><br>
        <label>Benefit per Order:</label>
        <input type="number" step="any" name="benefitPerOrder" required><br><br>
        <label>Sales per Customer:</label>
        <input type="number" step="any" name="salesPerCustomer" required><br><br>
        <input type="submit" value="Predict">
    </form>
</body>
</html>
