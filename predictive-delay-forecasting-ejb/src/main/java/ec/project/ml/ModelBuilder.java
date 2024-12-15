//package ec.project.ml;
//
//import ec.project.dao.ModelDAO;
//import ec.project.model.ModelMetadata;
//import nz.ac.waikato.cms.weka.classifiers.Classifier;
//import nz.ac.waikato.cms.weka.core.Instances;
//import nz.ac.waikato.cms.weka.core.converters.ConverterUtils.DataSource;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import java.io.File;
//import java.io.ObjectOutputStream;
//import java.io.FileOutputStream;
//
//@Stateless
//public class ModelBuilder {
//
//    @Inject
//    private ModelDAO modelDAO;
//
//    public void trainModel(String modelName, String trainingDataPath) {
//        try {
//            // Load training data
//            DataSource source = new DataSource(trainingDataPath);
//            Instances trainingData = source.getDataSet();
//            trainingData.setClassIndex(trainingData.numAttributes() - 1); // Set the class attribute
//
//            // Train the model (Example: Linear Regression)
//            Classifier model = new nz.ac.waikato.cms.weka.classifiers.functions.LinearRegression();
//            model.buildClassifier(trainingData);
//
//            // Save the model to a file
//            String modelPath = "models/" + modelName + ".bin";
//            File modelFile = new File(modelPath);
//            modelFile.getParentFile().mkdirs();
//            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(modelFile))) {
//                out.writeObject(model);
//            }
//
//            // Save model metadata to the database
//            ModelMetadata metadata = new ModelMetadata();
//            metadata.setName(modelName);
//            metadata.setPath(modelPath);
//            metadata.setType("Linear Regression");
//            metadata.setCreatedBy("system"); // You can replace this with the logged-in user
//            metadata.setCreatedAt(java.time.LocalDateTime.now().toString());
//            modelDAO.addModel(metadata);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
