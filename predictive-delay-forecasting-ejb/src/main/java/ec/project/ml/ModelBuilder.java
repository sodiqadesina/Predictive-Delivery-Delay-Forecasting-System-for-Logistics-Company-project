package ec.project.ml;

import ec.project.dao.ModelDAO;
import ec.project.model.ModelMetadata;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;

@Stateless
public class ModelBuilder {

    @Inject
    private ModelDAO modelDAO;

    public void trainAndSaveModels( ) {
    	String trainingDataPath = "C:/enterprise/workspace/Ec-project/data/train_shipment_data.arff";
    	String testDataPath = "C:/enterprise/workspace/project/Project/Ec-project/data/test_shipment_data.arf";
        try {
            // Load training and test data
            Instances trainingData = DataSource.read(trainingDataPath);
            trainingData.setClassIndex(trainingData.numAttributes() - 1);

            Instances testData = DataSource.read(testDataPath);
            testData.setClassIndex(testData.numAttributes() - 1);

            // Initialize classifiers
            Classifier[] classifiers = {
                new J48(),           // Decision Tree
                new RandomForest()   // Random Forest
            };

            String[] algorithmNames = { "Decision Tree", "Random Forest" };

            for (int i = 0; i < classifiers.length; i++) {
                Classifier classifier = classifiers[i];
                String algorithmName = algorithmNames[i];

                // Configure specific settings for each classifier
                if (classifier instanceof J48) {
                    ((J48) classifier).setUnpruned(true);
                } else if (classifier instanceof RandomForest) {
                    ((RandomForest) classifier).setNumIterations(100);
                }

                // Train the model
                classifier.buildClassifier(trainingData);

                // Evaluate the model
                Evaluation evaluation = new Evaluation(trainingData);
                evaluation.evaluateModel(classifier, testData);

                // Collect performance metrics
                String summary = evaluation.toSummaryString();
                double accuracy = evaluation.pctCorrect();

                // Serialize the model into a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeObject(classifier);
                }
                byte[] modelBytes = byteArrayOutputStream.toByteArray();

                // Convert byte array to BLOB
                Blob modelBlob = new SerialBlob(modelBytes);

                
               
                
                
                // Save metadata and model BLOB to the database
                ModelMetadata metadata = new ModelMetadata();
                metadata.setName(algorithmName + "_Model");
                metadata.setModelBlob(modelBlob);
                metadata.setPerformanceMetrics("Accuracy: " + accuracy + "%\n" + summary);
                modelDAO.addModel(metadata);
                
             
                //should be deleted aftyer sql connections are made 
               // String modelDirectoryPath = "C:/enterprise/tmp/model/project";

                
                    // Create the model directory if it doesn't exist
                   // File modelDirectory = new File(modelDirectoryPath);
                    //if (!modelDirectory.exists()) {
                    //    modelDirectory.mkdirs();
                   // }
                
                 // Save the model to a binary file
                  //  String modelFileName = algorithmName.replace(" ", "_") + "_Model.bin";
                  //  File modelFile = new File(modelDirectory, modelFileName);
                    
                   // try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(modelFile))) {
                   //     out.writeObject(classifier);
                   // }
                    
                    //temp code ends here 
                
                
                System.out.println("Model trained, tested, and saved to database: " + algorithmName);
            }

        } catch (Exception e) {
            System.err.println("Error training or saving models: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
