package ProyectoPOO.Main.BL.entities;

public class Ram extends Product {
    private int GBs;

    public Ram(int code, String name, float price, int GBs, String productType, float rating) {
        super(code, name, price, productType, rating);
        this.GBs = GBs;
    }

    public int getGBs() {
        return GBs;
    }

    public void setGBs(int GBs) {
        this.GBs = GBs;
    }
}
