package ec.project.dao;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.project.model.Shipment;
import java.util.List;

@Stateful
public class ShipmentDAOImpl implements ShipmentDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public void addShipment(Shipment shipment) {
        em.persist(shipment);
    }

    @Override
    public Shipment getShipmentById(Long id) {
        return em.find(Shipment.class, id);
    }

    @Override
    public List<Shipment> getAllShipments() {
        return em.createQuery("SELECT s FROM Shipment s", Shipment.class).getResultList();
    }

    @Override
    public List<Shipment> getShipmentsByRegion(String region) {
        return em.createQuery(
                "SELECT s FROM Shipment s WHERE s.orderRegion = :region", Shipment.class)
                .setParameter("region", region)
                .getResultList();
    }

    @Override
    public List<Shipment> getLateDeliveries() {
        return em.createQuery(
                "SELECT s FROM Shipment s WHERE s.lateDeliveryRisk > 0", Shipment.class)
                .getResultList();
    }

    @Override
    public void updateShipment(Shipment shipment) {
        em.merge(shipment);
    }

    @Override
    public void deleteShipment(Long id) {
        Shipment shipment = getShipmentById(id);
        if (shipment != null) {
            em.remove(shipment);
        }
    }
}
