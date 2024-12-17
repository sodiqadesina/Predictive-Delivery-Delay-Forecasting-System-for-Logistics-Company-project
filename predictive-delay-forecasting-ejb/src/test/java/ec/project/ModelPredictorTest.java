package ec.project;

import org.junit.Test;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ModelPredictorTest {

    @Test
    public void testPredictLateDeliveryRisk() {
        long startTime = System.currentTimeMillis();
        try {
            // Arrange: Load the saved model
            String modelFilePath = "C:/enterprise/tmp/model/project/Random_Forest_Model.bin";
            File modelFile = new File(modelFilePath);
            if (!modelFile.exists()) {
                fail("Model file does not exist at: " + modelFilePath);
            }

            Classifier model;
            try (ObjectInputStream modelIn = new ObjectInputStream(new FileInputStream(modelFile))) {
                model = (Classifier) modelIn.readObject();
            }
            assertNotNull("Loaded model should not be null", model);
            System.out.println("Model loaded successfully.");

            // Define attributes programmatically
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("type", new ArrayList<>(java.util.Arrays.asList("CASH", "DEBIT", "PAYMENT", "TRANSFER"))));
            attributes.add(new Attribute("daysforshipping(real)"));
            attributes.add(new Attribute("daysforshipment(scheduled)"));
            attributes.add(new Attribute("late_delivery_risk", new ArrayList<>(java.util.Arrays.asList("0", "1")))); // Class attribute
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

            // Create dataset with defined schema
            Instances dummyData = new Instances("TestInstances", attributes, 0);
            dummyData.setClassIndex(3); // Set late_delivery_risk at position 3 as class index

            // Create dummy instance and set values
            Instance dummyInstance = new DenseInstance(attributes.size());
            dummyInstance.setValue(attributes.get(0), "CASH");         // type
            dummyInstance.setValue(attributes.get(1), 3.0);            // daysforshipping(real)
            dummyInstance.setValue(attributes.get(2), 4.0);            // daysforshipment(scheduled)
            dummyInstance.setMissing(attributes.get(3));               // late_delivery_risk: set to ?
            dummyInstance.setValue(attributes.get(4), 91.25);          // benefitperorder
            dummyInstance.setValue(attributes.get(5), 314.64);         // salespercustomer
            dummyInstance.setValue(attributes.get(6), 18.25);          // latitude
            dummyInstance.setValue(attributes.get(7), -66.03);         // longitude
            dummyInstance.setValue(attributes.get(8), "Same Day");     // shippingmode
            dummyInstance.setValue(attributes.get(9), "CLOSED");       // orderstatus
            dummyInstance.setValue(attributes.get(10), "Canada");      // orderregion
            dummyInstance.setValue(attributes.get(11), "Afganistán");  // ordercountry
            dummyInstance.setValue(attributes.get(12), "Aachen");      // ordercity
            dummyInstance.setValue(attributes.get(13), "Europe");      // market
            dummyInstance.setValue(attributes.get(14), "Late delivery");// deliverystatus
            dummyInstance.setValue(attributes.get(15), 31);            // order_day
            dummyInstance.setValue(attributes.get(16), 1);             // order_month
            dummyInstance.setValue(attributes.get(17), 2018);          // order_year
            dummyInstance.setValue(attributes.get(18), 3);             // shipping_day
            dummyInstance.setValue(attributes.get(19), 2);             // shipping_month
            dummyInstance.setValue(attributes.get(20), 2018);          // shipping_year

            dummyData.add(dummyInstance);
            System.out.println("Dummy instance created successfully.");

            // Act: Perform the prediction
            double prediction = model.classifyInstance(dummyData.instance(0));
            System.out.printf("Predicted Late Delivery Risk: %.2f%n", prediction);

            // Assert: Check that prediction is valid
            assertTrue("Prediction should be 0 or 1", prediction == 0.0 || prediction == 1.0);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during prediction: " + e.getMessage());
        }
    }
} 