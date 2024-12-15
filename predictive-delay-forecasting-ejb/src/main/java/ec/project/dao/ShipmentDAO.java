package ec.project.dao;

import ec.project.model.Shipment;
import java.util.List;

public interface ShipmentDAO {
    void addShipment(Shipment shipment);
    Shipment getShipmentById(Long id);
    List<Shipment> getAllShipments();
    List<Shipment> getShipmentsByRegion(String region);
    List<Shipment> getLateDeliveries();
    void updateShipment(Shipment shipment);
    void deleteShipment(Long id);
}
