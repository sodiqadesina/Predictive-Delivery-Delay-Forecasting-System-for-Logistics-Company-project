<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Model Selection</title>
</head>
<body>
    <h2>Select Model to Deploy</h2>
    <form action="ModelSelectionServlet" method="post">
        <table border="1">
            <tr>
                <th>Model Name</th>
                <th>Performance Metrics</th>
                <th>Select</th>
            </tr>
            <c:forEach var="model" items="${models}">
                <tr>
                    <td>${model.name}</td>
                    <td>${model.performanceMetrics}</td>
                    <td>
                        <input type="radio" name="selectedModel" value="${model.name}" required>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <input type="submit" value="Deploy Selected Model">
    </form>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
</body>
</html>
