package ec.project.web.servlets;

import ec.project.model.ModelMetadata;
import ec.project.service.PredictionService;

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
        // Fetch all available models from the database
        List<ModelMetadata> models = predictionService.getAllModels();
        request.setAttribute("models", models);
        request.getRequestDispatcher("/admin/modelSelection.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the selected model name
        String modelName = request.getParameter("modelName");

        // Deploy the selected model
        predictionService.deployModel(modelName);

        // Redirect to admin landing page with a success message
        request.setAttribute("message", "Model '" + modelName + "' has been successfully deployed!");
        request.getRequestDispatcher("/admin/landing.jsp").forward(request, response);
    }
}
