package ec.project.ml;

import ec.project.dao.ModelDAO;
import ec.project.dao.ShipmentDAO;
import ec.project.model.ModelMetadata;
import ec.project.model.Shipment;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

@Stateless
public class ModelPredictor {

    @Inject
    private ModelDAO modelDAO;

    @Inject
    private ShipmentDAO shipmentDAO;

    public double predictLateDeliveryRisk(String modelName, Long shipmentId) {
        try {
            // Load the model metadata from the database
            ModelMetadata metadata = modelDAO.getModelByName(modelName);
            if (metadata == null) {
                throw new RuntimeException("Model not found: " + modelName);
            }

            // Deserialize the model from the BLOB
            Classifier model;
            try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(metadata.getModelBlob().getBytes(1, (int) metadata.getModelBlob().length())))) {
                model = (Classifier) in.readObject();
            }

            // Retrieve the shipment record from the database
            Shipment shipment = shipmentDAO.getShipmentById(shipmentId);
            if (shipment == null) {
                throw new RuntimeException("Shipment not found with ID: " + shipmentId);
            }

            // Prepare the data instance for prediction
            Instances dataSet = DataSource.read("c://enterprise/tmp/shipment.arff");
            dataSet.setClassIndex(dataSet.numAttributes() - 1);

            Instance instance = dataSet.firstInstance(); // Placeholder for actual instance mapping
            instance.setValue(dataSet.attribute("daysForShippingReal"), shipment.getDaysForShippingReal());
            instance.setValue(dataSet.attribute("daysForShippingScheduled"), shipment.getDaysForShippingScheduled());
            instance.setValue(dataSet.attribute("benefitPerOrder"), shipment.getBenefitPerOrder());
            instance.setValue(dataSet.attribute("salesPerCustomer"), shipment.getSalesPerCustomer());

            // Perform the prediction
            double riskPrediction = model.classifyInstance(instance);

            System.out.println("Predicted Late Delivery Risk: " + riskPrediction);
            return riskPrediction;

        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN; // Return NaN to indicate an error
        }
    }
}
