package ec.project.dao;

import ec.project.model.ModelMetadata;
import java.util.List;

public interface ModelDAO {
    void addModel(ModelMetadata model);
    ModelMetadata getModelByName(String modelName);
    List<ModelMetadata> getAllModels();
    void deleteModel(String modelName);
}
