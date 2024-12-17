package ec.project.web.servlets;

import ec.project.jpa.PredictionService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PredictionServlet extends HttpServlet {
    @Inject
    private PredictionService predictionService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));

        double result = predictionService.predictLateDeliveryRisk("RandomForest", latitude, longitude);
        request.setAttribute("result", result);
        request.getRequestDispatcher("/shipment/result.jsp").forward(request, response);
    }
}
