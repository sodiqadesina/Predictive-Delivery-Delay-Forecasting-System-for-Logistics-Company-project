package ec.project.jpa;

import ec.project.dao.ShipmentDAO;
import ec.project.model.Shipment;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ShipmentService {

    @Inject
    private ShipmentDAO shipmentDAO;

    public void addShipment(Shipment shipment) {
        shipmentDAO.addShipment(shipment);
    }

    public Shipment getShipmentById(Long id) {
        return shipmentDAO.getShipmentById(id);
    }

    public List<Shipment> getAllShipments() {
        return shipmentDAO.getAllShipments();
    }

    public List<Shipment> getShipmentsByRegion(String region) {
        return shipmentDAO.getShipmentsByRegion(region);
    }

    public List<Shipment> getLateDeliveries() {
        return shipmentDAO.getLateDeliveries();
    }

    public void updateShipment(Shipment shipment) {
        shipmentDAO.updateShipment(shipment);
    }

    public void deleteShipment(Long id) {
        shipmentDAO.deleteShipment(id);
    }
}
