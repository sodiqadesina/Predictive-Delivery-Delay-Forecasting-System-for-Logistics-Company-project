package ec.project.ml;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import javax.ejb.Stateless;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelPredictor {

    public double predictLateDeliveryRisk(String modelFilePath, String type, double daysForShippingReal, double daysForShippingScheduled,
                                          double benefitPerOrder, double salesPerCustomer, double latitude, double longitude,
                                          String shippingMode, String orderStatus, String orderRegion, String orderCountry,
                                          String orderCity, String market, String deliveryStatus, int orderDay, int orderMonth,
                                          int orderYear, int shippingDay, int shippingMonth, int shippingYear) {
        try {
            // Load the model from the specified file path
            File modelFile = new File(modelFilePath);
            if (!modelFile.exists()) {
                throw new RuntimeException("Model file does not exist at: " + modelFilePath);
            }
            Classifier model;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(modelFile))) {
                model = (Classifier) in.readObject();
            }

            // Prepare the Weka dataset
            Instances dataSet = DataSource.read("C:/enterprise/workspace/project/Project/Ec-project/data/test_shipment_data.arff");
            dataSet.setClassIndex(dataSet.numAttributes() - 1);

            // Define attributes programmatically
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("type", new ArrayList<>(java.util.Arrays.asList("CASH", "DEBIT", "PAYMENT", "TRANSFER"))));
            attributes.add(new Attribute("daysforshipping(real)"));
            attributes.add(new Attribute("daysforshipment(scheduled)"));
            attributes.add(new Attribute("late_delivery_risk", new ArrayList<>(java.util.Arrays.asList("0", "1")))); // Class
            attributes.add(new Attribute("benefitperorder"));
            attributes.add(new Attribute("salespercustomer"));
            attributes.add(new Attribute("latitude"));
            attributes.add(new Attribute("longitude"));
            attributes.add(new Attribute("shippingmode", new ArrayList<>(java.util.Arrays.asList("First Class", "Same Day"))));
            attributes.add(new Attribute("orderstatus", new ArrayList<>(java.util.Arrays.asList("CANCELED", "CLOSED"))));
            attributes.add(new Attribute("orderregion", new ArrayList<>(java.util.Arrays.asList("Canada", "Caribbean"))));
            attributes.add(new Attribute("ordercountry", new ArrayList<>(java.util.Arrays.asList("Afganistán", "Albania"))));
            attributes.add(new Attribute("ordercity", new ArrayList<>(java.util.Arrays.asList("Aachen", "Aalen", "Aalst"))));
            attributes.add(new Attribute("market", new ArrayList<>(java.util.Arrays.asList("Africa", "Europe", "LATAM", "Pacific Asia", "USCA"))));
            attributes.add(new Attribute("deliverystatus", new ArrayList<>(java.util.Arrays.asList("Advance shipping", "Late delivery"))));
            attributes.add(new Attribute("order_day"));
            attributes.add(new Attribute("order_month"));
            attributes.add(new Attribute("order_year"));
            attributes.add(new Attribute("shipping_day"));
            attributes.add(new Attribute("shipping_month"));
            attributes.add(new Attribute("shipping_year"));

            // Create dataset matching the test features
            Instances inputData = new Instances("PredictionData", attributes, 0);
            inputData.setClassIndex(3); // Class index: late_delivery_risk

            // Create and populate instance
            Instance instance = new DenseInstance(attributes.size());
            instance.setValue(attributes.get(0), type);
            instance.setValue(attributes.get(1), daysForShippingReal);
            instance.setValue(attributes.get(2), daysForShippingScheduled);
            instance.setMissing(attributes.get(3)); // late_delivery_risk
            instance.setValue(attributes.get(4), benefitPerOrder);
            instance.setValue(attributes.get(5), salesPerCustomer);
            instance.setValue(attributes.get(6), latitude);
            instance.setValue(attributes.get(7), longitude);
            instance.setValue(attributes.get(8), shippingMode);
            instance.setValue(attributes.get(9), orderStatus);
            instance.setValue(attributes.get(10), orderRegion);
            instance.setValue(attributes.get(11), orderCountry);
            instance.setValue(attributes.get(12), orderCity);
            instance.setValue(attributes.get(13), market);
            instance.setValue(attributes.get(14), deliveryStatus);
            instance.setValue(attributes.get(15), orderDay);
            instance.setValue(attributes.get(16), orderMonth);
            instance.setValue(attributes.get(17), orderYear);
            instance.setValue(attributes.get(18), shippingDay);
            instance.setValue(attributes.get(19), shippingMonth);
            instance.setValue(attributes.get(20), shippingYear);

            inputData.add(instance);

            // Perform prediction
            double prediction = model.classifyInstance(inputData.firstInstance());
            System.out.println("Predicted Late Delivery Risk: " + prediction);
            return prediction;

        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN; // Return NaN to indicate an error
        }
    }
}
