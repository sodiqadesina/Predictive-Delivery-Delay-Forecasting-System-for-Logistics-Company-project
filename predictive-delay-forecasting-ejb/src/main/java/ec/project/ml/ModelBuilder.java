package ec.project.ml;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.REPTree;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

import javax.ejb.Stateless;

@Stateless
public class ModelBuilder {

    public void trainAndSaveModels() {
        String trainingDataPath = "src/main/resources/data/train_shipment_data.arff";
        String testingDataPath = "src/main/resources/data/test_shipment_data.arff";
        String modelDirectoryPath = "C:/enterprise/tmp/model/project";
        String metricsFilePath = "C:/enterprise/tmp/model/project/metrics.txt";
        int seed = 42; // Fixed seed for reproducibility

        try {
            // Load preprocessed datasets
            Instances trainingData = DataSource.read(trainingDataPath);
            trainingData.setClassIndex(trainingData.attribute("late_delivery_risk").index());

            Instances testingData = DataSource.read(testingDataPath);
            testingData.setClassIndex(testingData.attribute("late_delivery_risk").index());

            // Print dataset sizes
            System.out.println("Dataset sizes:");
            System.out.println("Training Set: " + trainingData.numInstances());
            System.out.println("Testing Set: " + testingData.numInstances());

            // Ensure the model directory exists
            File modelDirectory = new File(modelDirectoryPath);
            if (!modelDirectory.exists()) {
                modelDirectory.mkdirs();
            }

            File metricsFile = new File(metricsFilePath);
            try (FileWriter metricsWriter = new FileWriter(metricsFile)) {

                // Initialize classifiers
                Classifier[] classifiers = {
                    //new RandomForest(),  // Random Forest
                    new REPTree()        // REPTree
                };
                //String[] algorithmNames = { "Random Forest", "REPTree" };
                String[] algorithmNames = {  "REPTree" };

                for (int i = 0; i < classifiers.length; i++) {
                    Classifier classifier = classifiers[i];
                    String algorithmName = algorithmNames[i];

                    // Configure specific settings for classifiers
                    if (classifier instanceof RandomForest) {
                    	RandomForest rf = (RandomForest) classifier;
                        rf.setNumIterations(30); 
                        rf.setMaxDepth(7);
                    }

                    // Train the model
                    classifier.buildClassifier(trainingData);

                    // Evaluate the model on the testing set
                    Evaluation testEval = new Evaluation(trainingData);
                    testEval.evaluateModel(classifier, testingData);

                    // Collect performance metrics
                    double accuracy = testEval.pctCorrect();
                    double kappa = testEval.kappa();
                    double meanAbsoluteError = testEval.meanAbsoluteError();
                    double rootMeanSquaredError = testEval.rootMeanSquaredError();
                    double relativeAbsoluteError = testEval.relativeAbsoluteError();
                    double rootRelativeSquaredError = testEval.rootRelativeSquaredError();
                    int totalInstances = testingData.numInstances();

                    // Write metrics to text file
                    metricsWriter.write(String.format("Model: %s\n", algorithmName));
                    metricsWriter.write(String.format("Accuracy: %.2f%%\n", accuracy));
                    metricsWriter.write(String.format("Kappa: %.2f\n", kappa));
                    metricsWriter.write(String.format("Mean Absolute Error: %.4f\n", meanAbsoluteError));
                    metricsWriter.write(String.format("Root Mean Squared Error: %.4f\n", rootMeanSquaredError));
                    metricsWriter.write(String.format("Relative Absolute Error: %.2f%%\n", relativeAbsoluteError));
                    metricsWriter.write(String.format("Root Relative Squared Error: %.2f%%\n", rootRelativeSquaredError));
                    metricsWriter.write(String.format("Total Instances: %d\n", totalInstances));
                    metricsWriter.write("--------------------------\n");

                    System.out.println("\nTesting Set Results for " + algorithmName + ":");
                    System.out.printf("Accuracy: %.2f%%\n", accuracy);

                    // Serialize the model to a binary file
                    String modelFileName = algorithmName.replace(" ", "_") + "_Model.bin";
                    File modelFile = new File(modelDirectory, modelFileName);

                    try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(modelFile))) {
                        fileOut.writeObject(classifier);
                    }

                    // Output final results
                    System.out.println("Model trained and saved to file: " + modelFile.getAbsolutePath());
                }
            }

            System.out.println("Metrics saved to file: " + metricsFilePath);

        } catch (Exception e) {
            System.err.println("Error training or saving models: " + e.getMessage());
            e.printStackTrace();
        }
    }
}