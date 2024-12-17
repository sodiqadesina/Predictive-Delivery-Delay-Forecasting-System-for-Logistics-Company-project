package ec.project.web.dto;

public class ShipmentDTO {
    private String origin;
    private String destination;
    private double benefit;
    private double sales;

    public ShipmentDTO() {}

    public ShipmentDTO(String origin, String destination, double benefit, double sales) {
        this.origin = origin;
        this.destination = destination;
        this.benefit = benefit;
        this.sales = sales;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBenefit() {
        return benefit;
    }

    public void setBenefit(double benefit) {
        this.benefit = benefit;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }
}
