<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Model Selection</title>
</head>
<body>
    <h1>Model Selection</h1>
    <c:if test="${not empty error}">
        <div style="color: red;">
            <p>${error}</p>
        </div>
    </c:if>
    <c:if test="${not empty success}">
        <div style="color: green;">
            <p>${success}</p>
        </div>
    </c:if>

    <form action="/admin/ModelSelectionServlet" method="post">
        <table border="1">
            <thead>
                <tr>
                    <th>Select</th>
                    <th>Model Name</th>
                    <th>Performance Metrics</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${models}" var="model">
                    <tr>
                        <td>
                            <input type="radio" name="selectedModel" value="${model.name}" />
                        </td>
                        <td>${model.name}</td>
                        <td>${model.performanceMetrics}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        <input type="submit" value="Deploy Selected Model" />
    </form>
</body>
</html>
