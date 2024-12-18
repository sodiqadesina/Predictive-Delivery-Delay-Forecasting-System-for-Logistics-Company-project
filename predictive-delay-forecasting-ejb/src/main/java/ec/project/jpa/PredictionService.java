package ec.project.jpa;

import ec.project.dao.ModelDAO;
import ec.project.ml.ModelBuilder;
import ec.project.ml.ModelPredictor;
import ec.project.model.ModelMetadata;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;

@Stateless
public class PredictionService {

    @EJB
    private ModelDAO modelDAO;

    @EJB
    private ModelBuilder modelBuilder;

    @Inject
    private ModelPredictor modelPredictor;

    private static final String MODEL_DIR_PATH = "C:/enterprise/tmp/model/project";
    private static final Logger logger = Logger.getLogger(PredictionService.class.getName());

    /**
     * Add a new model to the database.
     */
    public void addModel(ModelMetadata model) {
        try {
            modelDAO.addModel(model);
            logger.info("Model added successfully: " + model.getName());
        } catch (Exception e) {
            logger.severe("Error adding model: " + e.getMessage());
            throw e; // Re-throw exception to inform callers
        }
    }

    /**
     * Retrieve a model by name.
     */
    public ModelMetadata getModelByName(String modelName) {
        try {
            return modelDAO.getModelByName(modelName);
        } catch (Exception e) {
            logger.severe("Error retrieving model by name: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve all models from the database.
     */
    public List<ModelMetadata> getAllModels() {
        try {
            return modelDAO.getAllModels();
        } catch (Exception e) {
            logger.severe("Error retrieving all models: " + e.getMessage());
            return null;
        }
    }

    /**
     * Delete a model by name.
     */
    public void deleteModel(String modelName) {
        try {
            modelDAO.deleteModel(modelName);
            logger.info("Model deleted successfully: " + modelName);
        } catch (Exception e) {
            logger.severe("Error deleting model: " + e.getMessage());
            throw e; // Re-throw to handle at a higher level
        }
    }

    /**
     * Train and save models using the ModelBuilder.
     */
    public void trainModel() {
        try {
            modelBuilder.trainAndSaveModels();
        } catch (Exception e) {
            logger.severe("Error during model training: " + e.getMessage());
            throw e; // Propagate exception
        }
    }

    /**
     * Deploy a specific model as the active prediction model.
     */
    public void deployModel(String modelName) {
        try {
            List<ModelMetadata> allModels = modelDAO.getAllModels();
            if (allModels == null || allModels.isEmpty()) {
                throw new IllegalStateException("No models available to deploy.");
            }

            for (ModelMetadata model : allModels) {
                model.setDeployed(model.getName().equals(modelName));
                modelDAO.updateModel(model);
            }
            logger.info("Model deployed successfully: " + modelName);
        } catch (Exception e) {
            logger.severe("Error deploying model: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieve the currently deployed model.
     */
    public ModelMetadata getDeployedModel() {
        try {
            return modelDAO.getDeployedModel();
        } catch (Exception e) {
            logger.severe("Error retrieving deployed model: " + e.getMessage());
            return null;
        }
    }

    /**
     * Predict late delivery risk using custom shipment features and the specified model.
     */
    public double predictLateDeliveryRisk(String type, double daysForShippingReal, double daysForShippingScheduled,
                                          double benefitPerOrder, double salesPerCustomer, double latitude, double longitude,
                                          String shippingMode, String orderStatus, String orderRegion, String orderCountry,
                                          String orderCity, String market, String deliveryStatus, int orderDay, int orderMonth,
                                          int orderYear, int shippingDay, int shippingMonth, int shippingYear) {
        try {
            String modelPath = MODEL_DIR_PATH + "/Random_Forest_Model.bin";
            return modelPredictor.predictLateDeliveryRisk(modelPath, type, daysForShippingReal, daysForShippingScheduled,
                                                          benefitPerOrder, salesPerCustomer, latitude, longitude, shippingMode,
                                                          orderStatus, orderRegion, orderCountry, orderCity, market, 
                                                          deliveryStatus, orderDay, orderMonth, orderYear, shippingDay,
                                                          shippingMonth, shippingYear);
        } catch (Exception e) {
            logger.severe("Error predicting late delivery risk: " + e.getMessage());
            return Double.NaN; // Return NaN to indicate failure
        }
    }

    /**
     * Initialize models from the directory and store them in the database.
     */
    public void initializeModels() {
        logger.info("Initializing models from directory: " + MODEL_DIR_PATH);
        File modelDir = new File(MODEL_DIR_PATH);

        if (modelDir.exists() && modelDir.isDirectory()) {
            File[] modelFiles = modelDir.listFiles();

            if (modelFiles != null) {
                for (File modelFile : modelFiles) {
                    try {
                        String fileName = modelFile.getName();

                        // Skip metrics.txt
                        if ("metrics.txt".equalsIgnoreCase(fileName)) {
                            logger.info("Skipping metrics file: " + fileName);
                            continue;
                        }

                        // Check if model already exists in the database
                        if (modelDAO.getModelByName(fileName) != null) {
                            logger.info("Model already exists in database: " + fileName);
                            continue; // Skip processing for existing models
                        }

                        // Read model file content into a Blob
                        byte[] fileContent = Files.readAllBytes(modelFile.toPath());
                        Blob modelBlob = new SerialBlob(fileContent);

                        // Read performance metrics from metrics.txt if available
                        File metricsFile = new File(modelDir, "metrics.txt");
                        String performanceMetrics = metricsFile.exists() 
                                ? new String(Files.readAllBytes(metricsFile.toPath())).trim()
                                : "Default Metrics";

                        // Create and save ModelMetadata
                        ModelMetadata model = new ModelMetadata();
                        model.setName(fileName);
                        model.setModelBlob(modelBlob);
                        model.setPerformanceMetrics(performanceMetrics);
                        model.setDeployed(false);

                        modelDAO.addModel(model);
                        logger.info("Successfully added model: " + fileName);
                    } catch (IOException | SQLException e) {
                        logger.severe("Error processing model file: " + modelFile.getName() + " - " + e.getMessage());
                    }
                }
            } else {
                logger.warning("No model files found in directory.");
            }
        } else {
            logger.warning("Model directory does not exist or is not a directory: " + MODEL_DIR_PATH);
        }
    }
}
