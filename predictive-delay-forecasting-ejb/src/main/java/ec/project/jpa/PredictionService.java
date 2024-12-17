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

    /**
     * Add a new model to the database.
     */
    public void addModel(ModelMetadata model) {
        modelDAO.addModel(model);
    }

    /**
     * Retrieve a model by name.
     */
    public ModelMetadata getModelByName(String modelName) {
        return modelDAO.getModelByName(modelName);
    }

    /**
     * Retrieve all models from the database.
     */
    public List<ModelMetadata> getAllModels() {
        return modelDAO.getAllModels();
    }

    /**
     * Delete a model by name.
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
     * Predict late delivery risk for a given shipment ID.
     */
    public double predictLateDeliveryRisk(String modelName, Long shipmentId) {
        return modelPredictor.predictLateDeliveryRisk(modelName, shipmentId);
    }

    /**
     * Deploy a selected model as the active model for predictions.
     */
    public void deployModel(String modelName) {
        // Fetch all models
        List<ModelMetadata> allModels = modelDAO.getAllModels();

        // Iterate through all models and mark the selected one as deployed
        for (ModelMetadata model : allModels) {
            if (model.getName().equals(modelName)) {
                model.setDeployed(true);  // Mark selected model as deployed
            } else {
                model.setDeployed(false); // Unmark others
            }
            modelDAO.updateModel(model);
        }
    }

    /**
     * Get the currently deployed model.
     */
    public ModelMetadata getDeployedModel() {
        return modelDAO.getAllModels().stream()
                .filter(ModelMetadata::isDeployed)
                .findFirst()
                .orElse(null);
    }
}
