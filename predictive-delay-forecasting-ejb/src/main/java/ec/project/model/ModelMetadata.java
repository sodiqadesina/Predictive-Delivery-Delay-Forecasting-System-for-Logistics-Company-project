package ec.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModelMetadata {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private Blob modelBlob;
    private String performanceMetrics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public Blob getModelBlob() {
        return modelBlob;
    }

    public void setModelBlob(Blob modelBlob) {
        this.modelBlob = modelBlob;
    }

    public String getPerformanceMetrics() {
        return performanceMetrics;
    }

    public void setPerformanceMetrics(String performanceMetrics) {
        this.performanceMetrics = performanceMetrics;
    }
}
