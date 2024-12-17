package ec.project.ml;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.REPTree;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.ejb.Stateless;

@Stateless
public class ModelBuilder {

    public void trainAndSaveModels() {
    	 String datasetURL = "C:/enterprise/workspace/project/Ec-project/predictive-delay-forecasting-ejb/src/main/resources/data/shipment_data_clean.arff";
         String modelDirectoryPath = "C:/enterprise/tmp/model/project";
         int seed = 42; // Fixed seed for reproducibility

         try {
             // Load dataset from URL
             Instances fullData = DataSource.read(datasetURL);
             fullData.setClassIndex(fullData.attribute("late_delivery_risk").index());

             // Randomize the dataset using a seed
             Randomize randomize = new Randomize();
             randomize.setRandomSeed(seed); 
             randomize.setInputFormat(fullData);
             Instances randomizedData = Filter.useFilter(fullData, randomize);

             // Training Data (70%)
             RemovePercentage filter = new RemovePercentage();
             filter.setPercentage(70); // Remove 30% for training (keep 70%)
             filter.setInvertSelection(true);
             filter.setInputFormat(randomizedData);
             Instances trainingData = Filter.useFilter(randomizedData, filter);

             filter.setInvertSelection(false);
             filter.setInputFormat(randomizedData);
             Instances remainingData = Filter.useFilter(randomizedData, filter);

             // Step 3: Validation Data (15% of the total data, i.e., 50% of remaining)
             RemovePercentage secondFilter = new RemovePercentage();
             secondFilter.setPercentage(50); // Remove 50%, keep 50% of remaining (15% of total)
             secondFilter.setInvertSelection(true);
             secondFilter.setInputFormat(remainingData);
             Instances validationData = Filter.useFilter(remainingData, secondFilter);

             // Step 4: Testing Data (Remaining 15%)
             secondFilter.setInvertSelection(false);
             secondFilter.setInputFormat(remainingData);
             Instances testingData = Filter.useFilter(remainingData, secondFilter);

            System.out.println("Dataset sizes:");
            System.out.println("Training Set: " + trainingData.numInstances());
            System.out.println("Validation Set: " + validationData.numInstances());
            System.out.println("Testing Set: " + testingData.numInstances());

            // Ensure the model directory exists
            File modelDirectory = new File(modelDirectoryPath);
            if (!modelDirectory.exists()) {
                modelDirectory.mkdirs();
            }

            // Initialize classifiers
            Classifier[] classifiers = {
                new RandomForest(),  // Random Forest
                new REPTree()        // REPTree
            };
            String[] algorithmNames = { "Random Forest", "REPTree" };

            for (int i = 0; i < classifiers.length; i++) {
                Classifier classifier = classifiers[i];
                String algorithmName = algorithmNames[i];

                // Configure specific settings for classifiers
                if (classifier instanceof RandomForest) {
                    ((RandomForest) classifier).setNumIterations(100);
                }

                // Cross-validation (10-fold)
                Evaluation crossValidationEval = new Evaluation(trainingData);
                crossValidationEval.crossValidateModel(classifier, trainingData, 10, new java.util.Random(seed));

                System.out.println("\nCross-Validation Results for " + algorithmName + ":");
                System.out.println(crossValidationEval.toSummaryString());

                // Train the model
                classifier.buildClassifier(trainingData);

                // Evaluate the model on the testing set
                Evaluation testEval = new Evaluation(trainingData);
                testEval.evaluateModel(classifier, testingData);

                // Output evaluation metrics
                System.out.println("\nTesting Set Results for " + algorithmName + ":");
                System.out.println(testEval.toSummaryString());

                // Custom accuracy calculation using binary threshold
                int correctPredictions = 0;
                System.out.println("Predictions vs Actual Class Values (First 20 Instances):");
                for (int j = 0; j < testingData.numInstances(); j++) {
                    Instance instance = testingData.instance(j);
                    double prediction = classifier.classifyInstance(instance);
                    double binaryPrediction = prediction >= 0.5 ? 1.0 : 0.0;
                    double actualValue = instance.classValue();

                    if (j < 20) { // Print only first 20 instances
                        System.out.printf("Instance %d: Predicted = %.2f, Binary = %.0f, Actual = %.0f%n",
                                j + 1, prediction, binaryPrediction, actualValue);
                    }

                    if (binaryPrediction == actualValue) {
                        correctPredictions++;
                    }
                }
                double customAccuracy = (double) correctPredictions / testingData.numInstances() * 100;

                // Serialize the model to a binary file
                String modelFileName = algorithmName.replace(" ", "_") + "_Model.bin";
                File modelFile = new File(modelDirectory, modelFileName);

                try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(modelFile))) {
                    fileOut.writeObject(classifier);
                }

                // Output final results
                System.out.println("\nModel trained and saved to file: " + modelFile.getAbsolutePath());
                System.out.println("Custom Accuracy (Binary Threshold): " + String.format("%.2f", customAccuracy) + "%");
            }

        } catch (Exception e) {
            System.err.println("Error training or saving models: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
