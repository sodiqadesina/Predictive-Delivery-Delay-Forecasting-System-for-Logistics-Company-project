package ec.project.ml;

import org.junit.Test;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ModelBuilderTest {

    @Test
    public void testTrainAndSaveModels() {
        try {
            // Arrange
            ModelBuilder modelBuilder = new ModelBuilder();
//        	String trainingDataPath = "C:/enterprise/workspace/Ec-project/data/train_shipment_data.arff";
//        	String testDataPath = "C:/enterprise/workspace/project/Project/Ec-project/data/test_shipment_data.arf";

            // Act
            modelBuilder.trainAndSaveModels();

            // Assert
            //Instances trainingData = DataSource.read(trainingDataPath);
            //assertNotNull("Training data should not be null", trainingData);

            System.out.println("Model training and saving test passed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Model training and saving test failed: " + e.getMessage());
        }
    }
}
