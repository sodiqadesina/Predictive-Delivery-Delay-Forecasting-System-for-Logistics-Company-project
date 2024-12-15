package ec.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private int daysForShippingReal;
    private int daysForShippingScheduled;
    private int lateDeliveryRisk;
    private double benefitPerOrder;
    private double salesPerCustomer;
    private double latitude;
    private double longitude;
    private String shippingMode;
    private String orderStatus;
    private String orderRegion;
    private String orderCountry;
    private String orderCity;
    private String market;
    private String deliveryStatus;

    private int orderDay;
    private int orderMonth;
    private int orderYear;

    private int shippingDay;
    private int shippingMonth;
    private int shippingYear;

    // Getters and Setters
    // (Omitted for brevity. Please refer to the previous example for Shipment entity getters and setters.)
}
