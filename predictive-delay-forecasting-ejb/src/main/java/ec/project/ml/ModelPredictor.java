package ec.project.ml;

import ec.project.dao.ModelDAO;
import ec.project.model.ModelMetadata;
import nz.ac.waikato.cms.weka.classifiers.Classifier;
import nz.ac.waikato.cms.weka.core.Instance;
import nz.ac.waikato.cms.weka.core.Instances;
import nz.ac.waikato.cms.weka.core.converters.ConverterUtils.DataSource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

@Stateless
public class ModelPredictor {

    @Inject
    private ModelDAO modelDAO;

    public double predict(String modelName, String inputDataPath) {
        try {
            // Get model metadata
            ModelMetadata metadata = modelDAO.getModelByName(modelName);
            if (metadata == null) {
                throw new RuntimeException("Model not found: " + modelName);
            }

            // Load the model from file
            Classifier model;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(metadata.getPath()))) {
                model = (Classifier) in.readObject();
            }

            // Load input data
            DataSource source = new DataSource(inputDataPath);
            Instances inputData = source.getDataSet();
            inputData.setClassIndex(inputData.numAttributes() - 1);

            // Perform prediction for the first instance
            Instance instance = inputData.firstInstance();
            return model.classifyInstance(instance);

        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN; // Indicate an error
        }
    }
}
