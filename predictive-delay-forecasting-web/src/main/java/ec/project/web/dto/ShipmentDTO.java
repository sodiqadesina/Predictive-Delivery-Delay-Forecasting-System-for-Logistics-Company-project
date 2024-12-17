package ec.project.web.dto;

public class ShipmentDTO {
    private double latitude;
    private double longitude;
    private double benefitPerOrder;
    private double salesPerCustomer;

    public ShipmentDTO() {}

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getBenefitPerOrder() { return benefitPerOrder; }
    public void setBenefitPerOrder(double benefitPerOrder) { this.benefitPerOrder = benefitPerOrder; }

    public double getSalesPerCustomer() { return salesPerCustomer; }
    public void setSalesPerCustomer(double salesPerCustomer) { this.salesPerCustomer = salesPerCustomer; }
}
