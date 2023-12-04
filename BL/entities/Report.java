package ProyectoPOO.Main.BL.entities;
import java.awt.*;
import java.util.List;

public class Report {
        private int sales;
        private String computerDetails;
        private int rating;
        private double price;
        private List<Component> componentsSold;

    public Report(int sales, String computerDetails, int rating, double price, List<Component> componentsSold) {
        this.sales = sales;
        this.computerDetails = computerDetails;
        this.rating = rating;
        this.price = price;
        this.componentsSold = componentsSold;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getComputerDetails() {
        return computerDetails;
    }

    public void setComputerDetails(String computerDetails) {
        this.computerDetails = computerDetails;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Component> getComponentsSold() {
        return componentsSold;
    }

    public void setComponentsSold(List<Component> componentsSold) {
        this.componentsSold = componentsSold;
    }
}
