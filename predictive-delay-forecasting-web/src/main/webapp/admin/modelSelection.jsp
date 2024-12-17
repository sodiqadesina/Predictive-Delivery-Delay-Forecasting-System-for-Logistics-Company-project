<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Model Selection</title>
    <link rel="stylesheet" href="../resources/css/styles.css">
</head>
<body>
    <h1>Model Selection</h1>
    
    <form action="${pageContext.request.contextPath}/admin/modelSelection" method="post">
        <label for="modelName">Select a Model to Deploy:</label>
        <select name="modelName" id="modelName" required>
            <%
                // Retrieve models from the request scope
                java.util.List<ec.project.model.ModelMetadata> models = 
                    (java.util.List<ec.project.model.ModelMetadata>) request.getAttribute("models");
                if (models != null && !models.isEmpty()) {
                    for (ec.project.model.ModelMetadata model : models) {
            %>
                <option value="<%= model.getName() %>"><%= model.getName() %> (Accuracy: <%= model.getPerformanceMetrics() %>)</option>
            <%
                    }
                } else {
            %>
                <option disabled>No models available</option>
            <%
                }
            %>
        </select>
        <br><br>
        <input type="submit" value="Deploy Model">
    </form>
    
    <%-- Display success message if set --%>
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <p style="color: green;"><%= message %></p>
    <%
        }
    %>

    <a href="landing.jsp">Back to Admin Dashboard</a>
</body>
</html>
