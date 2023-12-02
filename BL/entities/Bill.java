package ProyectoPOO.Main.BL.entities;
import java.util.Date;
import java.util.Random;

public class Bill {
    private int billNumber;
    private Date date;
    private String name;
    private String lastName;
    private String phoneNumber;

    public Bill(String name, String lastName, String phoneNumber) {
        this.billNumber = billNumber;
        this.date = new Date();
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}