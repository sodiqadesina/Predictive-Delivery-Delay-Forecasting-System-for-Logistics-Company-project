package ec.project.web.servlets;

import ec.project.jpa.PredictionService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/PredictionServlet")
public class PredictionServlet extends HttpServlet {

    @Inject
    private PredictionService predictionService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String type = request.getParameter("type");
            double daysForShippingReal = Double.parseDouble(request.getParameter("daysforshipping(real)"));
            double daysForShippingScheduled = Double.parseDouble(request.getParameter("daysforshipment(scheduled)"));
            double benefitPerOrder = Double.parseDouble(request.getParameter("benefitPerOrder"));
            double salesPerCustomer = Double.parseDouble(request.getParameter("salesPerCustomer"));
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            String shippingMode = request.getParameter("shippingMode");
            String orderStatus = request.getParameter("orderStatus");
            String orderRegion = request.getParameter("orderRegion");
            String orderCountry = request.getParameter("orderCountry");
            String orderCity = request.getParameter("orderCity");
            String market = request.getParameter("market");
            String deliveryStatus = request.getParameter("deliveryStatus");
            int orderDay = Integer.parseInt(request.getParameter("orderDay"));
            int orderMonth = Integer.parseInt(request.getParameter("orderMonth"));
            int orderYear = Integer.parseInt(request.getParameter("orderYear"));
            int shippingDay = Integer.parseInt(request.getParameter("shippingDay"));
            int shippingMonth = Integer.parseInt(request.getParameter("shippingMonth"));
            int shippingYear = Integer.parseInt(request.getParameter("shippingYear"));

            double prediction = predictionService.predictLateDeliveryRisk(
                    type, daysForShippingReal, daysForShippingScheduled, benefitPerOrder,
                    salesPerCustomer, latitude, longitude, shippingMode, orderStatus,
                    orderRegion, orderCountry, orderCity, market, deliveryStatus,
                    orderDay, orderMonth, orderYear, shippingDay, shippingMonth, shippingYear
            );

            request.setAttribute("result", prediction);
            request.getRequestDispatcher("/shipment/result.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing prediction: " + e.getMessage());
            request.getRequestDispatcher("/shipment/error.jsp").forward(request, response);
        }
    }
}
