package ec.project.web.servlets;

import ec.project.jpa.PredictionService;
import ec.project.model.ModelMetadata;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ModelSelectionServlet extends HttpServlet {
    @Inject
    private PredictionService predictionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve all available models from the database
        List<ModelMetadata> models = predictionService.getAllModels();

        // Set the models as a request attribute to display in JSP
        request.setAttribute("models", models);

        // Forward to modelSelection.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/modelSelection.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the model name selected by the admin
        String selectedModelName = request.getParameter("selectedModel");

        try {
            // Deploy the selected model
            predictionService.deployModel(selectedModelName);

            // Redirect to admin landing page with success message
            response.sendRedirect("/admin/landing.jsp?success=Model " + selectedModelName + " deployed successfully");
        } catch (Exception e) {
            // Handle any errors
            request.setAttribute("error", "Failed to deploy model: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/modelSelection.jsp");
            dispatcher.forward(request, response);
        }
    }
}
