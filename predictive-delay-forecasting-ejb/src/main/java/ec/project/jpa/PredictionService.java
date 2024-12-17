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

    public void addModel(ModelMetadata model) {
        modelDAO.addModel(model);
    }

    public ModelMetadata getModelByName(String modelName) {
        return modelDAO.getModelByName(modelName);
    }

    public List<ModelMetadata> getAllModels() {
        return modelDAO.getAllModels();
    }

    public void deleteModel(String modelName) {
        modelDAO.deleteModel(modelName);
    }

    public void trainModel() {
        modelBuilder.trainAndSaveModels();
    }

    public double predictLateDeliveryRisk(String modelName, Long shipmentId) {
        return modelPredictor.predictLateDeliveryRisk(modelName, shipmentId);
    }
}
