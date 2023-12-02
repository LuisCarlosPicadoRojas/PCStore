package ProyectoPOO.Main.BL.entities;
public class Product {
    private int code;
    private String name;
    private float price;
    private float rating = 0;
    private String productType;

    // Constructors
    public Product(int code, String name, float price, String productType) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.productType = productType;
    }
    public Product(int code, String name, float price, String productType, float rating) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.rating = rating;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}