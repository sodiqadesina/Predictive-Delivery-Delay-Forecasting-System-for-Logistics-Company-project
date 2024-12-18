<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shipment Prediction</title>
</head>
<body>
    <h2>Shipment Delay Prediction</h2>
   <form action="../PredictionServlet" method="post">
    <label>Type:</label> <input type="text" name="type" required><br>
    <label>Days for Shipping (Real):</label> <input type="number" step="any" name="daysForShippingReal" required><br>
    <label>Days for Shipping (Scheduled):</label> <input type="number" step="any" name="daysForShippingScheduled" required><br>
    <label>Benefit per Order:</label> <input type="number" step="any" name="benefitPerOrder" required><br>
    <label>Sales per Customer:</label> <input type="number" step="any" name="salesPerCustomer" required><br>
    <label>Latitude:</label> <input type="number" step="any" name="latitude" required><br>
    <label>Longitude:</label> <input type="number" step="any" name="longitude" required><br>
    <label>Shipping Mode:</label> <input type="text" name="shippingMode" required><br>
    <label>Order Status:</label> <input type="text" name="orderStatus" required><br>
    <label>Order Region:</label> <input type="text" name="orderRegion" required><br>
    <label>Order Country:</label> <input type="text" name="orderCountry" required><br>
    <label>Order City:</label> <input type="text" name="orderCity" required><br>
    <label>Market:</label> <input type="text" name="market" required><br>
    <label>Delivery Status:</label> <input type="text" name="deliveryStatus" required><br>
    <label>Order Day:</label> <input type="number" name="orderDay" required><br>
    <label>Order Month:</label> <input type="number" name="orderMonth" required><br>
    <label>Order Year:</label> <input type="number" name="orderYear" required><br>
    <label>Shipping Day:</label> <input type="number" name="shippingDay" required><br>
    <label>Shipping Month:</label> <input type="number" name="shippingMonth" required><br>
    <label>Shipping Year:</label> <input type="number" name="shippingYear" required><br>
    <input type="submit" value="Predict">
</form>

</body>
</html>
