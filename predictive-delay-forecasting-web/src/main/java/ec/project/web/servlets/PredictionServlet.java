package ec.project.web.servlets;

import ec.project.model.Shipment;
import ec.project.service.PredictionService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PredictionServlet extends HttpServlet {
    @Inject
    private PredictionService predictionService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        double benefit = Double.parseDouble(request.getParameter("benefit"));
        double sales = Double.parseDouble(request.getParameter("sales"));

        Shipment shipment = new Shipment(origin, destination, benefit, sales);
        double risk = predictionService.predictLateDeliveryRisk("DecisionTree_Model", shipment);

        request.setAttribute("risk", risk);
        request.getRequestDispatcher("/shipment/result.jsp").forward(request, response);
    }
}
