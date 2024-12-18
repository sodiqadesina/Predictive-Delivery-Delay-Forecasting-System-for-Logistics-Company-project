package ec.project.dao;

import ec.project.model.ModelMetadata;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
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
            TypedQuery<ModelMetadata> query = em.createQuery(
                "SELECT m FROM ModelMetadata m WHERE m.name = :modelName", ModelMetadata.class);
            query.setParameter("modelName", modelName);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ModelMetadata> getAllModels() {
        try {
            return em.createQuery("SELECT m FROM ModelMetadata m", ModelMetadata.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteModel(String modelName) {
        try {
            ModelMetadata model = getModelByName(modelName);
            if (model != null) {
                em.remove(em.contains(model) ? model : em.merge(model));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateModel(ModelMetadata model) {
        try {
            em.merge(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModelMetadata getDeployedModel() {
        try {
            TypedQuery<ModelMetadata> query = em.createQuery(
                "SELECT m FROM ModelMetadata m WHERE m.deployed = true", ModelMetadata.class);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
