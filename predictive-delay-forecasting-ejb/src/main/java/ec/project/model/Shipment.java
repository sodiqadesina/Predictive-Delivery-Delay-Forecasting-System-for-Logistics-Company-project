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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDaysForShippingReal() {
        return daysForShippingReal;
    }

    public void setDaysForShippingReal(int daysForShippingReal) {
        this.daysForShippingReal = daysForShippingReal;
    }

    public int getDaysForShippingScheduled() {
        return daysForShippingScheduled;
    }

    public void setDaysForShippingScheduled(int daysForShippingScheduled) {
        this.daysForShippingScheduled = daysForShippingScheduled;
    }

    public int getLateDeliveryRisk() {
        return lateDeliveryRisk;
    }

    public void setLateDeliveryRisk(int lateDeliveryRisk) {
        this.lateDeliveryRisk = lateDeliveryRisk;
    }

    public double getBenefitPerOrder() {
        return benefitPerOrder;
    }

    public void setBenefitPerOrder(double benefitPerOrder) {
        this.benefitPerOrder = benefitPerOrder;
    }

    public double getSalesPerCustomer() {
        return salesPerCustomer;
    }

    public void setSalesPerCustomer(double salesPerCustomer) {
        this.salesPerCustomer = salesPerCustomer;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderRegion() {
        return orderRegion;
    }

    public void setOrderRegion(String orderRegion) {
        this.orderRegion = orderRegion;
    }

    public String getOrderCountry() {
        return orderCountry;
    }

    public void setOrderCountry(String orderCountry) {
        this.orderCountry = orderCountry;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(int orderDay) {
        this.orderDay = orderDay;
    }

    public int getOrderMonth() {
        return orderMonth;
    }

    public void setOrderMonth(int orderMonth) {
        this.orderMonth = orderMonth;
    }

    public int getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(int orderYear) {
        this.orderYear = orderYear;
    }

    public int getShippingDay() {
        return shippingDay;
    }

    public void setShippingDay(int shippingDay) {
        this.shippingDay = shippingDay;
    }

    public int getShippingMonth() {
        return shippingMonth;
    }

    public void setShippingMonth(int shippingMonth) {
        this.shippingMonth = shippingMonth;
    }

    public int getShippingYear() {
        return shippingYear;
    }

    public void setShippingYear(int shippingYear) {
        this.shippingYear = shippingYear;
    }
}
