package ec.project;

import org.junit.Test;

import ec.project.ml.ModelBuilder;
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


            // Act
            modelBuilder.trainAndSaveModels();

          

            System.out.println("Model training and saving test passed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Model training and saving test failed: " + e.getMessage());
        }
    }
}
