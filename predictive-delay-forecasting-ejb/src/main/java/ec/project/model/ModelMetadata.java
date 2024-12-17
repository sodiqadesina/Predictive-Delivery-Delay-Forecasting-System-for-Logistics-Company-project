package ec.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;

@Entity
public class ModelMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Changed to Long

    private String name; // Separate field for model name
    private Blob modelBlob;
    private String performanceMetrics;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

