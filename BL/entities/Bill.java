package ProyectoPOO.Main.BL.entities;
import java.util.Date;

public class Bill {
    private int billNumber;
    private Date date;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String ram;
    private String processor;
    private String storage;
    private String powerSupply;
    private String motherboard;
    private String videoCard;
    private float price;

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

    public String getRam() {
        return ram;
    }

    public String getProcessor() {
        return processor;
    }

    public String getStorage() {
        return storage;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public String getVideoCard() {
        return videoCard;
    }

    public Bill(int billNumber, String name, String lastName, String phoneNumber, String ram, String processor, String storage, String powerSupply, String motherboard, String videoCard, float Price) {
        this.billNumber = billNumber;
        this.date = new Date();
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.ram = ram;
        this.processor = processor;
        this.storage = storage;
        this.powerSupply = powerSupply;
        this.motherboard = motherboard;
        this.videoCard = videoCard;
        this.price = Price;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public void setVideoCard(String videoCard) {
        this.videoCard = videoCard;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}