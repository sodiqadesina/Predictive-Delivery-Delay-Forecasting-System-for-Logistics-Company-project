package ec.project.jpa;

import ec.project.dao.ModelDAO;
import ec.project.ml.ModelBuilder;
import ec.project.ml.ModelPredictor;
import ec.project.model.ModelMetadata;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PredictionService {

    @Inject
    private ModelDAO modelDAO;

    @Inject
    private ModelBuilder modelBuilder;

    @Inject
    private ModelPredictor modelPredictor;

    private static final String MODEL_FILE_PATH = "C:/enterprise/tmp/model/project/Random_Forest_Model.bin";

    /**
     * Add a new model to the database.
     * 
     * @param model Model metadata to add
     */
    public void addModel(ModelMetadata model) {
        modelDAO.addModel(model);
    }

    /**
     * Retrieve a model by name.
     * 
     * @param modelName Name of the model to retrieve
     * @return ModelMetadata object
     */
    public ModelMetadata getModelByName(String modelName) {
        return modelDAO.getModelByName(modelName);
    }

    /**
     * Retrieve all models from the database.
     * 
     * @return List of all ModelMetadata objects
     */
    public List<ModelMetadata> getAllModels() {
        return modelDAO.getAllModels();
    }

    /**
     * Delete a model by name.
     * 
     * @param modelName Name of the model to delete
     */
    public void deleteModel(String modelName) {
        modelDAO.deleteModel(modelName);
    }

    /**
     * Train and save models using the ModelBuilder.
     */
    public void trainModel() {
        modelBuilder.trainAndSaveModels();
    }

    /**
     * Deploy a specific model as the active prediction model.
     * 
     * @param modelName Name of the model to deploy
     */
    public void deployModel(String modelName) {
        List<ModelMetadata> allModels = modelDAO.getAllModels();
        for (ModelMetadata model : allModels) {
            if (model.getName().equals(modelName)) {
                model.setDeployed(true);
            } else {
                model.setDeployed(false);
            }
            modelDAO.updateModel(model);
        }
    }

    /**
     * Retrieve the currently deployed model.
     * 
     * @return Deployed ModelMetadata object
     */
    public ModelMetadata getDeployedModel() {
        return modelDAO.getAllModels().stream()
                .filter(ModelMetadata::isDeployed)
                .findFirst()
                .orElse(null);
    }

    /**
     * Predict late delivery risk using custom shipment features and the specified model.
     * 
     * @param type                Shipment type
     * @param daysForShippingReal Real days for shipping
     * @param daysForShippingScheduled Scheduled days for shipping
     * @param benefitPerOrder     Benefit per order
     * @param salesPerCustomer    Sales per customer
     * @param latitude            Latitude of the shipment
     * @param longitude           Longitude of the shipment
     * @param shippingMode        Shipping mode
     * @param orderStatus         Order status
     * @param orderRegion         Order region
     * @param orderCountry        Order country
     * @param orderCity           Order city
     * @param market              Market type
     * @param deliveryStatus      Delivery status
     * @param orderDay            Day of the order
     * @param orderMonth          Month of the order
     * @param orderYear           Year of the order
     * @param shippingDay         Day of the shipment
     * @param shippingMonth       Month of the shipment
     * @param shippingYear        Year of the shipment
     * @return Predicted late delivery risk score
     */
    public double predictLateDeliveryRisk(String type, double daysForShippingReal, double daysForShippingScheduled,
                                          double benefitPerOrder, double salesPerCustomer, double latitude, double longitude,
                                          String shippingMode, String orderStatus, String orderRegion, String orderCountry,
                                          String orderCity, String market, String deliveryStatus, int orderDay, int orderMonth,
                                          int orderYear, int shippingDay, int shippingMonth, int shippingYear) {
        return modelPredictor.predictLateDeliveryRisk(MODEL_FILE_PATH, type, daysForShippingReal, daysForShippingScheduled,
                benefitPerOrder, salesPerCustomer, latitude, longitude, shippingMode, orderStatus, orderRegion, orderCountry,
                orderCity, market, deliveryStatus, orderDay, orderMonth, orderYear, shippingDay, shippingMonth, shippingYear);
    }
}
