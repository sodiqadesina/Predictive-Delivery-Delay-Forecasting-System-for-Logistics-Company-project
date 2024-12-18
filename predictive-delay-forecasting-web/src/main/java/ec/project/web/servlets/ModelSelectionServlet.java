package ec.project.web.servlets;

import ec.project.jpa.PredictionService;
import ec.project.model.ModelMetadata;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/ModelSelectionServlet")
public class ModelSelectionServlet extends HttpServlet {

    @Inject
    private PredictionService predictionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Calling getAllModels() to fetch models.");
            List<ModelMetadata> models = predictionService.getAllModels();

            if (models != null && !models.isEmpty()) {
                models.forEach(model -> 
                    System.out.println("Retrieved model: " + model.getName() + " with metrics: " + model.getPerformanceMetrics())
                );
            } else {
                System.out.println("No models found in the database.");
            }

            // Attach models to request
            request.setAttribute("models", models);

            // Forward request to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/modelSelection.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load models: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/modelSelection.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedModelName = request.getParameter("selectedModel");
        try {
            // Deploy the selected model
            predictionService.deployModel(selectedModelName);

            // Redirect with success message
            response.sendRedirect("/admin/landing.jsp?success=Model " + selectedModelName + " deployed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to deploy model: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/modelSelection.jsp");
            dispatcher.forward(request, response);
        }
    }
}
