package ec.project.dao;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.project.model.ModelMetadata;
import java.util.List;

@Stateful
public class ModelDAOImpl implements ModelDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public void addModel(ModelMetadata model) {
        em.persist(model);
    }

    @Override
    public ModelMetadata getModelByName(String modelName) {
        try {
            return em.createQuery(
                "SELECT m FROM ModelMetadata m WHERE m.name = :modelName", ModelMetadata.class)
                .setParameter("modelName", modelName)
                .getSingleResult();
        } catch (Exception e) {
            return null;  // Handle exception, e.g., model not found
        }
    }

    @Override
    public List<ModelMetadata> getAllModels() {
        return em.createQuery("SELECT m FROM ModelMetadata m", ModelMetadata.class).getResultList();
    }

    @Override
    public void deleteModel(String modelName) {
        try {
            ModelMetadata model = getModelByName(modelName);
            if (model != null) {
                em.remove(model);
            }
        } catch (Exception e) {
            // Handle exception, e.g., model not found
        }
    }
}
