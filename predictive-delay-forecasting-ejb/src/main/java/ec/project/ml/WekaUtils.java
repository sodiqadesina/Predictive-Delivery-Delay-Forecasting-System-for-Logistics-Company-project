package ec.project.ml;

import nz.ac.waikato.cms.weka.classifiers.Classifier;
import nz.ac.waikato.cms.weka.classifiers.Evaluation;
import nz.ac.waikato.cms.weka.core.Instances;
import nz.ac.waikato.cms.weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;

public class WekaUtils {

    public static Instances loadDataset(String path) {
        try {
            DataSource source = new DataSource(path);
            Instances data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1); // Set the last attribute as the class
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double evaluateModel(Classifier model, Instances data) {
        try {
            Evaluation evaluation = new Evaluation(data);
            evaluation.crossValidateModel(model, data, 10, new Random(1)); // 10-fold cross-validation
            return evaluation.pctCorrect(); // Percentage of correctly classified instances
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }
}
